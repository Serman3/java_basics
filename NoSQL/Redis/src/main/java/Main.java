import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;
import java.util.*;

public class Main {

    // И всего на сайт заходило 20 различных пользователей
    private static final int USERS = 20;

    // Также мы добавим задержку между посещениями
    private static final int SLEEP = 1000;

    private static final String KEY = "user";

   // private static SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {

        RedisStorage redisStorage = new RedisStorage();
        Jedis jedis = redisStorage.RedisConnector("redis://127.0.0.1:6379");
        jedis.flushAll();

        for (int i = 1; i <= USERS; i++) {
            /*LocalDateTime now = LocalDateTime.now();
            int a = now.get(ChronoField.MILLI_OF_SECOND);*/
            jedis.zadd(KEY, new Date().getTime(), "пользователь " + i);
            Thread.sleep(1000);
        }

            List<Tuple> allUser = redisStorage.getAllUser(KEY, 0, -1);
            Collections.sort(allUser);
            for(Tuple user: allUser) {
            System.out.println(user.getElement());
            }

            for(;;){
                for(Tuple user: allUser) {
                    System.out.println(user.getElement());
                    if (Math.random() < 0.10) {
                        int i = (int) (allUser.size() * Math.random());
                        String element = allUser.get(i).getElement();
                        System.out.println("Пользователь " + element + " оплатил платную услугу");
                        System.out.println(element);
                    }
                }
                Thread.sleep(SLEEP);
            }
    }
}

