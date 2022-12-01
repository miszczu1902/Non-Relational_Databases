package repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import model.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.search.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientRepository extends ClientMongoRepository {
    private Jsonb jsonb = JsonbBuilder.create();
    private ClientMongoRepository mongoRepository = new ClientMongoRepository();

    private final String prefix = "client_";

    public ClientRepository() {
        super();
    }

    @Override
    public Client get(Object element) {
        try {
            Client client = (Client) element;
            return Optional.ofNullable(pool.jsonGet(prefix + client.getPersonalID(), Client.class))
                    .orElseThrow();
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            return mongoRepository.get(element);
        }
    }

    @Override
    public void add(Client... elements) {
        try {
            Arrays.stream(elements).forEach(client ->
                    pool.jsonSet(prefix + client.getPersonalID(), jsonb.toJson(client)));
            mongoRepository.add(elements);
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            throw new JedisException("Redis connection failed", e);
        }
    }

    @Override
    public void remove(Client... elements) {
        try {
            Arrays.stream(elements).forEach(client ->
                    pool.jsonDel(prefix + client.getPersonalID()));
            mongoRepository.remove(elements);
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            throw new RuntimeException("Redis connection failed", e);
        }
    }

    @Override
    public void update(Client... elements) {
        try {
            Arrays.stream(elements).forEach(client ->
                    pool.jsonSet(prefix + client.getPersonalID(), jsonb.toJson(client)));
            mongoRepository.update(elements);
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            throw new JedisException("Redis connection failed", e);
        }
    }

    @Override
    public List<Client> find(Object... elements) {
        try {
            return Optional.of(Arrays.stream(elements).map(this::get)
                    .collect(Collectors.toList())).orElseThrow();
        } catch (JedisException e) {
            return super.find(elements);
        }
    }

    @Override
    public List<Client> getAll() {
        try {
            Schema schema = new Schema().addTextField("$.personalID", 1.0);
            IndexDefinition rule = new IndexDefinition(IndexDefinition.Type.JSON)
                    .setPrefixes(prefix);
            pool.ftCreate("client-search", IndexOptions.defaultOptions().setDefinition(rule), schema);

            SearchResult searchResult = pool.ftSearch("client-search",
                    new Query());
            return searchResult.getDocuments().stream()
                    .map(document -> pool.jsonGet(document.getId(), Client.class))
                    .collect(Collectors.toList());
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            return mongoRepository.getAll();
        }
    }

    public void clear() {
        try (Jedis jedis = new Jedis(properties.getProperty("redisHostname"),
                Integer.parseInt(properties.getProperty("redisPort")))) {
            jedis.flushDB();
        } catch (JedisException e) {
            throw new JedisException("Redis operation failed", e);
        }
    }

    @Override
    public void close() throws Exception {
        pool.getPool().close();
        super.close();
    }
}
