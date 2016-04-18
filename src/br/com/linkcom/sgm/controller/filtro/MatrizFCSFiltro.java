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
package br.com.linkcom.sgm.controller.filtro;

import java.util.List;

import br.com.linkcom.neo.bean.annotation.DisplayName;
import br.com.linkcom.neo.validation.annotation.Required;
import br.com.linkcom.sgm.beans.FatorAvaliacao;
import br.com.linkcom.sgm.beans.MatrizFCS;
import br.com.linkcom.sgm.beans.MatrizFCSFator;
import br.com.linkcom.sgm.beans.MatrizFCSIniciativa;
import br.com.linkcom.sgm.beans.ObjetivoMapaEstrategico;
import br.com.linkcom.sgm.beans.PerspectivaMapaEstrategico;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.util.Nomes;

@DisplayName("Matriz de Iniciativas x FCS")
public class MatrizFCSFiltro {
	private PerspectivaMapaEstrategico perspectivaMapaEstrategico;
	private ObjetivoMapaEstrategico objetivoMapaEstrategico;
	private PlanoGestao planoGestao;
	private UnidadeGerencial unidadeGerencial;
	private FatorAvaliacao fatorAvaliacao;
	private MatrizFCS matrizFCS;

	// Campos utilizados para a grava��o dos dados da matriz
	List<MatrizFCSIniciativa> listaMatrizFCSIniciativaForDelete;
	List<MatrizFCSFator> listaMatrizFCSFatorForDelete;
	
	@DisplayName(Nomes.Plano_de_Gestao)	
	@Required
	public PlanoGestao getPlanoGestao() {
		return planoGestao;
	}
	
	@DisplayName("Unidade Gerencial")
	@Required
	public UnidadeGerencial getUnidadeGerencial() {
		return unidadeGerencial;
	}
	
	@DisplayName("Sistema de pontua��o")
	@Required
	public FatorAvaliacao getFatorAvaliacao() {
		return fatorAvaliacao;
	}	
	
	@DisplayName("Perspectiva")
	@Required
	public PerspectivaMapaEstrategico getPerspectivaMapaEstrategico() {
		return perspectivaMapaEstrategico;
	}
	
	@DisplayName(Nomes.Estrategia)	
	@Required
	public ObjetivoMapaEstrategico getObjetivoMapaEstrategico() {
		return objetivoMapaEstrategico;
	}
	
	public MatrizFCS getMatrizFCS() {
		return matrizFCS;
	}	
	
	public void setPlanoGestao(PlanoGestao planoGestao) {
		this.planoGestao = planoGestao;
	}
	
	public void setUnidadeGerencial(UnidadeGerencial unidadeGerencial) {
		this.unidadeGerencial = unidadeGerencial;
	}
	
	public void setPerspectivaMapaEstrategico(PerspectivaMapaEstrategico perspectivaMapaEstrategico) {
		this.perspectivaMapaEstrategico = perspectivaMapaEstrategico;
	}
	
	public void setObjetivoMapaEstrategico(ObjetivoMapaEstrategico objetivoMapaEstrategico) {
		this.objetivoMapaEstrategico = objetivoMapaEstrategico;
	}
	
	public void setFatorAvaliacao(FatorAvaliacao fatorAvaliacao) {
		this.fatorAvaliacao = fatorAvaliacao;
	}
	
	public void setMatrizFCS(MatrizFCS matrizFCS) {
		this.matrizFCS = matrizFCS;
	}

	public List<MatrizFCSIniciativa> getListaMatrizFCSIniciativaForDelete() {
		return listaMatrizFCSIniciativaForDelete;
	}

	public List<MatrizFCSFator> getListaMatrizFCSFatorForDelete() {
		return listaMatrizFCSFatorForDelete;
	}

	public void setListaMatrizFCSIniciativaForDelete(List<MatrizFCSIniciativa> listaMatrizFCSIniciativaForDelete) {
		this.listaMatrizFCSIniciativaForDelete = listaMatrizFCSIniciativaForDelete;
	}

	public void setListaMatrizFCSFatorForDelete(List<MatrizFCSFator> listaMatrizFCSFatorForDelete) {
		this.listaMatrizFCSFatorForDelete = listaMatrizFCSFatorForDelete;
	}
}
