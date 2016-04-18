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

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import br.com.linkcom.neo.core.standard.Neo;
import br.com.linkcom.sgm.util.neo.persistence.GenericDAO;
import br.com.linkcom.neo.persistence.SaveOrUpdateStrategy;
import br.com.linkcom.sgm.beans.ParametrosSistema;


public class ParametrosSistemaDAO extends GenericDAO<ParametrosSistema> {

	private static ParametrosSistemaDAO instance;
	
	private ArquivoDAO arquivoDAO;
	
	public void setArquivoDAO(ArquivoDAO arquivoDAO) {
		this.arquivoDAO = arquivoDAO;
	}
	
	@Override
	public void updateSaveOrUpdate(final SaveOrUpdateStrategy save) {
		getTransactionTemplate().execute(new TransactionCallback(){
			public Object doInTransaction(TransactionStatus status) {
				arquivoDAO.saveFile(save.getEntity(), "imgEmpresa", false);
				arquivoDAO.saveFile(save.getEntity(), "imgEmpresaRelatorio", false);
				return null;
			}			
		});
	}
	
	public static ParametrosSistemaDAO getInstance(){
		if(instance == null){
			instance = Neo.getObject(ParametrosSistemaDAO.class);
		}
		return instance;
	}	

}
