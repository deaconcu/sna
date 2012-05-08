<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	      <base href="<%=basePath%>">
	      <meta http-equiv="pragma" content="no-cache">
	      <meta http-equiv="cache-control" content="no-cache">
	      <meta http-equiv="expires" content="0">   
	      <meta http-equiv="content-type" content="text/html;charset=utf8"> 
	</head>
	<body>
	    ${succMsg}
	</body>
</html>