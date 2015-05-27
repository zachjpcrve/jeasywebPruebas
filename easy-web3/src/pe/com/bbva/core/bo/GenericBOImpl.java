package pe.com.bbva.core.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.core.util.StringUtil;
import pe.com.bbva.mantenimiento.domain.Tabla;

/**
 * 
 * Clase generica para los BO
 * 
 * @author P018543
 *
 * @param <T>
 */
public  class GenericBOImpl <T>{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private GenericDAOImpl<T> genericDAO;
	
	@Resource
	public void setGenericDAO(GenericDAOImpl genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	/**
	 * 
	 * @param t
	 * @throws BOException
	 */
	public void beforeSave(T t) throws BOException{
			
	}
	
	public boolean validate(T t) throws BOException,DAOException{
		return true;
	}
	
	public void afterSave(T t) throws BOException{
		
	}
	
	public void doSave(T t) throws BOException{
		try {
			genericDAO.saveOrUpdate(t);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	
	public void save(T t) throws BOException,DAOException{	
		beforeSave(t);
		if(validate(t)){
			doSave(t);
		}
		afterSave(t);
	}
	
	
	public void onlySave(T t) throws BOException{
		try {
			genericDAO.save(t);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error(StringUtil.getStackTrace(e));
		}
	}
	
	public void saveOrUpdate(T t) throws BOException{
		try {
			genericDAO.saveOrUpdate(t);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	

	
	/**
	 * busca un objeto por su ID
	 * @param <TD> tipo de dato
	 * @param c clase a buscar
	 * @param id id del objeto
	 * @return
	 * @throws BOException
	 */
	public<TD> T findById(Class c,
						  TD id)throws BOException{
		T t = null;
		try {
			t = this.genericDAO.findById(c,
										 id);
		} catch (DAOException e) {
			throw new BOException(e);
		}
		return t;
	}
	
	public<TD> List<T> findByParams(Class c, 
									HashMap<String,TD> maparib_val) throws BOException{
		try {
			return this.genericDAO.findByParams(c, 
												maparib_val);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	
	public<TD> List<T> findByParamsOrder(Class c, 
										 HashMap<String,TD> maparib_val, 
										 HashMap<String,String> order) throws BOException{
		try {
			return this.genericDAO.findByParamsOrder(c, 
													 maparib_val, 
													 order);
		} catch (DAOException e) {
			throw new BOException(e);
		} catch (Exception e) {
			throw new BOException(e);
		}
	}
	
	/**
	 * la cadena enviada para el where y orders debe contener la 
	 * clausula where y order by al inicio de las cadenas respectivamente
	 * @param clase
	 * @param where
	 * @param orders
	 * @return
	 * @throws BOException
	 */
	public List executeQuery(Class clase, 
									  String where, 
									  String orders) throws BOException{
		try {
			return genericDAO.executeQuery(clase, 
										   where, 
										   orders);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	
	public List executeQueryHql(String hql,List listParam) throws BOException{
		try {
			return genericDAO.executeHqlParam(hql, listParam);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	
	public<TD> List findByParams2(Class c, 
								  HashMap<String,TD> maparib_val) throws BOException{
		try {
			return this.genericDAO.findByParams2(c,
												 maparib_val);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	
	public List executeSQL(String sql) throws BOException{
		List lista = null;
		try {
			lista = genericDAO.executeSQL(sql);
		} catch (DAOException e) {
			throw new BOException(e);
		}
		return lista;
	}
	
	public List executeSQL(String sql, Class entity )  throws BOException{
		List lista = null;
		try {
			lista = genericDAO.executeSQL(sql, entity);
		} catch (DAOException e) {
			throw new BOException(e);
		}
		return lista;
	}
	
	public int executeNamedQuery(String nameQuery,
			 					 List parametros)  throws BOException{
		int res = 0;
		try {
			res = genericDAO.executeNamedQuery(nameQuery, parametros);
		} catch (DAOException e) {
			throw new BOException(e);
		}
		return res;
	}
	
	public int executeUpdateSQL(String sql)throws DAOException{
		int rowCount=genericDAO.executeUpdateSQL(sql);
		return rowCount;
	}
	
	
	public List executeListNamedQuery(String nameQuery,
			 						  List parametros) throws BOException{
		List lista = null;
		try {
			lista = genericDAO.executeListNamedQuery(nameQuery, parametros);
		} catch (DAOException e) {
			throw new BOException(e);
		}
		return lista;
	}
			 
	public List<T> listNamedQuery(String nameQuery, List parametros)  throws BOException{
		List<T> lista = null;
		try {
			lista = (List<T>)genericDAO.listNamedQuery(nameQuery, parametros);
		} catch (DAOException e) {
		throw new BOException(e);
		}
		return lista;
	}
	
	public List<T> listNamedQueryPaginacion(String nameQuery, List parametros,int filaInicial,int filaFinal)  throws BOException{
		List<T> lista = null;
		try {
			lista = (List<T>)genericDAO.listNamedQueryPaginacion(nameQuery, parametros,filaInicial,filaFinal);
		} catch (DAOException e) {
		throw new BOException(e);
		}
		return lista;
	}
		
	public void saveCollection(Collection<?> collection)throws BOException{
		try {
			genericDAO.saveCollection(collection);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	/**
	 * Elimina de forma permanente una entidad
	 * @param t
	 * @throws BOException
	 */
	public void delete(T t) throws BOException{
		try {
			genericDAO.delete(t);
		} catch (DAOException e) {
			throw new BOException(e);
		}catch (Exception e) {
			if(e.getCause() != null &&
			   e.getCause() instanceof ConstraintViolationException){
				throw new BOException("El registro esta relacionado no es posible eliminarlo.");
			}
		}
	}
	
	/**
	 * Elimina de forma permanente un conjunto de entidades.
	 * @param collection
	 * @throws BOException
	 */
	public void deleteCollection(Collection<?> collection)throws BOException{
		try {
			genericDAO.deleteCollection(collection);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	
	public void update(T t) throws BOException{
		try {
			genericDAO.update(t);
		} catch (DAOException e) {
			throw new BOException(e);
		}
	}
	public Grid<T> findToGrid(T t,String where,String order,int page,int rows,int records) throws BOException, DAOException{
		Grid<T> grid=new Grid<T>();
		grid.setPage(page);
		grid.setRows(rows);
		double count;
		if(records==0){
		 count=genericDAO.executeQuery(t.getClass(), where,"").size();
		}else{
			count=records;
		}
		double pages=(count>0?Math.ceil( count/rows):0); 
		grid.setTotal((int)pages);
		grid.setRecords((int)count);
		List<T> dataModel=null;
		
		int firstResult=(page-1)*rows;
		dataModel=genericDAO.executeQuery(t.getClass(), where,order,firstResult,rows);
		grid.setDataModel(dataModel);
		return grid;
	}
	
	
	
	public Grid<T> findToGridList(List<T> list ,String where,String order,int page,int rows) throws BOException, DAOException{
		Grid<T> grid=new Grid<T>();
		List<T> newList=null;
		grid.setPage(page);
		grid.setRows(rows);
		double count;
		count=list.size();
		double pages=(count>0?Math.ceil( count/rows):0); 
		grid.setTotal((int)pages);
		grid.setRecords((int)count);
		int firstResult=(page-1)*rows;
		newList=divListGrid(list, firstResult, rows);
		grid.setDataModel(newList);
		return grid;
	}
	
	public List<T> divListGrid(List<T> list,int firstResult,int rows){
		List<T> newList=new ArrayList<T>();
		
		for(int i=firstResult;i<rows+firstResult;i++){
			if(i<list.size()){
			T t=list.get(i);
			newList.add(t);
			}
		}
		
		return newList;
	}
	
	public List<?> findObjects(Class clase,
							   Map<String, Object> parameters)throws BOException{
		List<?> lista = null;
		try {
			lista = (List<?>)genericDAO.findObjects(clase, parameters);
		} catch (DAOException e) {
			throw new BOException(e);
		}
		return lista;
   }
	
	public List<?> findTipos(Long idTipo) throws BOException{
		HashMap<String, Long> parametros = new HashMap<String, Long>();
		parametros.put("id", idTipo);
		List lista;
		try {
			lista = this.findByParams(Tabla.class, parametros);
		} catch (BOException e) {
			throw new BOException(e);
		} 
		return lista;
	}
	
	/**
	 * Elimina de forma logica una entidad
	 * para poder ejecutar correctamente este metodo
	 * la entidad debe tener un atributo mapeado con el
	 * nombre estado
	 * @param t
	 * @throws BOException
	 */
	public void deleteLog(Class clase, Long id) throws BOException{
		try {
			String hql = "update "+ clase.getSimpleName()+ " set estado='0' where id="+id;
			genericDAO.executeHQLUpdate(hql);
		} catch (Exception e) {
			throw new BOException(e);
		}
	}
	
	/**
	 * Actualiza los valores de una entidad
	 * para poder ejecutar correctamente este metodo
	 * la entidad debe tener un atributo mapeado con el
	 * nombre estado
	 * @param t
	 * @throws BOException
	 */
	public void updateHql(String hql) throws BOException{
		try {
			genericDAO.executeHQLUpdate(hql);
		} catch (Exception e) {
			throw new BOException(e);
		}
	}
	
	/**
	 * Metodo para obtener un bean de spring
	 * @param name
	 * @return
	 */
	public static Object getSpringBean(String name){
		WebApplicationContext context =
			WebApplicationContextUtils.getRequiredWebApplicationContext(
                                    ServletActionContext.getServletContext()
                        );
		return context.getBean(name);
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
		lista = genericDAO.executeSQLtoMap(sql);
		return lista;
	}
}
