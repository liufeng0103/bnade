<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>打赏 - BNADE魔兽世界</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-md-8">
					<h3 class="page-header">打赏</h3>
					<p>如果您觉得本网站对您帮助很大，不妨打赏我一下，我将更有动力开发和完善更多功能。</p>
					<hr/>
					<p class="text-center">
						<a id="weixinBtn" class="btn btn-primary">微信支付</a>
						<a id="alipayBtn" class="btn btn-primary">支付宝支付</a>
					</p>
					<div id="weixinDiv" style="display: none;">
						<ul class="nav nav-tabs">
							<li><a href="#donate-weixin-5" data-toggle="tab">￥5</a></li>
							<li class="active"><a href="#donate-weixin-10" data-toggle="tab">￥10</a></li>
							<li><a href="#donate-weixin-20" data-toggle="tab">￥20</a></li>
							<li><a href="#donate-weixin-39" data-toggle="tab">￥39</a></li>
							<li><a href="#donate-weixin-88" data-toggle="tab">我是土豪</a></li>
						</ul>
						<div class="tab-content">
							<div id="donate-weixin-5" class="tab-pane">
								<img src="/images/weixin-5.jpg">
							</div>
							<div id="donate-weixin-10" class="tab-pane in active">
								<img src="/images/weixin-10.jpg">
							</div>
							<div id="donate-weixin-20" class="tab-pane">
								<img src="/images/weixin-20.jpg">
							</div>
							<div id="donate-weixin-39" class="tab-pane">
								<img src="/images/weixin-39.jpg">
							</div>
							<div id="donate-weixin-88" class="tab-pane">
								<img src="/images/weixin-88.jpg">
							</div>
						</div>
					</div>
					<div id="alipayDiv" style="display: none;">
						<ul class="nav nav-tabs">
							<li><a href="#donate-alipay-5" data-toggle="tab">￥5</a></li>
							<li class="active"><a href="#donate-alipay-10" data-toggle="tab">￥10</a></li>
							<li><a href="#donate-alipay-20" data-toggle="tab">￥20</a></li>
							<li><a href="#donate-alipay-39" data-toggle="tab">￥39</a></li>
							<li><a href="#donate-alipay-88" data-toggle="tab">我是土豪</a></li>
						</ul>
						<div class="tab-content">
							<div id="donate-alipay-5" class="tab-pane">
								<img src="/images/alipay-5.jpg">
							</div>
							<div id="donate-alipay-10" class="tab-pane in active">
								<img src="/images/alipay-10.jpg">
							</div>
							<div id="donate-alipay-20" class="tab-pane">
								<img src="/images/alipay-20.jpg">
							</div>
							<div id="donate-alipay-39" class="tab-pane">
								<img src="/images/alipay-39.jpg">
							</div>
							<div id="donate-alipay-88" class="tab-pane">
								<img src="/images/alipay-88.jpg">
							</div>
						</div>
					</div>
					<div id="messageDiv" style="display: none;">
						<p>非常感谢您的打赏支持，请在下面留言，稍后我会把打赏和留言放到打赏列表中，再次感谢您的支持。</p>
						<form>
							<div class="form-group">
								<label for="exampleInputName2">昵称:</label> 
								<input id="nickname" type="text" class="form-control" style="width: 300px">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">留言:</label>
								<textarea id="message" class="form-control" rows="5"></textarea>
							</div>
							<button id="sendMessageBtn" type="button" class="btn btn-success">发送留言</button>
							<label id="msg"></label>
						</form>
					</div>
				</div>
			</div>
			<div class="row" style="display: none;">
				<div class="col-md-8">
					<h4>打赏列表</h4>
					<table class="table table-hover">
						<thead>
							<tr><th>日期</th><th>打赏人</th><th>金额</th><th>留言</th></tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script type="text/javascript">
	$('#weixinBtn').click(function() {
		$('#weixinDiv').toggle();
		$('#alipayDiv').hide();
		$('#messageDiv').show();
	});
	$('#alipayBtn').click(function() {
		$('#alipayDiv').toggle();
		$('#weixinDiv').hide();
		$('#messageDiv').show();
	});
	$('#sendMessageBtn').click(function() {
		var nickname = $('#nickname').val().trim();
		var message = $('#message').val().trim();
		var data = {
			nickname: nickname,
			message: message
		};
		if (nickname === "") {
			$('#msg').text("请输入昵称");
		} else if (nickname.length > 16) {
			$('#msg').text("昵称超长，请不要超过16个字符");
		} else if (message === "") {
			$('#msg').text("请输入留言");
		} else if (message.length > 128) {
			$('#msg').text("留言超长，请不要超过128个字符");
		} else {
			$.ajax({
				url: "https://api.bnade.com/messages",
				method: "POST",
				data: data,
				crossDomain : true == !(document.all), // 解决IE9跨域访问问题
				success: function(data) {
					$('#msg').text("留言发送成功");
					$('#nickname').val("");
					$('#message').val("");
				},
				error: function(xhr) {
					$('#msg').text("留言发送失败，抱歉，请联系管理员");
				}
			});
		}
	});
	</script>
</body>
</html>