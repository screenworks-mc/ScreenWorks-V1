package org.screenwork.screenworksv1.testing.cake;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoClientConnection {

    private static MongoClient mongoClient;

    private MongoClientConnection() {
        String connectionString = "mongodb+srv://drewhummer:I8e15bTifjokKUwr@testing.2la1bur.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        try {
            System.out.println("Connecting to the DB...");
            mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Testing");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to MongoDB!");
        }
    }

    public static MongoClient getInstance() {
        if (mongoClient == null) {
            new MongoClientConnection();
        }
        return mongoClient;
    }
}
