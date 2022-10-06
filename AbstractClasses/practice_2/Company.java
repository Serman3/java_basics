import java.util.*;

public class Company{
    private String name;
    private double income;
    List<Employee> listEmployee;

    public Company(){};

    public Company(String name, double income, List<Employee> listEmployee){
        this.name = name;
        this.income = income;
        this.listEmployee = listEmployee;
    }

    public void hire(Employee employee){
        listEmployee.add(employee);
    }

    public void hireAll(Collection<Employee> employes){
        listEmployee.addAll(employes);
    }

    public void fire(Employee employee){
        listEmployee.remove(employee);
    }

    public double getIncome() {
        return income;
    }

    public List<Employee> getTopSalaryStaff(int count){
        Collections.sort(listEmployee, Collections.reverseOrder(new MyComparator()));
        List<Employee> list = new ArrayList<>(count);
        if(listEmployee.size() >= count) {
            for(int i = 0; i < count; i++){
                list.add(listEmployee.get(i));
            }
            return list;
            } else {
                System.out.println("Вы передали отрицательные или превышающие количество сотрудников в компании");
            }
        return new ArrayList<>();
    }

    public List<Employee> getLowestSalaryStaff(int count){
        Collections.sort(listEmployee, new MyComparator());
        List<Employee> list = new ArrayList<>(count);
        if(listEmployee.size() >= count) {
            for(int i = 0; i < count; i++){
                list.add(listEmployee.get(i));
            }
            return list;
        } else {
                System.out.println("Вы передали отрицательные или превышающие количество сотрудников в компании");
            }
        return new ArrayList<>();
    }

    public List<Employee> getListEmployee() {
        return listEmployee;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", listEmployee=" + listEmployee +
                '}';
    }
}
