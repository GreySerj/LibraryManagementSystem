package library;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.time.LocalDate.parse;
/**
 * Library is used to get, save and work with the data from the given txt files.
 * The txt files must contain comma-separated values.
 *
 * @author Sergiu Ivanov
 * @version 12-03-2018.
 */
public class Library {
    public String books;
    public String members;
    public String bookloans;
    public LocalDate today = LocalDate.now();

    public static final float fine = 0.1f;
    public static final int overdueDays = 30;
    public static final int maxNumberOfBooks = 5;

    private ArrayList<Book> bookObj = new ArrayList<>();
    private ArrayList<Borrower> borrowerObj = new ArrayList<>();
    private ArrayList<Bookloan> bookLoanObj = new ArrayList<>();
    public ArrayList<Book> foundBooks = new ArrayList<>();//temporary arraylist for storing books that are found
    public ArrayList<Borrower> foundMembers = new ArrayList<>();//temporary arraylist for storing members that are found

    public Library(String books, String members, String bookloans){
        this.books = books;
        this.members = members;
        this.bookloans = bookloans;
    }

    /**
     * This method is used to get the data from a txt file
     * and store it into array list.
     *
     * @exception FileNotFoundException If file cannot be found.
     * @exception IOException   Reading a local file that was
     * no longer available, etc.
     */

    public void showAllBooks(){
        try{
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(books));
            String line = bufferedReader.readLine();
            System.out.println("*** All Books ***");
            while(line != null){
                System.out.println(line);
                String[] parts = line.split(",");
                Book x = new Book();
                x.setId(Integer.parseInt(parts[0]));
                x.setBookname(parts[1]);
                x.setAuthor(parts[2]);
                x.setYear(Integer.parseInt(parts[3]));
                x.setCopies(Integer.parseInt(parts[4]));
                this.bookObj.add(x);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();//Always close it.
        } catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    /**
     * This method is used to get the data from a txt file
     * and store it into array list.
     *
     * @exception FileNotFoundException If file cannot be found.
     * @exception IOException   Reading a local file that was
     * no longer available, etc.
     */

    public void showAllMembers(){
        try{
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(members));
            String line = bufferedReader.readLine();
            System.out.println("*** All Members ***");
            while(line != null){
                System.out.println(line);
                String[] parts = line.split(",");
                Borrower y = new Borrower();
                y.setId(Integer.parseInt(parts[0]));
                y.setFirstName(parts[1]);
                y.setLastName(parts[2]);
                LocalDate date  = parse(parts[3]);
                y.setJoinDate(date);
                borrowerObj.add(y);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();//Always close it.
        } catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    /**
     * This method is used to get the data from a txt file
     * and store it into array list.
     *
     * @exception FileNotFoundException If file cannot be found.
     * @exception IOException   Reading a local file that was
     * no longer available, etc.
     */

    public void showAllBookLoans(){
        try{
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(bookloans));
            String line = bufferedReader.readLine();
            System.out.println("*** All Bookloans ***");
            while(line != null){
                System.out.println(line);
                String[] parts = line.split(",");
                Bookloan l = new Bookloan();
                l.setLoanId(Integer.parseInt(parts[0]));
                l.setBookId(Integer.parseInt(parts[1]));
                l.setMemberId(Integer.parseInt(parts[2]));
                LocalDate date  = parse(parts[3]);
                l.setBorrowDate(date);
                bookLoanObj.add(l);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();//Always close it.
        } catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    /**
     * This method is used to call searchBook method,
     * but with user input
     */
    public boolean searchBook(){
        Scanner input = new Scanner(System.in);
        String title;
        System.out.println("Please enter book's title :\n");
        title = input.nextLine();
        return searchBook(title);
    }
    /**
     * This method is used for searching a book title in the array list bookObj.
     * The books found with partially matched title are temporary stored in the
     * foundBooks array list.
     * If the title is found, the results are displayed and true is returned.
     *
     * @param title is a book title
     * @return true if book has been found, otherwise false.
     */
    public boolean searchBook(String title){
        foundBooks.clear();
        boolean isTrue = true;
        for (Book i : bookObj) {
            if (i.getBookname().toLowerCase().contains(title.toLowerCase()))
                foundBooks.add(i);
        }
        if (foundBooks.size() == 0 ){
            System.out.println("No book(s) found with this name \n");
            isTrue = false;
        }else{
            System.out.println("**************************************************");
            System.out.printf("%d book(s) found \n",foundBooks.size());
            System.out.println("**************************************************");
            for (Book i : foundBooks){
                int count = 0;
                int index = foundBooks.indexOf(i);
                System.out.printf("*** Book %d *** \n",index+1);//so books count starts from 1
                for(Bookloan j : bookLoanObj )
                    if (i.getId() == j.getBookId()){
                        count++;
                    }
                    count = i.getCopies() - count;
                System.out.printf("Id: %d\nTitle: %s\nAuthors: %s\nNumber of copies: %d\nAvailable copies: %d\n\n",
                        i.getId(),
                        i.getBookname(),
                        i.getAuthor(),
                        i.getCopies(),
                        count);
            }
        }
        return isTrue;
    }

    /**
     * This method is used for searching member history such as
     * first, last name and bookloan information. The results are
     * displayed, if the book overdue, a warning message is given.
     *
     * @param member is a member in borrower array list.
     */
    public void showMemberLoanInfo(Borrower member){
        int index = 1;
        System.out.println("****************************************************************");
        System.out.printf("%s %s with ID %d has the following book-loan history:\n", member.getFirstName(),
                member.getLastName(), member.getId());
        for (Bookloan k: bookLoanObj){
            if(member.getId() == k.getMemberId()) {
                System.out.printf("*** Book %d ***\n", index);//so books count starts from 1
                String foundTitle = "";
                for (Book l : bookObj) {
                    if (k.getBookId() == l.getId())
                        foundTitle = (l.getBookname());
                }
                LocalDate dueDate = k.getBorrowDate().plusMonths(1);
                System.out.printf("Book loan id: %d\nTitle: %s\nBorrow date: %s\nDue date: %s\n",
                        k.getLoanId(),
                        foundTitle,
                        k.getBorrowDate(),
                        dueDate);
                if (fineCalculator(k.getBorrowDate()) != 0) {// if the fine is not 0
                    System.out.println("--------------------------------------------------");
                    System.out.printf("Book %d is overdue, please pay %.2f£\n",
                            index,
                            fineCalculator(k.getBorrowDate()));
                    System.out.println("**************************************************\n");
                }
                index++;
            }
        }
        if (index == 1)
            System.out.println("No books borrowed.\n");
    }

    /**
     * This method is used for searching members in the borrowerObj array list.
     * If the desirable member is found, it is temporary stored in the
     * foundMembers array list. The searching results are printed on the screen.
     *
     *@param first is member first name
     *@param last is member last name
     * @return true if the member has been found, otherwise false.
     */

    public boolean searchMemberInfo(String first, String last) {
        foundMembers.clear();
        boolean maxQuant = false;
        for (Borrower i : borrowerObj) {
            if ((i.getFirstName().toLowerCase().equals(first.toLowerCase())) &&
                    (i.getLastName().toLowerCase().equals(last.toLowerCase()))) {
                foundMembers.add(i);
            }
        }
        if (foundMembers.size() == 0)
            System.out.println("No member(s) matched\n");
        else {
            System.out.println("**************************************************");
            System.out.printf("%d member(s) matched \n", foundMembers.size());
            System.out.println("**************************************************");
            int memberNumber = 1;
            System.out.printf("*** Member %d ***\n", memberNumber);
            for (Borrower i : foundMembers) {
                int booksBorrowed = getNumOfBorrowedBooks(i.getId());
                System.out.printf("Id: %d\nName: %s %s\nDate of joining: %s\nNumber of borrowed books: %d\n\n",
                        i.getId(),
                        i.getFirstName(), i.getLastName(),
                        i.getJoinDate(),
                        booksBorrowed);
                if (booksBorrowed >= maxNumberOfBooks) {
                    System.out.println("This member has reached his maximum borrowing quantity.\n");
                    maxQuant = true;
                }
                memberNumber++;
            }
        }
        return maxQuant;
    }
    /**
     * This method is used to call searchMember method but
     * ask the user for input.
     */
    public void searchMember(){
        Scanner input = new Scanner(System.in);
        String first, last;
        System.out.println("Please enter member's first name:\n");
        first = input.nextLine();
        System.out.println("Please enter member's last name:\n");
        last = input.nextLine();
        searchMember(first,last);

    }
    /**
     * This method is used to call searchMemberInfo and showMemberLoanInfo
     * methods to display information about any member ,if the member has
     * been found.
     *
     *@param first is member first name
     *@param last is member last name
     */
    public void searchMember(String first, String last){
        searchMemberInfo(first,last);
        for (Borrower i : foundMembers){
            showMemberLoanInfo(i);
        }
    }
    /**
     * This method is used to calculate the fine that a specific
     * member has to pay if a book is overdue.
     *
     *@param date is the date when member borrowed the book.
     * @return a float value which is the fine that member has
     * to pay. If the book is not overdue, the fine is 0.
     *
     */

    public float fineCalculator(LocalDate date) {// date is the date when the book was borrowed. Show fine amount if the book is overdue.
        Float newFine = 0.f;
        Period one = Period.between(date, today);
        int difference = (one.getMonths() * overdueDays) + one.getDays();
        if (difference > overdueDays) {
            int overDue = difference - overdueDays;
            newFine = fine * overDue;
        }
        return newFine;
    }

    /**
     * This method is used to compute and return available copies of a book when book ID
     * is set as a parameter.
     *
     *@param bookId is the book ID in the system.
     * @return an integer value of copies that are available for borrowing
     */

    public int getAvailableCopies(int bookId){// insert bookId to get number of its copies.
        int count = 0;
        for( Bookloan i: bookLoanObj){
            if(i.getBookId()== bookId)
                count++;
        }
        int numberOfCopies = 0;
        for (Book j : bookObj){
            if (j.getId() == bookId) {
                numberOfCopies = j.getCopies();
            }
        }
        int availableCopies = numberOfCopies - count;
        return  availableCopies;
    }

    /**
     * This method is used to get and return the number of borrowed
     * books of a member.
     *
     *@param memberId is the member ID.
     * @return an integer value , number of books borrowed.
     */
    public int getNumOfBorrowedBooks(int memberId){// insert member id to get number of books borrowed.
        int booksBorrowed = 0;
        for (Bookloan j : bookLoanObj){
            if (j.getMemberId() == memberId)
                booksBorrowed++;
        }
        return  booksBorrowed;
    }
    /**
     * This method is used to check if the member exists in the system and
     * he reached the limit of books that he can borrow.
     *
     *@param firstInt is the book ID.
     *@param secondInt is the member ID.
     *
     * @return true if the member has been found.
     */
    public boolean checkUserInput(int firstInt, int secondInt) {
        //ask user input for book ID and member ID and returns true if found, otherwise - false
        boolean isTrue = false, found = false;
        for (Book book : foundBooks) {
            if ((book.getId() == firstInt) && (getAvailableCopies(book.getId())) > 0) {
                for (Borrower member : foundMembers) {
                    if (secondInt == member.getId()) {
                        found = true;
                        break;
                    }
                }
            }
            if (found == true) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }
    /**
     * This method is used to call borrowBook method  but ask
     * the user for input.
     *
     */
    public void borrowBook(){
        Scanner input = new Scanner(System.in);
        String title, first, last;
        System.out.println("Please enter book title:");
        title = input.nextLine();
        System.out.println("Please enter member's first name:");
        first = input.nextLine();
        System.out.println("Please enter member's last name:");
        last = input.nextLine();
        System.out.println();
        borrowBook(title,first,last);

    }

    /**
     * This method is used to check if the member exists in the system and
     * he reached the limit of books that he can borrow. It allow to borrow
     * the book  member has'nt reached his limit.
     *
     *@param title is the book title.
     *@param first is the member first name.
     *@param last is the member last name.
     *
     */

    public void borrowBook(String title, String first, String last) {
        Scanner input = new Scanner(System.in);
        char inputChar;
        boolean isTrue = true, checkResult = false;
        searchBook(title);
        if (searchMemberInfo(first, last) == true) isTrue = false;
        if (foundBooks.size() == 0 || foundMembers.size() == 0) isTrue = false;
        while (isTrue) {
            System.out.println("To exit type [Q]\nTo borrow a book type [B]\n");
            inputChar = input.next().charAt(0);
            switch (inputChar) {
                case 'q': case 'Q':
                    isTrue = false;
                    break;
                case 'b': case 'B':
                    borrowBookPreCheck();
            }
        }
    }

    /**
     * This method is used to check if the member exists in the system and
     * if he reached the limit of books that he can borrow.
     *
     */
    public void borrowBookPreCheck() {
        Scanner input = new Scanner(System.in);
        int firstInt, secondInt;
        try{
            System.out.println("Please type book ID that you want to borrow from the list above.\n");
            firstInt = input.nextInt();
            System.out.println("Please type wanted member ID from the list above:\n");
            secondInt = input.nextInt();
            if (checkUserInput(firstInt, secondInt) == false) {// if book, member doesn't exist or no copies available
                System.out.println("Error.You see this error because:\nMember or book ID cannot be found," +
                        " or\nThere are no more available copies of this book.\n");
            } else {
                if (getNumOfBorrowedBooks(secondInt) >= maxNumberOfBooks)
                    System.out.println("This member has reached his maximum borrowing quantity :(");
                else {
                    Bookloan newBookLoan = new Bookloan();
                    Bookloan lastObject = bookLoanObj.get(bookLoanObj.size() - 1);
                    newBookLoan.setLoanId(lastObject.getLoanId() + 1);
                    newBookLoan.setBookId(firstInt);
                    newBookLoan.setMemberId(secondInt);
                    newBookLoan.setBorrowDate(today);
                    bookLoanObj.add(newBookLoan);
                    System.out.println("Success, the book has been borrowed. Thank you!\n");
                    System.out.println("**************************************************");
                }
            }
        }catch (InputMismatchException ex){
            System.out.println("Please type in a valid book/member ID\n");
        }
    }

    /**
     * This method is used to ask the user if the fine will be paid
     * id yes, the book is returned, if no, it isn't.
     *
     *@param i is the object index in bookLoanObj.
     *
     */

    public void fineManagement(int i){
        Scanner input = new Scanner(System.in);
        char inChar;
        for(Borrower member: borrowerObj){
            if ( member.getId() == bookLoanObj.get(i).getMemberId()){
                System.out.println("---------------------------------------------------");
                System.out.printf("%s %s with ID: %d try to return a book that overdue.\n" +
                                "The fine is: %.2f£\n",
                        member.getFirstName(), member.getLastName(), member.getId(), fine);

                System.out.println("Would you like to pay the fine now?[y/n]");
                inChar = input.next().charAt(0);
                switch (inChar){
                    case 'y': case'Y':
                        bookLoanObj.remove(i);
                        System.out.println("***************************************************");
                        System.out.println("The book has been successfully returned. Thank you!");
                        System.out.println("***************************************************");
                        break;
                    case 'n': case'N':
                        System.out.println("**************************************************");
                        System.out.println("The book has'nt been returned. Have a nice day!");
                        System.out.println("**************************************************");
                        break;
                    default:
                        System.out.println("Wrong input. Try again");
                }
            break;
            }
        }
    }
    /**
     * This method is used to call returnBook method but ask the
     * user for input.
     *
     *
     */
    public void returnBook(){
        Scanner input = new Scanner(System.in);
        int loanId;
        try{
            System.out.println("Please enter loan ID:");
            loanId = input.nextInt();
            returnBook(loanId);
        }catch (InputMismatchException ex){
            System.out.println("Wrong input. Please enter a valid book loan ID.");
        }

    }
    /**
     * This method is used to return a book.
     *
     *@param bookLoanId is the book loan ID.
     *
     */
    public  void returnBook(int bookLoanId){
        Scanner input = new Scanner(System.in);
        char inChar;
        boolean isTrue = false;
        for (int i = 0; i< bookLoanObj.size(); i++){
            if (bookLoanObj.get(i).getLoanId() == bookLoanId){
                isTrue = true;
                System.out.println("--------------------------------------------------");
                System.out.printf("Book ID: %d\nDate borrowed: %s\n", bookLoanObj.get(i).getBookId(), bookLoanObj.get(i).getBorrowDate());
                System.out.println("Do you wish to return this book? [y/n]");
                inChar = input.next().charAt(0);
                switch (inChar){
                    case 'Y': case 'y':
                        float fine = fineCalculator(bookLoanObj.get(i).getBorrowDate());
                        if (fine == 0){
                            bookLoanObj.remove(i);
                            System.out.println("***************************************************");
                            System.out.println("The book has been successfully returned. Thank you!");
                            System.out.println("***************************************************");
                        }else
                            fineManagement(i);
                        break;
                    case 'N': case 'n':
                        break;
                    default:
                        System.out.println("Wrong input. Try again");
                }
            }
        }
        if (isTrue == false)
            System.out.println("This book loan ID cannot be found in the system(:\n");
    }

    /**
     * This method is used to check if the book already exists in the system
     * and ask the user to add it as new or not.
     *
     *@param title is the book title.
     *@return true if the book has been added as new.
     */
    public boolean addNewBookPreCheck(String title){
        Scanner input = new Scanner(System.in);
        boolean isTrue = true;
        char inChar;
        for (Book i : bookObj){
            if ( i.getBookname().toLowerCase().equals(title.toLowerCase())){
                System.out.printf("%s already exist, would you like to add it as new [y/n] ? ",i.getBookname());
                inChar = input.next().charAt(0);
                switch (inChar){
                    case 'Y': case 'y':
                        break;
                    case 'N': case 'n':
                        System.out.println("**************************************************");
                        System.out.println("The book was not added. Thank you!");
                        System.out.println("**************************************************");
                        isTrue = false;
                        break;
                }
            }
        }
        return isTrue;
    }
    /**
     * This method is used to add new book in the library.
     * It calls addNewBook method and ask the user for input.
     *
     * @exception InputMismatchException if the user typed wrong characters.
     */
    public void addNewBook(){
        Scanner input = new Scanner(System.in);
        String title, name;
        String [] names;
        int year,copies;
        try{
            System.out.println("Please enter book title:\n");
            title = input.nextLine();
            System.out.println("Please enter all authors name in one line and separate each one by colon:\n");
            name = input.nextLine();
            names = new String[]{name};
            System.out.println("Please enter book year:\n");
            year = input.nextInt();
            System.out.println("Please enter number of copies:\n");
            copies = input.nextInt();
            addNewBook(title, names, year, copies);
        }catch (InputMismatchException ex){
            System.out.println("**************************************************");
            System.out.println("Wrong input. Please try again ");
            System.out.println("**************************************************");
        }
    }

    /**
     * This method is used to add new book in the library.
     *
     *@param title is the book title.
     *@param authors is the array containing authors name.
     *@param year is the book year.
     *@param copies is the book copies.
     */
    public void addNewBook(String title, String [] authors, int year, int copies){
        boolean isTrue = true;
        String allAuthors = "";
        while (isTrue){
            title = title.replaceAll(",","");
            if(addNewBookPreCheck(title) == false){
                break;
            }
            for ( int i = 0; i<authors.length; i++){
                if (i < authors.length -1 )
                    allAuthors += authors[i] + ":";
                else
                    allAuthors += authors[i];
            }
            allAuthors = allAuthors.replaceAll(","," ");
            int lastIndex = bookObj.size() -1;// last object in the book list
            Book newBook = new Book();
            newBook.setId(bookObj.get(lastIndex).getId()+1);// adding new id for the new book
            newBook.setBookname(title);
            newBook.setAuthor(allAuthors);
            newBook.setYear(year);
            newBook.setCopies(copies);
            bookObj.add(newBook);
            System.out.println("**************************************************");
            System.out.println("The book was added successfully. Thank you!");
            System.out.println("**************************************************");
            break;
        }
    }
    /**
     * This method is used to add new member to the library.
     * It calls addNewMember method and ask the user for input.
     *
     */
    public void addNewMember(){
        Scanner input = new Scanner(System.in);
        String first, last;
        System.out.println("Please enter member's first name:\n");
        first = input.nextLine();
        System.out.println("Please enter member's last name:\n");
        last = input.nextLine();
        addNewMember(first, last, today);
    }
    /**
     * This method is used to add new member to the library.
     *
     *@param first is member first name.
     *@param last is member last name.
     *@param now is the local date, today.
     */
    public void addNewMember(String first, String last, LocalDate now){
        first = first.replaceAll(",","");
        last = last.replaceAll(",", "");
        int lastIndex = borrowerObj.size()-1;
        Borrower newMem = new Borrower();
        newMem.setId(borrowerObj.get(lastIndex).getId()+1);//set and add +1 to the new member ID
        newMem.setFirstName(first);
        newMem.setLastName(last);
        newMem.setJoinDate(now);
        borrowerObj.add(newMem);
        System.out.println("**************************************************");
        System.out.printf("%s %s was added successfully.\n",first ,last);
        System.out.println("**************************************************");

    }
    /**
     * This method is used to check if the new quantity is  smaller than available copies.
     *
     *@param bookID is book ID.
     *@param quantity is the desirable quantity that has to be increased or decreased.
     *@return an integer number which means the number of books which stock were changed
     */
    public int changeQuantityPreCheck(int bookID, int quantity){
        int newCopies, count = 0;
        for (Book book : foundBooks) {
            if (book.getId() == bookID)  {
                count++;
                int available = getAvailableCopies(book.getId());
                newCopies = book.getCopies() + (quantity);
                if(available > newCopies){// if the new quantity is  smaller than available copies.
                    System.out.println("***************************************************************");
                    System.out.println("You cannot have in stock less books than already borrowed books");
                    System.out.println("***************************************************************");
                }else{
                    System.out.println("**********************************************");
                    System.out.println("The stock was successfully changed. Thank you!");
                    System.out.println("**********************************************");
                    book.setCopies(newCopies);
                }
            }
        }
        return count;
    }
    /**
     * This method is used to change quantity of an existing book.
     *It call changeQuantity method and ask the user input.
     *
     * @exception InputMismatchException if the user typed wrong characters.
     */
    public void changeQuantity(){
        Scanner input = new Scanner(System.in);
        String title;
        int quantity;
        try{
            System.out.println("Please enter book title:\n");
            title = input.nextLine();
            System.out.println("Please enter quantity you want to increase or decrease:\n");
            quantity = input.nextInt();
            changeQuantity(title, quantity);
        }catch (InputMismatchException ex){
            System.out.println("**************************************************");
            System.out.println("Wrong input. Please try again ");
            System.out.println("**************************************************");
        }
    }
    /**
     * This method is used to change quantity of an existing book.
     *
     *@param title is book title.
     *@param quantity is the desirable quantity that has to be increased or decreased.
     *
     */
    public void changeQuantity(String title, int quantity){
        Scanner input = new Scanner(System.in);
        boolean isTrue = true;
        char inChar;
        int bookID;
        while(isTrue){
            try{
                searchBook(title);
                System.out.println("Please select book ID that you want to change the stock:\n");
                bookID = input.nextInt();
                if (changeQuantityPreCheck(bookID,quantity) == 0){
                    System.out.println("Please enter valid book ID from the list.\nTry again [y/n]?");
                    inChar = input.next().charAt(0);
                    switch (inChar){
                        case 'Y': case 'y':
                            break;
                        case 'N': case 'n':
                            isTrue = false;
                            break;
                    }
                }else
                    break;
            }catch (InputMismatchException ex) {
                System.out.println("**************************************************");
                System.out.println("Please enter a valid book ID \n");
                System.out.println("**************************************************");
                break;
            }
        }
    }
    /**
     * This method is used to save all changes made in the bookObj arraylist.
     *
     *@param books is the string containing the path to the books.txt.
     *
     *@exception IOException Writing a local file that was
     * no longer available and other errors.
     */
    public void saveBooks(String books){
        try{
            FileWriter newBooks = new FileWriter(books);
            for (Book book : bookObj) {
                newBooks.write(String.valueOf(book.getId()));
                newBooks.write(",");
                newBooks.write(book.getBookname());
                newBooks.write(",");
                newBooks.write(book.getAuthor());
                newBooks.write(",");
                newBooks.write(String.valueOf(book.getYear()));
                newBooks.write(",");
                newBooks.write(String.valueOf(book.getCopies()));
                newBooks.write("\n");
            }
            newBooks.close();
        }catch (IOException ex) {
            System.out.println("The file cannot be found.");
        }
    }
    /**
     * This method is used to save all changes made in the borrowerObj array list.
     *
     *@param members is the string containing the path to the members.txt.
     *
     *@exception IOException Writing a local file that was
     * no longer available and other errors.
     */
    public  void saveMembers(String members){
        try{
            FileWriter newMembers = new FileWriter(members);
            for(Borrower member: borrowerObj){
                newMembers.write(String.valueOf(member.getId()));
                newMembers.write(",");
                newMembers.write(member.getFirstName());
                newMembers.write(",");
                newMembers.write(member.getLastName());
                newMembers.write(",");
                newMembers.write(String.valueOf(member.getJoinDate()));
                newMembers.write("\n");
            }
            newMembers.close();
        }catch (IOException ex) {
            System.out.println("The file cannot be found.");
        }
    }

    /**
     * This method is used to save all changes made in the bookLoanObj array list.
     *
     *@param bookloans is the string containing the path to the bookloan.txt.
     *
     *@exception IOException Writing a local file that was
     * no longer available and other errors.
     */
    public void saveBookLoans(String bookloans){
        try{
            FileWriter newBookLoans = new FileWriter(bookloans);
            for (Bookloan loan: bookLoanObj){
                newBookLoans.write(String .valueOf(loan.getLoanId()));
                newBookLoans.write(",");
                newBookLoans.write(String .valueOf(loan.getBookId()));
                newBookLoans.write(",");
                newBookLoans.write(String .valueOf(loan.getMemberId()));
                newBookLoans.write(",");
                newBookLoans.write(String .valueOf(loan.getBorrowDate()));
                newBookLoans.write("\n");
            }
            newBookLoans.close();
        }catch (IOException ex) {
            System.out.println("The file cannot be found.");
        }
    }

    /**
     * This method is used to save all changes made in the bookObj, borrowerObj
     * and bookLoanObj array lists.
     *
     *@param books is the string containing the path to the book.txt.
     *@param members is the string containing the path to the members.txt.
     *@param bookloans is the string containing the path to the bookloan.txt.
     *
     */
    public void saveChanges(String books, String members, String bookloans){

        saveBooks(books);
        saveMembers(members);
        saveBookLoans(bookloans);
    }

}
