<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>物品提醒管理</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div id="msgModal" class="modal fade bs-example-modal-sm">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<h1 class="page-header">物品提醒管理</h1>
				<h3>添加</h3>
				<p>
					服务器请在<a href="/page/user/realm">我的服务器</a>页面设置
				</p>
				<form onsubmit="return false;" class="form-inline">
					<input id="itemInput" type="text" placeholder="物品名"
						class="form-control">
					<button id="searchBtn" class="btn btn-primary">搜索</button>
					<label id="msg" class="text-danger"></label>
				</form>
				<p></p>
				<div id="setItemNForm" class="form-inline">
					<span>当</span><span id="itemSelectDiv"></span><span>在</span>
					<select id="realmSlt" class="form-control"></select><span>的最低一口单价</span>
					<select id="isInvertedSlt" class="form-control">
						<option value="0">低于</option>
						<option value="1">高于</option>
					</select> <input id="goldInput" type="text" placeholder="如：1.25" class="form-control"><span>金时通知我</span>
					<button id="addItemBtn" class="btn btn-primary">
						<span class="glyphicon glyphicon-plus"></span>添加
					</button>
				</div>
				<h3>所有的物品通知</h3>
				<div>
					<button id="delItemNBtn" class="btn btn-sm btn-danger">
						<span class='glyphicon glyphicon-remove'></span> 删除
					</button>
					<button id="enableMailBtn" class="btn btn-sm btn-success">
						<span class='glyphicon glyphicon-envelope'></span> 启用邮件通知
					</button>
					<button id="disableMailBtn" class="btn btn-sm btn-warning">
						<span class='glyphicon glyphicon-minus'></span> 关闭邮件通知
					</button>
					<a id="exportBtn" class="btn btn-sm btn-primary">
						<span class='glyphicon glyphicon-export'></span> 导出配置
					</a>
				</div>
				<table class="table table-condensed table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>服务器</th>
							<th>物品</th>
							<th>说明</th>
							<th>邮件通知</th>
							<th>高于/低于</th>
							<th>单价一口价</th>
							<th>修改</th>
							<th><input id="selectAllCheckbox" class="checkbox"
								type="checkbox" /></th>
						</tr>
					</thead>
					<tbody id="itemNotificationBody"></tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script>
	var count = 0;
	function myAlert(msg) {
		$("#msgModal").on("show.bs.modal", function(event){
			$(".modal-body").html("<div class='modal-body'><div class='modal-body-content'>"+msg+"</div></div><div class='modal-footer'><button class='btn btn-default' type='button' data-dismiss='modal'>关闭</button></div>");
		});
	}

	function loading() {
		$("#msgModal").on("show.bs.modal", function(event){
			$(".modal-body").html("<div><img src='images/loading.gif'> 正在查询，请稍等...</div>");
		});
	}

	function hideAlert() {
		$('#msgModal').modal('hide');
	}

	function generateRow(itemN) {
		var desc = "";
		var bonusList = itemN.bonusList == null ? "" : itemN.bonusList;
		if (itemN.itemId == 82800 && itemN.petStats != null) {
			desc = "类型"+itemN.petBreedId+" 生命值"+itemN.petStats.health+" 攻击"+itemN.petStats.power+" 速度"+itemN.petStats.speed;
		} else {
			desc = itemN.bonusList == null || itemN.bonusList == "" ? "" : Bnade.getBonusDesc(itemN.bonusList, itemN.itemLevel);
		}
		var row = "<tr realmId='"+itemN.realmId+"' itemId='"+itemN.itemId+"' isInverted='"+itemN.isInverted+"' data-petSpeciesId='"+itemN.petSpeciesId+"' data-petBreedId='"+itemN.petBreedId+"' data-bonusList='"+itemN.bonusList+"'><td>"
				+ (++count)
				+ "</td><td>"
				+ BN.Realm.getRealmById(itemN.realmId).connected
				+ "</td><td>"
				+ itemN.itemName
				+ "</td><td>"
				+ desc
				+ "</td><td class='emailN'>"
				+ (itemN.emailNotification === 0 ? "关闭" : "启用")
				+ "</td><td>"
				+ (itemN.isInverted == 0 ? "低于" : "高于")
				+ "</td><td>"
				+ "<div class='form-inline'><input class='form-control input-sm priceInput' type='text' value='"
				+ BN.Util.getGold(itemN.price)
				+ "'/><label>g</label></div>"
				+ "</td><td><button class='btn btn-sm btn-primary updateBtn'><span class='glyphicon glyphicon-edit'></span> 修改</button></td><td><input class='checkbox deleteItemNCheckbox' type='checkbox'/></td></tr>";
		return row;
	}

	function bindUpdate(){
		$(".updateBtn").click(function(){
			var $tr = $(this).parent().parent();
			var itemN = {itemId : $tr.attr("itemId"),
					petSpeciesId : $tr.attr("data-petSpeciesId"),
					petBreedId : $tr.attr("data-petBreedId"),
					bonusList : $tr.attr("data-bonusList"),
					realmId : $tr.attr("realmId"),
					isInverted : $tr.attr("isInverted"),
					price : 10000 * $tr.find(".priceInput").val()};
			BN.Resource.updateUserItemNotification(itemN, function(result){
				if (result.code === 0) {
					alert("修改成功");
				} else {
					alert("修改失败："+result.message);
				}
			});
		});
	}

	function bindDelete(){
		$("#delItemNBtn").click(function() {
			if (confirm("确定要删除这些物品通知吗？")) {
				var rows = [];
				var trs = getCheckedRows();
				for (var i in trs) {
					var $tr = trs[i];
					var itemN = {itemId : $tr.attr("itemId"),
							realmId : $tr.attr("realmId"),
							isInverted : $tr.attr("isInverted"),
							petSpeciesId : $tr.attr("data-petSpeciesId"),
							petBreedId : $tr.attr("data-petBreedId"),
							bonusList : $tr.attr("data-bonusList"),};
					rows.push(itemN);
				}
				BN.Resource.deleteUserItemNotifications(JSON.stringify(rows),function(result){
					if (result.code === 0) {
						for (var i in trs) {
							trs[i].remove();
						}
						alert("删除成功");
					} else {
						alert("删除失败："+result.message);
					}
				});
			}
		});
	}

	function getCheckedRows() {
		var trs = [];
		$("#itemNotificationBody").find("tr").each(function(){
			var $tr = $(this);
			if ($tr.find("td .deleteItemNCheckbox").prop('checked')) {
				trs.push($tr);
			}
		});
		return trs;
	}

	function bindMailNotification(enabled){
		var rows = [];
		var trs = getCheckedRows();
		for (var i in trs) {
			var $tr = trs[i];
			var itemN = {itemId : $tr.attr("itemId"),
					realmId : $tr.attr("realmId"),
					isInverted : $tr.attr("isInverted"),
					petSpeciesId : $tr.attr("data-petSpeciesId"),
					petBreedId : $tr.attr("data-petBreedId"),
					bonusList : $tr.attr("data-bonusList"),
					emailNotification: enabled};
			rows.push(itemN);
		}
		BN.Resource.updateEmailNotifications(JSON.stringify(rows),function(result){
			if (result.code === 0) {
				for (var i in trs) {
					var $tr = trs[i];
					$tr.find(".emailN").html((enabled === 0 ? "关闭" : "启用"));
				}
				alert("成功");
			} else {
				alert("失败："+result.message);
			}
		});
	}

	function bindEnable(){
		$("#enableMailBtn").click(function() {
			bindMailNotification(1);
		});
	}

	function bindDisable(){
		$("#disableMailBtn").click(function() {
			bindMailNotification(0);
		});
	}

	!function() {
		$("#setItemNForm").hide();
		BN.Resource.getUserRealms(function(userRealms) {
			if (userRealms.code === -1) {
				$("#msg").html("服务器数据加载失败");
			} else {
				var options = "<option value='0'>所有服务器</option>";
				for ( var i in userRealms) {
					var realm = userRealms[i];
					var realmName = BN.Realm.getRealmById(realm.realmId).connected;
					options += "<option value='" + realm.realmId + "'>" + realmName
							+ "</option>";
				}
				$("#realmSlt").html(options);
			}
		});
		BN.Resource.getUserItemNotifications(function(itemNotifications) {
			if (itemNotifications.code === -1) {
				alert("加载数据出错：" + itemNotifications.message);
			} else {
				$("#exportBtn").attr("download", "items.js")
					.attr("href", "data:text/js;charset=UTF-8," + JSON.stringify(itemNotifications));
				count = 0;
				var tableRows = "";
				for ( var i in itemNotifications) {
					var itemN = itemNotifications[i];
					tableRows += generateRow(itemN);
				}
				$("#itemNotificationBody").append(tableRows);
				bindUpdate();
				bindDelete();
				bindEnable();
				bindDisable();
			}
		});	
		
		$("#selectAllCheckbox").change(function() {
			if (this.checked) {
				$(".deleteItemNCheckbox").prop('checked', true);
			} else {
				$(".deleteItemNCheckbox").prop('checked', false);
			}
		});

	}();

	$("#searchBtn").click(function() {
		$("#itemSelectDiv").html("");
		var itemName = $("#itemInput").val();
		if (itemName === "") {
			$("#msg").html("请输入物品名");
		} else {
			$("#setItemNForm").hide();				
			var items = BN.Resource.getItemsByName(itemName);
			if (!items) {
				$("#msg").html("查询出错：" + items.message);
			} else if (items.length === 0) {
				$("#msg").html("找不到物品：" + itemName);
			} else {
				var html = "<select id='itemSlt' class='form-control'>";
				for ( var i in items) {
					var item = items[i];
					html += "<option value='" + item.id + "' data-itemlevel='"+item.level+"'>" + item.name + "</option>";
					var bonusHtml = "<select class='itemBonusSlt form-control' data-petspeciesid='"+item.petSpeciesId+"'>";
					for (var j in item.bonusLists) {
						var bonus = item.bonusLists[j];
						bonusHtml += "<option value='" + bonus + "'>" + Bnade.getBonusDesc(bonus, item.level) + "</option>";
					}
					for (var j in item.petStatsList) {
						var petStats = item.petStatsList[j];
						bonusHtml += "<option value='" + petStats.breedId + "'>类型"+petStats.breedId+" 生命值"+petStats.health+" 攻击"+petStats.power+" 速度"+petStats.speed+"</option>";
					}
					bonusHtml += "</select>";
					$("#itemSelectDiv").append(bonusHtml);
				}
				html += "</select>";
				$("#itemSelectDiv").prepend(html);
				$("#setItemNForm").show();
				$("#itemSlt").change(function(){
					var index = $(this).get(0).selectedIndex;
					$(".itemBonusSlt").hide().eq(index).show();
	           });
	           $("#itemSlt").change();
			}
		}
	});

	$("#addItemBtn").click(function() {
		var $goldInput = $("#goldInput").val();
		if ($goldInput === "") {
			$("#msg").html("请输入金币数");
		} else {
			var itemId = $("#itemSlt").val();
			var itemName = $("#itemSlt").find("option:selected").text();
			var itemLevel = $("#itemSlt").find("option:selected").attr("data-itemlevel");
			var realmId = $("#realmSlt").val();
			var $itemBonusSlt = $(".itemBonusSlt").eq($("#itemSlt").get(0).selectedIndex);
			if (itemId == 82800) {
				var petSpeciesId = $itemBonusSlt.attr("data-petspeciesid");
				var petBreedId = $itemBonusSlt.val();
				var bonusList = "";
			} else {
				var petSpeciesId = 0;
				var petBreedId = 0;
				var bonusList = $itemBonusSlt.val();
			}
			var realmName = BN.Realm.getRealmById(realmId).connected;
			var isInverted = $("#isInvertedSlt").val();
			var itemN = {
				itemId : itemId,
				petSpeciesId : petSpeciesId,
				petBreedId : petBreedId,
				itemName: itemName,
				itemLevel : itemLevel,
				bonusList : bonusList,
				realmId : realmId,
				emailNotification: 1,
				isInverted : isInverted,
				price : 10000 * $goldInput
			};
			var result = BN.Resource.addUserItemNotification(itemN);
			if (result.code === 0) {
				$("#msg").html(itemN.itemName + "添加成功");
				$("#itemNotificationBody").append(generateRow(itemN));
				bindUpdate();
				bindDelete();
			} else {
				$("#msg").html("添加失败：" + result.message);
			}
		}
	});
	</script>
</body>
</html>