"use strict";

var API_HOST = "https://api.bnade.com";

var page = 0;
/**
 * 加载市场价排行
 * 
 * @param {*} page page从0开始
 */
function loadItemMarket(page) {
	var size = 50;
	$('#msg').text("正在加载，请稍等...");
	var url = API_HOST + "/items/search-statistics?type=2&page=" + page;
	$.get(url, function (data) {
		var rowsHtml = '';
		for (var i in data) {
			var item = data[i];
			var num = +i + 1 + page * size;
			rowsHtml += '<tr><td>' + num + '</td><td>' + item.itemId + '</td><td><img src="http://content.battlenet.com.cn/wow/icons/18/' + item.itemIcon + '.jpg"/> <a href="/itemQuery.jsp?itemName=' + encodeURIComponent(item.itemName) + '" target="_blank">' + item.itemName + '</a></td><td>' + item.searchCount + '</td></tr>';
		}
		$('#itemMarketTbl tbody').html(rowsHtml);
		$('#msg').text("");

		// 按钮禁用检测
		if (page === 0) {
			$('#prePageBtn').attr('disabled', 'disabled');
		} else {
			$('#prePageBtn').removeAttr('disabled');
		}
		if (data.length !== size) {
			$('#nexPageBtn').attr('disabled', 'disabled');
		} else {
			$('#nexPageBtn').removeAttr('disabled');
		}
	}).fail(function () {
		$('#msg').text("查询出错");
	});
}

loadItemMarket(page);

$('#prePageBtn').click(function() {
	loadItemMarket(--page);
});

$('#nextPageBtn').click(function() {
	loadItemMarket(++page);
});