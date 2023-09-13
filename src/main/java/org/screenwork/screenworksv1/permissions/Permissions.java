package org.screenwork.screenworksv1.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Permissions {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type playerPermissionsType = new TypeToken<Map<UUID, Set<String>>>() {}.getType();

    private static final File permissionsFile = new File("permissions.json");

    private static Map<UUID, Set<String>> playerPermissions = new HashMap<>();

    public static void load() {
        if (permissionsFile.exists()) {
            try (Reader reader = new FileReader(permissionsFile)) {
                playerPermissions = gson.fromJson(reader, playerPermissionsType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (Writer writer = new FileWriter(permissionsFile)) {
            gson.toJson(playerPermissions, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void assign(Player player, Permission permission) {
        player.addPermission(permission);
        UUID playerId = player.getUuid();
        playerPermissions.computeIfAbsent(playerId, uuid -> new HashSet<>()).add(permission.getPermissionName());
        save();
    }

    public static void remove(Player player, Permission permission) {
        player.removePermission(permission);
        UUID playerId = player.getUuid();
        Set<String> permissions = playerPermissions.get(playerId);
        if (permissions != null) {
            permissions.remove(permission.getPermissionName());
            save();
        }
    }

    public static Set<String> fetch(Player player) {
        UUID playerId = player.getUuid();
        return playerPermissions.getOrDefault(playerId, Collections.emptySet());
    }
}
