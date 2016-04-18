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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.linkcom.neo.bean.annotation.DescriptionProperty;
import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.util.Nomes;

@Entity
@SequenceGenerator(name = "sq_planogestao", sequenceName = "sq_planogestao")
public class PlanoGestao {
	
	private Integer id;
	
	private String descricao;
	private Integer anoExercicio;
	private Date limiteCriacaoMetasIndicadores;
	private Date limiteCriacaoMapaNegocio;	
	private Date limiteCriacaoMapaEstrategico;	
	private Date limiteCriacaoMapaCompetencia;	
	private Date limiteCriacaoMatrizFcs;	
	private Boolean lembreteCriacaoMetasIndicadores;
	
	/* Datas-limite para o lan�amento de resultados por trimestre. 
	 * A partir de cada uma dessas datas, os respons�veis por cada UG 
	 * onde tenha algum indicador com resultado faltante ser�o notificados 
	 * atrav�s de email. 
	 * Os emails ser�o enviados com uma periodicidade definida atrav�s
	 * do par�metro 'diasLembreteLancamentoValoresReais' at� a respectiva
	 * data de travamento do lan�amento de resultados, a partir da qual 
	 * somente os administradores poder�o lan�ar / alterar os resultados */
	private Date dtLimLancRes1T;
	private Date dtLimLancRes2T;
	private Date dtLimLancRes3T;
	private Date dtLimLancRes4T;
	
	/* Datas para o travamento do lan�amento de resultados por trimestre. 
	 * A partir de cada uma dessas datas, com exce��o dos administradores,
	 * os usu�rios ficar�o impedidos de lan�ar / alterar os resultados. */	
	private Date dtTravLancRes1T;
	private Date dtTravLancRes2T;
	private Date dtTravLancRes3T;
	private Date dtTravLancRes4T;
	
	private List<UnidadeGerencial> unidadesGerenciais = new ArrayList<UnidadeGerencial>();	
	
	public PlanoGestao() {
	}
	
	public PlanoGestao(Integer id) {
		this.id = id;
	}
	
	//=========================Get e Set==================================//
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_planogestao")
	public Integer getId() {
		return id;
	}
	
	@DescriptionProperty
	@DisplayName("Ano do exerc�cio")
	@Required
	@MaxLength(4)
	public Integer getAnoExercicio() {
		return anoExercicio;
	}
	
	@DisplayName("Descri��o")
	@MaxLength(500)
	public String getDescricao() {
		return descricao;
	}
	
	@DisplayName("Data limite para cria��o de indicadores/iniciativas/planos de a��o")
	@Required
	public Date getLimiteCriacaoMetasIndicadores() {
		return limiteCriacaoMetasIndicadores;
	}
	@DisplayName("Data limite para lan�amento de " + Nomes.valores_reais + " - 1�T")
	@Required	
	public Date getDtLimLancRes1T() {
		return dtLimLancRes1T;
	}
	@DisplayName("Data limite para lan�amento de " + Nomes.valores_reais + " - 2�T")
	@Required
	public Date getDtLimLancRes2T() {
		return dtLimLancRes2T;
	}
	@DisplayName("Data limite para lan�amento de " + Nomes.valores_reais + " - 3�T")
	@Required
	public Date getDtLimLancRes3T() {
		return dtLimLancRes3T;
	}
	@DisplayName("Data limite para lan�amento de " + Nomes.valores_reais + " - 4�T")
	@Required
	public Date getDtLimLancRes4T() {
		return dtLimLancRes4T;
	}
	@DisplayName("Data para travamento do lan�amento de " + Nomes.valores_reais + " - 1�T")
	@Required
	public Date getDtTravLancRes1T() {
		return dtTravLancRes1T;
	}
	@DisplayName("Data para travamento do lan�amento de " + Nomes.valores_reais + " - 2�T")
	@Required
	public Date getDtTravLancRes2T() {
		return dtTravLancRes2T;
	}
	@DisplayName("Data para travamento do lan�amento de " + Nomes.valores_reais + " - 3�T")
	@Required
	public Date getDtTravLancRes3T() {
		return dtTravLancRes3T;
	}
	@DisplayName("Data para travamento do lan�amento de " + Nomes.valores_reais + " - 4�T")
	@Required
	public Date getDtTravLancRes4T() {
		return dtTravLancRes4T;
	}
	@DisplayName("Data limite para cria��o do mapa do neg�cio")
	@Required	
	public Date getLimiteCriacaoMapaNegocio() {
		return limiteCriacaoMapaNegocio;
	}
	@DisplayName("Lembrete de data limite para cria��o de indicadores enviado")
	public Boolean getLembreteCriacaoMetasIndicadores() {
		return lembreteCriacaoMetasIndicadores;
	}

	@OneToMany(mappedBy="planoGestao")
	public List<UnidadeGerencial> getUnidadesGerenciais() {
		return unidadesGerenciais;
	}
	@DisplayName("Data limite para cria��o do mapa estrat�gico")
	@Required
	public Date getLimiteCriacaoMapaEstrategico() {
		return limiteCriacaoMapaEstrategico;
	}
	@DisplayName("Data limite para cria��o do mapa de compet�ncias")
	@Required
	public Date getLimiteCriacaoMapaCompetencia() {
		return limiteCriacaoMapaCompetencia;
	}
	@DisplayName("Data limite para cria��o da matriz de iniciativas x fcs")
	@Required
	public Date getLimiteCriacaoMatrizFcs() {
		return limiteCriacaoMatrizFcs;
	}
	public void setAnoExercicio(Integer anoExercicio) {
		this.anoExercicio = anoExercicio;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setLimiteCriacaoMetasIndicadores(
			Date limiteCriacaoMetasIndicadores) {
		this.limiteCriacaoMetasIndicadores = limiteCriacaoMetasIndicadores;
	}
	public void setDtLimLancRes1T(Date dtLimLancRes1T) {
		this.dtLimLancRes1T = dtLimLancRes1T;
	}
	public void setDtLimLancRes2T(Date dtLimLancRes2T) {
		this.dtLimLancRes2T = dtLimLancRes2T;
	}
	public void setDtLimLancRes3T(Date dtLimLancRes3T) {
		this.dtLimLancRes3T = dtLimLancRes3T;
	}
	public void setDtLimLancRes4T(Date dtLimLancRes4T) {
		this.dtLimLancRes4T = dtLimLancRes4T;
	}
	public void setDtTravLancRes1T(Date dtTravLancRes1T) {
		this.dtTravLancRes1T = dtTravLancRes1T;
	}
	public void setDtTravLancRes2T(Date dtTravLancRes2T) {
		this.dtTravLancRes2T = dtTravLancRes2T;
	}
	public void setDtTravLancRes3T(Date dtTravLancRes3T) {
		this.dtTravLancRes3T = dtTravLancRes3T;
	}
	public void setDtTravLancRes4T(Date dtTravLancRes4T) {
		this.dtTravLancRes4T = dtTravLancRes4T;
	}
	public void setLimiteCriacaoMapaNegocio(Date limiteCriacaoMapaNegocio) {
		this.limiteCriacaoMapaNegocio = limiteCriacaoMapaNegocio;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLembreteCriacaoMetasIndicadores(
			Boolean lembreteCriacaoMetasIndicadores) {
		this.lembreteCriacaoMetasIndicadores = lembreteCriacaoMetasIndicadores;
	}
	public void setUnidadesGerenciais(List<UnidadeGerencial> unidadesGerenciais) {
		this.unidadesGerenciais = unidadesGerenciais;
	}
	public void setLimiteCriacaoMapaEstrategico(Date limiteCriacaoMapaEstrategico) {
		this.limiteCriacaoMapaEstrategico = limiteCriacaoMapaEstrategico;
	}
	public void setLimiteCriacaoMapaCompetencia(Date limiteCriacaoMapaCompetencia) {
		this.limiteCriacaoMapaCompetencia = limiteCriacaoMapaCompetencia;
	}
	public void setLimiteCriacaoMatrizFcs(Date limiteCriacaoMatrizFcs) {
		this.limiteCriacaoMatrizFcs = limiteCriacaoMatrizFcs;
	}	

	
	//=========================Transientes==================================//
	private Boolean copia;
	
	@Transient
	public Boolean getCopia() {
		return copia;
	}
	
	public void setCopia(Boolean copia) {
		this.copia = copia;
	}
}
