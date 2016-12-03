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
			<c:if test="${!sessionScope.user.isVip}">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/itemQuery.jsp">物价查询</a></li>
					<li><a href="/wowtoken.jsp">时光徽章</a></li>
					<li><a href="/auctionQuantity.jsp">服务器排行</a></li>
					<li><a href="/download.jsp">下载</a></li>
					<li><a href="/premium.jsp">高级版</a></li>
					<c:if test="${empty sessionScope.user}">
						<li><a href="/page/user/login">登录</a></li>
					</c:if>
					<c:if test="${!empty sessionScope.user}">
						<li class="dropdown">
							<a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">欢迎,${sessionScope.user.nickname}<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/page/user/activation">激活高级版</a></li>
								<li class="divider"></li>
								<li><a href="/page/user/signOut">退出</a></li>
							</ul>
						</li>
					</c:if>
				</ul>
			</c:if>
			<c:if test="${sessionScope.user.isVip}">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/page/user/itemQuery">物价查询</a></li>
					<li><a href="/wowtoken.jsp">时光徽章</a></li>
					<li><a href="/auctionQuantity.jsp">服务器排行</a></li>
					<li><a href="/download.jsp">下载</a></li>
					<li class="dropdown">
						<a href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" class="dropdown-toggle">拍卖行<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/page/item/hotSearch">搜索排行</a></li>
							<li><a href="/page/item/search">物品搜索</a></li>
							<li><a href="/topOwner.jsp">玩家排行</a></li>
							<li><a href="/petQuery.jsp">宠物价格</a></li>
							<li><a href="/ownerQuery.jsp">玩家物品</a></li>
						</ul>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${sessionScope.user.nickname}<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/page/user/info">我的信息</a></li>
							<li><a href="/page/user/mail">修改邮箱</a></li>
							<li><a href="/page/user/realm">我的服务器</a></li>
							<li><a href="/page/user/character">我的角色</a></li>
							<li><a href="/page/user/itemNotification">我的物品提醒</a></li>
							<li class="divider"></li>
							<li><a href="/page/user/signOut">退出</a></li>
						</ul>
					</li>
				</ul>
				<form class="navbar-form navbar-right" action="/page/item/search">
					<div class="input-group">
						<input id="itemName2" name="name" type="text" class="form-control" placeholder="物品名称" value="${searchName }">
						<span class="input-group-btn">
							<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
						</span>
					</div>
				</form>
			</c:if>
		</div>
	</div>
</nav>