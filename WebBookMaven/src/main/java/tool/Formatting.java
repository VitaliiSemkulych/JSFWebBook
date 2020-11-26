/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

/**
 *
 * @author Admin
 */
// dividing text by 17 character in line for boxing in single recension area
public class Formatting {
    
    
    
    public static String handleEmailText(String text) {
        String finnalText = "";

        for (int i = 0; i < text.length(); i++) {
            if (i % 17 == 0) {
                finnalText += "\n";
                finnalText += text.charAt(i);
            } else {
                finnalText += text.charAt(i);
            }
        }
        return finnalText;
    }
    
}
