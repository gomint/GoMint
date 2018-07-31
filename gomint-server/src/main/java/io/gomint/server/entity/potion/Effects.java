/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion;

import io.gomint.server.GoMintServer;
import io.gomint.server.entity.potion.effect.Effect;
import io.gomint.server.player.EffectManager;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Effects {

    private static final Logger LOGGER = LoggerFactory.getLogger( Effects.class );
    private final Registry<Effect> generators;

    public Effects( GoMintServer server ) {
        this.generators = new Registry<>( server, clazz -> () -> {
            try {
                return (Effect) clazz.newInstance();
            } catch ( InstantiationException | IllegalAccessException e ) {
                LOGGER.error( "Could not generate new effect", e );
            }

            return null;
        } );

        this.generators.register( "io.gomint.server.entity.potion.effect" );
    }

    public Effect generate( int id, int amplifier, long lengthInMS, EffectManager manager ) {
        Generator<Effect> instance = this.generators.getGenerator( id );
        if ( instance != null ) {
            Effect effect = instance.generate();
            effect.setData( manager, amplifier, lengthInMS );
            return effect;
        }

        return null;
    }

}
