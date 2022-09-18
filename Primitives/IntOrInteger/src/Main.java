public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());
        container.addCount(5672);
        System.out.println(container.getCount());


        for (char ch = 'Ё'; ch <= 'ё'; ch++) {
            if ((ch > 'Ё' && ch < 'А') || (ch > 'я' && ch < 'ё')) {
                continue;
            }
            System.out.println(ch + " (" + ((int) ch) + ")");
        }

        // TODO: ниже напишите код для выполнения задания:
        //  С помощью цикла и преобразования чисел в символы найдите все коды
        //  букв русского алфавита — заглавных и строчных, в том числе буквы Ё.

    }
}
