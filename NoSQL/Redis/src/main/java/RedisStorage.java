import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;
import java.util.List;

public class RedisStorage {

    private Jedis jedis;

    public Jedis RedisConnector(String host) {
        jedis = new Jedis(host);
        try {
            jedis.connect();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return jedis;
    }

    public List<Tuple> getAllUser(String key, int min, int max) {
        return jedis.zrevrangeWithScores(key,min,max);
    }

    public void closeConnection() {
        if (jedis.isConnected()) {
            jedis.disconnect();
        }
    }
}
