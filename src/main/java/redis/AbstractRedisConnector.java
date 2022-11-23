package redis;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AbstractRedisConnector implements AutoCloseable {
    protected static JedisPooled pool;
    protected Properties properties = new Properties();

    public void initDbConnection() {
        try {
            properties.load(new FileInputStream(
                    new File("src/main/resources/credentials.properties").getAbsoluteFile()));
            pool = new JedisPooled(new HostAndPort(
                    properties.getProperty("hostname"), Integer.parseInt(properties.getProperty("port"))),
                    DefaultJedisClientConfig.builder().build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
