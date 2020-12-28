/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test.inventory.item;

import io.gomint.math.Location;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.math.Vector;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.test.IntegrationTest;
import io.gomint.server.util.ClassPath;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.World;
import io.gomint.world.WorldType;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.BlockAir;
import io.gomint.world.block.data.Facing;
import io.gomint.world.generator.CreateOptions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlacementTest extends IntegrationTest {

    private WorldAdapter world;

    @Test
    @Order(1)
    public void generateNonExisting() {
        this.world = (WorldAdapter) this.server.createWorld("test", new CreateOptions().worldType(WorldType.IN_MEMORY));
    }

    @Test
    @Order(2)
    public void placeAll() throws IOException {
        Block block = this.world.getBlockAt(50, 50, 50);
        Block downBlock = block.getSide(Facing.DOWN);

        EntityPlayer player = mock(EntityPlayer.class);
        when(player.getWorld()).thenReturn(this.world);
        when(player.getLocation()).thenReturn(new Location(this.world, 51, 50, 50, 10, 20));

        ClassPath classPath = new ClassPath("io.gomint");
        classPath.getTopLevelClasses("io.gomint.inventory.item", classInfo -> {
            Class<? extends ItemStack> clazz = classInfo.load();
            ItemStack stack = this.server.createItemStack(clazz, 1);
            if (stack != null) {
                block.setBlockType(BlockAir.class);

                System.out.println("Testing stack: " + stack.getClass().getName());
                Block newBlock = (Block) stack.getBlock();
                if (newBlock != null) {
                    this.server.getBlocks().replaceWithItem(newBlock, player, downBlock, block, Facing.UP, stack, new Vector(0.5f, 0, 0.5f));
                }
            } else {
                System.out.println("Class " + clazz.getName() + " did not generate a item");
            }
        });
    }

}
