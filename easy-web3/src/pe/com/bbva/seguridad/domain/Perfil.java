package pe.com.bbva.seguridad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.com.bbva.core.domain.EntidadBase;

@Entity
@Table(name="TMONAPP_PERFIL",schema="MONAPP")
@NamedQueries({
@NamedQuery(name="listaPerfilesActivos", 
			query = " from Perfil where estado='1' order by codigo"),				
})
@SequenceGenerator(name="SEQ_PERFIL",
				   initialValue=1,
				   allocationSize=1,
				   sequenceName="MONAPP.SEQ_PERFIL")
public class Perfil extends EntidadBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1912706698437392006L;

	/**
	 * PK de la tabla
	 */
	private Long id;
	
	/**
	 * Codigo unico del perfil
	 */
	private String codigo;
	
	/**
	 * descripcion o nombre del perfil
	 */
	private String descripcion;
	
	/**
	 * modulos que pose el perfil
	 */
	private List<Modulo> modulos = new ArrayList<Modulo>();
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_PERFIL")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PERFIL")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="CODIGO_PERFIL", length=6)
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="DESCRIPCION" ,length=20)
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToMany(
			targetEntity = Modulo.class,
			cascade={CascadeType.PERSIST,CascadeType.MERGE},
			fetch=FetchType.LAZY
	)
	@JoinTable(
			name = "MONAPP.TMONAPP_PERFIL_MODULO",
			joinColumns =@JoinColumn(name="ID_PERFIL") ,
			inverseJoinColumns = @JoinColumn(name = "ID_MODULO")
	)
	public List<Modulo> getModulos() {
		return modulos;
	}
	
	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}
}
