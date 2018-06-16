package net.core.commands;

import io.gomint.ChatColor;
import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.entity.EntityPlayer;

import java.util.ArrayList;
import java.util.Map;

@Name( "vanish" )
@Description( "Hide yourself." )

public class CommandVanish extends Command {

    public static ArrayList<String> vanish = new ArrayList<>();

    @Override
    public CommandOutput execute( EntityPlayer entityPlayer, String s, Map<String, Object> map ) {
        CommandOutput commandOutput = new CommandOutput();

        String uuid = entityPlayer.getUUID().toString();

        if( ! vanish.contains( uuid ) ) {
            for( EntityPlayer players : GoMint.instance().getPlayers() ) {
                if( GoMint.instance().getPlayers().size() != 1 ) {
                    if( ! entityPlayer.isHidden( players ) ) {

                        vanish.add( uuid );
                        players.hidePlayer( entityPlayer );
                        commandOutput.success( "You are now invisible." );

                    } else {
                        commandOutput.fail( ChatColor.RED + "You are already invisible!" );
                    }
                } else {
                    commandOutput.fail( ChatColor.RED + "You are alone at the server!" );
                }
            }
        } else {
            for( EntityPlayer players : GoMint.instance().getPlayers() ) {
                if( entityPlayer.isHidden( players ) ) {

                    vanish.remove( uuid );
                    players.showPlayer( entityPlayer );
                    commandOutput.success( "You are now visible." );
                } else {
                    commandOutput.fail( ChatColor.RED + "You are not invisible!" );
                }
            }
        }
        return commandOutput;
    }
}
