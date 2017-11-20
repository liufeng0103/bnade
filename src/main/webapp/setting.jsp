<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>设置 - BNADE魔兽世界</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-md-8">
					<h1 class="page-header">设置</h1>
					<form>
						<div class="form-group">
							<label>服务器可保存数量:</label> 
							<input id="realmCountInput" type="text" class="form-control" style="width: 300px">
						</div>
						<div class="form-group">
							<label>物品可保存数量:</label> 
							<input id="itemCountInput" type="text" class="form-control" style="width: 300px">
						</div>
						<button id="saveBtn" type="button" class="btn btn-success">保存</button>
						<a id="exportBtn" type="button" class="btn btn-success">导出物品</a>
						<button id="importBtn" type="button" class="btn btn-success">导入物品</button>
						<label id="msg"></label>
						<div class="form-group" id="importPanel" style="display:none">
							<label>粘贴物品列表到下面文本框:</label>
							<textarea id="itemListTxt" class="form-control" rows="3"></textarea>
							<button id="importOkBtn" type="button" class="btn btn-success">确定导入</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script type="text/javascript">
	var realmKey = "queryRealms";
	var itemKey = "queryItems";
	var realmNameArray = store.get("queryRealms");
	var itemNameArray = store.get("queryItems");
	!function() {
		$("#realmCountInput").val(realmNameArray.length);
		$("#itemCountInput").val(itemNameArray.length);
	} ();
	$("#saveBtn").click(function() {
		var realmCount = $("#realmCountInput").val();
		var itemCount = $("#itemCountInput").val();
		// 验证输入
		if (isNaN(realmCount) || realmCount < 0) {
			$("#msg").text("请设置正确的服务器数");
		} else if (isNaN(itemCount) || itemCount < 0) {
			$("#msg").text("请设置正确的物品数");
		} else {
			realmNameArray.length = parseInt(realmCount);
			store.set(realmKey, realmNameArray);
			
			itemNameArray.length = parseInt(itemCount);
			store.set(itemKey, itemNameArray);
			$("#msg").text("保存成功");
		}
	});
	$("#exportBtn")
		.attr("download", "items.txt")
		.attr("href", "data:text/txt;charset=UTF-8," + JSON.stringify(itemNameArray));
	$("#importBtn").click(function() {
		$("#importPanel").show();
	});
	$("#importOkBtn").click(function() {
		var itemList = $("#itemListTxt").val();
		store.set(itemKey, itemList);
		$("#msg").text("物品导入成功");
	});

	</script>
</body>
</html>