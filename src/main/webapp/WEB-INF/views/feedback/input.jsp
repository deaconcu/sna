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
	    <h1>提交feedback</h1>
		<form action="feedback/add" method="post">
		    title: (请输入1-40个字)<br/><input type="text" name="title" size="100"> 
		    <br/><br/>	
			email: <br/><input type="text" name="email" size="100"> 
			<br/><br/>
			content: (请输入1-200个字)<br/><textarea name="content" cols="85" rows="12"></textarea>
			<br/><br/>
			<input type="submit" value="submit"/>&nbsp;<input type="reset" value="reset"/>
		</form>
	</body>
</html>