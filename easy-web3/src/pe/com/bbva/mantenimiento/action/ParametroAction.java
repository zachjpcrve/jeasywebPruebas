package pe.com.bbva.mantenimiento.action;

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
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.mantenimiento.bo.ParametroBO;
import pe.com.bbva.mantenimiento.domain.Parametro;
import pe.com.bbva.util.Constantes;

@Service("parametroAction")
@Scope("prototype")
@Results({
	@Result(name="viewListParametro",type="tiles", location="viewListParametro"),
	@Result(name="viewFormParametro",type="tiles", location="viewFormParametro"),
	@Result(name="listaParametros",type="json",params={"root","grid"})
})
public class ParametroAction extends GenericAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private ParametroBO parametroBO;

	private Long idParametro;
	private String codigoAntiguo;
	private Parametro parametro;
	private Parametro parametroBuscar;
	private List<Parametro> listaParametros;

	public void cleanForm() {
		setIdParametro(null);
		setCodigoAntiguo("");
		setParametro(null);
		setParametroBuscar(null);
		
	}
	
	@Action(value="initParametro")
	public String init() {
		parametro = new Parametro();
		return "viewListParametro";
	}
	
	@Action(value="findAllParametro")
	public String findAll() {
		try {
			if (parametroBuscar == null) {
				parametroBuscar = new Parametro();
			}
			setGrid(parametroBO.findToGrid(parametroBuscar,getSidx()+" "+getSord(),getPage(),getRows()));
			if(getGrid().getRecords().equals(new Integer(0))){
				addActionError("No se encontraron resultados");
			}
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "listaParametros";
	}

	@Action(value="newParametro")
	public String newForm() {
		return "viewFormParametro";
	}
	
	@Action(value="updateParametro")
	public String updateForm() {
		try {
			parametro = parametroBO.findById(idParametro);
			codigoAntiguo = parametro.getCodigo();
		} catch (BOException e) {
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewFormParametro";
	}

	@Action(value="deleteParametro")
	public String delete() {
		try {
			parametroBO.deleteLog(Parametro.class, idParametro);
			addActionMessage("Eliminado Correctamente.");
		} catch (BOException e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		}
		return "viewListParametro";
	}

	@Action(value="saveParametro")
	public String save() {
		String forward = "";
		String mensaje = "";
		if (idParametro == null) {
			mensaje = "Registrado Correctamente";
		} else {
			mensaje = "Actualizado Correctamente";
		}
		try {
			UsuarioSession usuarioSession = (UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
			setCamposAuditoria(parametro, usuarioSession);
			parametroBO.save(parametro, codigoAntiguo);
			listaParametros = parametroBO.findParametros(new Parametro());
			addActionMessage(mensaje);
			cleanForm();
			forward = "viewListParametro";
		} catch (BOException e) {
			forward = "viewFormParametro";
			addActionError(e.getMessage());
			logger.error(StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(StringUtil.getStackTrace(e));
		}
		return forward;
	}

	public ParametroBO getParametroBO() {
		return parametroBO;
	}

	public void setParametroBO(ParametroBO parametroBO) {
		this.parametroBO = parametroBO;
	}

	public Long getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}

	public String getCodigoAntiguo() {
		return codigoAntiguo;
	}

	public void setCodigoAntiguo(String codigoAntiguo) {
		this.codigoAntiguo = codigoAntiguo;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public Parametro getParametroBuscar() {
		return parametroBuscar;
	}

	public void setParametroBuscar(Parametro parametroBuscar) {
		this.parametroBuscar = parametroBuscar;
	}

	public List<Parametro> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List<Parametro> listaParametros) {
		this.listaParametros = listaParametros;
	}


}
