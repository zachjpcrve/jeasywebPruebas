<%@taglib prefix="s" uri="/struts-tags"%>
 
<s:form action="saveTabla" id="tablaForm" theme="simple">
	<s:hidden name="tabla.id" />
	<s:hidden name="idTabla" />
	<table width="100%">
		<tr>
			<td>
			<table width="100%">
				<tr>
					<td>
					<div id="pageTitle">Tabla</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="formpanel">
					<table cellspacing="0" width="100%">
						<tr>
							<td class="label"><label>Nombre:</label></td>
							<td><s:textfield theme="simple" name="tabla.abreviado"
								id="txtNombre" maxlength="70" size="30"
								cssClass="validate[required],custom[onlyLetterNumbereneacentoesp]" />
							</td>
						</tr>
						<tr>
							<td class="label">Descripci&oacute;n:</td>
							<td><s:textfield theme="simple" name="tabla.descripcion"
								id="txtDescripcion" maxlength="100" size="30"
								cssClass="validate[required],custom[onlyLetterNumbereneacentoesp]" />
							</td>
						</tr>
						<tr>
							<td class="label">Estado:</td>
							<td><s:select list="#{'1':'ACTIVO','0':'INACTIVO'}"
								theme="simple" id="cmbEstado" name="tabla.estado" /></td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td><s:submit theme="simple" value="Guardar" id="btnGuardar"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all"></s:submit>&nbsp;
					<input type="button" value="Limpiar" id="btnLimpiar"
						class="ui-button ui-widget ui-state-default ui-corner-all" />&nbsp;
					<input type="button" value="Retornar" id="btnRetornar"
						onclick="retornar()"
						class="ui-button ui-widget ui-state-default ui-corner-all" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
<script language="JavaScript" type="text/javascript">	
	$(document).ready(function(){
	   $("#tablaForm").validationEngine();
	   $("#btnLimpiar").click(function(){
	    			limpiarForm();
	    	});
	});

	function limpiarForm(){
		$("#txtNombre").val("");
		$("#txtDescripcion").val("");
		$("#cmbEstado").val("1");
	}
	
   function retornar(){
		$("#formAux").attr("action","./findAllTabla.do");
		$("#formAux").submit();
	}  
</script>
