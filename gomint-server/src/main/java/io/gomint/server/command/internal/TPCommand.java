

package io.gomint.server.command.internal;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.BlockPositionValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.world.World;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "tp" )
@Description( "Teleport to a given place or entity" )
@Permission( "gomint.command.tp" )
@Overload( {
    @Parameter( name = "world", validator = StringValidator.class, arguments = { "[a-zA-Z0-9ß\\-]+" } ),
    @Parameter( name = "position", validator = BlockPositionValidator.class )
} )
@Overload( {
    @Parameter( name = "target", validator = TargetValidator.class )
} )
@Overload( {
    @Parameter( name = "position", validator = BlockPositionValidator.class )
} )
public class TPCommand extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();

        // Check for entity teleportation
        if ( arguments.containsKey( "target" ) ) {
            EntityPlayer entity = (EntityPlayer) arguments.get( "target" );
            player.teleport( entity.getLocation() );
            output.success( "You have been teleported to %%s", entity.getName() );
            return output;
        }

        // Do we have a world given?
        Location to = new Location( player.getWorld(), 0, 0, 0 );
        if ( arguments.containsKey( "world" ) ) {
            World world = GoMint.instance().getWorld( (String) arguments.get( "world" ) );
            if ( world == null ) {
                output.fail( "World %%s could not be found", arguments.get( "world" ) );
                return output;
            } else {
                to.setWorld( world );
            }
        }

        BlockPosition position = (BlockPosition) arguments.get( "position" );
        to.setX( position.getX() );
        to.setY( position.getY() );
        to.setZ( position.getZ() );

        player.teleport( to );
        output.success( "You have been teleported to %%s, %%s, %%s, %%s", to.getWorld().getWorldName(), to.getX(), to.getY(), to.getZ() );
        return output;
    }

}
