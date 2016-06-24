$(document).ready(function() {
	!function(){
		$('#auctionQuantityContainer').html("加载数据,请稍等...");
		$.get("wow/auction/realms/summary", function(data) {
       		if (data.length === 0) {
       			$('#auctionQuantityContainer').html("数据未找到");
       		} else {
       			data.sort(function(a,b){
       				return -(a.auctionQuantity - b.auctionQuantity);
       			});
       			var tblHtml = "<table class='table table-striped'><thead><tr><th>#</th><th>服务器</th><th>类型</th><th>拍卖总数量</th><th>拍卖行玩家数</th><th>物品种类</th><th>更新时间</th></tr></thead><tbody>";
       			for (var i in data) {
       				var realmAuction = data[i];
       				var realm = Realm.getConnectedById(realmAuction.id);
       				var type = realmAuction.type;
       				var realmTypeClass = "text-danger";
       				if (type === 'pve') {
       					realmTypeClass = "text-primary";
       				}
       				tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+realm+"</td><td class='"+realmTypeClass+"'>"+type+"</td><td>"+realmAuction.auctionQuantity+"</td><td>"+realmAuction.playerQuantity+"</td><td>"+realmAuction.itemQuantity+"</td><td>"+new Date(realmAuction.lastModified).format("MM-dd hh:mm:ss")+"</td></tr>";
       			}
       			tblHtml += "</tbody></table>";
       			$('#auctionQuantityContainer').html(tblHtml);
       		}
       	}).fail(function() {
			$("#auctionQuantityContainer").html("数据加载出错");
	    }); 
	}();
});