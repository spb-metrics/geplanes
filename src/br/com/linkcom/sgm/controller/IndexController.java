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
package br.com.linkcom.sgm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import br.com.linkcom.neo.bean.annotation.Bean;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.DefaultAction;
import br.com.linkcom.neo.controller.MultiActionController;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.sgm.beans.ControleCadastro;
import br.com.linkcom.sgm.beans.Mensagem;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.DTO.EstatisticaIndexDTO;
import br.com.linkcom.sgm.service.ControleCadastroService;
import br.com.linkcom.sgm.service.IndicadorService;
import br.com.linkcom.sgm.service.MensagemService;
import br.com.linkcom.sgm.service.PlanoGestaoService;
import br.com.linkcom.sgm.service.UnidadeGerencialService;
import br.com.linkcom.sgm.service.UsuarioService;


@Bean
@Controller(
	path="/sgm/Index"
)
public class IndexController extends MultiActionController {
	private MensagemService mensagemService;
	private PlanoGestaoService planoGestaoService;
	private UnidadeGerencialService unidadeGerencialService;
	private IndicadorService indicadorService;
	private ControleCadastroService controleCadastroService;
	private UsuarioService usuarioService;
	
	public void setMensagemService(MensagemService mensagemService) {
		this.mensagemService = mensagemService;
	}
	public void setPlanoGestaoService(PlanoGestaoService planoGestaoService) {
		this.planoGestaoService = planoGestaoService;
	}
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) {
		this.unidadeGerencialService = unidadeGerencialService;
	}
	public void setIndicadorService(IndicadorService indicadorService) {
		this.indicadorService = indicadorService;
	}
	public void setControleCadastroService(ControleCadastroService controleCadastroService) {
		this.controleCadastroService = controleCadastroService;
	}
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@DefaultAction
	public ModelAndView exec(WebRequestContext request){
		
		List<Mensagem> mensagens = mensagemService.listaVisiveis();
		
		PlanoGestao planoGestao = planoGestaoService.obtemPlanoGestaoAtual();
		
		List<UnidadeGerencial> listaUnidadeGerencial = usuarioService.getUsuarioLogadoUGs(planoGestao);
		
		List<ControleCadastro> listaControleCadastro = controleCadastroService.geraListaControleCadastro(planoGestao, listaUnidadeGerencial);
		
		List<EstatisticaIndexDTO> estatisticas = new ArrayList<EstatisticaIndexDTO>();
		if (planoGestao != null) {
			montaTabela(planoGestao, estatisticas);
		}
		
		request.setAttribute("planoGestao", planoGestao);
		request.setAttribute("mensagens", mensagens);
		request.setAttribute("estatisticas", estatisticas);
		request.setAttribute("listaControleCadastro", listaControleCadastro);
		
		return new ModelAndView("index");
	}
	
	private void montaTabela(PlanoGestao pg, List<EstatisticaIndexDTO> estatisticas) {
		
		int ugs = unidadeGerencialService.contaUG(pg);
		int inds = indicadorService.contaIndicadores(pg);
		
		estatisticas.add( new EstatisticaIndexDTO("Total de U.G. (" + pg.getAnoExercicio() + ")",			String.valueOf(ugs) 	) );
		estatisticas.add( new EstatisticaIndexDTO("Total de Indicadores (" + pg.getAnoExercicio() + ")", 	String.valueOf(inds)	) );
		
	}
}
