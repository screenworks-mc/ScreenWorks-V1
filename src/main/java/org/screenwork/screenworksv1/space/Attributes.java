package org.screenwork.screenworksv1.space;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Attributes {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private File attributesFile;

    private int strength;
    private int stamina;
    private int agility;
    private int dexterity;
    private int intelligence;
    private int charisma;

    public Attributes() {
        System.out.println("Attributes class instance created");
        GlobalEventHandler globalEventHandler = new GlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> loadAttributes(event.getPlayer()));
        globalEventHandler.addListener(PlayerDisconnectEvent.class, event -> saveAttributes(event.getPlayer()));

        attributesFile = new File("src/main/java/org/screenwork/minestomtest/space/attributes.json");
        loadAttributesFromJson();
    }

    private boolean attributesExistInJson() {
        if (attributesFile.exists()) {
            try (FileReader reader = new FileReader(attributesFile)) {
                Attributes savedAttributes = gson.fromJson(reader, Attributes.class);
                return savedAttributes != null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void loadAttributes(Player player) {
        if (attributesExistInJson()) {
            loadAttributesFromJson();
        } else {
            Random random = new Random();
            strength = random.nextInt(8) + 3;
            stamina = random.nextInt(8) + 3;
            agility = random.nextInt(8) + 3;
            dexterity = random.nextInt(8) + 3;
            intelligence = random.nextInt(8) + 3;
            charisma = random.nextInt(8) + 3;

            saveAttributesToJson();

            System.out.println("Attributes for " + player.getUsername() + " loaded");
        }
    }

    private void saveAttributesToJson() {
        try (FileWriter writer = new FileWriter(attributesFile)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAttributesFromJson() {
        if (attributesFile.exists()) {
            try (FileReader reader = new FileReader(attributesFile)) {
                Attributes savedAttributes = gson.fromJson(reader, Attributes.class);
                if (savedAttributes != null) {
                    strength = savedAttributes.strength;
                    stamina = savedAttributes.stamina;
                    agility = savedAttributes.agility;
                    dexterity = savedAttributes.dexterity;
                    intelligence = savedAttributes.intelligence;
                    charisma = savedAttributes.charisma;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Functions for each attribute
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    // ... (same for other attributes)

    public void saveAttributes(Player player) {
        saveAttributesToJson();
    }
}
