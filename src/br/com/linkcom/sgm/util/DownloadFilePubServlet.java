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
package br.com.linkcom.sgm.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.linkcom.neo.controller.resource.Resource;
import br.com.linkcom.neo.core.standard.Neo;
import br.com.linkcom.sgm.beans.Arquivo;
import br.com.linkcom.sgm.dao.ArquivoDAO;


public class DownloadFilePubServlet extends br.com.linkcom.neo.view.DownloadFileServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long cdfile;
		try {
			cdfile = extractCdfile(request);
		}
		catch (Exception e) {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
		}
		
		// Obt�m o conte�do
		Resource resource = getResource(request, cdfile);
        if(resource == null){
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }

        response.setContentType(resource.getContentType());
		response.setHeader("Content-Disposition","attachment; filename=\""+ resource.getFileName() + "\";");
		//response.setHeader("Last-Modified", );
		if (resource.getSize()>=0) {
			response.setContentLength(resource.getSize());
		}

		response.getOutputStream().write(resource.getContents());
		response.flushBuffer();
	}
	
	@Override
	protected Resource getResource(HttpServletRequest request, Long cdfile) {
		Arquivo arquivo = new Arquivo();
		arquivo.setCdfile(cdfile);
		arquivo = Neo.getObject(ArquivoDAO.class).load(arquivo);
		Resource resource = new Resource(arquivo.getContenttype(), arquivo.getName(), arquivo.getContent());
		if (resource.getContents() != null) {
			resource.setSize(resource.getContents().length);
		}
		return resource;
	}
}
