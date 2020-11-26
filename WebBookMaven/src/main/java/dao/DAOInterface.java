/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author Admin
 */
//define behaviour for DAO object
public interface DAOInterface<K> {
    String SELECT_AUTHOR="SELECT  * FROM author ORDER BY fio";
    String SELECT_PUBLISHER="SELECT  * FROM publisher ORDER BY name";
    String SELECT_GENRE="SELECT  * FROM genre ORDER BY name";
    String SELECT_RECENSION="SELECT  * FROM recension";
    String SELECT_BOOK="SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n" +
"inner join genre G on (B.genre_id=G.id) \n" +
"inner join publisher P on(B.publisher_id=P.id) \n" +
"ORDER BY B.name";
    

    List<K> getAll();
    
    
    
}
