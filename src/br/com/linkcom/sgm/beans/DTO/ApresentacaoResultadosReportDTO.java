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
package br.com.linkcom.sgm.beans.DTO;

public class ApresentacaoResultadosReportDTO {

	Integer indicadorid;
	String indicador;
	String data;
	String epoca;	
	String valorLimiteInferior;
	String valorLimiteSuperior;
	String valorReal;
	String percentual;
	String farol;
	String numFarois;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getEpoca() {
		return epoca;
	}
	public void setEpoca(String epoca) {
		this.epoca = epoca;
	}
	public String getFarol() {
		return farol;
	}
	public void setFarol(String farol) {
		this.farol = farol;
	}
	public String getIndicador() {
		return indicador;
	}
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	public Integer getIndicadorid() {
		return indicadorid;
	}
	public void setIndicadorid(Integer indicadorid) {
		this.indicadorid = indicadorid;
	}
	public String getNumFarois() {
		return numFarois;
	}
	public void setNumFarois(String numFarois) {
		this.numFarois = numFarois;
	}
	public String getPercentual() {
		return percentual;
	}
	public void setPercentual(String percentual) {
		this.percentual = percentual;
	}
	public String getValorReal() {
		return valorReal;
	}
	public void setValorReal(String valorReal) {
		this.valorReal = valorReal;
	}
	public String getValorLimiteInferior() {
		return valorLimiteInferior;
	}
	public void setValorLimiteInferior(String valorLimiteInferior) {
		this.valorLimiteInferior = valorLimiteInferior;
	}
	public String getValorLimiteSuperior() {
		return valorLimiteSuperior;
	}
	public void setValorLimiteSuperior(String valorLimiteSuperior) {
		this.valorLimiteSuperior = valorLimiteSuperior;
	}
}
