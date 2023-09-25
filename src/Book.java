import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

public class Book {

    String title;
    String author;
    String year;
    String edition;
    boolean available;

    public Book(String title, String author, String year, String edition) {
        this.available = true;
        this.title = title;
        this.author = author;
        this.year = year;
        this.edition = edition;


    }

    public static Book createBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Skriv bokens titel:");
        String title = sc.nextLine();
        String author = "";
        System.out.println("Skriv bokens författare, börja med efternamn (t.ex. Tolkien, J.R.R.)");
        do {
            author = sc.nextLine();
        } while (!validName(author));

        String year = "";
        boolean yearBeforeChrist;
        boolean editionBeforeChrist;

        do {
            yearBeforeChrist = false;
            System.out.println("Årtal då boken skrevs/först publicerades:");
            year = sc.nextLine();
            //OBS om man vill ange ett årtal före år 0 skriver man "f" efter årtalet.
            if (year.endsWith("f")){
                year = year.substring(0, year.length() - 1);
                yearBeforeChrist = true;
            }
        } while (!validYear(year, yearBeforeChrist));

        String edition = "";
        do {
            editionBeforeChrist = false;
            System.out.println("Denna utgåvas årtal:");
            edition = sc.nextLine();
            if (edition.endsWith("f")) {
                edition = edition.substring(0, edition.length() - 1);
                editionBeforeChrist = true;
            }
        } while (!validYear(edition, editionBeforeChrist));

        if (yearBeforeChrist) {
            year = year + " f.v.t.";
        }
        if (editionBeforeChrist) {
            edition = edition + " f.v.t.";
        }
        return new Book(title, author, year, edition);
    }

    // funktion validName() som kollar att man skrivit Namn, Namn (utan siffror)
    public static boolean validName(String name) {
        if (!name.contains(",")) {
            System.out.println("Namnet måste bestå av efternamn följt av ett kommatecken, " +
                    "därefter alla eventuella förnamn. Exempel:\n" +
                    "Lagerlöf, Selma\n" +
                    "Rowling, J.K.\n" +
                    "Homeros, \n" +
                    "Försök igen:");
            return false;
        }
        return true;
    }

    // funktion validYear() som kollar att man skrivit max 4 tecken, och som
    // sedan kollar att alla tecken är siffror

    public static boolean validYear(String str, boolean beforeChrist) {

        int currentYear = Year.now().getValue();
        if ((str.length() > 4)) {
            System.out.println("Årtalet kan inte bestå av fler än fyra siffror.");
            return false;
        }
        try {
            int yearInt = Integer.parseInt(str);
            if (yearInt > currentYear && !beforeChrist) {
                System.out.println("Årtalet kan inte vara senare än nuvarande år " +
                        currentYear + ".");
                return false;
            }
            return true;
        } catch(NumberFormatException e){ // om strängen inte består av siffror
            System.out.println("Årtalet måste bestå av siffror.\n" +
                    "(Om du vil ange ett årtal före vår tideräkning kan du lägga " +
                    "till ett f efter årtalet, t.ex. 800f)");
            return false;
        }
    }
    //En metod loan (boolean) som kollar om en bok går att låna eller
    // inte (dvs tillgänglig eller inte) och göra följande:
    //Om boken är tillgänglig, då ska bokens status ändras till otillgänglig och
    // metoden ska returnera true (dvs att lånet lyckades). Annars returnera false
    //
    public static boolean loan(Book book) {
        if (book.available) {
            book.available = false; // ändrar till false när boken lånas ut
            System.out.println("Du har nu lånat boken. Glöm inte att lämna tillbaka den!");
            return true; // loan successful
        }
        else {
            System.out.println("Boken är tyvärr redan utlånad.");
            return false;
        }
    }

    //En metod returnBook som gör motsatsen till loan.
    public static void returnBook(Book book) {
        book.available = true; // ändrar till available när boken lämnas tillbaka
        System.out.println("Boken är nu återlämnad.");
    }

    //En toString metod som returnerar en sträng med bokinformationen.
    public static String toString(Book book) {
        return "Namn: " + book.title +
                ", författare: " + book.author +
                ", år: " + book.year +
                ", utgåva: " + book.edition;

    }

    // en metod som returnerar true om en bok hittas som matchar angivna sökord
    public static boolean foundBook(String search, ArrayList<Book> list) {
        for (Book book : list) {
            String bookStr = Book.toString(book);
            if (bookStr.contains(search)) {
                //System.out.println("Hittad!"); //TEST
                return true;
            }
        }
        //System.out.println("Finns tyvärr inte."); //TEST
        return false;
    }


    //en metod som söker efter en bok i en lista och returnerar boken som hittas
    //metoden bör bara användas efter att foundBook returnerat true
    public static Book searchBook(String search, ArrayList<Book> list) {
        for (Book book : list) {
            String bookStr = Book.toString(book);
            if (bookStr.contains(search)) {
                return book;
            }
        }
        return null;
    }

    /*/ ev en searchBook() metod/funktion (fast den borde väl vara i Library-klassen?) som tar
    parametrarna String search och ArrayList<Book> list.
    Den ska hitta matchning med inte bara första utan alla förekomster i listan.
    Det kanske kan göras genom att den först returnerar index på den första förekomsten,
    sedan söker igen då den startar på det indexet + 1, osv tills den når det sista
    indexet i listan.
    Sedan ska den lista alla träffar, helst rangordnat så att t ex träffar med fler än ett
    ord prioriteras högre. (Eller helt enkelt: en träff som är en längre sträng prioriteras
    högre.)

     */
}
