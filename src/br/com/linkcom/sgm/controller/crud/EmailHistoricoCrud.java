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
package br.com.linkcom.sgm.controller.crud;

import java.util.List;

import br.com.linkcom.neo.authorization.crud.CrudAuthorizationModule;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.crud.CrudController;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.neo.persistence.ListagemResult;
import br.com.linkcom.sgm.beans.EmailHistorico;
import br.com.linkcom.sgm.filtro.EmailHistoricoFiltro;
import br.com.linkcom.sgm.service.EmailHistoricoUsuarioService;


@Controller(path="/sgm/crud/EmailHistorico", authorizationModule=CrudAuthorizationModule.class)
public class EmailHistoricoCrud extends CrudController<EmailHistoricoFiltro, EmailHistorico, EmailHistorico> {
	
	private EmailHistoricoUsuarioService emailHistoricoUsuarioService;
	
	public void setEmailHistoricoUsuarioService(EmailHistoricoUsuarioService emailHistoricoUsuarioService) {
		this.emailHistoricoUsuarioService = emailHistoricoUsuarioService;
	}
	
	@Override
	protected ListagemResult<EmailHistorico> getLista(WebRequestContext request, EmailHistoricoFiltro filtro) {
		ListagemResult<EmailHistorico> listagemResult = super.getLista(request, filtro);
		
		List<EmailHistorico> lista = listagemResult.list();
		
		if (lista != null) {
			for (EmailHistorico emailHistorico : lista) {
				emailHistorico.setListaEmailHistoricoUsuario(emailHistoricoUsuarioService.findByEmailHistorico(emailHistorico));
			}
		}
		
		return listagemResult;
	}
}
