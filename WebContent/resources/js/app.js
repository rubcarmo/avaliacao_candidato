var permissao = false;
// configuracao do modulo
var app = angular.module('candidato', [ 'ngRoute', 'ngResource', 'ngAnimate' ]);

// configurando rotas
app.config(function($routeProvider) {

			$routeProvider.when("/usuariolist", {
				controller : "usuarioController",
				templateUrl : "usuario/list.html"
			})// listar
			
			.when("/usuarioedit/:id", {
				controller : "usuarioController",
				templateUrl : "usuario/cadastro.html"
			})// editar
			
			.when("/usuario/cadastro", {
				controller : "usuarioController",
				templateUrl : "usuario/cadastro.html"
			})// novo
			
});




// configura��es do controller de usuarios
app.controller('usuarioController', function($scope, $http, $location, $routeParams) {
	
	if ($routeParams.id != null && $routeParams.id != undefined
			&& $routeParams.id != ''){// se estiver editando o usuario
		// entra pra editar
		$http.get("usuario/buscarusuario/" + $routeParams.id).success(function(response) {
			$scope.usuario = response;
			
			//------------------ carrega Funcaos e Perfis do usuario em edicao
			setTimeout(function () {
								
				$("#selectFuncaos").prop('selectedIndex', (new Number($scope.usuario.funcaos.id) + 1));

				$("#selectPerfils").prop('selectedIndex', (new Number($scope.usuario.perfils.id) + 1));
											
			}, 1000);
			//----------------------
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
		
	}else { // novo usuario
		$scope.usuario = {};
	}

	$scope.editarUsuario = function(id) {
		$location.path('usuarioedit/' + id);
		document.getElementById('edicao').value = true;
	};
	
	
	// Responsavel por salvar o usuario ou editar os dados
	$scope.salvarUsuario = function() {

		var retorno = $scope.validaCPF($scope.usuario.cpf);

		if (retorno) {

				$http.post("usuario/salvar", $scope.usuario).success(function(response) {

					$scope.usuario = {};

					if(document.getElementById('edicao').value == true) {
						msg = 'Alteração efetuada com sucesso!';
					} else {
						msg = 'Cadastro efetuado com sucesso';
					}

					sucesso(msg);
				}).error(function(response) {
					erro("Error: " + response);
				});
		}		
      };
          
          
	// listar todos os usuarios
	$scope.listarUsuarios = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("usuario/listar/" + numeroPagina).success(function(response) {
			
			if (response == null || response == '') {
				$scope.ocultarNavegacao = true;
			}else {
				$scope.ocultarNavegacao = false;
			}
			
			$scope.data = response;
			
			//---------Inicio total p�gina----------
				$http.get("usuario/totalPagina").success(function(response) {
					$scope.totalPagina = response;
				}).error(function(response) {
					erro("Error: " + response);
				});
			//---------Fim total p�gina----------
			
		}).error(function(response) {
			erro("Error: " + response);
		});
		
	};
	
	$scope.proximo = function () {
		if (new Number($scope.numeroPagina) < new Number($scope.totalPagina)) {
		 $scope.listarUsuarios(new Number($scope.numeroPagina + 1));
		} 
	};
	
	$scope.anterior = function () {
		if (new Number($scope.numeroPagina) > 1) {
		  $scope.listarUsuarios(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remover usuario passado como parametro
	$scope.removerUsuario = function(codUsuario,nomeUsuario) {

		var resposta = window.confirm('Deseja realmente excluir o usuario: ' + nomeUsuario + ' ?');

		if (resposta) {
			$http.delete("usuario/deletar/" + codUsuario).success(function(response) {
				$scope.listarUsuarios($scope.numeroPagina);
				sucesso("Exclusão efetuada com sucesso."); 
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			}); 
		} else {
			return;
		}
	};
	
	
	// chavear usuario passado como parametro
	$scope.chavearUsuario = function(codUsuario,statusUsuario,usuarioNome) {

		var operacao;
		var operacao1;
		if(statusUsuario) {
			operacao = "habilitado";
			operacao1 = "habilitar";
		} else {
			operacao = "Desabilitado";
			operacao1 = "desabilitar";
		}
		
		var params = codUsuario + '|' + statusUsuario.toString();

		var resposta = window.confirm('Confirma ' + operacao1 + ' o usuario: ' + usuarioNome + ' ? ');


		if (resposta) 	{

			$http.post("usuario/chavear/" + params).success(function(response) {
				sucesso("Usuario " + operacao + " com sucesso!"); 
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			});
			
		} else {
			return;
		} 
		
	};	

	$scope.validaCPF = function(strCPF) {

		strCPF = strCPF.replace(".", "").replace(".", "").replace("-", "");

		if (strCPF.length != 11 ||
            strCPF == "00000000000" ||
            strCPF == "11111111111" ||
            strCPF == "22222222222" ||
            strCPF == "33333333333" ||
            strCPF == "44444444444" ||
            strCPF == "55555555555" ||
            strCPF == "66666666666" ||
            strCPF == "77777777777" ||
            strCPF == "88888888888" ||
            strCPF == "99999999999") {
			  alert(' Operação não realizada. CPF digitado é inválido !');
			  return false;
			}
            
        add = 0;

        for (i = 0; i < 9; i++) 
                add += parseInt(strCPF.charAt(i)) * (10 - i);
        rev = 11 - (add % 11);
        if (rev == 10 || rev == 11)
            rev = 0;
        if (rev != parseInt(strCPF.charAt(9))) {
			alert(' Operação não realizada. CPF digitado é inválido !');
			 return false;
		}
            
        add = 0;
                for (i = 0; i < 10; i++)
                add += parseInt(strCPF.charAt(i)) * (11 - i);
        rev = 11 - (add % 11);
        if (rev == 10 || rev == 11)
            rev = 0;
        if (rev != parseInt(strCPF.charAt(10))) {
			alert(' Operação não realizada. CPF digitado é inválido !');
			 return false;
		}

        // CPF Valido    
		return true;
			
	}

	
	// carrega as funcoes ao iniciar a tela de cadastro 
	$scope.carregarFuncaos = function() {
		$scope.dataFuncaos = [{}];
		$http.get("funcaos/listar").success(function(response) {
			$scope.dataFuncaos = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};

	// carrega os perfis ao iniciar a tela de cadastro 
	$scope.carregarPerfils = function() {
		$scope.dataPerfils = [{}];
		$http.get("perfils/listar").success(function(response) {
			$scope.dataPerfils = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};


	
});



// mostra msg de sucesso
function sucesso(msg) {
    	$.notify({
        	message: msg

        },{
            type: 'success',
            timer: 1000
        });
}

// mostra msg de erro
function erro(msg) {
	$.notify({
    	message: msg

    },{
        type: 'danger',
        timer: 1000
    });
}

// identificar navegador
function identific_nav(){
    var nav = navigator.userAgent.toLowerCase();
    if(nav.indexOf("msie") != -1){
       return browser = "msie";
    }else if(nav.indexOf("opera") != -1){
    	return browser = "opera";
    }else if(nav.indexOf("mozilla") != -1){
        if(nav.indexOf("firefox") != -1){
        	return  browser = "firefox";
        }else if(nav.indexOf("firefox") != -1){
        	return browser = "mozilla";
        }else if(nav.indexOf("chrome") != -1){
        	return browser = "chrome";
        }
    }else{
    	alert("Navegador desconhecido!");
    }
}

