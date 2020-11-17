package mongo;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;

public class Main {

    public static void main(String[] args){

        //Connecting a Mongo client
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //Creating a Mongo database
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        //Creating a collection
        MongoCollection mongoCollection = mongoDatabase.getCollection("user");

        //save(mongoCollection);
        //findAll(mongoCollection);
        //findByPosition(mongoCollection);
        //findByPositionAndSalary(mongoCollection);
        //updateOne(mongoCollection);
        //updateMany(mongoCollection);
        //replaceOne(mongoCollection);
        //deleteOne(mongoCollection);
        //deleteMultiple(mongoCollection);
        //sort(mongoCollection);
        //limit(mongoCollection);
    }

    private static void limit(MongoCollection mongoCollection) {
        mongoCollection.find().limit(1).skip(1).forEach(new Block<Document>() {
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    private static void sort(MongoCollection mongoCollection) {
        //value: 1 -> ASC, -1 -> DESC
        mongoCollection.find().sort(eq("name", 1)).forEach(new Block<Document>() {
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    private static void deleteMultiple(MongoCollection mongoCollection) {
        mongoCollection.deleteMany(eq("position", "Junior"));
    }

    private static void deleteOne(MongoCollection mongoCollection) {
        mongoCollection.deleteOne(eq("name", "VV"));
    }

    private static void replaceOne(MongoCollection mongoCollection) {
        mongoCollection.replaceOne(eq("name", "Olaf"), new Document().append("name", "VV").append("surname", "KK"));
    }

    private static void updateMany(MongoCollection mongoCollection) {
        mongoCollection.updateMany(
          eq("position", "Junior"),
                Updates.combine(Updates.set("salary", 3500))
        );
    }

    private static void updateOne(MongoCollection mongoCollection) {
        mongoCollection.updateOne(eq("name", "Vlad"), Updates.combine(Updates.set("salary", 3800), Updates.set("age", 24)));
    }

    private static void findByPositionAndSalary(MongoCollection mongoCollection) {
        mongoCollection.find(and(gte("salary", 3400), lte("salary", 3700), eq("position", "Junior")))
                .forEach(new Block<Document>() {
                    public void apply(Document document) {
                        System.out.println(document.toJson());
                    }
                });
    }

    private static void findByPosition(MongoCollection mongoCollection) {
        mongoCollection.find(eq("position", "Junior")).forEach(new Block<Document>() {
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    private static void findAll(MongoCollection mongoCollection) {
        mongoCollection.find().forEach(new Block<Document>() {
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    private static void save(MongoCollection mongoCollection) {
        Document user = new Document("name", "Vlad")
                .append("surname", "Kop")
                .append("age", 23)
                .append("position", "Junior")
                .append("salary", 3500);

        Document user1 = new Document("name", "Volod")
                .append("surname", "Swiet")
                .append("age", 24)
                .append("position", "Junior")
                .append("salary", 3300);

        Document user2 = new Document("name", "Olaf")
                .append("surname", "Olawer")
                .append("age", 26)
                .append("position", "Middle")
                .append("salary", 5500);

        Document user3 = new Document("name", "Jan")
                .append("surname", "Kowal")
                .append("age", 29)
                .append("position", "Senior")
                .append("salary", 8000);
        //Adding one document
        //mongoCollection.insertOne(user);
        //Adding more document
        mongoCollection.insertMany(Arrays.asList(user, user1, user2, user3));
    }
}
