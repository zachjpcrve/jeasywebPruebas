package pe.com.bbva.mantenimiento.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.dao.ParametroDAO;
import pe.com.bbva.mantenimiento.domain.Parametro;

@Service("parametroDAO")
public class ParametroDAOImpl extends GenericDAOImpl<Parametro> implements ParametroDAO{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public ParametroDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public List<Parametro> findParametros(Parametro parametro)throws BOException, DAOException {
		String where = " where upper(descripcion) like upper('%" +
						(parametro.getDescripcion()== null?"":parametro.getDescripcion())+
						"%')";
		if(parametro.getCodigo()!= null &&
		   !parametro.getCodigo().equals("")){
			where = where +" and upper(codigo) like upper('%" +
					parametro.getCodigo()+
					"%') ";
		}
		if(parametro.getValor()!= null &&
				   !parametro.getValor().equals("")){
					where = where +" and upper(valor) like upper('%" +
							parametro.getValor()+
							"%') ";
		}
		
		if(parametro.getEstado()!= null &&
				   !parametro.getEstado().equals("")){
					where = where +" and upper(estado) like upper('%" +
							parametro.getEstado()+
							"%') ";
		}
		
		String orders = " order by fechaCreacion desc,codigo, descripcion";
		List<Parametro> listaParametros = super.executeQuery(Parametro.class,where,orders);
		return listaParametros;
		}

}
