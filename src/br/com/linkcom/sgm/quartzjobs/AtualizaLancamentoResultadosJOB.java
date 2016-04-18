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
package br.com.linkcom.sgm.quartzjobs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.linkcom.neo.core.standard.Neo;
import br.com.linkcom.sgm.beans.AcompanhamentoIndicador;
import br.com.linkcom.sgm.beans.Indicador;
import br.com.linkcom.sgm.beans.LogProcesso;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.service.AcompanhamentoIndicadorService;
import br.com.linkcom.sgm.service.IndicadorService;
import br.com.linkcom.sgm.service.LogProcessoService;
import br.com.linkcom.sgm.service.PlanoGestaoService;
import br.com.linkcom.sgm.util.calculos.CalculosAuxiliares;

public class AtualizaLancamentoResultadosJOB implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		atualizaLancamentoResultados();
	}	
	
	public void atualizaLancamentoResultados() {

		// Services
		PlanoGestaoService planoGestaoService = Neo.getObject(PlanoGestaoService.class);
		IndicadorService indicadorService = Neo.getObject(IndicadorService.class);
		AcompanhamentoIndicadorService acompanhamentoIndicadorService = Neo.getObject(AcompanhamentoIndicadorService.class);
		LogProcessoService logProcessoService = Neo.getObject(LogProcessoService.class);
		
		// Listas
		List<AcompanhamentoIndicador> listaAcompanhamentoIndicador;
		
		// Geral
		Integer trimestreAcompanhamento;
		
		// Busca o ano de gest�o atual
		PlanoGestao planoGestaoAtual = planoGestaoService.obtemPlanoGestaoAtual();		
		
		// Busca todos os indicadores cadastrados para o ano em quest�o
		List<Indicador> listaIndicador = indicadorService.findBy(planoGestaoAtual, null, null, "indicador.id");
		
		if (listaIndicador != null && !listaIndicador.isEmpty()) {
			
			System.out.println("------------------------------------------------------------");
			System.out.println(" Iniciando processo de atualiza��o lan�amento de resultados ");
			System.out.println("------------------------------------------------------------");			
			
			for (Indicador indicador : listaIndicador) {
				
				listaAcompanhamentoIndicador = new ArrayList<AcompanhamentoIndicador>(indicador.getAcompanhamentosIndicador());
				for (AcompanhamentoIndicador acompanhamentoIndicador : listaAcompanhamentoIndicador) {
					acompanhamentoIndicador.setIndicador(indicador);
					
					trimestreAcompanhamento = CalculosAuxiliares.getAcompanhamentoTrimestre(indicador.getFrequencia(), acompanhamentoIndicador.getIndice());
					
					// Somente para os acompanhamentos aplic�veis ao per�odo
					if (!Boolean.TRUE.equals(acompanhamentoIndicador.getNaoaplicavel())) {
						//Se o resultado n�o tiver sido lan�ado e a data limite para lan�amento esteja expirada, atualiza com o valor 0.
						if (acompanhamentoIndicador.getValorReal() == null && planoGestaoService.isDtTravLancResultadosExpirada(planoGestaoAtual, trimestreAcompanhamento)) {
								
								System.out.println("Inserindo o valor 0 para o resultado do acompanhamento: " + acompanhamentoIndicador.getId());
								
								// Atualiza o valor no banco de dados;
								acompanhamentoIndicadorService.updateValorReal(acompanhamentoIndicador, 0.0);
						}
					}
				}
			}
			
			System.out.println("--------------------------------------------------------------");
			System.out.println(" Finalizando processo de atualiza��o lan�amento de resultados ");
			System.out.println("--------------------------------------------------------------");			
		}
		
		// Atualiza no banco de dados a data de execu��o do processo
		LogProcesso logProcesso = logProcessoService.getLogProcessoAtual();
		logProcesso.setDtAtualizaLancamentoResultados(new Timestamp(System.currentTimeMillis()));
		logProcessoService.saveOrUpdate(logProcesso);
	}	
}