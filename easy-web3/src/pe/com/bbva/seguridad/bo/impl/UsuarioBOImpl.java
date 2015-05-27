package pe.com.bbva.seguridad.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.bbva.core.bo.GenericBOImpl;
import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.core.exceptions.ValidationException;
import pe.com.bbva.mantenimiento.domain.Parametro;
import pe.com.bbva.seguridad.bo.UsuarioBO;
import pe.com.bbva.seguridad.dao.ModuloDAO;
import pe.com.bbva.seguridad.dao.PerfilDAO;
import pe.com.bbva.seguridad.dao.UsuarioDAO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.seguridad.domain.Usuario;

/**
 * 
 * @author epomayay
 *
 */
@Service("usuarioBO")
public class UsuarioBOImpl extends GenericBOImpl<Usuario> implements UsuarioBO {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private PerfilDAO perfilDAO;
	@Resource
	private UsuarioDAO usuarioDAO;
	@Resource
	private ModuloDAO moduloDAO;
	
	private String codigoAntiguo;
			
	public boolean validate(Usuario usuario) throws BOException, DAOException{
		if(!usuario.getCodigo().equals(this.codigoAntiguo)){
			HashMap<String,String> parametros = new HashMap<String, String>();
			parametros.put("codigo", usuario.getCodigo());
			List<Usuario> listaTemp = super.findByParams(Usuario.class,parametros);
			if(!listaTemp.isEmpty()){
				throw new BOException("El código ya esta registrado.");
			}
		}
		return true;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Usuario usuario, 
					 String codigoAntiguo) throws BOException, DAOException {
		this.codigoAntiguo = codigoAntiguo;
		super.save(usuario);
	}

	public List<Usuario> findUsuarios(Usuario usuario) throws BOException, DAOException {
		List<Usuario> lista =usuarioDAO.findUsuarios(usuario);
		return lista;
	}

	/**
	 * busca todos los perfiles que aun no fueron asignados al
	 * usuario
	 * @throws DAOException 
	 */
	@Transactional(readOnly=true)
	public List<Perfil> findPerfilesDisponibles(Usuario usuario)
			throws BOException, DAOException {
		List<Perfil> listaAllPerfiles = super.executeListNamedQuery("listaPerfilesActivos", new ArrayList<String>());
		List<Perfil> listaSeleccionados = findPerfilesSeleccionados(usuario);
		List<Perfil> listaDisponibles = new ArrayList<Perfil>();
		boolean encontrado = false;
		for(Perfil perfil : listaAllPerfiles ){
			for(Perfil perfilTemp : listaSeleccionados){
				if(perfilTemp.getId().equals(perfil.getId())){
					encontrado = true;
					break;
				}
			}
			if(!encontrado){
				listaDisponibles.add(perfil);
			}
			encontrado = false;
		}
		return listaDisponibles;
	}

	@Transactional(readOnly=true)
	public List<Perfil> findPerfilesSeleccionados(Usuario usuario)
			throws BOException, DAOException {
		List<Perfil> listaPerfiles = new ArrayList<Perfil>();
		List<Perfil> lista = new ArrayList<Perfil>(); 
		if(usuario.getCodigo() != null){
			Usuario usu =super.findById(Usuario.class, usuario.getId());
			if(usu != null){
				listaPerfiles = usu.getPerfiles();
				for(Perfil perfil : listaPerfiles){
					lista.add(perfil);
				}
			}
		}
		return lista;
	}

	public Usuario findById(Long id) throws BOException, DAOException {
		Usuario usuario = super.findById(Usuario.class, id);
		return usuario;
	}

	public List<Modulo> findModulosByUsuario(Usuario usuario)
			throws BOException, DAOException {
		
		List<Modulo> listaModulo = moduloDAO.findModulosByUsuario(usuario);
		return listaModulo;
	}

	/**
	 * Busca un usuario por su código
	 * @throws DAOException 
	 */
	public Usuario findByCodigo(String codigo) throws BOException, DAOException {
		Usuario usuario = null;
		HashMap<String,String> parametros = new HashMap<String,String>();
		parametros.put("codigo", codigo);
		List<Usuario> listaUsuario = super.findByParams(Usuario.class, parametros);
		if(!listaUsuario.isEmpty()){
			 usuario=listaUsuario.get(0);
		}
		return usuario;
	}
	
	@Override
	public Grid<Usuario> findToGrid(Usuario usuario,String order, int page, int rows)
	throws BOException, DAOException {
		String where="where ";	
		
		List<Usuario> listaUsuario= findUsuarios(usuario);
		for(Usuario obj:listaUsuario){
			obj.setPerfiles(null);
		}
	    return super.findToGridList(listaUsuario,where, " order by "+order,page,rows);
		
	}

}
