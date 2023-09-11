package org.screenwork.screenworksv1.permissions;

import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Permissions {

    public static void assign(Player player, Permission permission) {
        player.addPermission(permission);
    }

    public static void remove(Player player, Permission permission) {
        player.removePermission(permission);
    }

    public static @NotNull Set<Permission> fetch(Player player) {
        return player.getAllPermissions();
    }

}
