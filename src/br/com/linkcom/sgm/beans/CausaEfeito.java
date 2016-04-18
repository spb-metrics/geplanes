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

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.types.ListSet;
import br.com.linkcom.neo.validation.annotation.MaxLength;

@Entity
@SequenceGenerator(name = "sq_causaefeito", sequenceName = "sq_causaefeito")
public class CausaEfeito {
	
	private Integer id;
	private String descricao;
	private CausaEfeito efeito;
	private Anomalia anomalia;
	private Set<CausaEfeito> listaCausaEfeito = new ListSet<CausaEfeito>(CausaEfeito.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_causaefeito")
	public Integer getId() {
		return id;
	}
	@DisplayName("Descri��o")
	@MaxLength(150)
	public String getDescricao() {
		return descricao;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@DisplayName("Efeito")
	@JoinColumn(name = "efeito_id")
	public CausaEfeito getEfeito() {
		return efeito;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@DisplayName("Anomalia")
	public Anomalia getAnomalia() {
		return anomalia;
	}
	@Transient
	public Set<CausaEfeito> getListaCausaEfeito() {
		return listaCausaEfeito;
	}
	public void setListaCausaEfeito(Set<CausaEfeito> listaCausaEfeito) {
		this.listaCausaEfeito = listaCausaEfeito;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setEfeito(CausaEfeito efeito) {
		this.efeito = efeito;
	}
	public void setAnomalia(Anomalia anomalia) {
		this.anomalia = anomalia;
	}
	
	

}
