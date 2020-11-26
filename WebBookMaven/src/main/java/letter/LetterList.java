/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letter;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Admin
 */
// create list with english alphabet letter
@Named
@ApplicationScoped
public class LetterList {
    private List<String> letterList = new ArrayList<String>();
  public  LetterList(){
     for(int i =65;i<=90;i++){
        letterList.add(String.valueOf((char)i));
    
     }
    }

    public List<String> getLetterList() {
        return letterList;
    }
    
   
  
    
}
