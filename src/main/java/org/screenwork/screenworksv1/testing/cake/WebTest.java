package org.screenwork.screenworksv1.testing.cake;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.Tunnel;

public class WebTest {

    public static void launchWeb() {
        final NgrokClient ngrokClient = new NgrokClient.Builder().build();
        final Tunnel httpTunnel = ngrokClient.connect();

        System.out.println(httpTunnel.getPublicUrl());
    }

}
