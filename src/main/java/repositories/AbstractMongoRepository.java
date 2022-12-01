package repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import mongo.UniqueIdCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import redis.AbstractRedisConnector;

import java.util.List;

public abstract class AbstractMongoRepository extends AbstractRedisConnector {

    protected ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    protected MongoCredential credential = MongoCredential.createCredential("admin", "admin",
            "adminpassword".toCharArray());
    protected CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());
    protected MongoClient mongoClient;
    protected MongoDatabase hotelDB;

    @Override
    public void initDbConnection() {
        super.initDbConnection();

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(new UniqueIdCodecProvider()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();

        this.mongoClient = MongoClients.create(settings);
    }

    public AbstractMongoRepository() {
        this.initDbConnection();
        this.hotelDB = mongoClient.getDatabase("hotel");
    }
}