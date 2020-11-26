/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import beans.Book;
import dao.BookDAO;
import emums.SearchType;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Admin
 */
// servise where defined query for retriving data from book DAO
public class BookService implements Serializable{
    
    private final BookDAO bookDAO = new BookDAO();
    
    public List<Book> selectByGenreID(String id) {
        String selectByGenreID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n"
                + "Where B.genre_id=" + id + " ORDER BY B.name";
        if (id.equals("all")) {
            return bookDAO.getAll();
        } else {
            return bookDAO.selectBooks(selectByGenreID);
        }
        
    }
    
    public List<Book> selectByLetter(String letter) {
        String selectByLetter = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n"
                + "Where B.name Like '" + letter + "%' ORDER BY B.name";
        return bookDAO.selectBooks(selectByLetter);
    }
    
    public List<Book> selectByFrace(String keyWord, SearchType type) {
        
        String selectByLetter = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n";
        
        if (type == SearchType.AUTHOR) {
            selectByLetter += "Where A.fio Like '%" + keyWord.toLowerCase() + "%' ORDER BY B.name";
        } else {
            selectByLetter += "Where B.name Like '%" + keyWord.toLowerCase() + "%' ORDER BY B.name";
        }
        return bookDAO.selectBooks(selectByLetter);
    }
    
    
     public List<Book> selectReadingBookByUserID(String userId) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id)"
                + " inner join reading_book R on(R.book_id=B.id) \n"
                + "Where R.user_id='" + userId+"'";
        return bookDAO.selectBooks(selectBookByID);
    }
     public List<Book> selectReadedBookByUserID(String userId) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id)"
                + " inner join readed_book R on(R.book_id=B.id) \n"
                + "Where R.user_id='" + userId+"'";
        return bookDAO.selectBooks(selectBookByID);
    }
     public List<Book> selectInterestingBookByUserID(String userId) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id)"
                + " inner join interesting_book R on(R.book_id=B.id) \n"
                + "Where R.user_id='" + userId+"'";
        return bookDAO.selectBooks(selectBookByID);
    }
    
    public Book selectBookByID(String id) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n"
                + "Where B.id=" + id;
        return bookDAO.selectBooks(selectBookByID).get(0);
    }
    
    public List<Book> selectAll() {
        return bookDAO.getAll();
    }
    
}
