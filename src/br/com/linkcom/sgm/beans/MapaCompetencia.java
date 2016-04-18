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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.types.ListSet;

@Entity
@SequenceGenerator(name = "sq_mapacompetencia", sequenceName = "sq_mapacompetencia")
public class MapaCompetencia {
	
	private Integer id;
	private UnidadeGerencial unidadeGerencial;
	
	/* Campo TRANSIENTE. Seu cadastro � feito no Mapa do Neg�cio. */
	private String missao;
	
	private List<Competencia> competencias = new ListSet<Competencia>(Competencia.class);
	private List<Atividade> atividades = new ListSet<Atividade>(Atividade.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_mapacompetencia")
	public Integer getId() {
		return id;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	public UnidadeGerencial getUnidadeGerencial() {
		return unidadeGerencial;
	}
	
	@Transient
	@DisplayName("Miss�o")
	public String getMissao() {
		return missao;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		this.unidadeGerencial = unidadeGerencial;
	}
	
	public void setMissao(String missao) {
		this.missao = missao;
	}
	
	// LISTAS
	
	@OneToMany(mappedBy = "mapaCompetencia")
	public List<Atividade> getAtividades() {
		return atividades;
	}
	
	@OneToMany(mappedBy = "mapaCompetencia")
	public List<Competencia> getCompetencias() {
		return competencias;
	}
	
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	
	public void setCompetencias(List<Competencia> competencias) {
		this.competencias = competencias;
	}
	
}
