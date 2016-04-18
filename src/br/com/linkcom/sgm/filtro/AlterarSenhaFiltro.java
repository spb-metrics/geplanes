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
package br.com.linkcom.sgm.filtro;

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.beans.Usuario;


public class AlterarSenhaFiltro {
	String senha,novaSenha,repetirNovaSenha;
	Usuario listaUsuarios;
	
	@Required
	@MaxLength(20)
	@DisplayName("Sua senha")
	public String getSenha() {
		return senha;
	}
	@Required
	@MaxLength(20)
	@DisplayName("Nova senha")
	public String getNovaSenha() {
		return novaSenha;
	}
	@Required
	@MaxLength(20)
	@DisplayName("Repetir nova senha")
	public String getRepetirNovaSenha() {
		return repetirNovaSenha;
	}
	@Required
	@DisplayName("Usu�rio")
	public Usuario getListaUsuarios() {
		return listaUsuarios;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public void setRepetirNovaSenha(String repetirNovaSenha) {
		this.repetirNovaSenha = repetirNovaSenha;
	}
	public void setListaUsuarios(Usuario listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

}
