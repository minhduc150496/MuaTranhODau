/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlingtest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import sample.painting.PaintingDAO;
import sample.utils.*;

/**
 *
 * @author MinhDuc
 */
public class CrawlingTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PaintingDAO dao = new PaintingDAO();
        String s = "";
        try {
            if (dao==null || true) {
                s = XMLUtils.convertHTMLToString("https://tuongxinh.com.vn/tranh-phu-dieu-3d/");
            }
//            String html  = "<head>  <link rel=\"next\" href=\"/order-status/tranh-trang-tri/tranh-trang-tri-noi-that/results19-36\" /><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\"><link rel=\"stylesheet\" href=\"/templates/ot_ceramic/css/media-mobile.css\" type=\"text/css\" /><script type=\"text/javascript\" src=\"/templates/ot_ceramic/scripts/jquery.min.js\"></script><script type=\"text/javascript\" src=\"/templates/ot_ceramic/scripts/jquery.flexmenu.js\"></script><script type=\"text/javascript\" src=\"/templates/ot_ceramic/scripts/media-mobile.js\"></script>  <base href=\"http://www.tranh-dep.com/tranh-tranh-tri-noi-that.html\" />  <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />  <meta name=\"keywords\" content=\"tranh trang tri noi that, tranh trang tri van phong\" />  <meta name=\"rights\" content=\"Tranh-dep.com\" />  <meta name=\"robots\" content=\"index, follow\" />    <meta name=\"description\" content=\"Tranh trang trí nội thất, phong phú về chủ để và chất liệu. của hơn 1400 họa sĩ khác nhau cùng các nhà cung cấp ảnh kỹ thuật số chất lượng cao.\" />    <title>Tranh trang trí nội thất - Tranh trang trí văn phòng</title>      <script src=\"/media/srizon/js/jquery.min.js\" type=\"text/javascript\"></script>  <script src=\"/media/srizon/js/jquery-noconflict.js\" type=\"text/javascript\"></script>  <link href=\"/tranh-tranh-tri-noi-that.html\" rel=\"canonical\" />  <link href=\"/templates/ot_ceramic/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\" />  <link rel=\"stylesheet\" href=\"/plugins/editors/jckeditor/typography/typography2.php\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/media/system/css/modal.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/templates/ot_ceramic/css/k2.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/components/com_virtuemart/assets/css/facebox.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/components/com_virtuemart/assets/css/vmsite-ltr.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/plugins/vmcustom/customfieldsforall/assets/css/customsforall_fe.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/templates/system/css/system.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/templates/system/css/general.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/templates/ot_ceramic/css/template.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/templates/ot_ceramic/css/layouts/default.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"/media/system/css/calendar-jos.css\" type=\"text/css\"  title=\"Green\"  media=\"all\" />  <link rel=\"stylesheet\" href=\"http://www.tranh-dep.com/modules/mod_cf_filtering/assets/style.css\" type=\"text/css\" />  <link rel=\"stylesheet\" href=\"http://www.tranh-dep.com/plugins/vmcustom/customfieldsforall/assets/css/customsforall_fe.css\" type=\"text/css\" />  <style type=\"text/css\">div.ot-headertop-2 {width: 100%}div.ot-footer div.ot-bottom-1 {width: 100%}			.width100{ width: 100%; } 			 			.width50{ width: 50%; } 			 			.width33{ width: 33%; } 			 			.width31{ width: 31%; } 			 			.width23{ width: 23%; } 			 			.width20{ width: 20%; } 			 			.width100{ width: 100%; } 			 			.width50{ width: 50%; } 			 			.width33{ width: 33%; } 			 			.width48{ width: 48%; } 			 			.width18{ width: 18%; } 			 			.width17{ width: 17%; } 			 			.width20{ width: 20%; } 			   </style>  <script src=\"/media/system/js/mootools-core.js\" type=\"text/javascript\"></script>  <script src=\"/media/system/js/core.js\" type=\"text/javascript\"></script>  <script src=\"/media/system/js/modal.js\" type=\"text/javascript\"></script>  <script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js\" type=\"text/javascript\"></script>  <script src=\"/media/k2/assets/js/k2.noconflict.js\" type=\"text/javascript\"></script>  <script src=\"/components/com_k2/js/k2.js\" type=\"text/javascript\"></script>    <script src=\"/components/com_virtuemart/assets/js/jquery.noConflict.js\" type=\"text/javascript\"></script>  <script src=\"/components/com_virtuemart/assets/js/vmsite.js\" type=\"text/javascript\"></script>  <script src=\"/components/com_virtuemart/assets/js/facebox.js\" type=\"text/javascript\"></script>  <script src=\"/components/com_virtuemart/assets/js/vmprices.js\" type=\"text/javascript\"></script>  <script src=\"/media/system/js/mootools-more.js\" type=\"text/javascript\"></script>  <script src=\"/templates/ot_ceramic/scripts/dropdownMenu.js\" type=\"text/javascript\"></script>  <script src=\"/templates/ot_ceramic/scripts/otscript.js\" type=\"text/javascript\"></script>  <script src=\"/media/system/js/calendar.js\" type=\"text/javascript\"></script>  <script src=\"/media/system/js/calendar-setup.js\" type=\"text/javascript\"></script>  <script src=\"http://www.tranh-dep.com/modules/mod_cf_filtering/assets/general.js\" type=\"text/javascript\"></script>  <script src=\"http://www.tranh-dep.com/media/system/js/modal.js\" type=\"text/javascript\"></script>  <script src=\"http://www.tranh-dep.com/modules/mod_cf_filtering/assets/slider.js\" type=\"text/javascript\"></script>  <script src=\"http://www.tranh-dep.com/modules/mod_cf_filtering/assets/drag_refactor.js\" type=\"text/javascript\"></script>  <script type=\"text/javascript\">		window.addEvent('domready', function() {			SqueezeBox.initialize({});			SqueezeBox.assign($$('a.modal'), {				parse: 'rel'			});		});var K2SitePath = '/';vmSiteurl = 'http://www.tranh-dep.com/' ;vmLang = \"\"vmCartText = ' was added to your cart.' ;vmCartError = 'There was an error while updating your cart.' ;loadingImage = '/components/com_virtuemart/assets/images/facebox/loading.gif' ;closeImage = '/components/com_virtuemart/assets/images/facebox/closelabel.png' ; Virtuemart.addtocart_popup = '0' ; faceboxHtml = '<div id=\"facebox\" style=\"display:none;\"><div class=\"popup\"><div class=\"content\"></div> <a href=\"#\" class=\"close\"></a></div></div>'  ;jQuery(document).ready(function () {	jQuery('.orderlistcontainer').hover(		function() { jQuery(this).find('.orderlist').stop().show()},		function() { jQuery(this).find('.orderlist').stop().hide()}	)});Calendar._DN = new Array (\"Sunday\", \"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\", \"Saturday\", \"Sunday\"); Calendar._SDN = new Array (\"Sun\", \"Mon\", \"Tue\", \"Wed\", \"Thu\", \"Fri\", \"Sat\", \"Sun\"); Calendar._FD = 0; Calendar._MN = new Array (\"January\", \"February\", \"March\", \"April\", \"May\", \"June\", \"July\", \"August\", \"September\", \"October\", \"November\", \"December\"); Calendar._SMN = new Array (\"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec\"); Calendar._TT = {};Calendar._TT[\"INFO\"] = \"About the Calendar\"; Calendar._TT[\"ABOUT\"] = \"DHTML Date/Time Selector\\n\" + \"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\\n\" +\"For latest version visit: http://www.dynarch.com/projects/calendar/\\n\" +\"Distributed under GNU LGPL.  See http://gnu.org/licenses/lgpl.html for details.\" +\"\\n\\n\" +\"Date selection:\\n\" +\"- Use the « and » buttons to select year\\n\" +\"- Use the < and > buttons to select month\\n\" +\"- Hold mouse button on any of the above buttons for faster selection.\";Calendar._TT[\"ABOUT_TIME\"] = \"\\n\\n\" +\"Time selection:\\n\" +\"- Click on any of the time parts to increase it\\n\" +\"- or Shift-click to decrease it\\n\" +\"- or click and drag for faster selection.\";		Calendar._TT[\"PREV_YEAR\"] = \"Click to move to the previous year. Click and hold for a list of years.\"; Calendar._TT[\"PREV_MONTH\"] = \"Click to move to the previous month. Click and hold for a list of the months.\"; Calendar._TT[\"GO_TODAY\"] = \"Go to today\"; Calendar._TT[\"NEXT_MONTH\"] = \"Click to move to the next month. Click and hold for a list of the months.\"; Calendar._TT[\"NEXT_YEAR\"] = \"Click to move to the next year. Click and hold for a list of years.\"; Calendar._TT[\"SEL_DATE\"] = \"Select a date.\"; Calendar._TT[\"DRAG_TO_MOVE\"] = \"Drag to move\"; Calendar._TT[\"PART_TODAY\"] = \"Today\"; Calendar._TT[\"DAY_FIRST\"] = \"Display %s first\"; Calendar._TT[\"WEEKEND\"] = \"0,6\"; Calendar._TT[\"CLOSE\"] = \"Close\"; Calendar._TT[\"TODAY\"] = \"Today\"; Calendar._TT[\"TIME_PART\"] = \"(Shift-)Click or Drag to change the value.\"; Calendar._TT[\"DEF_DATE_FORMAT\"] = \"%Y-%m-%d\"; Calendar._TT[\"TT_DATE_FORMAT\"] = \"%a, %b %e\"; Calendar._TT[\"WK\"] = \"wk\"; Calendar._TT[\"TIME\"] = \"Time:\";		if(typeof customFiltersProp==\"undefined\")customFiltersProp=new Array();		customFiltersProp[251]={base_url:'http://www.tranh-dep.com/',Itemid:'776',component_base_url:'/',cf_direction:'ltr',results_trigger:'sel',results_wrapper:'bd_results',loadModule:'1',parent_link:'1',mod_type:'filtering',use_ajax_spinner:'0',use_results_ajax_spinner:'0',results_loading_mode:'ajax',category_flt_parent_link:'1'};window.addEvent('domready',function(){customFilters.keyword_search_clear_filters_on_new_search=1; customFilters.assignEvents(251);customFilters.assignEvents(251);customFilters.createToggle('virtuemart_category_id_251','show');customFilters.createToggle('custom_f_20_251','show');customFilters.createToggle('custom_f_21_251','show');customFilters.createToggle('custom_f_22_251','show');customFilters.createToggle('custom_f_23_251','show');customFilters.createToggle('custom_f_24_251','show');customFilters.createToggle('custom_f_25_251','show');customFilters.createToggle('custom_f_26_251','show');customFilters.createToggle('custom_f_27_251','show');customFilters.createToggle('custom_f_28_251','show');customFilters.createToggle('custom_f_30_251','show');customFilters.createToggle('custom_f_31_251','show');});  </script>  <script type=\"text/javascript\">    (function() {      var strings = {\"MOD_CF_FILTERING_INVALID_CHARACTER\":\"Invalid input. Please use numerical values\",\"MOD_CF_FILTERING_PRICE_MIN_PRICE_CANNOT_EXCEED_MAX_PRICE\":\"MOD_CF_FILTERING_PRICE_MIN_PRICE_CANNOT_EXCEED_MAX_PRICE\"};      if (typeof Joomla == 'undefined') {        Joomla = {};        Joomla.JText = strings;      }      else {        Joomla.JText.load(strings);      }    })();  </script><!-- Facebook Pixel Code --><script>!function(f,b,e,v,n,t,s){if(f.fbq)return;n=f.fbq=function(){n.callMethod?n.callMethod.apply(n,arguments):n.queue.push(arguments)};if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';n.queue=[];t=b.createElement(e);t.async=!0;t.src=v;s=b.getElementsByTagName(e)[0];s.parentNode.insertBefore(t,s)}(window,document,'script','//connect.facebook.net/en_US/fbevents.js');fbq('init', '1602567013339355');fbq('track', 'PageView');</script><!--Start of Zopim Live Chat Script<script type=\"text/javascript\">window.$zopim||(function(d,s){var z=$zopim=function(c){z._.push(c)},$=z.s=d.createElement(s),e=d.getElementsByTagName(s)[0];z.set=function(o){z.set._.push(o)};z._=[];z.set._=[];$.async=!0;$.setAttribute('charset','utf-8');$.src='//v2.zopim.com/?1swunTWlxRAmOgFFwgM3ShAqYmBJhZge';z.t=+new Date;$.type='text/javascript';e.parentNode.insertBefore($,e)})(document,'script');</script> --><noscript><img height=\"1\" width=\"1\" style=\"display:none\"src=\"https://www.facebook.com/tr?id=1602567013339355&ev=PageView&noscript=1\"/></noscript><!-- End Facebook Pixel Code --><noscript><img height=\"1\" width=\"1\" alt=\"\" style=\"display:none\" src=\"https://www.facebook.com/tr?id=344275062425864&amp;ev=PixelInitialized\" /></noscript><div id=\"fb-root\"></div><script>(function(d, s, id) {  var js, fjs = d.getElementsByTagName(s)[0];  if (d.getElementById(id)) return;  js = d.createElement(s); js.id = id;  js.src = \"//connect.facebook.net/vi_VN/all.js#xfbml=1\";  fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script><script>(function() {  var _fbq = window._fbq || (window._fbq = []);  if (!_fbq.loaded) {    var fbds = document.createElement('script');    fbds.async = true;    fbds.src = '//connect.facebook.net/en_US/fbds.js';    var s = document.getElementsByTagName('script')[0];    s.parentNode.insertBefore(fbds, s);    _fbq.loaded = true;  }  _fbq.push(['addPixelId', '422889231195872']);})();window._fbq = window._fbq || [];window._fbq.push(['track', 'PixelInitialized', {}]);</script><noscript><img height=\"1\" width=\"1\" alt=\"\" style=\"display:none\" src=\"https://www.facebook.com/tr?id=422889231195872&amp;ev=PixelInitialized\" /></noscript> <script>  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');  ga('create', 'UA-64600530-1', 'auto');  ga('send', 'pageview');</script><!-- Facebook Conversion Code for Khách hàng tiềm năng - ShinHaile 1 --><script>(function() {var _fbq = window._fbq || (window._fbq = []);if (!_fbq.loaded) {var fbds = document.createElement('script');fbds.async = true;fbds.src = '//connect.facebook.net/en_US/fbds.js';var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(fbds, s);_fbq.loaded = true;}})();window._fbq = window._fbq || [];window._fbq.push(['track', '6025126648537', {'value':'0.00','currency':'VND'}]);</script><div id=\"fb-root\"></div><script>(function(d, s, id) {  var js, fjs = d.getElementsByTagName(s)[0];  if (d.getElementById(id)) return;  js = d.createElement(s); js.id = id;  js.src = \"//connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v2.7&appId=602020669954730\";  fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script><noscript><img height=\"1\" width=\"1\" alt=\"\" style=\"display:none\" src=\"https://www.facebook.com/tr?ev=6025126648537&amp;cd[value]=0.00&amp;cd[currency]=VND&amp;noscript=1\" /></noscript> <!-- Google Code dành cho Thẻ tiếp thị lại --><script type=\"text/javascript\">var google_tag_params = {ecomm_prodid: 'REPLACE_WITH_VALUE',ecomm_pagetype: 'REPLACE_WITH_VALUE',ecomm_totalvalue: 'REPLACE_WITH_VALUE',};</script><script type=\"text/javascript\">/* <![CDATA[ */var google_conversion_id = 993299932;var google_custom_params = window.google_tag_params;var google_remarketing_only = true;/* ]]> */</script><script type=\"text/javascript\" src=\"//www.googleadservices.com/pagead/conversion.js\"></script><noscript><div style=\"display:inline;\"><img height=\"1\" width=\"1\" style=\"border-style:none;\" alt=\"\" src=\"//googleads.g.doubleclick.net/pagead/viewthroughconversion/993299932/?value=0&amp;guid=ON&amp;script=0\"/></div></noscript><!-- Google Code dành cho Thẻ tiếp thị lại --><div id=\"fb-root\"></div><script>(function(d, s, id) {  var js, fjs = d.getElementsByTagName(s)[0];  if (d.getElementById(id)) return;  js = d.createElement(s); js.id = id;  js.src = \"//connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v2.8&appId=336886423171190\";  fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script><script>  window.fbAsyncInit = function() {    FB.init({      appId      : '1218498121604475',      xfbml      : true,      version    : 'v2.8'    });    FB.AppEvents.logPageView();  };  (function(d, s, id){     var js, fjs = d.getElementsByTagName(s)[0];     if (d.getElementById(id)) {return;}     js = d.createElement(s); js.id = id;     js.src = \"//connect.facebook.net/en_US/sdk.js\";     fjs.parentNode.insertBefore(js, fjs);   }(document, 'script', 'facebook-jssdk'));</script></head>";
            String s2 = XMLPreProcessor.wellForm(s);
            System.out.println(s2);
            
//            dao.insert("Tranh dong ho M01", "M01", "some-URL", 100000, "Tranh đồng hồ mạ vàng chất lượng cao.");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NamingException ex) {
//            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}