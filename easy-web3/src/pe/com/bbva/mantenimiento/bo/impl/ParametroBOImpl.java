package pe.com.bbva.mantenimiento.bo.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.bbva.core.bo.GenericBOImpl;
import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.bo.ParametroBO;
import pe.com.bbva.mantenimiento.dao.ParametroDAO;
import pe.com.bbva.mantenimiento.domain.Parametro;

@Service("parametroBO")
public class ParametroBOImpl extends GenericBOImpl<Parametro> implements ParametroBO{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private ParametroDAO parametroDAO;
	
	private String codigoAntiguo;
	
	public Parametro findById(Long id) throws BOException {
		Parametro parametro = super.findById(Parametro.class, id);
		return parametro;
	}

	public List<Parametro> findParametros(Parametro parametro)
			throws BOException, DAOException {
		List<Parametro> listaParametros = parametroDAO.findParametros(parametro);
		return listaParametros;
	}

	public boolean validate(Parametro parametro) throws BOException{
		if(!parametro.getCodigo().equals(codigoAntiguo)){
			HashMap<String, String> parametros = new HashMap<String,String>();
			parametros.put("codigo", parametro.getCodigo());
			List<Parametro> listaTemp = super.findByParams(Parametro.class, parametros);
			if(!listaTemp.isEmpty()){
				throw new BOException("El código ya esta registrado");
			}
		}
		return true;
	}
	
	public Parametro findByNombreParametro(String codigo) throws BOException {
		
		Parametro parametro = null;
		try {
			HashMap<String, String> parametros = new HashMap<String, String>();
			parametros.put("codigo", codigo);
			List<Parametro> lista = super.findByParams(Parametro.class, parametros);
			if(lista !=null &&
			   !lista.isEmpty()){
				parametro = lista.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException(e.getMessage());
		}
		return parametro;
	}
	
	@Override
	public Grid<Parametro> findToGrid(Parametro parametro,String order, int page, int rows)
	throws BOException, DAOException {
		String where="where ";	
		
		List<Parametro> listaParametros= findParametros(parametro);
	    return super.findToGridList(listaParametros,where, " order by "+order,page,rows);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Parametro parametro , 
					 String codigoAntiguo) throws BOException, DAOException {
		this.codigoAntiguo = codigoAntiguo;
		super.save(parametro);
	}
	
	public List<Parametro> findParamListByDescrip(String descripcion) throws BOException {
		List<Parametro> lista = null;
		try {
			HashMap<String, String> parametros = new HashMap<String, String>();
			parametros.put("descripcion", descripcion);
			lista = super.findByParams(Parametro.class, parametros);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException(e.getMessage());
		}
		
		return lista;
	}
	
}
