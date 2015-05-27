package pe.com.bbva.monitoreo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.com.bbva.core.domain.EntidadBase;

@Entity
@Table(name="TMONAPP_SERVICIO",schema="MONAPP")
@SequenceGenerator(name="SEQ_SERVICIO",
						initialValue=1,
						allocationSize=1,
						sequenceName="MONAPP.SEQ_SERVICIO")
public class Servicio extends EntidadBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String url;
	private boolean estado;
	private String nombre;
	private boolean req_aut;
	private String usuario;
	private String clave;
	private String descripcion;
	private String aplicativo;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_SERVICIO")
	@Column(name="ID_SERV")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="URL_SERV")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="ESTADO_SERV")
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Column(name="NOMBRE_SERV")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="REQ_AUTENTIFICACION")
	public boolean isReq_aut() {
		return req_aut;
	}
	public void setReq_aut(boolean req_aut) {
		this.req_aut = req_aut;
	}
	
	@Column(name="USUARIO_SERV")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Column(name="CLAVE_SERV")
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	@Column(name="DESCRIPCION_SERV")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="APLICATIVO")
	public String getAplicativo() {
		return aplicativo;
	}
	public void setAplicativo(String aplicativo) {
		this.aplicativo = aplicativo;
	}
}
