/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Author;
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
// data access object, which bring data for functionality related with author
public class AuthorDAO implements DAOInterface<Author> {

   private final List<Author> authorSet = new ArrayList<Author>();

    public List<Author> getUpdateAll() {
        Connection con = ConnectionDBUtility.getInstance();
        try (Statement st = con.createStatement();
                ResultSet res = st.executeQuery(SELECT_AUTHOR);) {
            while (res.next()) {
                authorSet.add(new Author(res.getLong("id"),res.getString("fio")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return authorSet;
    }

    @Override
    public List<Author> getAll() {
        if (!authorSet.isEmpty()) {
            return authorSet;
        } else {
           return getUpdateAll();
        }
    }

}
