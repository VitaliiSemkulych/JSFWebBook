/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Genre;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import utilities.ConnectionDBUtility;
/**
 *
 * @author Admin
 */
@Named
@ApplicationScoped
// data access object, which bring data for functionality related with genre
public class GenreDAO implements DAOInterface<Genre>{
private final List<Genre> genreSet = new ArrayList<Genre>();

public List<Genre> getUpdateAll() {
         Connection con = ConnectionDBUtility.getInstance();
        try (Statement st = con.createStatement();
                ResultSet res = st.executeQuery(SELECT_GENRE);) {
            while (res.next()) {
                genreSet.add(new Genre(res.getLong("id"),res.getString("name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return genreSet;
    }
    @Override
    public List<Genre> getAll() {
        if(!genreSet.isEmpty()){
            return genreSet;
        }else{
            return getUpdateAll();
        }
    }
    
}
