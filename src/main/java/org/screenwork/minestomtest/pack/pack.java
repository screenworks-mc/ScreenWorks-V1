package org.screenwork.minestomtest.pack;

import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import org.screenwork.minestomtest.Main;
import team.unnamed.creative.BuiltResourcePack;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;
import team.unnamed.creative.server.ResourcePackServer;
import team.unnamed.creative.texture.Texture;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class pack {

    private ResourcePackServer packServer;

    public pack() {
        try {
            ResourcePack resourcePack = ResourcePack.create();
            resourcePack.packMeta(9, "Description!");
            resourcePack.icon(Writable.file(new File("src/main/java/org/screenwork/minestomtest/pack/assets/my-icon.png")));
            resourcePack.unknownFile("credits.txt", Writable.stringUtf8("Unnamed Team"));

            File resourcePackFile = new File("src/main/java/org/screenwork/minestomtest/pack/resource-pack.zip");
            MinecraftResourcePackWriter.minecraft().writeToZipFile(resourcePackFile, resourcePack);

            byte[] packData = Files.readAllBytes(resourcePackFile.toPath());
            BuiltResourcePack pack = BuiltResourcePack.of(packData, "ScreenWorks");

            packServer = ResourcePackServer.builder()
                    .address("0.0.0.0", 7270)
                    .handler((request, exchange) -> {
                        byte[] data = pack.bytes();
                        exchange.getResponseHeaders().set("Content-Type", "application/zip");
                        exchange.sendResponseHeaders(200, data.length);
                        try (OutputStream responseStream = exchange.getResponseBody()) {
                            responseStream.write(data);
                        }
                    })
                    .build();

            packServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
