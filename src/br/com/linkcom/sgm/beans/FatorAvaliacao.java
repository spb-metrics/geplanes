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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.com.linkcom.neo.bean.annotation.DescriptionProperty;
import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.types.ListSet;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;

@Entity
@DisplayName("Sistema de pontua��o")
@SequenceGenerator(name = "sq_fatoravaliacao", sequenceName = "sq_fatoravaliacao")
public class FatorAvaliacao {
	
	private Integer id;
	private String nome;
	private Boolean utilizarMatrizFCS = Boolean.FALSE;

	private List<ItemFatorAvaliacao> listaItemFatorAvaliacao = new ListSet<ItemFatorAvaliacao>(ItemFatorAvaliacao.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_fatoravaliacao")
	public Integer getId() {
		return id;
	}
	
	@Required
	@DisplayName("Nome")
	@MaxLength(50)
	@DescriptionProperty
	public String getNome() {
		return nome;
	}
	
	@DisplayName("Fatores de avalia��o")
	@OneToMany(mappedBy="fatorAvaliacao")
	public List<ItemFatorAvaliacao> getListaItemFatorAvaliacao() {
		return listaItemFatorAvaliacao;
	}
	
	@DisplayName("Utilizar na Matriz FCS")
	public Boolean getUtilizarMatrizFCS() {
		return utilizarMatrizFCS;
	}
	
	public void setUtilizarMatrizFCS(Boolean utilizarMatrizFCS) {
		this.utilizarMatrizFCS = utilizarMatrizFCS;
	}

	public void setListaItemFatorAvaliacao(
			List<ItemFatorAvaliacao> listaItemFatorAvaliacao) {
		this.listaItemFatorAvaliacao = listaItemFatorAvaliacao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FatorAvaliacao) {
			FatorAvaliacao that = (FatorAvaliacao) obj;
			return this.getId().equals(that.getId());
		}
		return false;
	}
}
