<%-- 
    Document   : search
    Created on : Jul 7, 2018, 4:25:05 PM
    Author     : MinhDuc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="bootstrap3/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
        <style>
            .painting .name {
                display: block;
                font-weight: bold;
                margin-top: 5px;
            }
            .painting .code {
                color: gray;
            }
            .painting .price {
                font-weight: bold;
                color: red;
                margin-bottom: 15px;
                font-size: 16px;
            }
        </style>
    </head>
    <body onload="handleOnLoad()">
        <div class="container">
            <div class="pull-left">
                <input id="inputSearch" type="text" placeholder="Tên SP/ mã SP" onkeypress="handleKeyPress(event)"/>
            <button class="btn btn-info" type="button" onclick="search()">Tìm</button>            
            </div>
            <div class="pull-right">
                <form action="CenterServlet">
                    <input id="pdfInput" type="hidden" name="pdfInput" value=""/>
                    <button name="btAction" value="PrintPDF">Print PDF</button>
                </form>
            </div>
            <c:set var="xml" value="${requestScope.XML_RESULTS}"/>
            <br><br>
            <div class="row" id="gridResults" style="display: none">
            </div>
            <font id="noResults" color="red" style="display: none">Hỏi khó quá ông nội!</font>
        </div>

        <script type="text/javascript">
            var xml = stringToXMLDom('${xml}');
            var searchValue = '${requestScope.SEARCH_VALUE}';
            let gridResults = document.getElementById("gridResults");
            let textNoResults = document.getElementById("noResults");
            var hasResult = false;
            var resultCounter = 0;            
            var resultCodes = []; 
            let pdfInput = document.getElementById("pdfInput");
            
            
            function updatePDFInput() {
                pdfInput.value = resultCodes.toString();
            }
            
            function updateTableDisplay() {
                if (hasResult) {
                    gridResults.style.display = "block";
                    textNoResults.style.display = "none";
                } else {
                    gridResults.style.display = "none";
                    textNoResults.style.display = "block";
                }
            }

            function handleOnLoad() {
                gridResults.innerHTML = "";
                searchNode(xml, searchValue);
                updateTableDisplay();
                updatePDFInput();
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
                    +'<a href="'+pageURL+'">'
                    +'<img class="img-responsive" src="'+imageURL+'"/>'
                    +'</a>'
                    +'<a class="name" href="'+pageURL+'">'+name+'</a>'
                    +'<div class="code">'+code+'</div>'
                    +'<div class="price">'+price+' đ</div>'
                    +'</div>';
                gridResults.innerHTML += tmp;
                resultCounter++;
                if (resultCounter%4==0) {
                    gridResults.innerHTML += '<div class="col-md-12"></div>';
                }
            }
            function search() {
                var strSearch = document.getElementById("inputSearch").value;
                if (strSearch == "") {
                    alert("Please enter search keyword");
                    return;
                }
                gridResults.innerHTML = "";
                hasResult = false;
                resultCounter = 0;
                resultCodes = [];
                searchNode(xml, strSearch);
                updateTableDisplay();
                updatePDFInput();
            }
            function searchNode(node, strSearch) {
                if (node == null) {
                    return;
                }
                if (node.tagName == "painting") {
                    var code = node.childNodes[1].firstChild.nodeValue;
                    code = code.toLowerCase();
                    var keywords = node.lastChild.firstChild.nodeValue;
                    if (keywords.indexOf(strSearch, 0) > -1 || code.indexOf(strSearch, 0) > -1) {
                        hasResult = true;
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

            function stringToXMLDom(string) {
                var xmlDoc = null;
                if (window.DOMParser)
                {
                    parser = new DOMParser();
                    xmlDoc = parser.parseFromString(string, "text/xml");
                } else // Internet Explorer
                {
                    xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
                    xmlDoc.async = "false";
                    xmlDoc.loadXML(string);
                }
                return xmlDoc;
            }
        </script>
    </body>
</html>
