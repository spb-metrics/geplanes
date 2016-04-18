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
package br.com.linkcom.sgm.beans.enumeration;


public enum ItemControleCadastroEnum {
	
	MAPA_NEGOCIO			(0, "Cadastro do mapa do neg�cio", "/sgm/process/DefinicaoNegocio"),
	MAPA_ESTRATEGICO		(1, "Cadastro do mapa estrat�gico", "/sgm/process/DefinicaoEstrategia"),
	MAPA_COMPETENCIA		(2, "Cadastro do mapa de compet�ncias", "/sgm/process/DefinicaoCompetencia"),
	MATRIZ_FCS				(3, "Cadastro da matriz de iniciativas x fcs", "/sgm/process/MatrizFCS"),
	INDICADORES				(4, "Cadastro de indicadores", "/sgm/process/DistribuicaoPesosIndicadores"),
	VALORES_BASE			(5, "Cadastro de metas", "/sgm/process/DistribuicaoPesosIndicadores"),
	VALORES_REAIS			(6, "Lan�amento de resultados", "/sgm/process/LancamentoValorReal"),
	TRATAMENTO_ANOMALIA		(7, "Tratamento da anomalia", "/sgm/crud/Anomalia"),
	PLANO_ACAO_INICIATIVA	(8, "Plano de a��o da iniciativa", "/sgm/process/IniciativaPlanoAcao");
	
	private Integer codigo;
	private String descricao;
	private String path;
	
	ItemControleCadastroEnum(Integer codigo, String descricao, String path) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.path = path;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	public String getName(){
		return name();
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}