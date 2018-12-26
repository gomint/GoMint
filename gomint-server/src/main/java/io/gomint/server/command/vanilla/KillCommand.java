package io.gomint.server.command.vanilla;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.TargetValidator;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;

/**
 * @author Kaooot
 * @version 1.0
 */
@Name( "kill" )
@Permission( "gomint.command.kill" )
@Description( "Kills entities (players, mobs, etc.)." )
@Overload( {
    @Parameter( name = "target", validator = TargetValidator.class, optional = true )
} )
public class KillCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String alias, Map<String, Object> arguments ) {
        CommandOutput commandOutput = new CommandOutput();

        EntityPlayer target = (EntityPlayer) arguments.get( "target" );

        if ( target != null ) {
            target.attack( target.getHealth(), EntityDamageEvent.DamageSource.PROJECTILE );
            GoMint.instance().getPlayers().forEach( players -> players.sendMessage( target.getName() + " died" ) );
            commandOutput.success( "Killed " + target.getName() );
            System.out.println( commandSender instanceof PlayerCommandSender ? target.getName() + " was killed by " + ((EntityPlayer) commandSender).getName() : target.getName() + " was killed by CONSOLE" );
        } else {
            if ( commandSender instanceof PlayerCommandSender ) {
                EntityPlayer player = (EntityPlayer) commandSender;

                player.attack( player.getHealth(), EntityDamageEvent.DamageSource.PROJECTILE );
                GoMint.instance().getPlayers().forEach( players -> players.sendMessage( player.getName() + " died" ) );
                commandOutput.success( "Killed " + player.getName() );
                System.out.println( player.getName() + " was killed by " + player.getName() );
            } else {
                commandOutput.fail( "Please provide a player to kill: /kill [target]" );
            }
        }
        return commandOutput;
    }
}
