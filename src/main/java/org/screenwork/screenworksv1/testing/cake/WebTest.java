package org.screenwork.screenworksv1.testing.cake;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Region;
import com.github.alexdlaird.ngrok.protocol.Tunnel;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebTest {

    private static NgrokClient ngrokClient;

    static final JavaNgrokConfig javaNgrokConfig = new JavaNgrokConfig.Builder()
            .withAuthToken("2VPFPZ4CirAS7Y3lgsR2cJPLJXD_XFqve5tmLVQTazq27Dno")
            .withRegion(Region.US)
            .withConfigPath(Paths.get("src/main/java/org/screenwork/screenworksv1/testing/cake/ngrok.yml"))
            .withMaxLogs(10)
            .build();

    public static void stopNgrok() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/IM", "ngrok.exe", "/F");
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void launchWeb() {
        stopNgrok();

        if (ngrokClient == null) {
            ngrokClient = new NgrokClient.Builder()
                    .withJavaNgrokConfig(javaNgrokConfig)
                    .build();
        }

        final CreateTunnel tunnel = new CreateTunnel.Builder()
                .withName("swadmin")
                .withAddr(8080)
                .build();
        if (ngrokClient.getTunnels() != null) {
            ngrokClient.connect(tunnel);
        }
    }
}
