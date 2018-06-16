package io.gomint.plugin.command;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.plugin.TestPlugin;
import io.gomint.plugin.injection.InjectPlugin;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "spawn" )
@Description( "Test command for entity spawn" )
@Overload( {
    @Parameter( name = "target", validator = TargetValidator.class )
} )
public class CommandSpawn extends Command {

    @InjectPlugin
    private TestPlugin plugin;

    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        Location spawn = player.getWorld().getSpawnLocation();
        Location test = spawn.add( new Vector( 900, 0, 900 ) );

        for ( EntityPlayer entityPlayer : GoMint.instance().getPlayers() ) {
            entityPlayer.teleport( test );
        }

        this.plugin.getScheduler().schedule( () -> {
            for ( EntityPlayer entityPlayer : GoMint.instance().getPlayers() ) {
                entityPlayer.teleport( spawn );
            }
        }, 30, TimeUnit.SECONDS );

        return new CommandOutput();
    }

}
