package io.gomint.command.validator;

import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;
import io.gomint.entity.Entity;
import io.gomint.entity.Player;

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
    public Object validate( List<String> input, Entity entity ) {
        if ( input.get( 0 ).equals( "@s" ) ) {
            return entity;
        }

        for ( Player player : entity.getWorld().getPlayers() ) {
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

}
