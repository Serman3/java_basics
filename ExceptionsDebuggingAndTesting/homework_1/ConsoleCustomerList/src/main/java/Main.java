import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;


import java.util.Scanner;

public class Main {
    private static Logger logger;
    private static final Marker INFO_HISTORY = MarkerManager.getMarker("INFO_HISTORY");
    private static final Marker ERROR_HISTORY = MarkerManager.getMarker("ERROR_HISTORY");
    private static final String ADD_COMMAND = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static final String COMMAND_EXAMPLES = "\t" + ADD_COMMAND + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static final String COMMAND_ERROR = "Wrong command! Available command examples: \n" +
            COMMAND_EXAMPLES;
    private static final String HELP_TEXT = "Command examples:\n" + COMMAND_EXAMPLES;

    public static void main(String[] args) {
         logger = LogManager.getRootLogger();
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();

        while (true) {
            try {
                String command = scanner.nextLine();
            logger.info(INFO_HISTORY,"Запросы к приложению " + command);
                String[] tokens = command.split("\\s+", 2);

                if (tokens[0].equals("add")) {
                        executor.addCustomer(tokens[1]);
                } else if (tokens[0].equals("list")) {
                    executor.listCustomers();
                } else if (tokens[0].equals("remove")) {
                    executor.removeCustomer(tokens[1]);
                } else if (tokens[0].equals("count")) {
                    System.out.println("There are " + executor.getCount() + " customers");
                } else if (tokens[0].equals("help")) {
                    System.out.println(HELP_TEXT);
                } else {
                    System.out.println(COMMAND_ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(ERROR_HISTORY, e.toString());
            }
        }
    }
}
