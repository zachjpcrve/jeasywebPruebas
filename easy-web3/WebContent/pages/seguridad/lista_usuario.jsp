<%@taglib prefix="s" uri="/struts-tags"%>

<s:form action="findAllUsuario" id="buscarUsuariosForm" theme="simple">
	<table width="100%">
		<tr>
			<td>
			<div id="pageTitle">Lista de Usuarios</div>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%">
				<tr>
					<td class="ui-widget-content2">
					<div class="formpanel">
					<table>
						<tr>
							<td class="label">C&oacute;digo:</td>
							<td><s:textfield theme="simple" id="txtCodigo"
								name="usuarioBuscar.codigo" /></td>
							<td class="label">Estado:</td>
							<td><s:select
								list="#{'':'TODOS','1':'ACTIVO','0':'INACTIVO'}" theme="simple"
								id="cmbEstado" name="usuarioBuscar.estado" /></td>
						</tr>

					</table>
					</div>
					</td>
				</tr>
				<tr>
					<td><s:submit value="Buscar" id="btnUsuario" theme="simple" target="dataTable"
						cssClass="search-form ui-button ui-widget ui-state-default ui-corner-all" />&nbsp;
					<s:submit value="Nuevo Usuario" id="btnNuevo" theme="simple"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all"
						action="newUsuario" /> &nbsp;<input type="button" value="Limpiar"
						id="btnLimpiar"
						class="ui-button ui-widget ui-state-default ui-corner-all" /></td>
				</tr>
				
			</table>
			</td>
		</tr>
	</table>
</s:form>
<div style="width: 100%; padding: 10px 10px 10px 10px;">
<table id="dataTable" width="100%"></table>
 <div id="tablePager"></div>
</div> 
<script language="JavaScript" type="text/javascript">
	$(document).ready(function() {
   	   	$("thead tr th").attr("class","standardTable_Header_footer");
	   	$("caption").attr("class","standardTable_Header_footer caption");
	   	$("#btnLimpiar").click(function(){
    			limpiarForm();
    	});
    	
    	 jQuery("#dataTable").jqGrid({
		   	url:'./findAllUsuario.do',
			datatype: "json",
		   	colNames:['ID','Código','Estado','Acciones'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true},
		   		{name:'codigo',index:'codigo', width:300,align:"center"},
		   		{name:'estado',index:'estado', width:50,align:"center"},
   				{name:'act',index:'act',align:"left", width:150,sortable:false,title:false}
		   	],
		   	rowNum:10,
		   	rowList:[10,25,50],
		   	pager: '#tablePager',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"Listado de Usuarios",
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
					 actions += actionIcon('updateUsuario.do','idUsuario='+cl,'Editar Usuario','images/icons/editar.png'); 
					 actions +='&nbsp;';
					 if(estado !='0' ){
						 actions += actionIconConfirm('deleteUsuario.do','idUsuario='+cl,'Eliminar Usuario','images/icons/eliminar.png','Esta seguro de anular el registro');
					 } 
					 $(this).jqGrid('setRowData',ids[i],{act:actions});
				}	
				//dataTable_estado : concatenacion de id de Grid y el id de columna
				paintEstado('dataTable_estado');
			}
		});
		
	});
	
	function limpiarForm(){
		$("#txtCodigo").val("");
		$("#cmbEstado").val("");
	}
</script>