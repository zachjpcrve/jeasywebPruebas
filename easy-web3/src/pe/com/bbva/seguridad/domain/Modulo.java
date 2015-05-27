package pe.com.bbva.seguridad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
/**
 * 
 * @author epomayay
 *
 */
@Entity
@Table(name="TMONAPP_MODULO",schema="MONAPP")
@NamedQueries({
@NamedQuery(name="listaPadres", 
			query = " from Modulo where tipoModulo.id in ("+
					Constantes.VAL_TIPO_MODULO_STRING+","+
					Constantes.VAL_TIPO_OPCION_STRING+") and estado ='"+
					Constantes.VAL_ACTIVO+"' order by superior.id"),				
})
@SequenceGenerator(name="SEQ_MODULO",
				   initialValue=1,
				   allocationSize=1,
				   sequenceName="MONAPP.SEQ_MODULO")
public class Modulo extends EntidadBase{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1293947205381901444L;

	/** 
	 * pk de la base de datos 
	 **/
	private Long id;
	   
	/** 
	 * codigo unico de la identidad 
	 **/
	private String codigo;
	   
	/** 
	 * nombre del modulo sub modulo u opcion 
	 **/
	private String descripcion;
	
	private Tabla tipoModulo;
	/** 
	 * id del padre de la opcion 
	 **/
	private Modulo superior;
	   
	/** 
	 * nombre de la clase controlador o formularo que se va activar 
	 **/
	private String controlador;
	   
	/** 
	 * nombre del metodo que se va activar
	**/
	private String metodo;
	
	/**
	 * indica el orden en que deben aparecer los modulos 
	 * con sus respectivas opciones y sub opciones
	 */
	private Integer orden;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_MODULO")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_MODULO")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="CODIGO_MODULO", length=6, nullable=true)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name="DESCRIPCION", length=60, nullable=true)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne
	@JoinColumn(name="ID_TIPOMODULO")
	public Tabla getTipoModulo() {
		return tipoModulo;
	}

	public void setTipoModulo(Tabla tipoModulo) {
		this.tipoModulo = tipoModulo;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "SUPERIOR_ID")
	public Modulo getSuperior() {
		return superior;
	}

	public void setSuperior(Modulo superior) {
		this.superior = superior;
	}

	@Column(name="CONTROLLADOR", length=100)
	public String getControlador() {
		return controlador;
	}

	public void setControlador(String controlador) {
		this.controlador = controlador;
	}

	@Column(name="METODO")
	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	@Column(name="ORDEN")
	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
