/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import beans.Recension;

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
// data access object, which bring data for functionality related with recension
public class RecensionDAO implements DAOInterface<Recension> {

    private final List<Recension> recensionList = new ArrayList<Recension>();

    public List<Recension> selectRecension(String SQL) {
        recensionList .clear();
        Connection con = ConnectionDBUtility.getInstance();
        try (Statement st = con.createStatement();
                ResultSet res = st.executeQuery(SQL);) {
            while (res.next()) {

                recensionList.add(new Recension(res.getLong("recension_id"), res.getString("user_id"), res.getLong("book_id"), res.getString("recension_text")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recensionList;
    }

    @Override
    public List<Recension> getAll() {
        return selectRecension(SELECT_RECENSION);
    }

}
