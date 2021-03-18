package library;

import java.time.LocalDate;
/**
 * Bookloan is used to keep track of what books have been borrowed from the library.
 *
 * @author Sergiu Ivanov
 * @version 12-03-2018.
 */

public class Bookloan {
    private int loanId;
    private int bookId;
    private int memberId;
    private LocalDate borrowDate;

    public Bookloan(){}


    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }
}
