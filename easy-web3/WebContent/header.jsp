<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="pe.com.bbva.util.Constantes"%>
<%@page import="pe.com.bbva.core.action.GenericAction"%>
<%@page import="pe.com.bbva.core.domain.UsuarioSession"%>
<%@page import="pe.com.bbva.core.service.ServiceUtil"%>
<%
String menuBar = (String)GenericAction.getObjectSession(Constantes.MENU_CONTROLLER);
UsuarioSession usuarioSession =(UsuarioSession)GenericAction.getObjectSession(Constantes.USUARIO_SESSION);

%>
<script>
//Para desactivar la tecla F5
// 		     if ( $.browser.msie ){
// 				window.history.forward(1);
// 				document.attachEvent("onkeydown", my_onkeydown_handler);
// 				function my_onkeydown_handler()
// 				{
// 					switch (event.keyCode){
// 						case 116 : // 'F5'
// 									event.returnValue = false;
// 									event.keyCode = 0;
// 									window.status = "Hemos Desabilitado F5";
// 								  break;
// 					}
// 				}
// 			}
		    
function closeSession(){
	if(confirmar()){
		cerrar();
		$("#formAux").attr("action","./closeSession.do");
		$("#formAux").submit();  
	}
}


function confirmar()   
{  
    return confirm('Esta apunto de cerrar esta página y perdera todos sus datos?');  
}

function cerrar(){
	window.open('','_parent','');
	window.close();
} 
	 
</script>
<table bgcolor="#FFFFFF" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    	<div id="header-custom">
    		<div class="hc-horizontal hc-horizontal-level1">	    	
		    	<div id="logo-enlaces" class="hc-horizontal-level1-right">
		    		<div class="hc-enlaces hc-enlaces-home">
			    		<a href="<s:url action='home'/>" title="Ir a la p&aacute;gina principal"><img title="Ir a la p&aacute;gina principal"  alt="Ir al inicio" src="images/icon-home.png"><span>Inicio</span></a>
			    	</div>
			    	<div class="hc-enlaces hc-enlaces-exit">
<%-- 			    		<a href="<s:url action='closeSession'/>"><img title="Cerrar sesi&oacute;n" alt="Cerrar sesi&oacute;n" src="images/icon-exit.png"><span>Salir</span></a> --%>
							<a href="javascript:closeSession();"><img title="Cerrar sesi&oacute;n" alt="Cerrar sesi&oacute;n" src="images/icon-exit.png"><span>Salir</span></a> 
			    	</div>
		    	</div>
		    	<div id="logo-sistema" class="hc-horizontal-level1-right">
		    		
		    	</div>
		    	
		    	<div id="logo-bbva">
		    		<img alt="Logo BBVA Continental" src="images/logo_bbva.gif">
		    	</div> 
	    	</div> 
	    	<div class="hc-horizontal hc-horizontal-level2">
	    		<div id="hc-bienvenida">
		    		<p>		    			
		    			<span class="txt-bienvenido txt-bienvenido-titulo"><img alt="" src="images/icon-user.png">Bienvenido(a): <span class="txt-bienvenido-usuario"><%=usuarioSession.getNombres()%></span></span>
		    		</p>
		    	</div>
	    	</div> 
    	</div>  		  
  	</td>  	
  </tr>
  
  <tr>
    <td colspan="4">
<%=menuBar%>
    </td>
  </tr>
  
</table>
