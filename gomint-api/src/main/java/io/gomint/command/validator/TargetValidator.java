package io.gomint.command.validator;

import io.gomint.GoMint;
import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;
import io.gomint.command.PlayerCommandSender;
import io.gomint.entity.EntityPlayer;

import java.util.Collection;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TargetValidator extends ParamValidator {

    @Override
    public ParamType getType() {
        return ParamType.TARGET;
    }

    @Override
    public boolean hasValues() {
        return false;
    }

    @Override
    public List<String> values() {
        return null;
    }

    @Override
    public Object validate( List<String> input, CommandSender commandSender ) {
        Collection<EntityPlayer> searchPool = null;
        if ( commandSender instanceof PlayerCommandSender ) {
            if ( input.get( 0 ).equals( "@s" ) ) {
                return commandSender;
            }

            searchPool = ( (EntityPlayer) commandSender ).getWorld().getPlayers();
        } else {
            searchPool = GoMint.instance().getPlayers();
        }

        for ( EntityPlayer player : searchPool ) {
            if ( player.getName().equals( input.get( 0 ) ) ) {
                return player;
            }
        }

        return null;
    }

    @Override
    public int consumesParts() {
        return 1;
    }

    @Override
    public String getHelpText() {
        return "target:player";
    }

}
