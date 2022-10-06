import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Company company1 = new Company("ASPRO", 20_000_000, new ArrayList<>());

        for(int i = 0; i <= 180; i++){
            company1.hire(new Operator(50_000 + i));
        }

        for(int i = 0; i <= 80; i ++){
            company1.hire(new Manager(100_000 + i));
        }

        for(int i = 0; i <= 10; i++){
            company1.hire(new TopManager(300_000 + i, company1));
        }

        System.out.println(company1);
        System.out.println();
        List<Employee> list = company1.getTopSalaryStaff(15);
        System.out.println("Топ зарплаты " + list);
        System.out.println();
        List<Employee> list2 = company1.getLowestSalaryStaff(30);
        System.out.println("Низкие зарплаты " + list2);
        System.out.println();
        System.out.println(company1.getListEmployee().size());
        for(int i = company1.getListEmployee().size() / 2; i >= 0; i--){
            company1.fire(company1.getListEmployee().get(i));
        }
        List<Employee> list3 = company1.getTopSalaryStaff(15);
        System.out.println("Топ зарплаты " + list3);
        System.out.println();
        List<Employee> list4 = company1.getLowestSalaryStaff(30);
        System.out.println("Низкие зарплаты " + list4);
        System.out.println(company1.getListEmployee().size());






    }
}
