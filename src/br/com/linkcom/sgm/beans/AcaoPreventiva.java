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
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.linkcom.neo.bean.annotation.DescriptionProperty;
import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.types.ListSet;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.beans.enumeration.EficaciaAcaoPreventivaEnum;
import br.com.linkcom.sgm.beans.enumeration.OrigemAcaoPreventivaEnum;
import br.com.linkcom.sgm.beans.enumeration.StatusAcaoPreventivaEnum;
import br.com.linkcom.sgm.beans.enumeration.TipoAcaoEnum;
import br.com.linkcom.sgm.util.Nomes;

@Entity
@DisplayName("A��o Preventiva")
@SequenceGenerator(name = "sq_acaopreventiva", sequenceName = "sq_acaopreventiva")
public class AcaoPreventiva {
	
	private Integer id;
	private Integer sequencial;
	private UnidadeGerencial ugRegistro;
	private Date dataAbertura;
	private Date dataEncerramento;
	private TipoAcaoEnum tipo;
	private OrigemAcaoPreventivaEnum origem;
	private StatusAcaoPreventivaEnum status;
	private String descricao;
	private String observacoes;
	private String conclusao;
	private EficaciaAcaoPreventivaEnum avalEficaciaAcao;
	private String evidenciaEficaciaAcao;
	private Set<PlanoAcao> planosAcao = new ListSet<PlanoAcao>(PlanoAcao.class);

	
	//=========================Get e Set==================================//
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_acaopreventiva")
	public Integer getId() {
		return id;
	}
	
	@DisplayName("Conclus�o da �rea de Qualidade")
	@MaxLength(3000)
	public String getConclusao() {
		return conclusao;
	}
	
	@Required
	@DisplayName("Data de abertura")
	public Date getDataAbertura() {
		return dataAbertura;
	}
	
	@DisplayName("Data de encerramento")
	public Date getDataEncerramento() {
		return dataEncerramento;
	}
	
	@DisplayName("Descri��o da situa��o atual")
	@MaxLength(3000)
	@DescriptionProperty
	@Required
	public String getDescricao() {
		return descricao;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@Required
	@DisplayName("Setor de registro")
	public UnidadeGerencial getUgRegistro() {
		return ugRegistro;
	}
	
	@DisplayName("N�mero")
	@Required
	public Integer getSequencial() {
		return sequencial;
	}
	
	@DisplayName("Observa��es")
	@MaxLength(1500)	
	public String getObservacoes() {
		return observacoes;
	}
	
	@OneToMany(mappedBy="acaoPreventiva")
	@DisplayName("Planos de A��o")	
	public Set<PlanoAcao> getPlanosAcao() {
		return planosAcao;
	}
	
	@DisplayName("Avalia��o da efic�cia da a��o executada")
	public EficaciaAcaoPreventivaEnum getAvalEficaciaAcao() {
		return avalEficaciaAcao;
	}

	@DisplayName("Evid�ncia da efic�cia")
	@MaxLength(1000)
	public String getEvidenciaEficaciaAcao() {
		return evidenciaEficaciaAcao;
	}
	
	@Required
	@DisplayName("Status")
	public StatusAcaoPreventivaEnum getStatus() {
		return status;
	}

	@DisplayName("Tipo")
	public TipoAcaoEnum getTipo() {
		return tipo;
	}

	@DisplayName("Origem")
	public OrigemAcaoPreventivaEnum getOrigem() {
		return origem;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setSequencial(Integer sequencial) {
		this.sequencial = sequencial;
	}

	public void setUgRegistro(UnidadeGerencial ugRegistro) {
		this.ugRegistro = ugRegistro;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public void setTipo(TipoAcaoEnum tipo) {
		this.tipo = tipo;
	}

	public void setOrigem(OrigemAcaoPreventivaEnum origem) {
		this.origem = origem;
	}

	public void setStatus(StatusAcaoPreventivaEnum status) {
		this.status = status;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setConclusao(String conclusao) {
		this.conclusao = conclusao;
	}

	public void setAvalEficaciaAcao(EficaciaAcaoPreventivaEnum avalEficaciaAcao) {
		this.avalEficaciaAcao = avalEficaciaAcao;
	}

	public void setEvidenciaEficaciaAcao(String evidenciaEficaciaAcao) {
		this.evidenciaEficaciaAcao = evidenciaEficaciaAcao;
	}

	public void setPlanosAcao(Set<PlanoAcao> planosAcao) {
		this.planosAcao = planosAcao;
	}	
	
	//=================================Transiente===============================//
	private PlanoGestao planoGestao;
	private AcompanhamentoIndicador acompanhamentoIndicador;
	private Boolean podeEditar;
	private Boolean podeExcluir;
	private Boolean podeImprimir;
	private Boolean encerrado;
	
	@Transient
	@Required
	@DisplayName(Nomes.Plano_de_Gestao)
	public PlanoGestao getPlanoGestao() {
		return planoGestao;
	}
	
	@Transient
	public AcompanhamentoIndicador getAcompanhamentoIndicador() {
		return acompanhamentoIndicador;
	}
	
	@Transient
	public Boolean getPodeEditar() {
		return podeEditar;
	}
	
	@Transient
	public Boolean getPodeExcluir() {
		return podeExcluir;
	}
	
	@Transient
	public Boolean getPodeImprimir() {
		return podeImprimir;
	}
	
	@Transient
	public Boolean getEncerrado() {
		return encerrado;
	}
	
	public void setEncerrado(Boolean encerrado) {
		this.encerrado = encerrado;
	}
	
	public void setPodeImprimir(Boolean podeImprimir) {
		this.podeImprimir = podeImprimir;
	}
	
	public void setPodeEditar(Boolean podeEditar) {
		this.podeEditar = podeEditar;
	}
	
	public void setPodeExcluir(Boolean podeExcluir) {
		this.podeExcluir = podeExcluir;
	}
	
	public void setPlanoGestao(PlanoGestao planoGestao) {
		this.planoGestao = planoGestao;
	}
	
	public void setAcompanhamentoIndicador(AcompanhamentoIndicador acompanhamentoIndicador) {
		this.acompanhamentoIndicador = acompanhamentoIndicador;
	}
}
