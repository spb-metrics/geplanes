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

import br.com.linkcom.neo.bean.annotation.DescriptionProperty;
import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;

@Entity
@SequenceGenerator(name = "sq_requisitonorma", sequenceName = "sq_requisitonorma")
public class RequisitoNorma implements Comparable<RequisitoNorma> {
	
	private Integer id;
	private Norma norma;
	private String descricao;
	private String indice;
	
	//=========================Get e Set==================================//
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_requisitonorma")
	public Integer getId() {
		return id;
	}
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY)
	public Norma getNorma() {
		return norma;
	}
	
	@MaxLength(500)
	@DisplayName("Descri��o")
	@Required
	public String getDescricao() {
		return descricao;
	}
	
	@MaxLength(20)
	@DisplayName("�ndice")
	@Required
	public String getIndice() {
		return indice;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNorma(Norma norma) {
		this.norma = norma;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setIndice(String indice) {
		this.indice = indice;
	}
	
	@Transient
	@DescriptionProperty
	public String getDescricaoCompleta() {
		return indice + " " + (descricao != null && descricao.length() > 80 ? descricao.substring(0, 80) + "..." : descricao);
	}

	public int compareTo(RequisitoNorma that) {
		if (this.getIndice() != null && that.getIndice() != null) {
			String[] thisFields = this.getIndice().split("\\.");
			String[] thatFields = that.getIndice().split("\\.");
			Integer thisField;
			Integer thatField;
			
			int count = thisFields.length < thatFields.length ? thisFields.length : thatFields.length;
			
			for (int i = 0; i < count; i++) {
				try {
					thisField = Integer.parseInt(thisFields[i]);
				}
				catch (NumberFormatException e) {
					thisField = (int) thisFields[i].charAt(0);
				}
				
				try {
					thatField = Integer.parseInt(thatFields[i]);
				}
				catch (NumberFormatException e) {
					thatField = (int) thatFields[i].charAt(0);
				}
				
				if (thisField.compareTo(thatField) != 0) {
					return thisField.compareTo(thatField);
				}
			}
			
			// Caso os peda�os comparados sejam iguais, o menor n�mero � o que tiver menor quantidade de peda�os.
			// Ex: 1 < 1.1
			return thisFields.length < thatFields.length ? -1 : 1;
		}
		return 0;
	}
}
