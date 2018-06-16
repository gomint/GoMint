package io.gomint.plugin.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.Vector;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "velocity" )
@Description( "Give custom velocity to player" )
public class CommandVelocity extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        player.setVelocity( new Vector( 0, 2, 0 ) );
        output.success( "Applied velocity" );
        return output;
    }

}
