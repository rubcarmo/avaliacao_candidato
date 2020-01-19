package datainfo.candidato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import datainfo.candidato.dao.DaoImplementacao;
import datainfo.candidato.dao.DaoInterface;
import datainfo.candidato.model.FuncaoUsuarioExterno;

@Controller
@RequestMapping(value = "/funcaos")
public class FuncaoController extends DaoImplementacao<FuncaoUsuarioExterno> implements
		DaoInterface<FuncaoUsuarioExterno> {

	public FuncaoController(Class<FuncaoUsuarioExterno> persistenceClass) {
		super(persistenceClass);
	}
	
	/**
	 * Faz o carregamento da lista de Funcões
	 * @return List<Funcaos> 
	 * @throws Exception
	 */
	@RequestMapping(value = "listar", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listar() throws Exception {
		return new Gson().toJson(super.lista());
	}

}
