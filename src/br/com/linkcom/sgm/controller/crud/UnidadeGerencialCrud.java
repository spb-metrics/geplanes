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

import java.util.ArrayList;
import java.util.List;

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
import br.com.linkcom.neo.util.Util;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.filtro.UnidadeGerencialFiltro;
import br.com.linkcom.sgm.service.PlanoGestaoService;
import br.com.linkcom.sgm.service.UnidadeGerencialService;
import br.com.linkcom.sgm.service.UsuarioService;
import br.com.linkcom.sgm.util.FiltroUtils;
import br.com.linkcom.sgm.util.Nomes;


@Controller(path="/sgm/crud/UnidadeGerencial", authorizationModule=CrudAuthorizationModule.class)
public class UnidadeGerencialCrud extends CrudController<UnidadeGerencialFiltro, UnidadeGerencial, UnidadeGerencial> {

	private UnidadeGerencialService unidadeGerencialService;
	private PlanoGestaoService planoGestaoService;
	private UsuarioService usuarioService;
		
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) {this.unidadeGerencialService = unidadeGerencialService;}
	public void setPlanoGestaoService(PlanoGestaoService planoGestaoService) { this.planoGestaoService = planoGestaoService;}
	public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService;}
	
	@Override
	protected void validateBean(UnidadeGerencial bean, BindException errors) {
		
		String beanNome = (Util.strings.tiraAcento(bean.getNome())).trim();
		//String beanSigla = (Util.strings.tiraAcento(bean.getSigla())).trim();
		
		List<UnidadeGerencial> listaUnidadeGerencial = unidadeGerencialService.findWithSiglaNomeByPlanoGestao(bean.getPlanoGestao());
		
		/*** verifica se a unidade gerencial j� est� cadastrada no banco para o ano de gest�o especificado ***/
		if(listaUnidadeGerencial != null) {
			
			for (UnidadeGerencial unidadeGerencial : listaUnidadeGerencial) {
				
				String unidadeGerencialNome  = (Util.strings.tiraAcento(unidadeGerencial.getNome())).trim();
				//String unidadeGerencialSigla = (Util.strings.tiraAcento(unidadeGerencial.getSigla())).trim();
			
				if(!unidadeGerencial.getId().equals(bean.getId()) &&  beanNome.compareToIgnoreCase(unidadeGerencialNome)==0){
					errors.reject("","J� existe uma unidade gerencial com este nome para o " + Nomes.plano_de_gestao + " escolhido.");
				}
			}
		}
		
		if (bean.getId() != null) {
			UnidadeGerencial subordinacao = bean.getSubordinacao();
			if (subordinacao != null && bean.getId().equals(subordinacao.getId())) {
				errors.reject("","N�o � poss�vel cadastrar uma unidade gerencial subordinada a ela mesma");
			}
			if (subordinacao != null) {
				if (unidadeGerencialService.isSubordinado(bean, subordinacao, bean.getPlanoGestao())) {
					errors.reject("","A subordina��o escolhida j� � subordinada a essa unidade gerencial.");
				}
			}
		}
		
		super.validateBean(bean, errors);
	}

	@Override
	protected void salvar(WebRequestContext request, UnidadeGerencial bean) throws Exception {
		
		try {
			UnidadeGerencial unidadeGerencialPai = unidadeGerencialService.getPai(bean);
			if (unidadeGerencialPai != null) {
				bean.setNivelNum(unidadeGerencialPai.getNivelNum() + 1);
			}
			
			unidadeGerencialService.saveOrUpdate(bean);
			
			// Atualiza os n�veis das unidades gerenciais envolvidas.
			List<UnidadeGerencial> listaUnidadeGerencial = new ArrayList<UnidadeGerencial>();
			listaUnidadeGerencial = unidadeGerencialService.getListaDescendencia(bean, listaUnidadeGerencial);
			unidadeGerencialService.atualizaUnidadeGerencialNivelNum(listaUnidadeGerencial);
			
			request.addMessage("Unidade gerencial salva com sucesso.", MessageType.INFO);
		} 
		catch (Exception e) {
			request.addError(e.getMessage());
		}
	}
	
	@Override
	protected ListagemResult<UnidadeGerencial> getLista(WebRequestContext request, UnidadeGerencialFiltro filtro) {
		ListagemResult<UnidadeGerencial> result = super.getLista(request, filtro);
		List<UnidadeGerencial> listaUnidadeGerencial = result.list();
		
		Boolean hasAuthMapaNegocioReport = Neo.getApplicationContext().getAuthorizationManager().isAuthorized("/sgm/report/MapaNegocio", "generate", Neo.getUser());
		
		Boolean isUsuarioAdmin = usuarioService.isUsuarioLogadoAdmin();
		
		if (listaUnidadeGerencial != null) {
			for (UnidadeGerencial unidadeGerencial : listaUnidadeGerencial) {
				//Verifica se a ug pode ser editada
				if (isUsuarioAdmin) {
					unidadeGerencial.setPodeEditar(true);
				}
				else {
					unidadeGerencial.setPodeEditar(usuarioService.isUsuarioLogadoParticipanteUG(unidadeGerencial));
				}
				
				//Define se o bot�o de impress�o do mapa do neg�cio ser� exibido
				unidadeGerencial.setPodeImprimirMapaNegocio(hasAuthMapaNegocioReport);
			}
		}		
		return result;
	}
	
	@Override
	public ModelAndView doListagem(WebRequestContext request, UnidadeGerencialFiltro filtro) throws CrudException {
		/*** Seta valores default para o filtro ***/		
		FiltroUtils.preencheFiltroPlanoGestaoUnidadeUsuario(filtro);		

		return super.doListagem(request, filtro);
	}
	
	@Override
	protected void entrada(WebRequestContext request, UnidadeGerencial form) throws Exception {
		
		/*** Caso o plano de gest�o esteja nulo, seta o atual ***/
		if (form.getPlanoGestao() == null) {
			form.setPlanoGestao(planoGestaoService.obtemPlanoGestaoAtual());
		}
		
		request.setAttribute("listaNaoAdministradores", usuarioService.findNaoAdministradores());

		super.entrada(request, form);
	}
}
