package pe.com.bbva.seguridad.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import pe.com.bbva.core.action.GenericAction;
import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.seguridad.bo.UsuarioBO;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.seguridad.domain.Usuario;
import pe.com.bbva.util.Constantes;

@Controller("usuarioAction")
@Scope("prototype")
@Results({
	@Result(name="viewFormUsuario",type="tiles", location="viewFormUsuario"),
	@Result(name="viewListUsuario",type="tiles", location="viewListUsuario"),
	@Result(name="listaUsuarios",type="json",params={"root","grid"})
})
public class UsuarioAction extends GenericAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private UsuarioBO usuarioBO;
	private Long idUsuario;
	private String codigoAntiguo;
	private Usuario usuario;
	private Usuario usuarioBuscar;
	private List<Usuario> listaUsuarios;
	private List<Perfil> listaPerfilesDisponibles;
	private List<Perfil> listaPerfliesSeleccionados;

	private List<String> selectedItemsIzquierdaPerfiles;
	private List<String> selectedItemsDerechaPerfiles;

	public void cleanForm() {
		setCodigoAntiguo("");
		setIdUsuario(null);
	}

	@Action(value="initUsuario")
	public String init() {
		cleanForm();
		return "viewListUsuario";
	}

	@Action(value="findAllUsuario")
	public String findAll() {
		try {
			if (usuarioBuscar == null) {
				usuarioBuscar = new Usuario();
			}
			/**listaUsuarios = usuarioBO.findUsuarios(usuarioBuscar);
			if (listaUsuarios.isEmpty()) {
				addActionError("No se encontraron resultados");
			}**/
			
			setGrid(usuarioBO.findToGrid(usuarioBuscar,getSidx()+" "+getSord(),getPage(),getRows()));
			if(getGrid().getRecords().equals(new Integer(0))){
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "listaUsuarios";
	}

	@Action(value="newUsuario")
	public String newForm() {
		cleanForm();
		loadPerfiles(new Usuario());
		return "viewFormUsuario";
	}

	@Action(value="updateUsuario")
	public String updateForm() {
		try {
			usuario = usuarioBO.findById(idUsuario);
			codigoAntiguo = usuario.getCodigo();
			loadPerfiles(usuario);
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormUsuario";
	}
	
	@Action(value="deleteUsuario")
	public String delete() {
		try {
			usuarioBO.deleteLog(Usuario.class, idUsuario);
			/**if (usuarioBuscar == null) {
				usuarioBuscar = new Usuario();
			}
			listaUsuarios = usuarioBO.findUsuarios(usuarioBuscar);**/
			addActionMessage("Eliminado Correctamente.");
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListUsuario";
	}

	@Action(value="saveUsuario")
	public String save() {
		String forward = "";
		String mensaje = "";
		if (idUsuario == null) {
			mensaje = "Registrado Correctamente";
		} else {
			mensaje = "Actualizado Correctamente";
		}
		try {
			UsuarioSession usuarioSession = (UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
			setCamposAuditoria(usuario, usuarioSession);
			/**
			 * asignando los perfiles seleccionados
			 */
			listaPerfliesSeleccionados = (List<Perfil>) getObjectSession("listaPerfilesSelect");
			usuario.setPerfiles(listaPerfliesSeleccionados);

			if (listaPerfliesSeleccionados.size() > 0) {
				usuarioBO.save(usuario, codigoAntiguo);
				addActionMessage(mensaje);
				listaUsuarios = usuarioBO.findUsuarios(new Usuario());
				cleanForm();
				forward = "viewListUsuario";
			} else {
				addActionError("Debe ingresar el Perfil para el usuario");
				setFields(usuario);
				forward = "viewFormUsuario";
			}

		} catch (BOException e) {
			addActionError(e.getMessage());
			setFields(usuario);
			forward = "viewFormUsuario";
			logger.error(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			setFields(usuario);
			forward = "viewFormUsuario";
			logger.error(StringUtil.getStackTrace(e));
		}
		return forward;

	}

	@SuppressWarnings("unchecked")
	private void setFields(Usuario usuario) {

		listaPerfilesDisponibles = (List<Perfil>) getObjectSession("listaPerfiles");
		listaPerfliesSeleccionados = (List<Perfil>) getObjectSession("listaPerfilesSelect");
	}

	public void loadPerfiles(Usuario usuario) {
		try {
			if (usuario.getId() != null && usuario.getId().longValue() > 0) {
				listaPerfilesDisponibles = usuarioBO
						.findPerfilesDisponibles(usuario);
				listaPerfliesSeleccionados = usuarioBO
						.findPerfilesSeleccionados(usuario);

			} else {
				listaPerfilesDisponibles = usuarioBO
						.findPerfilesDisponibles(usuario);
				listaPerfliesSeleccionados = new ArrayList<Perfil>();
			}

			/**
			 * Ponemos en Session para que puedan ser utilizados por las listas
			 * comunicantes
			 */

			setObjectSession("listaPerfiles", listaPerfilesDisponibles);
			setObjectSession("listaPerfilesSelect", listaPerfliesSeleccionados);

		} catch (BOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioBuscar() {
		return usuarioBuscar;
	}

	public void setUsuarioBuscar(Usuario usuarioBuscar) {
		this.usuarioBuscar = usuarioBuscar;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public UsuarioBO getUsuarioBO() {
		return usuarioBO;
	}

	public void setUsuarioBO(UsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
	}

	public List<Perfil> getListaPerfilesDisponibles() {
		return listaPerfilesDisponibles;
	}

	public void setListaPerfilesDisponibles(
			List<Perfil> listaPerfilesDisponibles) {
		this.listaPerfilesDisponibles = listaPerfilesDisponibles;
	}

	public List<Perfil> getListaPerfliesSeleccionados() {
		return listaPerfliesSeleccionados;
	}

	public void setListaPerfliesSeleccionados(
			List<Perfil> listaPerfliesSeleccionados) {
		this.listaPerfliesSeleccionados = listaPerfliesSeleccionados;
	}

	/**
	 * Listas Comunnicantes
	 * 
	 * @return
	 */

	/**
	 * Agregar Elementos a la Derecha
	 */
	
	@Action(value="moveToRightPerfil")
	public String moveToRightPerfil() {

		setListPerfilFromSession();
		List<Perfil> listaTemp = listaPerfilesDisponibles;
		for (String item : getSelectedItemsIzquierdaPerfiles()) {

			for (Perfil itemsIzquierda : listaTemp) {
				if (itemsIzquierda.getCodigo() != null
						&& itemsIzquierda.getCodigo().equals(item)) {
					listaPerfilesDisponibles.remove(itemsIzquierda);
					listaPerfliesSeleccionados.add(itemsIzquierda);
					break;
				}
			}
		}
		setSelectedItemsIzquierdaPerfiles(null);
		return "viewFormUsuario";
	}

	/**
	 * Agregar Todas los Elementos a la Derecha
	 * 
	 * @return
	 */
	
	@Action(value="moveToRightAllPerfil")
	public String moveToRightAllPerfil() {

		Perfil pefil;
		setListPerfilFromSession();

		if (!getListaPerfilesDisponibles().isEmpty()) {
			int tamanio = getListaPerfilesDisponibles().size();
			for (int i = 0; i < tamanio; i++) {
				pefil = getListaPerfilesDisponibles().get(0);
				getListaPerfilesDisponibles().remove(pefil);
				getListaPerfliesSeleccionados().add(pefil);
			}
		}
		setSelectedItemsIzquierdaPerfiles(null);

		return "viewFormUsuario";
	}

	/**
	 * Agregar Elementos a la Izquierda
	 * 
	 * @return
	 */
	
	@Action(value="moveToLeftPerfil")
	public String moveToLeftPerfil() {

		setListPerfilFromSession();
		List<Perfil> lisaTemp = listaPerfliesSeleccionados;
		for (String item : getSelectedItemsDerechaPerfiles()) {
			for (Perfil itemDerecha : lisaTemp) {
				if (itemDerecha.getCodigo() != null
						&& itemDerecha.getCodigo().equals(item)) {
					listaPerfliesSeleccionados.remove(itemDerecha);
					listaPerfilesDisponibles.add(itemDerecha);
					break;
				}
			}
		}
		setSelectedItemsDerechaPerfiles(null);

		return "viewFormUsuario";
	}

	/**
	 * Agregar Todas los Elementos a la Derecha
	 * 
	 * @return
	 */
	
	@Action(value="moveToLeftAllPerfil")
	public String moveToLeftAllPerfil() {
		Perfil perfil;
		setListPerfilFromSession();
		if (!getListaPerfliesSeleccionados().isEmpty()) {
			int tamanio = getListaPerfliesSeleccionados().size();
			for (int i = 0; i < tamanio; i++) {
				perfil = getListaPerfliesSeleccionados().get(0);
				getListaPerfliesSeleccionados().remove(perfil);
				getListaPerfilesDisponibles().add(perfil);
			}
		}
		setSelectedItemsDerechaPerfiles(null);
		return "viewFormUsuario";
	}

	@SuppressWarnings("unchecked")
	public void setListPerfilFromSession() {
		Map<String, Object> sessionparam = ActionContext.getContext()
				.getSession();
		listaPerfilesDisponibles = (List<Perfil>) sessionparam
				.get("listaPerfiles");
		listaPerfliesSeleccionados = (List<Perfil>) sessionparam
				.get("listaPerfilesSelect");
	}



	public String getCodigoAntiguo() {
		return codigoAntiguo;
	}

	public void setCodigoAntiguo(String codigoAntiguo) {
		this.codigoAntiguo = codigoAntiguo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<String> getSelectedItemsIzquierdaPerfiles() {
		return selectedItemsIzquierdaPerfiles;
	}

	public void setSelectedItemsIzquierdaPerfiles(
			List<String> selectedItemsIzquierdaPerfiles) {
		this.selectedItemsIzquierdaPerfiles = selectedItemsIzquierdaPerfiles;
	}

	public List<String> getSelectedItemsDerechaPerfiles() {
		return selectedItemsDerechaPerfiles;
	}

	public void setSelectedItemsDerechaPerfiles(
			List<String> selectedItemsDerechaPerfiles) {
		this.selectedItemsDerechaPerfiles = selectedItemsDerechaPerfiles;
	}

}
