package org.screenwork.screenworksv1.permissions;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.screenwork.screenworksv1.testing.cake.MongoClientConnection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Permissions {

    private static final MongoClient mongoClient = MongoClientConnection.getInstance();
    private static final MongoDatabase database = mongoClient.getDatabase("Permissions");
    private static final MongoCollection<Document> permissionsCollection = database.getCollection("playerPermissions");

    public static void assign(UUID playerId, String permission) {
        Document playerDocument = permissionsCollection.find(Filters.eq("playerId", playerId.toString())).first();
        if (playerDocument == null) {
            playerDocument = new Document("playerId", playerId.toString())
                    .append("permissions", new HashSet<>());
        }

        Set<String> permissions = playerDocument.get("permissions", Set.class);
        permissions.add(permission);

        permissionsCollection.updateOne(
                Filters.eq("playerId", playerId.toString()),
                Updates.set("permissions", permissions),
                new UpdateOptions().upsert(true)
        );
    }

    public static void remove(UUID playerId, String permission) {
        Document playerDocument = permissionsCollection.find(Filters.eq("playerId", playerId.toString())).first();
        if (playerDocument != null) {
            Set<String> permissions = playerDocument.get("permissions", Set.class);
            permissions.remove(permission);

            permissionsCollection.updateOne(
                    Filters.eq("playerId", playerId.toString()),
                    Updates.set("permissions", permissions)
            );
        }
    }

    public static Set<String> fetch(UUID playerId) {
        Document playerDocument = permissionsCollection.find(Filters.eq("playerId", playerId.toString())).first();
        if (playerDocument != null) {
            return playerDocument.get("permissions", Set.class);
        }
        return new HashSet<>();
    }
}
