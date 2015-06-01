package pe.com.bbva.monitoreo.action;

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
import pe.com.bbva.monitoreo.bo.ServicioBO;
import pe.com.bbva.monitoreo.domain.Servicio;
import pe.com.bbva.util.ComboUtil;
import pe.com.bbva.util.Constantes;
import pe.com.bbva.util.SelectItem;

@Controller("servicioAction")
@Scope("prototype")
@Results({
	@Result(name="viewFormServicio",type="tiles", location="viewFormServicio"),
	@Result(name="viewListServicio",type="tiles", location="viewListServicio"),
	@Result(name="listaServicios",type="json",params={"root","grid"})
})
public class ServicioAction extends GenericAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger=Logger.getLogger(this.getClass());
	
	@Resource
	private ServicioBO servicioBO;

	private Long idServicio;
	private String urlAntiguo;
	private Servicio servicio;
	private Servicio servicioBuscar;
	private List<Servicio> listaServicios;
	private List<SelectItem> listaPadresServ = new ArrayList<SelectItem>();
	private List<SelectItem> listaTiposAmbiente = new ArrayList<SelectItem>();
//	private List<SelectItem> listaTiposAplicativo= new ArrayList<SelectItem>();
	
	public void cleanForm() {
		setIdServicio(null);
		setUrlAntiguo("");
	}
	
	@Action(value="initMonitoreo")
	public String init() {
		cleanForm();
		listaServicios = null;
		return "viewListServicio";
	}
	
	@Action(value="findAllServicio")
	public String findAll() {
		try {
			if (servicioBuscar == null) {
				servicioBuscar = new Servicio();
			}

			setGrid(servicioBO.findToGrid(servicioBuscar,getSidx()+" "+getSord(),getPage(),getRows()));
			if(getGrid().getRecords().equals(new Integer(0))){
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		} 
		return "listaServicios";
	}

	@Action(value="newServicio")
	public String newForm() {
		return "viewFormServicio";
	}

	@Action(value="updateServicio")
	public String updateForm() {
		try {
			servicio = servicioBO.findById(idServicio);
			urlAntiguo = servicio.getUrl();
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormServicio";
	}
	
	@Action(value="deleteServicio")
	public String delete() {
		try {
			servicioBO.deleteLog(Servicio.class, idServicio);
			addActionMessage("Eliminado Correctamente.");
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListServicio";
	}
	
	@Action(value="saveServicio")
	public String save() {

		String forward = "";
		String mensaje = "";
		if (servicio.getId() == null) {
			mensaje = "Registrado Correctamente";
		} else {
			mensaje = "Actualizado Correctamente";
		}
		try {
			UsuarioSession usuarioSession = (UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
			setCamposAuditoria(servicio, usuarioSession);
			servicioBO.save(servicio, urlAntiguo);
			listaServicios = servicioBO.findServicios(new Servicio());
			addActionMessage(mensaje);
			cleanForm();
			forward = "viewListServicio";
		} catch (BOException e) {
			forward = "viewFormServicio";
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return forward;
	}

	public ServicioBO getServicioBO() {
		return servicioBO;
	}

	public void setServicioBO(ServicioBO servicioBO) {
		this.servicioBO = servicioBO;
	}

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public String getUrlAntiguo() {
		return urlAntiguo;
	}

	public void setUrlAntiguo(String urlAntiguo) {
		this.urlAntiguo = urlAntiguo;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Servicio getServicioBuscar() {
		return servicioBuscar;
	}

	public void setServicioBuscar(Servicio servicioBuscar) {
		this.servicioBuscar = servicioBuscar;
	}

	public List<Servicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public List<SelectItem> getListaPadresServ() {
		try {
			listaPadresServ = ComboUtil.getSelectItems(servicioBO.findPadres(), "id",
					"descripcion", Constantes.VAL_DEFAULT_SELECTION);
		} catch (UtilException e) {
			e.printStackTrace();
		} catch (BOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listaPadresServ;
	}

	public void setListaPadresServ(List<SelectItem> listaPadresServ) {
		this.listaPadresServ = listaPadresServ;
	}

	@SuppressWarnings("static-access")
	public List<SelectItem> getListaTiposAmbiente() {		
		listaTiposAmbiente = ((ServiceUtil) getObjectSession("serviceUtil"))
				.getTipos(Constantes.ID_TABLA_TIPO_AMBIENTE,
						Constantes.VAL_DEFAULT_SELECTION);
		return listaTiposAmbiente;
	}

	public void setListaTiposAmbiente(List<SelectItem> listaTiposAmbiente) {
		this.listaTiposAmbiente = listaTiposAmbiente;
	}	
}
