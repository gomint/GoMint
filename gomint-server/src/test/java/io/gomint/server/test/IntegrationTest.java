/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test;

import io.gomint.server.GoMintServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {

    protected GoMintServer server;

    @BeforeAll
    public void setupGomint() throws IOException {
        this.server = new GoMintServer();
    }
    
    @AfterAll
    public void tearDownGomint() {
        this.server.shutdown();
    }

}
