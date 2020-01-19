package datainfo.candidato.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import datainfo.candidato.dao.DaoImplementacao;
import datainfo.candidato.dao.DaoInterface;
import datainfo.candidato.model.Usuario;

@Controller
@RequestMapping(value="/usuario")
public class UsuarioController extends DaoImplementacao<Usuario> implements 
		DaoInterface<Usuario> {

	public UsuarioController(Class<Usuario> persistenceClass) { 
		super(persistenceClass); 
	}
	  
	/**
	 * Salva ou atualiza o usuario
	 * @param jsonUsuario
	 * @return ResponseEntity
	 * @throws Exception
	 */
	 @RequestMapping(value="salvar", method= RequestMethod.POST)
	 @ResponseBody
	 public ResponseEntity salvar (@RequestBody String jsonUsuario) throws Exception {
		 Usuario usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
		 
		 Usuario objeto = super.consultaPorCPF(usuario.getCpf());
		 
		 if (objeto == null) {
		 
			 if (usuario != null && usuario.getAtivo() == null){
				 usuario.setAtivo(false);
			 }
			 
			 super.salvarOuAtualizar(usuario);
			 return new ResponseEntity(HttpStatus.CREATED);
		 
		 } else {			 
			 
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		 }
		 
	 }
	
	
	/**
	* Retorna a lista de usuários cadastrados
	* @return JSON String de Usuários
	* @throws Exception
	*/
	@RequestMapping(value="listar/{numeroPagina}", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String listar(@PathVariable("numeroPagina") String numeroPagina) throws Exception {
		return new Gson().toJson(super.consultaPaginada(numeroPagina)); 
	}
	
	
	@RequestMapping(value="totalPagina", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String totalPagina() throws Exception {
		return ""+super.quantidadePagina(); 
	}
	 
	/**
	 * Delete o usuario informado
	 * @param codUsuario
	 * @return String vazia como resposta
	 * @throws Exception
	 */
	@RequestMapping(value="deletar/{codUsuario}", method=RequestMethod.DELETE)
	public  @ResponseBody String deletar (@PathVariable("codUsuario") String codUsuario) throws Exception {
		
		super.deletar(loadObjeto(Long.parseLong(codUsuario)));
		return "";
	}
	
	
	/**
	* Altera o status do usuario informado
	* @param codUsuario
	* @return String vazia como resposta
	* @throws Exception
	*/
	@RequestMapping(value="chavear/{params}", method=RequestMethod.POST)
	public  @ResponseBody String chavear (@PathVariable("params") String params) throws Exception {
		
		String[] parametros = params.split("|", 2);
		
		long codUsuario = Long.parseLong(parametros[0]);
		boolean status = Boolean.parseBoolean(parametros[1].substring(1));
		
		Usuario objeto = super.loadObjeto(codUsuario);
		objeto.setAtivo(status);
	 
		super.salvarOuAtualizar(objeto);
		
		return "";
	}

	
	
	/**
	 * Consulta e retorna o usuario com o codigo informado
	 * @param codUsuario
	 * @return JSON usuario pesquisado
	 * @throws Exception
	 */
	@RequestMapping(value="buscarusuario/{codUsuario}", method=RequestMethod.GET)
	public  @ResponseBody String buscarUsuario (@PathVariable("codUsuario") String codUsuario) throws Exception {
		Usuario objeto = super.loadObjeto(Long.parseLong(codUsuario));
		if (objeto == null) {
			return "{}";
		}
		return new Gson().toJson(objeto);
	}
	
	
	/**
	 * Consulta e retorna o usuario com o nome  informado
	 * @param nomeUsuario
	 * @return JSON usuario pesquisado
	 * @throws Exception
	 */
	@RequestMapping(value="buscarnome/{nomeUsuario}", method=RequestMethod.GET)
	public  @ResponseBody String buscarNome (@PathVariable("nomeUsuario") String nomeUsuario) throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = super.listaLikeExpression("nome", nomeUsuario);

		if (usuarios == null || usuarios.isEmpty() ) {
			return "{}";
		}
		
		return new Gson().toJson(usuarios);
	}


}
