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

import java.sql.Timestamp;
import java.util.Calendar;

import br.com.linkcom.neo.controller.crud.FiltroListagem;
import br.com.linkcom.neo.persistence.DefaultOrderBy;
import br.com.linkcom.sgm.util.neo.persistence.GenericDAO;
import br.com.linkcom.neo.persistence.QueryBuilder;
import br.com.linkcom.neo.persistence.SaveOrUpdateStrategy;
import br.com.linkcom.sgm.beans.EmailHistorico;
import br.com.linkcom.sgm.filtro.EmailHistoricoFiltro;

@DefaultOrderBy("data DESC")
public class EmailHistoricoDAO extends GenericDAO<EmailHistorico> {

	@Override
	public void updateListagemQuery(QueryBuilder<EmailHistorico> query, FiltroListagem _filtro) {
		EmailHistoricoFiltro filtro = (EmailHistoricoFiltro) _filtro;
		Timestamp tsInicio = null;
		Timestamp tsFim = null;
		
		if (filtro.getDtInicio() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(filtro.getDtInicio());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			tsInicio = new Timestamp(cal.getTimeInMillis());
		}
		if (filtro.getDtFim() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(filtro.getDtFim());
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			tsFim = new Timestamp(cal.getTimeInMillis());
		}		
		
		query
			.whereLikeIgnoreAll("remetente",filtro.getRemetente())
			.whereLikeIgnoreAll("assunto",filtro.getAssunto())
			.where("data >= ?", tsInicio)
			.where("data <= ?", tsFim);
	}
	
	@Override
	public void updateEntradaQuery(QueryBuilder<EmailHistorico> query) {
		query
			.joinFetch("emailHistorico.listaEmailHistoricoUsuario");
	}
	
	@Override
	public void updateSaveOrUpdate(SaveOrUpdateStrategy save) {
		save.saveOrUpdateManaged("listaEmailHistoricoUsuario");
	}
}
