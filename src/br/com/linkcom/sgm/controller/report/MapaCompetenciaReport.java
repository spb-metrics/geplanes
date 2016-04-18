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
package br.com.linkcom.sgm.controller.report;

import org.springframework.web.servlet.ModelAndView;

import br.com.linkcom.neo.authorization.report.ReportAuthorizationModule;
import br.com.linkcom.neo.bean.annotation.Bean;
import br.com.linkcom.neo.controller.Action;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.DefaultAction;
import br.com.linkcom.neo.controller.resource.Resource;
import br.com.linkcom.neo.controller.resource.ResourceGenerationException;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.neo.report.IReport;
import br.com.linkcom.sgm.controller.report.filtro.MapaCompetenciaReportFiltro;
import br.com.linkcom.sgm.exception.GeplanesException;
import br.com.linkcom.sgm.service.UnidadeGerencialService;
import br.com.linkcom.sgm.service.UsuarioService;
import br.com.linkcom.sgm.util.FiltroUtils;



@Bean
@Controller(path="/sgm/report/MapaCompetencia", authorizationModule=ReportAuthorizationModule.class)
public class MapaCompetenciaReport extends GeplanesReport<MapaCompetenciaReportFiltro>{

	private UnidadeGerencialService unidadeGerencialService;
	private UsuarioService usuarioService;
	
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService){this.unidadeGerencialService = unidadeGerencialService;}
	public void setUsuarioService(UsuarioService usuarioService) {this.usuarioService = usuarioService;}
 
	@Override
	@DefaultAction	
	@Action("gerar")
	public Resource generateResource(WebRequestContext request, MapaCompetenciaReportFiltro filtro)throws Exception {
		
		filtro.setUnidadeGerencial(unidadeGerencialService.load(filtro.getUnidadeGerencial()));
		if (!Boolean.TRUE.equals(filtro.getUnidadeGerencial().getPermitirMapaCompetencia())) {
			throw new GeplanesException("N�o � permitida a exibi��o do mapa de compet�ncias para essa unidade gerencial.");
		}
		
		if (!usuarioService.isAcessoConsultaAutorizado(filtro.getUnidadeGerencial())) {
			throw new GeplanesException("Voc� n�o tem permiss�o para acessar os dados dessa unidade gerencial.");		
		}		
		
		Resource resource = unidadeGerencialService.createMapaCompetenciaReport(filtro);
		if (resource == null) {
			throw new GeplanesException("Mapa de compet�ncias n�o cadastrado para essa unidade gerencial.");
		}
		return resource;
	}	
	
	@Override
	@DefaultAction
	public ModelAndView doFiltro(WebRequestContext request,MapaCompetenciaReportFiltro filtro)throws ResourceGenerationException {
		
		/*** Seta valores default para o filtro ***/		
		FiltroUtils.preencheFiltroPlanoGestaoUnidadeUsuario(filtro);	
		
		return super.doFiltro(request, filtro);
	}
	
	@Override
	public String getTitulo() {
		return "Mapa de Compet�ncias";
	}

	@Override
	public IReport createReportGeplanes(WebRequestContext request, MapaCompetenciaReportFiltro filtro) throws Exception {
		throw new GeplanesException("O m�todo createReportGeplanes n�o deve ser chamado na gera��o do Mapa de Compet�ncias.");
	}
}
