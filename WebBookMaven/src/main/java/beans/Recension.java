/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import tool.Formatting;
import utilities.ConnectionDBUtility;



/**
 *
 * @author Admin
 */
//class which contain model for book recension
public class Recension {
    private long recensionId;
    private String userEmail;
    private long bookId;
    private String recensionText;

    public Recension() {
    }

    public Recension(long recensionId, String userEmail, long bookId, String recensionText) {
        this.recensionId = recensionId;
        this.userEmail = userEmail;
        this.bookId = bookId;
        this.recensionText = recensionText;
    }

    public long getRecensionId() {
        return recensionId;
    }

    public String getUserName(){
    String userName=null;
    Connection conn = ConnectionDBUtility.getInstance();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select userName from users where userid='"+userEmail+"'");) {
            while (rs.next()) {
               
                    userName = rs.getString("userName");
               
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return Formatting.handleEmailText(userName);
    }
    public String getUserEmail() {
        return Formatting.handleEmailText(userEmail);
    }
     public String getUnformedUserEmail() {
        return userEmail;
    }

    public long getBookId() {
        return bookId;
    }

    public String getRecensionText() {
        return recensionText;
    }

    public void setRecensionId(long recensionId) {
        this.recensionId = recensionId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setRecensionText(String recensionText) {
        this.recensionText = recensionText;
    }
    
   
    
}
