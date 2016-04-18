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
package br.com.linkcom.sgm.controller;

import java.util.List;

import br.com.linkcom.neo.bean.annotation.Bean;
import br.com.linkcom.neo.controller.Controller;
import br.com.linkcom.neo.controller.DefaultAction;
import br.com.linkcom.neo.controller.MultiActionController;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.neo.view.ajax.View;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.controller.filtro.UnidadeGerencialTreeFiltro;
import br.com.linkcom.sgm.service.PlanoGestaoService;
import br.com.linkcom.sgm.service.UnidadeGerencialService;


@Bean
@Controller(path="/util/UnidadeGerencialAutoComplete")
public class UnidadeGerencialAutoCompleteController extends MultiActionController {

	UnidadeGerencialService unidadeGerencialService;
	PlanoGestaoService planoGestaoService;
	
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) {
		this.unidadeGerencialService = unidadeGerencialService;
	}
	
	public void setPlanoGestaoService(PlanoGestaoService planoGestaoService) {
		this.planoGestaoService = planoGestaoService;
	}
	
	@DefaultAction
	public void view(WebRequestContext request, UnidadeGerencialTreeFiltro filtro){
		List<UnidadeGerencial> find = unidadeGerencialService.findAutocomplete(filtro.getQ(), filtro.getPlanoGestao());
		request.getServletResponse().setCharacterEncoding("UTF-8");
		View.getCurrent().eval(criarEstrutura(find));
	}

	private String criarEstrutura(List<UnidadeGerencial> find) {
		StringBuilder builder = new StringBuilder();
		builder.append("<script>");
		if(find != null && find.size() > 0){
			for (UnidadeGerencial ug : find) {
				builder.append(ug.getSigla() + "|" + ug.getId() + "\n");
			}
//			builder.delete(builder.length() - 2, builder.length());
		}
		builder.append("</script>");
		return builder.toString();
	}

}
