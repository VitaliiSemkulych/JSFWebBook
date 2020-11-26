/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


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
// data access object, which bring data for functionality related with publisher
public class PublisherDAO implements DAOInterface<Publisher>{
  private final List<Publisher> pablisherSet = new ArrayList<Publisher>();
    public List<Publisher> getUpdateAll() {
      Connection con = ConnectionDBUtility.getInstance();
        try (Statement st = con.createStatement();
                ResultSet res = st.executeQuery(SELECT_PUBLISHER);) {
            while (res.next()) {
                pablisherSet.add(new Publisher(res.getLong("id"),res.getString("name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pablisherSet;
    }

    @Override
    public List<Publisher> getAll() {

       if(!pablisherSet.isEmpty()){
            return pablisherSet;
        }else{
          return  getUpdateAll();
        }
    
    }
 

   
    
}
