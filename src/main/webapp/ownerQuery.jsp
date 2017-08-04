<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>玩家拍卖物品查询 - BNADE魔兽世界</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul id="realmQueryList" class="nav nav-sidebar"></ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="col-md-10">
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
							<div class="form-group">
								<input id="owner" type="text" placeholder="玩家名"
									class="form-control">
							</div>
							<input id="queryBtn" type="button" value="查询"
								class="btn btn-success"> <input type="reset" value="重置"
								class="btn btn-success">
						</div>
					</form>
					<div id="ownerItemsContainer"></div>
				</div>
				<div class="col-md-2"></div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/ownerQuery.js"></script>
</body>
</html>