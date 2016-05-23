$(document).ready(function() {
	!function(){
		$('#itemRuleContainer').html("加载数据,请稍等...");
		$.get("wow/item/rules", function(data) {
       		if (data.length === 0) {
       			$('#itemRuleContainer').html("数据未找到");
       		} else {       			
       			var tblHtml = "<table class='table table-striped'><thead><tr><th>#</th><th>ID</th><th>物品名</th><th>低于价格</th><th>高于价格</th></tr></thead><tbody>";
       			for (var i in data) {
       				var item = data[i];       				  				
       				tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+item.itemId+"</td><td>"+item.name+"</td><td>"+Bnade.getGold(item.ltBuy)+"</td><td>"+Bnade.getGold(item.gtBuy)+"</td></tr>";
       			}
       			tblHtml += "</tbody></table>";
       			$('#itemRuleContainer').html(tblHtml);
       		}
       	}).fail(function() {
			$("#itemRuleContainer").html("数据加载出错");
	    }); 
	}();
});