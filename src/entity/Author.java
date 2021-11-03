package entity;

import java.io.Serializable;


public class Author implements Serializable{
    private String firstname;
    private String lastname;
    private int birthYear;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Author{" 
                + "firstname=" + firstname 
                + ", lastname=" + lastname 
                + ", birthYear=" + birthYear 
                + '}';
    }
    
    
}
