<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	</head>
	
	<body>
	    <h1>feedbacks</h1>
	    
	    <c:if test="${page > 1}"><a href="feedback/list/<c:out value="${page-1}"/>">上一页</a></c:if>
	    <a href="feedback/list/<c:out value="${page+1}"/>">下一页</a>
	    <br /><br />
	    <c:forEach var="feedback" items="${list}">
            title: <c:out value="${feedback.title}" /> <br />
            content: <c:out value="${feedback.content}" /> <br />	
            email: <c:out value="${feedback.email}" /><br />
            <hr>
	    </c:forEach>
	    <br />
        <c:if test="${page > 1}"><a href="feedback/list/<c:out value="${page-1}"/>">上一页</a></c:if>
        <a href="feedback/list/<c:out value="${page+1}"/>">下一页</a>
	    <br />
	    <br />
	
	</body>

</html>