<%@taglib prefix="s" uri="/struts-tags"%>

<%@page import="pe.com.bbva.util.Constantes"%>
<s:form action="saveModulo" id="moduloForm" theme="simple">
	<s:hidden name="modulo.id" />
	<s:hidden name="idModulo" />
	<s:hidden name="codigoAntiguo" />
	<table width="100%">
		<tr>
			<td>
			<table width="100%">
				<tr>
					<td>
					<div id="pageTitle">M&oacute;dulo</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="formpanel">
					<table cellspacing="0" width="100%">
						<tr>
							<td class="label">C&oacute;digo:</td>
							<td><s:textfield theme="simple" name="modulo.codigo"
								id="txtCodigo" maxlength="6" size="30"
								cssClass="validate[required,custom[onlyLetterNumber]]" /></td>
						</tr>
						<tr>
							<td class="label">Descripci&oacute;n:</td>
							<td><s:textfield theme="simple" name="modulo.descripcion"
								id="txtDescripcion" maxlength="60" size="60"
								cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]" />
							</td>
						</tr>
						<tr>
							<td class="label">Acci&oacute;n:</td>
							<td><s:textfield theme="simple" name="modulo.controlador"
								id="txtControlador" maxlength="100" size="70"
								cssClass="validate[required]" /></td>
						</tr>
						<tr>
							<td class="label">Tipo M&oacute;dulo:</td>
							<td><s:select id="cmbTipoModulo" name="modulo.tipoModulo.id"
								list="listaTiposModulo" listKey="value" listValue="label"
								cssClass="validate[required]" /></td>
						</tr>
						<tr>
							<td class="label">Padre:</td>
							<td><s:select id="cmbPadre" name="modulo.superior.id"
								list="listaPadres" listKey="value" listValue="label" /></td>
						</tr>
						<tr>
							<td class="label">Orden:</td>
							<td><s:textfield id="txtOrden"
								onkeypress="return acceptNum(event);" name="modulo.orden"
								maxlength="3" cssClass="validate[required,custom[integer]]" /></td>
						</tr>
						<tr>
							<td class="label">Estado:</td>
							<td><s:select list="#{'1':'ACTIVO','0':'INACTIVO'}"
								theme="simple" id="cmbEstado" name="modulo.estado" /></td>
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
		$("#moduloForm").validationEngine();
		$("#btnLimpiar").click(function(){
			limpiarForm();
		});
    	$("#cmbTipoModulo").change(function(){
    			if($("#cmbTipoModulo").val()=="<%=Constantes.VAL_TIPO_MODULO_STRING%>" ){
    			
    			$("#txtControlador").attr("disabled",true);
    			$("#cmbPadre").attr("disabled",true);
    			}else{
    			$("#txtControlador").attr("disabled",false);
    			$("#cmbPadre").attr("disabled",false);
    			}
    	});
    	$("#cmbTipoModulo").trigger('change');
	});


	function limpiarForm(){
		$("#txtCodigo").val("");
		$("#txtDescripcion").val("");
		$("#txtControlador").val("");
		$("#txtMetodo").val("");
		$("#cmbTipoModulo").val("");
		$("#cmbPadre").val("");
		$("#txtOrden").val("");
		$("#cmbEstado").val("1");
	}
	function retornar(){
		$("#formAux").attr("action","./initModulo.do");
		$("#formAux").submit();
	}  
</script>
