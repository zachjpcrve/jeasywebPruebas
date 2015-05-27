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
import pe.com.bbva.seguridad.bo.ModuloBO;
import pe.com.bbva.seguridad.dao.ModuloDAO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.util.Constantes;

/**
 * 
 * @author epomayay
 *
 */
@Service("moduloBO")
public class ModuloBOImpl extends GenericBOImpl<Modulo> implements ModuloBO {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private ModuloDAO moduloDAO;
	
	private String codigoAntiguo;
	
	public List<Modulo> findModulos(Modulo modulo) throws Exception {
		
		List<Modulo> listaModulos =moduloDAO.findModulos(modulo);
		return listaModulos;
	}

	public boolean validate(Modulo modulo) throws BOException{
		if(!modulo.getCodigo().equals(codigoAntiguo)){
			HashMap<String, String> parametros = new HashMap<String,String>();
			parametros.put("codigo", modulo.getCodigo());
			List<Modulo> listaTemp = super.findByParams(Modulo.class, parametros);
			if(!listaTemp.isEmpty()){
				throw new BOException("El c\u00F3digo ya esta registrado");
			}
		}
		if(modulo.getTipoModulo().getId().equals(Constantes.VAL_TIPO_OPCION) ||
		   modulo.getTipoModulo().getId().equals(Constantes.VAL_TIPO_SUB_OPCION)){
			if(modulo.getSuperior() == null){
				throw new BOException("Seleccione un padre");
			}
		}
		return true;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Modulo modulo , 
					 String codigoAntiguo) throws BOException, DAOException {
		this.codigoAntiguo = codigoAntiguo;
		super.save(modulo);
	}

	public List<Modulo> findPadres() throws BOException, DAOException {
		List<Modulo> listaPadres = super.executeListNamedQuery("listaPadres", new ArrayList<String>());
		return listaPadres;
	}

	public Modulo findById(Long id) throws BOException, DAOException {
		Modulo modulo = super.findById(Modulo.class, id);
		return modulo;
	}

	@Override
	public Grid<Modulo> findToGrid(Modulo modulo,String order, int page, int rows)
	throws Exception {
		String where="where ";	
		
		List<Modulo> listaModulo= findModulos(modulo);
		
		return super.findToGridList(listaModulo,where, " order by "+order,page,rows);
		
	}

}
