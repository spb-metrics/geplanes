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

import java.util.ArrayList;
import java.util.List;

import br.com.linkcom.sgm.util.neo.persistence.GenericDAO;
import br.com.linkcom.neo.persistence.SaveOrUpdateStrategy;
import br.com.linkcom.sgm.beans.MatrizFCS;
import br.com.linkcom.sgm.beans.ObjetivoMapaEstrategico;
import br.com.linkcom.sgm.beans.UnidadeGerencial;

public class MatrizFCSDAO extends GenericDAO<MatrizFCS> {
	
	@Override
	public void updateSaveOrUpdate(SaveOrUpdateStrategy save) {
		save.saveOrUpdateManaged("listaMatrizFcsFator");
	}
	
	public MatrizFCS loadInfoMatriz(MatrizFCS matrizFCS){
		if (matrizFCS == null || matrizFCS.getObjetivoMapaEstrategico() == null || matrizFCS.getUnidadeGerencial() == null) {
			throw new RuntimeException("Todos os campos s�o obrigat�rios");
		}
		return
			query()
				.leftOuterJoinFetch("matrizFCS.fatorAvaliacao fatorAvaliacao")
				.where("matrizFCS.unidadeGerencial=?",matrizFCS.getUnidadeGerencial())
				.where("matrizFCS.objetivoMapaEstrategico = ?", matrizFCS.getObjetivoMapaEstrategico())
				.unique();
	}
	
	public MatrizFCS carregarMatriz(MatrizFCS matrizFCS){
		return query()
				.joinFetch("matrizFCS.objetivoMapaEstrategico objetivoMapaEstrategico")
				.joinFetch("matrizFCS.listaMatrizFcsIniciativa listaMatrizFcsIniciativa")
				.joinFetch("listaMatrizFcsIniciativa.listaMatrizFcsIniciativaFator listaMatrizFcsIniciativaFator")
				.joinFetch("listaMatrizFcsIniciativaFator.itemFatorAvaliacao itemFatorAvaliacao")
				.entity(matrizFCS)
				.unique();
	}
	
	/**
	 * Retorna as matrizes para uma unidade gerencial e um objetivo estrat�gico.
	 * Se o objetivo estrat�gico for nulo retorna as matrizes para todos os objetivos da unidade gerencial.
	 * 
	 * @param unidadeGerencial
	 * @param objetivoMapaEstrategico
	 * @return
	 */
	public List<MatrizFCS> findByUnidadeGerencialObjetivoEstrategico(UnidadeGerencial unidadeGerencial, ObjetivoMapaEstrategico objetivoMapaEstrategico) {
		return 
			query()
				.joinFetch("matrizFCS.objetivoMapaEstrategico objetivoMapaEstrategico")
				.where("matrizFCS.unidadeGerencial = ?", unidadeGerencial)
				.where("objetivoMapaEstrategico = ?", objetivoMapaEstrategico)
				.orderBy("objetivoMapaEstrategico.objetivoEstrategico.descricao")
				.list();
	}
	
	/***
	 * Busca os objetivos estrat�gicos que est�o associados a uma determinada unidade gerencial
	 * atrav�s da matriz de iniciativas x FCS.
	 * 
	 * @param unidadeGerencial
	 * @return
	 */	
	public List<MatrizFCS> findWithEstrategiasByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		if (unidadeGerencial == null || unidadeGerencial.getId() == null) {
			return new ArrayList<MatrizFCS>();
		}
		return 
			query()
				.select("matrizFCS.id, " +
						"objetivoEstrategico.id, objetivoEstrategico.descricao")
				.join("matrizFCS.objetivoMapaEstrategico objetivoMapaEstrategico")
				.join("objetivoMapaEstrategico.objetivoEstrategico objetivoEstrategico")
				.where("matrizFCS.unidadeGerencial = ?", unidadeGerencial)
				.orderBy("objetivoEstrategico.descricao")
				.list();
	}
	
	/**
	 * Remove todas as matrizes de uma determinada Unidade Gerencial.
	 * 
	 * @param unidadeGerencial
	 */	
	public void deleteByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		getJdbcTemplate().update("DELETE FROM MATRIZFCS WHERE MATRIZFCS.UNIDADEGERENCIAL_ID = ?", new Object[]{unidadeGerencial.getId()});		
	}	

	/**
	 * Carrega a matriz de iniciativas x fcs de uma determinada unidade gerencial.
	 *
	 * @param unidadeGerencial
	 * @return
	 */
	public MatrizFCS loadByUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		return 
			query()
				.join("matrizFCS.unidadeGerencial unidadeGerencial")
				.where("unidadeGerencial = ?", unidadeGerencial)
				.unique();
	}	
}
