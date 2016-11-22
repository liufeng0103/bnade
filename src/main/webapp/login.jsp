<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title></title>
<%@ include file="includes/link.jsp"%>
<style type="text/css">
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #fff;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container">
		<form class="form-signin" role="form">
			<h2 class="form-signin-heading">请登录</h2>
			<input type="email" class="form-control" placeholder="请输入邮箱" required autofocus> 
			<input type="password" class="form-control" placeholder="请输入密码" required>
			<div class="checkbox">
				<label> 
					<input type="checkbox" value="remember-me">	记住我
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
			<div>
				<p>没有账号？ <a>立即注册</a></p>
				<p>使用以下账号直接登录</p>
				<a href="/login.do"><img alt="qq" src="/images/qq_login.png"></a>
			</div>
		</form>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>