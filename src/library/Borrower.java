package library;

import java.time.LocalDate;
/**
 * Borrower is used to keep track of what books members have borrowed from the library.
 *
 * @author Sergiu Ivanov
 * @version 12-03-2018.
 */

public class Borrower {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate joinDate;

    public Borrower(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }
}
