package org.screenwork.minestomtest.pack;

import net.kyori.adventure.key.Key;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;
import team.unnamed.creative.texture.Texture;

import java.io.File;

public class pack {

    public pack() {
        ResourcePack resourcePack = ResourcePack.create();
        resourcePack.packMeta(9, "Description!");
        resourcePack.icon(Writable.file(new File("src/main/java/org/screenwork/minestomtest/pack/assets/my-icon.png")));
        resourcePack.unknownFile("credits.txt", Writable.stringUtf8("Unnamed Team"));
        MinecraftResourcePackWriter.minecraft().writeToZipFile(
                new File("my-resource-pack.zip"),
                resourcePack
        );
    }
}
