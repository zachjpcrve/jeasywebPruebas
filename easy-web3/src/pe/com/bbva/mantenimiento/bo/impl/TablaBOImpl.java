package pe.com.bbva.mantenimiento.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import pe.com.bbva.core.bo.GenericBOImpl;
import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.bo.TablaBO;
import pe.com.bbva.mantenimiento.dao.TablaDAO;
import pe.com.bbva.mantenimiento.domain.Tabla;

@Component("tablaBO")
public class TablaBOImpl extends GenericBOImpl<Tabla> implements TablaBO {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private TablaDAO tablaDAO;
	
	
	private String codigoAntiguo;
	/**
	 * Este medoto solo retornar el detalle de las tablas 
	 * que esten con estado ACTIVO
	 * @throws DAOException 
	 * @throws DAOException 
	 */
	public List<Tabla> findDetalleTabla(Long idTabla) throws BOException, DAOException {
		List<Tabla> listaTablas = tablaDAO.findDetalleTabla(idTabla); 
		return listaTablas;
	}

	/**
	 * Recupera todas las tablas con estado ACTIVO e INACTIVO
	 * @throws DAOException 
	 */
	public List<Tabla> findAllTabla() throws BOException {
		List<Tabla> listaTablas = null;
		listaTablas = super.executeQuery(Tabla.class,""," order by padre.id,codigo asc");
		return listaTablas;
	}

	public Tabla findTablaById(Long id) throws BOException {
		Tabla tabla = super.findById(Tabla.class, id);
		return tabla;
	}

	public Tabla findTablaByCodigo(String codigo) throws BOException {
		HashMap<String, String > parametros = new HashMap<String, String>();
		parametros.put("codigo", codigo);
		Tabla tabla = null ;
		List<Tabla> lista = super.findByParams(Tabla.class, parametros);
		if(!lista.isEmpty()){
			tabla = lista.get(0);
		}
		return tabla;
	}
	
	public List<Tabla> findTablas(Tabla tabla) throws BOException, DAOException {
		List<Tabla> listaTablas = tablaDAO.findTablas(tabla);
		return listaTablas;
	}
	
	public Tabla findDescripcionTabla(Long id, Long idPadre) throws BOException, DAOException {		
		Tabla tabla = tablaDAO.findDescripcionTabla(id, idPadre);
		return tabla;
	}
	/**
	 * Obtiene toda la lista de tablas padre
	 * que tengan estado ACTIVO
	 * @return
	 * @throws DAOException 
	 */
	public List<Tabla> findTablasPadre()throws BOException{
		List<Tabla> lista = null;
		try {
			lista = super.executeListNamedQuery("listaTablasPadre",  
												new ArrayList<String>());
		} catch (BOException e) {
			throw new BOException(e);
		}
		return lista; 
	}
	
	public List<Tabla> findTablasPadreRecur()throws BOException{
		List<Tabla> lista = null;
		try {
			lista = super.executeListNamedQuery("listaTablasPadreRecur",  
												new ArrayList<String>());
		} catch (BOException e) {
			throw new BOException(e);
		}
		return lista; 
	}
	
	
	public List<Tabla> findTablasPadre(Tabla tabla)throws BOException, DAOException{
				
		List<Tabla> lista = tablaDAO.findTablasPadre(tabla);
		return lista;
	}
  
	public boolean validate(Tabla tabla) throws BOException{
		return true;
	}
	 
	
	public void save(Tabla tabla) throws BOException, DAOException {
		this.codigoAntiguo = codigoAntiguo; 
		super.save(tabla);
	}

	@Override
	public Grid<Tabla> findToGrid(Tabla tabla,
			String order, int page, int rows) throws BOException, DAOException {
		String where="where ";	
		
		List<Tabla> listaTablas= findTablas(tabla);
	    return super.findToGridList(listaTablas,where, " order by "+order,page,rows);
		
	}
}
