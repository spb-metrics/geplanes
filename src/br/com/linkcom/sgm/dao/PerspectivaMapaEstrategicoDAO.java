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

import br.com.linkcom.sgm.util.neo.persistence.GenericDAO;
import br.com.linkcom.sgm.beans.MapaEstrategico;
import br.com.linkcom.sgm.beans.PerspectivaMapaEstrategico;
import br.com.linkcom.sgm.beans.UnidadeGerencial;

public class PerspectivaMapaEstrategicoDAO extends GenericDAO<PerspectivaMapaEstrategico> {
	
	/**
	 * M�todo retorna a lista de todas as perspectivas (com seus objetivos estrat�gicos) relacionadas a um determinado MapaEstrat�gico.
	 * @author Matheus Melo Gon�alves
	 * @param mapaEstrategico
	 * @return List<Perspectiva>
	 */
	public List<PerspectivaMapaEstrategico> findByMapaEstrategico(MapaEstrategico mapaEstrategico){
		return 
			query()
				.select("perspectivaMapaEstrategico.id, perspectivaMapaEstrategico.ordem, " +
						"perspectiva.id, perspectiva.descricao," +
						"objetivoMapaEstrategico.id, " +
						"objetivoEstrategico.id, objetivoEstrategico.descricao")
				.join("perspectivaMapaEstrategico.perspectiva perspectiva")
				.join("perspectivaMapaEstrategico.mapaEstrategico mapaEstrategico")
				.leftOuterJoin("perspectivaMapaEstrategico.listaObjetivoMapaEstrategico objetivoMapaEstrategico")
				.leftOuterJoin("objetivoMapaEstrategico.objetivoEstrategico objetivoEstrategico")
				.where("mapaEstrategico = ?", mapaEstrategico)
				.orderBy("perspectivaMapaEstrategico.ordem, objetivoMapaEstrategico.id")
				.list();
	}
	
	/**
	 * Retorna as perspectivas cadastradas para uma determinada UG, 
	 * baseado no Mapa Estrategico.
	 * 
	 * @param unidadeGerencial
	 * @param orderBy
	 * @return
	 */
	public List<PerspectivaMapaEstrategico> findByUnidadeGerencialThroughMapaEstrategico(UnidadeGerencial unidadeGerencial, String orderBy) {
		return query()
			.select("perspectivaMapaEstrategico.id, " +
					"perspectiva.id, perspectiva.descricao, " +
					"mapaEstrategico.id, " +
					"unidadeGerencial.id")
			.join("perspectivaMapaEstrategico.perspectiva perspectiva")
			.join("perspectivaMapaEstrategico.mapaEstrategico mapaEstrategico")
			.join("mapaEstrategico.unidadeGerencial unidadeGerencial")
			.where("unidadeGerencial = ?", unidadeGerencial)
			.orderBy(orderBy != null ? orderBy : "perspectivaMapaEstrategico.perspectiva.descricao")
			.list();
	}
	
	/**
	 * Retorna as perspectivas cadastradas para uma determinada UG, 
	 * baseado na Matriz de Inciativas x FCS.
	 * 
	 * @param unidadeGerencial
	 * @return
	 */
	public List<PerspectivaMapaEstrategico> findByUnidadeGerencialThroughMatrizFCS(UnidadeGerencial unidadeGerencial) {
		return query()
			.join("perspectivaMapaEstrategico.listaObjetivoMapaEstrategico objetivoMapaEstrategico")
			.join("objetivoMapaEstrategico.listaMatrizFCS matrizFCS")
			.join("matrizFCS.unidadeGerencial unidadeGerencial")
			.where("unidadeGerencial = ?", unidadeGerencial)
			.orderBy("perspectivaMapaEstrategico.perspectiva.descricao")
			.list();
	}
	
	/**
	 * Carrega a perspectiva do mapa estrat�gico com a unidade gerencial � qual est� vinculada.
	 * 
	 * @param perspectivaMapaEstrategico
	 * @return
	 */
	public PerspectivaMapaEstrategico loadWithUnidadeGerencial(PerspectivaMapaEstrategico perspectivaMapaEstrategico) {
		return query()
			.select("perspectivaMapaEstrategico.id, " +
					"perspectiva.id, perspectiva.descricao, " +
					"mapaEstrategico.id, " +
					"unidadeGerencial.id")
			.from(PerspectivaMapaEstrategico.class)
			.join("perspectivaMapaEstrategico.perspectiva perspectiva")
			.join("perspectivaMapaEstrategico.mapaEstrategico mapaEstrategico")
			.join("mapaEstrategico.unidadeGerencial unidadeGerencial")
			.entity(perspectivaMapaEstrategico)
			.unique();
	}
}
