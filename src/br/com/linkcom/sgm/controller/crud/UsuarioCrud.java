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
package br.com.linkcom.sgm.controller.crud;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import br.com.linkcom.neo.authorization.crud.CrudAuthorizationModule;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.MessageType;
import br.com.linkcom.neo.controller.crud.CrudController;
import br.com.linkcom.neo.controller.crud.CrudException;
import br.com.linkcom.neo.core.standard.Neo;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.neo.persistence.ListagemResult;
import br.com.linkcom.neo.util.CollectionsUtil;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.filtro.UsuarioFiltro;
import br.com.linkcom.sgm.service.PlanoGestaoService;
import br.com.linkcom.sgm.service.UnidadeGerencialService;
import br.com.linkcom.sgm.service.UsuarioPapelService;
import br.com.linkcom.sgm.service.UsuarioService;
import br.com.linkcom.sgm.util.FiltroUtils;



@Controller(path={"/sgm/crud/Usuario","/util/crud/Usuario"}, authorizationModule=CrudAuthorizationModule.class)
public class UsuarioCrud extends CrudController<UsuarioFiltro, Usuario, Usuario> {
	
	private UsuarioService usuarioService;
	private UsuarioPapelService usuarioPapelService;
	private UnidadeGerencialService unidadeGerencialService;
	private PlanoGestaoService planoGestaoService;
 	
	public void setUsuarioPapelService(UsuarioPapelService usuarioPapelService) {this.usuarioPapelService = usuarioPapelService;}
	public void setUsuarioService(UsuarioService usuarioService) {this.usuarioService = usuarioService;}
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) { this.unidadeGerencialService = unidadeGerencialService;}
	public void setPlanoGestaoService(PlanoGestaoService planoGestaoService) { this.planoGestaoService = planoGestaoService;}
	
	@Override
	protected void entrada(WebRequestContext request, Usuario form) throws Exception {
		if (form.getId()==null)
			request.setAttribute("novo", true);
		//apenas se o usuario digitar que ser� verificado novamente
		form.setSenha(null);
		form.setVerificaSenha(null);
		
		//popular os perfis do usuario
		if(form.getId() != null){
			form.setPapeis(usuarioPapelService.getPapeis(form));
		}
	}
	
	@Override
	public ModelAndView doListagem(WebRequestContext request, UsuarioFiltro filtro) throws CrudException {
		/*** Seta valores default para o filtro ***/		
		FiltroUtils.preencheFiltroPlanoGestaoUnidadeUsuario(filtro);
		
		return super.doListagem(request, filtro);
	}
	
	@Override
	protected ListagemResult<Usuario> getLista(WebRequestContext request, UsuarioFiltro filtro) {		
		ListagemResult<Usuario> result = super.getLista(request, filtro);
		List<Usuario> listaUsuario = result.list();
		
		if (request.getRequestQuery().contains("util")) {
			StringBuilder ids = new StringBuilder();
			for (Usuario usuario : listaUsuario) {
				ids.append("'"+ usuario.getNome() + "|" + usuario.getEmail() +"',");
			}
			if(ids.length() > 0){
				ids.delete(ids.length() - 1, ids.length());
			}
			request.setAttribute("idsFormatados", ids.toString());
		}
		
		PlanoGestao planoGestaoAtual = planoGestaoService.obtemPlanoGestaoAtual();
		
		for (Usuario usuario : listaUsuario) {
			List<UnidadeGerencial> lista = unidadeGerencialService.loadByUsuarioPlanoGestao(usuario, planoGestaoAtual);
			if(lista != null && lista.size() > 0){
				usuario.setUgsAtuais(CollectionsUtil.listAndConcatenate(lista, "sigla", ", "));
			}
		}
		return result;
	}
	
	@Override
	protected void validateBean(Usuario bean, BindException errors) {
		
		if(bean.getId() == null){
			//esta valida��o n�o � feita no bean pq a regra de negocio permite na altera��o
			// do usuario n�o alterar a senha
			if(bean.getSenha() == null || bean.getSenha().equals("")){
				errors.reject("� necess�rio informar a senha.","� necess�rio informar a senha.");
			}
		}
		
		if(bean.getId() == null){
			if(!bean.getSenha().equals("")){
				if(bean.getVerificaSenha() == null || bean.getVerificaSenha().equals("")){
					errors.reject("� necess�rio a confirma��o da senha.","� necess�rio a confirma��o da senha.");
				}
				if(bean.getVerificaSenha() != null || !bean.getVerificaSenha().equals("")){
					if(!bean.getSenha().equals(bean.getVerificaSenha())){
						errors.reject("As senhas digitadas n�o conferem.","As senhas digitadas n�o conferem.");
					}else{
						if (bean.getId()==null) {						
							//criptogafrar a senha
							bean.setSenha(DigestUtils.md5Hex(bean.getSenha()));
						}
					}
				}
			}
		}
		//verificar se j� existe o login informado
		if(!bean.getLogin().equals("") && bean.getId() == null){
			Boolean existe = usuarioService.verificaExisteLogin(bean.getLogin());
			if(existe){
				errors.reject("Este login j� existe. Favor informar outro.","Este login j� existe. Favor informar outro.");
			}
		}
		//se o usuario alterou o login verificar se este j� existe
		if(!bean.getLogin().equals("") && bean.getId() != null){
			Usuario usuario = usuarioService.obtemLogin(bean);
			if(!usuario.getLogin().equals("") && !bean.getLogin().equals(usuario.getLogin())){
				Boolean existe = usuarioService.verificaExisteLogin(bean.getLogin());
				if(existe){
					errors.reject("Este login j� existe. Favor informar outro.","Este login j� existe. Favor informar outro.");
				}
			}
		}
		
		if(bean.getPapeis() == null ||bean.getPapeis().size() == 0 ){
			errors.reject("� necess�rio pelo menos um perfil.","� necess�rio pelo menos um perfil.");
		}
		
		if(bean.getFoto() != null && bean.getFoto().getSize() > 0) {
			if(bean.getFoto().getContent().length > 500000){
				errors.reject("O tamanho m�ximo permitido para o arquivo � 500kb.","O tamanho m�ximo permitido para o arquivo � 500kb.");
			}
			//400x500
			if (!"image/jpeg".equals(bean.getFoto().getContenttype()) && !"image/jpg".equals(bean.getFoto().getContenttype())) {
				errors.reject("O formato da imagem � inv�lido. � permitido apenas jpeg.","O formato da imagem � inv�lido. � permitido apenas jpeg.");
			}
			
			try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(bean.getFoto().getContent()));
				if (image != null && image.getWidth() > 400) {
					errors.reject("O tamanho m�ximo permitido para o arquivo � de 400px de largura","O tamanho m�ximo permitido para o arquivo � de 400px de largura");
				}
				if (image != null && image.getHeight() > 500) {
					errors.reject("A tamanho m�ximo permitido para o arquivo � de 500px de altura","O tamanho m�ximo permitido para o arquivo � de 500px de altura");
				}
			} catch (IOException e) {
				errors.reject("N�o foi poss�vel carregar a imagem","N�o foi poss�vel carregar a imagem");
			}
		}
		super.validateBean(bean, errors);
		
	}
	
	@Override
	protected void excluir(WebRequestContext request, Usuario bean) throws Exception {
		// Verifica se quem foi exclu�do � quem esta logado, se for invalida a sess�o
		Usuario usuario = (Usuario) Neo.getUser();
		boolean invalidar = bean.getId().equals(usuario.getId()) ? true : false;
		
		super.excluir(request, bean);
		
		if (invalidar) {
			request.getSession().invalidate();
		}
	}
	
	@Override
	protected void salvar(WebRequestContext request, Usuario bean) throws Exception {
		super.salvar(request, bean);
		request.addMessage("Usu�rio salvo com sucesso", MessageType.INFO);
	}
}
