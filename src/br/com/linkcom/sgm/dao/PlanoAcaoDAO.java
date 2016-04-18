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

import java.sql.Date;
import java.util.List;

import br.com.linkcom.sgm.util.neo.persistence.GenericDAO;
import br.com.linkcom.neo.persistence.QueryBuilder;
import br.com.linkcom.neo.util.CollectionsUtil;
import br.com.linkcom.sgm.beans.AcaoPreventiva;
import br.com.linkcom.sgm.beans.Anomalia;
import br.com.linkcom.sgm.beans.Iniciativa;
import br.com.linkcom.sgm.beans.PlanoAcao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.enumeration.StatusPlanoAcaoEnum;
import br.com.linkcom.sgm.controller.filtro.IniciativaPlanoAcaoFiltro;


public class PlanoAcaoDAO extends GenericDAO<PlanoAcao> {
	
	/**
	 * Busca todos planos de a��o vinculados a uma determinada anomalia.
	 * @author Matheus Melo Gon�alves
	 * @param anomalia
	 * @return List<PlanoAcao>
	 */
	public List<PlanoAcao> findByAnomalia(Anomalia anomalia){
		return 
			query()
			.where("planoAcao.anomalia = ?", anomalia)
			.list();
	}

	/**
	 * Busca todos planos de a��o vinculados a uma determinada a��o preventiva.
	 * @author Matheus Melo Gon�alves
	 * @param anomalia
	 * @return List<PlanoAcao>
	 */	
	public List<PlanoAcao> findByAcaoPreventiva(AcaoPreventiva acaoPreventiva) {
		return 
		query()
		.where("planoAcao.acaoPreventiva = ?", acaoPreventiva)
		.list();
	}
	
	/**
	 * Busca todos planos de a��o vinculados a uma determinada unidade gerencial
	 * e a uma determinada iniciativa.
	 * 
	 * @param unidadeGerencial
	 * @param iniciativa
	 * @return List<PlanoAcao>
	 */
	public List<PlanoAcao> findByUGIniciativa(UnidadeGerencial unidadeGerencial, Iniciativa iniciativa) {
		return 
			query()
				.where("planoAcao.unidadeGerencial = ?", unidadeGerencial)
				.where("planoAcao.iniciativa = ?", iniciativa)
				.list();
	}
	
	/**
	 * Remove todos os planos de a��o (vinculados a iniciativas) de uma determinada Unidade Gerencial.
	 * 
	 * @param unidadeGerencial
	 */	
	public void deleteByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		getJdbcTemplate().update("DELETE FROM PLANOACAO WHERE PLANOACAO.UNIDADEGERENCIAL_ID = ?", new Object[]{unidadeGerencial.getId()});		
	}
	
	/**
	 * Lista os planos de a��o das iniciativas de acordo com o filtro selecionado.
	 * 
	 * @param filtro
	 */	
	public List<PlanoAcao> findByIniciativas(IniciativaPlanoAcaoFiltro filtro) {
		QueryBuilder<PlanoAcao> queryBuilder = 
			query()
				.select("planoAcao.id, planoAcao.texto, planoAcao.textoComo, planoAcao.textoPorque, planoAcao.textoQuem, planoAcao.dtPlano, planoAcao.status, planoAcao.dtAtualizacaoStatus, " +
						"iniciativa.id, iniciativa.descricao, " +
						"objetivoEstrategico.id, objetivoEstrategico.descricao, " +
						"unidadeGerencial.id, unidadeGerencial.sigla, unidadeGerencial.nome, " +
						"planoGestao.id, planoGestao.anoExercicio")
				.join("planoAcao.unidadeGerencial unidadeGerencial")
				.join("unidadeGerencial.planoGestao planoGestao")
				.join("planoAcao.iniciativa iniciativa")
				.join("iniciativa.objetivoMapaEstrategico objetivoMapaEstrategico")
				.join("objetivoMapaEstrategico.perspectivaMapaEstrategico perspectivaMapaEstrategico")
				.join("objetivoMapaEstrategico.objetivoEstrategico objetivoEstrategico")
				.where("planoGestao = ?", filtro.getPlanoGestao())
				.where("perspectivaMapaEstrategico = ?", filtro.getPerspectivaMapaEstrategico())
				.where("objetivoMapaEstrategico = ?", filtro.getObjetivoMapaEstrategico())
				.where("iniciativa = ?", filtro.getIniciativa())
				.whereIn("unidadeGerencial.id", CollectionsUtil.listAndConcatenate(filtro.getListaUnidadeGerencial(),"id",","))
				.whereIn("unidadeGerencial.id", CollectionsUtil.listAndConcatenate(filtro.getListaUnidadeGerencialDisponivel(),"id",","))
				.orderBy("planoGestao.anoExercicio, unidadeGerencial.sigla, unidadeGerencial.nome, objetivoEstrategico.descricao, iniciativa.descricao, planoAcao.id");
		
		if (filtro.getExpirado() != null) {
			if (filtro.getExpirado()) {
				queryBuilder.where("planoAcao.dtPlano < ?", new Date(System.currentTimeMillis()));
			}
			else {
				queryBuilder.where("planoAcao.dtPlano >= ?", new Date(System.currentTimeMillis()));
			}
		}
		
		if (filtro.getListaStatusPlanoAcaoEnum() != null && !filtro.getListaStatusPlanoAcaoEnum().isEmpty()) {
			queryBuilder.openParentheses();
			int i = 0;
			for (StatusPlanoAcaoEnum statusPlanoAcaoEnum : filtro.getListaStatusPlanoAcaoEnum()) {
				queryBuilder.where("planoAcao.status = ?", statusPlanoAcaoEnum);
				if (i < filtro.getListaStatusPlanoAcaoEnum().size() - 1) {
					queryBuilder.or();
				}
				i++;
			}
			queryBuilder.closeParentheses();
		}		
		
		return queryBuilder.list();		
	}
}
