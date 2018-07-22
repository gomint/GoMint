package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import io.gomint.command.annotation.Overload;
import io.gomint.command.validator.BooleanValidator;
import io.gomint.command.validator.EnumValidator;
import io.gomint.command.validator.IntegerValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.potion.Effect;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "effect" )
@Description( "Add or remove status effects." )
@Permission( "gomint.command.effect" )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "clear", validator = EnumValidator.class, arguments = { "clear" } )
} )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "effect", validator = EnumValidator.class, arguments = {
        "absorption", "blindness", "conduit_power", "fatal_poison", "fire_resistance", "haste", "health_boost", "hunger", "instant_damage",
        "instant_health", "invisibility", "jump_boost", "levitation", "mining_fatigue", "nausea", "night_vision", "poison", "regeneration",
        "resistance", "saturation", "slowness", "speed", "strength", "water_breathing", "weakness", "wither" } ),
    @Parameter( name = "seconds", validator = IntegerValidator.class, optional = true ),
    @Parameter( name = "amplifier", validator = IntegerValidator.class, optional = true ),
    @Parameter( name = "hideParticles", validator = BooleanValidator.class, optional = true )
} )
public class EffectCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = arguments.get( "player" ) == null ? (EntityPlayer) player : (EntityPlayer) arguments.get( "player" );

        if( target == null ) {
            return output.fail( "No targets matched selector" );
        }

        if( arguments.containsKey( "clear" ) ) {
            if( !target.hasEffects() ) {
                return output.fail( "Couldn't take any effects from %%s as they do not have any", target.getName() );
            }
            target.removeAllEffects();
            return output.success( "Took all effects from %%s", target.getName() );
        }

        if( arguments.containsKey( "effect" ) ) {
            int amplifier = 0;
            int duration = 30;
            PotionEffect potionEffect = PotionEffect.valueOf( ( (String) arguments.get( "effect" ) ).toUpperCase() );

            if( arguments.containsKey( "seconds" ) ) {
                duration = (int) arguments.get( "seconds" );
            }
            if( arguments.containsKey( "amplifier" ) ) {
                amplifier = (int) arguments.get( "amplifier" );
            }

            Effect effect = target.addEffect( potionEffect, amplifier, duration, TimeUnit.SECONDS );

            if( arguments.containsKey( "hideParticles" ) ) {
                effect.setVisible( (boolean) arguments.get( "hideParticles" ) );
            }
            output.success( "Gave %%s * %%s to %%s for %%s seconds", potionEffect.getEffectName(), amplifier, target.getName(), duration );
        }

        return output;
    }

}
