
package MyLibraryJPTV20;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import entity.Author;
import entity.Book;
import entity.History;
import entity.Reader;
import interfaces.Keeping;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tools.SaverToFile;




public class App {
    private Scanner scanner = new Scanner(System.in);
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
    private Keeping keeper = new SaverToFile();
   // private Keeping keeper = new SaverToBase();
   // private SaverToBase saverToBase = new SaverToBase();

    public App() {
        books = keeper.loadBooks();
        readers = keeper.loadReaders();
        histories = keeper.loadHistories();
    }
    
    public void run(){
        String repeat = "r";
        do{
            System.out.println("Выберите номер задачи: ");
            System.out.println("0: Выход из программы");
            System.out.println("1: Добавить книгу");
            System.out.println("2: Список книг");
            System.out.println("3: Добавить читателя");
            System.out.println("4: Список читателей");
            System.out.println("5: Выдать книгу");
            System.out.println("6: Список выданных книг");
            System.out.println("7: Возврат книги");
            int task = scanner.nextInt(); scanner.nextLine();
            switch (task) {
                case 0:
                    repeat="q";
                    System.out.println("Пока! :)");
                    break;
                case 1:
                    System.out.println("---- Добавление книги ----");
                    books.add(addBook());
                    keeper.saveBooks(books);
                    System.out.println("-----------------------");
                    break;
                case 2:
                    System.out.println("---- Список книг -----");
                    printListBooks();
                    System.out.println("-----------------------");
                    break;
                case 3:
                    System.out.println("---- Добавление читателя ----");
                    readers.add(addReader());
                    keeper.saveReaders(readers);
                    System.out.println("-----------------------");
                    break;
                case 4:
                    System.out.println("---- Список читателей -----");
                    for (int i = 0; i < readers.size(); i++) {
                        if(readers.get(i) != null){
                            System.out.println(readers.get(i).toString());
                        }
                    }
                    System.out.println("-----------------------");
                    break;
                case 5:
                    System.out.println("---- Выдать книгу -----");
                    History history = addHistory();
                    if(history != null){
                        histories.add(history);
                        keeper.saveHistories(histories);
                        keeper.saveBooks(books);
                    }
                    System.out.println("-----------------------");
                    break;
                case 6:
                    System.out.println("---- Список выданных книг -----");
                    printGivenBooks();
                    System.out.println("-----------------------");
                    break;
                case 7:
                    System.out.println("---- Возврат книги -----");
                    System.out.println("Список выданных книг:");
                    if(!printGivenBooks()){
                        break;
                    }
                    System.out.print("Выберите номер возвращаемой книги: ");
                    int numberHistory = scanner.nextInt(); scanner.nextLine();
                    Calendar c = new GregorianCalendar();
                    if(histories.get(numberHistory - 1).getBook().getCount()
                            < histories.get(numberHistory - 1).getBook().getQuantity()){
                        histories.get(numberHistory - 1).setReturnedDate(c.getTime());
                        histories.get(numberHistory - 1).getBook().setCount(histories.get(numberHistory - 1).getBook().getCount()+1);
                    }else{
                        System.out.println("Все экземпляры книги в библиотеке");
                    }
                    
                    keeper.saveHistories(histories);
                    keeper.saveBooks(books);
                    System.out.printf("Книга \"%s\" возвращена%n", histories.get(numberHistory-1).getBook().getBookName());
                    break;
                default:
                    System.out.println("Введите номер из списка!");;
            }
            
        }while("r".equals(repeat));
    }
    private boolean printGivenBooks(){
        int n = 0;
        for (int i = 0; i < histories.size(); i++) {
            if(histories.get(i) != null 
                    && histories.get(i).getReturnedDate() == null 
                    && histories.get(i).getBook().getCount() < histories.get(i).getBook().getQuantity()
                    ){
                System.out.printf("%d. Книгу %s читает %s %s. Выдана книга: %s%n",
                        i+1,
                        histories.get(i).getBook().getBookName(),
                        histories.get(i).getReader().getFirstname(),
                        histories.get(i).getReader().getLastname(),
                        histories.get(i).getGivenDate().toString()
                );
                n++;
            }
        }
        if(n < 1){
            System.out.println("Нет выданных книг");
            return false;
        }
        return true;
    }
    private Book addBook(){
        Book book = new Book();
        System.out.print("Введите название книги: ");
        book.setBookName(scanner.nextLine());
        System.out.print("Введите год публикации книги: ");
        book.setPublishedYear(scanner.nextInt()); scanner.nextLine();
        System.out.print("Введите количество экземпляров книги: ");
        book.setQuantity(scanner.nextInt()); scanner.nextLine();
        book.setCount(book.getQuantity());
        System.out.println("Автор книги: ");
        System.out.print("Введите количество авторов: ");
        int countAutors = scanner.nextInt(); scanner.nextLine();
        Author[] authors = new Author[countAutors];
        for (int i = 0; i < authors.length; i++) {
            Author author = new Author();
            System.out.print("Введите имя автора "+(i+1)+": ");
            author.setFirstname(scanner.nextLine());
            System.out.print("Введите фамилию автора: ");
            author.setLastname(scanner.nextLine());
            System.out.print("Введите год рождения автора: ");
            author.setBirthYear(scanner.nextInt());scanner.nextLine();
            authors[i] = author;
        }
        book.setAuthor(authors);
        
        return book;
    }
    private Reader addReader(){
        Reader reader = new Reader();
        System.out.print("Введите имя читателя: ");
        reader.setFirstname(scanner.nextLine());
        System.out.print("Введите фамилию читателя: ");
        reader.setLastname(scanner.nextLine());
        System.out.print("Введите телефон читателя: ");
        reader.setPhone(scanner.nextLine());
        return reader;
    }

    private History addHistory() {
        History history = new History();
        /**
         * 1. Вывести пронумерованный список книг библиотеки
         * 2. Попросить пользователя выбрать номер книги
         * 3. Вывести пронумерованный список читателей
         * 4. Попрость пользователя выбрать номер читателя
         * 5. Генерироват текущую дату
         * 6. инициировать (задать состояние) объект history
         */
        System.out.println("Список книг: ");
        if(!printListBooks()){
            return null;
        }
        System.out.print("Введите номер книги: ");
        int numberBook = scanner.nextInt(); scanner.nextLine();
        System.out.println("Список читателей: ");
        for (int i = 0; i < readers.size(); i++) {
            if(readers.get(i) != null){
                System.out.println(i+1+". "+readers.get(i).toString());
            }
        }
        System.out.print("Введите номер читателя: ");
        int numberReader = scanner.nextInt(); scanner.nextLine();
        history.setBook(books.get(numberBook-1));
        if(history.getBook().getCount() > 0){
            history.getBook().setCount(history.getBook().getCount()-1);
        }
        history.setReader(readers.get(numberReader-1));
        Calendar c = new GregorianCalendar();
        history.setGivenDate(c.getTime());
        return history;
    }

    private boolean printListBooks() {
        int booksForReaders = 0;
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i) != null && books.get(i).getCount() > 0){
                System.out.println(i+1+". "+books.get(i).toString());
                booksForReaders++;
            }else{
                StringBuilder sbDateReturn = new StringBuilder();
                List<Long> listGivenBookDateInMilliseconds = new ArrayList<>();
                for (int j = 0; j < histories.size(); j++) {
                    if(histories.get(j).getBook().equals(books.get(i))){
                        listGivenBookDateInMilliseconds.add(histories.get(j).getGivenDate().getTime());
                    } 
                }
                LocalDate dateGiven = convertToLocalDateViaInstant(histories.get(i).)
                System.out.printf("%d. Книги: \"%s\" нет в наличии. Ожидается: %s"
                        ,i+1
                        ,books.get(i).getBookName()
                        ,dateReturn
                );
            }
        }
        if(booksForReaders < 1){
            System.out.println("Нет книг для чтения.");
            return false;
        }
        return true;
    }
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
} 
}