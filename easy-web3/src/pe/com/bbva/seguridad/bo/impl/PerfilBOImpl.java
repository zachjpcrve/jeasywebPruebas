package pe.com.bbva.seguridad.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.bbva.core.bo.GenericBOImpl;
import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.bo.PerfilBO;
import pe.com.bbva.seguridad.dao.ModuloDAO;
import pe.com.bbva.seguridad.dao.PerfilDAO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.seguridad.domain.Usuario;
import pe.com.bbva.util.Constantes;

/**
 * 
 * @author epomayay
 *
 */
@Service("perfilBO")
public class PerfilBOImpl extends GenericBOImpl<Perfil> implements PerfilBO {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private PerfilDAO perfilDAO;
	@Resource
	private ModuloDAO moduloDAO;
	
	private String codigoAntiguo;
	
	@Transactional(readOnly=true)
	public List<Modulo> findModulosDisponibles(Perfil perfil) throws BOException {
		
		HashMap<String, String> parametros = new HashMap<String, String>();
		parametros.put("estado", Constantes.VAL_ACTIVO);
		List<Modulo> listaTodosModulos = super.executeQuery(Modulo.class, " where estado = '1' ", " order by codigo ");
		List<Modulo> listaModulosDisponibles = new ArrayList<Modulo>();
		List<Modulo> listaModulosSeleccionados = findModulosSeleccionados(perfil);
		boolean encontrado = false;
		for(Modulo modulo : listaTodosModulos){
			for(Modulo moduloSel : listaModulosSeleccionados){
				if(modulo.getId().equals(moduloSel.getId())){
					encontrado = true;
					break;
				}
			}
			if(!encontrado){
				listaModulosDisponibles.add(modulo);
			}
			encontrado = false;
		}
		return listaModulosDisponibles;
	}
	
	
	
	@Transactional(readOnly=true)
	public List<Modulo> findModulosSeleccionados(Perfil perfil)	throws BOException{
		List<Modulo> listaModulos  = new ArrayList<Modulo>();
		List<Modulo> lista = new ArrayList<Modulo>();
		if(perfil.getId() != null){
			listaModulos = super.findById(Perfil.class, perfil.getId()).getModulos();
			for(Modulo modulo : listaModulos){
				lista.add(modulo);
			}
		}
		return lista;
	}
	
	

	public Perfil findById(Long id) throws BOException{
		Perfil perfil = null;
		perfil = super.findById(Perfil.class, id);
		return perfil;
	}

	public List<Perfil> findPerfiles(Perfil perfil) throws BOException, DAOException {
		List<Perfil> listaPerfiles = perfilDAO.findPerfiles(perfil);
		return listaPerfiles;
	}

	public boolean validate(Perfil t) throws BOException{
		
		if(!t.getCodigo().equals(this.codigoAntiguo)){
			HashMap<String, String> parametros = new HashMap<String, String>();
			parametros.put("codigo", t.getCodigo());
			List<Perfil> listaTemp = super.findByParams(Perfil.class, parametros);
			if(!listaTemp.isEmpty()){
				throw new BOException("El c\u00F3digo ya esta registrado.");
			}
		}
		return true;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Perfil perfil, String codigoAntiguo) throws BOException, DAOException {
		this.codigoAntiguo = codigoAntiguo;
		super.save(perfil);
	}
	
	@Override
	public Grid<Perfil> findToGrid(Perfil perfil,String order, int page, int rows)
	throws BOException, DAOException {
		String where="where ";	
		
		List<Perfil> listaPerfil= findPerfiles(perfil);
		
		for(Perfil obj:listaPerfil){
			obj.setModulos(null);
		}
		
	    return super.findToGridList(listaPerfil,where, " order by "+order,page,rows);
		
	}

}
