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

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.MaxLength;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.beans.enumeration.TipoUsuarioAuditoriaInternaEnum;

@Entity
@SequenceGenerator(name = "sq_usuarioauditoriainterna", sequenceName = "sq_usuarioauditoriainterna")
public class UsuarioAuditoriaInterna {
	
	private Integer id;
	private AuditoriaInterna auditoriaInterna;
	private String nome;
	private TipoUsuarioAuditoriaInternaEnum tipo;
	private String funcao;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sq_usuarioauditoriainterna")
	public Integer getId() {
		return id;
	}
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY)
	public AuditoriaInterna getAuditoriaInterna() {
		return auditoriaInterna;
	}

	@Required
	@MaxLength(60)
	public String getNome() {
		return nome;
	}

	@Required
	public TipoUsuarioAuditoriaInternaEnum getTipo() {
		return tipo;
	}
	
	@Required
	@MaxLength(50)
	@DisplayName("Fun��o")
	public String getFuncao() {
		return funcao;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAuditoriaInterna(AuditoriaInterna auditoriaInterna) {
		this.auditoriaInterna = auditoriaInterna;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(TipoUsuarioAuditoriaInternaEnum tipo) {
		this.tipo = tipo;
	}
	
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
}
