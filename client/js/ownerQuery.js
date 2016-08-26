$(document).ready(function() {   
	$('#queryBtn').click(function() {
		query();
	});
	function query() {
		$("#ownerItemsContainer").html("正在查询,请稍等...");
		var realm = $('#realm').val();
		var owner = $('#owner').val();
		if (realm === "" || owner === "") {
			$("#ownerItemsContainer").html("服务器或玩家名不能为空");
		} else {
			var realmId = Realm.getIdByName(realm);
			if (realmId > 0) {
				$.get("wow/auction/realm/" + encodeURIComponent(realmId) + "/owner/" + encodeURIComponent(owner), function(data) {
		       		if (data.length === 0) {
		       			$("#ownerItemsContainer").html("找不到玩家拍卖的物品");
		       		} else {	
		       			data.sort(function(a,b){
		       				return a[4]/a[5] - b[4]/b[5];
		       			});
		       			var tblHtml = "<table class='table table-striped'><thead><tr><th>#</th><th>ID</th><th>物品名</th><th>竞价</th><th>一口价</th><th>数量</th><th>单价</th><th>剩余时间</th><th>更新时间</th><th>英雄榜</th></tr></thead><tbody>";
		       			for (var i in data) {
		       				var item = data[i];
		       				var bid = Bnade.getGold(item[3]);
		       				var buyout = Bnade.getGold(item[4]);
		       				tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+item[0]+"</td><td>"+item[1]+"</td><td>"+bid+"</td><td>"+buyout+"</td><td>"+item[5]+"</td><td>"+toDecimal(buyout/item[5])+"</td><td>"+leftTimeMap[item[6]]+"</td><td>"+new Date(item[7]).format("MM-dd hh:mm:ss")+"</td><td><a href='http://www.battlenet.com.cn/wow/zh/character/" + encodeURIComponent(item[2]) + "/" + encodeURIComponent(owner) + "/simple' target='_blank'>"+item[2]+"</a></td></tr>";
		       			}
		       			tblHtml += "</tbody></table>";
		       			$("#ownerItemsContainer").html(tblHtml);		       			
		       		}
				}).fail(function() {
					$("#ownerItemsContainer").html("数据加载出错");
			    });
			} else {
				$("#ownerItemsContainer").html("找不到服务器名：" + realm);
			}    		
		}
	}
	function queryByUrl() {
		var realm = getUrlParam('realm');
		var owner = getUrlParam('owner');			
		if (owner !== null && owner !== "") {
			$('#realm').val(realm);
			$('#owner').val(owner);
			$("#queryBtn").click();
		}		
	}
	!function() {
		queryByUrl();
	}();
});