package pe.com.bbva.monitoreo.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import net.fckeditor.response.GetResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.com.bbva.core.action.GenericAction;
import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.service.ServiceUtil;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.monitoreo.bo.ServicioBO;
import pe.com.bbva.monitoreo.domain.Servicio;
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
	private String urlServicio;
	private String urlAntiguo;
	private Servicio servicio;
	private Servicio servicioBuscar;
	private List<Servicio> listaServicios;
	private List<Long> listaIds= new ArrayList<Long>();
	private List<SelectItem> listaTiposAmbiente = new ArrayList<SelectItem>();
	private List<SelectItem> listaTiposAplicativo= new ArrayList<SelectItem>();
	private long tiempoEjecucion;
	private boolean finalizoproceso=false;

	public void cleanForm() {
		setIdServicio(null);
		setUrlServicio("");
		setUrlAntiguo("");
	}
	
	@Action(value="initMonitoreo")
	public String init() {
		cleanForm();
		listaServicios = null;
		return "viewListServicio";
	}
	
	@Action(value="mensajeServicio")
	public String message(){
		try {
			servicio = servicioBO.findById(idServicio);
			addActionError(servicio.getPat_Exito());
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR: "+e.toString());
		}
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
	
	@Action(value="refreshServicioSelected")
	public void refreshSeleccionados() throws IOException{
					// TODO Auto-generated method stub
					try {
						servicio = servicioBO.findById(idServicio);
						urlAntiguo = servicio.getUrl();
						servicio.setEstado_serv(servicioBO.testByUrl(servicio, urlAntiguo));
						servicioBO.save(servicio, urlAntiguo);
						Thread.sleep(0);
					} catch (BOException e) {
						addActionError(e.getMessage());
						logger.error(StringUtil.getStackTrace(e));
					} catch (Exception e) {
						logger.error(StringUtil.getStackTrace(e));
					}
	}
	
	@Action(value="refreshServicio")
	public String refresh(){
		String mensaje = "Actualizando estado de servicio";
		try {
			servicio = servicioBO.findById(idServicio);
			urlAntiguo = servicio.getUrl();
			servicio.setEstado_serv(servicioBO.testByUrl(servicio, urlAntiguo));
			servicioBO.save(servicio, urlAntiguo);
			addActionMessage(mensaje);
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListServicio";
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
			urlAntiguo=servicio.getUrl();
			servicio.setEstado_serv(servicioBO.testByUrl(servicio, urlAntiguo));
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
	
	@Action("obtenerLista")
	public void Obtener(){
		listaIds.add(idServicio);
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
	
	public String getUrlServicio(){
		return urlServicio;
	}
	
	public void setUrlServicio(String urlServicio){
		this.urlServicio=urlServicio;
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
	
	@SuppressWarnings("static-access")
	public List<SelectItem> getListaTiposAplicativo() {
		listaTiposAplicativo = ((ServiceUtil) getObjectSession("serviceUtil"))
				.getTipos(Constantes.ID_TABLA_TIPO_APLICATIVO,
						Constantes.VAL_DEFAULT_SELECTION);
		return listaTiposAplicativo;
	}

	public void setListaTiposAplicativo(List<SelectItem> listaTiposAplicativo) {
		this.listaTiposAplicativo = listaTiposAplicativo;
	}
	public List<Long> getListaIds() {
		return listaIds;
	}

	public void setListaIds(List<Long> listaIds) {
		this.listaIds = listaIds;
	}
	
	public long TiempoMayor(){
		long mayor=-1;
		for (int i = 0; i < listaIds.size(); i++) {
			if(mayor<getListaIds().get(i)){
				mayor=getListaIds().get(i);
			}
		}
		return mayor;
	}

	public long getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(long tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	public boolean isFinalizoproceso() {
		return finalizoproceso;
	}

	public void setFinalizoproceso(boolean finalizoproceso) {
		this.finalizoproceso = finalizoproceso;
	}
}