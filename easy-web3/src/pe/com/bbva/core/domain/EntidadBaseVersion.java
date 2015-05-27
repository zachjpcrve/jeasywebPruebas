package pe.com.bbva.core.domain;

import javax.persistence.Column;
import javax.persistence.Version;

/**
 * Clase que debe ser utilizada para poder manejar versiones
 * y dar seguiridad a las tablas para usuarios concurrentes
 * 
 * @author epomayay
 *
 */
public class EntidadBaseVersion extends EntidadBase {

	private Integer version;
	
	@Version
	@Column(name="VERSION")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
