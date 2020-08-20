package io.gomint.server;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;

class GoMintServerHelper {
    static void offerMinecraftLoopbackExempt()  {
        System.err.println(" ");
        System.err.println(" ");
        System.err.println("A loopback exempt must be added for Minecraft in order to join this server.");
        System.err.println("Open up an administrative command line or PowerShell session and run this command:");
        System.err.println("  CheckNetIsolation LoopbackExempt -a -n=\"Microsoft.MinecraftUWP_8wekyb3d8bbwe\"");
        System.err.println(" ");
    }

    static boolean minecraftLoopbackExemptIsNotPermitted() throws IOException, InterruptedException {
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            return false;
        }
        var process = new ProcessBuilder()
            .command("CheckNetIsolation", "LoopbackExempt", "-s", "-n=\"microsoft.minecraftuwp_8wekyb3d8bbwe\"")
            .start();

        String processStdOutput;
        String processErrOutput;
        try (var reader = new InputStreamReader(process.getInputStream())) {
            processStdOutput = CharStreams.toString(reader);
        }
        try (var reader = new InputStreamReader(process.getErrorStream())) {
            processErrOutput = CharStreams.toString(reader);
        }

        process.waitFor();

        return !processStdOutput.contains("microsoft.minecraftuwp_8wekyb3d8bbwe") &&
            !processErrOutput.contains("microsoft.minecraftuwp_8wekyb3d8bbwe") ;
    }
}
