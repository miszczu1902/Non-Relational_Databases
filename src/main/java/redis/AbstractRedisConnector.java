package redis;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractRedisConnector implements AutoCloseable {

    protected static JedisPooled pool;
    protected Properties properties = new Properties();
    protected String clientPrefix = "client_";
    protected String roomPrefix = "room_";

    public void initDbConnection() {
        try {
            properties.load(new FileInputStream(
                    new File("src/main/resources/credentials.properties").getAbsoluteFile()));
            pool = new JedisPooled(new HostAndPort(
                    properties.getProperty("redisHostname"), Integer.parseInt(properties.getProperty("redisPort"))),
                    DefaultJedisClientConfig.builder().build());

            try {
                pool.ftDropIndex("client-search");
                pool.ftDropIndex("room-search");
            } catch (JedisDataException e) {
                throw new JedisDataException("Index doesn't exist");
            } finally {
                Schema clientSchema = new Schema().addTextField("$.personalID", 1.0);
                IndexDefinition clientRule = new IndexDefinition(IndexDefinition.Type.JSON)
                        .setPrefixes(clientPrefix);
                pool.ftCreate("client-search", IndexOptions.defaultOptions().setDefinition(clientRule), clientSchema);

                Schema roomSchema = new Schema().addTextField("$.roomNumber", 1.0);
                IndexDefinition roomRule = new IndexDefinition(IndexDefinition.Type.JSON)
                        .setPrefixes(roomPrefix);
                pool.ftCreate("room-search", IndexOptions.defaultOptions().setDefinition(roomRule), roomSchema);
            }

        } catch (IOException | JedisDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
    }
}