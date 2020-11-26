/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emums;

/**
 *
 * @author Admin
 */
//enum where counted search by phrase category
public enum SearchType {
    
    BOOK_NAME("Book Name"),
    AUTHOR("Author");
    private String value;
    SearchType(String value){
        this.value=value;
    }
    public String getType(){
    return value;
    } 
}
