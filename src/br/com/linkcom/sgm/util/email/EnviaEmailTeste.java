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
package br.com.linkcom.sgm.util.email;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.linkcom.sgm.beans.ParametrosSistema;
import br.com.linkcom.sgm.util.EmailUtil;
import br.com.linkcom.sgm.util.GeplanesUtils;

public class EnviaEmailTeste {
	
	public EnviaEmailTeste() {
	}
	
	public String enviaEmail(HttpServletRequest request, ParametrosSistema bean) {
		String assunto = "[Geplanes] E-mail de teste";
		String texto = 
			"<table width='100%'>" +
			"	<tr>" +
			"		<td>" +
			"			<img src='cid:img0'>" +
			"		</td> " +
			"	</tr>" +
			"</table>" +
			"<table width='100%' cellspacing='10' style='border-bottom: 5px solid #CCCCCC; border-top: 5px solid #CCCCCC; background-color: #EEEEEE' >" +
			"	<tr>" +					
			"		<td>" +
			"			<span style='font-size: 12px; font-weight: normal; color: #444444'>" +			
			"			E-mail de teste.<br>" +
			"			Suas configura��es do e-mail est�o corretas!" +
			"			</span>" +
			"		</td>" +
			"	</tr>" +
			"</table>" +			
			"<table width='100%' >" +
			"	<tr align='right'>" +
			"		<td><img src='cid:img1'></td>" +
			"	</tr>" +
			"</table>";
		
		List<String> listaImgs = EmailUtil.getEmailImageList(GeplanesUtils.getApplicationPath(request));

		try {
			boolean ok = EnvioEmail.getInstance().enviaEmail(bean.getEmailServidorSMTP(), bean.getEmailPortaSMTP(), bean.getEmailServidorUsaSSL(), bean.getEmailNeedAuth(), bean.getEmailUsuarioDominio(), bean.getEmailSenha(), bean.getEmailRemetente(), bean.getEmailTeste(), assunto, texto, listaImgs);
			return ok ? "E-MAIL ENVIADO COM SUCESSO PARA " + bean.getEmailTeste() + "." : "ERRO AO ENVIAR E-MAIL.";
		}
		catch(Exception ex) {
			return "ERRO AO ENVIAR E-MAIL DE TESTE: " + ex.getMessage();
		}
	}

}