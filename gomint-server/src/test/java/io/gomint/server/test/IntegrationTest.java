/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test;

import io.gomint.server.GoMintServer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {

    protected GoMintServer server;
    protected AnnotationConfigApplicationContext context;

    @BeforeAll
    public void setupGomint() {
        // Enable reflection access to JDK NIO buffers for netty
        System.setProperty( "io.netty.tryReflectionSetAccessible","true");

        OptionParser parser = new OptionParser();
        OptionSet options = parser.parse();

        // Init spring boot context
        this.context = new AnnotationConfigApplicationContext();
        this.context.registerBean(OptionSet.class,  () -> options); // Register CLI options
        this.context.scan("io.gomint.server");
        this.context.refresh();

        this.server = this.context.getBean(GoMintServer.class);
    }
    
    @AfterAll
    public void tearDownGomint() {
        this.server.shutdown();
        this.context.close();
    }

}
