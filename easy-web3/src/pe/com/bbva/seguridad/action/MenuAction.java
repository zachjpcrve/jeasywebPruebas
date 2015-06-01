package pe.com.bbva.seguridad.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.action.GenericAction;
import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.util.Constantes;

@Service("menuAction")
@Scope("prototype")
public class MenuAction extends GenericAction{

	private Logger logger = Logger.getLogger(this.getClass());
	
	private List<Modulo> listaModulos;
	
	private StringBuilder  menuBarString = new StringBuilder();
 
	public String getMenuBar() {
		
		UsuarioSession usuario = new UsuarioSession();
	    usuario=(UsuarioSession) getObjectSession(Constantes.USUARIO_SESSION);
	    try {
	    	if(!listaModulos.isEmpty()){

	    		menuBarString = new StringBuilder();
	    		menuBarString.append("<div class=\"demo\">");
	    		menuBarString.append("<ul id=\"bar1\" class=\"menubar\">");
	    		
				List<Modulo> listaModulosPadre = new ArrayList<Modulo>();
				for(Modulo modulo : listaModulos){
					if(modulo.getTipoModulo()!=null && 
							modulo.getTipoModulo().getId().equals(Constantes.VAL_TIPO_MODULO)){
						listaModulosPadre.add(modulo);
					}
				}
				listaModulosPadre = orderModulosByOrden(listaModulosPadre);	       
			    for(Modulo modPadre : listaModulosPadre){   
			    	setChildren(  modPadre);
			    }  
			    
			    menuBarString.append("</ul>");
			  menuBarString.append("</div>");
	    	}
		}catch (Exception e) {
			logger.info(StringUtil.getStackTrace(e));
		}     
	    System.out.println("MENU="+menuBarString);
		return menuBarString.toString(); 
	}

	public void setChildren(Modulo modPadre){
		
			if(modPadre.getTipoModulo()!=null &&
					modPadre.getTipoModulo().getId().equals(Constantes.VAL_TIPO_MODULO)){
			     
			     List<Modulo> listaHijos= getModulosHijos(modPadre);
			     
			     //Solo si se tiene modulo Hijos se muestra el modulo
			     boolean estaVacio=listaHijos.isEmpty();
			     if(!estaVacio){
					 menuBarString.append("<li>");
				     	menuBarString.append("<a href=\"#\">"+modPadre.getDescripcion()+"</a>");
				     	menuBarString.append("<ul>");
			     }
			     listaHijos = orderModulosByOrden(listaHijos);
			     for(Modulo hijo : listaHijos){
			    	 setChildren(hijo);
			     }
			     if(!estaVacio){
			    	 	menuBarString.append("</ul>");
					 menuBarString.append("</li>");
			     }

			}else if(modPadre.getTipoModulo()!=null && 
						modPadre.getTipoModulo().getId().equals(Constantes.VAL_TIPO_OPCION)){
				
				if(getModulosHijos(modPadre).size()==0){
					//El controlador en struts 2 sera el action 
					menuBarString.append("<li><a href=\""+modPadre.getControlador()+"\">"+modPadre.getDescripcion()+"</a></li>");

				}else{
					
					List<Modulo> listaHijos= getModulosHijos(modPadre);
				     boolean estaVacio=listaHijos.isEmpty();
				     if(!estaVacio){
						 menuBarString.append("<li>");
					     	menuBarString.append("<a href=\"#\">"+modPadre.getDescripcion()+"</a>");
					     	menuBarString.append("<ul>");
				     }
					listaHijos = orderModulosByOrden(listaHijos);
					for(Modulo hijo : listaHijos){
						setChildren(hijo);
				     }
				     if(!estaVacio){
					     	menuBarString.append("</ul>");
						 menuBarString.append("</li>");
				     }

				}
			}
		
	}
	
	public List<Modulo> getModulosHijos(Modulo modulo){
		List<Modulo> listaHijos = new ArrayList<Modulo>();
		for(Modulo mod : listaModulos){
			if(mod.getSuperior() != null &&
			   mod.getSuperior().getId().equals(modulo.getId())){
				listaHijos.add(mod);
			}
		}
		return listaHijos;
	}
	
	public List<Modulo>  orderModulosByOrden(List<Modulo> lista){
		Map<Integer, Modulo> listahash = new TreeMap<Integer, Modulo>();
		for(Modulo modulo : lista){
			listahash.put(modulo.getOrden(), modulo);
		}
		lista = new ArrayList<Modulo>();
		for(Map.Entry<Integer,Modulo> entry : listahash.entrySet()){
			lista.add(entry.getValue());
		}
		return lista; 
	}
	
	public void setListaModulos(List<Modulo> listaModulos) {
		this.listaModulos = listaModulos;
	}  

	public String getMenuBarHTML(){
		
		return getmenuBarFijo();
	}
	
	private String getmenuBarFijo(){
		StringBuilder sb= new StringBuilder("");

		
		sb.append("<div class=\"demo\">");
		
			sb.append("<ul id=\"bar1\" class=\"menubar\">");
		
			sb.append("<li>");
				sb.append("<a href=\"#File\">C&oacute;digo de Barras</a>");
				sb.append("<ul>");
					sb.append("<li><a href=\"#Open...\">Open...</a></li>");
					sb.append("<li class=\"ui-state-disabled\">Open recent...</li>");

					sb.append("<li><a href=\"#Save\">Save</a></li>");
					sb.append("<li><a href=\"#Save as...\">Save as...</a></li>");
					sb.append("<li><a href=\"#Close\">Close</a></li>");
					sb.append("<li><a href=\"#Quit\">Quit</a></li>");
				sb.append("</ul>");
			sb.append("</li>");
		
		sb.append("<li>");
		sb.append("<a href=\"#View\"> &nbsp;Guias&nbsp; </a>");
		sb.append("<ul>");
		sb.append("<li><a href=\"#Fullscreen\">Fullscreen</a></li>");
		sb.append("<li><a href=\"#Fit into view\">Fit into view</a></li>");
		sb.append("<li>");
		sb.append("<a href=\"#Encoding\">Encoding</a>");

		sb.append("<ul>");
		sb.append("<li><a href=\"#Auto-detect\">Auto-detect</a></li>");
		sb.append("<li><a href=\"#UTF-8\">UTF-8</a></li>");
		sb.append("<li>");
		sb.append("<a href=\"#UTF-16\">UTF-16</a>");
		sb.append("<ul>");
		sb.append("<li><a href=\"#Option 1\">Option 1</a></li>");

		sb.append("<li><a href=\"#Option 2\">Option 2</a></li>");
		sb.append("<li><a href=\"#Option 3\">Option 3</a></li>");
		sb.append("<li><a href=\"#Option 4\">Option 4</a></li>");
		sb.append("</ul>");
		sb.append("</li>");
		sb.append("</ul>");
		sb.append("</li>");

		sb.append("<li><a href=\"#Customize...\">Customize...</a></li>");
		sb.append("</ul>");
		sb.append("</li>");
		
		sb.append("<li>");
		sb.append("<a href=\"#\">Matenimiento</a>");
		sb.append("<ul>");
		sb.append("<li><a href=\"./mantAlmacen.do\">RC002:Almacenes</a></li>");
					sb.append("<li><a href=\"./mantProvTansp.do\">RC002:Proveedores y Transportistas</a></li>");
					sb.append("<li><a href=\"./mantClaseMov.do\">RC002:Clase Movimiento</a></li>");
					sb.append("<li><a href=\"./mantChofer.do\">RC002:Chofer</a></li>");
					sb.append("<li><a href=\"./mantVehiculo.do\">RC002:Vehiculo</a></li>");
					sb.append("<li><a href=\"./centCostosAction.do\">RC002:Centro Costos</a></li>");
				sb.append("</ul>");
		sb.append("</li>");
		
		sb.append("<li>");
		sb.append("<a href=\"#\">&nbsp;Seguridad&nbsp; </a>");
		sb.append("<ul>");
					sb.append("<li><a href=\"./usuariosAction.do\"> Usuario  </a></li>");
					sb.append("<li><a href=\"./perfilesAction.do\"> Perfil </a></li>");
					sb.append("<li><a href=\"./modulosAction.do\"> Modulo </a></li>");
					sb.append("<li class=\"ui-state-disabled\"></li>");
				sb.append("</ul>");
		sb.append("</li>");
		
		sb.append("</ul>");
	sb.append("</div>");
		
		return sb.toString();
	}
}
