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

@Entity
@SequenceGenerator(name = "sq_itemauditoriainterna", sequenceName = "sq_itemauditoriainterna")
public class ItemAuditoriaInterna {
	
	private Integer id;
	private AuditoriaInterna auditoriaInterna;
	private RequisitoNorma requisitoNorma;
	private String descricao;
	private UnidadeGerencial ugExterna;
	
	// Transientes
	private Norma norma;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_itemauditoriainterna")
	public Integer getId() {
		return id;
	}
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY)
	public AuditoriaInterna getAuditoriaInterna() {
		return auditoriaInterna;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@DisplayName("Requisito")
	public RequisitoNorma getRequisitoNorma() {
		return requisitoNorma;
	}
	
	@Required
	@DisplayName("Evid�ncias")
	@MaxLength(3000)
	public String getDescricao() {
		return descricao;
	}
	
	@DisplayName("UG respons�vel pelo tratamento (Causa externa)")
	@ManyToOne(fetch=FetchType.LAZY)
	public UnidadeGerencial getUgExterna() {
		return ugExterna;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAuditoriaInterna(AuditoriaInterna auditoriaInterna) {
		this.auditoriaInterna = auditoriaInterna;
	}

	public void setRequisitoNorma(RequisitoNorma requisitoNorma) {
		this.requisitoNorma = requisitoNorma;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setUgExterna(UnidadeGerencial ugExterna) {
		this.ugExterna = ugExterna;
	}
	
	@Transient
	public String getDescricaoCompleta() {
		return requisitoNorma != null ? requisitoNorma.getIndice() + " - " + descricao : descricao;
	}
	
	@DisplayName("Norma")
	@Transient
	public Norma getNorma() {
		if (norma != null) {
			return norma;
		}
		if (requisitoNorma != null && requisitoNorma.getNorma() != null) {
			return requisitoNorma.getNorma();
		}
		return null;
	}
	
	public void setNorma(Norma norma) {
		this.norma = norma;
	}
	
}
