package pe.com.bbva.mantenimiento.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.dao.TablaDAO;
import pe.com.bbva.mantenimiento.domain.Tabla;

@Service("tablaDAO")
public class TablaDAOImpl extends GenericDAOImpl<Tabla> implements TablaDAO {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public TablaDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public List<Tabla> findDetalleTabla(Long idTabla) throws BOException, DAOException {
		List<Tabla> listaTablas = null;
		List<Long> parametros = new ArrayList<Long>();
		parametros.add(idTabla);
		listaTablas = super.executeListNamedQuery("listaDetalleTabla", parametros);
		return listaTablas;
	}
	
	
	public List<Tabla> findTablas(Tabla tabla) throws BOException, DAOException {
		String where =  " where upper(descripcion) like upper('%"+
						(tabla.getDescripcion()== null?"":tabla.getDescripcion())
						+"%')";
		if(tabla.getCodigo()!= null &&
		   !tabla.getCodigo().trim().equals("")){
			where = where +" and upper(codigo) like upper('%"+tabla.getCodigo()+"%') ";
		}
		if(tabla.getAbreviado()!= null &&
				   !tabla.getAbreviado().trim().equals("")){
					where = where +" and upper(abreviado) like upper('%"+tabla.getAbreviado()+"%') ";
		}
		if(tabla.getEstado()!= null &&
				   !tabla.getEstado().trim().equals("")){
					where = where +" and upper(estado) like upper('%"+tabla.getEstado()+"%') ";
		}
		if(tabla.getPadre()!= null && tabla.getPadre().getId()!=null){
			where = where +" and (padre.id = "+tabla.getPadre().getId()+" or id="+tabla.getPadre().getId()+")";
		}
		
		String orders = " order by fechaCreacion desc,padre.id,codigo, descripcion ";
		
		List<Tabla> listaTablas = super.executeQuery(Tabla.class,where,orders);
		return listaTablas;
	}
	
	public Tabla findDescripcionTabla(Long id, Long idPadre) throws BOException, DAOException {
		Tabla tabla = null;
		String where =  " where upper(id_tabla) = "+id;
		if(idPadre!= null){
					where = where +" and upper(id_tabla_padre) = "+idPadre;
				}
		String orders = "";
		
		List<Tabla> listaTablas = super.executeQuery(Tabla.class, where,orders);
		if (listaTablas != null && !listaTablas.isEmpty())
			tabla = listaTablas.get(0);
		return tabla;
	}
	
	public List<Tabla> findTablasPadre(Tabla tabla)throws BOException, DAOException{
		String where = "select o from "+Tabla.class.getName()+" o where o.padre.id is null and o.estado like '%" + (tabla.getEstado() != null && !tabla.getEstado().equals("")? tabla.getEstado().trim() :""  )
		+ "%'";
		if (tabla.getAbreviado() != null && !tabla.getAbreviado().trim().equals("")) {
			where = where + " and upper(o.abreviado) like upper('%"
					+ tabla.getAbreviado().trim() + "%') ";
		}
		
		if (tabla.getDescripcion() != null && !tabla.getDescripcion().trim().equals("")) {
		
			where = where + " and upper(o.descripcion) like upper('%" + tabla.getDescripcion().trim()
					+ "%')";
		
		}
		
		where=where+" order by fechaCreacion desc,codigo, descripcion";
		
		List<Tabla> lista = super.executeHqlParam(where, new ArrayList());
		return lista;
	}

}
