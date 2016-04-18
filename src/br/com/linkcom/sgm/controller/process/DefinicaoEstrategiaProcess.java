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
package br.com.linkcom.sgm.controller.process;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import br.com.linkcom.neo.authorization.process.ProcessAuthorizationModule;
import br.com.linkcom.neo.bean.annotation.Bean;
import br.com.linkcom.neo.controller.Command;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.DefaultAction;
import br.com.linkcom.neo.controller.Input;
import br.com.linkcom.neo.controller.MessageType;
import br.com.linkcom.neo.controller.MultiActionController;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.sgm.beans.Indicador;
import br.com.linkcom.sgm.beans.Iniciativa;
import br.com.linkcom.sgm.beans.MapaEstrategico;
import br.com.linkcom.sgm.beans.MatrizFCS;
import br.com.linkcom.sgm.beans.PerspectivaMapaEstrategico;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.controller.filtro.DefinicaoEstrategiaFiltro;
import br.com.linkcom.sgm.exception.GeplanesException;
import br.com.linkcom.sgm.service.IndicadorService;
import br.com.linkcom.sgm.service.IniciativaService;
import br.com.linkcom.sgm.service.MapaEstrategicoService;
import br.com.linkcom.sgm.service.MapaNegocioService;
import br.com.linkcom.sgm.service.MatrizFCSService;
import br.com.linkcom.sgm.service.PerspectivaMapaEstrategicoService;
import br.com.linkcom.sgm.service.PlanoGestaoService;
import br.com.linkcom.sgm.service.UnidadeGerencialService;
import br.com.linkcom.sgm.service.UsuarioService;
import br.com.linkcom.sgm.util.FiltroUtils;


@Bean
@Controller(path="/sgm/process/DefinicaoEstrategia", authorizationModule=ProcessAuthorizationModule.class)
public class DefinicaoEstrategiaProcess extends MultiActionController {
	
	private UnidadeGerencialService unidadeGerencialService;
	private PlanoGestaoService planoGestaoService;
	private UsuarioService usuarioService;
	private MapaEstrategicoService mapaEstrategicoService;
	private MapaNegocioService mapaNegocioService;
	private PerspectivaMapaEstrategicoService perspectivaMapaEstrategicoService;
	private MatrizFCSService matrizFCSService;
	private IndicadorService indicadorService;
	private IniciativaService iniciativaService;	
	
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	public void setPlanoGestaoService(PlanoGestaoService planoGestaoService) {
		this.planoGestaoService = planoGestaoService;
	}
	
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) {
		this.unidadeGerencialService = unidadeGerencialService;
	}
	
	public void setMapaEstrategicoService(MapaEstrategicoService mapaEstrategicoService) {
		this.mapaEstrategicoService = mapaEstrategicoService;
	}
	
	public void setMapaNegocioService(MapaNegocioService mapaNegocioService) {
		this.mapaNegocioService = mapaNegocioService;
	}
	
	public void setPerspectivaMapaEstrategicoService(PerspectivaMapaEstrategicoService perspectivaMapaEstrategicoService) {
		this.perspectivaMapaEstrategicoService = perspectivaMapaEstrategicoService;
	}
	
	public void setMatrizFCSService(MatrizFCSService matrizFCSService) {
		this.matrizFCSService = matrizFCSService;
	}
	
	public void setIndicadorService(IndicadorService indicadorService) {
		this.indicadorService = indicadorService;
	}
	
	public void setIniciativaService(IniciativaService iniciativaService) {
		this.iniciativaService = iniciativaService;
	}	
	
	@DefaultAction	
    public ModelAndView entrada(WebRequestContext request, DefinicaoEstrategiaFiltro filtro) {
		
		if (!"true".equals(request.getParameter("reload"))) {
			FiltroUtils.preencheFiltroPlanoGestaoUnidadeUsuario(filtro);		
		}
		
		if (filtro.getPlanoGestao() != null && filtro.getUnidadeGerencial() != null) {
			
			UnidadeGerencial unidadeGerencial = unidadeGerencialService.loadWithMapaEstrategico(filtro.getUnidadeGerencial());
			
			MapaEstrategico mapaEstrategico;
			if (unidadeGerencial.getMapaEstrategico() != null && unidadeGerencial.getMapaEstrategico().getId() != null) {
				mapaEstrategico = unidadeGerencial.getMapaEstrategico();
				
				//Lista de todas as perspectivas de um determinado Mapa Estrat�gico.
				List<PerspectivaMapaEstrategico> listaPerspectivaMapaEstrategico = perspectivaMapaEstrategicoService.findByMapaEstrategico(unidadeGerencial.getMapaEstrategico());
				mapaEstrategico.setListaPerspectivaMapaEstrategico(listaPerspectivaMapaEstrategico);
			}
			else {
				// Caso ainda n�o exista o mapa estrat�gico cadastrado para a UG,
				// verifica se j� foi cadastrada a matriz de iniciativas x fcs para a UG.
				// Caso afirmativo, o cadastro foi feito baseado no mapa estrat�gico de uma UG superior.
				// Nesse caso, deve-se impedir o cadastro do mapa estrat�gico para a UG em quest�o.
				List<MatrizFCS> listaMatrizFCS = matrizFCSService.findByUnidadeGerencialObjetivoEstrategico(unidadeGerencial, null);
				if (listaMatrizFCS != null && !listaMatrizFCS.isEmpty()) {
					request.setAttribute("SEMPERMISSAO", true);
					request.addMessage("N�o � permitido o cadastro do mapa estrat�gico para essa unidade gerencial, pois a matriz de iniciativas x fcs j� foi criada para os objetivos definidos no mapa estrat�gico de uma unidade gerencial superior.", MessageType.ERROR);
					return new ModelAndView("process/definicaoEstrategia", "filtro", filtro);
				}
				
				// Caso ainda n�o exista o mapa estrat�gico cadastrado para a UG,
				// verifica se j� foi cadastrado algum indicador para a UG.
				// Caso afirmativo, o cadastro foi feito baseado no mapa estrat�gico de uma UG superior.
				// Nesse caso, deve-se impedir o cadastro do mapa estrat�gico para a UG em quest�o.
				List<Indicador> listaIndicador = indicadorService.findByUnidadeGerencialObjetivoEstrategico(unidadeGerencial, null, true, false, false, false);
				if (listaIndicador != null && !listaIndicador.isEmpty()) {
					request.setAttribute("SEMPERMISSAO", true);
					request.addMessage("N�o � permitido o cadastro do mapa estrat�gico para essa unidade gerencial, pois j� existe(m) indicador(es) cadastrado(s) para os objetivos definidos no mapa estrat�gico de uma unidade gerencial superior.", MessageType.ERROR);
					return new ModelAndView("process/definicaoEstrategia", "filtro", filtro);
				}
				
				// Caso ainda n�o exista o mapa estrat�gico cadastrado para a UG,
				// verifica se j� foi cadastrado alguma iniciativa para a UG.
				// Caso afirmativo, o cadastro foi feito baseado no mapa estrat�gico de uma UG superior.
				// Nesse caso, deve-se impedir o cadastro do mapa estrat�gico para a UG em quest�o.
				List<Iniciativa> listaIniciativa = iniciativaService.findByUnidadeGerencial(unidadeGerencial);
				if (listaIniciativa != null && !listaIniciativa.isEmpty()) {
					request.setAttribute("SEMPERMISSAO", true);
					request.addMessage("N�o � permitido o cadastro do mapa estrat�gico para essa unidade gerencial, pois j� existe(m) iniciativa(s) cadastrada(s) para os objetivos definidos no mapa estrat�gico de uma unidade gerencial superior.", MessageType.ERROR);
					return new ModelAndView("process/definicaoEstrategia", "filtro", filtro);
				}
				
				mapaEstrategico = new MapaEstrategico();
			}
			
			// Busca o campo miss�o vindo do cadastro do mapa do neg�cio.
			mapaEstrategico.setMissao(mapaNegocioService.loadMissaoByUnidadeGerencial(unidadeGerencial));
			
			unidadeGerencial.setMapaEstrategico(mapaEstrategico);
			filtro.setUnidadeGerencial(unidadeGerencial);
			
			boolean podeCadastrarMapa = unidadeGerencial.getPermitirMapaEstrategico() != null ? unidadeGerencial.getPermitirMapaEstrategico() : false;
			boolean dtLimCrMapEstExpirada = !(planoGestaoService.dataLimiteCriacaoMapaEstrategicoNaoExpirada(unidadeGerencial.getPlanoGestao()));
			boolean usuarioLogadoParticipanteUG = usuarioService.isUsuarioLogadoParticipanteUG(unidadeGerencial);
			boolean usuarioLogadoParticipanteUGAncestral = usuarioService.isUsuarioLogadoParticipanteUGAncestral(unidadeGerencial);
			boolean usuarioLogadoIsAdmin = usuarioService.isUsuarioLogadoAdmin();
			
			if (podeCadastrarMapa) {
				if (usuarioLogadoParticipanteUG || usuarioLogadoParticipanteUGAncestral || usuarioLogadoIsAdmin) {
					if (dtLimCrMapEstExpirada) {
						if (usuarioLogadoIsAdmin) {
							request.addMessage("A data limite para cria��o do mapa estrat�gico est� ultrapassada.", MessageType.WARN);
						}
						else {
							request.addMessage("A data limite para cria��o do mapa estrat�gico est� ultrapassada.", MessageType.ERROR);
							request.setAttribute("HIDEBOTAOSALVAR", Boolean.TRUE);							
						}
					}
					else {
						request.setAttribute("SEMPERMISSAO", false);
					}
				}
				else {
					request.setAttribute("SEMPERMISSAO", true);
					request.addMessage("Voc� n�o tem permiss�o para acessar os dados dessa unidade gerencial.", MessageType.ERROR);
				}
				
			} 
			else {
				request.setAttribute("SEMPERMISSAO", true);
				request.addMessage("N�o � permitido o cadastro do mapa estrat�gico para essa unidade gerencial.", MessageType.ERROR);
			}
		}
				
		return new ModelAndView("process/definicaoEstrategia", "filtro", filtro);
    }
	
	@Command(validate=true)
	@Input("error")
	public ModelAndView salvar(WebRequestContext request, DefinicaoEstrategiaFiltro filtro) {
		if (filtro.getUnidadeGerencial() != null) {
			try {
				UnidadeGerencial unidadeGerencial = filtro.getUnidadeGerencial();
				mapaEstrategicoService.salvaDefinicaoObjetivoEstrategico(unidadeGerencial.getMapaEstrategico(), unidadeGerencial, true);
				request.addMessage("Registro salvo com sucesso.");
			}
			catch (GeplanesException e) {
				request.addError(e.getMessage());
			}
		}
		return continueOnAction("entrada", new DefinicaoEstrategiaFiltro());
	}
	
	@Command(validate=true)
	@Input("error")
	public ModelAndView excluir(WebRequestContext request, DefinicaoEstrategiaFiltro filtro) throws Exception {
		MapaEstrategico mapaEstrategico = filtro.getUnidadeGerencial().getMapaEstrategico();
		
		if (mapaEstrategico != null) {
			try {
				mapaEstrategicoService.excluiMapaEstrategico(mapaEstrategico);
				request.addMessage("Mapa estrat�gico exclu�do com sucesso!");
			}
			catch (GeplanesException e) {
				request.addError(e.getMessage());
			}
		}

		return continueOnAction("entrada", new DefinicaoEstrategiaFiltro());
	}	
	
	@Override
	protected void validate(Object obj, BindException errors, String acao) {
		DefinicaoEstrategiaFiltro filtro = (DefinicaoEstrategiaFiltro) obj;
		UnidadeGerencial unidadeGerencial = filtro.getUnidadeGerencial();
		
		boolean cadastroCompleto = false;
		// Verifica se foi inserido pelo menos um objetivo estrat�gico.
		if (unidadeGerencial.getMapaEstrategico() != null && unidadeGerencial.getMapaEstrategico().getListaPerspectivaMapaEstrategico() != null && !unidadeGerencial.getMapaEstrategico().getListaPerspectivaMapaEstrategico().isEmpty()) {
			for (PerspectivaMapaEstrategico perspectivaMapaEstrategico : unidadeGerencial.getMapaEstrategico().getListaPerspectivaMapaEstrategico()) {
				if (perspectivaMapaEstrategico.getListaObjetivoMapaEstrategico() != null && !perspectivaMapaEstrategico.getListaObjetivoMapaEstrategico().isEmpty()) {
					cadastroCompleto = true;
				}
			}
		}
		
		if (!cadastroCompleto) {
			errors.reject("","� necess�rio cadastrar pelo menos um objetivo estrat�gico no mapa.");
		}
		super.validate(obj, errors, acao);
	}	
	
	public ModelAndView error(WebRequestContext request, DefinicaoEstrategiaFiltro filtro) {
    	return new ModelAndView("process/definicaoEstrategia", "filtro", filtro);
    }	
}