
package entity;

import java.io.Serializable;
import java.util.Arrays;


public class Book implements Serializable{
    private String bookName;
    private int publishedYear;
    private Author[] author;
    private int quantity;
    private int count;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Author[] getAuthor() {
        return author;
    }

    public void setAuthor(Author[] author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" 
                + "bookName=" + bookName 
                + ", publishedYear=" + publishedYear 
                + ", author=" + Arrays.toString(author) 
                + ", quantity=" + quantity 
                + ", count=" + count 
                + '}';
    }

    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
}