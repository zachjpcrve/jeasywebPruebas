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
import pe.com.bbva.mantenimiento.domain.Parametro;
import pe.com.bbva.seguridad.bo.PerfilBO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.util.Constantes;

@Controller("perfilAction")
@Scope("prototype")
@Results({
	@Result(name="viewFormPerfil",type="tiles", location="viewFormPerfil"),
	@Result(name="viewListPerfil",type="tiles", location="viewListPerfil"),
	@Result(name="listaPerfiles",type="json",params={"root","grid"})
})
public class PerfilAction extends GenericAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private PerfilBO perfilBO;

	private Long idPerfil;
	private String codigoAntiguo;
	private Perfil perfil;
	private Perfil perfilBuscar;
	private List<Perfil> listaPerfiles;
	private List<Modulo> modulosDisponibles;
	private List<Modulo> modulosSeleccionados;
	private List<String> selectedItemsIzquierdaModulos;
	private List<String> selectedItemsDerechaModulos;
	private List<String> selectedItemsIzquierdaAlmacen;
	private List<String> selectedItemsDerechaAlmacen;

	public void cleanForm() {
		setIdPerfil(null);
		setCodigoAntiguo("");
		setPerfil(null);
		setPerfilBuscar(null);
	}
	
	@Action(value="initPerfil")
	public String init() {
		cleanForm();
		listaPerfiles = null;
		return "viewListPerfil";
	}
	
	@Action(value="findAllPerfil")
	public String findAll() {
		try {
			if (perfilBuscar == null) {
				perfilBuscar = new Perfil();
			}
			/**listaPerfiles = perfilBO.findPerfiles(perfilBuscar);
			if (listaPerfiles.isEmpty()) {
				addActionError("No se encontraron resultados");
			}**/
			setGrid(perfilBO.findToGrid(perfilBuscar,getSidx()+" "+getSord(),getPage(),getRows()));
			if(getGrid().getRecords().equals(new Integer(0))){
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "listaPerfiles";
	}

	
	@Action(value="newPerfil")
	public String newForm() {
		try {
			cleanForm();
			loadOpcionesPerfil(new Perfil());
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormPerfil";
	}

	
	@Action(value="updatePerfil")
	public String updateForm() {
		try {
			perfil = perfilBO.findById(idPerfil);
			codigoAntiguo = perfil.getCodigo();
			loadOpcionesPerfil(perfil);
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormPerfil";
	}

	
	@Action(value="deletePerfil")
	public String delete() {
		try {
			perfilBO.deleteLog(Perfil.class, idPerfil);
			/**if (perfilBuscar == null) {
				perfilBuscar = new Perfil();
			}
			listaPerfiles = perfilBO.findPerfiles(perfilBuscar);**/
			addActionMessage("Eliminado Correctamente.");
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListPerfil";
	}

	@Action(value="savePerfil")
	public String save() {
		String forward = "";
		String mensaje = "";
		/**
		 * Asignando los Modulos seleccionados
		 */
		modulosSeleccionados = (List<Modulo>) getObjectSession("listaModulosSelect");
		perfil.setModulos(modulosSeleccionados);
		if (idPerfil == null) {
			mensaje = "Registrado Correctamente";
		} else {
			mensaje = "Actualizado Correctamente";
		}
		try {
			UsuarioSession usuarioSession = (UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
			setCamposAuditoria(perfil, usuarioSession);
			perfilBO.save(perfil, codigoAntiguo);
			listaPerfiles = perfilBO.findPerfiles(new Perfil());
			addActionMessage(mensaje);
			cleanForm();
			forward = "viewListPerfil";
		} catch (BOException e) {
			addActionError(e.getMessage());
			setFields();
			forward = "viewFormPerfil";
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			setFields();
			forward = "viewFormPerfil";
			logger.error(StringUtil.getStackTrace(e));
		}
		return forward;
	}

	@SuppressWarnings("unchecked")
	private void setFields() {
		modulosDisponibles = (List<Modulo>) getObjectSession("listaModulos");
		modulosSeleccionados = (List<Modulo>) getObjectSession("listaModulosSelect");
	}

	public void loadOpcionesPerfil(Perfil perfil) {
		try {

			modulosDisponibles = perfilBO.findModulosDisponibles(perfil);
			modulosSeleccionados = perfilBO.findModulosSeleccionados(perfil);

			/**
			 * Ponemos en Session para que puedan ser utilizados por las listas
			 * Comunicantes
			 */
			setObjectSession("listaModulos", modulosDisponibles);
			setObjectSession("listaModulosSelect", modulosSeleccionados);

		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
	}

	/**
	 * Agregar Elementos a la Derecha
	 * 
	 * @return
	 */
	
	@Action(value="moveToRightModulo")
	public String moveToRightModulo() {
		setListFromSession();
		List<Modulo> listaTemp = modulosDisponibles;
		List<String> listaPadre = listaProcesaRigth();
		for (String item : listaPadre) {

			for (Modulo itemsIzquierda : listaTemp) {
				if (itemsIzquierda.getCodigo() != null
						&& itemsIzquierda.getCodigo().equals(item)) {
					modulosDisponibles.remove(itemsIzquierda);
					modulosSeleccionados.add(itemsIzquierda);
					break;
				}
			}
		}
		setSelectedItemsIzquierdaModulos(null);
		return "viewFormPerfil";
	}

	public List<String> listaProcesaRigth() {
		List<String> nuevaLista = new ArrayList<String>();

		List<Modulo> listaTemp = modulosDisponibles;
		int contador = 0;
		for (String item : getSelectedItemsIzquierdaModulos()) {
			for (Modulo itemsIzquierda : listaTemp) {
				if (itemsIzquierda.getCodigo() != null
						&& itemsIzquierda.getCodigo().equals(item)) {
					contador = 0;
					if (itemsIzquierda.getSuperior() != null) {
						String codigo = itemsIzquierda.getSuperior()
								.getCodigo();
						for (String aux : nuevaLista) {
							if (aux == codigo) {
								contador++;
							}
						}
						if (contador == 0) {
							nuevaLista.add(codigo);
							nuevaLista.add(item);
						}
					} else {
						nuevaLista.add(item);
					}
				}
			}
		}
		return nuevaLista;
	}

	/**
	 * Agregar Todas los Elementos a la Derecha
	 * 
	 * @return
	 */
	
	@Action(value="moveToRightAllModulo")
	public String moveToRightAllModulo() {
		Modulo modulo;
		setListFromSession();

		if (!getModulosDisponibles().isEmpty()) {
			int tamanio = getModulosDisponibles().size();
			for (int i = 0; i < tamanio; i++) {
				modulo = getModulosDisponibles().get(0);
				getModulosDisponibles().remove(modulo);
				getModulosSeleccionados().add(modulo);
			}
		}
		setSelectedItemsIzquierdaModulos(null);
		return "viewFormPerfil";
	}

	/**
	 * Agregar Elementos a la Izquierda
	 * 
	 * @return
	 */
	
	@Action(value="moveToLeftModulo")
	public String moveToLeftModulo() {
		setListFromSession();
		List<Modulo> lisaTemp = this.getModulosSeleccionados();
		List<String> listaPadre = listaProcesaLefth();
		for (String item : listaPadre) {
			for (Modulo itemDerecha : lisaTemp) {
				if (itemDerecha.getCodigo() != null
						&& itemDerecha.getCodigo().equals(item)) {
					this.getModulosSeleccionados().remove(itemDerecha);
					this.getModulosDisponibles().add(itemDerecha);
					break;
				}
			}
		}
		this.setSelectedItemsDerechaModulos(null);
		return "viewFormPerfil";
	}

	public List<String> listaProcesaLefth() {
		List<String> nuevaLista = new ArrayList<String>();

		List<Modulo> listaTemp = this.getModulosSeleccionados();
		;
		int contador = 0;
		for (String item : this.getSelectedItemsDerechaModulos()) {
			for (Modulo itemDerecha : listaTemp) {
				if (itemDerecha.getCodigo() != null
						&& itemDerecha.getCodigo().equals(item)) {
					if (itemDerecha.getSuperior() == null) {

						contador = 0;
						nuevaLista.add(item);

						for (Modulo itemAux : listaTemp) {
							if (itemAux.getSuperior() != null) {
								if (itemAux.getSuperior().getId() == itemDerecha
										.getId()) {
									String codigo = itemAux.getCodigo();

									for (String aux : nuevaLista) {
										if (aux == codigo) {
											contador++;
										}
									}

									if (contador == 0) {
										nuevaLista.add(codigo);

									}

								}

							}
						}
					} else {
						nuevaLista.add(item);
					}
				}
			}
		}
		return nuevaLista;
	}

	/**
	 * Agregar Todas los Elementos a la Derecha
	 * 
	 * @return
	 */
	
	@Action(value="moveToLeftAllModulo")
	public String moveToLeftAllModulo() {
		Modulo modulo;
		setListFromSession();
		if (!this.getModulosSeleccionados().isEmpty()) {
			int tamanio = getModulosSeleccionados().size();
			for (int i = 0; i < tamanio; i++) {
				modulo = getModulosSeleccionados().get(0);
				getModulosSeleccionados().remove(modulo);
				getModulosDisponibles().add(modulo);
			}
		}
		this.setSelectedItemsDerechaModulos(null);

		return "viewFormPerfil";
	}

	@SuppressWarnings("unchecked")
	public void setListFromSession() {
		Map<String, Object> sessionparam = ActionContext.getContext()
				.getSession();
		this.setModulosDisponibles((List<Modulo>) sessionparam
				.get("listaModulos"));
		this.setModulosSeleccionados((List<Modulo>) sessionparam
				.get("listaModulosSelect"));
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Perfil getPerfilBuscar() {
		return perfilBuscar;
	}

	public void setPerfilBuscar(Perfil perfilBuscar) {
		this.perfilBuscar = perfilBuscar;
	}

	public List<Perfil> getListaPerfiles() {
		return listaPerfiles;
	}

	public void setListaPerfiles(List<Perfil> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public PerfilBO getPerfilBO() {
		return perfilBO;
	}

	public void setPerfilBO(PerfilBO perfilBO) {
		this.perfilBO = perfilBO;
	}

	public List<Modulo> getModulosDisponibles() {
		return modulosDisponibles;
	}

	public void setModulosDisponibles(List<Modulo> modulosDisponibles) {
		this.modulosDisponibles = modulosDisponibles;
	}

	public List<Modulo> getModulosSeleccionados() {
		return modulosSeleccionados;
	}

	public void setModulosSeleccionados(List<Modulo> modulosSeleccionados) {
		this.modulosSeleccionados = modulosSeleccionados;
	}

	public String getCodigoAntiguo() {
		return codigoAntiguo;
	}

	public void setCodigoAntiguo(String codigoAntiguo) {
		this.codigoAntiguo = codigoAntiguo;
	}

	public List<String> getSelectedItemsIzquierdaModulos() {
		return selectedItemsIzquierdaModulos;
	}

	public void setSelectedItemsIzquierdaModulos(
			List<String> selectedItemsIzquierdaModulos) {
		this.selectedItemsIzquierdaModulos = selectedItemsIzquierdaModulos;
	}

	public List<String> getSelectedItemsDerechaModulos() {
		return selectedItemsDerechaModulos;
	}

	public void setSelectedItemsDerechaModulos(
			List<String> selectedItemsDerechaModulos) {
		this.selectedItemsDerechaModulos = selectedItemsDerechaModulos;
	}

	public List<String> getSelectedItemsIzquierdaAlmacen() {
		return selectedItemsIzquierdaAlmacen;
	}

	public void setSelectedItemsIzquierdaAlmacen(
			List<String> selectedItemsIzquierdaAlmacen) {
		this.selectedItemsIzquierdaAlmacen = selectedItemsIzquierdaAlmacen;
	}

	public List<String> getSelectedItemsDerechaAlmacen() {
		return selectedItemsDerechaAlmacen;
	}

	public void setSelectedItemsDerechaAlmacen(
			List<String> selectedItemsDerechaAlmacen) {
		this.selectedItemsDerechaAlmacen = selectedItemsDerechaAlmacen;
	}

}
