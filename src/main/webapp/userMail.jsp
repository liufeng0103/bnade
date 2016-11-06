<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp" %>
<title>修改邮箱</title>
<%@ include file="includes/link.jsp" %>
</head>
<body>
	<jsp:include page="includes/header.jsp" />	
    <div class="container-fluid">
      <div class="page-content container">
      	<div class="row">
	      	<div class="panel panel-default">
	      		<div class="panel-heading">修改我的邮箱</div>
	      		<div class="panel-body">
	      			<form class="form-inline" action="/page/user/saveMail" method="post">
	      				<label>邮箱：</label><input name="email" class="form-control" type="email" value="${sessionScope.user.email}" placeholder="请输入您的邮箱">
	      				<button class="btn btn-primary" type="submit">保存</button>
	      				<c:if test="${!empty sessionScope.user.email}">
	      					<c:if test="${sessionScope.user.validated != 1}">
		      					<label class="text-danger">未验证，<a id="validateMail" href="javascript:void(0)">点击发送验证</a></label>
		      				</c:if>
		      				<c:if test="${sessionScope.user.validated == 1}">
		      					<label class="text-success">已验证</label>
		      				</c:if>
	      				</c:if>
	      			</form>
	      			<label>${message}</label>
	      			<label id="msg"></label>
	      		</div>
	      	</div>
      	</div>
      </div>
    </div>
    <%@ include file="includes/footer.jsp" %> 
    <%@ include file="includes/script.jsp" %>
    <script>
    	$("#validateMail").click(function(){
    		BN.Resource.sendMailValidation(function(result){
    			if (result.code === 0) {
    				alert("验证邮件已发送，请激活");
    			} else {
    				alert("出错："+result.message);
    			}
    		});
    	});
    </script>
</body>
</html>