/* 
 * Pure Java Script Route 
 * v 1.0.0
 * Author: Viplav Fauzdar
 * Date: 1/6/2015
 * License: MIT
 */


console.log(window.location);
var curhash = window.location.hash;
//var curvw = document.getElementById('js-view').innerHTML;
var curpart;
console.log("current hash:" + curhash);
//            console.log("current vw:" + curvw);

var prehash = [], prevw = [], prepart = [];
function hvtrk() {
    prehash.push(curhash);
    //prevw.push(curvw);
    prepart.push(curpart);
    console.log("previous hash:" + curhash);
    //               console.log("previous vw:" + curvw);
    curhash = window.location.hash;
    //curvw = document.getElementById('js-view').innerHTML;
    console.log("current hash:" + curhash);
    //               console.log("current vw:" + curvw);
}

var linkclicked = false;
window.onhashchange = function() {
    console.log(window.location);
    console.log('hash changed');
    console.log("linkclicked: " + linkclicked);
    if (linkclicked){// || window.location.hash.indexOf('/')===-1) {
        //do nothing
    } else {
        console.log("back pressed. changing vw to previous!");
        //document.getElementById('vw').innerHTML = prevw.pop();//works only with back                    
        //document.getElementById('vw').innerHTML = prevw[prehash.indexOf(window.location.hash)]; //works with both back & forward
        //getPartial(prepart[prehash.indexOf(window.location.hash)]); //loads via ajax instead
        if (window.location.hash !== "" && window.location.hash !== "#/"){
            getPartial(prepart[prehash.indexOf(window.location.hash)]); //loads via ajax instead            
        }else{
            window.location = "index3.html";
        }
    }
    linkclicked = false;
};

function getPartial(partial) {    
    console.log("loading page: " + partial);
    linkclicked = true;
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            document.getElementById('js-view').innerHTML = xmlhttp.responseText;
            hvtrk();
            curpart = partial;
            //document.getElementById('smallJumbotron').style.display='block';            
        }
    };
    xmlhttp.open("GET", partial + "?t=" + Math.random(), true);
    xmlhttp.send();
    //$('#js-view').load(partial);
}

//document.getElementById('smallJumbotron').style.display='none';