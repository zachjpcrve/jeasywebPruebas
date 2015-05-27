<%@taglib prefix="s" uri="/struts-tags"%>

<s:form action="saveUsuario" id="usuarioForm" theme="simple">
	<s:hidden name="usuario.id" />
	<s:hidden name="idUsuario" />
	<s:hidden name="codigoAntiguo" />
	<table width="100%">
		<tr>
			<td>
			<table width="100%">
				<tr>
					<td>
					<div id="pageTitle">Usuario</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="formpanel">
					<table cellspacing="0" width="100%">
						<tr>
							<td class="label">C&oacute;digo:</td>
							<td><s:textfield theme="simple" name="usuario.codigo"
								id="txtCodigo" maxlength="20" size="8"
								cssClass="validate[required,custom[onlyLetterNumbereneacentoesp]]" />
							</td>
						</tr>
						<tr>
							<td class="label">Estado:</td>
							<td><s:select list="#{'1':'ACTIVO','0':'INACTIVO'}"
								theme="simple" id="cmbEstado" name="usuario.estado" /></td>
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
	<table width="100%">
		<tr>
			<td>
			<div id="pageTitle">Perfiles</div>
			&nbsp;</td>
		</tr>

		<tr>
			<td>

			<div id="divPerfiles" class="formpanel">

			<table class="ln_formatos" cellspacing="0">
				<tr>
					<td>
					<table border="0">
						<tr>
							<td rowspan="2"><label for="leftTitle">Perfiles
							Disponibles</label><br />
							<s:select id="idPerfil" name="selectedItemsIzquierdaPerfiles"
								list="listaPerfilesDisponibles" listKey="codigo"
								listValue="descripcion" headerKey="999999"
								headerValue="---------------------" size="15" multiple="true"
								cssStyle="height:200px;width:200">
							</s:select> <input type="hidden" id="__multiselect_idPerfil"
								name="__multiselect_listaPerfil" value="" /></td>
							<td valign="middle" align="center"><br />
							<br />
							<s:submit value=">>>" action="moveToRightAllPerfil"
								id="idMoverTodoDerecha"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />
							<s:submit value=">>" action="moveToRightPerfil"
								id="idMoverDerecha"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />
							<s:submit value="<<" action="moveToLeftPerfil"   id="idMoverIzquierda"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />
							<s:submit value="<<<" action="moveToLeftAllPerfil"  
							id="idMoverTodoIzquierda"
								cssClass="ui-button ui-widget ui-state-default ui-corner-all" /><br />
							<br />

							<br />
							<br />
							</td>
							<td rowspan="2"><label for="rightTitle">Perfiles
							Seleccionadas</label><br />
							<s:select id="idPerfilSelect" name="selectedItemsDerechaPerfiles"
								size="15" list="listaPerfliesSeleccionados" listKey="codigo"
								listValue="descripcion" headerKey="999999"
								headerValue="---------------------" multiple="true"
								cssStyle="height:200px;width:200">
							</s:select> <input type="hidden" id="__multiselect_idPerfilSelect"
								name="__multiselect_listaPerfilSelect" value="" /></td>
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
		
		$("#idMoverDerecha").attr("disabled",true);
		$("#idMoverIzquierda").attr("disabled",true);
	
    	$("#usuarioForm").validationEngine();
    	
    	$("#btnLimpiar").click(function(){
    			limpiarForm();
    		});
    	$("#btRetornar").click(function(){
    			retornar();
    		});
    		
    	$("#idPerfil").click(function(){
    		if($("#idPerfil").val()!="999999"){
    		$("#idMoverDerecha").attr("disabled",false);
			}else{
    		$("#idMoverDerecha").attr("disabled",true);
    		}
    	});	
    		
    	$("#idPerfilSelect").click(function(){
    		if($("#idPerfilSelect").val()!="999999"){
    		$("#idMoverIzquierda").attr("disabled",false);
    		}else{
    		$("#idMoverIzquierda").attr("disabled",true);
    		}	
    	});	
   	});
	function limpiarForm(){
	  		$("#txtCodigo").val("");
	  		$("#cmbEstado").val("1");
	}
	function retornar(){
	   $("#formAux").attr("action","./initUsuario.do");
	   $("#formAux").submit();
	}
	
	
</script>
