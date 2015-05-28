<%@taglib prefix="s" uri="/struts-tags"%>

<s:form action="savePerfil" id="perfilForm" theme="simple">
	<s:hidden name="perfil.id" />
	<s:hidden name="idPerfil" />
	<s:hidden name="codigoAntiguo" />
	<table width="100%">
		<tr>
			<td>
			<table width="100%">
				<tr>
					<td>
					<div id="pageTitle">Perfil</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="formpanel">
					<table cellspacing="0" width="100%">
						<tr>
							<td class="label">C&oacute;digo:</td>
							<td><s:textfield theme="simple" name="perfil.codigo"
								id="txtCodigo" maxlength="6" size="30"
								cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]" />
							</td>
						</tr>
						<tr>
							<td class="label">Descripci&oacute;n:</td>
							<td><s:textfield theme="simple" name="perfil.descripcion"
								id="txtDescripcion" maxlength="100" size="60"
								cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]" />
							</td>
						</tr>
						<tr>
							<td class="label">Estado:</td>
							<td><s:select list="#{'1':'ACTIVO','0':'INACTIVO'}"
								theme="simple" id="cmbEstado" name="perfil.estado" /></td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td><s:submit theme="simple" value="Guardar" id="btnGuardar"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all">
					</s:submit>&nbsp; <input type="button" value="Limpiar" id="btnLimpiar"
						class="ui-button ui-widget ui-state-default ui-corner-all" />&nbsp;
					<input type="button" value="Retornar" id="btnRetornar"
						onclick="retornar()"
						class="ui-button ui-widget ui-state-default ui-corner-all" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td>
			<div id="pageTitle">M&oacute;dulos</div>
			&nbsp;</td>
		</tr>

		<tr>
			<td>

			<div id="divModulos">

			<table class="ln_formatos" cellspacing="0">
				<tr>
					<td>
					<table border="0">
						<tr>
							<td rowspan="2"><label for="leftTitle">M&oacute;dulos
							Disponibles</label><br />
							<s:select id="idPerfil" name="selectedItemsIzquierdaModulos"
								list="modulosDisponibles" listKey="codigo"
								listValue="descripcion" headerKey="999999"
								headerValue="---------------------" size="15" multiple="true"
								cssStyle="height:200px;width:200">
							</s:select> <input type="hidden" id="__multiselect_idModulo"
								name="__multiselect_listaModulo" value="" /></td>
							<td valign="middle" align="center"><br />
							<br />
							<s:submit value=">>>" action="moveToRightAllModulo"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />
							<s:submit value=">>" action="moveToRightModulo"
								id="idMoverDerecha"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />
							<s:submit value="<<" action="moveToLeftModulo"
								id="idMoverIzquierda"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />
							<s:submit value="<<<"
								action="moveToLeftAllModulo"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />

							<br />
							<br />
							</td>
							<td rowspan="2"><label for="rightTitle">M&oacute;dulos
							Seleccionadas</label><br />
							<s:select id="idModuloSelect" name="selectedItemsDerechaModulos"
								size="15" list="modulosSeleccionados" listKey="codigo"
								listValue="descripcion" headerKey="999999"
								headerValue="---------------------" multiple="true"
								cssStyle="height:200px;width:200">
							</s:select> <input type="hidden" id="__multiselect_idModuloSelect"
								name="__multiselect_listaModuloSelect" value="" /></td>
						</tr>
					</table>

					</td>
				</tr>
			</table>
			<br />
			</div>

			</td>
		</tr>



	</table>
</s:form>
<script language="JavaScript" type="text/javascript">	
	$(document).ready(function(){
    	$("#perfilForm").validationEngine();
    	$("#idMoverDerecha").attr("disabled",true);
		$("#idMoverIzquierda").attr("disabled",true);
		$("#idMoverDerechaAlmacen").attr("disabled",true);
		$("#idMoverIzquierdaAlmacen").attr("disabled",true);
    	
		$("#btnLimpiar").click(function(){
			limpiarForm();
		});

	    $("#idPerfil").click(function(){
    		if($("#idPerfil").val()!="999999"){
    			$("#idMoverDerecha").attr("disabled",false);
			}else{
    			$("#idMoverDerecha").attr("disabled",true);
    		}
    	});	

    	$("#idModuloSelect").click(function(){
    		if($("#idModuloSelect").val()!="999999"){
	    		$("#idMoverIzquierda").attr("disabled",false);
    		}else{
    			$("#idMoverIzquierda").attr("disabled",true);
    		}	
    	});	
   	});
   	
	function limpiarForm(){
		$("#txtCodigo").val("");
		$("#txtDescripcion").val("");
		$("#cmbEstado").val("1");
	  	}
	function retornar(){
	    $("#formAux").attr("action","./initPerfil.do");
		$("#formAux").submit();
	}  
</script>