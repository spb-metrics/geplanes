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
package br.com.linkcom.sgm.filtro;

import java.util.List;

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.controller.crud.FiltroListagem;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.enumeration.StatusAcaoPreventivaEnum;
import br.com.linkcom.sgm.util.Nomes;

public class AcaoPreventivaFiltro extends FiltroListagem  {
	private Integer sequencial;
	private String descricao;
	private PlanoGestao planoGestao;
	private UnidadeGerencial ugRegistro;
	private StatusAcaoPreventivaEnum status;
	private boolean incluirSubordinadasReg;
	private List<UnidadeGerencial> listaUnidadeGerencialReg;
	private List<UnidadeGerencial> listaUnidadeGerencialDisponivel;
	
	//=========================Get e Set==================================//
	@DisplayName("N�mero")
	public Integer getSequencial() {
		return sequencial;
	}
	
	@DisplayName("Descri��o")
	@MaxLength(100)
	public String getDescricao() {
		return descricao;
	}
	
	@DisplayName(Nomes.Plano_de_Gestao)
	@Required
	public PlanoGestao getPlanoGestao() {
		return planoGestao;
	}
	
	@DisplayName("Setor de registro")
	public UnidadeGerencial getUgRegistro() {
		return ugRegistro;
	}

	@DisplayName("Status")
	public StatusAcaoPreventivaEnum getStatus() {
		return status;
	}
	
	@DisplayName("Incluir subordinadas")
	public boolean isIncluirSubordinadasReg() {
		return incluirSubordinadasReg;
	}
	
	public List<UnidadeGerencial> getListaUnidadeGerencialReg() {
		return listaUnidadeGerencialReg;
	}
	
	public List<UnidadeGerencial> getListaUnidadeGerencialDisponivel() {
		return listaUnidadeGerencialDisponivel;
	}

	public void setSequencial(Integer sequencial) {
		this.sequencial = sequencial;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setPlanoGestao(PlanoGestao planoGestao) {
		this.planoGestao = planoGestao;
	}

	public void setUgRegistro(UnidadeGerencial ugRegistro) {
		this.ugRegistro = ugRegistro;
	}

	public void setStatus(StatusAcaoPreventivaEnum status) {
		this.status = status;
	}

	public void setIncluirSubordinadasReg(boolean incluirSubordinadasReg) {
		this.incluirSubordinadasReg = incluirSubordinadasReg;
	}

	public void setListaUnidadeGerencialReg(
			List<UnidadeGerencial> listaUnidadeGerencialReg) {
		this.listaUnidadeGerencialReg = listaUnidadeGerencialReg;
	}

	public void setListaUnidadeGerencialDisponivel(
			List<UnidadeGerencial> listaUnidadeGerencialDisponivel) {
		this.listaUnidadeGerencialDisponivel = listaUnidadeGerencialDisponivel;
	}

}
