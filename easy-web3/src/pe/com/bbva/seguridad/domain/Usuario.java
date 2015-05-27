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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.com.bbva.core.domain.EntidadBase;

@Entity
@Table(name="TMONAPP_USUARIO",schema="MONAPP")
@SequenceGenerator(allocationSize=1, 
				   initialValue=1, 
				   sequenceName="MONAPP.SEQ_USUARIO",
				   name="SEQ_USUARIO")
public class Usuario extends EntidadBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * codigo del usuario
	 */
	private String codigo;
	
	/**
	 * nombre del usuairo
	 */
	private String nombres;
	
	/**
	 * perfiles del usuario
	 */
	private List<Perfil> perfiles = new ArrayList<Perfil>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_USUARIO")
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_USUARIO")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="CODIGO_USUARIO")
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="nombres", length=60)
	public String getNombres() {
		return nombres;
	}
	
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	
	@ManyToMany(
			targetEntity = Perfil.class,
			cascade={CascadeType.PERSIST,
					 CascadeType.MERGE},
			fetch=FetchType.LAZY
	)
	@JoinTable(
			name = "MONAPP.TMONAPP_USUARIO_PERFIL",
			joinColumns =@JoinColumn(name="ID_USUARIO") ,
			inverseJoinColumns = @JoinColumn(name = "ID_PERFIL")
	)
	public List<Perfil> getPerfiles() {
		return perfiles;
	}
	
	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}
}