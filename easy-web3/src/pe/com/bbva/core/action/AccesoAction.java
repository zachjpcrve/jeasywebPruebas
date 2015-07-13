package pe.com.bbva.core.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.service.ServiceUtil;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.mantenimiento.bo.ParametroBO;
import pe.com.bbva.mantenimiento.bo.TablaBO;
import pe.com.bbva.mantenimiento.domain.Parametro;
import pe.com.bbva.seguridad.action.MenuAction;
import pe.com.bbva.seguridad.bo.PerfilBO;
import pe.com.bbva.seguridad.bo.UsuarioBO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.seguridad.domain.Usuario;
import pe.com.bbva.util.Constantes;

import com.grupobbva.bc.per.tele.ldap.comunes.IILDPeExcepcion;
import com.grupobbva.bc.per.tele.ldap.directorio.IILDPeUsuario;
import com.grupobbva.bc.per.tele.seguridad.ServiciosSeguridadBbva;

/**
 * 
 * Clase que integra la aplicacion con el LDAP del banco
 * 
 * @author P018543
 *
 */
@Service("accesoAction")
@Scope("prototype")
@ResultPath(value="/")
@Results({
	@Result(name="panel",type="tiles", location="panel"),
	@Result(name="login", location="login-ldap.jsp"),
	@Result(name="error", location="error.jsp"),
	@Result(name="logoutIni", location="login_ldap_pre.jsp"),
	@Result(name="logout", location="logout.jsp")
})
public class AccesoAction extends GenericAction{
	
	public String codldap;
	public String desa;
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Obtiene el contenido de una invocación 
	 * GET O POST
	 * @param request
	 * @param response
	 */
	  @Action(value="acceso")
	  public String acceso() throws Exception{
		String forward = "";
		  
		logger.info("INICIO execute");
		logger.info("inicio servlet");
		ServiciosSeguridadBbva ssBbva; 
		String codigoUsuario = ""; 
		String codigoCargo = "";
		String codigoOficina = "";
		IILDPeUsuario user = null;
		ApplicationContext context = null;
		UsuarioBO usuarioBO =null;
		MenuAction menuCll = null;
		ParametroBO parametroBO = null;
		List<Modulo> listaModulos =null;
		
		try {
			try {
				String desa = this.getRequest().getParameter("desa");
				if(desa!=null){
					logger.info("Obteniendo parametro de desarrollo");
					codigoUsuario = this.getRequest().getParameter("codldap").toUpperCase();
					codigoCargo = codigoUsuario;
					codigoOficina = codigoUsuario;
					user = new IILDPeUsuario();
				}else{
					logger.info("Conectandose al ldap");
					ssBbva = new ServiciosSeguridadBbva(this.getRequest());
					if(ssBbva != null) {
						ssBbva.obtener_ID();
						codigoUsuario  =  ssBbva.getUsuario().toUpperCase();	
					} 
					user = IILDPeUsuario.recuperarUsuario(codigoUsuario);
					codigoCargo = user.getCargo().getCodigo();
					codigoOficina = user.getBancoOficina().getCodigo();
				}
				logger.info("USUARIO:"+codigoUsuario);
				if(user != null){
					context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
					usuarioBO = (UsuarioBO)context.getBean("usuarioBO");
					Usuario usuario = null;
					 usuario = usuarioBO.findByCodigo(codigoOficina); 
					if(usuario == null){
						 usuario = usuarioBO.findByCodigo(codigoCargo); 
						if(usuario == null){
						usuario = usuarioBO.findByCodigo(codigoUsuario); 
						}
					}
					if(usuario!=null){
												
						TablaBO tablaBO = (TablaBO)context.getBean("tablaBO");
						//Se suben todas la tablas de tablas a session para evitar
						//consultas masivas a la base de datos
						ServiceUtil serviceUtil = (ServiceUtil)context.getBean("serviceUtil");
						serviceUtil.setListaTablas(tablaBO.findAllTabla());
						UsuarioSession usuarioSession = new UsuarioSession();
						usuarioSession.setCodigo(codigoUsuario);
						usuarioSession.setCodigoCargo(codigoUsuario);//user.getCargo().getCodigo());
						usuarioSession.setCodigoOficina(codigoUsuario);//user.getBancoOficina().getCodigo());
						usuarioSession.setNombres(codigoUsuario);//user.getNombreCompleto());
						usuarioSession.setEmail(codigoUsuario);//user.getEmail());
						
						setObjectSession(Constantes.USUARIO_SESSION, usuarioSession);
						System.out.println("consultando todos los modulos");
						listaModulos = usuarioBO.findModulosByUsuario(usuario);
						
						menuCll = (MenuAction)context.getBean("menuAction");
						menuCll.setListaModulos(listaModulos);
						String menuBar=menuCll.getMenuBar();
							
						setObjectSession("menuCll",menuBar);
												
						parametroBO= (ParametroBO)context.getBean("parametroBO");
						setObjectSession(Constantes.PARAMETROS_SESSION,parametroBO.findParametros(new Parametro()));	
						setObjectSession(Constantes.USUARIO_SESSION, usuarioSession);
//						setMaxInactiveInterval(Integer.parseInt(getObjectParamtrosSession(
//								   Constantes.MAX_TIME_SESSION).toString()));
						forward =  "panel";	
						
					}else{
						logger.info("El usuario no esta registrado en el sistema");
						this.getRequest().setAttribute("mensaje", "El usuario no esta registrado en el sistema");
						forward = "error"; 
					}
				}else{
					logger.info("No se encontro del usuario en el LDAP");
					this.getRequest().setAttribute("mensaje", "No se encontro del usuario en el LDAP");
					forward = "error"; 
				}
	
			} catch (IILDPeExcepcion e) {
				logger.error(StringUtil.getStackTrace(e));
				this.getRequest().setAttribute("mensaje", e.getMensaje());
				forward ="error"; 
			} catch (Exception e) {
				logger.error(StringUtil.getStackTrace(e));
				this.getRequest().setAttribute("mensaje", e.getMessage());
				forward ="error"; 
			}
			
			
			
		} catch (Exception e) {
			logger.info("Error general");
			logger.error(StringUtil.getStackTrace(e));
		}
		
		logger.info("FIN execute");
		
	    return forward;
	}
	  
	@Action(value="closeSession")
	public String closeSession(){
			try{
				getSession(false).setAttribute(Constantes.USUARIO_SESSION, null);
				getSession(false).invalidate();
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			return "logoutIni";
	}
	
	@Action(value="home")
	public String home(){
		
		return "panel";
	}
}
