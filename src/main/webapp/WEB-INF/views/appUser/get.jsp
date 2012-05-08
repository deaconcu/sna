<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body>
	用户：
	<c:out value="${appUser.userId}" />
	<br />
	<br /> 来自：
	<c:out value="${appUser.app.appName}" />
	<br />
	<br /> 朋友：[ 
	<c:forEach var="friend" items="${appUser.friends}">
		<c:out value="${friend.userId}" />,&nbsp;&nbsp;
    </c:forEach>]
	<br />
	<br /> 历史登录组：
	<br />
	<c:forEach var="group" items="${appUser.groups}">
	    (
		<c:forEach var="user" items="${group.contains}">
			<c:out value="${user.app.appName}" />: <c:out value="${user.userId}" />,&nbsp;&nbsp;
		</c:forEach>
		)
		<br />
	</c:forEach>
</body>
</html>
