/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 *
 * @author Admin
 */
// singletone class 
//return  connection to database
public class ConnectionDBUtility {

    private static Connection con=null;

  private static void initConnection(){
        
        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("jdbc/Library");
            con = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ConnectionDBUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static Connection getInstance() {
        if(con==null){
            initConnection();
        }
        return con;
    }

}
