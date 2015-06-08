package pe.com.bbva.core.dao;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.domain.UsuarioSession;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.core.util.FechaUtil;
import pe.com.bbva.core.util.StringUtil;

/**
 * Clase Generica para trabaja la capa de acceso a datos
 * 
 * @author EPOMAYAY
 *
 * @param <T> Objeto con el que se trabajara
 */

@Service("genericDAO")
public class GenericDAOImpl <T> extends HibernateDaoSupport  {

	Logger logger = Logger.getLogger(this.getClass());
	/**
	 * Template para la conexion a datos spring-hibernate
	 */
	
	/**
	 * Usuario loggeado actualmente este usuario 
	 * es verificado y validado contra Host
	 * este usuario debe ser pasaso por los action hacia
	 * cada DAO
	 */
	private UsuarioSession usuarioSession = new UsuarioSession();

	@Autowired
	public GenericDAOImpl(SessionFactory sessionFactory) {  
		super.setSessionFactory(sessionFactory);  
	}


	

	/**
	 * Método que guarda y actualiza al mismo tiempo
	 * esto depende de que si el objeto a guardar o
	 * actualizar tiene un ID creado
	 * generalmente este método sera usado con objetos
	 * que tienen PK autogenerados
	 * @param t
	 * @throws DAOException
	 */
	public void saveOrUpdate(T t) throws DAOException{
		getHibernateTemplate().saveOrUpdate(t);
	}
	
	/**
	 * Este método sera usado solo para guardar 
	 * generalmente usado para alamacenar objetos
	 * donde su PK son pasados de forma manual
	 * @param t
	 * @throws DAOException
	 */
	public void save(T t) throws DAOException{
		getHibernateTemplate().save(t);
	}	
	
	/**
	 * Este método sera usado solo actualizar 
	 * generalmente usado para alamacenar objetos
	 * donde su PK son pasados de forma manual
	 * @param t
	 * @throws DAOException
	 */
	public void update(T t) throws DAOException{
		getHibernateTemplate().update(t);
	}
	
	/**
	 * Método para eliminar los objetos
	 * @param <TID> Tipo de dato de la PK
	 * @param id
	 * @throws DAOException
	 */
	public void delete(T t) throws DAOException{
		getHibernateTemplate().delete(t);
	}
	
	/**
	 * M�todo para buscar un objeto por su ID
	 * @param <TID> Tipo de dato del PK
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public<TID> T findById(Class c, TID id) throws DAOException{
		T t = null;
		List list = null;
		if(id instanceof String){
			list= getHibernateTemplate().find("from "+ c.getSimpleName() +" where id='"+id+"'");
		}else{
			list= getHibernateTemplate().find("from "+ c.getSimpleName() +" where id="+id);
		}
		if(!list.isEmpty()){
			t = (T)list.get(0);
		}
		return t;
	}
	
	/**
	 * Metodo para buscar un objeto con sus atributos
	 * se debe pasar el map con el mismo nombre que tienen
	 * los atributos
	 * si no se pasan valores retornara toda la tabla
	 * @param <TID> Tipo de dato del PK
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public<TD> List<T> findByParams(Class c, HashMap<String,TD> maparib_val) throws DAOException{
		List<T> list = null;
		String criterio = " ";
		String query = "from "+ c.getSimpleName() +" ";
		if(maparib_val != null &&
		   !maparib_val.isEmpty()){
			criterio = " where ";
			for(Entry<String, TD> entri :maparib_val.entrySet())
				if(entri.getValue() instanceof String){
					criterio = criterio +" " + entri.getKey()+" = '"+entri.getValue()+"' and";
				}else{
					criterio = criterio +" " + entri.getKey()+" = "+entri.getValue()+" and";
				}
			criterio = criterio.substring(0,
										  criterio.length()-3);
		}
		logger.info(query+criterio);
		list = getHibernateTemplate().find(query+criterio);
		return list;
	}
	
	
	/**
	 * Elimina de forma logica una entidad
	 * para poder ejecutar correctamente este metodo
	 * la entidad debe tener un atributo mapeado con el
	 * nombre estado
	 * @param t
	 * @throws BOException
	 */
	public void deleteLog(Class clase, Long id) throws DAOException{
		try {
			String hql = "update "+ clase.getSimpleName()+ " set estado='0' where id="+id;
			executeHQLUpdate(hql);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public List executeQuery(Class clase, 
			 String where,
			 String orders,Integer firstResult,Integer maxResults) throws DAOException {
		List lista = null;
	    Session ses=null;
		try {
			if(where==null){
				where = "";
			}
			if(orders == null){
				orders = "";
			}
			ses = getHibernateTemplate().getSessionFactory().openSession();
			Query query = ses.createQuery("from "+clase.getSimpleName()+" "+where+" "+orders);
			if(firstResult!=null){
			query.setFirstResult(firstResult);
			}
			if(maxResults!=null){
			query.setMaxResults(maxResults);
			}
	       lista = query.list();
		} catch (HibernateException e) {
			throw new DAOException(e.getMessage());
		}finally{
			 try {
				ses.close();
			} catch (HibernateException e) {
				throw new DAOException(e.getMessage());
			}
		}
	    return lista;
	}
	
	/**
	 * Permitira la busqueda avanzada de objetos usando la clase criteria.
	 * para ello hay que enviar como parametros la clase sobre la cual
	 * se realizara la busqueda y luego enviar todos los criterios de 
	 * b�squeda que se tenga por medio de un objeto criteria.
	 * 
	 * @param c
	 * @param criteria
	 * @return
	 * @throws DAOException
	 */
	public List executeQuery(Class clase, 
							 String where,
							 String orders) throws DAOException {
		
	    return executeQuery(clase, where, orders, null, null);
	}

	/**
	 * Permitira la busqueda avanzada de objetos usando la clase criteria.
	 * para ello hay que enviar como parametros la clase sobre la cual
	 * se realizara la busqueda y luego enviar todos los criterios de 
	 * b�squeda que se tenga por medio de un objeto criteria.
	 * 
	 * @param c
	 * @param criteria
	 * @return
	 * @throws DAOException
	 */
	public List executeHqlParam(String hql,List listParam) throws DAOException {
		List lista = null;
	    Session ses=null;
		try {
			
		   ses = getHibernateTemplate().getSessionFactory().openSession();
		   Query query = ses.createQuery(hql);
		   for (Iterator itDescriptorParam = listParam.iterator(); itDescriptorParam.hasNext();) {
               Map mpParam = (Map) itDescriptorParam.next();
               query.setParameter((String) mpParam.get("name"), mpParam.get("value"), (Type) mpParam.get("type"));
           }
		   
           lista = query.list();
		} catch (HibernateException e) {
			throw new DAOException(e.getMessage());
		}finally{
			 try {
				ses.close();
			} catch (HibernateException e) {
				throw new DAOException(e.getMessage());
			}
		}
	    return lista;
	}
	
	/**
	 * Metodo para buscar un objeto con sus atributos
	 * se debe pasar el map con el mismo nombre que tienen
	 * los atributos
	 * si no se pasan valores retornara toda la tabla
	 * el metodo tambien permite la ordenacion por diferentes atributos
	 * para ello tambien hay que pasar los campos a ordenar con sus 
	 * respectivos criterios campo asc campo  desc etc.
	 * @param <TD>
	 * @param c
	 * @param maparib_val
	 * @param order
	 * @return
	 * @throws DAOException
	 */
	public<TD> List<T> findByParamsOrder(Class c, HashMap<String,TD> maparib_val, HashMap<String,String> order) throws DAOException{
		List<T> list = null;
		String criterio = " ";
		String query = "from "+ c.getSimpleName() +" ";
		String orders = " order by ";
		if(maparib_val != null &&
		   !maparib_val.isEmpty()){
			criterio = " where ";
			for(Entry<String, TD> entri :maparib_val.entrySet()){
				if(entri.getValue() instanceof String){
					criterio = criterio +" " + entri.getKey()+" = '"+entri.getValue()+"' and";
				}else{
					criterio = criterio +" " + entri.getKey()+" = "+entri.getValue()+" and";
				}
			criterio = criterio.substring(0,
										  criterio.length()-3);
			}
			
		}
		if(order != null &&
		   !order.isEmpty()){
			boolean flagfirst = true;
			for(Entry<String, String> entri :order.entrySet()){
				if(flagfirst){
					orders = orders +entri.getKey()+" " + entri.getValue();
				}else{
					orders = " , "+orders +entri.getKey()+" " + entri.getValue() ;
				}
			}
		}
		logger.info(query+criterio+orders);
		list = getHibernateTemplate().find(query+criterio+orders);
		return list;
	}
	
	/**
	 * Metodo para buscar un objeto con sus atributos
	 * se debe pasar el map con el mismo nombre que tienen
	 * los atributos
	 * si no se pasan valores retornara toda la tabla
	 * @param <TID> Tipo de dato del PK
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public<TD> List findByParams2(Class c, HashMap<String,TD> maparib_val) throws DAOException{
		List list = null;
		String criterio = " ";
		String query = "from "+ c.getSimpleName() +" ";
		if(maparib_val != null &&
		   !maparib_val.isEmpty()){
			criterio = " where ";
			for(Entry<String, TD> entri :maparib_val.entrySet())
				if(entri.getValue() instanceof String){
					criterio = criterio +" " + entri.getKey()+" = '"+entri.getValue()+"' and";
				}else{
					criterio = criterio +" " + entri.getKey()+" = "+entri.getValue()+" and";
				}
			criterio = criterio.substring(0,
										  criterio.length()-3);
		}
		logger.info(query+criterio);
		list = getHibernateTemplate().find(query+criterio);
		return list;
	}
	
	
	/**
	 * Método para buscar objetos el criterio de 
	 * búsqueda esta en fución al estado en que se encuentre
	 * el objeto
	 * @param t
	 * @return
	 * @throws DAOException
	 */
	public List<T> findObjects() throws DAOException{
		List<T> lista = null;
		return lista;
	}
	
	public List executeSQL(String sql) throws DAOException{
		List lista = null;
		Session session = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			lista =  session.createSQLQuery(sql).list();
		}finally{
			session.close();
		}
		return lista;
	}
	
	public List executeSQL(String sql, Class entity ) throws DAOException{
		List lista = null;
		Session session = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			lista =  session.createSQLQuery(sql).addEntity(entity).list();
		}finally{
			session.close();
		}
		return lista;
	}
	
	public int executeUpdateSQL(String sql) throws DAOException{
		Session session = null;
		int rowCount=0;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			rowCount =session.createSQLQuery(sql).executeUpdate();
		}finally{
			session.close();
		}
		return rowCount;
		
	}
	
	/**
	 * Retorna una lista con objetos HashMap las llaves de los elementos map
	 * seran los alias declarados en las columnas de la consulta.
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<HashMap<String,String>> executeSQLtoMap(String sql) throws DAOException{
		List<HashMap<String,String>> lista = null;
		Session session = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query =  session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			lista = query.list();
		}finally{
			session.close();
		}
		return lista;
	}
	
	public int executeNamedQuery(String nameQuery,
								 List parametros) throws DAOException{
		Session session = null;
		int res =0;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery(nameQuery);
			int i=0;
			for(Object param : parametros){
				query.setParameter(i++, param);	
			}
			res = query.executeUpdate();
						
		}finally{
			session.close();
		}
		return res;
	}
	
	public List executeListNamedQuery(String nameQuery,
			 							 List parametros) throws DAOException{
		Session session = null;
		List lista = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery(nameQuery);
			int i=0;
			for(Object param : parametros){
				query.setParameter(i++, param);	
			}
			lista = query.list();
			
		}finally{
			session.close();
		}
		return lista;
	}
	
	/**
	 * M�todo para enviar consultas HQL de insercion
	 * actualizacion on eliminacion de datos.
	 * 
	 * @param hql 
	 * @return catidad de registros afectados
	 */
	public int executeHQLUpdate(String hql) throws DAOException{
		Session session = null;
		 int rowCount =0;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
		    Query query = session.createQuery(hql);
		    rowCount = query.executeUpdate();
		}finally{
			session.close();
		}
	    return rowCount;

	}
	
	public List<T> listNamedQuery(String nameQuery, List parametros) throws DAOException
	{
		Session session = null;
		List<T> lista = null;
		try{
		session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.getNamedQuery(nameQuery);
		int i=0;
		for(Object param : parametros){
		query.setParameter(i++, param);	
		}
		lista = (List<T>)query.list();
			
		}finally{
		session.close();
		}
		return lista;
	}
	
	public List<T> listNamedQueryPaginacion(String nameQuery, List parametros,int filaInicial,int filaFinal) throws DAOException
	{
		Session session = null;
		List<T> lista = null;
		try{
		session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.getNamedQuery(nameQuery);
		query.setFirstResult(filaInicial);
		query.setMaxResults(filaFinal);
		int i=0;
		for(Object param : parametros){
		query.setParameter(i++, param);	
		}
		lista = (List<T>)query.list();
			
		}finally{
		session.close();
		}
		return lista;
	}
		
	public void saveCollection(Collection<?> collection)throws DAOException{
		getHibernateTemplate().saveOrUpdateAll(collection);
	}
	
	public void deleteCollection(Collection<?> collection)throws DAOException{
		getHibernateTemplate().deleteAll(collection);
	}
	
	public List<?> findObjects(Class clase,Map<String, Object> parameters)throws DAOException {

	      Session session = null;

	      try {
	         Criteria criteria = null;
	         List<?> lista = null;

	         session = getHibernateTemplate().getSessionFactory().openSession();  //getSession();
	         criteria = session.createCriteria(clase);

	         for (Iterator<?> it = parameters.entrySet().iterator(); it.hasNext();) {
	            Entry<?, ?> entry = (Entry<?, ?>) it.next();

	            if(entry.getValue() instanceof Date){
	            	
					criteria.add(Restrictions.sqlRestriction("to_char("+entry.getKey().toString()+",'dd/MM/yyyy') = ?", FechaUtil.formatFecha((Date)entry.getValue(), "dd/MM/yyyy") ,Hibernate.STRING));
				}else if(entry.getValue() instanceof Map)
				{
					
					Entry<?, ?> entryx = (Entry<?, ?>) ((Map)entry.getValue()).entrySet().iterator().next();
					if(entryx.getKey().toString().trim().equals("IN")){
						
						
						criteria.add(
								Restrictions.sqlRestriction(
										entry.getKey().toString()+entryx.getKey().toString()+
										"("+entryx.getValue().toString()+")"));	
					}
					
				}else{
					criteria.add(Restrictions.eq(entry.getKey().toString(), entry.getValue()));
				}
				
	         }

	         criteria.addOrder(Order.desc("id"));

	         lista = criteria.list();
	         return lista;
	      } finally {
	         try {
	     		session.close();
	         } catch (Exception e) {
	            throw new DAOException(e.getMessage(), e);
	         }
	      }
	   }

	public void saveBeansCollection(List<?> listaBeans)throws DAOException {
		Session session=null;
		Transaction tx =null;
		try {
			session =  getHibernateTemplate().getSessionFactory().openSession();// sessionFactory.openSession();
			tx = session.beginTransaction();
			int tamanio=listaBeans.size();
			for ( int i=0; i<tamanio; i++ ) {
				
				//session.save(listaBeans.get(i));
				session.saveOrUpdate(listaBeans.get(i));
				if ( (i+1) % 20 == 0 ) { //20, same as the JDBC batch size
					//flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			tx.commit();

	    }
	    catch(Exception e) {
	    	tx.rollback();
	    	logger.error(StringUtil.getStackTrace(e));
	        throw new DAOException(e.getMessage());
	    }finally{
			if(session!=null) session.close();
	    }
	}
}
