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
package br.com.linkcom.sgm.report.bean;

import java.util.Map;

public class DesempenhoReportBean {
	protected String descObjetivoEstrategico;
	protected Integer idUnidadeGerencial;
	protected String descUnidadeGerencial;
	protected String descMeta;
	protected String descIndicador;
	Map<Integer,Double> mapaPercentualEpoca;
	
	public String getDescObjetivoEstrategico() {
		return descObjetivoEstrategico;
	}
	
	public void setDescObjetivoEstrategico(String descObjetivoEstrategico) {
		this.descObjetivoEstrategico = descObjetivoEstrategico;
	}
	
	public Integer getIdUnidadeGerencial() {
		return idUnidadeGerencial;
	}
	
	public void setIdUnidadeGerencial(Integer idUnidadeGerencial) {
		this.idUnidadeGerencial = idUnidadeGerencial;
	}
	
	public String getDescUnidadeGerencial() {
		return descUnidadeGerencial;
	}
	
	public void setDescUnidadeGerencial(String descUnidadeGerencial) {
		this.descUnidadeGerencial = descUnidadeGerencial;
	}
	
	public String getDescMeta() {
		return descMeta;
	}
	
	public void setDescMeta(String descMeta) {
		this.descMeta = descMeta;
	}
	
	public String getDescIndicador() {
		return descIndicador;
	}
	
	public void setDescIndicador(String descIndicador) {
		this.descIndicador = descIndicador;
	}
	
	public Map<Integer, Double> getMapaPercentualEpoca() {
		return mapaPercentualEpoca;
	}
	
	public void setMapaPercentualEpoca(Map<Integer, Double> mapaPercentualEpoca) {
		this.mapaPercentualEpoca = mapaPercentualEpoca;
	}
}
