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
package br.com.linkcom.sgm.quartzjobs;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.util.EmailUtil;
import br.com.linkcom.sgm.util.email.EnvioEmail;


public abstract class EnviaEmailLembreteJOB implements Job {
	private String applicationPath;
	private String assunto;
	private String texto;
	
	public EnviaEmailLembreteJOB() {
	}
	
	public abstract void enviaEmail();	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		carregaApplicationPath(arg0.getJobDetail().getJobDataMap());		
		enviaEmail();
	}
	
	public String getApplicationPath() {
		return applicationPath;
	}
	
	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	protected void carregaApplicationPath(JobDataMap map){
		setApplicationPath((String) map.get("applicationPath"));
	}
	
	protected Calendar getDataInicioLimite(Calendar dataLimite, int diasLembrete) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataLimite.getTime()); 
		cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH) - diasLembrete);
		return cal;
	}
	protected Calendar getDataInicioLimite(Date dataLimite, int diasLembrete) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataLimite); 
		cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH) - diasLembrete);
		return cal;
	}
	
	protected boolean envia(String emailFrom, List<Usuario> listaUsuarios, String descricao, Date dataLimite) {
		boolean enviado        = false;
		boolean enviadoUsuario = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String texto = getTexto();
		
		List<String> listaImgs = EmailUtil.getEmailImageList(getApplicationPath());
		
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getEmail() != null && !usuario.getEmail().trim().equals("")) {			
				texto = texto
							.replace("--U--",usuario.getNome())
							.replace("--P--",descricao)
							.replace("--D--",dateFormat.format(dataLimite));
				
				enviadoUsuario = EnvioEmail.getInstance().enviaEmail(emailFrom, usuario.getEmail(),getAssunto(),texto,listaImgs);
			}
			if (!enviado)
				enviado = enviadoUsuario;
		}
		return enviado;
	}	
}
