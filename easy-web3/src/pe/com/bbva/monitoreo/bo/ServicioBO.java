package pe.com.bbva.monitoreo.bo;

import java.util.List;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.monitoreo.domain.Servicio;

public interface ServicioBO {
	public List<Servicio> findServicios(Servicio servicio) throws Exception;
	public boolean validate(Servicio servicio) throws BOException;
	public void save(Servicio servicio, String urlAntiguo) throws BOException, DAOException;
	public Servicio findById(Long id) throws BOException, DAOException;
	public void delete(Servicio servicio)throws BOException;
	public void deleteLog(Class servicio, Long id)throws BOException;
	public Grid<Servicio> findToGrid(Servicio servicio,String order, int page, int rows)throws Exception;
	public String testByUrl(Servicio servicio, String urlServicio)throws BOException,DAOException;
	public List<String> Listaids(List<String> listaIds);
	public String testByUrlAccount(Servicio servicio,String urlAntiguo,String user,String password)throws Exception;
}
