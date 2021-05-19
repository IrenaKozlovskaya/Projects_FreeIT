

import java.util.*;

public class Application {
    public Application() {
    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        String command;

        library.addAllObjectsXMLToDB();
        library.getAllBooks();

        do {
            System.out.println("Выберите действие и нажмите \"Enter\" \n" +
                    "1. Показать все книги \n" +
                    "2. Вывести информацию о книге по ID \n" +
                    "3. Добавить книгу \n" +
                    "4. Удалить книгу \n" +
                    "5. Редактировать книгу \n" +
                    "6. Выход");
            command = scanner.nextLine();

            switch (command) {

                case ("1"):
                    System.out.println(library.getAllBooks().toString());
                    break;
                case ("2"):
                    System.out.println("Введите ID книги");
                    try {
                        System.out.println(library.getBook(Long.parseLong(scanner.nextLine())).toString());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ID должно состоять из цифр");
                    }
                case ("3"):
                    System.out.println("Введите название книги, автора, жанр и ISBN.");

                    System.out.println(library.addBook(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine()).toString());
                    break;
                case ("4"):
                    System.out.println("Введите ID книги, которую хотите удалить");
                    try {
                        library.deleteBook(Long.parseLong(scanner.nextLine()));
                        System.out.println("Книга удалена!");
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ID должно состоять из цифр");
                    }
                case ("5"):
                    System.out.println("Введите Id книги которую хотите изменить, новое название, автора и жанр .");

                    try {
                        System.out.println(library.editBook(Integer.parseInt(scanner.nextLine()), scanner.nextLine(), scanner.nextLine(), scanner.nextLine()).toString());
                    } catch (NumberFormatException e) {
                        System.out.println("ID должно состоять из цифр");
                    }


            }
        }
        while (!command.equals("6"));
    }
}
