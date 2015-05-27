package pe.com.bbva.mantenimiento.domain;

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
import javax.persistence.Transient;

import pe.com.bbva.core.domain.EntidadBase;
import pe.com.bbva.util.Constantes;

@Entity
@Table(name="TMONAPP_TABLA_DE_TABLA",schema="MONAPP")
@NamedQueries({
@NamedQuery(name="listaDetalleTabla", 
			query = " from Tabla  where padre.id = ? and estado='"+Constantes.VAL_ACTIVO+"' order by padre.id"),
@NamedQuery(name="obtieneTablaPadrePorCodigo", 
			query = " select o from Tabla o where o.codigo = ? and o.padre.id is null"),
@NamedQuery(name="obtieneTablaPadrePorCodigoSinActual", 
			query = " select o from Tabla o where o.codigo = ? and o.id <> ? and o.padre.id is null"),
@NamedQuery(name="obtieneTablaHijaPorCodigo", 
			query = " select o from Tabla o where o.codigo = ? and o.padre.id = ?"),
@NamedQuery(name="obtieneTablaHijaPorCodigoSinActual", 
			query = " select o from Tabla o where o.codigo = ? and o.padre.id = ? and o.id <> ?"),
@NamedQuery(name="listaTablasPadre", 
			query = " select o from Tabla o where o.estado = '1' and o.padre.id is null "),
@NamedQuery(name="listaTablasPadreRecur", 
			query = " select distinct o from Tabla o where o.padre.id is null  or (select count(obj) from Tabla obj where obj.padre.id=o.id)>0  order by o.id "),			
@NamedQuery(name="obtieneTablaPorId", 
			query = " select o from Tabla o where o.id = ?")					
})
@SequenceGenerator(name = "SEQ_TABLA_TABLA", 
				   sequenceName = "MONAPP.SEQ_TABLA_TABLA", 
				   allocationSize = 1, 
				   initialValue = 1) 
				 
public class Tabla extends EntidadBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;//ID_TABLA	NUMBER
	private String codigo;//CODIGO	VARCHAR2(30 BYTE)
	private String descripcion;//DESCRIPCION	VARCHAR2(200 BYTE)
	private Tabla padre;//ID_TABLA_DE_TABLA	NUMBER
	private String abreviado; // ABREVIADO VARCHAR2(20 BYTE)
	private Integer combinar;
		
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TABLA_TABLA")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TABLA")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="CODIGO")
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_TABLA_PADRE")
	public Tabla getPadre() {
		return padre;
	}
	
	public void setPadre(Tabla padre) {
		this.padre = padre;
	}
	
	@Column(name="ABREVIADO")
	public String getAbreviado() {
		return abreviado;
	}

	public void setAbreviado(String abreviado) {
		this.abreviado = abreviado;
	}
	@Transient
	public Integer getCombinar() {
		return combinar;
	}

	public void setCombinar(Integer combinar) {
		this.combinar = combinar;
	}
	
}