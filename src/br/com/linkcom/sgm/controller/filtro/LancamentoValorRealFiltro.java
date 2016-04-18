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
package br.com.linkcom.sgm.controller.filtro;

import java.util.List;

import br.com.linkcom.sgm.beans.Anomalia;
import br.com.linkcom.sgm.beans.PerspectivaMapaEstrategico;
import br.com.linkcom.sgm.beans.enumeration.FrequenciaIndicadorEnum;


public class LancamentoValorRealFiltro extends PlanoGestaoUnidadeGerencialFiltro {
	private FrequenciaIndicadorEnum frequencia;
	private List<PerspectivaMapaEstrategico> listaPerspectivaMapaEstrategico;
	private List<Anomalia> listaAnomalias;
	private Boolean alternar = new Boolean(false);	
	
	//============================get e set==============================//
	public List<PerspectivaMapaEstrategico> getListaPerspectivaMapaEstrategico() {
		return listaPerspectivaMapaEstrategico;
	}
	
	public FrequenciaIndicadorEnum getFrequencia() {
		return frequencia;
	}
	
	public void setFrequencia(FrequenciaIndicadorEnum frequencia) {
		this.frequencia = frequencia;
	}
	
	public void setListaPerspectivaMapaEstrategico(List<PerspectivaMapaEstrategico> listaPerspectivaMapaEstrategico) {
		this.listaPerspectivaMapaEstrategico = listaPerspectivaMapaEstrategico;
	}
	
	public List<Anomalia> getListaAnomalias() {
		return listaAnomalias;
	}
	
	public Boolean getAlternar() {
		return alternar;
	}
		
	public void setListaAnomalias(List<Anomalia> listaAnomalias) {
		this.listaAnomalias = listaAnomalias;
	}	
	
	public void setAlternar(Boolean alternar) {
		this.alternar = alternar;
	}	
}
