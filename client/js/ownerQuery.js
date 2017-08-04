var API_HOST = "https://api.bnade.com";

/**
 * 把同一卖家，一口价相同的同一类物品整合到一起
 */
function foldAuctionsByOwnerAndBuyoutAndBonusList(data) {
	if (data.length <= 1) {
		return data;
	}
	data.sort(function(a, b) {
		return a.buyout / a.quantity - b.buyout / b.quantity;
	});
	var result = [];
	var preAuc = data[0];
	for (var i = 1, j = data.length; i < j; i++) {
		var auc = data[i];
		if (preAuc.itemId === auc.itemId &&
				preAuc.owner === auc.owner && //同一卖家
				Bnade.getGold(preAuc.buyout / preAuc.quantity) === Bnade.getGold(auc.buyout / auc.quantity) && // 一口单价一样
				preAuc.bonusList === auc.bonusList) { // 类型一样
			preAuc.bid += auc.bid;
			preAuc.buyout += auc.buyout;
			preAuc.quantity += auc.quantity;
		} else {
			result.push(preAuc);
			preAuc = auc;
		}
		if (i === data.length - 1) {
			result.push(preAuc);
		}
	}
	return result;
}

/**
 * 玩家拍卖物品查询
 */
var ownerAuction = {
	/**
	 * 页面初始化
	 */
	initialize : function() {
		// 通过url查询
		var realm = getUrlParam('realm');
		var owner = getUrlParam('owner');			
		if (realm !== null && realm !== "" && owner !== null && owner !== "") {
			$('#realm').val(realm);
			$('#owner').val(owner);
			ownerAuction.search(realm, owner);
		}
	},
	search : function(realm, owner) {
		if (realm === "" || owner === "") {
			$("#ownerItemsContainer").text("服务器或玩家名不能为空");
		} else {
			var realmId = Realm.getIdByName(realm);
			if (realmId > 0) {
				$("#ownerItemsContainer").text("正在查询，请稍等...");
				var url = API_HOST + "/auctions?realmId=" + encodeURIComponent(realmId) + "&owner=" + encodeURIComponent(owner);
				$.ajax({
					url : url,
					crossDomain : true == !(document.all), // 解决IE9跨域访问问题
					success : function(data) {
						BnadeLocalStorage.addItem("queryRealms", realm);
						BnadeLocalStorage.refresh();
						var result = foldAuctionsByOwnerAndBuyoutAndBonusList(data);
						var tblHtml = "<table class='table table-hover'><thead><tr><th>#</th><th>ID</th><th>物品名</th><th>竞价</th><th>一口价</th><th>数量</th><th>单价</th><th>剩余时间</th><th>说明</th></tr></thead><tbody>";
						for (var i in result) {
							var auc = result[i];
							var name;
							var desc;
							if (auc.itemId !== 82800) {
								name = auc.itemName;
								desc = Bnade.getBonusDesc(auc.bonusList, auc.itemLevel);
							} else {
								name = "宠物笼(" + auc.petName + ")";
								desc = "装笼宠物";
							}
							tblHtml += "<tr><td>"+(parseInt(i) + 1)+"</td><td>"+auc.itemId+"</td><td><img src='http://content.battlenet.com.cn/wow/icons/18/" + auc.itemIcon + ".jpg'/> " + name + "</td><td>" + Bnade.getGold(auc.bid) + "</td><td>"+Bnade.getGold(auc.buyout)+"</td><td>"+auc.quantity+"</td><td>"+Bnade.getGold(auc.buyout/auc.quantity)+"</td><td>"+leftTimeMap[auc.timeLeft]+"</td><td>"+desc+"</td></tr>";
						}
						tblHtml += "</tbody></table>";
		       			$("#ownerItemsContainer").html(tblHtml);
		       			sortableTable();
		       			$('th').css("cursor", "pointer");
					},
					error : function(xhr) {
						if (xhr.status === 404) {
							$("#ownerItemsContainer").text("数据找不到");
						} else if (xhr.status === 500) {
							$("#ownerItemsContainer").text("服务器错误");
						} else {
							$("#ownerItemsContainer").text("未知错误");
						}
					},
				});
			} else {
				$("#ownerItemsContainer").text("找不到服务器名：" + realm);
			}    		
		}
	}
};

ownerAuction.initialize();

$('#queryBtn').click(function() {
	ownerAuction.search($('#realm').val(), $('#owner').val());
});

