<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="n" uri="neo"%>
<%@ taglib prefix="t" uri="template"%>

<html>
	<head>
		<n:head/>
		<script language="JavaScript" src="${ctx}/javascript/ajquery.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/jquery.maskedinput-1.1.1.pack.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/dimensions.pack.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/dimmer.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/treetable.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/GeplanesUtil.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/thickbox-compressed.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/jquery.bgiframe.min.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/jquery.autocomplete.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/jquery.maskedinput-1.1.1.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/jquery.ajaxQueue.js"></script>
		<script language="JavaScript" src="${ctx}/javascript/akModal.js"></script>		
	</head>
	<body leftmargin="0" topmargin="0" style="padding:0px" bgColor="#FFFFFF" style="background-color: #FFFFFF">
		<table class="lookup_janela">
			<TR>
				<TD class="fd_tela_titulo" style="height: 20px;">Anexo do indicador</TD>
			</TR>
			<TR>
				<TD valign="top">
					<n:bean name="indicadorAnexo">
						<t:janelaResultados>
							<n:dataGrid property="anexosIndicador" headerStyleClass="txt_tit" bodyStyleClasses="txt_l1, txt_l2" styleClass="fd_tabela1">
								<t:property name="nome" /> 
								<t:property name="descricao" /> 
								<t:property name="arquivo" mode="output"/> 
							</n:dataGrid>
						</t:janelaResultados>
					</n:bean>
				</TD>
			</TR>
			
		</table>
	</body>
</html>