$(document).ready(function() {
	$("#queryBtn").click(function() {
		query();
	});
	function query() {	
		var realm = $("#realm").val();
		if (realm !== "") {
			var realmId = Realm.getIdByName(realm);
			if (realmId > 0) {
				$('#msg').html("正在查询,请稍等...");
				loadTopWorth(realmId);
				loadTopCategory(realmId);
				loadTopQuantity(realmId);
			} else {
				$('#msg').html("服务器名不正确：" + realm);
			}			
		} else {
			$('#msg').html("服务器名不能为空");
		}		
	}
	function loadTopWorth(realmId){
		var contentDiv="topWorth";
		var url="wow/auction/realm/" + realmId + "/owner/item/worth/top";
		loadTop(realmId, url, contentDiv);
	}
	function loadTopCategory(realmId){
		var contentDiv="topCategory";
		var url="wow/auction/realm/" + realmId + "/owner/item/category/top";
		loadTop(realmId, url, contentDiv);
	}
	function loadTopQuantity(realmId){
		var contentDiv="topQuantity";
		var url="wow/auction/realm/" + realmId + "/owner/item/quantity/top";
		loadTop(realmId, url, contentDiv);
	}
	function loadTop(realmId, url, contentDiv) {
		$.get(url, function(data) {
			if (data.length === 0) {
				$('#msg').html("数据没查到");								
			} else {								
				var tblHtml="<table class='table table-striped'><thead><tr><th>#</th><th>玩家</th><th>总价值</th><th>种类数</th><th>总数量</th></tr></thead><tbody>";
       			for (var i in data) {
       				var item = data[i];
       				var worth = Bnade.getGold(item.worth);
       				tblHtml+="<tr><td>"+(parseInt(i)+1)+"</td><td><a href='/ownerQuery.jsp?realm="+encodeURIComponent($("#realm").val())+"&owner="+encodeURIComponent(item.owner)+"'  target='_blank'>"+item.owner+"</a></td><td>"+worth+"</td><td>"+item.itemCategeryCount+"</td><td>"+item.quantity+"</td></tr>";
       			}
       			tblHtml+="</tbody></table>";
       			$('#'+contentDiv).html(tblHtml);				
			}
			$('#msg').html("");
		}).fail(function() {
			$('#msg').html("查询出错");
	    });
	}
});