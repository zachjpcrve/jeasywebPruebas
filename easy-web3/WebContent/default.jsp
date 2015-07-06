
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
    
<html>
    <head>
		
        <link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
        <link rel="stylesheet" href="css/form.css" type="text/css"/>
         <link rel="stylesheet" type="text/css" href="css/bbva.css"/>  
         <link rel="stylesheet" type="text/css" href="css/ui.button.css"/>  
		<link rel="stylesheet" href="jqmenu/demos.css" type="text/css" />
		<link rel="stylesheet" href="jqmenu/jquery.ui.all.css" />
        
		<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.10.custom.css"/>
<!-- 		<link rel="stylesheet" type="text/css" href="css/displayTable.css"/>  -->
<!-- 		<link rel="stylesheet" type="text/css" href="css/jquery.checkboxtree.min.css"> -->
		
		<link rel="stylesheet" type="text/css" href="css/timepicker.css">
		<link rel="stylesheet" type="text/css" href="css/custom-home.css">	
		
		<!-- agregado EZM -->
		<link rel="stylesheet" type="text/css" href="css/ui.jqgrid.css">	
		<!-- fin -->		
        <script type="text/javascript" src="js/struts.js"></script>
		<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="js/validation/jquery.validationEngine-es.js"></script>
		<script type="text/javascript" src="js/validation/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="js/util.js"></script>
		<script type="text/javascript" src="js/validation/jquery.meio.mask.js"></script>
		<script type="text/javascript" src="js/jquery.counter-1.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.10.custom.min.js"></script>
  		<script type="text/javascript" src="js/jquery.ui.datepicker-es.js"></script>
  		<script type="text/javascript" src="js/json2.js"></script>
		<script type="text/javascript" src="js/jquery.alphanumeric.js"></script>
		<script type="text/javascript" src="js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="js/grid.locale-es.js"></script>
		<script type="text/javascript" src="js/jquery.multiselects-0.3.js"></script>

  		
		<script src="jqmenu/jquery.ui.core.js"></script>
		<script src="jqmenu/jquery.ui.widget.js"></script>
		<script src="jqmenu/jquery.ui.position.js"></script>
		<script src="jqmenu/jquery.ui.button.js"></script>
		<script src="jqmenu/jquery.ui.menu.js"></script>
		<script src="jqmenu/jquery.ui.menubar.js"></script>

		<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
		<script type="text/javascript" src="js/jquery-ui-timepicker-es.js"></script>

        <title><tiles:insertAttribute name="title" ignore="true" /></title>
            <% 
			String x = request.getAttribute("scrollX")==null?"ningun valor":request.getAttribute("scrollX").toString();
			String y = request.getAttribute("scrollY")==null?"ningun valor":request.getAttribute("scrollY").toString();
		 	%>

    </head>
    <body id="mybody" onload="ResetScrollPosition('<%=x%>','<%=y%>');">
        <table id="tabla-contenedor-principal" border="0" cellpadding="2" cellspacing="2" align="center" >
            <tr>
                <td height="30">
                    <tiles:insertAttribute name="header" />
                </td>
            </tr>
            <tr>
                <td valign="top"  class="ui-widget-content2 ln_formatos" style="border: #A4A4A4 1px solid;">
                	<div>
						<s:if test="hasActionErrors()">
						   <div class="errors">
						     <s:actionerror theme="simple"/>
						</div>
						</s:if>
						<s:if test="hasActionMessages()">
						   <div class="success">
							     <s:actionmessage theme="simple"/>
						   </div>
						</s:if>
					</div>
                	<div >
                    	<tiles:insertAttribute name="body" />
                    </div>
                </td>
            </tr>
            <tr>
                <td height="30"  style="border: #A4A4A4 1px solid;" class="ui-widget-content2 ln_formatos">
                    <tiles:insertAttribute name="footer" />
                </td>
            </tr>
        </table>
    </body>
    <script>

	function actionIcon(action,data,title,iconSrc){
		var _confirm=false;
		return paintActionIcon(action,data,title,iconSrc,_confirm,'');
	}
	function actionIconConfirm(action,data,title,iconSrc,msg){
		var _confirm=true;
		return paintActionIcon(action,data,title,iconSrc,_confirm,msg);
	}

	function paintActionIcon(action,data,title,iconSrc,_confirm,msg){
		var btn="<input  type='image' title='"+title+"' onClick=\"redirect('"+action+"?"+data+"',"+_confirm+",'"+msg+"');\" src='"+iconSrc+"' />";
		return btn;
	}
	
	function actionAct(action,data){
		var _confirm=false;
		return paintActionAct(action,data,_confirm,'');
	}
	function actionActConfirm(action,data){
		var _confirm=true;
		return paintActionAct(action,data,_confirm,'');
	}
	
	function paintActionAct(action,data,_confirm,msg){
		return window.onload=redirect(action+"?"+data,_confirm,'');
	}
	function redirect(url,_confirm,msg){
	    if(_confirm){
	    	if(confirm(msg)){
	    		window.location=url;
	    	}
	    }else{
	    	window.location=url;
	    }
    	return url;
    }
    
    function paintEstado(gridColumn){
    $("td[aria-describedby='"+gridColumn+"']").each(function (){
			var _html=$(this).html();
			if(_html=='&nbsp;' || _html=='1'){
				$(this).html('<img border="0" alt="ACTIVO" title="ACTIVO" src="images/icons/activo.png">');
			}else{
				$(this).html('<img border="0" alt="INACTIVO" title="INACTIVO" src="images/icons/inactivo.png">');
			}
		});
    }
    
    function paintEstado_serv(gridColumn){
    	$("td[aria-describedby='"+gridColumn+"']").each(function(){
    		var _html_serv=$(this).html();
    		if(_html_serv=='&nbsp;' || _html_serv=='1'){
    			$(this).html('<img border="0" alt="OK" title="OK" src="images/icons/verde2.png">');
    		}else{
    			$(this).html('<img border="0" alt="ERROR" title="ERROR" src="images/icons/rojo2.png">');	
    		}
    	});
    }
    
    //Usado en los cuadros de asignaciones
    	function syncronizedValueNumeric(idText,idTextFixed){
	
        var $objTextFixed=$("#"+idTextFixed);
        var $objText=$("#"+idText);
       	var numPatron=/[0-9]+[.]?[0-9]*/;
       	var number='';
        var _valueText=$objText.val();
        if(_valueText==''){
        	$objText.val(0);
        }
		var previousText='';
		$objText.focus(function () {
       		previousText = this.value;
   		}).change(function() {
   			
   			var _val=parseFloat(this.value);
			$(this).val(_val);
			if(this.value!='0' && !parseFloat(this.value)){
				this.value=previousText;
			}else{
				var val=$objTextFixed.val();
				number=val.match(numPatron);
				val=val.replace(number,this.value);
				$objTextFixed.val(val);
			}
			
		});
		var previousTextFixed='';
		$objTextFixed.focus(function () {
       			previousTextFixed = this.value;
   		}).change(function() {
   			var _value=$objText.val();
   		    number=this.value.match(numPatron);
   			if(_value != number){
   				this.value=previousTextFixed;
   			}
   		});
	}
    
	$(function() {
		function select(event, ui) {
			$("<div/>").text("Selected: " + ui.item.text()).appendTo("#log");
			if (ui.item.text() == 'Quit') {
				$(this).menubar('destroy');
			}
		}
		$("#bar1").menubar({
			position: {
				within: $("#demo-frame").add(window).first()
			},
			select: select
		});

		$(".menubar-icons").menubar({
			autoExpand: true,
			menuIcon: true,
			buttons: true,
			position: {
				within: $("#demo-frame").add(window).first()
			},
			select: select
		});
		
		$(".clean-form").click(function(){
	   	     var $form=$(this).parents().filter("form");
	   	     $form.find('input:text,select').each(function() {
	   	    
                  var  _type=$(this).attr('type');
                  var _readonly=$(this).attr('readonly');
                  var _disabled=$(this).attr('disabled');
         
        
				if(!(_readonly || _disabled)){
                 	 if(_type =='text'){
                  		$(this).val('');
                 	 }else{

                 		var valIndex0=$(this).children().first().attr('value');
                 	 	$(this).val(valIndex0);
                 	 }
                  }
        	});
	   	         return false;
    	});
    	$(".search-form").click(function(){
	  	    var $form=$(this).parents().filter("form");
	  	    var action=$form.attr('action');
	  	    var target=$(this).attr('target');
	  	    if(!target){
	  	    	alert('dataTable no definido');
	  	    	return false;
	  	    }
	  	    if(!action){
	  	     	alert('url no definido');
	  	    	return false;
	  	    }
	  	    var data= $form.serialize();
	  		jQuery("#"+target).jqGrid('setGridParam',{url:action+"?"+data,page:1}).trigger("reloadGrid");
	  		return false;
	  	});
	  	
	});
</script>
    
</html>
