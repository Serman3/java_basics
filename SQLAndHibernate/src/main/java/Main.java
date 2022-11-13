import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction(); //открываем транзакцию

        CriteriaBuilder builder = session.getCriteriaBuilder(); //строит объекты запросов

        CriteriaQuery<Purchase> query = builder.createQuery(Purchase.class); //возвращает запрос, указывает тип данных
        Root<Purchase> root = query.from(Purchase.class); //корневой объект, от которого производится обход дерева
        query.select(root);
        List<Purchase> purchaseList = session.createQuery(query).getResultList();

        for (Purchase p : purchaseList) {
            int studentId = p.getStudent().getId();
            int courseId = p.getCourse().getId();
            String courseName = p.getCourseName();
            String studentName = p.getStudentName();

            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setId(new LinkedPurchaseListKey(studentId, courseId));
            linkedPurchaseList.setStudentName(studentName);
            linkedPurchaseList.setCourseName(courseName);
            linkedPurchaseList.setPrice(p.getPrice());
            linkedPurchaseList.setDate(p.getSubscriptionDate());
            session.saveOrUpdate(linkedPurchaseList);
        }
        transaction.commit();
        session.close();
        sessionFactory.close();
/*
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        for(int i = 1; i <= 20; i++) {
            Course course = session.get(Course.class, i);
            System.out.println(course + "\n");
            System.out.println(course.getTeacher() + "\n");
            System.out.println(course.getStudents() + "\n");
            List<Subscription> subscriptionList = course.getSubscriptions();
            for(Subscription subscription : subscriptionList){
                System.out.println(course.getId());
                System.out.println(subscription + "\n");
            }
            System.out.println(course.getSubscriptions());
            Student student = session.get(Student.class, i);
            List<Course> listCourse = student.getCourses();
            for(Course course1 : listCourse){
                System.out.println(course1.getName());
            }
            List<Subscription> subscriptionList2 = student.getSubscriptions();
            for(Subscription subscription : subscriptionList2){
                System.out.println(student.getId());
                System.out.println(subscription + "\n");
            }
        }
        sessionFactory.close();
*/
        /*String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String password = "root";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select c.name, scp.subscription_date, round(count(c.name)/(max(month(scp.subscription_date)) - min(month(scp.subscription_date))),2) as 'count_month'\n" +
                        "from courses c\n" +
                        "join subscriptions scp on scp.course_id = c.id\n" +
                        "where scp.subscription_date between '2018-01-01 00:00:00' and '2018-12-31 00:00:00'\n" +
                        "group by c.name\n" +
                        "order by scp.subscription_date;"
            );

            while(resultSet.next()){
                String courseName = resultSet.getString("name");
                Double countOnMonth = resultSet.getDouble("count_month");
                System.out.println(courseName + " - " + countOnMonth);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
