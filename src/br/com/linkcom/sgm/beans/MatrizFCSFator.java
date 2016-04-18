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

import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;

@Entity
@SequenceGenerator(name = "sq_matrizfcsfator", sequenceName = "sq_matrizfcsfator")
public class MatrizFCSFator {
	
	private Integer id;
	private MatrizFCS matrizFCS;
	private String descFator;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_matrizfcsfator")
	public Integer getId() {
		return id;
	}
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY)
	public MatrizFCS getMatrizFCS() {
		return matrizFCS;
	}
	
	@Required
	@MaxLength(100)
	public String getDescFator() {
		return descFator;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMatrizFCS(MatrizFCS matrizFCS) {
		this.matrizFCS = matrizFCS;
	}

	public void setDescFator(String descFator) {
		this.descFator = descFator;
	}
}
