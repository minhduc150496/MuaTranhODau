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
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>TranhĐâyRồi.com</title>        
    </head>
    <body onload="handleOnLoad()">
        <header class="container-fluid">
            <div class="pull-left logo">
                TranhĐâyRồi!
            </div>
            <div class="pull-left search-box">
                <input id="inputSearch" type="text" placeholder="Tên hoặc mã sp. Ví dụ: hoa, chim, PD739..." onkeypress="handleKeyPress(event)"/>
                <button class="btn-search" type="button" onclick="search()">Tìm</button>            
            </div>
            <div class="pull-right">
                <form action="CenterServlet">
                    <input id="pdfInput" type="hidden" name="pdfInput" value=""/>
                    <button name="btAction" value="PrintPDF">Print PDF</button>
                </form>
            </div>
        </header>
        <div class="container">
            <c:set var="xml" value="${requestScope.XML_RESULTS}"/>
            <br><br>
            <font id="totalResults" color="green" style="display: none">Tìm thấy 25 kết quả.</font>
            <font id="noResults" color="red" style="display: none">Hỏi khó quá ông nội!</font>
            <div class="row grid" id="gridResults" style="display: none">
            </div>
        </div>
           
        <script src="utils.js" type="text/javascript"></script>
        <script src="scripts.js" type="text/javascript"></script>
    </body>
</html>
