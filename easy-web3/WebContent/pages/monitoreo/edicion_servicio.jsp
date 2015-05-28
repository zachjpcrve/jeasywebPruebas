<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="pe.com.bbva.util.Constantes" %>
<s:form action="saveServicio" id="servicioForm" them="simple">
	<s:hidden name="servicio.id"></s:hidden>
	<s:hidden name="idServicio"></s:hidden>
	<s:hidden name="urlAntiguo"></s:hidden>
	<table width="100%">
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td>
						<div id="pageTitle">Servicio</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="formpanel">
								<table cellspacing="0" width="100%">
									<tr>
										<td class="label">Direccion Url:</td>
										<td><s:textfield theme="simple" name="servicio.url"
											id="txtUrl" maxlength="60" size="100"
											cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]"
											/>
										</td>
									</tr>
									<tr>
										<td class="label">Tipo Ambiente:</td>
										<td><s:select id="cmbTipoAmbiente" name="servicio.tipoAmbiente.id"
											list="listaTiposModulo" listKey="value" listValue="label"
											cssClass="validate[required]"/>
										</td>
									</tr>
									<tr>
										<td class="label">Nombre</td>
										<td><s:textfield theme="simple" name="servicio.nombre"
										id="txtNombre" maxlength="60" size="80"
										cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]"/>
										</td>
									</tr>
									<tr>
										<td class="label">Requiere Autentificaci&oacute;n</td>
										<td><s:select list="#{'1':'SI','0':'NO'}"
											them="simple" id="cmbReq_Aut" name="servicio.req_aut"/>
										</td>
									</tr>
									<tr>
										<td class="label">Usuario:</td>
										<td><s:textfield theme="simple" name="servicio.usuario"
										id="txtUsuario" maxlength="20" size="40"
										cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]"/>
										</td>
									</tr>
									<tr>
										<td class="label">Clave:</td>
										<td><s:password theme="simple" name="servicio.clave"
										id="pswClave" maxlength="8" size="10"
										cssClass="validate[required,custom[]onlyLetterNumber]"/>
										</td>
									</tr>
									<tr>
										<td class="label">Descripci&oacute;n:</td>
										<td><s:textfield theme="simple" name="servicio.descripcion_serv"
										id="txtDescripcion" maxlength="80" size="80"
										cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]"/>
										</td>
									</tr>
									<tr>
										<td class="label">Aplicativo:</td>
										<td><s:textfield theme="simple" name="servicio.aplicativo"
										id="txtAplicativo" maxlength="50" size="50"
										cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]"/>
										</td>
									</tr>
								</table>
							</div>
						</td>
					<tr>
						
					</tr>
				</table>
				<table>
					<tr>
						<td><s:submit theme="simple" value="Guardar" id="btnGuardar"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all"></s:submit>&nbsp;
						<input type="button" value="limpiar" id="btnLimpiar"
							class="ui-button ui-widget ui-state-default ui-corner-all"/>&nbsp;
						<input type="button" value="Retornar" id="btnRetornar"
							onclick="retornar()"
							class="ui-button ui-widget ui-state-default ui-corner-all"/>
						 </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</s:form>
<script language="JavaScript" type="text/javascript">	
	$(document).ready(function(){
		$("#servicioForm").validationEngine();
		$("#btnLimpiar").click(function(){
			limpiarForm();
		});
    	$("#cmbReq_Aut").change(function(){
    			if($("#cmbReq_Aut").val()=="<%=Constantes.VAL_TIPO_SERVICIO_STRING%>" ){
    			//Habilitando Campos
    			$("#txtUsuario").attr("disabled",true);
    			$("#txtClave").attr("disabled",true);
    			}else{
    			$("#txtUsuario").attr("disabled",false);
    			$("#txtClave").attr("disabled",false);
    			}
    	});
    	$("#cmbReq_Aut").trigger('change');
	});


	function limpiarForm(){
		$("#txtUrl").val("");
		$("#cmbTipoAmbiente").val("");
		$("#txtNombre").val("");
		$("#cmbReq_Aut").val("0");
		$("#txtUsuario").val("");
		$("#txtClave").val("");
		$("#txtDescripcion").val("");
		$("#txtAplicativo").val("");
	}
	function retornar(){
		$("#formAux").attr("action","./initMonitoreo.do");
		$("#formAux").submit();
	}  
</script>