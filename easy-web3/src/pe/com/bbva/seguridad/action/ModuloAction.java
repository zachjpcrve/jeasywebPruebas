package pe.com.bbva.seguridad.action;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.com.bbva.core.action.GenericAction;
import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.core.exceptions.UtilException;
import pe.com.bbva.core.service.ServiceUtil;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.seguridad.bo.ModuloBO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.util.ComboUtil;
import pe.com.bbva.util.Constantes;
import pe.com.bbva.util.SelectItem;

@Controller("moduloAction")
@Scope("prototype")
@Results({
	@Result(name="viewFormModulo",type="tiles", location="viewFormModulo"),
	@Result(name="viewListModulo",type="tiles", location="viewListModulo"),
	@Result(name="listaModulos",type="json",params={"root","grid"})
})
public class ModuloAction extends GenericAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private ModuloBO moduloBO;

	private Long idModulo;
	private String codigoAntiguo;
	private Modulo modulo;
	private Modulo moduloBuscar;
	private List<Modulo> listaModulos;
	private List<SelectItem> listaPadres = new ArrayList<SelectItem>();
	private List<SelectItem> listaTiposModulo = new ArrayList<SelectItem>();

	public void cleanForm() {
		setIdModulo(null);
		setCodigoAntiguo("");
	}

	/**
	 * Este metodo se debe ejecutar la primera vez que se invoque al controlador
	 * para poder realizar la carga de data o limpiar datos de session que sean
	 * inecesarios
	 * 
	 * @return
	 */
	
	@Action(value="initModulo")
	public String init() {
		cleanForm();
		listaModulos = null;
		return "viewListModulo";
	}
	
	@Action(value="findAllModulo")
	public String findAll() {
		try {
			if (moduloBuscar == null) {
				moduloBuscar = new Modulo();
			}
			/**listaModulos = moduloBO.findModulos(moduloBuscar);
			if (listaModulos.isEmpty()) {
				addActionError("No se encontraron resultados");
			}**/
			setGrid(moduloBO.findToGrid(moduloBuscar,getSidx()+" "+getSord(),getPage(),getRows()));
			if(getGrid().getRecords().equals(new Integer(0))){
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "listaModulos";
	}

	@Action(value="newModulo")
	public String newForm() {
		return "viewFormModulo";
	}

	@Action(value="updateModulo")
	public String updateForm() {
		try {
			modulo = moduloBO.findById(idModulo);
			codigoAntiguo = modulo.getCodigo();
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormModulo";
	}
	
	@Action(value="deleteModulo")
	public String delete() {
		try {
			moduloBO.deleteLog(Modulo.class, idModulo);
			/**if (moduloBuscar == null) {
				moduloBuscar = new Modulo();
			}
			listaModulos = moduloBO.findModulos(moduloBuscar);**/
			addActionMessage("Eliminado Correctamente.");
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListModulo";
	}
	
	@Action(value="saveModulo")
	public String save() {

		String forward = "";
		String mensaje = "";
		if (modulo.getId() == null) {
			mensaje = "Registrado Correctamente";
		} else {
			mensaje = "Actualizado Correctamente";
		}
		try {
			UsuarioSession usuarioSession = (UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
			setCamposAuditoria(modulo, usuarioSession);
			moduloBO.save(modulo, codigoAntiguo);
			listaModulos = moduloBO.findModulos(new Modulo());
			addActionMessage(mensaje);
			cleanForm();
			forward = "viewListModulo";
		} catch (BOException e) {
			forward = "viewFormModulo";
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return forward;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Modulo getModuloBuscar() {
		return moduloBuscar;
	}

	public void setModuloBuscar(Modulo moduloBuscar) {
		this.moduloBuscar = moduloBuscar;
	}

	public List<Modulo> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List<Modulo> listaModulos) {
		this.listaModulos = listaModulos;
	}

	public ModuloBO getModuloBO() {
		return moduloBO;
	}

	public void setModuloBO(ModuloBO moduloBO) {
		this.moduloBO = moduloBO;
	}

	@SuppressWarnings("static-access")
	public List<SelectItem> getListaTiposModulo() {
		
		listaTiposModulo = ((ServiceUtil) getObjectSession("serviceUtil"))
				.getTipos(Constantes.ID_TABLA_TIPO_MODULO,
						Constantes.VAL_DEFAULT_SELECTION);
		return listaTiposModulo;
	}

	public void setListaTiposModulo(List<SelectItem> listaTiposModulo) {
		this.listaTiposModulo = listaTiposModulo;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public List<SelectItem> getListaPadres() {
		try {
			listaPadres = ComboUtil.getSelectItems(moduloBO.findPadres(), "id",
					"descripcion", Constantes.VAL_DEFAULT_SELECTION);
		} catch (UtilException e) {
			e.printStackTrace();
		} catch (BOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaPadres;
	}

	public void setListaPadres(List<SelectItem> listaPadres) {
		this.listaPadres = listaPadres;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}

	public String getCodigoAntiguo() {
		return codigoAntiguo;
	}

	public void setCodigoAntiguo(String codigoAntiguo) {
		this.codigoAntiguo = codigoAntiguo;
	}

}
