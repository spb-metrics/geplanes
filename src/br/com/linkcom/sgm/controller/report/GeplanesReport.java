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
package br.com.linkcom.sgm.controller.report;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import br.com.linkcom.neo.controller.resource.ReportController;
import br.com.linkcom.neo.core.web.WebRequestContext;
import br.com.linkcom.neo.report.IReport;
import br.com.linkcom.neo.report.Report;
import br.com.linkcom.neo.util.NeoFormater;
import br.com.linkcom.neo.util.NeoImageResolver;
import br.com.linkcom.sgm.beans.ParametrosSistema;
import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.service.ParametrosSistemaService;


public abstract class GeplanesReport<FILTRO> extends ReportController<FILTRO> {

	private NeoImageResolver neoImageResolver;

	public void setNeoImageResolver(NeoImageResolver neoImageResolver) {
		this.neoImageResolver = neoImageResolver;
	}

	@Override
	public IReport createReport(WebRequestContext request, FILTRO command) throws Exception {

		Report report = (Report) createReportGeplanes(request, command);
		configurarParametrosGeplanes(request, report);
		return report;
	}
	
	protected String getReportName(IReport report) {
		String name = report.getFileName();
        if(name == null){
        	name = report.getName();
        	if (name.indexOf('/') != -1) {
        		name = name.substring(report.getName().lastIndexOf('/') + 1);
			}
        }
        if(!name.endsWith(".pdf")){
        	name+=".pdf";
        }
        name = "Geplanes_"+name.substring(0, name.length()-4)+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".pdf";
		return name;
	}

	protected void configurarParametrosGeplanes(WebRequestContext request, Report report) {
		Image image1 = null;
		Image image2 = null;
		Image image4 = null;
		try {
			image1 = neoImageResolver.getImage("/images/img_sgm_relatorio.png");
			image2 = neoImageResolver.getImage("/images/fd_rodape.gif");
			
			ParametrosSistema parametrosSistema = ParametrosSistemaService.getParametrosSistema();
			if (parametrosSistema.getImgEmpresaRelatorio() != null) {
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parametrosSistema.getImgEmpresaRelatorio().getContent());
				image4 = ImageIO.read(byteArrayInputStream);
			}
			else {
				image4 = neoImageResolver.getImage("/images/img_empresa_relatorio.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		report.addParameter("LOGO", image1);
		report.addParameter("IMG_RODAPE", image2);
		report.addParameter("LOGO_EMPRESA", image4);
		report.addParameter("NEOFORMATER", NeoFormater.getInstance());
		report.addParameter("TITULO", getTitulo());
		report.addParameter("DATA",new Date(System.currentTimeMillis()));
		report.addParameter("HORA", System.currentTimeMillis());
		report.addParameter("USUARIOLOGADO", ((Usuario)request.getUser()).getNome());
		report.addParameter("HEADER", "GEST�O DE PLANEJAMENTO ESTRAT�GICO");
	}

	public abstract IReport createReportGeplanes(WebRequestContext request, FILTRO filtro) throws Exception;

	public abstract String getTitulo();

}
