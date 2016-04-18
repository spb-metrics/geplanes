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

import java.awt.Image;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.linkcom.neo.controller.crud.FiltroListagem;
import br.com.linkcom.neo.controller.resource.Resource;
import br.com.linkcom.neo.core.standard.Neo;
import br.com.linkcom.neo.persistence.ListagemResult;
import br.com.linkcom.neo.report.Report;
import br.com.linkcom.sgm.util.neo.service.GenericService;
import br.com.linkcom.neo.types.ListSet;
import br.com.linkcom.neo.util.NeoFormater;
import br.com.linkcom.neo.util.NeoImageResolver;
import br.com.linkcom.sgm.beans.AcaoPreventiva;
import br.com.linkcom.sgm.beans.PlanoAcao;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.beans.enumeration.StatusAcaoPreventivaEnum;
import br.com.linkcom.sgm.dao.AcaoPreventivaDAO;
import br.com.linkcom.sgm.filtro.AcaoPreventivaFiltro;
import br.com.linkcom.sgm.report.MergeReport;

public class AcaoPreventivaService extends GenericService<AcaoPreventiva> {
	
	private UnidadeGerencialService unidadeGerencialService;
	private AcaoPreventivaDAO acaoPreventivaDAO;
	private UsuarioService usuarioService;
	private AcompanhamentoIndicadorService acompanhamentoIndicadorService;
	private NeoImageResolver neoImageResolver;
	private PlanoAcaoService planoAcaoService;
	
	public void setPlanoAcaoService(PlanoAcaoService planoAcaoService){this.planoAcaoService = planoAcaoService;}
	public void setNeoImageResolver(NeoImageResolver neoImageResolver) {this.neoImageResolver = neoImageResolver;}
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) {this.unidadeGerencialService = unidadeGerencialService;}
	public void setAcaoPreventivaDAO(AcaoPreventivaDAO acaoPreventivaDAO) {this.acaoPreventivaDAO = acaoPreventivaDAO;}	
	public void setUsuarioService(UsuarioService usuarioService) {this.usuarioService = usuarioService;}	
	public void setAcompanhamentoIndicadorService(AcompanhamentoIndicadorService acompanhamentoIndicadorService) {this.acompanhamentoIndicadorService = acompanhamentoIndicadorService;}
	
	@Override
	public ListagemResult<AcaoPreventiva> findForListagem(FiltroListagem filtro) {
		AcaoPreventivaFiltro acaoPreventivaFiltro = (AcaoPreventivaFiltro) filtro;
		
		List<UnidadeGerencial> listaUGRegistro = new ArrayList<UnidadeGerencial>();		
		List<UnidadeGerencial> listaUGDisponivel = new ArrayList<UnidadeGerencial>();
		List<UnidadeGerencial> listaUGUsuario = usuarioService.getUsuarioLogadoUGs(acaoPreventivaFiltro.getPlanoGestao());
		Boolean usuarioLogadoIsAdmin  = usuarioService.isUsuarioLogadoAdmin();
		Boolean usuarioLogadoIsRepQualidade = usuarioService.isUsuarioLogadoVinculadoAreaQualidade(acaoPreventivaFiltro.getPlanoGestao());
		
		// UG de Registro
		if (acaoPreventivaFiltro.getUgRegistro() != null) {
			listaUGRegistro.add(acaoPreventivaFiltro.getUgRegistro());
			
			if (acaoPreventivaFiltro.isIncluirSubordinadasReg()) {
				// Busca todas as UGs subordinadas da UG selecionada
				listaUGRegistro = unidadeGerencialService.getListaDescendencia(acaoPreventivaFiltro.getUgRegistro(), listaUGRegistro);
			}
		}
		acaoPreventivaFiltro.setListaUnidadeGerencialReg(listaUGRegistro);
		
		// UGs dispon�veis para a listagem
		if (!usuarioLogadoIsAdmin && !usuarioLogadoIsRepQualidade) {
			for (UnidadeGerencial unidadeGerencial : listaUGUsuario) {
				listaUGDisponivel.add(unidadeGerencial);
				listaUGDisponivel = unidadeGerencialService.getListaDescendencia(unidadeGerencial, listaUGDisponivel);
			}			
		}
		acaoPreventivaFiltro.setListaUnidadeGerencialDisponivel(listaUGDisponivel);
		
		return super.findForListagem(acaoPreventivaFiltro);
	}
	
	@Override
	public void saveOrUpdate(AcaoPreventiva bean) {
		if (bean.getPlanosAcao() != null && !bean.getPlanosAcao().isEmpty()) {
			PlanoAcao planoAcaoDB;
			Date dataAtual = new Date(System.currentTimeMillis());
			for (PlanoAcao planoAcao : bean.getPlanosAcao()) {		
				// Verifica se houve mudan�a no status.
				// Caso afirmativo, grava a data atual.				
				if (planoAcao.getId() != null) {
					planoAcaoDB = planoAcaoService.load(planoAcao);
					if (!planoAcaoDB.getStatus().equals(planoAcao.getStatus())) {
						planoAcao.setDtAtualizacaoStatus(dataAtual);
					}
				}
			}
		}
		super.saveOrUpdate(bean);
	}	
	
	public UnidadeGerencial verificaUgOrigem(String idStringUgOrigem, AcaoPreventiva acaoPreventiva){
		UnidadeGerencial ugOrigem = new UnidadeGerencial();
		try {
			Integer idUgOrigem = Integer.parseInt(idStringUgOrigem);
			ugOrigem.setId(idUgOrigem);
			ugOrigem = unidadeGerencialService.loadWithPlanoGestao(ugOrigem);
		} catch (Exception e) {}
		return ugOrigem;
	}

	/**
	 * M�todo respons�vel por criar o relat�rio de a��es preventivas.
	 * @author Rodrigo Alvarenga
	 * @param AcaoPreventiva
	 * @return 
	 */
	public Resource gerarRelatorioAcaoPreventiva(AcaoPreventiva bean){
		bean = this.loadForEntrada(bean);
		bean.setPlanosAcao(new ListSet<PlanoAcao>(PlanoAcao.class,planoAcaoService.findByAcaoPreventiva(bean)));
		
		//Listas
		List<AcaoPreventiva> listaAcaoPreventiva = new ArrayList<AcaoPreventiva>();
		List<Report> listaReport = new ArrayList<Report>();
		listaAcaoPreventiva.add(bean);
		
		//Objetos
		Report report = new Report("../relatorio/acaoPreventivaMae");
		MergeReport mergeReport = new MergeReport("A��o Preventiva");
		Image image1 = null;
		Image image2 = null;
		Image image4 = null;
		
		//Recuperando as imagens do relat�rio.
		try {
			image1 = neoImageResolver.getImage("/images/img_sgm_relatorio.png");
			image2 = neoImageResolver.getImage("/images/fd_rodape.gif");
			image4 = neoImageResolver.getImage("/images/img_empresa_relatorio.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Primeiro Sub-relat�rio
		report.addSubReport("primeiroSub", new Report("../relatorio/acaoPreventiva"));
		report.addParameter("numero", bean.getSequencial());
		if (bean.getOrigem() != null){
			report.addParameter("origem", bean.getOrigem().toString());
		}		
		report.addParameter("dataAcaoPreventiva", bean.getDataAbertura());
		report.addParameter("origemAcaoPreventiva", bean.getUgRegistro().getDescricao());
		report.addParameter("descAcaoPreventiva", bean.getDescricao() == null ? "" : bean.getDescricao());
		report.addParameter("obsComplementares", bean.getObservacoes() == null ? "" : bean.getObservacoes());
		if(bean.getTipo() != null){
			report.addParameter("tipo", bean.getTipo().toString());
		}	
		report.addParameter("LOGO", image1);
		report.addParameter("IMG_RODAPE", image2);
		report.addParameter("LOGO_EMPRESA", image4);
		report.addParameter("NEOFORMATER", NeoFormater.getInstance());
		report.addParameter("TITULO", "RELAT�RIO DE A��O PREVENTIVA");
		report.addParameter("DATA",new Date(System.currentTimeMillis()));
		report.addParameter("HORA", System.currentTimeMillis());
		report.addParameter("USUARIOLOGADO", ((Usuario)Neo.getUser()).getNome());
		report.addParameter("HEADER", "SISTEMA DE GEST�O INTEGRADA");
		report.setDataSource(listaAcaoPreventiva);
		
		//Segundo sub.
		report.addSubReport("segundoSub", new Report("../relatorio/acaoPreventivaSub"));
		report.addParameter("avalEficaciaAcao", bean.getAvalEficaciaAcao() == null ? "" : bean.getAvalEficaciaAcao().toString());
		report.addParameter("evidenciaEficaciaAcao", bean.getEvidenciaEficaciaAcao() == null ? "" : bean.getEvidenciaEficaciaAcao());
		report.addParameter("conclusao", bean.getConclusao() == null ? "" : bean.getConclusao());
		
		//Adicionando os dos dois relat�rios a uma lista que ser� passada a mergeReport.
		listaReport.add(report);
		mergeReport.setReportlist(listaReport);
		
		try {
			return mergeReport.generateResource();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public void delete(AcaoPreventiva bean) {
		acompanhamentoIndicadorService.setNullAcaoPreventiva(bean);
		super.delete(bean);
	}
	
	public Integer getProximoSequencial(UnidadeGerencial unidadeGerencial) {
		return acaoPreventivaDAO.getProximoSequencial(unidadeGerencial);
	}
	
	public StatusAcaoPreventivaEnum getStatusAcaoPreventiva(AcaoPreventiva acaoPreventiva) {
		if (acaoPreventiva != null) {
			
			if (acaoPreventiva.getStatus() != null) {
				
				if (acaoPreventiva.getStatus().equals(StatusAcaoPreventivaEnum.ABERTA)) {
					if (acaoPreventiva.getDataEncerramento() != null) {
						return StatusAcaoPreventivaEnum.ENCERRADA;
					}
					return StatusAcaoPreventivaEnum.ABERTA;
				}
				
				if (acaoPreventiva.getStatus().equals(StatusAcaoPreventivaEnum.ENCERRADA)) {
					if (acaoPreventiva.getDataEncerramento() != null) {
						return StatusAcaoPreventivaEnum.ENCERRADA;
					}
					return StatusAcaoPreventivaEnum.ABERTA;
				}

			}
			return StatusAcaoPreventivaEnum.ABERTA;
		}
		return null;
	}
	
	public Boolean usuarioPodeEncerrarAcaoPreventiva(PlanoGestao planoGestao) {
		return usuarioService.isUsuarioLogadoVinculadoAreaQualidade(planoGestao);		
	}
	
	/**
	 * Percorre a hierarquia de UGs e retorna a unidade gerencial que possui 
	 * o mesmo id da ug passada como par�metro.
	 *  
	 * @param ugRaiz
	 * @param ug
	 * @return
	 */
	public UnidadeGerencial obtemUGHierarquia(UnidadeGerencial ugRaiz, UnidadeGerencial ug) {
		if (ugRaiz != null && ug != null) {
			if (ugRaiz.getId().equals(ug.getId())) {
				return ugRaiz;
			}
			if (ugRaiz.getFilhos() != null) {
				for (UnidadeGerencial ugFilha : ugRaiz.getFilhos()) {
					UnidadeGerencial ugNeta = obtemUGHierarquia(ugFilha, ug);
					if (ugNeta != null) {
						return ugNeta;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Remove todas as a��es preventivas de uma determinada Unidade Gerencial.
	 * 
	 * @param unidadeGerencial
	 */
	public void deletebyUnidadeGerencial(UnidadeGerencial bean) {
		acaoPreventivaDAO.deleteByUnidadeGerencial(bean); 
	}	
}
