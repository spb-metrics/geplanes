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
@SequenceGenerator(name = "sq_norma", sequenceName = "sq_norma")
public class Norma {
	
	private Integer id;
	private String nome;
	private String descricao;
	
	private List<RequisitoNorma> listaRequisitoNorma = new ListSet<RequisitoNorma>(RequisitoNorma.class);
	
	public Norma(Integer id) {
		this.id = id;
	}
	
	public Norma() {
	}
	
	//=========================Get e Set==================================//
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_norma")
	public Integer getId() {
		return id;
	}
	
	@Required
	@DisplayName("Nome")
	@MaxLength(100)
	@DescriptionProperty
	public String getNome() {
		return nome;
	}
	
	@MaxLength(500)
	@DisplayName("Descri��o")
	public String getDescricao() {
		return descricao;
	}

	@DisplayName("Requisitos")
	@OneToMany(mappedBy="norma")
	public List<RequisitoNorma> getListaRequisitoNorma() {
		return listaRequisitoNorma;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setListaRequisitoNorma(List<RequisitoNorma> listaRequisitoNorma) {
		this.listaRequisitoNorma = listaRequisitoNorma;
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( !(obj instanceof Norma) ) return false;
		Norma that = (Norma) obj;
		if (this.id == null || that.getId() == null ) return false; 
		return this.id.equals(that.getId());
	}	
}
