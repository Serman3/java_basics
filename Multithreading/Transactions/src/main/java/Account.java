public class Account implements Comparable<Account>{

    private long money;
    private final String accNumber;
    private boolean isBlocked;

    public Account(long money, String accNumber) {
        this.accNumber = accNumber;
        this.money = money;
        isBlocked = false;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public synchronized void blockAccount() {
        isBlocked = true;
       // setMoney(0);
    }

    public boolean getStatus() {
        return isBlocked;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}

