<%@taglib prefix="s" uri="/struts-tags"%>
<s:form action="findAllServicio" id="buscarServiciosForm" theme="simple">
	<table width="100%">
		<tr>
			<td>
				<div id="pageTitle">Listado de Servicios</div>
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
								<td class="label"> Ambiente:</td>
								<td><s:select theme="simple" id="cmbAmbiente"
								name="servicioBuscar.tipoAmbiente.id" list="listaTiposAmbiente"
								listKey="value" listValue="label" /></td>
							</tr>
							<tr>
								<td class="label"> Aplicativo:</td>
								<td><s:select theme="simple" id="cmbAplicativo" 
									name="servicioBuscar.tipoAplicativo.id" list="listaTiposAplicativo" 
									listKey="value" listValue="label"/> </td>
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
							cssClass="ui-button ui-widget ui-state-default ui-corner-all" action="newServicio" /> &nbsp;
						<input type="button" value="Limpiar" id="btnLimpiar" 
							class="ui-button ui-widget ui-state-default ui-corner-all"/>&nbsp;
							
						<s:submit value="Actualizar" id="btnActualizar" theme="simple"
							cssClass="ui-button ui-widget ui-state-default ui-corner-all" action="refreshAllServicio"/> &nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</s:form>
<div style="width:100%; padding:10px 10px 10px 10px;">
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
		   	url:'./findAllServicio.do',
			datatype: "json",
		   	colNames:['','Id','Nombre','Url','Ambiente','Aplicativo','Estado','Acciones'],
		   	colModel:[
		   	    {name:'selected',index:'selected',width:30,
// 					editable:true,edittype:"select",editoptions:{value:"True:False"},
		   	    	formatter:"checkbox",
		   	    	formatoptions:{disabled:false},
					align:"center"
		   	    },
		   		{name:'id',index:'id',hidden:true},
		   		{name:'nombre',index:'nombre', width:120,align:"center"},
		   		{name:'url',index:'url', width:350,align:"center"},
		   		{name:'tipoAmbiente.descripcion',index:'tipoAmbiente.descripcion',width:120,align:"center"},
		   		{name:'tipoAplicativo.descripcion',index:'tipoAplicativo.descripcion',width:120,align:"center"},
		   		{name:'estado_serv',index:'estado_serv', width:50,align:"center"},
		   		{name:'act',index:'act',align:"left",width:150,sortable:false,title:false}
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
		   gridObtenerId:function(){
			   var servicio=jQuery('#dataTable').jqGrid('getRowData',rowid);
			   <% %>
		   }
		   ,
			gridComplete: function(){
			  
		
				var ids = $(this).jqGrid('getDataIDs');
		
				for(var i=0;i < ids.length;i++){
					var cl = ids[i];
					var estado_serv = $(this).getCell(cl, 'estado_serv');       		
					var actions ='&nbsp;&nbsp;'; 
					 actions += actionIcon('updateServicio.do','idServicio='+cl,'Editar Servicio','images/icons/editar.png'); 
					 actions +='&nbsp;';
					 actions += actionIcon('refreshServicio.do','idServicio='+cl,'Actualizar Estado Servicio','images/icons/actualizar.png');
// 					 if(estado_serv!='0'){
					 actions +='&nbsp;';
					 actions +=actionIcon('mensajeServicio.do','idServicio='+cl,'Mensaje de Error','images/icons/alerta.png');
// 					 }
					 $(this).jqGrid('setRowData',ids[i],{act:actions});
				}	
				//dataTable_estado_serv : concatenacion de id de Grid y el id de columna
				paintEstado_serv('dataTable_estado_serv');
			}
		});
	});
	
	function limpiarForm(){
		$("#cmbAmbiente").val("");
		$("#cmbAplicativo").val("");
	}
</script>