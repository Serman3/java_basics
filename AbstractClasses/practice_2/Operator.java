public class Operator implements Employee {

    double monthSalary;
    Company company;
    public Operator(double monthSalary) {
        this.monthSalary = monthSalary;
    }

    @Override
    public double getMonthSalary() {
        return monthSalary;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "monthSalary=" + getMonthSalary() +
                '}';
    }
}
