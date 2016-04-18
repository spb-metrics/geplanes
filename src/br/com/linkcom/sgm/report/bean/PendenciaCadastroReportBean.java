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

import java.io.InputStream;

public class PendenciaCadastroReportBean {
	private String unidadeGerencial;
	private InputStream imgMapaNegocio;
	private InputStream imgMapaEstrategico;
	private InputStream imgMapaCompetencia;
	private InputStream imgMatrizFCS;
	private InputStream imgTratamentoAnomalia;
	private InputStream imgPlanoAcaoIniciativa;
	private InputStream imgIndicador;
	private InputStream imgValorBase;
	private InputStream imgValorReal;
	
	
	public String getUnidadeGerencial() {
		return unidadeGerencial;
	}
	public InputStream getImgMapaNegocio() {
		return imgMapaNegocio;
	}
	public InputStream getImgMapaEstrategico() {
		return imgMapaEstrategico;
	}
	public InputStream getImgMapaCompetencia() {
		return imgMapaCompetencia;
	}
	public InputStream getImgMatrizFCS() {
		return imgMatrizFCS;
	}
	public InputStream getImgTratamentoAnomalia() {
		return imgTratamentoAnomalia;
	}
	public InputStream getImgPlanoAcaoIniciativa() {
		return imgPlanoAcaoIniciativa;
	}
	public InputStream getImgIndicador() {
		return imgIndicador;
	}
	public InputStream getImgValorBase() {
		return imgValorBase;
	}
	public InputStream getImgValorReal() {
		return imgValorReal;
	}
	public void setUnidadeGerencial(String unidadeGerencial) {
		this.unidadeGerencial = unidadeGerencial;
	}
	public void setImgMapaNegocio(InputStream imgMapaNegocio) {
		this.imgMapaNegocio = imgMapaNegocio;
	}
	public void setImgMapaEstrategico(InputStream imgMapaEstrategico) {
		this.imgMapaEstrategico = imgMapaEstrategico;
	}
	public void setImgMapaCompetencia(InputStream imgMapaCompetencia) {
		this.imgMapaCompetencia = imgMapaCompetencia;
	}
	public void setImgMatrizFCS(InputStream imgMatrizFCS) {
		this.imgMatrizFCS = imgMatrizFCS;
	}
	public void setImgTratamentoAnomalia(InputStream imgTratamentoAnomalia) {
		this.imgTratamentoAnomalia = imgTratamentoAnomalia;
	}
	public void setImgPlanoAcaoIniciativa(InputStream imgPlanoAcaoIniciativa) {
		this.imgPlanoAcaoIniciativa = imgPlanoAcaoIniciativa;
	}
	public void setImgIndicador(InputStream imgIndicador) {
		this.imgIndicador = imgIndicador;
	}
	public void setImgValorBase(InputStream imgValorBase) {
		this.imgValorBase = imgValorBase;
	}
	public void setImgValorReal(InputStream imgValorReal) {
		this.imgValorReal = imgValorReal;
	}

}
