$(document).ready(function() {
	$("#queryBtn").click(function() {
		query();
	});
	function query() {	
		var realm = $("#realm").val();
		if (realm !== "") {
			$('#msg').html("正在查询,请稍等...");
			loadTopAuc(realm);
			loadTopPrice(realm);
		} else {
			$('#msg').html("服务器名不能为空");
		}		
	}
	function loadTopAuc(realm){
		var contentDiv="topAuc";
		var url="wow/auction/top/aucquantity/realm/"+realm;
		loadTop(realm, url, contentDiv);
	}
	function loadTopPrice(realm){
		var contentDiv="topPrice";
		var url="wow/auction/top/totalprice/realm/"+realm;
		loadTop(realm, url, contentDiv);
	}
	function loadTop(realm, url, contentDiv) {
		$.get(url, function(data) {
			if (data.code === 201) {
				$('#msg').html("加载数据失败:"+data.errorMessage);								
			} else {	
				BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.realm.key, realm);					
				var tblHtml="<table class='table table-striped'><thead><tr><th>#</th><th>玩家</th><th>拍卖数量</th><th>拍卖总价值</th></tr></thead><tbody>";
       			for (var i in data) {
       				var d = data[i];
       				var total = Bnade.getGold(d.total);
       				tblHtml+="<tr><td>"+(parseInt(i)+1)+"</td><td>"+d.owner+"</td><td>"+d.aucQuantity+"</td><td>"+total+"</td></tr>";
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