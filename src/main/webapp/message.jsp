<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp" %>
<title>${title}</title>
<%@ include file="includes/link.jsp" %>
</head>
<body>
	<jsp:include page="includes/header.jsp" />	
    <div class="container-fluid">
      <div class="container">
      	<h3>${message}</h3>
      </div>
    </div>
    <%@ include file="includes/footer.jsp" %>
    <%@ include file="includes/script.jsp" %>
</body>
</html>