package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
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
import io.gomint.server.entity.EntityPlayer;
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
    @Parameter( name = "tileName", validator = StringValidator.class ),
    @Parameter( name = "tileData", validator = IntegerValidator.class, optional = true ),
    @Parameter( name = "action", validator = EnumValidator.class, optional = true, arguments = { "replace", "destroy", "keep" } )
} )
public class SetblockCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) player;
        BlockPosition position = (BlockPosition) arguments.get( "position" );

        if( arguments.containsKey( "action" ) ) {
            switch( (String) arguments.get( "action" ) ) {
                case "replace":

                    break;
                case "destroy":

                    break;
                case "keep":

                    break;
            }
        }

        return output.success( "Block placed" );
    }

}
