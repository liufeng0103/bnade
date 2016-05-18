$(document).ready(function() {
	!function(){
		$('#worthItemContainer').html("加载数据,请稍等...");
		$.get("wow/item/rule/matchs", function(data) {
       		if (data.length === 0) {
       			$('#worthItemContainer').html("数据未找到");
       		} else {     
       			data.sort(function(a, b){
       				return a.realmId - b.realmId;
       			});
       			var tblHtml = "<table class='table table-striped'><thead><tr><th>#</th><th>服务器</th><th>物品ID</th><th>物品名</th><th>价格</th><th>更新时间</th></tr></thead><tbody>";
       			for (var i in data) {
       				var item = data[i];       				  				
       				tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+Realm.getConnectedById(item.realmId)+"</td><td>"+item.itemId+"</td><td>"+item.name+"</td><td>"+Bnade.getGold(item.buyout)+"</td><td>"+new Date(item.lastModified).format("MM-dd hh:mm:ss")+"</td></tr>";
       			}
       			tblHtml += "</tbody></table>";
       			$('#worthItemContainer').html(tblHtml);
       		}
       	}).fail(function() {
			$("#worthItemContainer").html("数据加载出错");
	    }); 
	}();
});