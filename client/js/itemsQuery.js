$(document).ready(function(){
	!function() {
		// 加载物品列表
		var jsonArr=JSON.parse(localStorage.getItem(BnadeLocalStorage.lsItems.item.key));
		var itemList = "";
		for(var i in jsonArr) {
			itemList += jsonArr[i] + "\n";
		}
		$("#itemList").val(itemList);
		// 去掉回车搜索
		$("body").unbind("keydown");
	}(); 
	$("#queryBtn").click(function(){
		var realm = $("#realm").val();
		var itemList = $("#itemList").val();
		if (realm === "" || itemList === "") {
			$('#msg').html("服务器名或物品列表不能为空");
		} else {
			var itemArr = itemList.split("\n");
			var nameList = "";
			for (var i in itemArr) {
				var name = itemArr[i].trim();
				if (name !== "") {
					if (nameList.length > 0) {
						nameList += "_";
					}
					nameList += name;
				}
			}
			itemsQuery(realm, nameList);
		}
	});
	function itemsQuery(realm, nameList) {
		$('#msg').html("开始查询，请稍等...");
		$.get("wow/auction/realm/" + encodeURIComponent(realm) + "/item/names/" + encodeURIComponent(nameList),function(data, status){
			if (data.code==201) {
				$('#msg').html("查询出错:" + data.errorMessage);
			} else {
				var tblHtml="<table class='table table-striped'><thead><tr><th>#</th><th>物品名</th><th>一口价</th><th>拍卖玩家</th><th>剩余时间</th><th>物品总数量</th><th>更新时间</th></tr></thead><tbody>";
				for (var i in data) {
					var itemArr = data[i];
					var url = window.location.protocol + "//" + window.location.host + "/itemQuery.html?itemName=" + itemArr[0] + "&realm=" + realm;
					tblHtml+="<tr><td>"+(parseInt(i)+1)+"</td><td><a href='" + url + "' target='_blank'>"+itemArr[0]+"</a></td><td>"+Bnade.getGold(itemArr[1])+"</td><td>"+itemArr[2]+"</td><td>"+leftTimeMap[itemArr[5]]+"</td><td>"+itemArr[3]+"</td><td>"+new Date(itemArr[4]).format("MM-dd hh:mm:ss")+"</td></tr>";
				}
       			tblHtml+="</tbody></table>";
				$('#resultList').html(tblHtml);
				$('#msg').html("");
			}
		}).fail(function() {
			$('#msg').html("查询出错");
	    });
	}
});