import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;


public class Main {
    public static void main(String[] args) {

        // En lista som lagrar alla böcker i biblioteket
        ArrayList<Book> bookList = new ArrayList<>();
        // Några böcker som redan finns inne i biblioteket
        Book book = new Book("Iliaden", "Homeros", "800 f.v.t.", "1978");
        bookList.add(book);
        book = new Book("I, Robot", "Asimov, Isaac", "1967", "2018");
        bookList.add(book);

        Scanner sc = new Scanner(System.in);

        System.out.println("Välkommen till biblioteket!");

        boolean quit = false;

        do {
            System.out.println("\nVad vill du göra? Välj ett nummer i menyn:");
            System.out.println("""
                    1. Lägg till en bok i biblioteket
                    2. Sök efter en bok
                    3. Lista alla böcker
                    4. Lämna tillbaka en bok
                    5. Avsluta programmet""");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Du vill lägga till en bok.");
                    book = Book.createBook();
                    bookList.add(book);
                    System.out.println("Boken har lagts till i biblioteket med följande data:\n" + Book.toString(book));
                    break;
                case "2":
                    System.out.println("Skriv titel, författare eller årtal på den bok du söker:");
                    String search = sc.nextLine();
                    if (Book.foundBook(search, bookList)) {
                        Book currentBook = Book.searchBook(search, bookList);
                        System.out.println("Boken finns i bibliotekets databas:\n" +
                                Book.toString(currentBook) +
                                "\nVill du låna boken? (j/n) ");
                        choice = sc.nextLine();
                        if (choice.equalsIgnoreCase("j")) {
                            Book.loan(currentBook);
                        }
                    }
                    else {
                        System.out.println("Sökningen gav tyvärr ingen träff.");
                    }
                    break;
                case "3":
                    boolean madeChoice = false;
                    do { //while (!madeChoice);
                        int backToMainMenu = bookList.size() + 1; //genererar sista numret i menyn
                        System.out.println("Här är alla böcker i bibliotekets sortiment. \n" +
                                "Om du vill låna en av dem, ange bokens nummer, eller ange " +
                                backToMainMenu + " för att gå tillbaka.");
                        // Skapar en meny med alla böcker i sortimentet:
                        for (int i = 0; i < bookList.size(); i++) {
                            System.out.print(i + 1 + ". ");
                            System.out.println(Book.toString(bookList.get(i)));
                        }
                        System.out.println(backToMainMenu +
                                ". Tillbaka till huvudmenyn.");

                        String borrowBookMenuChoice = sc.nextLine();
                        int borrowChoiceInt = Integer.parseInt(borrowBookMenuChoice);

                        if (borrowChoiceInt == backToMainMenu) {
                            madeChoice = true;
                        }
                        // kollar att man inte skriver en siffra utanför menyn:
                        else if (borrowChoiceInt > bookList.size()) {
                            System.out.println("Ogiltigt val. Skriv en siffra mellan 1 och "
                                    + backToMainMenu + ".");
                        }
                        else {
                            // lånar boken
                            int indexOfBookToBorrow = borrowChoiceInt - 1;
                            Book.loan(bookList.get(indexOfBookToBorrow));
                            madeChoice = true;

                        }
                    } while (!madeChoice);
                    break;

                case "4":
                    ArrayList<Book> unavailableBooks = new ArrayList<>();
                    for (Book b : bookList) {
                        if (!b.available) {
                            unavailableBooks.add(b);
                        }
                    }

                    if (unavailableBooks.isEmpty()) {
                        System.out.println("Du har inte lånat några böcker.");
                        break;
                    } else if (unavailableBooks.size() == 1) {
                        System.out.println("Du har lånat följande bok:");
                        System.out.println(Book.toString(unavailableBooks.get(0)));
                        System.out.print("är du säker på att du vill lämna tillbaka den? (j/n)");
                        String returnConfirm = sc.nextLine();
                        if (returnConfirm.equalsIgnoreCase("j")) {
                            Book.returnBook(unavailableBooks.get(0));
                        }
                        else {
                            break;
                        }
                    } else {
                        madeChoice = false;
                        do {
                            System.out.println("Vilken bok vill du lämna tillbaka? Välj från menyn.");
                            // Skapar en meny med utlånade (unavailable) böcker:
                            for (int i = 0; i < unavailableBooks.size(); i++) {
                                System.out.print(i + 1 + ". ");
                                System.out.println(Book.toString(unavailableBooks.get(i)));
                            }
                            int backToMainMenu = unavailableBooks.size() + 1;
                            System.out.println(backToMainMenu +
                                    ". Tillbaka till huvudmenyn.");
                            String bookReturnMenuChoice = sc.nextLine();
                            int bRMenuChoiceInt = Integer.parseInt(bookReturnMenuChoice);

                            if (bRMenuChoiceInt == backToMainMenu) {
                                madeChoice = true;
                            }
                            // kollar att man inte skriver en siffra som inte finns i menyn:
                            else if (bRMenuChoiceInt > unavailableBooks.size()) {
                                System.out.println("Ogiltigt val. Skriv en siffra mellan 1 och "
                                        + backToMainMenu + ".");
                            }
                            else {
                                // lämnar tillbaka boken
                                int indexOfBookToReturn = bRMenuChoiceInt - 1;
                                Book.returnBook(unavailableBooks.get(indexOfBookToReturn));
                                madeChoice = true;

                            }
                            } while (!madeChoice);
                        break;

                        }
                        case "5":
                            quit = true;
                            System.out.println("Hejdå, välkommen åter!");
                            break;
                        default:
                            System.out.println("Jag uppfattade inte ditt val. Försök igen.\n" +
                                    "(Psst! Välj en siffra mellan 1 och 5.)");

                    }


            } while (!quit) ;

            //2.
            //Refaktorisera din kod (gör om den) genom att skapa en klass Library som
            // innehåller all funktionalitet för ett bibliotek och ändra din main funktion
            // så att den i stället skapar ett Library objekt som du sedan skriver kod för att testa.
            //
        }


    }