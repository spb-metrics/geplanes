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

import br.com.linkcom.neo.bean.annotation.DescriptionProperty;
import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.util.Nomes;

@Entity
@SequenceGenerator(name = "sq_ocorrencia", sequenceName = "sq_ocorrencia")
@DisplayName("Di�rio de bordo")
public class Ocorrencia {
	
	private Integer id;
	private Integer numero;
	private String descricao;
	private Date dataLancamento;
	private String situacao;
	private UnidadeGerencial unidadeGerencial;
	private Usuario relator;
	private Boolean reincidente;
	private String contraMedidasImediatas;
	
	
	//=========================Get e Set==================================//
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_ocorrencia")
	public Integer getId() {
		return id;
	}
	@DisplayName("Data de Lan�amento")
	@Required
	public Date getDataLancamento() {
		return dataLancamento;
	}
	@Required
	@DisplayName("Descri��o")
	@MaxLength(1800)
	public String getDescricao() {
		return descricao;
	}
	@DisplayName("N� Di�rio de Bordo")
	@DescriptionProperty
	public Integer getNumero() {
		return numero;
	}
	@DisplayName("Situa��o")
	@MaxLength(100)
	public String getSituacao() {
		return situacao;
	}
	@DisplayName("Relator")
	@ManyToOne(fetch=FetchType.LAZY)
	public Usuario getRelator() {
		return relator;
	}
	@DisplayName("Unidade Gerencial")
	@ManyToOne(fetch=FetchType.LAZY)
	@Required
	public UnidadeGerencial getUnidadeGerencial() {
		return unidadeGerencial;
	}
	@DisplayName("Reincidente")
	public Boolean getReincidente() {
		return reincidente;
	}
	@DisplayName("Corre��o")
	public String getContraMedidasImediatas() {
		return contraMedidasImediatas;
	}
	public void setReincidente(Boolean reincidente) {
		this.reincidente = reincidente;
	}
	public void setContraMedidasImediatas(String contraMedidasImediatas) {
		this.contraMedidasImediatas = contraMedidasImediatas;
	}
	public void setUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		this.unidadeGerencial = unidadeGerencial;
	}
	public void setRelator(Usuario relator) {
		this.relator = relator;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	//=============================Transiente============================//
	private Boolean podeEditar;
	private Boolean podeExcluir;
	private PlanoGestao planoGestao;
	private Anomalia anomalia;
	
	@Transient
	public Boolean getPodeEditar() {
		return podeEditar;
	}
	@Transient
	public Boolean getPodeExcluir() {
		return podeExcluir;
	}	
	@Transient
	@DisplayName(Nomes.Plano_de_Gestao)
	public PlanoGestao getPlanoGestao() {
		return planoGestao;
	}
	@Transient
	public Anomalia getAnomalia() {
		return anomalia;
	}
	public void setAnomalia(Anomalia anomalia) {
		this.anomalia = anomalia;
	}
	public void setPlanoGestao(PlanoGestao planoGestao) {
		this.planoGestao = planoGestao;
	}
	public void setPodeEditar(Boolean podeEditar) {
		this.podeEditar = podeEditar;
	}
	public void setPodeExcluir(Boolean podeExcluir) {
		this.podeExcluir = podeExcluir;
	}
}
