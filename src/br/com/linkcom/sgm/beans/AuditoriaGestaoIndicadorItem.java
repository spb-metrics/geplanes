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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;

@Entity
@SequenceGenerator(name = "sq_auditoriagestaoindicadoritem", sequenceName = "sq_auditoriagestaoindicadoritem")
public class AuditoriaGestaoIndicadorItem {
	
	private Integer id;
	private AuditoriaGestaoIndicador auditoriaGestaoIndicador;
	private ItemModeloAuditoriaGestao itemModeloAuditoriaGestao;
	private ItemFatorAvaliacao itemFatorAvaliacao;
	private String texto;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_auditoriagestaoindicadoritem")
	public Integer getId() {
		return id;
	}
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="auditoriaGestaoIndicador_id")
	public AuditoriaGestaoIndicador getAuditoriaGestaoIndicador() {
		return auditoriaGestaoIndicador;
	}
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemModeloAuditoriaGestao_id")
	public ItemModeloAuditoriaGestao getItemModeloAuditoriaGestao() {
		return itemModeloAuditoriaGestao;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemFatorAvaliacao_id")
	public ItemFatorAvaliacao getItemFatorAvaliacao() {
		return itemFatorAvaliacao;
	}
	
	@MaxLength(500)
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setItemFatorAvaliacao(ItemFatorAvaliacao itemFatorAvaliacao) {
		this.itemFatorAvaliacao = itemFatorAvaliacao;
	}

	public void setItemModeloAuditoriaGestao(
			ItemModeloAuditoriaGestao itemModeloAuditoriaGestao) {
		this.itemModeloAuditoriaGestao = itemModeloAuditoriaGestao;
	}

	public void setAuditoriaGestaoIndicador(
			AuditoriaGestaoIndicador auditoriaGestaoIndicador) {
		this.auditoriaGestaoIndicador = auditoriaGestaoIndicador;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
