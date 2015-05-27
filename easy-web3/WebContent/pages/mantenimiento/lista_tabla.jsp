<%@taglib prefix="s" uri="/struts-tags"%> 

<s:form action="findAllTabla" id="consultaTablaForm" theme="simple">
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
							<td class="label">Nombre:</td>
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
					<td><s:submit value="Buscar" id="btnTabla" theme="simple"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all" />&nbsp;
					<s:submit value="Nueva Tabla" id="btnNuevo" theme="simple"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all"
						action="newTabla" /> &nbsp;
					<input type="button" value="Limpiar"
						id="btnLimpiar"
						class="ui-button ui-widget ui-state-default ui-corner-all" /></td>
				</tr>
				<tr>
					<td><s:if test="listaTablasPadre==null">
						<div class="my_div">
						<table id="tb">
							<caption>Lista de Tablas</caption>
							<thead>
								<tr>
									<th>Nombre</th>
									<th>Descripci&oacute;n</th>
									<th>Estado</th>
									<th>Editar</th>
									<th>Eliminar</th>
								</tr>
							</thead>
						</table>
						</div>
					</s:if> <s:else>
						<div>
						<div class="my_div"><display:table id="listaTablas"
							name="listaTablasPadre" uid="tb" pagesize="10" export="false"
							requestURI="/findAllTabla.do" class="ui-widget ui-widget-content">
							<display:caption>Lista de Tablas</display:caption>
							<display:column title="Nombre" property="abreviado" />
							<display:column title="Descripción" property="descripcion" />
							<display:column title="Estado" style="text-align: center;">
								<s:if test="#attr.tb.estado==1">
									<img src="images/icons/activo.png" title="ACTIVO" alt="ACTIVO"
										border="0" />
								</s:if>
								<s:elseif test="#attr.tb.estado==0">
									<img src="images/icons/inactivo.png" title="INACTIVO"
										alt="INACTIVO" border="0" />
								</s:elseif>
							</display:column>
							<display:column title="Editar" style="text-align: center;"
								url="/updateTabla.do" paramId="idTabla" paramProperty="id">
								<img src="images/icons/editar.png" title="MODIFICAR"
									alt="MODIFICAR" border="0" />
							</display:column>
							<display:column title="Eliminar" style="text-align:center;"
								url="/deleteTabla.do" paramId="idTabla" paramProperty="id">
								<s:if test="#attr.tb.estado==1">
									<img src="images/icons/eliminar.png" title="ELIMINAR"
										alt="ELIMINAR" border="0" onclick="return confirmDelete();" />
								</s:if>
							</display:column>
							<display:setProperty name="paging.banner.placement"
								value="bottom" />
						</display:table></div>
						</div>
					</s:else></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function() {
   	   	$("thead tr th").attr("class","standardTable_Header_footer");
	   	$("caption").attr("class","standardTable_Header_footer caption");
	   	$("#btnLimpiar").click(function(){
    			limpiarForm();
    	});
	});
	function limpiarForm(){
		$("#txtNombre").val("");
		$("#txtDescripcion").val("");
		$("#cmbEstado").val("");
	}
</script>



