import junit.framework.TestCase;
import org.junit.*;
import java.util.HashMap;
import java.util.Map;

public class BankTest extends TestCase {

    Bank bank;
    Account account1;
    Account account2;
    Account account3;
    Map<String, Account> acc;


    @Before
    @Override
    public void setUp() throws Exception{
        bank = new Bank();
        account1 = new Account( 1500000, "one");
        account2 = new Account(2000000, "two");
        account3 = new Account(2500000, "three");
        acc = new HashMap<>();
        acc.put("one", account1);
        acc.put("two", account2);
        acc.put("three", account3);
        bank.setAccounts(acc);
    }

    @Test
    public void testAllSumMoneyAccounts() {
        long expected = account1.getMoney() + account2.getMoney() + account3.getMoney();
        long actual = bank.getSumAllAccounts();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSumMoneyAllAccountBlockAccounts() {
        bank.transfer(account1.getAccNumber(), account2.getAccNumber(), 1000000);
        long expectedSumMoneyAccounts;
        if (account2.getStatus()) {
            expectedSumMoneyAccounts = bank.getBalance(account1.getAccNumber()) + bank.getBalance(account2.getAccNumber()) + bank.getBalance(account3.getAccNumber());
        } else {
            expectedSumMoneyAccounts =
                    bank.getBalance(account1.getAccNumber()) + bank.getBalance(account2.getAccNumber()) + bank.getBalance(account3.getAccNumber());
        }
        long actualSumMoneyAccounts = bank.getSumAllAccounts();
        Assert.assertEquals(expectedSumMoneyAccounts, actualSumMoneyAccounts);
    }

    @Test
    public void testSumMoneyWhenCheckingForBlocking() {
        long expectedSumMoneyAccounts;
        try {
            if(bank.isFraud(account1.getAccNumber(), account2.getAccNumber(), 100000) && bank.getBalance(account1.getAccNumber()) > 100000){
                bank.transfer(account1.getAccNumber(), account2.getAccNumber(), 100000);
                expectedSumMoneyAccounts = 6000000;
            }else {
                expectedSumMoneyAccounts = 6000000;
            }
            long actualSumMoneyAfterTransfer = bank.getSumAllAccounts();
            Assert.assertEquals(expectedSumMoneyAccounts, actualSumMoneyAfterTransfer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



