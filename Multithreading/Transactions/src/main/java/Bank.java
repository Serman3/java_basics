import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    //Lock lock = new ReentrantLock();
    private Map<String, Account> accounts;
    private final Random random = new Random();
    private final static long limit = 50000;

    public Bank(){};
    public Bank(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);
        Account firstLock, secondLock;
        if (fromAccount.compareTo(toAccount) > 0) {
            firstLock = fromAccount;
            secondLock = toAccount;
        }
        else {
            firstLock = toAccount;
            secondLock = fromAccount;
        }
        synchronized (firstLock) {
            synchronized (secondLock) {
                if (amount > limit) {
                    if (fromAccount.getMoney() > amount) {
                        toAccount.setMoney(toAccount.getMoney() + amount);
                        fromAccount.setMoney(fromAccount.getMoney() - amount);
                        try {
                            boolean trueOrFalse = isFraud(fromAccountNum, toAccountNum, amount);
                            if (trueOrFalse) {
                                fromAccount.blockAccount();
                                toAccount.blockAccount();
                                System.out.println("Здесь была блокировка счетов" + "\n");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("С этого аккаунта невозможно исполнить перевод, т.к. недостачно средств" + "\n");
                    }
                }
                if (fromAccount.getMoney() < amount) {
                    System.out.println("С этого аккаунта невозможно исполнить перевод, т.к. недостачно средств" + "\n");
                } else {
                    System.out.println("С аккаунта " + fromAccount.getAccNumber() + " " + fromAccount.getMoney() +
                            " перевод на " + toAccount.getAccNumber() + " " + toAccount.getMoney());
                    toAccount.setMoney(toAccount.getMoney() + amount);
                    fromAccount.setMoney(fromAccount.getMoney() - amount);
                    System.out.println("После перевода " + fromAccount.getAccNumber() + " " + fromAccount.getMoney() +
                            ", сумма перевода " + amount + " на " + toAccount.getAccNumber() + " " + toAccount.getMoney() + "\n");
                }
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public synchronized long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public synchronized long getSumAllAccounts() {
        AtomicLong atomicLong = new AtomicLong();
        for (Map.Entry<String, Account> entry : accounts.entrySet()){
            atomicLong.addAndGet(entry.getValue().getMoney());
        }
        System.out.println("Сумма на всех счетах " + atomicLong.get());
        return atomicLong.get();
    }
}
