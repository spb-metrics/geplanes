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
package br.com.linkcom.sgm.report.bean;

public class AuditoriaGestaoGraficoBean {
	protected String nomeIndicador;
	protected String nomeItemAuditoria;
	protected String descItemAuditoria;
	
	public AuditoriaGestaoGraficoBean() {
	}
	
	public AuditoriaGestaoGraficoBean(String nomeIndicador, String nomeItemAuditoria, String descItemAuditoria) {
		this.nomeIndicador = nomeIndicador;
		this.nomeItemAuditoria = nomeItemAuditoria;
		this.descItemAuditoria = descItemAuditoria;
	}
	
	public String getNomeIndicador() {
		return nomeIndicador;
	}
	
	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}
	
	public String getNomeItemAuditoria() {
		return nomeItemAuditoria;
	}
	
	public void setNomeItemAuditoria(String nomeItemAuditoria) {
		this.nomeItemAuditoria = nomeItemAuditoria;
	}
	
	public String getDescItemAuditoria() {
		return descItemAuditoria;
	}
	
	public void setDescItemAuditoria(String descItemAuditoria) {
		this.descItemAuditoria = descItemAuditoria;
	}
	

}
