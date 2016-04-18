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

import java.util.List;

import br.com.linkcom.neo.persistence.QueryBuilder;
import br.com.linkcom.neo.util.CollectionsUtil;
import br.com.linkcom.sgm.beans.Indicador;
import br.com.linkcom.sgm.beans.ObjetivoMapaEstrategico;
import br.com.linkcom.sgm.beans.SolicitacaoCancelamentoIndicador;
import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.beans.enumeration.AprovacaoEnum;
import br.com.linkcom.sgm.controller.filtro.SolicitacaoCancelamentoIndicadorFiltro;
import br.com.linkcom.sgm.exception.GeplanesException;
import br.com.linkcom.sgm.util.neo.persistence.GenericDAO;


public class SolicitacaoCancelamentoIndicadorDAO extends GenericDAO<SolicitacaoCancelamentoIndicador> {

	/**
	 * Retorna uma lista de solicita��es de cancelamento de indicador de acordo com o filtro
	 * Somente o planoGestao do filtro � obrigat�rio.
	 *  
	 * @author Rodrigo Alvarenga
	 * @param filtro
	 * @return List<SolicitacaoCancelamentoIndicador>
	 */
	public List<SolicitacaoCancelamentoIndicador> findSolicitacoes(SolicitacaoCancelamentoIndicadorFiltro filtro) {
		if (filtro == null || filtro.getPlanoGestao() == null) {
			throw new GeplanesException("Par�metros inv�lidos na chamada do m�todo SolicitacaoCancelamentoIndicadorDAO.findSolicitacoes");
		}
		return query()
			.select("solicitacaoCancelamentoIndicador.id, solicitacaoCancelamentoIndicador.justificativaSol, solicitacaoCancelamentoIndicador.justificativaRes, solicitacaoCancelamentoIndicador.dtSolicitacao, solicitacaoCancelamentoIndicador.status, " +
					"unidadeGerencial.id, unidadeGerencial.sigla, unidadeGerencial.nome, " +
					"objetivoMapaEstrategico.id, " +
					"objetivoEstrategico.id, objetivoEstrategico.descricao, " +
					"indicador.id, indicador.nome, " +
					"usuario.id, usuario.nome")
			.join("solicitacaoCancelamentoIndicador.indicador indicador")
			.join("indicador.objetivoMapaEstrategico objetivoMapaEstrategico")
			.join("objetivoMapaEstrategico.perspectivaMapaEstrategico perspectivaMapaEstrategico")
			.join("objetivoMapaEstrategico.objetivoEstrategico objetivoEstrategico")
			.join("indicador.unidadeGerencial unidadeGerencial")
			.join("unidadeGerencial.planoGestao planoGestao")
			.join("solicitacaoCancelamentoIndicador.solicitante usuario")
			.where("planoGestao = ?", filtro.getPlanoGestao())
			.where("unidadeGerencial = ?", filtro.getUnidadeGerencial())
			.whereIn("unidadeGerencial.id", CollectionsUtil.listAndConcatenate(filtro.getListaUnidadeGerencialDisponivel(),"id",","))
			.where("perspectivaMapaEstrategico = ?", filtro.getPerspectivaMapaEstrategico())
			.where("objetivoMapaEstrategico = ?", filtro.getObjetivoMapaEstrategico())
			.where("solicitacaoCancelamentoIndicador.status = ?", filtro.getStatus())
			.orderBy("unidadeGerencial.sigla,objetivoEstrategico.descricao,indicador.nome")
			.list();
	}	

	/**
	 * @author Rodrigo Duarte
	 * @param indicador
	 * @return
	 */
	public List<SolicitacaoCancelamentoIndicador> findByIndicador(Indicador indicador) {
		return 
			query()
				.select("solicitacaoCancelamentoIndicador.id, " +
						"indicador.id")
				.leftOuterJoin("solicitacaoCancelamentoIndicador.indicador indicador")
				.where("indicador = ?", indicador)
				.list();
	}
	
	/**
	 * Update status
	 * @author Rodrigo Alvarenga
	 * @param solicitacaoCancelamento
	 */
	public void saveOrUpdateStatus(SolicitacaoCancelamentoIndicador solicitacaoCancelamento) {
		getJdbcTemplate().update("update solicitacaoCancelamentoIndicador set status="+solicitacaoCancelamento.getStatus().ordinal()+", justificativaRes = '" + solicitacaoCancelamento.getJustificativaRes() + "' where id=?", new Object[]{solicitacaoCancelamento.getId()});		
	}

	/**
	 * @author Rodrigo Alvarenga
	 * @param usuario
	 * @return
	 */
	public List<SolicitacaoCancelamentoIndicador> findByUsuario(Usuario solicitante) {
		return query()
		.leftOuterJoin("solicitacaoCancelamentoIndicador.solicitante solicitante")
		.where("solicitante=?", solicitante)
		.list();

	}
	
	/**
	 * Retorna as solicita��es de cancelamento de indicador (em aprova��o, aprovadas ou reprovadas) 
	 * para um determinado objetivo estrat�gico.
	 * 
	 * @author Rodrigo Alvarenga
	 * @param objetivoMapaEstrategico
	 * @return lista de solicita��es de cancelamento de indicador
	 */
	public List<SolicitacaoCancelamentoIndicador> find(ObjetivoMapaEstrategico objetivoMapaEstrategico, AprovacaoEnum statusAprovacao) {
		return query()
			.join("solicitacaoCancelamentoIndicador.indicador indicador")
			.join("indicador.objetivoMapaEstrategico objetivoMapaEstrategico")
			.where("solicitacaoCancelamentoIndicador.status = ?", statusAprovacao)
			.where("objetivoMapaEstrategico = ?", objetivoMapaEstrategico)
			.list();

	}
	
	/**
	 * Retorna a solicita��o de cancelamento com o indicador associado
	 * 
	 * @author Rodrigo Alvarenga
	 * @param solicitacaoCancelamentoIndicador
	 * @return solicitacaoCancelamento
	 */
	public SolicitacaoCancelamentoIndicador loadWithIndicador(SolicitacaoCancelamentoIndicador solicitacaoCancelamentoIndicador) {
		return query()
			.select("solicitacaoCancelamentoIndicador.id, " +
					"indicador.id")
			.join("solicitacaoCancelamentoIndicador.indicador indicador")
			.where("solicitacaoCancelamentoIndicador = ?", solicitacaoCancelamentoIndicador)
			.unique(); 
	}

	public boolean isAprovado(SolicitacaoCancelamentoIndicador solicitacaoCancelamento) {
		return new QueryBuilder<Long>(getHibernateTemplate())
					.select("count(*)")
					.from(SolicitacaoCancelamentoIndicador.class, "sol")
					.where("sol = ?", solicitacaoCancelamento)
					.where("sol.status = ?", AprovacaoEnum.APROVADO)
					.unique() > 0;
	}
	
	/**
	 * Retorna a solicita��o de cancelamento com os coment�rios associados
	 * 
	 * @author Rodrigo Alvarenga
	 * @param solicitacaoCancelamentoIndicador
	 * @return solicitacaoCancelamentoIndicador
	 */
	public SolicitacaoCancelamentoIndicador loadWithComentarios(SolicitacaoCancelamentoIndicador solicitacaoCancelamentoIndicador) {
		return query()
			.leftOuterJoinFetch("solicitacaoCancelamentoIndicador.comentario comentario")
			.leftOuterJoinFetch("comentario.listaComentarioItem comentarioitem")
			.leftOuterJoinFetch("comentarioitem.usuario usuario")
			.where("solicitacaoCancelamentoIndicador = ?", solicitacaoCancelamentoIndicador)
			.unique(); 
	}
	
	/**
	 * Veifica se tem alguma solicita��o de cancelamento do indicador aberta.
	 *
	 * @param indicador
	 * @return
	 * @author Rodrigo Freitas
	 */
	public Boolean existeSolicitacaoCancelamentoAberta(Indicador indicador) {
		if(indicador == null || indicador.getId() == null){
			throw new GeplanesException("O id do indicador n�o pode ser nulo.");
		}
		return new QueryBuilder<Long>(getHibernateTemplate())
				.select("count(*)")
				.from(SolicitacaoCancelamentoIndicador.class)
				.where("solicitacaoCancelamentoIndicador.status = ?", AprovacaoEnum.AG_APROVANDO)
				.where("solicitacaoCancelamentoIndicador.indicador = ?", indicador)
				.unique() > 0;
	}	
}
