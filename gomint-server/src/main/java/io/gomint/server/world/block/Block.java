package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketUpdateBlock;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.storage.TemporaryStorage;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Block implements io.gomint.world.block.Block {

    // CHECKSTYLE:OFF
    @Setter protected WorldAdapter world;
    @Setter @Getter protected Location location;
    @Setter @Getter private byte blockData;
    @Setter private TileEntity tileEntity;
    @Setter @Getter private byte skyLightLevel;
    @Setter @Getter private byte blockLightLevel;
    // CHECKSTYLE:ON

    /**
     * Called when a normal block update should be done
     *
     * @param updateReason  The reason why this block should update
     * @param currentTimeMS The timestamp when the tick has begun
     * @param dT            The difference time in full seconds since the last tick
     * @return a timestamp for the next execution
     */
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        return -1;
    }

    /**
     * Method which gets called when a entity steps on a block
     *
     * @param entity which stepped on the block
     */
    public void stepOn( Entity entity ) {

    }

    /**
     * method which gets called when a entity got off a block which it {@link #stepOn(Entity)}.
     *
     * @param entity which got off the block
     */
    public void gotOff( Entity entity ) {

    }

    /**
     * Called when a entity decides to interact with the block
     *
     * @param entity  The entity which interacts with it
     * @param face    The block face the entity interacts with
     * @param facePos The position where the entity interacted with the block
     * @param item    The item with which the entity interacted, can be null
     */
    public boolean interact( Entity entity, int face, Vector facePos, ItemStack item ) {
        return false;
    }

    public <T, R> R storeInTemporaryStorage( String key, Function<T, R> func ) {
        // Check world storage
        BlockPosition blockPosition = this.location.toBlockPosition();
        TemporaryStorage temporaryStorage = ( (WorldAdapter) this.location.getWorld() ).getTemporaryBlockStorage( blockPosition );
        return temporaryStorage.store( key, func );
    }

    public <T> T getFromTemporaryStorage( String key ) {
        // Check world storage
        BlockPosition blockPosition = this.location.toBlockPosition();
        TemporaryStorage temporaryStorage = ( (WorldAdapter) this.location.getWorld() ).getTemporaryBlockStorage( blockPosition );
        return (T) temporaryStorage.get( key );
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    /**
     * Does this block need a tile entity on placement?
     *
     * @return true when it needs a tile entity, false if it doesn't
     */
    public boolean needsTileentity() {
        return false;
    }

    /**
     * Create a tile entity at the blocks location
     */
    public void createTileentity() {

    }

    /**
     * Update the data of the block and send the update
     */
    protected void updateBlock() {
        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        worldAdapter.setBlockData( this.location, this.blockData );
        worldAdapter.updateBlock( this.location.toBlockPosition() );
    }

    @Override
    public <T extends io.gomint.world.block.Block> T setType( Class<T> blockType, byte data ) {
        Vector pos = this.location.toVector();
        Block instance = Blocks.get( blockType );
        if ( instance != null ) {
            WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
            worldAdapter.setBlockId( pos, instance.getBlockId() );
            worldAdapter.setBlockData( pos, data );

            instance.setLocation( this.location );

            // Check if new block needs tile entity
            if ( instance.needsTileentity() ) {
                instance.createTileentity();
            }

            worldAdapter.updateBlock( pos.toBlockPosition() );
        }

        return world.getBlockAt( pos.toBlockPosition() );
    }

    /**
     * Get the break time needed to break this block without any enchantings or tools
     *
     * @return time in milliseconds it needs to break this block
     */
    public long getBreakTime() {
        return 250;
    }

    /**
     * Get the attached tile entity to this block
     *
     * @param <T> The type of the tile entity
     * @return null when there is not tile entity attached, otherwise the stored tile entity
     */
    public <T extends TileEntity> T getTileEntity() {
        return (T) this.tileEntity;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 1,
                this.location.getZ() + 1
        );
    }

    @Override
    public float getFrictionFactor() {
        return 0.6f;
    }

    @Override
    public boolean canPassThrough() {
        return false;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof Block ) {
            if ( ( (Block) obj ).getBlockId() == getBlockId() ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public io.gomint.world.block.Block getSide( int face ) {
        switch ( face ) {
            case 0:
                return location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.DOWN ) );
            case 1:
                return location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.UP ) );
            case 2:
                return location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.NORTH ) );
            case 3:
                return location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.SOUTH ) );
            case 4:
                return location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.WEST ) );
            case 5:
                return location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.EAST ) );
        }

        return null;
    }

    /**
     * Check if this block can be replaced by the item in the arguments
     *
     * @param item which may replace this block
     * @return true if this block can be replaced with the item, false when not
     */
    public boolean canBeReplaced( ItemStack item ) {
        return false;
    }

    /**
     * Send informations for this block
     *
     * @param connection which should get the block data
     */
    public void send( PlayerConnection connection ) {
        PacketUpdateBlock updateBlock = new PacketUpdateBlock();
        updateBlock.setPosition( this.location.toBlockPosition() );
        updateBlock.setBlockId( this.getBlockId() );
        updateBlock.setPrioAndMetadata( (byte) ( ( PacketUpdateBlock.FLAG_ALL_PRIORITY << 4 ) | ( this.getBlockData() ) ) );
        connection.addToSendQueue( updateBlock );
    }

    public boolean beforePlacement( ItemStack item, Location location ) {
        return true;
    }

    public void afterPlacement() {

    }

    public byte calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        return (byte) item.getData();
    }

}
