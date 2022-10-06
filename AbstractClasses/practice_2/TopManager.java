public class TopManager implements Employee {

    double monthSalary;
    private final static double BONUSTOPMANAGER = 0.150;
    Company company;

    public TopManager(double monthSalary, Company company){
        this.monthSalary = monthSalary;
        this.company = company;
    }

    @Override
    public double getMonthSalary() {
        return company.getIncome() >= 10_000_000 ? monthSalary + (monthSalary * BONUSTOPMANAGER) : monthSalary;
    }

    @Override
    public String toString() {
        return "TopManager{" +
                "monthSalary=" + getMonthSalary() +
                '}';
    }
}
