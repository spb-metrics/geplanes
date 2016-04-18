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
package br.com.linkcom.sgm.service;

import br.com.linkcom.sgm.util.neo.service.GenericService;
import br.com.linkcom.sgm.beans.MapaNegocio;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.dao.MapaNegocioDAO;

public class MapaNegocioService extends GenericService<MapaNegocio> {

	private MapaNegocioDAO mapaNegocioDAO;
	
	public void setMapaNegocioDAO(MapaNegocioDAO mapaNegocioDAO) {this.mapaNegocioDAO = mapaNegocioDAO;}
	
	/**
	 * Carrega o mapa de neg�cio de uma determinada unidade gerencial.
	 *
	 * @param unidadeGerencial
	 * @return
	 */
	public MapaNegocio loadByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		return mapaNegocioDAO.loadByUnidadeGerencial(unidadeGerencial);
	}

	/**
	 * Retorna a miss�o cadastrada no Mapa do Neg�cio de uma determinada unidade gerencial.
	 *
	 * @param unidadeGerencial
	 * @return
	 */
	public String loadMissaoByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		return mapaNegocioDAO.loadMissaoByUnidadeGerencial(unidadeGerencial);
	}
}
