package repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import model.Room;
import redis.AbstractRedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.search.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomRepository extends AbstractRedisConnector implements Repository<Room> {
    private Jsonb jsonb = JsonbBuilder.create();
    private RoomMongoRepository mongoRepository = new RoomMongoRepository();

    public RoomRepository() {
        super();
    }

    @Override
    public Room get(Object element) {
        try {
            Room room = (Room) element;
            return Optional.ofNullable(pool.jsonGet(roomPrefix + room.getRoomNumber(), Room.class))
                    .orElseThrow();
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            return mongoRepository.get(element);
        }
    }

    @Override
    public void add(Room... elements) {
        try {
            Arrays.stream(elements).forEach(room ->
                    pool.jsonSet(roomPrefix + room.getRoomNumber(), jsonb.toJson(room)));
            mongoRepository.add(elements);
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            throw new JedisException("Redis connection failed", e);
        }
    }

    @Override
    public void remove(Room... elements) {
        try {
            Arrays.stream(elements).forEach(room ->
                    pool.jsonDel(roomPrefix + room.getRoomNumber()));
            mongoRepository.remove(elements);
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            throw new RuntimeException("Redis connection failed", e);
        }
    }

    @Override
    public void update(Room... elements) {
        try {
            Arrays.stream(elements).forEach(room ->
                    pool.jsonSet(roomPrefix + room.getRoomNumber(), jsonb.toJson(room)));
            mongoRepository.update(elements);
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            throw new JedisException("Redis connection failed", e);
        }
    }

    @Override
    public List<Room> find(Object... elements) {
        try {
            return Optional.of(Arrays.stream(elements).map(this::get)
                    .collect(Collectors.toList())).orElseThrow();
        } catch (JedisException e) {
            return mongoRepository.find(elements);
        }
    }

    @Override
    public List<Room> getAll() {
        try {
            SearchResult searchResult = pool.ftSearch("room-search", new Query());

            return searchResult.getDocuments().stream()
                    .map(document -> pool.jsonGet(document.getId(), Room.class))
                    .collect(Collectors.toList());
        } catch (JedisDataException e) {
            throw new JedisDataException("Redis operation failed", e);
        } catch (JedisException e) {
            return mongoRepository.getAll();
        }
    }

    @Override
    public Long size() {
        return pool.dbSize();
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
        pool.close();
    }
}
