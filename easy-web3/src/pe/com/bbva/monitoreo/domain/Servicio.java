package pe.com.bbva.monitoreo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.com.bbva.core.domain.EntidadBase;
import pe.com.bbva.mantenimiento.domain.Tabla;
import pe.com.bbva.util.Constantes;

@Entity
@Table(name="TMONAPP_SERVICIO",schema="MONAPP")
@NamedQueries({
@NamedQuery(name="listaPadresServ", 
			query = " from Servicio where tipoAmbiente.id in ("+
					Constantes.VAL_TIPO_CALIDAD_STRING+","+
					Constantes.VAL_TIPO_TEST_STRING+" ,"+
					Constantes.VAL_TIPO_PRODUCCION_STRING+") and estado ='"+
					Constantes.VAL_ACTIVO+"' order by superior.id"),				
})
@SequenceGenerator(name="SEQ_SERVICIO",
						initialValue=1,
						allocationSize=1,
						sequenceName="MONAPP.SEQ_SERVICIO")
public class Servicio extends EntidadBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String url;
	private String estado_serv;
	private String nombre;
	private Tabla tipoAmbiente;
	private String req_aut;
	private String usuario;
	private String clave;
	private String descripcion_serv;
	private Tabla tipoAplicativo;
	private Servicio superior;
	private Integer orden;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_SERVICIO")
	@Column(name="ID_SERV")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getEstado_serv() {
		return estado_serv;
	}
	public void setEstado_serv(String estado_serv) {
		this.estado_serv = estado_serv;
	}
	
	@Column(name="NOMBRE_SERV")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_TIPOAMBIENTE")
	public Tabla getTipoAmbiente() {
		return tipoAmbiente;
	}

	public void setTipoAmbiente(Tabla tipoAmbiente) {
		this.tipoAmbiente = tipoAmbiente;
	}
	@Column(name="REQ_AUTENTIFICACION")
	public String getReq_aut() {
		return req_aut;
	}
	public void setReq_aut(String req_aut) {
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
	public String getDescripcion_serv() {
		return descripcion_serv;
	}
	public void setDescripcion_serv(String descripcion_serv) {
		this.descripcion_serv = descripcion_serv;
	}
	
	@ManyToOne
	@JoinColumn(name="TIPO_APLICATIVO")
	public Tabla getTipoAplicativo() {
		return tipoAplicativo;
	}
	public void setTipoAplicativo(Tabla tipoAplicativo) {
		this.tipoAplicativo = tipoAplicativo;
	}
	
	@JoinColumn(name = "SUPERIOR_ID")
	public Servicio getSuperior() {
		return superior;
	}

	public void setSuperior(Servicio superior) {
		this.superior = superior;
	}

	@Column(name="ORDEN")
	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
