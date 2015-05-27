package pe.com.bbva.core.bo;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pe.com.bbva.core.dao.GenericDAOImpl;

/**
 * 
 * @author epomayay
 *
 * @param <T>
 */
public class GenericBOSQLServerImpl<T> extends GenericBOImpl {

	@Override
	@Resource(name="genericDAOSQLSERVER")
	public void setGenericDAO(GenericDAOImpl genericDAO) {
		super.setGenericDAO(genericDAO);
	}
}
