/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function loadBar(data) {
    
    if (data.status == "begin") {
        document.getElementById('loading_wrapper').style.display = "block";
    } else if (data.status == "success") {
        document.getElementById('loading_wrapper').style.display = "none";
    }
    }
 