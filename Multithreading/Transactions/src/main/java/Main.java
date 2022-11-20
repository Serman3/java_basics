import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank(Collections.synchronizedMap(addAccounts()));
        bank.getSumAllAccounts();

        for(int i = 0; i < 10; i++){
         Thread thread = new Thread(new Runnable() {
             @Override
             public void run() {
                 long generate = randomGenerate(10000, 70000);
                // synchronized (Account.class) {
                     bank.transfer("acc20" + 1, "acc50" + 1, generate);
                // }
             }

         });
         thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bank.getSumAllAccounts();
    }

    public static Map<String, Account> addAccounts(){
        Map<String, Account> acc = new HashMap<>();
        for(int i = 1; i <= 10000; i++){
            String name = "acc" + i;
            acc.put(name, new Account(randomGenerate(200000, 500000), name));
        }
        return acc;
    }
     public static long randomGenerate(long min, long max){
        long result = max - min;
         Random random = new Random();
         long l = random.nextLong(result + 1) + min;
         return l;
     }
}
