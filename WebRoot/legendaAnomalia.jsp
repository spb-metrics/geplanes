<% 
	String app = request.getContextPath();
%>

<html>
	<head>
		<link href="<%= app %>/css/estilo.css" rel="stylesheet" type="text/css"/>
	</head>
	<body leftmargin="0" topmargin="0" style="padding:0px" bgColor="#FFFFFF" style="background-color: #FFFFFF;overflow:hidden;">
		<table class="fd_tela">
			<tr>
				<td class="fd_tela_titulo" style="height: 20px;" colspan="3">Legenda - Status das Anomalias</td>
			</tr>		
			<tr>
				<td><img src="<%= app %>/images/ico_aberta.gif" alt="Aberta" title="Aberta"/></td>
				<td>Aberta</td>
				<td><span class="desc">Obtido assim que uma anomalia � criada</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_bloqueada.gif" alt="Bloqueada" title=""Bloqueada""/></td>
				<td>Bloqueada</td>
				<td><span class="desc">Obtido quando uma anomalia n�o � tratada no prazo pr�-estabelecido.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_cumprimentopendente.gif" alt="Cumprimento pendente" title="Cumprimento pendente"/></td>
				<td>Cumprimento pendente</td>
				<td><span class="desc">Obtido quando um ou mais planos de a��o de uma anomalia n�o foram cumpridos.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_encerrada.gif" alt="Encerrada" title="Encerrada"/></td>
				<td>Encerrada</td>
				<td><span class="desc">Obtido quando uma anomalia � encerrada.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_encerramentopendente.gif" alt="Encerramento pendente" title="Encerramento pendente"/></td>
				<td>Encerramento pendente</td>
				<td><span class="desc">Obtido quando uma anomalia n�o � encerrada no prazo pr�-estabelecido.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_encerramentosolicitado.gif" alt="Encerramento solicitado" title="Encerramento solicitado"/></td>
				<td>Encerramento solicitado</td>
				<td><span class="desc">Obtido quando a anomalia est� tratada e � solicitado ao setor de qualidade que verifique se o plano de a��o utilizado foi adequado.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_reanalise.gif" alt="Rean�lise" title="Rean�lise"/></td>
				<td>Rean�lise</td>
				<td><span class="desc">Obtido quando o plano de a��o utilizado para o tratamento de uma anomalia n�o foi eficaz.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_tratada.gif" alt="Tratada" title="Tratada"/></td>
				<td>Tratada</td>
				<td><span class="desc">Obtido quando uma anomalia com status 'Aberta' tem preenchidos o campo 'An�lise de Causas' e pelo menos um Plano de A��o.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/ico_tratamentopendente.gif" alt="Tratamento pendente" title="Tratamento pendente"/></td>
				<td>Tratamento pendente</td>
				<td><span class="desc">Obtido quando uma anomalia � desbloqueada pela �rea de qualidade.</span></td>
			</tr>			
		</table>
	</body>
</html>