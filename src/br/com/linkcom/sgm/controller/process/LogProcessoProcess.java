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

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import br.com.linkcom.neo.authorization.process.ProcessAuthorizationModule;
import br.com.linkcom.neo.bean.annotation.Bean;
import br.com.linkcom.neo.controller.Action;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.DefaultAction;
import br.com.linkcom.neo.controller.MultiActionController;
import br.com.linkcom.neo.core.web.NeoWeb;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.sgm.beans.LogProcesso;
import br.com.linkcom.sgm.exception.GeplanesException;
import br.com.linkcom.sgm.quartzjobs.AtualizaLancamentoResultadosJOB;
import br.com.linkcom.sgm.quartzjobs.AtualizaStatusAnomaliaJOB;
import br.com.linkcom.sgm.quartzjobs.EnviaEmailLembreteCriacaoIndicadoresJOB;
import br.com.linkcom.sgm.quartzjobs.EnviaEmailLembreteLancamentoResultadosJOB;
import br.com.linkcom.sgm.quartzjobs.EnviaEmailLembreteTratamentoAnomaliaJOB;
import br.com.linkcom.sgm.service.LogProcessoService;
import br.com.linkcom.sgm.util.GeplanesUtils;

@Bean
@Controller(path="/sgm/process/LogProcesso", authorizationModule=ProcessAuthorizationModule.class)
public class LogProcessoProcess extends MultiActionController {
	
	private LogProcessoService logProcessoService;
	
	public void setLogProcessoService(LogProcessoService logProcessoService) {
		this.logProcessoService = logProcessoService;
	}
	
	@DefaultAction
	public ModelAndView index(WebRequestContext request){
		return new ModelAndView("process/logProcesso","logProcesso",findLogProcessoItens());
	}	
	
	@Action("lembCriacaoIndicador")
	public ModelAndView lembCriacaoIndicador(WebRequestContext request){
		try {
			EnviaEmailLembreteCriacaoIndicadoresJOB job = new EnviaEmailLembreteCriacaoIndicadoresJOB();
			job.setApplicationPath(GeplanesUtils.getApplicationPath(NeoWeb.getRequestContext().getServletRequest()));
			job.enviaEmail();
			request.addMessage("Lembretes de cria��o de indicadores enviados com sucesso.");
		}
		catch (GeplanesException e) {
			request.addError(e.getMessage());
		}
		return new ModelAndView("process/logProcesso","logProcesso",findLogProcessoItens());	
	}
	
	@Action("lembLancResultado")
	public ModelAndView lembLancResultado(WebRequestContext request){
		try {
			EnviaEmailLembreteLancamentoResultadosJOB job = new EnviaEmailLembreteLancamentoResultadosJOB();
			job.setApplicationPath(GeplanesUtils.getApplicationPath(NeoWeb.getRequestContext().getServletRequest()));
			job.enviaEmail();
			request.addMessage("Lembretes de lan�amento de resultados enviados com sucesso.");
		}
		catch (GeplanesException e) {
			request.addError(e.getMessage());
		}
		return new ModelAndView("process/logProcesso","logProcesso",findLogProcessoItens());	
	}
	
	@Action("lembTratAnomalia")
	public ModelAndView lembTratAnomalia(WebRequestContext request){
		try {
			EnviaEmailLembreteTratamentoAnomaliaJOB job = new EnviaEmailLembreteTratamentoAnomaliaJOB();
			job.setApplicationPath(GeplanesUtils.getApplicationPath(NeoWeb.getRequestContext().getServletRequest()));
			job.enviaEmail();
			request.addMessage("Lembretes de tratamento de anomalias enviados com sucesso.");
		}
		catch (GeplanesException e) {
			request.addError(e.getMessage());
		}
		return new ModelAndView("process/logProcesso","logProcesso",findLogProcessoItens());	
	}
	
	@Action("statusAnomalia")
	public ModelAndView statusAnomalia(WebRequestContext request){
		try {
			AtualizaStatusAnomaliaJOB job = new AtualizaStatusAnomaliaJOB();
			job.atualizaStatusAnomalia();
			request.addMessage("Status das anomalias atualizados com sucesso.");
		}
		catch (GeplanesException e) {
			request.addError(e.getMessage());
		}
		return new ModelAndView("process/logProcesso","logProcesso",findLogProcessoItens());	
	}
	
	@Action("statusLancamentoResultados")
	public ModelAndView statusLancamentoResultados(WebRequestContext request){
		try {
			AtualizaLancamentoResultadosJOB job = new AtualizaLancamentoResultadosJOB();
			job.atualizaLancamentoResultados();
			request.addMessage("Lan�amento de resultados atualizados com sucesso.");
		}
		catch (GeplanesException e) {
			request.addError(e.getMessage());
		}
		return new ModelAndView("process/logProcesso","logProcesso",findLogProcessoItens());	
	}
	
	

	private List<LogProcesso> findLogProcessoItens() {
		List<LogProcesso> itens = logProcessoService.findAll();
		List<LogProcesso> listaLogProcesso = new ArrayList<LogProcesso>();
		LogProcesso log;
	
		if (itens != null && itens.size() > 0) {
			log = new LogProcesso(itens.get(0).getDtLembCriacaoIndicador(),"Lembrete de cria��o de indicadores","lembCriacaoIndicador");
			listaLogProcesso.add(log);
			
			log = new LogProcesso(itens.get(0).getDtLembLancResultado(),"Lembrete de lan�amento de resultados","lembLancResultado");
			listaLogProcesso.add(log);
			
			log = new LogProcesso(itens.get(0).getDtLembTratAnomalia(),"Lembrete de tratamento de anomalias","lembTratAnomalia");
			listaLogProcesso.add(log);
			
			log = new LogProcesso(itens.get(0).getDtAtualizaStatusAnomalia(),"Atualiza��o de status de anomalias","statusAnomalia");
			listaLogProcesso.add(log);
			
			log = new LogProcesso(itens.get(0).getDtAtualizaLancamentoResultados(),"Atualiza��o de lan�amento de resultados","statusLancamentoResultados");
			listaLogProcesso.add(log);
		}
		return listaLogProcesso;
	}	
	
}