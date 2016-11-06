"use strict";

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
	//var isInvertedOpt = itemN.isInverted === 0 ? "<option value='0' selected='selected'>低于</option><option value='1'>高于</option>" : "<option value='0' selected='selected'>低于</option><option value='1' selected='selected'>高于</option>";
	//var isInvertedCol = "<select class='form-control input-sm'>" + isInvertedOpt + "</select>";
	var row = "<tr realmId='"+itemN.realmId+"' itemId='"+itemN.itemId+"' isInverted='"+itemN.isInverted+"'><td>"
			+ BN.Realm.getRealmById(itemN.realmId).connected
			+ "</td><td>"
			+ itemN.itemName
			+ "</td><td class='emailN'>"
			+ (itemN.emailNotification === 0 ? "关闭" : "启用")
			+ "</td><td>"
			+ (itemN.isInverted === 0 ? "低于" : "高于")
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
				var $tr = $(this);
				var itemN = {itemId : $tr.attr("itemId"),
						realmId : $tr.attr("realmId"),
						isInverted : $tr.attr("isInverted")};
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
			var options = "";
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

$("#searchBtn").click(
		function() {
			var itemName = $("#itemInput").val();
			if (itemName === "") {
				$("#msg").html("请输入物品名");
			} else {
				$("#setItemNForm").hide();				
				var items = BN.Resource.getItemsByName(itemName);
				if (items.code === -1) {
					$("#msg").html("查询出错：" + items.message);
				} else if (items.length === 0) {
					$("#msg").html("找不到物品：" + itemName);
				} else {
					var options = "";
					for ( var i in items) {
						var item = items[i];
						options += "<option value='" + item.id + "'>"
								+ item.name + "</option>";
					}
					$("#itemSlt").html(options);
					$("#setItemNForm").show();
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
		var realmId = $("#realmSlt").val();
		var realmName = BN.Realm.getRealmById(realmId).connected;
		var isInverted = $("#isInvertedSlt").val();
		var itemN = {
			itemId : itemId,
			itemName: itemName,
			realmId : realmId,
			emailNotification: 1,
			isInverted : isInverted,
			price : 10000 * $goldInput
		};
		var result = BN.Resource.addUserItemNotification(itemN);
		var isInvertedOpt = isInverted === 0 ? "<option value='0' selected='selected'>低于</option><option value='1'>高于</option>"
				: "<option value='0' selected='selected'>低于</option><option value='1' selected='selected'>高于</option>";
		var isInvertedCol = "<select class='form-control'>"
				+ isInvertedOpt + "</select>";
		if (result.code === 0) {
			$("#msg").html("添加成功");
			$("#itemNotificationBody").append(generateRow(itemN));
			bindUpdate();
			bindDelete();
		} else {
			$("#msg").html("添加失败：" + result.message);
		}
	}
});