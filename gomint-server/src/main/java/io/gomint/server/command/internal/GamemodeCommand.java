package io.gomint.server.command.internal;

import io.gomint.ChatColor;
import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.IntegerValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.world.Gamemode;

import java.util.Map;

/**
 * @author NycuRO
 * @version 1.0
 */
@Name( "gamemode" )
@Description( "Sets a player's game mode" )
@Permission( "gomint.command.gamemode" )
@Overload( {
    @Parameter( name = "gameMode", validator = IntegerValidator.class),
    @Parameter( name = "gameMode", validator = StringValidator.class, arguments = { "[a-zA-Z0-9ß\\-]+" })
} )
@Overload( {
    @Parameter( name = "target", validator = TargetValidator.class),
} )
public class GamemodeCommand extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();

        Gamemode gamemode = (Gamemode) arguments.get( "gameMode");
        EntityPlayer entityPlayer = (EntityPlayer) arguments.get( "target" );

        // Spectator gamemode wasn't in showed commands, so i don't added it.
        if (arguments.get( "gameMode" ) == "s") {
            player.setGamemode(Gamemode.SURVIVAL);
            output.success("Set own game mode to %%s" + player.getGamemode());
        } else if (arguments.get( "gameMode" ) == "c") {
            player.setGamemode(Gamemode.CREATIVE);
            output.success("Set own game mode to %%s" + player.getGamemode());
        } else if (arguments.get( "gameMode" ) == "a") {
            player.setGamemode(Gamemode.ADVENTURE);
            output.success("Set own game mode to %%s" + player.getGamemode());
        } else if (entityPlayer == null || arguments.get( "target" ) == "@s") {
            player.setGamemode(gamemode);
            output.success( "Set own game mode to %%s" + player.getGamemode());
        } else if (arguments.get( "target" ) == "@a") {
            for (EntityPlayer players : GoMint.instance().getPlayers()) {
                players.setGamemode(gamemode);
                output.success("Set own game mode to %%s" + player.getGamemode());
            }
        } else if (arguments.get( "target" ) == "@e") {
            output.success(ChatColor.RED + "Select must be player-type" );
        } else if (arguments.get( "target") == "@p") {
            // I understand from Client was OFFLINE PLAYERS, yeah, but i get message again.
            output.success("Set own game mode to %%s" + player.getGamemode());
        }

        entityPlayer.setGamemode(gamemode);
        output.success( "Gamemode was changed succesfully to %%s" + entityPlayer.getGamemode());
        return output;
    }

}
