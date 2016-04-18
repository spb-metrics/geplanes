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
package br.com.linkcom.sgm.controller.report.filtro;

import java.util.List;

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.controller.crud.FiltroListagem;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.enumeration.StatusAnomaliaEnum;
import br.com.linkcom.sgm.beans.enumeration.StatusTratamentoAnomaliaEnum;
import br.com.linkcom.sgm.util.Nomes;


public class AnomaliaListagemReportFiltro extends FiltroListagem {
	private PlanoGestao planoGestao;
	private UnidadeGerencial ugResponsavel;
	private UnidadeGerencial ugRegistro;
	private StatusAnomaliaEnum status;
	private StatusTratamentoAnomaliaEnum statusTratamento;
	private boolean incluirSubordinadasReg;
	private List<UnidadeGerencial> listaUnidadeGerencialReg;
	private boolean incluirSubordinadasResp;
	private List<UnidadeGerencial> listaUnidadeGerencialResp;
	private List<UnidadeGerencial> listaUnidadeGerencialDisponivel;	
	
	@DisplayName(Nomes.Plano_de_Gestao)
	public PlanoGestao getPlanoGestao() {
		return planoGestao;
	}
	@DisplayName("setor respons�vel pelo tratamento da anomalia")
	public UnidadeGerencial getUgResponsavel() {
		return ugResponsavel;
	}
	@DisplayName("setor de registro da anomalia")
	public UnidadeGerencial getUgRegistro() {
		return ugRegistro;
	}
	
	public void setUgResponsavel(UnidadeGerencial ugResponsavel) {
		this.ugResponsavel = ugResponsavel;
	}

	public void setUgRegistro(UnidadeGerencial ugRegistro) {
		this.ugRegistro = ugRegistro;
	}

	public void setPlanoGestao(PlanoGestao planoGestao) {
		this.planoGestao = planoGestao;
	}
	@DisplayName("Status da Anomalia")
	public StatusAnomaliaEnum getStatus() {
		return status;
	}
	@DisplayName("Status do Tratamento")
	public StatusTratamentoAnomaliaEnum getStatusTratamento() {
		return statusTratamento;
	}
	public void setStatus(StatusAnomaliaEnum status) {
		this.status = status;
	}
	public void setStatusTratamento(StatusTratamentoAnomaliaEnum statusTratamento) {
		this.statusTratamento = statusTratamento;
	}
	@DisplayName("Incluir subordinadas")
	public boolean isIncluirSubordinadasReg() {
		return incluirSubordinadasReg;
	}
	public List<UnidadeGerencial> getListaUnidadeGerencialReg() {
		return listaUnidadeGerencialReg;
	}
	@DisplayName("Incluir subordinadas")
	public boolean isIncluirSubordinadasResp() {
		return incluirSubordinadasResp;
	}
	public List<UnidadeGerencial> getListaUnidadeGerencialResp() {
		return listaUnidadeGerencialResp;
	}
	public List<UnidadeGerencial> getListaUnidadeGerencialDisponivel() {
		return listaUnidadeGerencialDisponivel;
	}
	public void setIncluirSubordinadasReg(boolean incluirSubordinadasReg) {
		this.incluirSubordinadasReg = incluirSubordinadasReg;
	}
	public void setListaUnidadeGerencialReg(
			List<UnidadeGerencial> listaUnidadeGerencialReg) {
		this.listaUnidadeGerencialReg = listaUnidadeGerencialReg;
	}
	public void setIncluirSubordinadasResp(boolean incluirSubordinadasResp) {
		this.incluirSubordinadasResp = incluirSubordinadasResp;
	}
	public void setListaUnidadeGerencialResp(
			List<UnidadeGerencial> listaUnidadeGerencialResp) {
		this.listaUnidadeGerencialResp = listaUnidadeGerencialResp;
	}
	public void setListaUnidadeGerencialDisponivel(
			List<UnidadeGerencial> listaUnidadeGerencialDisponivel) {
		this.listaUnidadeGerencialDisponivel = listaUnidadeGerencialDisponivel;
	}
}
