<%@taglib prefix="s" uri="/struts-tags"%>
<s:form action="findAllServicio" id="buscarServiciosForm" theme="simple">
	<table>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td class="ui-widget-content2">
						<div class="formpanel">
						<table>
							<tr>
								<td class="label">Nombre</td>
								<td><s:textfield them="simple" id="txtNombre"
								 name="servicioBuscar.nombre"/> </td>
								<td class="label">Url</td>
								<td><s:textfield theme="simple" id="txtUrl"
								name="servicioBuscar.url"/> </td>
								<td class="label">Estado</td>
								<td><s:select
									list="#{'':'TODOS','1':'OK','0':'ERROR'}" theme="simple"
									id="cmbEstado" name="servicioBuscar.estado_serv"/> </td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
						</div>
						</td>
					</tr>
					<tr>
						<td><s:submit value="Buscar" id="btnServicio" theme="simple" target="dataTable"
							cssClass="search-form ui-button ui-widget ui-state-default ui-corner-all"
						/>&nbsp;
						<s:submit value="Nuevo Servicio" id="btnNuevo" theme="simple"
							cssClass="ui-button ui-widget ui-state-default ui-corner-all"
							action="newServicio"/>&nbsp;
						<input type="button" value="Limpiar" id="btnLimpiar" 
							class="ui-button ui-widget ui-state-default ui-corner-all"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</s:form>
<div style="width:100%; padding:10px 10px 10px 10px;">
	<table id="dataTable" width="100%">
	<div id="tablePager"></div>
	</table>
</div>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function() {
   	   	$("thead tr th").attr("class","standardTable_Header_footer");
	   	$("caption").attr("class","standardTable_Header_footer caption");
	   	$("#btnLimpiar").click(function(){
    			limpiarForm();
    	});
    	
    	jQuery("#dataTable").jqGrid({
		   	url:'./findAllServicio.do',
			datatype: "json",
		   	colNames:['Id','Nombre','Url','Estado'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true},
		   		{name:'nombre',index:'nombre', width:150,align:"center"},
		   		{name:'url',index:'url', width:400,align:"center"},
		   		{name:'estado',index:'estado', width:50,align:"center"},
		   	],
		   	rowNum:10,
		   	rowList:[10,25,50],
		   	pager: '#tablePager',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"Lista de Servicios",
		    jsonReader : {
		      root:"dataModel",
		      repeatitems: false
		   },beforeRequest: function(){
		   		var _page=$(this).jqGrid('getGridParam', 'page');
		   		
		   		if(_page!=1){
					var _url=$(this).jqGrid('getGridParam', 'url');
					var _idx=_url.indexOf('?');
					
			 		$(this).jqGrid('setGridParam',{url:_url,page:_page});
				}
				
		   }
		   ,
			gridComplete: function(){
			  
		
				var ids = $(this).jqGrid('getDataIDs');
		
				for(var i=0;i < ids.length;i++){
					var cl = ids[i];
					var estado = $(this).getCell(cl, 'estado');       		
					var actions ='&nbsp;&nbsp;'; 
					 actions += actionIcon('updateServicio.do','idServicio='+cl,'Editar Servicio','images/icons/editar.png'); 
					 actions +='&nbsp;';
					 if(estado !='0' ){
						 actions += actionIconConfirm('deleteServicio.do','idServicio='+cl,'Eliminar Servicio','images/icons/eliminar.png','Esta seguro de anular el servicio');
					 } 
					 $(this).jqGrid('setRowData',ids[i],{act:actions});
				}	
				//dataTable_estado : concatenacion de id de Grid y el id de columna
				paintEstado('dataTable_estado');
			}
		});
    	
    	
	});
	
	function limpiarForm(){
		$("#txtNombre").val("");
		$("#txtUrl").val("");
		$("#cmbEstado").val("");
	}
</script>