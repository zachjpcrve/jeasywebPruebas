<%@taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="IBM Software Development Platform">
<meta http-equiv="Content-Style-Type" content="text/css">

<title>Acceso LDAP</title>
</head>
<body>
<s:form action="acceso.do" theme="simple" id="formAcceso">
	<input type="hidden" name="desa" id="desa" value="desa"/>
	<p>Por Favor Ingrese Su C&oacute;digo de Registro</p>
	Usuario : <input type="text" id="codldap" name="codldap" value="P018543"/>
	<br/>
	<s:submit value="Aceptar" cssClass="ui-button ui-widget ui-state-default ui-corner-all"></s:submit>
</s:form>
</body>
</html>