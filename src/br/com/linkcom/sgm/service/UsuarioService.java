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
import java.util.Set;

import br.com.linkcom.neo.controller.crud.FiltroListagem;
import br.com.linkcom.neo.core.standard.Neo;
import br.com.linkcom.neo.persistence.ListagemResult;
import br.com.linkcom.neo.util.Util;
import br.com.linkcom.sgm.beans.Indicador;
import br.com.linkcom.sgm.beans.Papel;
import br.com.linkcom.sgm.beans.ParametrosSistema;
import br.com.linkcom.sgm.beans.PlanoGestao;
import br.com.linkcom.sgm.beans.UnidadeGerencial;
import br.com.linkcom.sgm.beans.Usuario;
import br.com.linkcom.sgm.beans.UsuarioPapel;
import br.com.linkcom.sgm.beans.UsuarioUnidadeGerencial;
import br.com.linkcom.sgm.beans.enumeration.FuncaoUGEnum;
import br.com.linkcom.sgm.dao.UsuarioDAO;
import br.com.linkcom.sgm.filtro.UsuarioFiltro;
import br.com.linkcom.sgm.util.Nomes;
import br.com.linkcom.sgm.util.email.EnvioEmail;
import br.com.linkcom.sgm.util.neo.service.GenericService;

public class UsuarioService extends GenericService<Usuario> {
	
	private UsuarioUnidadeGerencialService usuarioUnidadeGerencialService;
	private PapelService papelService;
	private UsuarioPapelService usuarioPapelService;
	private UsuarioDAO usuarioDAO;
	private UnidadeGerencialService unidadeGerencialService;
	private IndicadorService indicadorService;
	
	public void setIndicadorService(IndicadorService indicadorService) {this.indicadorService = indicadorService;}
	public void setUsuarioUnidadeGerencialService(UsuarioUnidadeGerencialService usuarioUnidadeGerencialService) {this.usuarioUnidadeGerencialService = usuarioUnidadeGerencialService;}
	public void setPapelService(PapelService papelService) {this.papelService = papelService;}
	public void setUsuarioPapelService(UsuarioPapelService usuarioPapelService) {this.usuarioPapelService = usuarioPapelService;}
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {this.usuarioDAO = usuarioDAO;}
	public void setUnidadeGerencialService(UnidadeGerencialService unidadeGerencialService) {this.unidadeGerencialService = unidadeGerencialService;}
	
	public List<Usuario> findByUg(UnidadeGerencial unidadeGerencial){
		
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		List<UsuarioUnidadeGerencial> lista = usuarioUnidadeGerencialService.findByUnidadeGerencial(unidadeGerencial);
		
		for (UsuarioUnidadeGerencial usuarioUnidadeGerencial : lista) {
			listaUsuario.add(usuarioUnidadeGerencial.getUsuario());
		}
		
		return listaUsuario;
	}
	
	@Override
	public void saveOrUpdate(Usuario bean) {
		if (bean.getId() != null || bean.getSenha().equals("")) {
			Usuario usuario = obtemSenha(bean);
			bean.setSenha(usuario.getSenha());
		}
		
		//o perfil escolhido ser� setado na cole��o
		Set<Papel> papeis = bean.getPapeis();
		for (Papel papel : papeis) {
			UsuarioPapel usuarioPapel = new UsuarioPapel();
			usuarioPapel.setPapel(papel);
			bean.getUsuariosPapel().add(usuarioPapel);
		}
		
		super.saveOrUpdate(bean);
	}
	
	public Boolean verificaExisteLogin(String login) {
		return ((UsuarioDAO)getGenericDAO()).verificaExisteLogin(login); 
	}

	/**
	 * Verifica se a fun��o do usu�rio logado � administrador do sistema.
	 * @author Rodrigo Duarte
	 * @return
	 */
	public boolean isUsuarioLogadoAdmin() {
		
		Usuario usuario = (Usuario) Neo.getRequestContext().getUser();
		
		//conferir se o metodo recupera todos os papeis do usuario
		List<Papel> listaPapel = papelService.findByUsuario(usuario);
		
		for (Papel papel : listaPapel) {
			if(Boolean.TRUE.equals(papel.getAdministrador()))
				return true;
		}		
		return false;
	}
	
	public Usuario obtemSenha(Usuario usuario) {
		return ((UsuarioDAO)getGenericDAO()).obtemSenha(usuario); 
	}
	
	public Usuario obtemLogin(Usuario usuario) {
		return ((UsuarioDAO)getGenericDAO()).obtemLogin(usuario); 
	}
	
	@Override
	public ListagemResult<Usuario> findForListagem(FiltroListagem filtro) {
		UsuarioFiltro usuarioFiltro = (UsuarioFiltro) filtro;
		
		List<UnidadeGerencial> listaUnidadeGerencial = new ArrayList<UnidadeGerencial>();		
		
		if (usuarioFiltro.getUnidadeGerencial() != null) {
			listaUnidadeGerencial.add(usuarioFiltro.getUnidadeGerencial());
			
			if (usuarioFiltro.isIncluirSubordinadas()) {
				// Busca todas as UGs subordinadas da UG selecionada
				listaUnidadeGerencial = unidadeGerencialService.getListaDescendencia(usuarioFiltro.getUnidadeGerencial(), listaUnidadeGerencial);
			}
		}	
		usuarioFiltro.setListaUnidadeGerencial(listaUnidadeGerencial);		
		
		ListagemResult<Usuario> listagemResult =  super.findForListagem(filtro);
		for (Usuario usuario : listagemResult.list()) {
			usuario.setPapeis(usuarioPapelService.getPapeis(usuario));
		}
		
		return listagemResult;
	}
	
	/**
	 * Obtem os papeis do usu�rio no sistema
	 */
	public Usuario obtemPapeis (Usuario usuario) {
		return ((UsuarioDAO)getGenericDAO()).obtemPapeis(usuario); 
	}
	
	/**
	 * Verifica se o usu�rio possui algum papel
	 */
	public boolean possuiPapel (Usuario usuario, Integer idPapel) {
		Usuario usuarioPapeis = obtemPapeis(usuario);
		for (UsuarioPapel usuarioPapel : usuarioPapeis.getUsuariosPapel() ) {
			if (usuarioPapel.getPapel().getId().equals(idPapel)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifica se o usuario � participante da unidade gerencial, independente da fun��o.
	 * @author Rodrigo Alvarenga
	 * @param unidadeGerencial
	 * @return
	 */
	public boolean isUsuarioLogadoParticipanteUG(UnidadeGerencial unidadeGerencial) {
		if (unidadeGerencial == null) {
			return false;
		}		
		return isUsuarioLogadoParticipanteUG(unidadeGerencial, null);
	}	
	
	/**
	 * Verifica se o usu�rio possui a fun��o de respons�vel na unidade gerencial.
	 * @author Rodrigo Alvarenga
	 * @param unidadeGerencial
	 * @return
	 */
	public boolean isUsuarioLogadoResponsavelUG(UnidadeGerencial unidadeGerencial) {
		if (unidadeGerencial == null) {
			return false;
		}		
		return isUsuarioLogadoParticipanteUG(unidadeGerencial, FuncaoUGEnum.RESPONSAVEL);
	}	
	
	/**
	 * Verifica se o usu�rio possui a fun��o de apoio na unidade gerencial.
	 * @author Rodrigo Alvarenga
	 * @param unidadeGerencial
	 * @return
	 */
	public boolean isUsuarioLogadoApoioUG(UnidadeGerencial unidadeGerencial) {
		if (unidadeGerencial == null) {
			return false;
		}		
		return isUsuarioLogadoParticipanteUG(unidadeGerencial, FuncaoUGEnum.APOIO);
	}	
	
	/**
	 * Verifica se o usuario � participante da unidade gerencial.
	 * Se o par�metro fun��o estiver nulo, verifica somente se existe o v�nculo do usu�rio com a UG, independente da fun��o.
	 * Sen�o, verifica se o usu�rio est� vinculado � UG na fun��o especificada (respons�vel ou apoio).
	 * @author Rodrigo Alvarenga
	 * @param unidadeGerencial
	 * @param funcao
	 * @return
	 */
	private boolean isUsuarioLogadoParticipanteUG(UnidadeGerencial unidadeGerencial, FuncaoUGEnum funcao) {
		if (unidadeGerencial == null) {
			return false;
		}		
		Usuario usuario = (Usuario) Neo.getRequestContext().getUser();
		List<UsuarioUnidadeGerencial> usuariosUnidadeGerencial = usuario.getUsuariosUnidadeGerencial();
		if (usuariosUnidadeGerencial != null) {
			for (UsuarioUnidadeGerencial usuarioUnidadeGerencial : usuariosUnidadeGerencial) {
				if (unidadeGerencial.equals(usuarioUnidadeGerencial.getUnidadeGerencial())) {
					if (funcao != null) {
						if (usuarioUnidadeGerencial.getFuncao().equals(funcao)) {
							return true;
						}
					}
					else {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Verifica se o usu�rio est� vinculado a alguma unidade gerencial ancestral da ug passada como par�metro
	 * @author Rodrigo Alvarenga
	 * @param unidadeGerencial
	 * @return verdadeiro ou falso
	 */
	public boolean isUsuarioLogadoParticipanteUGAncestral(UnidadeGerencial unidadeGerencial) {
		if (unidadeGerencial == null) {
			return false;
		}		
		List<UnidadeGerencial> listaUGAncestral = unidadeGerencialService.findAllParents(unidadeGerencial);
		if (listaUGAncestral != null) {
			for (UnidadeGerencial uGAncestral : listaUGAncestral) {
				if (isUsuarioLogadoParticipanteUG(uGAncestral)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Verifica se o usu�rio est� vinculado a alguma unidade gerencial subordinada da ug passada como par�metro
	 * @author Rodrigo Alvarenga
	 * @param unidadeGerencial
	 * @return verdadeiro ou falso
	 */
	public boolean isUsuarioLogadoParticipanteUGSubordinada(UnidadeGerencial unidadeGerencial) {
		if (unidadeGerencial == null) {
			return false;
		}
		
		List<UnidadeGerencial> listaUnidadeGerencial = new ArrayList<UnidadeGerencial>();
			
		// Busca todas as UGs subordinadas da UG selecionada
		listaUnidadeGerencial = unidadeGerencialService.getListaDescendencia(unidadeGerencial, listaUnidadeGerencial);
		
		for (UnidadeGerencial uGSubordinada : listaUnidadeGerencial) {
			if (isUsuarioLogadoParticipanteUG(uGSubordinada)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retorna todas as UGs do usu�rio logado que perten�am
	 * ao plano de gest�o passado como par�metro.
	 * 
	 * @param planoGestao
	 * @return
	 */
	public List<UnidadeGerencial> getUsuarioLogadoUGs(PlanoGestao planoGestao) {
		List<UnidadeGerencial> ugs = new ArrayList<UnidadeGerencial>();
		UnidadeGerencial ug;
		
		// Busca todas as UGs a que o usu�rio est� vinculado.
		Usuario usuario = (Usuario) Neo.getRequestContext().getUser();
		List<UsuarioUnidadeGerencial> usuariosUnidadeGerencial = usuario.getUsuariosUnidadeGerencial();
		if (usuariosUnidadeGerencial != null) {
			for (UsuarioUnidadeGerencial usuarioUnidadeGerencial : usuariosUnidadeGerencial) {
				ug = usuarioUnidadeGerencial.getUnidadeGerencial();
				
				if (ug.getPlanoGestao().getId().equals(planoGestao.getId())) {
					ugs.add(ug);
				}
			}
		}
		return ugs;		
	}
	
	/**
	 * Verifica se o usu�rio logado est� vinculado a uma �rea de qualidade do plano
	 * de gest�o passado como par�metro.
	 * 
	 * @param planoGestao
	 * @return
	 */
	public Boolean isUsuarioLogadoVinculadoAreaQualidade(PlanoGestao planoGestao) {
		UnidadeGerencial ug;
		
		// Busca todas as UGs a que o usu�rio est� vinculado.
		Usuario usuario = (Usuario) Neo.getRequestContext().getUser();
		List<UsuarioUnidadeGerencial> usuariosUnidadeGerencial = usuario.getUsuariosUnidadeGerencial();
		if (usuariosUnidadeGerencial != null) {
			for (UsuarioUnidadeGerencial usuarioUnidadeGerencial : usuariosUnidadeGerencial) {
				ug = usuarioUnidadeGerencial.getUnidadeGerencial();
				
				// Verifica para cada UG se a mesma � da �rea de qualidade.
				if (ug.getPlanoGestao().getId().equals(planoGestao.getId()) && ug.getAreaQualidade()) {
					return true;
				}
			}
		}
		return false;		
	}
	
	/**
	 * Verifica se o usu�rio logado est� vinculado a uma �rea de auditoria interna do plano
	 * de gest�o passado como par�metro.
	 * 
	 * @param planoGestao
	 * @return
	 */
	public Boolean isUsuarioLogadoVinculadoAreaAuditoriaInterna(PlanoGestao planoGestao) {
		UnidadeGerencial ug;
		
		// Busca todas as UGs a que o usu�rio est� vinculado.
		Usuario usuario = (Usuario) Neo.getRequestContext().getUser();
		List<UsuarioUnidadeGerencial> usuariosUnidadeGerencial = usuario.getUsuariosUnidadeGerencial();
		if (usuariosUnidadeGerencial != null) {
			for (UsuarioUnidadeGerencial usuarioUnidadeGerencial : usuariosUnidadeGerencial) {
				ug = usuarioUnidadeGerencial.getUnidadeGerencial();
				
				// Verifica para cada UG se a mesma � da �rea de auditoria interna.
				if (ug.getPlanoGestao().getId().equals(planoGestao.getId()) && ug.getAreaAuditoriaInterna()) {
					return true;
				}
			}
		}
		return false;		
	}	
	
	public List<Usuario> findVinculadosByPlanoGestao(PlanoGestao planoGestao) {
		return usuarioDAO.findVinculadosByPlanoGestao(planoGestao, null);
	}
	
	public List<Usuario> findResponsaveisByPlanoGestao(PlanoGestao planoGestao) {
		return usuarioDAO.findVinculadosByPlanoGestao(planoGestao, FuncaoUGEnum.RESPONSAVEL);
	}	
	
	public List<Usuario> findApoioByPlanoGestao(PlanoGestao planoGestao) {
		return usuarioDAO.findVinculadosByPlanoGestao(planoGestao, FuncaoUGEnum.APOIO);
	}	
	
	public List<Usuario> findAllNaoBloqueados() {
		return usuarioDAO.findAllNaoBloqueados();
	}
	
	public void alterarSenha(Usuario usuario) {
		usuarioDAO.alterarSenha(usuario);
	}
	
	/**
	 * Verifica se o usu�rio logado tem acesso de consulta
	 * aos dados da Unidade Gerencial selecionada.
	 * 
	 * @param ugSelecionada
	 * @return
	 */
	public boolean isAcessoConsultaAutorizado(UnidadeGerencial ugSelecionada) {
		Boolean isUsuarioAdmin                   = isUsuarioLogadoAdmin();
		Boolean isUsuarioParticipanteUG          = isUsuarioLogadoParticipanteUG(ugSelecionada);
		Boolean isUsuarioParticipanteUGAncestral = isUsuarioLogadoParticipanteUGAncestral(ugSelecionada);
		
		return (isUsuarioAdmin || isUsuarioParticipanteUG || isUsuarioParticipanteUGAncestral);
	}
	
	public List<Usuario> findAdministradores() {
		return usuarioDAO.findAdministradores();
	}
	
	public List<Usuario> findNaoAdministradores() {
		return usuarioDAO.findNaoAdministradores();
	}
	
	public void enviaEmailAdministradorSolicitacaoCancelamentoIndicador(Indicador indicador, Usuario solicitante, String justificativa) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
				"Prezado administrador do Geplanes,<br>" +		
				"o usu�rio " + solicitante.getNome() + " cadastrou uma solicita��o de cancelamento para o seguinte indicador:<BR><BR>" +
				"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
				"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
				"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
				"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
				"<b>Justificativa</b>: " + justificativa;
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
				
		enviaEmailAdministrador("[Geplanes] - Solicita��o de cancelamento de indicador.", msg);
	}
	
	public void enviaEmailAdministradorSolicitacaoRepactuacaoIndicador(Indicador indicador, Usuario solicitante, String justificativa) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
				"Prezado administrador do Geplanes,<br>" +		
				"o usu�rio " + solicitante.getNome() + " cadastrou uma solicita��o de repactua��o para o seguinte indicador:<BR><BR>" +
				"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
				"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
				"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
				"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
				"<b>Justificativa</b>: " + justificativa;
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
				
		enviaEmailAdministrador("[Geplanes] - Solicita��o de repactua��o de indicador.", msg);
	}	
	
	public void enviaEmailComentarioSolicitacaoCancelamentoIndicador(Indicador indicador, Usuario solicitante, String comentario) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
			"Prezado(a) usu�rio(a) " + solicitante.getNome() + ",<br>" +		
			"foi cadastrado um coment�rio sobre a solicita��o de cancelamento para o seguinte indicador:<BR><BR>" +
			"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
			"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
			"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
			"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
			"<b>Coment�rio</b>: " + comentario; 
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
		
		String[] to = new String[1];
		to[0] = solicitante.getEmail();
		ParametrosSistema parametrosSistema = ParametrosSistemaService.getParametrosSistema();
		String from = parametrosSistema.getEmailRemetente();
		EnvioEmail.getInstance().enviaEmail(from, to, "[Geplanes] - Coment�rio sobre solicita��o de cancelamento de indicador.", msg);		
	}
	
	public void enviaEmailComentarioSolicitacaoRepactuacaoIndicador(Indicador indicador, Usuario solicitante, String comentario) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
			"Prezado(a) usu�rio(a) " + solicitante.getNome() + ",<br>" +		
			"foi cadastrado um coment�rio sobre a solicita��o de repactua��o para o seguinte indicador:<BR><BR>" +
			"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
			"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
			"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
			"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
			"<b>Coment�rio</b>: " + comentario; 
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
		
		String[] to = new String[1];
		to[0] = solicitante.getEmail();
		ParametrosSistema parametrosSistema = ParametrosSistemaService.getParametrosSistema();
		String from = parametrosSistema.getEmailRemetente();
		EnvioEmail.getInstance().enviaEmail(from, to, "[Geplanes] - Coment�rio sobre solicita��o de repactua��o de indicador.", msg);		
	}
	
	public void enviaEmailAdministradorComentarioSolicitacaoCancelamentoIndicador(Indicador indicador, Usuario solicitante, String comentario) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
			"Prezado administrador do Geplanes,<br>" +		
			"o usu�rio " + solicitante.getNome() + " cadastrou um coment�rio sobre a solicita��o de cancelamento para o seguinte indicador:<BR><BR>" +
			"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
			"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
			"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
			"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
			"<b>Coment�rio</b>: " + comentario; 
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
		
		enviaEmailAdministrador("[Geplanes] - Coment�rio sobre solicita��o de cancelamento de indicador.", msg);
	}
	
	public void enviaEmailAdministradorComentarioSolicitacaoRepactuacaoIndicador(Indicador indicador, Usuario solicitante, String comentario) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
			"Prezado administrador do Geplanes,<br>" +		
			"o usu�rio " + solicitante.getNome() + " cadastrou um coment�rio sobre a solicita��o de repactua��o para o seguinte indicador:<BR><BR>" +
			"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
			"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
			"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
			"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
			"<b>Coment�rio</b>: " + comentario; 
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
		
		enviaEmailAdministrador("[Geplanes] - Coment�rio sobre solicita��o de repactua��o de indicador.", msg);
	}	
	
	public void enviaEmailRespostaSolicitacaoCancelamentoIndicador(Indicador indicador, Usuario solicitante, String justificativa, String resultado) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
			"Prezado(a) usu�rio(a) " + solicitante.getNome() + ",<br>" +		
			"foi " + resultado + " sua solicita��o de cancelamento para o seguinte indicador:<BR><BR>" +
			"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
			"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
			"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
			"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
			"<b>Justificativa</b>: " + justificativa;
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
		
		String[] to = new String[1];
		to[0] = solicitante.getEmail();
		ParametrosSistema parametrosSistema = ParametrosSistemaService.getParametrosSistema();
		String from = parametrosSistema.getEmailRemetente();
		EnvioEmail.getInstance().enviaEmail(from, to, "[Geplanes] - Resultado da solicita��o de cancelamento de indicador.", msg);		
	}
	
	public void enviaEmailRespostaSolicitacaoRepactuacaoIndicador(Indicador indicador, Usuario solicitante, String justificativa, String resultado) {
		indicador = indicadorService.loadForEmail(indicador);
		solicitante = this.load(solicitante);
		
		String msg = 	
			"Prezado(a) usu�rio(a) " + solicitante.getNome() + ",<br>" +		
			"foi " + resultado + " sua solicita��o de repactua��o para o seguinte indicador:<BR><BR>" +
			"<b>" + Nomes.Plano_de_Gestao + "</b>: " + indicador.getUnidadeGerencial().getPlanoGestao().getAnoExercicio() + "<BR>" +
			"<b>Unidade Gerencial</b>: " + indicador.getUnidadeGerencial().getDescricao() + "<BR>" +
			"<b>Objetivo Estrat�gico</b>: " + indicador.getObjetivoMapaEstrategico().getDescricao() + "<BR>" +
			"<b>Indicador</b>: " + indicador.getNome() + "<BR>" +
			"<b>Justificativa</b>: " + justificativa;
		
		msg = EnvioEmail.montaCorpoMensagem().replace("--M--", msg);
		
		String[] to = new String[1];
		to[0] = solicitante.getEmail();
		ParametrosSistema parametrosSistema = ParametrosSistemaService.getParametrosSistema();
		String from = parametrosSistema.getEmailRemetente();
		EnvioEmail.getInstance().enviaEmail(from, to, "[Geplanes] - Resultado da solicita��o de repactua��o de indicador.", msg);		
	}	
	
	public void enviaEmailAdministrador(String assunto, String msg){
		List<Usuario> listaUsuario = this.findAdministradores();
		String[] to = new String[listaUsuario.size()];
		for (int i = 0; i < listaUsuario.size(); i++) {
			to[i] = listaUsuario.get(i).getEmail();
		}
		
		ParametrosSistema parametrosSistema = ParametrosSistemaService.getParametrosSistema();
		String from = parametrosSistema.getEmailRemetente();
		
		EnvioEmail.getInstance().enviaEmail(from, to, assunto, msg);
	}
	
	public void updatePassword(Usuario usuario, boolean encrypt){
		if (encrypt) {
			usuario = encryptPassword(usuario);
		}
		usuarioDAO.updatePassword(usuario);
	}
	
	public Usuario encryptPassword(Usuario usuario) {		
		String encryptedPassword = Util.crypto.makeHashJasypt(usuario.getSenha());
		usuario.setSenha(encryptedPassword);
		return usuario;
	}	
	
	/* singleton */
	private static UsuarioService instance;
	public static UsuarioService getInstance() {
		if(instance == null){
			instance = Neo.getObject(UsuarioService.class);
		}
		return instance;
	}	
}