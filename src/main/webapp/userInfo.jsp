<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp" %>
<title>我的信息</title>
<%@ include file="includes/link.jsp" %>
</head>
<body>
	<jsp:include page="includes/header.jsp" />	
    <div class="container-fluid">
      <div class="page-content container">
      	<div class="row">
	      	<div class="panel panel-default">
	      		<div class="panel-heading">我的信息</div>
	      		<div class="panel-body">
	      			<p>昵称：${sessionScope.user.nickname}</p>
	      			<p>邮箱：${sessionScope.user.email}</p>
	      			<p>高级版：${sessionScope.user.expireDate}</p>
	      		</div>
	      	</div>
      	</div>
      </div>
    </div>
    <%@ include file="includes/footer.jsp" %> 
    <%@ include file="includes/script.jsp" %>
    <script>
    </script>
</body>
</html>