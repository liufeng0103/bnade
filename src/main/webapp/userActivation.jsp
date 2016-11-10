<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>用户激活</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">用户激活</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">激活</div>
					<div class="panel-body">
						<form class="form-inline" action="/page/user/activation" method="post">
		      				<input name="activationCode" class="form-control" type="text" placeholder="请输入激活码">
		      				<button class="btn btn-primary" type="submit">确定</button>
		      				<label class="text-success">激活成功</label>
		      			</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>