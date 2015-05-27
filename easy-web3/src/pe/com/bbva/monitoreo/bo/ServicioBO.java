package pe.com.bbva.monitoreo.bo;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.monitoreo.domain.Servicio;

public interface ServicioBO {
	public Grid<Servicio> findToGrid(Servicio servicio,String order, int page, int rows)throws Exception;
}
