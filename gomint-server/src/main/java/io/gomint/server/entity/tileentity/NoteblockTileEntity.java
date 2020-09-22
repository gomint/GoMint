package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Sound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Music")
public class NoteblockTileEntity extends TileEntity {

    private byte note;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public NoteblockTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.note = compound.getByte( "note", (byte) 0 );
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        if ( this.note == 24 ) {
            this.note = 0;
        } else {
            this.note++;
        }

        playSound();
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "Music" );
        compound.addValue( "note", this.note );
    }

    /**
     * Play the sound which this note block has stored
     */
    public void playSound() {
        this.getBlock().getWorld().playSound( new Vector(this.getBlock().getPosition()), Sound.NOTE, this.note );
    }

}
