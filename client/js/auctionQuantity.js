var API_HOST = "https://api.bnade.com";

/**
 * 生成table的header和body
 */
var tableGenerator = {
	/**
	 * 生成table的header
	 * @param columns 字符串数组
	 * @returns {String}
	 */
	header : function(columns) {
		var header = "<thead><tr>";
		for (var i in columns) {
			header += "<th>" + columns[i] + "</th>";
		}
		header += "</tr></thead>";
		return header;
	},
	/**
	 * 生成table的body
	 * @param dataSet 二维数组
	 * @returns {String}
	 */
	body : function(dataSet) {
		var body = "<tbody>";
		for (var i in dataSet) {
			body += "<tr>";
			for (var j in dataSet[i]) {
				body += "<td>" + dataSet[i][j] + "</td>";
			}
			body += "</tr>";
		}
		body += "</tbody>";
		return body
	}
}

$(function() {
	$('#tableContainer').text("加载数据,请稍等...");
	$.get(API_HOST + "/realms", function(data) {		
		
		// 排序，人数有高到低
		data.sort(function(a, b) { 
			return -(a.ownerQuantity - b.ownerQuantity);
		});
		
		// 构建数据
		var columns = ["#", "服务器", "类型", "拍卖总数", "卖家数", "物品种类", "更新间隔(分钟)", "更新时间"];
		var dataSet = [];
		for (var i in data) {
			var realm = data[i];
			var num =parseInt(i) + 1;
			var realmName = Realm.getConnectedById(realm.id);
			var typeClass = realm.type === "pve" ? "text-primary" : "text-danger";
			var typeColumn = "<span class='" + typeClass + "'>" + realm.type + "</span>";
			dataSet.push( [num, realmName, typeColumn, realm.auctionQuantity, realm.ownerQuantity, realm.itemQuantity, parseInt(realm.interval/60000), new Date(realm.lastModified).format("MM-dd hh:mm:ss")]);
		}
		
		$('#tableContainer').html('<table id="realmTbl" class="table table-hover table-condensed"></table>');
		$("#realmTbl").html(tableGenerator.header(columns) + tableGenerator.body(dataSet));
		
		sortableTable();
		$('th').css("cursor", "pointer");
		
		// 保存到localStorage
		store.set("realms", data);
	}).fail(function() {
		$('#tableContainer').text("查询出错");
	});
});