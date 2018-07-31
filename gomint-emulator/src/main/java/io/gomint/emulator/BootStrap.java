/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator;

import io.gomint.emulator.client.Client;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BootStrap {

    public static void main( String[] args ) throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool( 8 ); // Amount of cores
        PostProcessExecutorService postProcessExecutorService = new PostProcessExecutorService();

        service.scheduleAtFixedRate( new Runnable() {
            @Override
            public void run() {
                Client.printDebug();
            }
        }, 5, 5, TimeUnit.SECONDS );

        // Configurator.setLevel( "", Level.TRACE );
        Configurator.setLevel( "io.gomint.emulator.PostProcessWorker", Level.INFO );

        for ( int i = 0; i < 1; i++ ) {
            service.execute( () -> {
                Client client = new Client( service, postProcessExecutorService.getExecutor(), null );
                client.ping( "192.168.178.121", 19132 );

                try {
                    Thread.sleep( 150 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }

                client.connect( "192.168.178.121", 19132 );
            } );

            Thread.sleep( 500 );
        }
    }

}
