package pe.com.bbva.mantenimiento.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.action.GenericAction;
import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.mantenimiento.bo.TablaBO;
import pe.com.bbva.mantenimiento.domain.Tabla;
import pe.com.bbva.util.ComboUtil;
import pe.com.bbva.util.Constantes;
import pe.com.bbva.util.SelectItem;

@Service("tablaAction")
@Scope("prototype")
@Results({
	@Result(name="viewFormTablaValor",type="tiles", location="viewFormTablaValor"),
	@Result(name="viewListTablaValor",type="tiles", location="viewListTablaValor"),
	@Result(name="viewListTabla",type="tiles", location="viewListTabla"),
	@Result(name="viewFormTabla",type="tiles", location="viewFormTabla"),
	@Result(name="listTabla",type="json",params={"root","grid"}),
})
public class TablaAction extends GenericAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private TablaBO tablaBO;
	
//	@Resource
//	private CuadroAsignacionBO cuadroAsignacionBO;

	private Long idTabla;
	private String codigoAntiguo;
	private Tabla tabla;
	private Tabla tablaBuscar;
	private String codigo;
	private String abreviado;
	private String descripcion;
	private String tipoTabla;
	
	
	private List<SelectItem> itemTablasPadre = new ArrayList<SelectItem>();
	private List<SelectItem> itemNuevoTablaPadre = new ArrayList<SelectItem>();
	private List<Tabla> listaTablasPadre;
	private List<Tabla> listaTablas;

	public void cleanForm() {
		setIdTabla(null);
		setCodigoAntiguo("");
		setTabla(null);
		setTablaBuscar(null);
	}

	@Action(value="initTabla")
	public String init() {
		try {
			
			tablaBuscar = new Tabla();
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListTabla";
	}
	
	@Action(value="buscarTablaDetalle")
	public void buscarTablaDetalle(){
		
		PrintWriter out=null;
		int contador=0;
		StringBuilder stb = new StringBuilder();
		Tabla tblBuscar=new Tabla();
		try {
			tblBuscar.setCodigo(codigo);
			tblBuscar.setAbreviado(abreviado);
			tblBuscar.setDescripcion(descripcion);
			tblBuscar.setEstado(Constantes.VAL_ACTIVO);
			
			List<Tabla> listTabla=tablaBO.findTablas(tblBuscar);
			
			getResponse().setContentType("text/html");   
	        out = getResponse().getWriter(); 
	        stb.append("<table  id=\"tbRemAlmacen\">");
		        stb.append("<thead>");	
			        stb.append("<tr>");
				        stb.append("<th>#</th>");
				        stb.append("<th>Elija</th>");
				        stb.append("<th>C&oacute;digo</th>");
				        stb.append("<th>Padre</th>");
				        stb.append("<th>Abreviatura</th>");
				        stb.append("<th>Descripci&oacute;n</th>");
				    stb.append("</tr>");
		        stb.append("</thead>");
	        stb.append("<tbody>");
		for(Tabla obj: listTabla){
			contador++;
			stb.append("<tr>");
			stb.append("<td>"+contador+"</td>");
			stb.append("<td><input type=\"button\" value=\"-\" id=\"idElijaTabla\" onclick=\"respModTabla('"+obj.getId()+"','"+(obj.getDescripcion()==null?"":obj.getDescripcion())+"')\" class=\"ui-button ui-widget ui-state-default ui-corner-all\"/></td>");
			stb.append("<td>"+(obj.getCodigo()==null?"":obj.getCodigo())+"</td>");
			stb.append("<td>"+(obj.getPadre()==null?"":obj.getPadre().getDescripcion())+"</td>");
			stb.append("<td>"+(obj.getAbreviado()==null?"":obj.getAbreviado())+"</td>");
			stb.append("<td>"+(obj.getDescripcion()==null?"":obj.getDescripcion())+"</td>");
			stb.append("</tr>");

		}
		 stb.append("</tbody>");
		 stb.append("</table>");
		
         out.print(stb.toString());
			
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (DAOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (IOException e) {
			logger.error(StringUtil.getStackTrace(e));
		}finally{
			if(out !=null) out.close();
		}
	}
	
	@Action(value="findAllTabla")
	public String findAll() {
		try {
			if (tablaBuscar == null) {
				tablaBuscar = new Tabla();
			}
			listaTablasPadre = tablaBO.findTablasPadre(tablaBuscar);
			if (listaTablasPadre.isEmpty()) {
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListTabla";
	}
	
	@Action(value="newTabla")
	public String newForm() {
		return "viewFormTabla";
	}
	
	@Action(value="updateTabla")
	public String updateForm() {
		try {
			tabla = tablaBO.findTablaById(idTabla);
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormTabla";
	}
	
	@Action(value="deleteTabla")
	public String delete() {
		try {
			tablaBO.deleteLog(Tabla.class, idTabla);
			addActionMessage("Eliminado Correctamente.");
			if (tablaBuscar == null) {
				tablaBuscar = new Tabla();
			}
			listaTablasPadre = tablaBO.findTablasPadre(tablaBuscar);
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListTabla";
	}
	
	@Action(value="saveTabla")
	public String save() {
		String forward = "";
		String mensaje = "";
		if (idTabla == null) {
			mensaje = "Registrado Correctamente";
		} else {
			mensaje = "Actualizado Correctamente";
		}
		try {
			UsuarioSession usuarioSession = (UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
			setCamposAuditoria(tabla, usuarioSession);
			tablaBO.save(tabla);
			listaTablasPadre = tablaBO.findTablasPadre(new Tabla());
			addActionMessage(mensaje);
			cleanForm();
			forward = "viewListTabla";
		} catch (BOException e) {
			forward = "viewFormTabla";
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			forward = "viewFormTabla";
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return forward;
	}
	
	@Action(value="initTablaValor")
	public String initTablaValor() {
		try {
			tablaBuscar = new Tabla();
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListTablaValor";
	}
	
	/**@Action(value="findAllTablaValor")
	public String findAllTablaValor() {
		try {
			if (tablaBuscar == null) {
				tablaBuscar = new Tabla();
			}
			listaTablasPadre = tablaBO.findTablas(tablaBuscar);
			if (listaTablasPadre.isEmpty()) {
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListTablaValor";
	}**/
	
	@Action(value="findAllTablaValor")
	public String findAllTablaValor() {
			
		try {
			if (tablaBuscar == null) {
				tablaBuscar = new Tabla();
			}
			
			setGrid(tablaBO.findToGrid(tablaBuscar,getSidx()+" "+getSord(),getPage(),getRows()));
			if(getGrid().getRecords().equals(new Integer(0))){
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "listTabla";
	}

	@Action(value="newTablaValor")
	public String newFormTablaValor() {
		tipoTabla="1";
		return "viewFormTablaValor";
	}

	@Action(value="updateTablaValor")
	public String updateFormTablaValor() {
		try {
			tabla = tablaBO.findTablaById(idTabla);
			if(tabla.getPadre()==null){
				tipoTabla="1";
			}else{
				tipoTabla="2";
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormTablaValor";
	}

	@Action(value="deleteTablaValor")
	public String deleteTablaValor() {
		try {
			tablaBO.deleteLog(Tabla.class, idTabla);
			addActionMessage("Eliminado Correctamente.");
			if (tablaBuscar == null) {
				tablaBuscar = new Tabla();
			}
			listaTablasPadre = tablaBO.findTablas(tablaBuscar);
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListTablaValor";
	}
	
	@Action(value="saveTablaValor")
	public String saveTablaValor() {
		String forward = "";
		String mensaje = "";
		try {
		if (idTabla == null) {
			mensaje = "Registrado Correctamente";
				
		} else {
			mensaje = "Actualizado Correctamente";
		}
		
		
			UsuarioSession usuarioSession = (UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
			setCamposAuditoria(tabla, usuarioSession);
			tablaBO.save(tabla);
//			if(tipoTabla.equals("2")){	
//				if(tabla.getPadre()!=null && tabla.getPadre().getId().equals(Constantes.ID_DELEGACION)){
//					cuadroAsignacionBO.actualizaFilasPorEscalaRating();
//				}
//			}
			listaTablasPadre = tablaBO.findTablas(new Tabla());
			addActionMessage(mensaje);
			cleanForm();
			forward = "viewListTablaValor";
		} catch (BOException e) {
			forward = "viewFormTablaValor";
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (DAOException e) {
			forward = "viewFormTablaValor";
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			forward = "viewFormTablaValor";
			logger.error(StringUtil.getStackTrace(e));
		}
		return forward;
	}

	public TablaBO getTablaBO() {
		return tablaBO;
	}

	public void setTablaBO(TablaBO tablaBO) {
		this.tablaBO = tablaBO;
	}

	public List<Tabla> getListaTablas() {
		return listaTablas;
	}

	public void setListaTablas(List<Tabla> listaTablas) {
		this.listaTablas = listaTablas;
	}

	public Long getIdTabla() {
		return idTabla;
	}

	public void setIdTabla(Long idTabla) {
		this.idTabla = idTabla;
	}

	public String getCodigoAntiguo() {
		return codigoAntiguo;
	}

	public void setCodigoAntiguo(String codigoAntiguo) {
		this.codigoAntiguo = codigoAntiguo;
	}

	public List<Tabla> getListaTablasPadre() {
		return listaTablasPadre;
	}

	public void setListaTablasPadre(List<Tabla> listaTablasPadre) {
		this.listaTablasPadre = listaTablasPadre;
	}

	public Tabla getTablaBuscar() {
		return tablaBuscar;
	}

	public void setTablaBuscar(Tabla tablaBuscar) {
		this.tablaBuscar = tablaBuscar;
	}

	public Tabla getTabla() {
		return tabla;
	}

	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}

	public List<SelectItem> getItemTablasPadre() {

		try {
			//Tabla tablaAux = new Tabla();
			//tablaAux.setEstado("1");

			List<Tabla> listaTablaAux = tablaBO.findTablasPadreRecur();
			itemTablasPadre = ComboUtil
					.getSelectItemsSinSort(listaTablaAux, "id", "descripcion",
							Constantes.VAL_DEFAULT_SELECTION_TODOS);
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}

		return itemTablasPadre;
	}

	public void setItemTablasPadre(List<SelectItem> itemTablasPadre) {
		this.itemTablasPadre = itemTablasPadre;
	}

	public List<SelectItem> getItemNuevoTablaPadre() {
		try {
			//Tabla tablaAux = new Tabla();
			//tablaAux.setEstado("1");

			List<Tabla> listaTablaAux = tablaBO.findTablasPadreRecur();
			itemNuevoTablaPadre = ComboUtil.getSelectItemsSinSort(
					listaTablaAux, "id", "descripcion",
					Constantes.VAL_DEFAULT_SELECTION);
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return itemNuevoTablaPadre;
	}

	public void setItemNuevoTablaPadre(List<SelectItem> itemNuevoTablaPadre) {
		this.itemNuevoTablaPadre = itemNuevoTablaPadre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getAbreviado() {
		return abreviado;
	}

	public void setAbreviado(String abreviado) {
		this.abreviado = abreviado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoTabla() {
		return tipoTabla;
	}

	public void setTipoTabla(String tipoTabla) {
		this.tipoTabla = tipoTabla;
	}
	
	

}
