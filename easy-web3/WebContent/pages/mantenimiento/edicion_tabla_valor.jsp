<%@taglib prefix="s" uri="/struts-tags"%>
 
<s:form action="saveTablaValor" id="tablaValorForm" theme="simple">
	<s:hidden name="idTabla" />
	<s:hidden name="tabla.id" />
	<table width="100%">
		<tr>
			<td>
			<table width="100%">
				<tr>
					<td>
					<div id="pageTitle"> Tabla</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="formpanel">
					<table cellspacing="0" width="100%">
						<tr>
							<td class="label">Tipo:</td>
							<td>
								 <s:radio name="tipoTabla" list="#{'1':'Padre','2':'Hijo'}" />
							</td>
						</tr>
						<tr>
							<td class="label">Padre:</td>
							<td><s:select key="tabla.padre.id" id="tipTablaPadre"
								list="itemNuevoTablaPadre" listKey="value" listValue="label"
								theme="simple" cssClass="validate[required]" />
								<input id="btnBusqTabla" type="button" style="width:30px" value=".." 
								class="ui-button ui-widget ui-state-default ui-corner-all"/>
							</td>
						</tr>
						<tr>
							<td class="label">C&oacute;digo:</td>
							<td><s:textfield theme="simple" name="tabla.codigo"
								id="txtCodigo" maxlength="70" size="30"
								cssClass="validate[required]" /></td>
						</tr>
						<tr>
							<td class="label">Abreviado:</td>
							<td><s:textfield theme="simple" name="tabla.abreviado"
								id="txtNombre" maxlength="70" size="30"
								cssClass="validate[required]" />
							</td>
						</tr>
						<tr>
							<td class="label">Descripci&oacute;n</td>
							<td><s:textfield theme="simple" name="tabla.descripcion"
								id="txtDescripcion" maxlength="100" size="60"
								cssClass="validate[required]" />
							</td>
						</tr>
						<tr>
							<td class="label">Estado</td>
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
					<td><s:submit theme="simple" value="Guardar"
						cssClass="ui-button ui-widget ui-state-default ui-corner-all"></s:submit>&nbsp;
					<input type="button" value="Limpiar" id="cbLimpiar"
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

<!-- Modal para Busqueda Tabla-->
<div id="dModalBusqTabla" title="Buscar" style="display:none">
	 <s:form action="buscarTablaDetalle" method="post" theme="simple" name="frmModalBusqTabla">
	 	
		<table width="100%"  >
			<tr>
			<td class="ui-widget-content2">
						<div class="formpanel">
						<table>		
									<tr>		
									<td>	
										C&oacute;digo:
									</td>
									<td>	
										<s:textfield theme="simple" id="txtCodigo" name="codigo" size="35"/>
									</td>
									<td>	
										Abreviado:
									</td>
									<td>	
										<s:textfield theme="simple" id="txtAbreviado" name="abreviado" size="35"/>
									</td>
									</tr>
									<tr>		
									<td>	
										Descripci&oacute;n:
									</td>
									<td colspan="3">	
										<s:textfield theme="simple" id="txtDescripcion" name="descripcion" size="90"/>
									</td>
									</tr>			
						</table>
						</div>
				   </td>
				</tr>
				
				<tr>
					<td>
						<input type="button" value="Buscar" id="idBuscarTabla"  class="ui-button ui-widget ui-state-default ui-corner-all"/>
					</td>
				</tr>
				<tr>
									<td>
									 <div  class="my_div" id="idDivTabla">
											
									</div>
								</td>
				</tr>				
			</table>			
				
	 </s:form>
</div>
<script language="JavaScript" type="text/javascript">	
	$(document).ready(function(){
		$("#tablaValorForm").validationEngine();
		$("#cbLimpiar").click(function(){
			limpiarForm();
		});
		
		  validaRadioButton();
		  
		$("input[name=tipoTabla]:radio").click(function(){
		  
			validaRadioButton();
		});
		
		$("#btnBusqTabla").click(function(){
		
			if($("input[name=tipoTabla]:radio:checked").val()==2){
				$( "#dModalBusqTabla" ).dialog({	height: 400,
																	width: 600,
																	modal: true,
																	title: 'Buscar Tabla'
																});
			}else{
				alert("El tipo tiene que ser \"HIJO\"");
			}													
		}); 
		
		/*Ini Boton Buscar del Modal Tabla*/
					$("#idBuscarTabla").click(function () { 
				   			$.blockUI({message:  '<h3>Cargando Espere por favor...</h3>', 
				   					  overlayCSS: { backgroundColor: '#0174DF' } }); 
				   			
					   		$.post("buscarTablaDetalle.do", { codigo: $("#txtCodigo").val(),
					   								       abreviado: $("#txtAbreviado").val(),
					   								       descripcion: $("#txtDescripcion").val()},
						   		function(data){
						   	    				setTimeout($.unblockUI, 1);  
						   						$("#idDivTabla").html(data);
												$("thead tr").attr("class","ui-widget-header");
												
									$("#txtCodigo").val("");
					                $("#txtAbreviado").val("");
					   				$("#txtDescripcion").val("");			
				  			     	
						   	});
						});
		/*Fin Boton Buscar del Modal Tabla*/
		
		
		 
	});
	
	function validaRadioButton(){
		if($("input[name=tipoTabla]:radio:checked").val()==1){
			$("#tipTablaPadre").attr('disabled',true);
		}else{
			$("#tipTablaPadre").attr('disabled',false);
		}
	}
	
	function limpiarForm(){
		$("#txtCodigo").val("");
		$("#tipTablaPadre").val("");
		$("#txtNombre").val("");
		$("#txtDescripcion").val("");
		$("#cmbEstado").val("1");
	}
	
	function retornar(){
		$("#formAux").attr("action","./initTablaValor.do");
		$("#formAux").submit();
	}  
	
	function respModTabla(codTabla,desTabla){
	var options="";
	options = "<option value=\""+codTabla+"\">"+desTabla+"</option>";
	$("#tipTablaPadre").html(options);
	$("#dModalBusqTabla").dialog("close");
	}
</script>
