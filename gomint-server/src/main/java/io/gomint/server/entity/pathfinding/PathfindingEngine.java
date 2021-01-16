package io.gomint.server.entity.pathfinding;

import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Transformable;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.block.Block;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A pathfinding engine instance may be used to navigate an object through the game world.
 * It may be given the location to navigate to and it will try to find the best route available.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class PathfindingEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathfindingEngine.class);
    private static final int MAXIMUM_NODES_TO_EXPLORE = 200;

    // The transform that holds the current position the object is located at:
    private final Transformable transform;

    // The goal point the pathfinding engine is supposed to navigate to:
    private Location goal;
    private boolean dirty;

    // The path to the goal if cached:
    private List<BlockPosition> cachedPath;

    /**
     * Constructs a new pathfinding engine that will make changes to the given transform
     * when moving.
     *
     * @param transform The transform to modify accordingly
     */
    public PathfindingEngine(Transformable transform) {
        this.transform = transform;
        this.dirty = false;
        this.cachedPath = null;
    }

    /**
     * Gets the transform attached to the pathfinding engine.
     *
     * @return The transform attached to the pathfinding engine
     */
    public Transformable transform() {
        return this.transform;
    }

    /**
     * Gets the goal the pathfinding engine is trying to navigate to.
     *
     * @return The goal the pathfinding engine is trying to navigate to
     */
    public Location goal() {
        return this.goal;
    }

    /**
     * Sets the goal point the pathfinding engine is trying to reach.
     *
     * @param goal The goal point to reach
     */
    public PathfindingEngine goal(Location goal) {
        this.goal = goal;
        this.dirty = true;
        return this;
    }

    /**
     * Gets the path the pathfinding engine proposes for reaching the current goal. The
     * returned path will only ever be updated if a new goal is set. It will not update
     * and / or remove parts depending on a changed position of the transform the
     * pathfinding engine is associated with. In order to translate a path which has been
     * built into actual movement one will need to move the entity via a MovementController.
     * <p>
     * The path may be read as an instruction set of block-to-block movements which should
     * be followed the exact order they appear inside the returned list.
     *
     * @return The current path proposed by the pathfinding engine
     */
    public List<BlockPosition> path() {
        if (this.dirty) {
            this.cachedPath = this.calculateShortestPath();
        }

        return this.cachedPath;
    }

    /**
     * Calculates the shortest path from the pathfinding engine's transform to its goal. May
     * return null if there is no solution to the problem or a certain threshold has been
     * exceeded.
     *
     * @return The shortest path available or null if no solution was found
     */
    private List<BlockPosition> calculateShortestPath() {
        // Preamble:
        // Very simple implementation of A* pathfinding. May be optimized in the future.
        // Concrete implementation classes of collection types are used to aggressively
        // remind of the data structure actually chosen.

        if (this.goal == null) {
            return null;
        }

        final BlockPosition goalTriple = new BlockPosition((int) this.goal.x(), (int) this.goal.y(), (int) this.goal.z());

        LongSet closedSet = new LongOpenHashSet();
        Map<BlockPosition, AStarNode> discoveredMap = new HashMap<>();
        PriorityQueue<AStarNode> discoveredNodes = new PriorityQueue<>();

        AStarNode startNode = new AStarNode(new BlockPosition((int) this.transform.positionX(), (int) this.transform.positionY(), (int) this.transform.positionZ()));
        startNode.setG(0.0F);
        startNode.setF(this.estimateDistance(startNode.getBlockPosition(), this.goal));
        startNode.setK(1);
        startNode.setPredecessor(startNode);

        discoveredNodes.add(startNode);
        discoveredMap.put(startNode.getBlockPosition(), startNode);

        int exploredNodesCount = 0;

        this.dirty = false;

        while (!discoveredNodes.isEmpty() && exploredNodesCount < MAXIMUM_NODES_TO_EXPLORE) {
            AStarNode node = discoveredNodes.poll();
            if (node.getBlockPosition().equals(goalTriple)) {
                // Goal was reached -> reconstruct path and return:
                ArrayList<BlockPosition> path = new ArrayList<>(node.getK());
                while (true) {
                    path.add(node.getBlockPosition());

                    if (node.isStart()) {
                        break;
                    }

                    node = node.getPredecessor();
                }

                Collections.reverse(path);
                return path;
            }

            // This node will not be examined any further:
            discoveredMap.remove(node.getBlockPosition());
            closedSet.add(CoordinateUtils.toLong(node.getBlockPosition()));

            // Examine neighbour nodes:
            for (int i = node.getBlockPosition().x() - 1; i <= node.getBlockPosition().x() + 1; ++i) {
                out:
                for (int k = node.getBlockPosition().z() - 1; k <= node.getBlockPosition().z() + 1; ++k) {
                    if (closedSet.contains(CoordinateUtils.toLong(i, node.getBlockPosition().y(), k))) {
                        continue;
                    }

                    BlockPosition neighbourTriple = new BlockPosition(i, node.getBlockPosition().y(), k);

                    // Got to make sure this neighbour is even in reach from this block
                    // Check if the block is walkable or jumpable
                    Block block = this.goal().world().blockAt(neighbourTriple);
                    if (!block.canPassThrough()) {
                        List<AxisAlignedBB> bbs = block.boundingBoxes();
                        if (bbs != null) {
                            for (AxisAlignedBB bb : bbs) {
                                double diff = bb.maxY() - neighbourTriple.y();
                                if (diff > 0 && diff <= 0.5F) {
                                    neighbourTriple = new BlockPosition(neighbourTriple.x(), neighbourTriple.y() + 1, neighbourTriple.z());
                                    break;
                                } else {
                                    continue out;
                                }
                            }
                        }
                    }

                    // We need to account for gravity here
                    Block blockBeneath = this.goal().world().blockAt(neighbourTriple.x(), neighbourTriple.y() - 1, neighbourTriple.z());
                    if (blockBeneath.canPassThrough()) {
                        neighbourTriple = new BlockPosition(neighbourTriple.x(), neighbourTriple.y() - 1, neighbourTriple.z());
                    }

                    // This block is a valid neighbour:
                    AStarNode neighbourNode = discoveredMap.get(neighbourTriple);
                    if (neighbourNode != null) {
                        double g = node.getG() + this.gridDistance(node.getBlockPosition(), neighbourTriple);
                        if (g < neighbourNode.getG()) {
                            neighbourNode.setG(g);
                            neighbourNode.setF(g + this.estimateDistance(neighbourTriple, this.goal));
                            neighbourNode.setK(node.getK() + 1);
                            neighbourNode.setPredecessor(node);
                        }
                    } else {
                        neighbourNode = new AStarNode(neighbourTriple);
                        neighbourNode.setG(node.getG() + this.gridDistance(node.getBlockPosition(), neighbourTriple));
                        neighbourNode.setF(neighbourNode.getG() + this.estimateDistance(neighbourTriple, this.goal));
                        neighbourNode.setK(node.getK() + 1);
                        neighbourNode.setPredecessor(node);
                        discoveredMap.put(neighbourTriple, neighbourNode);
                        discoveredNodes.add(neighbourNode);
                    }
                }
            }

            ++exploredNodesCount;

            if (exploredNodesCount >= MAXIMUM_NODES_TO_EXPLORE) {
                if (LOGGER.isDebugEnabled()) {
                    // Debug
                    // Goal was reached -> reconstruct path and return:
                    ArrayList<BlockPosition> path = new ArrayList<>(node.getK());
                    while (true) {
                        path.add(node.getBlockPosition());

                        if (node.isStart()) {
                            break;
                        }

                        node = node.getPredecessor();
                    }

                    Collections.reverse(path);

                    LOGGER.debug("Path selected:");
                    for (BlockPosition intTriple : path) {
                        Block block = this.goal().world().blockAt(intTriple.x(), intTriple.y(), intTriple.z());
                        LOGGER.debug("> " + intTriple + " > " + block.getClass());
                    }
                }
            }
        }


        // Either has the threshold been exceeded or there is no solution to the problem:
        return null;
    }

    private double gridDistance(BlockPosition a, BlockPosition b) {
        return (Math.abs(b.x() - a.x()) + Math.abs(b.z() - a.z()));
    }

    public double estimateDistance(BlockPosition a, Vector b) {
        return (Math.abs(b.x() - a.x()) + Math.abs(b.y() - a.y()) + Math.abs(b.z() - a.z()));
    }

}
