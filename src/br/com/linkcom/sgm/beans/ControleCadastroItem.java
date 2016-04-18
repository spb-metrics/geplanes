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

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.sgm.beans.enumeration.ItemControleCadastroEnum;

@DisplayName("Item do controle de cadastro")
public class ControleCadastroItem{
	private ItemControleCadastroEnum itemControleCadastroEnum;
	private String descricao;
	private String url;
	private Boolean pendente;
	private boolean exibirLink;
	
	public ItemControleCadastroEnum getItemControleCadastroEnum() {
		return itemControleCadastroEnum;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getUrl() {
		return url;
	}
	public Boolean getPendente() {
		return pendente;
	}
	public boolean isExibirLink() {
		return exibirLink;
	}
	public void setItemControleCadastroEnum(ItemControleCadastroEnum itemControleCadastroEnum) {
		this.itemControleCadastroEnum = itemControleCadastroEnum;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setPendente(Boolean pendente) {
		this.pendente = pendente;
	}
	public void setExibirLink(boolean exibirLink) {
		this.exibirLink = exibirLink;
	}
}
