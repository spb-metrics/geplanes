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

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.beans.ObjetivoMapaEstrategico;
import br.com.linkcom.sgm.beans.PerspectivaMapaEstrategico;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.SolicitacaoRepactuacaoIndicador;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.enumeration.AprovacaoEnum;
import br.com.linkcom.sgm.util.Nomes;

public class SolicitacaoRepactuacaoIndicadorFiltro{
	private PlanoGestao planoGestao;
	private UnidadeGerencial unidadeGerencial;
	private PerspectivaMapaEstrategico perspectivaMapaEstrategico;
	private ObjetivoMapaEstrategico objetivoMapaEstrategico;
	private AprovacaoEnum status;
	private List<SolicitacaoRepactuacaoIndicador> listaSolicitacaoRepactuacaoIndicador;
	private List<UnidadeGerencial> listaUnidadeGerencialDisponivel;
	
	@DisplayName(Nomes.Plano_de_Gestao)
	@Required	
	public PlanoGestao getPlanoGestao() {
		return planoGestao;
	}
	@DisplayName("Unidade Gerencial")
	public UnidadeGerencial getUnidadeGerencial() {
		return unidadeGerencial;
	}
	@DisplayName("Perspectiva")
	public PerspectivaMapaEstrategico getPerspectivaMapaEstrategico() {
		return perspectivaMapaEstrategico;
	}
	@DisplayName(Nomes.Estrategia)
	public ObjetivoMapaEstrategico getObjetivoMapaEstrategico() {
		return objetivoMapaEstrategico;
	}
	@DisplayName("Status da solicita��o")
	public AprovacaoEnum getStatus() {
		return status;
	}
	public List<SolicitacaoRepactuacaoIndicador> getListaSolicitacaoRepactuacaoIndicador() {
		return listaSolicitacaoRepactuacaoIndicador;
	}
	public void setPlanoGestao(PlanoGestao planoGestao) {
		this.planoGestao = planoGestao;
	}
	public void setUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		this.unidadeGerencial = unidadeGerencial;
	}
	public void setPerspectivaMapaEstrategico(PerspectivaMapaEstrategico perspectivaMapaEstrategico) {
		this.perspectivaMapaEstrategico = perspectivaMapaEstrategico;
	}	
	public void setObjetivoMapaEstrategico(ObjetivoMapaEstrategico objetivoMapaEstrategico) {
		this.objetivoMapaEstrategico = objetivoMapaEstrategico;
	}
	public void setStatus(AprovacaoEnum status) {
		this.status = status;
	}
	public void setListaSolicitacaoRepactuacaoIndicador(
			List<SolicitacaoRepactuacaoIndicador> listaSolicitacaoRepactuacaoIndicador) {
		this.listaSolicitacaoRepactuacaoIndicador = listaSolicitacaoRepactuacaoIndicador;
	}
	
	// AUXILIARES
	private Integer id;
	private Boolean aprovar;
	
	public Integer getId() {
		return id;
	}
	public Boolean getAprovar() {
		return aprovar;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setAprovar(Boolean aprovar) {
		this.aprovar = aprovar;
	}
	public List<UnidadeGerencial> getListaUnidadeGerencialDisponivel() {
		return listaUnidadeGerencialDisponivel;
	}
	public void setListaUnidadeGerencialDisponivel(
			List<UnidadeGerencial> listaUnidadeGerencialDisponivel) {
		this.listaUnidadeGerencialDisponivel = listaUnidadeGerencialDisponivel;
	}	
}
