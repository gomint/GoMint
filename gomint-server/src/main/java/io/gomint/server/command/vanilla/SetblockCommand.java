package io.gomint.server.command.vanilla;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.ConsoleCommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import io.gomint.command.annotation.Overload;
import io.gomint.command.validator.EnumValidator;
import io.gomint.command.validator.IntegerValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.BlockPositionValidator;
import io.gomint.math.BlockPosition;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.world.block.Air;
import io.gomint.server.world.block.Block;
import io.gomint.world.World;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "setblock" )
@Description( "Changes a block to another block." )
@Permission( "gomint.command.setblock" )
@Overload( {
    @Parameter( name = "position", validator = BlockPositionValidator.class ),
    @Parameter( name = "tileName", validator = StringValidator.class, arguments = { "[a-zA-Z0-9ß\\-]+" } ),
    @Parameter( name = "tileData", validator = IntegerValidator.class, optional = true ),
    @Parameter( name = "action", validator = EnumValidator.class, optional = true, arguments = { "replace", "destroy", "keep" } )
} )
public class SetblockCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender sender, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        BlockPosition position = (BlockPosition) arguments.get( "position" );
        World world = ( sender instanceof ConsoleCommandSender ? GoMint.instance().getDefaultWorld() : ((EntityPlayer) sender).getWorld() );

        int id = MaterialMagicNumbers.valueOfWithId( (String) arguments.get( "tileName" ) );
        Block block = ((GoMintServer) GoMint.instance()).getBlocks().get( id );

        if( block == null ) {
            return output.fail( "Unknown block" );
        }

        if( arguments.containsKey( "action" ) ) {
            switch( (String) arguments.get( "action" ) ) {
                case "replace":

                    break;
                case "destroy":

                    break;
                case "keep":
                    if( !( world.getBlockAt( position ) instanceof Air ) ) {
                        return output.fail( "" );
                    }
                    break;
            }
        } else {
            world.getBlockAt( position ).setType( block.getClass() );
        }

        return output.success( "Block placed" );
    }

}
