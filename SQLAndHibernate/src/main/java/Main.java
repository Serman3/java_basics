import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String password = "тщлшф5230";

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
        }
    }
}
