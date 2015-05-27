package pe.com.bbva.core.seguridad.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pe.com.bbva.core.util.ConstantesUtil;
import pe.com.bbva.util.Constantes;


/**
 * 
 * Esta clase implementa el filtro de seguridad de la aplicacion
 * 
 * @author P018543
 *
 */
public class Seguridad implements Filter {
	
	private static String URL_LOGIN = "/error.jsp";

	
    /**
     * Default constructor. 
     */
    public Seguridad() {
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if(validarURL(httpRequest.getServletPath())){
			chain.doFilter(request, response);
			return;
		}
		if (session == null || session.getAttribute(Constantes.USUARIO_SESSION) == null){
			httpResponse.sendRedirect(httpRequest.getContextPath() + URL_LOGIN);
		}else{
			chain.doFilter(request, response);
		}
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public boolean validarURL(String url){
		List<String> listaUrls = new ArrayList<String>();
		listaUrls.add("");
		listaUrls.add("/login_ldap_pre.jsp");
		listaUrls.add("/acceso.do");
		listaUrls.add("/error.jsp");
		if(!listaUrls.contains(url)){
			return false;
		}
		return true;
	}
}
