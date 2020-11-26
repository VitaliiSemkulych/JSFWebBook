/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import beans.Recension;
import dao.RecensionDAO;
import java.util.List;

/**
 *
 * @author Admin
 */
// servise where defined query for retriving data from recension DAO
public class RecensionService {
    
     private final RecensionDAO recensionDAO = new RecensionDAO();

      public List<Recension> getRecensionListByUser(long bookId){
    
          String sql="SELECT  * FROM recension Where book_id="+String.valueOf(bookId);
          return recensionDAO.selectRecension(sql);  
    }
    
       
}
