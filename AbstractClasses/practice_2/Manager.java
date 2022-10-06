public class Manager implements Employee {

    double monthSalary;
    Company company;
    int moneyForCompany = rnd(min, max);
    private final static double BONUS = 0.05;
    private final static int min = 115_000;
    private final static int max = 140_000;

    public Manager(double monthSalary){
        this.monthSalary = monthSalary;
    }

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    @Override
    public double getMonthSalary() {
        return monthSalary + (moneyForCompany * BONUS);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "monthSalary=" + getMonthSalary() +
                '}';
    }
}
