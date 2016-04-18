/* 
		Copyright 2007,2008,2009,2010 da Linkcom Inform�tica Ltda
		
		Este arquivo � parte do programa GEPLANES.
 	   
 	    O GEPLANES � software livre; voc� pode redistribu�-lo e/ou 
		modific�-lo sob os termos da Licen�a P�blica Geral GNU, conforme
 	    publicada pela Free Software Foundation; tanto a vers�o 2 da 
		Licen�a como (a seu crit�rio) qualquer vers�o mais nova.
 	
 	    Este programa � distribu�do na expectativa de ser �til, mas SEM 
		QUALQUER GARANTIA; sem mesmo a garantia impl�cita de 
		COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM PARTICULAR. 
		Consulte a Licen�a P�blica Geral GNU para obter mais detalhes.
 	 
 	    Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU  	    
		junto com este programa; se n�o, escreva para a Free Software 
		Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
		02111-1307, USA.
		
*/
package br.com.linkcom.sgm.senha.process;

import java.util.ArrayList;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.web.servlet.ModelAndView;

import br.com.linkcom.neo.authorization.process.ProcessAuthorizationModule;
import br.com.linkcom.neo.bean.annotation.Bean;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.DefaultAction;
import br.com.linkcom.neo.controller.Input;
import br.com.linkcom.neo.controller.MessageType;
import br.com.linkcom.neo.controller.MultiActionController;
import br.com.linkcom.neo.core.standard.Neo;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.exception.GeplanesException;
import br.com.linkcom.sgm.filtro.AlterarSenhaFiltro;
import br.com.linkcom.sgm.service.UsuarioService;

@Bean
@Controller(path="/sgm/process/AlterarSenha", authorizationModule=ProcessAuthorizationModule.class)
public class AlterarSenhaProcess extends MultiActionController{
	UsuarioService usuarioService;
	
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@DefaultAction
	public ModelAndView filtrar(WebRequestContext request, AlterarSenhaFiltro filtro) {
		List<Usuario> listaUsuario = new ArrayList<Usuario>(); 
		if (usuarioService.isUsuarioLogadoAdmin()) {
			listaUsuario = usuarioService.findAllNaoBloqueados();
		}
		else { 
			listaUsuario.add((Usuario)Neo.getRequestContext().getUser());
		}
		request.setAttribute("lista", listaUsuario);
		return new ModelAndView("process/alterarsenha", "filtro", filtro);
	}
	
	@Input("filtrar")
	public ModelAndView salvar(WebRequestContext request, AlterarSenhaFiltro filtro) {
		Usuario usuario = filtro.getListaUsuarios();
		String usuarioSenha = filtro.getSenha();
		Usuario usuarioLogado = (Usuario) Neo.getRequestContext().getUser();
		usuarioLogado.setSenha(usuarioService.obtemSenha(usuarioLogado).getSenha());
		
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		// Verifica se a senha do usuario logado � valida 
		if (!encryptor.checkPassword(usuarioSenha, usuarioLogado.getSenha())) {
			throw new GeplanesException("Sua senha n�o confere. Favor, informe a senha correta.");
		}		
		
		String novaSenha = filtro.getNovaSenha();
		
		//Verifica se a nova senha tem pelo menos 4 posi��es 
		if ((filtro.getNovaSenha()).length() < 4) {
			throw new GeplanesException("A nova senha deve conter, no m�nimo, 4 caracteres.");
	    }
		
		//Verifica se a nova senha e a confirma��o da nova senha s�o iguais 
		if (!(filtro.getNovaSenha().equals(filtro.getRepetirNovaSenha()))) {
			throw new GeplanesException("A nova senha e sua confirma��o devem ser exatamente iguais.");
		}
		
		// Tudo OK para a atualiza��o da senha do usu�rio.
		usuario.setSenha(novaSenha);
		usuarioService.updatePassword(usuario, true);
		request.addMessage("Senha alterada com sucesso", MessageType.INFO);
		return filtrar(request,filtro);
	}
}
