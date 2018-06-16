package io.gomint.server.entity.tileentity;

import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class CommandBlockTileEntity extends ContainerTileEntity {

    private String command;
    private int successCount;
    private String output;
    private boolean trackOutput;
    private List<String> parameter;

    private boolean auto;
    private boolean powered;

    private boolean redstoneMode;
    private boolean conditionalMode;
    private int commandMode;

    private int version;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public CommandBlockTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        // Read the NBT tag for its given data
        this.command = tagCompound.getString( "Command", "" );
        this.successCount = tagCompound.getInteger( "SuccessCount", 0 );
        this.output = tagCompound.getString( "LastOutput", "" );
        this.trackOutput = tagCompound.getByte( "TrackOutput", (byte) 0 ) != 0;

        List<Object> parameterObjects = tagCompound.getList( "LastOutputParams", true );
        List<String> param = new ArrayList<>();
        for ( Object parameterObject : parameterObjects ) {
            param.add( (String) parameterObject );
        }

        this.parameter = param;

        //
        this.auto = tagCompound.getByte( "auto", (byte) 0 ) != 0;
        this.powered = tagCompound.getByte( "powered", (byte) 0 ) != 0;

        //
        this.redstoneMode = tagCompound.getByte( "LPRedstoneMode", (byte) 1 ) != 0;
        this.conditionalMode = tagCompound.getByte( "LPCondionalMode", (byte) 0 ) != 0;
        this.commandMode = tagCompound.getInteger( "LPCommandMode", 0 );

        //
        this.version = tagCompound.getInteger( "Version", 1 );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "CommandBlock" );
        compound.addValue( "conditionMet", (byte) 1 );

        compound.addValue( "Command", this.command );
        compound.addValue( "SuccessCount", this.successCount );

        compound.addValue( "TrackOutput", (byte) ( this.trackOutput ? 1 : 0 ) );

        if ( this.trackOutput ) {
            compound.addValue( "LastOutput", this.output );
        }

        compound.addValue( "LastOutputParams", this.parameter );

        //
        compound.addValue( "auto", (byte) ( this.auto ? 1 : 0 ) );
        compound.addValue( "powered", (byte) ( this.powered ? 1 : 0 ) );

        //
        compound.addValue( "LPRedstoneMode", (byte) ( this.redstoneMode ? 1 : 0 ) );
        compound.addValue( "LPCondionalMode", (byte) ( this.conditionalMode ? 1 : 0 ) );
        compound.addValue( "LPCommandMode", this.commandMode );

        //
        compound.addValue( "Version", this.version );
    }

}
