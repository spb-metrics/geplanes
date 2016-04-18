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

public class IndicadoresEstrategicoReportBean {
	String anoGestao;
	String unidadeGerencial;
	String objetivoEstrategico;
	String nome;
	String melhor;
	String unidadeMedida;
	String precisao;
	String tolerancia;
	String frequencia;
	Boolean cancelado;
	String tipoIndicador;
	
	public String getAnoGestao() {
		return anoGestao;
	}
	public String getUnidadeGerencial() {
		return unidadeGerencial;
	}
	public String getObjetivoEstrategico() {
		return objetivoEstrategico;
	}
	public String getNome() {
		return nome;
	}
	public String getMelhor() {
		return melhor;
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public String getPrecisao() {
		return precisao;
	}
	public String getTolerancia() {
		return tolerancia;
	}
	public String getFrequencia() {
		return frequencia;
	}
	public Boolean getCancelado() {
		return cancelado;
	}
	public String getTipoIndicador() {
		return tipoIndicador;
	}
	public void setTipoIndicador(String tipoIndicador) {
		this.tipoIndicador = tipoIndicador;
	}
	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}
	public void setAnoGestao(String anoGestao) {
		this.anoGestao = anoGestao;
	}
	public void setUnidadeGerencial(String unidadeGerencial) {
		this.unidadeGerencial = unidadeGerencial;
	}
	public void setObjetivoEstrategico(String objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setMelhor(String melhor) {
		this.melhor = melhor;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	public void setPrecisao(String precisao) {
		this.precisao = precisao;
	}
	public void setTolerancia(String tolerancia) {
		this.tolerancia = tolerancia;
	}
	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}
}
