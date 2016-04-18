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
import br.com.linkcom.neo.controller.resource.ResourceGenerationException;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.neo.report.IReport;
import br.com.linkcom.sgm.controller.report.filtro.MatrizFCSReportFiltro;
import br.com.linkcom.sgm.exception.GeplanesException;
import br.com.linkcom.sgm.service.MatrizFCSService;
import br.com.linkcom.sgm.service.UnidadeGerencialService;
import br.com.linkcom.sgm.service.UsuarioService;
import br.com.linkcom.sgm.util.FiltroUtils;

@Bean
@Controller(path="/sgm/report/MatrizFCS", authorizationModule=ReportAuthorizationModule.class)
public class MatrizFCSReport extends GeplanesReport<MatrizFCSReportFiltro>{

	private MatrizFCSService matrizFCSService;
	private UnidadeGerencialService unidadeGerencialService;
	private UsuarioService usuarioService;
	
	public void setMatrizFCSService(MatrizFCSService MatrizFCSService) {this.matrizFCSService = MatrizFCSService;}
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService){this.unidadeGerencialService = unidadeGerencialService;}
	public void setUsuarioService(UsuarioService usuarioService) {this.usuarioService = usuarioService;}	
	
	@Override
	public String getTitulo() {
		return "Matriz de Iniciativas x Fatores Cr�ticos de Sucesso";
	}
	
	@DefaultAction
	@Override
	public ModelAndView doFiltro(WebRequestContext request,MatrizFCSReportFiltro filtro) throws ResourceGenerationException {
		if (!"true".equals(request.getParameter("reload"))) {
			FiltroUtils.preencheFiltroPlanoGestaoUnidadeUsuario(filtro);
		}
		
		return super.doFiltro(request, filtro);
	}
	
	@Override
	@Action("gerar")	
	public IReport createReportGeplanes(WebRequestContext request, MatrizFCSReportFiltro filtro) throws Exception {
		
		filtro.setUnidadeGerencial(unidadeGerencialService.load(filtro.getUnidadeGerencial()));
		if (!Boolean.TRUE.equals(filtro.getUnidadeGerencial().getPermitirMatrizFcs())) {
			throw new GeplanesException("N�o � permitida a exibi��o da matriz de iniciativas x fcs para essa unidade gerencial.");
		}
		
		if (!usuarioService.isAcessoConsultaAutorizado(filtro.getUnidadeGerencial())) {
			throw new GeplanesException("Voc� n�o tem permiss�o para acessar os dados dessa unidade gerencial.");		
		}
		
		return matrizFCSService.createMatrizFCSReport(filtro);
	}
}
