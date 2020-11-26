/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Author;
import beans.Book;
import beans.Genre;
import beans.Publisher;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// data access object, which bring data for functionality related with book
public class BookDAO implements DAOInterface<Book> {

    private List<Book> bookSet = null;

    public List<Book> selectBooks(String SQL) {
        bookSet = new ArrayList<Book>();
        Connection con = ConnectionDBUtility.getInstance();
        try (Statement st = con.createStatement();
                ResultSet res = st.executeQuery(SQL);) {
            while (res.next()) {
                Book book = new Book();
                book.setId(res.getLong("id"));
                book.setBookName(res.getString("name"));
                book.setAuthor(new Author(res.getLong("author_id"), res.getString("author_name")));
                book.setGenre(new Genre(res.getLong("genre_id"), res.getString("genre_name")));
                book.setIsbn(res.getString("isbn"));
                book.setPageNumber(res.getInt("page_count"));
                book.setPublishYear(res.getInt("publish_year"));
                book.setPublisher(new Publisher(res.getLong("publisher_id"), res.getString("publisher_name")));
               // book.setImage(res.getBytes("image"));
                book.setDescription(res.getString("description"));
                bookSet.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookSet;
    }
    
    @Override
    public List<Book> getAll() {    
            return selectBooks(SELECT_BOOK);
    }
    

}
