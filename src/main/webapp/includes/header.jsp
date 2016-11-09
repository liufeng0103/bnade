<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar" class="navbar-toggle collapsed">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a href="/" class="navbar-brand">BNADE</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/itemQuery.jsp">普通物品</a></li>
				<li><a href="/wowtoken.jsp">时光徽章</a></li>
				<li><a href="/page/item/hotSearch">搜索排行(测试)</a></li>
				<li class="dropdown">
					<a href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" class="dropdown-toggle">拍卖<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/petQuery.jsp">宠物价格</a></li>
						<li><a href="/ownerQuery.jsp">玩家物品</a></li>
						<li><a href="/auctionQuantity.jsp">拍卖排行</a></li>
						<li><a href="/topOwner.jsp">玩家排行</a></li>
						<li><a href="/download.jsp">下载</a></li>
					</ul>
				</li>
				<c:if test="${empty sessionScope.user}">
					<li><a href="/login.do"><span class="glyphicon glyphicon-user"></span> QQ</a></li>
				</c:if>
				<c:if test="${!empty sessionScope.user}">
					<li class="dropdown">
						<a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${sessionScope.user.nickname}(测试中)<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/page/user/mail">修改邮箱</a></li>
							<li><a href="/page/user/realm">我的服务器</a></li>
							<li><a href="/page/user/itemNotification">我的物品提醒</a></li>
							<li><a href="/signOut.do">退出</a></li>
						</ul>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>