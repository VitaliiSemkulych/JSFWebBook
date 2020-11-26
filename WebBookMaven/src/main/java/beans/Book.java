/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.io.Serializable;


/**
 *
 * @author Admin
 */
//class which contain model for book
public class Book implements Serializable {
    private long id;
    private String bookName;
    private int pageNumber;
    private byte[] content;
    private String isbn;
    private int publishYear;
    private byte[] image;
    private Author author;
    private Genre genre;
    private Publisher publisher;
    private String description;

    public Book() {
    }

    public Book(long id, String bookName, int pageNumber, byte[] content, String isbn, int publishYear, byte[] image, Author author, Genre genre, Publisher publisher,String description) {
        this.id = id;
        this.bookName = bookName;
        this.pageNumber = pageNumber;
        this.content = content;
        this.isbn = isbn;
        this.publishYear = publishYear;
        this.image = image;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

  

    public long getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public byte[] getContent() {
        return content;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public byte[] getImage() {
        return image;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

 
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
   
    
    @Override
    public String toString() {
        return "bookID: " + id +", bookName: " + bookName + ", pageNumber: " + pageNumber + ", isbn: " + isbn + ", publishYear: " + publishYear + ", author: " + author.getName() + ", genre: " + genre.getName() + ", publisher: " + publisher.getPublisherName();
        
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

   

    
}
