<%@taglib prefix="s" uri="/struts-tags"%> 

<s:form action="findAllTablaValor" id="consultaTablaValorForm"
	theme="simple">
	<table width="100%">
		<tr>
			<td>
			<div id="pageTitle">Listado de Tablas</div>
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
							<td class="label">Padre:</td>
							<td><s:select key="tablaBuscar.padre.id" id="cmbTablaPadre"
								list="itemTablasPadre" listKey="value" listValue="label"
								theme="simple" /></td>
							<td class="label">Abreviatura:</td>
							<td><s:textfield theme="simple" id="txtNombre"
								name="tablaBuscar.abreviado" /></td>
							<td class="label">Descripci&oacute;n:</td>
							<td><s:textfield theme="simple" id="txtDescripcion"
								name="tablaBuscar.descripcion" /></td>
							<td class="label">Estado:</td>
							<td><s:select
								list="#{'':'TODOS','1':'ACTIVO','0':'INACTIVO'}" theme="simple"
								id="cmbEstado" name="tablaBuscar.estado" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				<tr>
					<td><s:submit value="Buscar" id="btnBuscar" theme="simple" target="dataTable"
						cssClass="search-form ui-button ui-widget ui-state-default ui-corner-all" />&nbsp;
					<s:submit value="Nuevo Valor de Tabla" id="btnNuevo" theme="simple"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all"
						action="newTablaValor" /> &nbsp;<input type="button"
						value="Limpiar" id="btnLimpiar"
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
   	url:'./findAllTablaValor.do',
	datatype: "json",
   	colNames:['ID','Padre','Codigo','Abreviatura','Descripcion','Estado','Acciones'],
   	colModel:[
   		{name:'id',index:'id',hidden:true},
   		{name:'padre.descripcion',index:'padre.descripcion', width:180,align:"center"},
   		{name:'codigo',index:'codigo', width:180,align:"center"},
   		{name:'abreviado',index:'abreviado', width:180,align:"center"},
   		{name:'descripcion',index:'descripcion', width:180,align:"center"},
   		{name:'estado',index:'estado', width:50,align:"center"},
   		{name:'act',index:'act',align:"left", width:73,sortable:false,title:false}
   	],
   	rowNum:10,
   	rowList:[10,25,50],
   	pager: '#tablePager',
   	sortname: 'id',
    viewrecords: true,
    sortorder: "desc",
    caption:"Listado de Tablas",
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
			 actions += actionIcon('updateTablaValor.do','idTabla='+cl,'Editar Tabla','images/icons/editar.png'); 
			 actions +='&nbsp;';
			 if(estado !='0' ){
				 actions += actionIconConfirm('deleteTablaValor.do','idTabla='+cl,'Eliminar Tabla','images/icons/eliminar.png','Esta seguro de anular el registro');
			 } 
			 $(this).jqGrid('setRowData',ids[i],{act:actions});
		}	
		//dataTable_estado : concatenacion de id de Grid y el id de columna
		paintEstado('dataTable_estado');
	}
});
    	
    	
    	
    	
    	
    	
	});
	
	function limpiarForm(){
		$("#cmbTablaPadre").val("");
		$("#txtNombre").val("");
		$("#txtDescripcion").val("");
		$("#cmbEstado").val("");
	}
</script>