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
package br.com.linkcom.sgm.beans.enumeration;

public enum OrigemAcaoPreventivaEnum {
	
	AUDITORIA_INTERNA  	("Auditoria interna"),
	REUNIAO_AN_CRITICA 	("Reuni�o de an�lise cr�tica"),
	ATIVIDADE_ROTINA	("Atividades de rotina"),
	RECLAMACAO_CLIENTE	("Sugest�o e/ou reclama��o cliente interno e/ou externo"),
	OUTROS				("Outros");

	private String descricao;
	
	OrigemAcaoPreventivaEnum(String descricao){
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	public String getName(){
		return name();
	}
	
}
