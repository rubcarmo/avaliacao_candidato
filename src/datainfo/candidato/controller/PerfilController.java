package datainfo.candidato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import datainfo.candidato.dao.DaoImplementacao;
import datainfo.candidato.dao.DaoInterface;
import datainfo.candidato.model.Perfil;

@Controller
@RequestMapping(value = "/perfils")
public class PerfilController extends DaoImplementacao<Perfil> implements
		DaoInterface<Perfil> {

	public PerfilController(Class<Perfil> persistenceClass) {
		super(persistenceClass);
	}
	
	/**
	 * Faz o carregamento da lista de Perfis
	 * @return List<Perfis> 
	 * @throws Exception
	 */
	@RequestMapping(value = "listar", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listar() throws Exception {
		return new Gson().toJson(super.lista());
	}

}
