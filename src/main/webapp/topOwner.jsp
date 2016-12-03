<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界拍卖行玩家排行</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<form onsubmit="return false;" class="navbar-form">
					<div class="form-inline">
						<div class="form-group">
							<div class="input-group">
								<input id="realm" type="text" placeholder="服务器(选填)"
									class="form-control">
								<div class="input-group-btn">
									<button type="button" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false"
										class="btn btn-default dropdown-toggle">
										选择 <span class="caret"></span>
									</button>
									<ul id="realmSelectList"
										class="dropdown-menu dropdown-menu-right"></ul>
								</div>
							</div>
						</div>
						<input id="queryBtn" type="button" value="查询"
							class="btn btn-success"> <input type="reset" value="重置"
							class="btn btn-success">
					</div>
				</form>
				<p id="msg"></p>
			</div>
			<div class="row">
				<div class="col-md-4">
					<h3>玩家物品总价值排行榜</h3>
					<div id="topWorth"></div>
				</div>
				<div class="col-md-4">
					<h3>玩家物品种类数排行榜</h3>
					<div id="topCategory"></div>
				</div>
				<div class="col-md-4">
					<h3>玩家物品总数量排行榜</h3>
					<div id="topQuantity"></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/topOwner.js"></script>
</body>
</html>