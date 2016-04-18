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
package br.com.linkcom.sgm.beans;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.beans.enumeration.AprovacaoEnum;

@Entity
@SequenceGenerator(name = "sq_solicitacaorepactuacaoindicador", sequenceName = "sq_solicitacaorepactuacaoindicador")
@DisplayName("Solicita��o de repactua��o de indicador")
public class SolicitacaoRepactuacaoIndicador {
	
	private Integer id;
	
	private AprovacaoEnum status;
	private Usuario solicitante;
	private Indicador indicador;	
	private String justificativaSol;
	private String justificativaRes;
	private Date dtSolicitacao;
	private Comentario comentario;
	
	//=========================Get e Set==================================//
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_solicitacaorepactuacaoindicador")
	public Integer getId() {
		return id;
	}
	@DisplayName("Status da solicita��o")	
	public AprovacaoEnum getStatus() {
		return status;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@Required
	public Usuario getSolicitante() {
		return solicitante;
	}
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY)
	public Indicador getIndicador() {
		return indicador;
	}
	
	@DisplayName("Justificativa da solicita��o")
	@MaxLength(500)
	@Required
	public String getJustificativaSol() {
		return justificativaSol;
	}

	@DisplayName("Justificativa da Resposta")
	@MaxLength(500)	
	public String getJustificativaRes() {
		return justificativaRes;
	}
	
	@DisplayName("Data")
	@Required
	public Date getDtSolicitacao() {
		return dtSolicitacao;
	}
	
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}
	public void setStatus(AprovacaoEnum status) {
		this.status = status;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setJustificativaSol(String justificativaSol) {
		this.justificativaSol = justificativaSol;
	}
	public void setJustificativaRes(String justificativaRes) {
		this.justificativaRes = justificativaRes;
	}
	public void setDtSolicitacao(Date dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)	
	public Comentario getComentario() {
		return comentario;
	}
	
	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}	
	
	// TRANSIENTES
	private Boolean aprovado;
	
	@Transient
	public Boolean getAprovado() {
		return aprovado;
	}
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}	
	
}
