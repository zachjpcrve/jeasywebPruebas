package pe.com.bbva.core.action;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.util.FechaUtil;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.mantenimiento.domain.Parametro;
import pe.com.bbva.util.Constantes;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * Clase generica para las clases Action
 * 
 * @author P018543
 *
 */
public class GenericAction extends ActionSupport {

	Logger logger = Logger.getLogger(this.getClass());
	
	//estas variables son usandas para volver a posicionar el formulario 
	//al lugar de donde se mando la peticion
	private String scrollX ;
	private String scrollY;
	
	//Variables usadas para la generacion de las grillas con jquery
	private int page;
	private int rows;
	private int records;
	private String sord ;
	private String sidx;
	private Grid<?> grid;
	
	/**
	 * Metodo para obtener un bean de spring
	 * @param name
	 * @return
	 */
	public static Object getSpringBean(String name){
		WebApplicationContext context =
			WebApplicationContextUtils.getRequiredWebApplicationContext(
                                    ServletActionContext.getServletContext()
                        );
		return context.getBean(name);
	}
	
	/**
	 * Metodo para obtener datos de session
	 * @param nombre
	 * @return
	 */
	public static Object getObjectSession(String nombre){
		Map<String, Object>  sessionparam =  ActionContext.getContext().getSession();
		Object  obj = sessionparam.get(nombre);
		return obj;
	}
	
	/**
	 * Metodos para enviar datos a session
	 * @param nombre
	 * @param valor
	 */
	public static void  setObjectSession(String nombre, Object valor){
		Map<String, Object> sessionparam = ActionContext.getContext().getSession();
		sessionparam.put(nombre, valor);
	}
	
	/**
	 * Método para obtener algun valor de los parametros subidos a 
	 * session
	 * @param codigoParametro
	 * @return
	 */
	public static Object getObjectParamtrosSession(String codigoParametro){
		String valor= "";
		List<Parametro> listaParametros = (List<Parametro>) getObjectSession(Constantes.PARAMETROS_SESSION);
		for(Parametro param : listaParametros){
			if(param.getCodigo().equals(codigoParametro)){
				valor = param.getValor();
				break;
			}
		}
		return valor;
	}
	
	/**
	 * Metodo para definir la duracion de la session
	 * @param time  tiempo en segundos
	 */
	public void setMaxInactiveInterval(int time){
		getRequest().getSession().setMaxInactiveInterval(time);
	}
	
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	 
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	/** 
	 * Returns an InputStream for a resource at the given path
	 */
	protected static InputStream getResourceInputStream(String relPath) {
		return ServletActionContext.getServletContext().getResourceAsStream(relPath);
	}
	
	public ServletContext getServletContext(){
		return ServletActionContext.getServletContext();
	}
	
	public HttpSession getSession(boolean create){
		return getRequest().getSession(create);
	}

	public String getScrollX() {
		return scrollX;
	}

	public void setScrollX(String scrollX) {
		this.scrollX = scrollX;
	}

	public String getScrollY() {
		return scrollY;
	}

	public void setScrollY(String scrollY) {
		this.scrollY = scrollY;
	}
	
	/**
	 * Asigna los valores de auditoria como fecha de creacion, usuario creador
	 * fecha de modificacion y usuario modificador dependiendo si el objeto
	 * tiene o no asignana una id
	 * para poder usar este metodo los objetos tiene que eredar de EntidadBase
	 * 
	 * @param obj
	 * @param usuarioSession
	 */
	public void setCamposAuditoria(Object obj,UsuarioSession usuarioSession){
		Method method = null;
		Object id = null;
			try {
				method = obj.getClass().getMethod("getId", null);
				id = method.invoke(obj, null);
				if(id==null){
					BeanUtils.setProperty(obj,
							  			  "codUsuarioCreacion", 
							  			  usuarioSession.getCodigo());
					BeanUtils.setProperty(obj,
							  			  "fechaCreacion", 
							  			  FechaUtil.getFechaActualDate());
					BeanUtils.setProperty(obj,
							  			  "fechaModificacion", 
							  			  FechaUtil.getFechaActualDate());
				}else{
					BeanUtils.setProperty(obj,
							  			  "codUsuarioModificacion", 
							  			  usuarioSession.getCodigo());
					BeanUtils.setProperty(obj,
							  			  "fechaModificacion", 
							  			  FechaUtil.getFechaActualDate());
				}
			} catch (SecurityException e) {
				logger.info(StringUtil.getStackTrace(e));
			} catch (NoSuchMethodException e) {
				logger.info(StringUtil.getStackTrace(e));
			} catch (IllegalArgumentException e) {
				logger.info(StringUtil.getStackTrace(e));
			} catch (IllegalAccessException e) {
				logger.info(StringUtil.getStackTrace(e));
			} catch (InvocationTargetException e) {
				logger.info(StringUtil.getStackTrace(e));
			} catch (Exception e) {
				logger.info(StringUtil.getStackTrace(e));
			}
			
	}
	
	/**
	 * Metodo que permite remover un objeto de la Session
	 * @param nombre
	 */
	public static void  removeObjectSession(String nombre){
		Map<String, Object> sessionparam = ActionContext.getContext().getSession();
		sessionparam.remove(nombre);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public Grid<?> getGrid() {
		return grid;
	}

	public void setGrid(Grid<?> grid) {
		this.grid = grid;
	}
}
