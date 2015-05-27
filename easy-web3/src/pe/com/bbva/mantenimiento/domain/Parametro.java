package pe.com.bbva.mantenimiento.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.com.bbva.core.domain.EntidadBase;

@Entity
@Table(name="TMONAPP_PARAMETRO",schema="MONAPP")
@NamedQueries({
@NamedQuery(name="obtieneParametroPorCodigo", 
			query = " select o from Parametro o where o.codigo = ?"),
@NamedQuery(name="obtieneParametroPorCodigoSinActual", 
					query = " select o from Parametro o where o.codigo = ? and o.id <> ?"),
@NamedQuery(name="obtieneParametroPorId", 
					query = " select o from Parametro o where o.id = ?")					
})
@SequenceGenerator(name = "SEQ_PARAMETRO", sequenceName = "MONAPP.SEQ_PARAMETRO", allocationSize = 1, initialValue = 1)
public class Parametro extends EntidadBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String codigo;
	private String valor;
	private String descripcion;
		
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_PARAMETRO")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PARAMETRO")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="CODIGO", length=300, nullable=false, unique=true)
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="VALOR", length=200, nullable=false)
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Column(name="DESCRIPCION", length=300)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
			
}
