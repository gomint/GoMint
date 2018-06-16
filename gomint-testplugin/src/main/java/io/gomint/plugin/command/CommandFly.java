package io.gomint.plugin.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.entity.EntityPlayer;

import java.util.ArrayList;
import java.util.Map;

@Name( "fly" )
@Description( "Activate / Deactivate survival flying on this server." )

public class CommandFly extends Command {

    private ArrayList<String> fly = new ArrayList<>();

    @Override
    public CommandOutput execute( EntityPlayer entityPlayer, String s, Map<String, Object> map ) {
        CommandOutput commandOutput = new CommandOutput();

        String uuid = entityPlayer.getUUID().toString();

        if( ! fly.contains( uuid ) ) {

            fly.add( uuid );
            entityPlayer.setAllowFlight( true );
            entityPlayer.setFlying( true );
            commandOutput.success( "You can fly now." );

        } else {

            fly.remove( uuid );
            entityPlayer.setAllowFlight( false );
            entityPlayer.setFlying( false );
            commandOutput.success( "You can't fly now." );
        }
        return commandOutput;
    }
}
