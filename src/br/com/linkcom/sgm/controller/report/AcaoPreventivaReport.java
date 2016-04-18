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

import br.com.linkcom.neo.authorization.report.ReportAuthorizationModule;
import br.com.linkcom.neo.bean.annotation.Bean;
import br.com.linkcom.neo.controller.Action;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.DefaultAction;
import br.com.linkcom.neo.controller.resource.Resource;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.neo.report.IReport;
import br.com.linkcom.sgm.beans.AcaoPreventiva;
import br.com.linkcom.sgm.exception.GeplanesException;
import br.com.linkcom.sgm.service.AcaoPreventivaService;

@Bean
@Controller(path="/sgm/report/AcaoPreventiva", authorizationModule=ReportAuthorizationModule.class)
public class AcaoPreventivaReport extends GeplanesReport<AcaoPreventiva>{

	AcaoPreventivaService acaoPreventivaService;
	public void setAcaoPreventivaService(AcaoPreventivaService acaoPreventivaService) {this.acaoPreventivaService = acaoPreventivaService;}
	
	@Override
	@DefaultAction	
	@Action("gerar")
	public Resource generateResource(WebRequestContext request, AcaoPreventiva filtro)throws Exception {
		return acaoPreventivaService.gerarRelatorioAcaoPreventiva(filtro);	
	}

	@Override
	public String getTitulo() {
		return "A��o Preventiva";
	}

	@Override
	public IReport createReportGeplanes(WebRequestContext request, AcaoPreventiva filtro)throws Exception {
		throw new GeplanesException("O m�todo createReportGeplanes n�o deve ser chamado na gera��o do relat�rio de AcaoPreventivas.");
	}

}
