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

import java.sql.Date;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import br.com.linkcom.neo.authorization.crud.CrudAuthorizationModule;
import br.com.linkcom.neo.controller.Command;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.MessageType;
import br.com.linkcom.neo.controller.crud.CrudController;
import br.com.linkcom.neo.controller.crud.CrudException;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.sgm.beans.Ocorrencia;
import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.filtro.OcorrenciaFiltro;
import br.com.linkcom.sgm.service.AnomaliaService;
import br.com.linkcom.sgm.service.OcorrenciaService;
import br.com.linkcom.sgm.service.UnidadeGerencialService;
import br.com.linkcom.sgm.service.UsuarioService;
import br.com.linkcom.sgm.util.FiltroUtils;



@Controller(path="/sgm/crud/Ocorrencia", authorizationModule=CrudAuthorizationModule.class)
public class OcorrenciaCrud extends CrudController<OcorrenciaFiltro, Ocorrencia, Ocorrencia> {
	
	private OcorrenciaService ocorrenciaService;
	private UnidadeGerencialService unidadeGerencialService;
	private AnomaliaService anomaliaService;
	private UsuarioService usuarioService;	
	
	public void setAnomaliaService(AnomaliaService anomaliaService) {this.anomaliaService = anomaliaService;}
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) {this.unidadeGerencialService = unidadeGerencialService;}
	public void setOcorrenciaService(OcorrenciaService medidaService) {this.ocorrenciaService = medidaService;}
	public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService;}
	
	@Override
	public ModelAndView doListagem(WebRequestContext request, OcorrenciaFiltro filtro) throws CrudException {
		
		FiltroUtils.preencheFiltroPlanoGestaoUnidadeUsuario(filtro);
		
		return super.doListagem(request, filtro);
	}
	
	@Override
	public ModelAndView doExcluir(WebRequestContext request, Ocorrencia form) throws CrudException {
		//Verifica se o Usu�rio Logado tem permiss�o de Exclus�o
		if  (ocorrenciaService.podeAlterar(form))
			return super.doExcluir(request, form);
		else
			throw new CrudException("excluir",new Exception("Voc� n�o tem permiss�o para excluir esse registro!"));
		
	}
	
	@Override
	protected void entrada(WebRequestContext request, Ocorrencia form) throws Exception {
		/*** Seta valores default para o filtro ***/		
		if (!"true".equals(request.getParameter("reload"))) {
			FiltroUtils.preencheFiltroPlanoGestaoUnidadeUsuario(form);
		}
		
		//Se for uma nova ocorr�ncia ser� dada a op��o de gerar uma anomalia.
		if(form.getId() == null){
			request.setAttribute("opcaoCriarAnomalia", true);

			//seta a data e o usuario que esta logado
			form.setDataLancamento(new Date(System.currentTimeMillis()));
			form.setRelator((Usuario)request.getUser());			
		}
		//Sen�o ser� apenas editada.
		else {
			request.setAttribute("opcaoCriarAnomalia", false);
			if(!anomaliaService.fazParteAnomalia(form)){
				request.setAttribute("opcaoCriarAnomalia", true);
			}
			
			if (!usuarioService.isUsuarioLogadoAdmin()) {
				request.setAttribute("editarSomenteSituacao", Boolean.TRUE);
			}			
		}
		
	}
	
	@Override
	protected Ocorrencia carregar(WebRequestContext request, Ocorrencia bean) throws Exception {
		bean = super.carregar(request, bean);
		bean.setPodeEditar(ocorrenciaService.podeAlterar(bean));
		bean.setPlanoGestao((unidadeGerencialService.load(bean.getUnidadeGerencial()).getPlanoGestao()));
		return bean;
	}
	
	@Override
	protected void validateBean(Ocorrencia bean, BindException errors) {
		
		/*** Verifica se o usu�rio tem permiss�o para cadastrar ocorr�ncia para a UG selecionada, ou seja, se � administrador ou vinculado � mesma ***/
		if(!usuarioService.isUsuarioLogadoAdmin() && !usuarioService.isUsuarioLogadoParticipanteUG(bean.getUnidadeGerencial())) {
			errors.reject("", "Voc� n�o tem permiss�o para cadastrar ocorr�ncias nessa Unidade Gerencial.");
		}
		
		super.validateBean(bean, errors);
	}	
	
	@Override
	protected void salvar(WebRequestContext request, Ocorrencia bean) throws Exception {
		
		if (bean.getNumero() == null) {
			bean.setNumero(ocorrenciaService.getProximoSequencial(bean.getUnidadeGerencial()));
		}		
		
		super.salvar(request, bean);
		request.addMessage("Ocorr�ncia salva com sucesso", MessageType.INFO);
	}
	
	@Command(session=true)
	public ModelAndView diarioBordo(WebRequestContext request, OcorrenciaFiltro filtro){
		return new ModelAndView("report/DiarioBordo", "filtro", filtro);
	}
	
	public ModelAndView salvarAndEncaminhar(WebRequestContext request, Ocorrencia ocorrencia)throws Exception {
		
		/*** Verifica se o usu�rio tem permiss�o para cadastrar ocorr�ncia para a UG selecionada, ou seja, se � administrador ou vinculado � mesma ***/
		if(!usuarioService.isUsuarioLogadoAdmin() && !usuarioService.isUsuarioLogadoParticipanteUG(ocorrencia.getUnidadeGerencial())) {
			request.addError("Voc� n�o tem permiss�o para cadastrar ocorr�ncias nessa Unidade Gerencial.");
			return doEntrada(request, ocorrencia);
		}	
		else {
			
			if (ocorrencia.getNumero() == null) {
				ocorrencia.setNumero(ocorrenciaService.getProximoSequencial(ocorrencia.getUnidadeGerencial()));
			}		
			ocorrenciaService.saveOrUpdate(ocorrencia);
	
			return new ModelAndView("redirect:/sgm/crud/Anomalia?ACAO=criar&ocorrencia.id="+ocorrencia.getId());
		}
	}
	
	public ModelAndView abrirEscolha(WebRequestContext request){
		return new ModelAndView("/crud/popup/escolhaAnomalia");
	}
}
