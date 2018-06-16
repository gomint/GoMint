package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Sound;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NoteblockTileEntity extends TileEntity {

    private byte note;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param compound The TagCompound which should be used to read data from
     * @param world    The world in which this TileEntity resides
     */
    public NoteblockTileEntity( NBTTagCompound compound, WorldAdapter world ) {
        super( compound, world );

        this.note = compound.getByte( "note", (byte) 0 );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        if ( this.note == 24 ) {
            this.note = 0;
        } else {
            this.note++;
        }

        playSound();
    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "Music" );
        compound.addValue( "note", this.note );
    }

    /**
     * Play the sound which this note block has stored
     */
    public void playSound() {
        this.location.getWorld().playSound( this.location, Sound.NOTE, this.note );
    }

}
