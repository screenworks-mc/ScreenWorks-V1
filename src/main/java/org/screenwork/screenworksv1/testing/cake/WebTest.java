package org.screenwork.screenworksv1.testing.cake;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Region;
import com.github.alexdlaird.ngrok.protocol.Tunnel;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebTest {

    private static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

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
        root.setLevel(Level.ERROR);
        stopNgrok();

        if (ngrokClient == null) {
            ngrokClient = new NgrokClient.Builder()
                    .withJavaNgrokConfig(javaNgrokConfig)
                    .build();
        }

        final CreateTunnel tunnel = new CreateTunnel.Builder()
                .withName("swadmin")
                .withAddr(8080)
                .withAuth("swadmin!:swadmin!")
                .build();

        ngrokClient.connect(tunnel);

        Tunnel swadminTunnel = null;
        for (Tunnel t : ngrokClient.getTunnels()) {
            if ("swadmin".equals(t.getName())) {
                swadminTunnel = t;
                break;
            }
        }

        if (swadminTunnel != null) {
            System.out.println("Admin tunnel initialized successfully on public URL: " + swadminTunnel.getPublicUrl());
        } else {
            System.out.println("Admin tunnel not found.");
        }
    }
}
