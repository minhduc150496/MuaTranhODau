/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getXMLHttpObject() {
    var xmlHttp = null;
    try {
        xmlHttp = new XMLHttpRequest();        
    } catch (e) {
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}

function xmlDomToString(xmlDom) {
   try {
      // Chrome, FF, Opera
      return (new XMLSerializer()).serializeToString(xmlDom);
  }
  catch (e) {
     try {
        // IE
        return xmlDom.xml;
     }
     catch (e) {  
        alert('Xmlserializer not supported');
     }
   }
   return false;
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

function toRawString(content) {
    var content = content.toLowerCase();
    var result = "";
    for (var i = 0; i < content.length; i++) {
        var ch = map.get(content[i]);
        if (ch == null) {
            result += content[i];
        } else {
            result += ch;
        }
    }
    return result;
}

map = new Map([
    ["á", "a"],
    ["à", "a"],
    ["ả", "a"],
    ["ã", "a"],
    ["ạ", "a"],

    ["ă", "a"],
    ["ắ", "a"],
    ["ằ", "a"],
    ["ẳ", "a"],
    ["ẵ", "a"],
    ["ặ", "a"],

    ["â", "a"],
    ["ấ", "a"],
    ["ầ", "a"],
    ["ẩ", "a"],
    ["ẫ", "a"],
    ["ậ", "a"],

    ["đ", "d"],

    ["é", "e"],
    ["è", "e"],
    ["ẻ", "e"],
    ["ẽ", "e"],
    ["ẹ", "e"],

    ["ê", "e"],
    ["ế", "e"],
    ["ề", "e"],
    ["ể", "e"],
    ["ễ", "e"],
    ["ệ", "e"],

    ["í", "i"],
    ["ì", "i"],
    ["ỉ", "i"],
    ["ĩ", "i"],
    ["ị", "i"],

    ["ó", "o"],
    ["ò", "o"],
    ["ỏ", "o"],
    ["õ", "o"],
    ["ọ", "o"],

    ["ô", "o"],
    ["ố", "o"],
    ["ồ", "o"],
    ["ổ", "o"],
    ["ỗ", "o"],
    ["ộ", "o"],

    ["ơ", "o"],
    ["ớ", "o"],
    ["ờ", "o"],
    ["ở", "o"],
    ["ỡ", "o"],
    ["ợ", "o"],

    ["ú", "u"],
    ["ù", "u"],
    ["ủ", "u"],
    ["ũ", "u"],
    ["ụ", "u"],

    ["ư", "u"],
    ["ứ", "u"],
    ["ừ", "u"],
    ["ử", "u"],
    ["ữ", "u"],
    ["ự", "u"],

    ["ý", "y"],
    ["ỳ", "y"],
    ["ỷ", "y"],
    ["ỹ", "y"],
    ["ỵ", "y"]
]);
