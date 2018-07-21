package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.IntegerValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Name( "effect" )
@Description( "Add an effect to given player." )
@Permission( "gomint.command.effect" )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "effect", validator = StringValidator.class, arguments = {".*"} ),
    @Parameter( name = "seconds", validator = IntegerValidator.class, arguments = {".*"} ),
    @Parameter( name = "amplifier", validator = IntegerValidator.class, arguments = {".*"} )
} )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "clear", validator = StringValidator.class, arguments = {".*"} )
} )
public class EffectCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();

        EntityPlayer player = (EntityPlayer) arguments.get( "player" );
        String effect = (String) arguments.get( "effect" );
        Integer seconds = (Integer) arguments.get( "seconds" );
        Integer amplifier = (Integer) arguments.get( "amplifier" );
        String clear = (String) arguments.get( "clear" );
        TimeUnit timeUnit = TimeUnit.SECONDS;

        switch( effect ) {
            case "speed":
                player.addEffect( PotionEffect.SPEED, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "slowness":
                player.addEffect( PotionEffect.SLOWNESS, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "haste":
                player.addEffect( PotionEffect.HASTE, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "mining_fatigue":
                player.addEffect( PotionEffect.MINING_FATIGUE, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "strength":
                player.addEffect( PotionEffect.STRENGTH, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "healing":
                player.addEffect( PotionEffect.HEALING, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "harming":
                player.addEffect( PotionEffect.HARMING, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "jump":
                player.addEffect( PotionEffect.JUMP, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "nausea":
                player.addEffect( PotionEffect.NAUSEA, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "regeneration":
                player.addEffect( PotionEffect.REGENERATION, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "damage_resistance":
                player.addEffect( PotionEffect.DAMAGE_RESISTANCE, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "fire_resistance":
                player.addEffect( PotionEffect.FIRE_RESISTANCE, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "invisibility":
                player.addEffect( PotionEffect.INVISIBILITY, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "blindness":
                player.addEffect( PotionEffect.BLINDNESS, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "night_vision":
                player.addEffect( PotionEffect.NIGHT_VISION, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "hunger":
                player.addEffect( PotionEffect.HUNGER, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "weakness":
                player.addEffect( PotionEffect.WEAKNESS, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "poison":
                player.addEffect( PotionEffect.POISON, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "wither":
                player.addEffect( PotionEffect.WITHER, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "health_boost":
                player.addEffect( PotionEffect.HEALTH_BOOST, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "absorption":
                player.addEffect( PotionEffect.ABSORPTION, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "saturation":
                player.addEffect( PotionEffect.SATURATION, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;

            case "levitation":
                player.addEffect( PotionEffect.LEVITATION, seconds, amplifier, timeUnit );
                output.success( "Effect " + effect + " added to " + player.getName() + " for " + seconds + " seconds with amplifier " + amplifier + "." );
                break;
        }

        if( clear.equals( "clear" ) ) {
            player.removeAllEffects();
            output.success( "All effects was successfully removed from " + player.getName() );
        }
        return output;
    }
}
