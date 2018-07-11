/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var xml = null;
let totalResults = document.getElementById("totalResults");
let gridResults = document.getElementById("gridResults");
let textNoResults = document.getElementById("noResults");
var resultCounter = 0;
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

function updateTableDisplay() {
    if (resultCounter > 0) {
        totalResults.style.display = "block";
        totalResults.innerHTML = "Tìm thấy " + resultCounter + " kết quả.";
        gridResults.style.display = "block";
        textNoResults.style.display = "none";
    } else {
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

function addResultItem(name, code, pageURL, price, imageURL) {
    resultCodes.push(code);
    price = parseInt(price);
    price = price.toLocaleString("vn-VI");
    var tmp = '<div class="col-md-3 painting">'
            + '<a href="' + pageURL + '">'
            + '<img class="img-responsive" src="' + imageURL + '"/>'
            + '</a>'
            + '<a class="name" href="' + pageURL + '">' + name + '</a>'
            + '<div class="code">' + code + '</div>'
            + '<div class="price">' + price + ' đ</div>'
            + '</div>';
    gridResults.innerHTML += tmp;
    resultCounter++;
    if (resultCounter % 4 == 0) {
        gridResults.innerHTML += '<div class="col-md-12"></div>';
    }
}
function search() {
    var strSearch = document.getElementById("inputSearch").value;
    if (strSearch == "") {
        alert("Please enter search keyword");
        return;
    }
    strSearch = toRawString(strSearch);
    gridResults.innerHTML = "";
    resultCounter = 0;
    resultCodes = [];
    searchNode(xml, strSearch);
    updateTableDisplay();
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
            addResultItem(name, code, pageURL, price, imageURL);
        }
    }
    var childs = node.childNodes;
    for (var i = 0; i < childs.length; i++) {
        searchNode(childs[i], strSearch);
    }
    return true;
}
