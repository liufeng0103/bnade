"use strict";

var API_HOST = "https://api.bnade.com";


function loadTop(realmId, data, contentDiv) {
	var tblHtml="<table class='table table-striped'><thead><tr><th>#</th><th>玩家</th><th>总价值</th><th>种类数</th><th>总数量</th></tr></thead><tbody>";
	for (var i in data) {
		var item = data[i];
		var worth = Bnade.getGold(item.worth);
		tblHtml+="<tr><td>"+(parseInt(i)+1)+"</td><td><a href='/ownerQuery.jsp?realm="+encodeURIComponent($("#realm").val())+"&owner="+encodeURIComponent(item.owner)+"'  target='_blank'>"+item.owner+"</a></td><td>"+worth+"</td><td>"+item.itemCategeryCount+"</td><td>"+item.quantity+"</td></tr>";
	}
	tblHtml+="</tbody></table>";
	$('#'+contentDiv).html(tblHtml);	
}

function loadTopWorth(realmId, data) {
	var tblHtml="<table class='table table-striped'><thead><tr><th>#</th><th>玩家</th><th>总价值</th></tr></thead><tbody>";
	for (var i in data) {
		var topOwner = data[i];
		if (topOwner.type == 1) {
			var worth = Bnade.getGold(topOwner.value);
			tblHtml+="<tr><td>"+(parseInt(i)+1)+"</td><td><a href='/ownerQuery.jsp?realm="+encodeURIComponent($("#realm").val())+"&owner="+encodeURIComponent(topOwner.owner)+"'  target='_blank'>"+topOwner.owner+"</a></td><td>"+worth+"</td></tr>";
		}
	}
	tblHtml+="</tbody></table>";
	$('#topWorth').html(tblHtml);	
}

function loadTopCategory(realmId, data) {
	var tblHtml="<table class='table table-striped'><thead><tr><th>#</th><th>玩家</th><th>种类数</th></tr></thead><tbody>";
	for (var i in data) {
		var topOwner = data[i];
		if (topOwner.type == 2) {
			tblHtml+="<tr><td>"+(parseInt(i)+1)+"</td><td><a href='/ownerQuery.jsp?realm="+encodeURIComponent($("#realm").val())+"&owner="+encodeURIComponent(item.owner)+"'  target='_blank'>"+topOwner.owner+"</a></td><td>"+topOwner.value+"</td></tr>";
		}
	}
	tblHtml+="</tbody></table>";
	$('#topCategory').html(tblHtml);	
}

function loadTopQuantity(realmId, data) {
	var tblHtml="<table class='table table-striped'><thead><tr><th>#</th><th>玩家</th><th>总数量</th></tr></thead><tbody>";
	for (var i in data) {
		var topOwner = data[i];
		if (topOwner.type == 3) {
			tblHtml+="<tr><td>"+(parseInt(i)+1)+"</td><td><a href='/ownerQuery.jsp?realm="+encodeURIComponent($("#realm").val())+"&owner="+encodeURIComponent(item.owner)+"'  target='_blank'>"+topOwner.owner+"</a></td><td>"+topOwner.value+"</td></tr>";
		}
	}
	tblHtml+="</tbody></table>";
	$('#topQuantity').html(tblHtml);	
}

function query() {	
	var realm = $("#realm").val();
	if (realm !== "") {
		var realmId = Realm.getIdByName(realm);
		if (realmId > 0) {
			$('#msg').text("正在查询,请稍等...");
			var url = API_HOST + "/auctions/top-owners?realmId=" + realmId;
			$.ajax({
				url: url,
				crossDomain: true == !(document.all), // 解决IE9跨域访问问题
				success: function (data) {
					loadTopWorth(realmId, data);
					loadTopCategory(realmId, data);
					loadTopQuantity(realmId, data);
				},
				error: function (xhr) {
					if (xhr.status === 404) {
						$('#msg').text("最后一页");
					} else if (xhr.status === 500) {
						$('#msg').text("服务器错误");
					} else {
						$('#msg').text("未知错误");
					}
				},
			});
		} else {
			$('#msg').text("服务器名不正确：" + realm);
		}			
	} else {
		$('#msg').text("服务器名不能为空");
	}		
}

$("#queryBtn").click(function() {
	query();
});