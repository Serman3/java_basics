import java.util.Comparator;

public class MyComparator implements Comparator <Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        if (o1.getMonthSalary() == o2.getMonthSalary()){
            return 0;
        }
        if (o1.getMonthSalary() < o2.getMonthSalary()){
            return -1;
        } else {
            return 1;
        }
    }
}
