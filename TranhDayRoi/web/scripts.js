/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var xml = null;
radioSort = "Inc"; // Inc/Dec
let totalResults = document.getElementById("totalResults");
let gridResults = document.getElementById("gridResults");
let textNoResults = document.getElementById("noResults");
let advanceSearch = document.getElementById("advanceSearch");
var resultCounter = 0;
var arrResults = [];
var resultCodes = [];
let pdfInput = document.getElementById("pdfInput");

preloadPaintings();
function preloadPaintings() {
    var sXml = localStorage.getItem('sXml');
    if (sXml == null) {
        var xmlHttp = getXMLHttpObject();
        var url = "./CenterServlet?btAction=Preload"; 
        xmlHttp.open("GET", url, false);
        xmlHttp.send(null);
        xml = xmlHttp.responseXML;
        var sXml = xmlDomToString(xml);
        localStorage.setItem('sXml', sXml);
    } else {
        xml = stringToXMLDom(sXml);
    }
}

function updatePDFInput() {
    pdfInput.value = resultCodes.toString();
}

function updateDisplay() {
    if (resultCounter > 0) {
        advanceSearch.style.display = "block";
        totalResults.style.display = "block";
        totalResults.innerHTML = "Tìm thấy " + resultCounter + " kết quả.";
        gridResults.style.display = "block";
        textNoResults.style.display = "none";
    } else {
        advanceSearch.style.display = "none";
        totalResults.style.display = "none";
        gridResults.style.display = "none";
        textNoResults.style.display = "block";
    }
}

function handleOnLoad() {
    gridResults.innerHTML = "";
}

function handleKeyPress(e) {
    var keyCode = e.keyCode || e.which;
    if (keyCode == 13) {
        search();
    }
}

function handleSortChange() {    
    var radios = document.getElementsByName("rdSort");    
    console.log(radios.length);
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            radioSort = radios[i].value;
        }
    }
    arrResults.sort(function (a, b) {
        if (radioSort == "Inc") {
            return a.price - b.price;
        } else {
            return b.price - a.price;
        }
    });
    renderResults(arrResults);
}

function handleRangeChange() {
    var iRange = document.getElementsByName("priceRange")[0].value;
    iRange = parseInt(iRange);
    console.log(iRange);
    switch(iRange) {
        case -1: 
            renderResults(arrResults);
            break;
        case 0: 
            var arr = arrResults.filter(function(x) {
               return (x.price<500);
            });
            renderResults(arr);
            break;
        case 1: 
            var arr = arrResults.filter(function(x) {
               return (500<=x.price<1000000);
            });
            renderResults(arr);
            break;
        case 2: 
            var arr = arrResults.filter(function(x) {
               return (1000000<=x.price<=2000000);
            });
            renderResults(arr);
            break;
        case 3: 
            var arr = arrResults.filter(function(x) {
               return (2000000<=x.price<=3000000);
            });
            renderResults(arr);
            break;
        case 4: 
            var arr = arrResults.filter(function(x) {
               return (3000000<=x.price<=4000000);
            });
            renderResults(arr);
            break;
        case 5: 
            var arr = arrResults.filter(function(x) {
               return (4000000<x.price);
            });
            renderResults(arr);
            break;
    }
}

function renderResults(arr) {
    gridResults.innerHTML = "";
    for (var i = 0; i < arr.length; i++) {
        var painting = arr[i];
        var price = parseInt(painting.price);
        var formatedPrice = price.toLocaleString("vn-VI");
        var tmp = '<div class="col-md-3 painting">'
                + '<a href="' + painting.pageURL + '">'
                + '<img class="img-responsive" src="' + painting.imageURL + '"/>'
                + '</a>'
                + '<a class="name" href="' + painting.pageURL + '">' + painting.name + '</a>'
                + '<div class="code">' + painting.code + '</div>'
                + '<div class="price">' + formatedPrice + ' đ</div>'
                + '</div>';
        gridResults.innerHTML += tmp;
        if (i % 4 == 3) {
            gridResults.innerHTML += '<div class="col-md-12"></div>';
        }
    }
}
function search() {
    var strSearch = document.getElementById("inputSearch").value;
    if (strSearch == "") {
        alert("Please enter search keyword");
        return;
    }
    strSearch = toRawString(strSearch);
    resultCounter = 0;
    resultCodes = [];
    arrResults = [];
    searchNode(xml, strSearch);
    arrResults.sort(function (a, b) {
        if (radioSort == "Inc") {
            return a.price - b.price;
        } else {
            return b.price - a.price;
        }
    });
    renderResults(arrResults);
    updateDisplay();
    updatePDFInput();
}
function searchNode(node, strSearch) {
    if (node == null || resultCounter >= 50) {
        return;
    }
    if (node.tagName == "painting") {
        var code = node.childNodes[1].firstChild.nodeValue;
        code = code.toLowerCase();
        var keywords = node.lastChild.firstChild.nodeValue;
        if (keywords.indexOf(strSearch, 0) > -1 || code.indexOf(strSearch, 0) > -1) {
            var childs = node.childNodes;
            var name = childs[0].firstChild.nodeValue.trim();
            var code = childs[1].firstChild.nodeValue.trim();
            var pageURL = childs[2].firstChild.nodeValue.trim();
            var price = childs[3].firstChild.nodeValue.trim();
            var imageURL = childs[4].firstChild.nodeValue.trim();
            arrResults.push({
                name: name,
                code: code,
                pageURL: pageURL,
                price: price,
                imageURL: imageURL
            });
            resultCodes.push(code);
            resultCounter++;
        }
    }
    var childs = node.childNodes;
    for (var i = 0; i < childs.length; i++) {
        searchNode(childs[i], strSearch);
    }
    return true;
}
