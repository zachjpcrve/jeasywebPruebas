<%@taglib prefix="s" uri="/struts-tags"%>
 
<s:form action="saveParametro" id="parametroForm" theme="simple">
	<s:hidden name="parametro.id" />
	<s:hidden name="idParametro" />
	<s:hidden name="codigoAntiguo" />
	<table width="100%">
		<tr>
			<td>
			<table width="100%">
				<tr>
					<td>
					<div id="pageTitle">Par&aacute;metro</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="formpanel">
					<table cellspacing="0" width="100%">
						<tr>
							<td class="label">C&oacute;digo:</td>
							<td><s:textfield theme="simple" name="parametro.codigo"
								id="txtCodigo" maxlength="70" size="30"
								cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]" />
							</td>
						</tr>
						<tr>
							<td class="label">Descripci&oacute;n:</td>
							<td><s:textfield theme="simple" name="parametro.descripcion"
								id="txtDescripcion" maxlength="100" size="60"
								cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]" />
							</td>
						</tr>
						<tr>
							<td class="label">Valor:</td>
							<td><s:textfield theme="simple" name="parametro.valor"
								id="txtValor" maxlength="100" size="60"
								cssClass="validate[required]" />
							</td>
						</tr>
						<tr>
							<td class="label">Estado:</td>
							<td><s:select list="#{'1':'ACTIVO','0':'INACTIVO'}"
								theme="simple" id="cmbEstado" name="parametro.estado" /></td>
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
		$("#parametroForm").validationEngine();
		$("#btnLimpiar").click(function(){
			limpiarForm();
		});
	});
	
	function limpiarForm(){
		$("#txtCodigo").val("");
		$("#txtDescripcion").val("");
		$("#txtValor").val("");
		$("#cmbEstado").val("1");
	}
		
	function retornar(){
		$("#formAux").attr("action","./initParametro.do");
		$("#formAux").submit();
	}  
</script>
