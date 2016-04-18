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

import java.util.ArrayList;
import java.util.List;

import br.com.linkcom.sgm.util.neo.service.GenericService;
import br.com.linkcom.sgm.beans.Iniciativa;
import br.com.linkcom.sgm.beans.ObjetivoMapaEstrategico;
import br.com.linkcom.sgm.beans.PlanoAcao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.dao.IniciativaDAO;

public class IniciativaService extends GenericService<Iniciativa>{

	private IniciativaDAO iniciativaDAO;
	private PlanoAcaoService planoAcaoService;
	
	public void setIniciativaDAO(IniciativaDAO iniciativaDAO) {
		this.iniciativaDAO = iniciativaDAO;
	}
	
	public void setPlanoAcaoService(PlanoAcaoService planoAcaoService) {
		this.planoAcaoService = planoAcaoService;
	}

	/**
	 * Retorna uma lista com as iniciativas de uma determinada unidade gerencial.
	 * 
	 * @param unidadeGerencial
	 * @return
	 */
	public List<Iniciativa> findByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		return iniciativaDAO.findByUnidadeGerencial(unidadeGerencial);
	}
	
	/**
	 * Retorna uma lista com as iniciativas de uma determinada
	 * unidade gerencial e um determinado objetivo estrat�gico.
	 * 
	 * @param unidadeGerencial
	 * @param objetivoMapaEstrategico
	 * @return
	 */	
	public List<Iniciativa> findByUnidadeGerencialObjetivoEstrategico(UnidadeGerencial unidadeGerencial, ObjetivoMapaEstrategico objetivoMapaEstrategico) {
		return iniciativaDAO.findByUnidadeGerencialObjetivoEstrategico(unidadeGerencial, objetivoMapaEstrategico);
	}
	
	/**
	 * Retorna a iniciativa, bem como o plano de a��o associado a ela.
	 * 
	 * @param iniciativa
	 * @return
	 */
	public Iniciativa loadWithPlanoAcao(Iniciativa iniciativa) {
		return iniciativaDAO.loadWithPlanoAcao(iniciativa);
	}
	
	public void atualizaIniciativas(List<Iniciativa> listaIniciativa, UnidadeGerencial unidadeGerencial, ObjetivoMapaEstrategico objetivoMapaEstrategico) {
		for (Iniciativa iniciativa : listaIniciativa) {
			if (iniciativa != null && iniciativa.getId() != null) {
				this.atualizaIniciativa(iniciativa);
			} 
			else {
				iniciativa.setUnidadeGerencial(unidadeGerencial);
				iniciativa.setObjetivoMapaEstrategico(objetivoMapaEstrategico);
				this.saveOrUpdate(iniciativa);
			}
		}
	}
	
	private void atualizaIniciativa(Iniciativa iniciativa) {
		iniciativaDAO.atualizaIniciativa(iniciativa);
	}
	
	public void deleteWhereNotIn(UnidadeGerencial unidadeGerencial, ObjetivoMapaEstrategico objetivoMapaEstrategico, String listAndConcatenate) {
		iniciativaDAO.deleteWhereNotIn(unidadeGerencial, objetivoMapaEstrategico, listAndConcatenate);
	}
	
	/**
	 * Grava a iniciativa com a lista dos planos de a��o vinculados,
	 * inserindo os novos, atualizando os existentes e removendo os n�o existentes.
	 * 
	 * @param unidadeGerencial
	 * @param objetivoMapaEstrategico
	 * @param iniciativa
	 */
	public void atualizaIniciativaListaPlanoAcaoDaIniciativa(UnidadeGerencial unidadeGerencial, ObjetivoMapaEstrategico objetivoMapaEstrategico, Iniciativa iniciativa) {
		
		if (iniciativa != null) {
			iniciativa.setObjetivoMapaEstrategico(objetivoMapaEstrategico);
			iniciativa.setUnidadeGerencial(unidadeGerencial);
			this.saveOrUpdate(iniciativa);
			
			List<PlanoAcao> listaPlanoAcaoDB = planoAcaoService.findByUGIniciativa(unidadeGerencial, iniciativa);
			List<PlanoAcao> listaPlanoAcaoForDelete = new ArrayList<PlanoAcao>();
			boolean planoAcaoExcluido;
			
			
			if (listaPlanoAcaoDB != null) {
				for (PlanoAcao planoAcaoDB : listaPlanoAcaoDB) {
					planoAcaoExcluido = true;
					if (iniciativa.getListaPlanoAcao() != null) {
						for (PlanoAcao planoAcaoApp : iniciativa.getListaPlanoAcao()) {
							if (planoAcaoDB.getId().equals(planoAcaoApp.getId())) {
								planoAcaoExcluido = false;
								break;
							}
						}
					}
					
					if (planoAcaoExcluido) {
						listaPlanoAcaoForDelete.add(planoAcaoDB);
					}					
				}
			}
			
			// Insere / atualiza os registros do plano de a��o
			if (iniciativa.getListaPlanoAcao() != null) {
				for (PlanoAcao planoAcao : iniciativa.getListaPlanoAcao()) {
					planoAcao.setUnidadeGerencial(unidadeGerencial);
					planoAcao.setIniciativa(iniciativa);
					planoAcaoService.saveOrUpdate(planoAcao);
				}
			}
			
			// Remove os planos de a��o exclu�dos pelo usu�rio
			for (PlanoAcao planoAcao : listaPlanoAcaoForDelete) {
				planoAcaoService.delete(planoAcao);
			}
		}
	}
	
	/**
	 * Remove todas as iniciativas de uma determinada Unidade Gerencial.
	 * 
	 * @param unidadeGerencial
	 */
	public void deleteByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		iniciativaDAO.deleteByUnidadeGerencial(unidadeGerencial);
	}	
}