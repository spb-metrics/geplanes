<% 
	String app = request.getContextPath();
%>

<html>
	<head>
		<link href="<%= app %>/css/estilo.css" rel="stylesheet" type="text/css"/>
	</head>
	<body leftmargin="0" topmargin="0" style="padding:0px" bgColor="#FFFFFF" style="background-color: #FFFFFF;overflow:hidden;">
		<table class="fd_tela" width="100%" height="100%">
			<tr>
				<td class="fd_tela_titulo" style="height: 20px;" colspan="3">Legenda - Far�is</td>
			</tr>		
			<tr>
				<td><img src="<%= app %>/images/bola-verde.png" alt="Verde" title="Verde"/></td>
				<td>Verde</td>
				<td><span class="desc11">Meta cumprida em 100%.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/bola-amarela.png" alt="Amarelo" title="Amarelo"/></td>
				<td>Amarelo</td>
				<td><span class="desc11">Meta cumprida parcialmente, dentro do(s) limite(s) definido(s) pelo percentual de toler�ncia.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/bola-vermelha.png" alt="Vermelho" title="Vermelho"/></td>
				<td>Vermelho</td>
				<td><span class="desc11">Meta n�o cumprida.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/bola-branca.png" alt="Branco" title="Branco"/></td>
				<td>Branco</td>
				<td><span class="desc11">Indicador n�o cadastrado ou nenhum resultado lan�ado no per�odo.</span></td>
			</tr>
			<tr>
				<td><img src="<%= app %>/images/bola-cinza.png" alt="Cinza" title="Cinza"/></td>
				<td>Cinza</td>
				<td><span class="desc11">Meta n�o aplic�vel ao per�odo.</span></td>
			</tr>
		</table>
	</body>
</html>