package library;

/**
 * Book is used to store information about each book. Its properties are written into a .txt file
 *
 * @author Sergiu Ivanov
 * @version 12-03-2018.
 */
public class Book {

    private int id;
    private String bookname;
    private String author;
    private int year;
    private int copies;

    public Book(){}

    public void setId(int id){
        this.id = id;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public  void setAuthor(String author){
        this.author = author;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setCopies(int copies){
        this.copies =copies;
    }

    public int getId(){
        return id;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getCopies() {
        return copies;
    }


}
