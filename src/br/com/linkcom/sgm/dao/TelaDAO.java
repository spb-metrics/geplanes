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
package br.com.linkcom.sgm.dao;

import java.util.HashMap;
import java.util.List;

import br.com.linkcom.sgm.util.neo.persistence.GenericDAO;
import br.com.linkcom.sgm.beans.Tela;


public class TelaDAO extends GenericDAO<Tela> {

	protected HashMap<String, String> mapTelas;
	
	/**
	 * Captura a descri��o cadastrada no banco a partir de uma url
	 * A url deve estar no formado /modulo/tela ( a mesma cadastrada no @controller na propriedade path)
	 * @param url
	 * @return Descri��o da tela cadastrada no banco
	 * @author Pedro Gon�alves
	 */
	public String getTelaDescriptionByUrl(String url) {
		if(mapTelas == null){
			List<Tela> telas = findAll();
			HashMap<String, String> mapa = new HashMap<String, String>();
			for (Tela tela : telas) {
				mapa.put(tela.getPath(), tela.getNome());
			}
			mapTelas = mapa;
		}
		return mapTelas.get(url);
	}	
	
	/**
	 * For�a uma nova busca no banco de dados das descri��es das telas.
	 */
	public void refreshTelaDescriptions() {
		mapTelas = null;
	}
}
