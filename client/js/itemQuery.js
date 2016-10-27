var itemQuery = itemQuery || {};

itemQuery.getItemAvgPrice = function(realmId, itemId){
	var price = 0;
	$.ajax({url:"wow/auction/past/realm/" + realmId + "/item/" + itemId,async:false,success:function(result){
		if (result.length > 0) {
			var calData = [];
			for(var i in result){
				var item=result[i];
				calData[i] = result[i][0];
			}
			var calResult = Bnade.getResult(calData);
			price = Bnade.getGold(calResult.avg);				
		}					
	}});
	return price;
};

itemQuery.getItemCreatedByPrice = function(realmId, itemCreatedBy) {
	var price = 0;
	for (var i in itemCreatedBy.reagent) {
		var reagent = itemCreatedBy.reagent[i];
		var reagentPrice = itemQuery.getItemAvgPrice(realmId, reagent.itemId);
		price += reagentPrice * reagent.count;
	}
	return price;
};

itemQuery.getItemCreatedBy = function(itemId) {
	var itemCreatedBy;
	$.ajax({url:"wow/item/createdBy/" + itemId,async:false,success:function(result){
		if (result.length > 0) {
			itemCreatedBy = result;			
		}				
	}});
	return itemCreatedBy;
};

itemQuery.isSameItem = function(item1,item2) {
	var isSame = false;
	if(item1 && item2 && item1[0] == item2[0] && Bnade.getGold(item1[3]/item1[4]) == Bnade.getGold(item2[3]/item2[4])) {
		isSame = true;
	}
	return isSame;
};

var sortColumn = "sort1";
var orderByDesc = true;
var gblData = [];
var gblItemId = 0;
var currentItem;

var BonusGroups1 = [ {
	"id" : 1,
	"desc" : "快刀之 全能 爆击",
	"bonusGroups" : [ {
		"bl" : "1676",
		"desc" : "245全能 614暴击"
	}, {
		"bl" : "1677",
		"desc" : "307全能 552暴击"
	}, {
		"bl" : "1678",
		"desc" : "368全能 491暴击"
	}, {
		"bl" : "1679",
		"desc" : "429全能 429暴击"
	}, {
		"bl" : "1680",
		"desc" : "491全能 368暴击"
	}, {
		"bl" : "1681",
		"desc" : "552全能 307暴击"
	}, {
		"bl" : "1682",
		"desc" : "614全能 245暴击"
	}, ]
}, {
	"id" : 2,
	"desc" : "无双之 爆击 精通",
	"bonusGroups" : [ {
		"bl" : "1683",
		"desc" : "614暴击 245精通"
	}, {
		"bl" : "1684",
		"desc" : "552暴击 307精通"
	}, {
		"bl" : "1685",
		"desc" : "491暴击 368精通"
	}, {
		"bl" : "1686",
		"desc" : "429暴击 429精通"
	}, {
		"bl" : "1687",
		"desc" : "368暴击 491精通"
	}, {
		"bl" : "1688",
		"desc" : "307暴击 552精通"
	}, {
		"bl" : "1689",
		"desc" : "245暴击 614精通"
	}, ]
}, {
	"id" : 3,
	"desc" : "燎火之 爆击 急速",
	"bonusGroups" : [ {
		"bl" : "1690",
		"desc" : "614暴击 245急速"
	}, {
		"bl" : "1691",
		"desc" : "552暴击 307急速"
	}, {
		"bl" : "1692",
		"desc" : "491暴击 368急速"
	}, {
		"bl" : "1693",
		"desc" : "429暴击 429急速"
	}, {
		"bl" : "1694",
		"desc" : "368暴击 491急速"
	}, {
		"bl" : "1695",
		"desc" : "307暴击 552急速"
	}, {
		"bl" : "1696",
		"desc" : "245暴击 614急速"
	}, ]
}, {
	"id" : 4,
	"desc" : "灼光之 精通 急速",
	"bonusGroups" : [ {
		"bl" : "1697",
		"desc" : "245精通 614急速"
	}, {
		"bl" : "1698",
		"desc" : "307精通 552急速"
	}, {
		"bl" : "1699",
		"desc" : "368精通 491急速"
	}, {
		"bl" : "1700",
		"desc" : "429精通 429急速"
	}, {
		"bl" : "1701",
		"desc" : "491精通 368急速"
	}, {
		"bl" : "1702",
		"desc" : "552精通 307急速"
	}, {
		"bl" : "1703",
		"desc" : "614精通 245急速"
	}, ]
}, {
	"id" : 5,
	"desc" : "曙光之 全能 急速",
	"bonusGroups" : [ {
		"bl" : "1704",
		"desc" : "245全能 614急速"
	}, {
		"bl" : "1705",
		"desc" : "307全能 552急速"
	}, {
		"bl" : "1706",
		"desc" : "368全能 491急速"
	}, {
		"bl" : "1707",
		"desc" : "429全能 429急速"
	}, {
		"bl" : "1708",
		"desc" : "491全能 368急速"
	}, {
		"bl" : "1709",
		"desc" : "552全能 307急速"
	}, {
		"bl" : "1710",
		"desc" : "614全能 245急速"
	}, ]
}, {
	"id" : 6,
	"desc" : "谐律之 全能 精通",
	"bonusGroups" : [ {
		"bl" : "1711",
		"desc" : "245全能 614精通"
	}, {
		"bl" : "1712",
		"desc" : "307全能 552精通"
	}, {
		"bl" : "1713",
		"desc" : "368全能 491精通"
	}, {
		"bl" : "1714",
		"desc" : "429全能 429精通"
	}, {
		"bl" : "1715",
		"desc" : "491全能 368精通"
	}, {
		"bl" : "1716",
		"desc" : "552全能 307精通"
	}, {
		"bl" : "1717",
		"desc" : "614全能 245精通"
	}, ]
}, {
	"id" : 7,
	"desc" : "屠夫之 爆击",
	"bonusGroups" : [ {
		"bl" : "1718",
		"desc" : "859爆击"
	}, ]
}, {
	"id" : 8,
	"desc" : "应变之 全能",
	"bonusGroups" : [ {
		"bl" : "1719",
		"desc" : "859全能"
	}, ]
}, {
	"id" : 9,
	"desc" : "焦躁之 急速",
	"bonusGroups" : [ {
		"bl" : "1720",
		"desc" : "859急速"
	}, ]
}, {
	"id" : 10,
	"desc" : "专擅之 精通",
	"bonusGroups" : [ {
		"bl" : "1721",
		"desc" : "859精通"
	}, ]
}, ];

var BonusGroups2 = [ {
	"id" : 1,
	"desc" : "快刀之 全能 爆击",
	"bonusGroups" : [ {
		"bl" : "1742",
		"desc" : "460全能 1150暴击"
	}, {
		"bl" : "1743",
		"desc" : "552全能 1058暴击"
	}, {
		"bl" : "1744",
		"desc" : "644全能 966暴击"
	}, {
		"bl" : "1745",
		"desc" : "736全能 874暴击"
	}, {
		"bl" : "1746",
		"desc" : "966全能 644暴击"
	}, {
		"bl" : "1747",
		"desc" : "1058全能 552暴击"
	}, {
		"bl" : "1748",
		"desc" : "1150全能 460暴击"
	}, ]
}, {
	"id" : 2,
	"desc" : "无双之 爆击 精通",
	"bonusGroups" : [ {
		"bl" : "1749",
		"desc" : "1150暴击 460精通"
	}, {
		"bl" : "1750",
		"desc" : "1058暴击 552精通"
	}, {
		"bl" : "1751",
		"desc" : "966暴击 644精通"
	}, {
		"bl" : "1752",
		"desc" : "874暴击 736精通"
	}, {
		"bl" : "1753",
		"desc" : "644暴击 966精通"
	}, {
		"bl" : "1754",
		"desc" : "552暴击 1058精通"
	}, {
		"bl" : "1755",
		"desc" : "460暴击 1150精通"
	}, ]
}, {
	"id" : 3,
	"desc" : "燎火之 爆击 急速",
	"bonusGroups" : [ {
		"bl" : "1756",
		"desc" : "460急速 1150暴击"
	}, {
		"bl" : "1757",
		"desc" : "598急速 1012暴击"
	}, {
		"bl" : "1758",
		"desc" : "644急速 966暴击"
	}, {
		"bl" : "1759",
		"desc" : "736急速 874暴击"
	}, {
		"bl" : "1760",
		"desc" : "966急速 644暴击"
	}, {
		"bl" : "1761",
		"desc" : "1058急速 552暴击"
	}, {
		"bl" : "1762",
		"desc" : "1150急速 460暴击"
	}, ]
}, {
	"id" : 4,
	"desc" : "灼光之 精通 急速",
	"bonusGroups" : [ {
		"bl" : "1763",
		"desc" : "1150精通 460急速"
	}, {
		"bl" : "1764",
		"desc" : "1058精通 552急速"
	}, {
		"bl" : "1765",
		"desc" : "966精通 644急速"
	}, {
		"bl" : "1766",
		"desc" : "874精通 736急速"
	}, {
		"bl" : "1767",
		"desc" : "644精通 966急速"
	}, {
		"bl" : "1768",
		"desc" : "552精通 1058急速"
	}, {
		"bl" : "1769",
		"desc" : "460精通 1150急速"
	}, ]
}, {
	"id" : 5,
	"desc" : "曙光之 全能 急速",
	"bonusGroups" : [ {
		"bl" : "1770",
		"desc" : "460全能 1150急速"
	}, {
		"bl" : "1771",
		"desc" : "552全能 1058急速"
	}, {
		"bl" : "1772",
		"desc" : "644全能 966急速"
	}, {
		"bl" : "1773",
		"desc" : "736全能 874急速"
	}, {
		"bl" : "1774",
		"desc" : "966全能 644急速"
	}, {
		"bl" : "1775",
		"desc" : "1058全能 552急速"
	}, {
		"bl" : "1776",
		"desc" : "1150全能 460急速"
	}, ]
}, {
	"id" : 6,
	"desc" : "谐律之 全能 精通",
	"bonusGroups" : [ {
		"bl" : "1777",
		"desc" : "460全能 1150精通"
	}, {
		"bl" : "1778",
		"desc" : "552全能 1058精通"
	}, {
		"bl" : "1779",
		"desc" : "644全能 966精通"
	}, {
		"bl" : "1780",
		"desc" : "736全能 874精通"
	}, {
		"bl" : "1781",
		"desc" : "966全能 644精通"
	}, {
		"bl" : "1782",
		"desc" : "1058全能 552精通"
	}, {
		"bl" : "1783",
		"desc" : "1150全能 460精通"
	}, ]
}, {
	"id" : 7,
	"desc" : "屠夫之 爆击",
	"bonusGroups" : [ {
		"bl" : "1784",
		"desc" : "1611爆击"
	}, ]
}, {
	"id" : 8,
	"desc" : "应变之 全能",
	"bonusGroups" : [ {
		"bl" : "1785",
		"desc" : "1611全能"
	}, ]
}, {
	"id" : 9,
	"desc" : "焦躁之 急速",
	"bonusGroups" : [ {
		"bl" : "1786",
		"desc" : "1611急速"
	}, ]
}, {
	"id" : 10,
	"desc" : "专擅之 精通",
	"bonusGroups" : [ {
		"bl" : "1787",
		"desc" : "1611精通"
	}, ]
}, ];

var BonusGroups3 = [ {
	"id" : 1,
	"desc" : "快刀之 全能 爆击",
	"bonusGroups" : [ {
		"bl" : "3343",
		"desc" : "460全能 229暴击"
	}, {
		"bl" : "3344",
		"desc" : "575全能 114暴击"
	}, {
		"bl" : "3345",
		"desc" : "690全能"
	}, {
		"bl" : "3361",
		"desc" : "229全能 460暴击"
	}, {
		"bl" : "3362",
		"desc" : "114全能 575暴击"
	}, {
		"bl" : "3363",
		"desc" : "690暴击"
	}, ]
}, {
	"id" : 2,
	"desc" : "无双之 爆击 精通",
	"bonusGroups" : [ {
		"bl" : "3346",
		"desc" : "229暴击 460精通"
	}, {
		"bl" : "3347",
		"desc" : "114暴击 575精通"
	}, {
		"bl" : "3348",
		"desc" : "690精通"
	}, {
		"bl" : "3351",
		"desc" : "229精通 460暴击"
	}, {
		"bl" : "3352",
		"desc" : "114精通 575暴击"
	}, {
		"bl" : "3354",
		"desc" : "690暴击"
	}, ]
}, {
	"id" : 3,
	"desc" : "燎火之 爆击 急速",
	"bonusGroups" : [ {
		"bl" : "3349",
		"desc" : "460急速 229暴击"
	}, {
		"bl" : "3350",
		"desc" : "575急速 114暴击"
	}, {
		"bl" : "3353",
		"desc" : "690急速"
	}, {
		"bl" : "3370",
		"desc" : "229急速 460暴击"
	}, {
		"bl" : "3371",
		"desc" : "114急速 575暴击"
	}, {
		"bl" : "3372",
		"desc" : "690暴击"
	}, ]
}, {
	"id" : 4,
	"desc" : "灼光之 精通 急速",
	"bonusGroups" : [ {
		"bl" : "3355",
		"desc" : "460急速 229精通"
	}, {
		"bl" : "3356",
		"desc" : "575急速 114精通"
	}, {
		"bl" : "3357",
		"desc" : "690急速"
	}, {
		"bl" : "3373",
		"desc" : "229急速 460精通"
	}, {
		"bl" : "3374",
		"desc" : "114急速 575精通"
	}, {
		"bl" : "3375",
		"desc" : "690精通"
	}, ]
}, {
	"id" : 5,
	"desc" : "谐律之 全能 精通",
	"bonusGroups" : [ {
		"bl" : "3358",
		"desc" : "460全能 229精通"
	}, {
		"bl" : "3359",
		"desc" : "575全能 114精通"
	}, {
		"bl" : "3360",
		"desc" : "690全能"
	}, {
		"bl" : "3367",
		"desc" : "229全能 460精通"
	}, {
		"bl" : "3368",
		"desc" : "114全能 575精通"
	}, {
		"bl" : "3369",
		"desc" : "690精通"
	}, ]
}, {
	"id" : 6,
	"desc" : "曙光之 全能 急速",
	"bonusGroups" : [ {
		"bl" : "3364",
		"desc" : "229全能 460急速"
	}, {
		"bl" : "3365",
		"desc" : "114全能 575急速"
	}, {
		"bl" : "3366",
		"desc" : "690急速"
	}, {
		"bl" : "3376",
		"desc" : "460全能 229急速"
	}, {
		"bl" : "3377",
		"desc" : "575全能 114急速"
	}, {
		"bl" : "3378",
		"desc" : "690全能"
	}, ]
}, ];

// 获取bonus说明，仅用于7.0制造业物品
function getItemBonusHtml(type) {
	var tmpHtml = "<div class='panel-group' role='tablist' aria-multiselectable='true'>";
	if (type == 1) {
		var bonusGroups = BonusGroups1;
	} else if (type == 2) {
		var bonusGroups = BonusGroups2;
	} else if (type == 3) {
		var bonusGroups = BonusGroups3;
	}
	for (i in bonusGroups) {
		var bls = bonusGroups[i];
		tmpHtml += "<div class='panel panel-default'><div class='panel-heading' role='tab' id='heading"+bls.id+"'><h4 class='panel-title'><a class='collapsed' data-toggle='collapse' data-parent='#accordion' href='#collapse"+bls.id+"' aria-expanded='false' aria-controls='collapse"+bls.id+"'>";
		tmpHtml += bls.desc;
		tmpHtml += "</a></h4></div><div id='collapse"+bls.id+"' class='panel-collapse collapse' role='tabpanel' aria-labelledby='heading"+bls.id+"'><div class='panel-body'>";
			// body
		tmpHtml += "<ul class='list-group'>";
		for (j in bls.bonusGroups) {
			var bl = bls.bonusGroups[j];
			tmpHtml += "<li class='list-group-item'><a class='bonus' href='javascript:void(0)' bl='"+bl.bl+"'>"+bl.desc+"</a></li>";
		}
		tmpHtml += "</ul>";
		tmpHtml += "</div></div></div>";
	}
	tmpHtml += "<a class='bonus btn btn-default' href='javascript:void(0)' bl='all'>全部</a></div>";
	return tmpHtml;
}

function itemQueryByName(realm, itemName) {
	$.get('wow/item/name/' + encodeURIComponent(itemName), function(data) {					
		if (data.length === 0) {
			$('#msg').html("找不到物品:" + itemName);
		} else if(data.length === 1) {
			var item = data[0];
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.item.key, itemName);
			if (item.createdBy !=null && item.createdBy.length > 0) {
				$('#itemCreatedByDiv').show();
				$('#itemCreatedByDetailDiv').html("");	
				currentItem = item;
			}
			if (item.bonusList.length === 0 || item.bonusList.length === 1) {							
				accurateQuery(realm, item.id, itemName);
			} else {
				// 多bonus物品
				if ((item.bonusList[0] >= 1676 && item.bonusList[0] <= 1721) || (item.bonusList[0] >= 1742 && item.bonusList[0] <= 1787) || (item.bonusList[0] >= 3343 && item.bonusList[0] <= 3378)) { // 7.0制造业物品
					$('#msg').html('7.0制造业装备有多种副属性组合，请选择一种查询');
					if (item.bonusList[0] >= 1676 && item.bonusList[0] <= 1721) {
						$('#itemListByName').html(getItemBonusHtml(1));
					} else if (item.bonusList[0] >= 1742 && item.bonusList[0] <= 1787) {
						$('#itemListByName').html(getItemBonusHtml(2));
					} else if (item.bonusList[0] >= 3343 && item.bonusList[0] <= 3378) {
						$('#itemListByName').html(getItemBonusHtml(3));
					}
					
					$(".bonus").click(function() {
						var regItemId = item.id+"?bl="+$(this).attr('bl');
						accurateQuery(realm, regItemId, itemName);									
					});
				} else {
					$('#msg').html('物品:' + itemName + ' 发现' + item.bonusList.length + '种版本,请选择下列表中的一种来查询');
					var tableHtml = "<table class='table table-striped'><thead><tr><th>ID</th><th>物品名</th><th>物品说明</th></tr></thead><tbody>";
					for (var i in item.bonusList) {
						var itemBonus = item.bonusList[i];
						tableHtml += "<tr><td>"+item.id+"</td><td><a href='javascript:void(0)' id='itemBonus"+i+item.id+"' itemId='"+item.id+"' bl='"+itemBonus+"'>"+item.name+"</a></td><td>"+Bnade.getBonusDesc(itemBonus)+"</td></tr>";
					}
					tableHtml += '</tbody></table>';
					$('#itemListByName').html(tableHtml);	
					for (var i in item.bonusList) {
						var itemBonus = item.bonusList[i];
						$("#itemBonus" + i + item.id).click(function() {
							var regItemId = $(this).attr('itemId')+"?bl="+$(this).attr('bl');
							var regItemName = $(this).html();
							accurateQuery(realm, regItemId, regItemName);									
						});
					}
				}
			}						
		}else if (data.length > 1) {
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.item.key, itemName);
			$('#msg').html('发现' + data.length + '个有相同名字的物品,请选择下列表中的一个物品来查询');
			var tableHtml = "<table class='table table-striped'><thead><tr><th>ID</th><th>物品名</th><th>物品等级</th></tr></thead><tbody>";
			for(var i in data) {
				var item = data[i];
				tableHtml += "<tr><td>"+item.id+"</td><td><a href='javascript:void(0)' id='"+item.id+"'>"+item.name+"</a></td><td>"+item.itemLevel+"</td></tr>";
			}
			tableHtml += '</tbody></table>';
			$('#itemListByName').html(tableHtml);	
			for(var i in data){
				var item = data[i];
				$("#" + item.id).click(function() {
					var regItemId = $(this).attr('id');
					var regItemName = $(this).html();
					accurateQuery(realm, regItemId, regItemName);								
				});
			}
		} 		
	}).fail(function() {
		$("#msg").html("查询出错");
    });	
}
function itemQuery() {		
	clear();		
	if (!checkCommand()) {
		var itemName = $("#itemName").val();
		var realm = $("#realm").val();
		if (itemName === "") {
			$("#msg").html("物品名不能为空");
		} else {	
			$('#itemListByName').empty();
			// 如果物品时数字则当作物品ID来查询
			if (parseInt(itemName, 10) == itemName) {
				var id = itemName;
				itemQueryById(realm, id);
			} else {
				itemQueryByName(realm, itemName);
			}
		}
	}
}
function itemQueryById(realm, id) {
	$.get('wow/item/' + id, function(data) {
		if (data != null) {
			itemQueryByName(realm, data.name);			
		} else {
			$('#msg').html("通过ID找不到物品:" + id);
		}						
	});
}

function clear(){
	$('#past24CtlDiv').hide();
	$("#past24Msg").html("");
	$('#pastWeekCtlDiv').hide();
	$("#pastWeekMsg").html("");
	$('#allRealmCtlDiv').hide();	
	$("#allRealmMsg").html("");			
	$('#msg').html("");
	$('#itemDetail').html("");
	$('#itemCreatedByDiv').hide();
}
var isShowAll=true;
function accurateQuery(realm, itemId, itemName) {		
	$("#showAllTbl").hide();
	$("#showAllA").html("显示全部+");
	loadItemDetail(itemId);
	if (realm !== "") {
		var realmId = Realm.getIdByName(realm);
		if (realmId > 0) {
			getPast24(realmId, realm, itemId, itemName);
			getPastWeek(realmId, realm, itemId, itemName);
		} else {
			$('#msg').html("找不到服务器：" + realm);
		}		
	}		
	
	gblItemId =itemId;
	sortColumn = "sort1";
	orderByDesc = true;
	
	getItemByAllRealms(itemId, itemName);
	var url = window.location.protocol + "//" + window.location.host + window.location.pathname + "?itemName=" + encodeURIComponent(itemName);
	if (realm != null && realm != '') {
		url += "&realm=" + encodeURIComponent(realm);
	}			
	$("#queryByUrl").html("快速查询URL: <a href='" + url + "'>" + url + "</a>");
}

function getPast24(realmId, realm, itemId, itemName) {		
	$('#past24Msg').html("正在查询24小时内数据,请稍等...");
	$.get("wow/auction/past/realm/" + realmId + "/item/" + itemId,function(data) {
		if (data.length === 0) {
			$('#past24Msg').html("24小时内数据找不到");
			$('#past24CtlDiv').hide();
		} else {				
			$('#past24CtlDiv').show();
			// 按更新时间排序
			data.sort(function(a, b){
				return a[2] - b[2];
			});
			var chartLabels=[];
			var chartMinBuyout=[];
			var chartQuantity=[];
			var tmpMinBuyout=0;
			var tmpMaxBuyout=0;
			var avgBuyout=0;
			var avgQuantity=0;
			var tmpMaxBuyout=0;
			for(var i in data){
				var item=data[i];
				var minBuyout=toDecimal(item[0]/10000);
				chartMinBuyout[i]=minBuyout;
				if(minBuyout>=10){
					chartMinBuyout[i] = parseInt(minBuyout);
				}
				chartLabels[i]=new Date(item[2]).format('hh:mm');
				chartQuantity[i]=item[1];
				avgBuyout+=minBuyout;
				avgQuantity+=item[1];
				if(tmpMinBuyout==0||minBuyout<tmpMinBuyout)
					tmpMinBuyout=minBuyout;
				if(tmpMaxBuyout==0||minBuyout>tmpMaxBuyout)
					tmpMaxBuyout=minBuyout;
			}
			avgBuyout=toDecimal(avgBuyout/data.length);
			if(avgBuyout>=10){
				avgBuyout=parseInt(avgBuyout);
			} 
			if(tmpMinBuyout>=10){tmpMinBuyout=parseInt(tmpMinBuyout);}
			if(tmpMaxBuyout>=10){tmpMaxBuyout=parseInt(tmpMaxBuyout);}
			avgQuantity = parseInt(avgQuantity/data.length);
			var latestBuyout=toDecimal(data[data.length-1][0]/10000);
			if(latestBuyout>=10) latestBuyout=parseInt(latestBuyout);						
			$('#past24MinBuyout').html(tmpMinBuyout);
			$('#past24MaxBuyout').html(tmpMaxBuyout);
			$('#past24AvgBuyout').html(avgBuyout);
			$('#past24AvgQuantity').html(avgQuantity);
			$('#past24LatestBuyout').html(latestBuyout);				
			loadChart('past24Container','24小时内价格走势',itemName,chartLabels,chartMinBuyout,tmpMinBuyout,tmpMaxBuyout,true,'areaspline',chartQuantity,'spline');
			$('#past24Msg').html("");
		}			
	}).fail(function() {
		$("#msg").html("24小时数据查询出错");
    });
}
function loadChart(containerId,title,subtitle,chartLabels,chartMinBuyout,minBuyout,maxBuyout,showxAxisLabel,series1Type,chartQuantity,series2Type){
	minBuyout = minBuyout < 100 ? 0 : minBuyout;
	$('#'+containerId).highcharts({
        chart:{zoomType: 'xy',ignoreHiddenSeries: false},	        
        title:{text:title},
        subtitle:{text:subtitle},
        xAxis:[{categories: chartLabels,crosshair: true,labels: {
            enabled: showxAxisLabel
        }}],
        yAxis:[{
        		title:{
               		text:'数量',style:{color: Highcharts.getOptions().colors[8]}
           		},
           		labels:{
                	format:'{value}个',style:{color: Highcharts.getOptions().colors[1]}
	            },
	            opposite: true
	        },{
	        	labels:{
	        		format:'{value}G',style:{color: Highcharts.getOptions().colors[1]}
	        	},
	           	title:{
	           		text:'价格',style:{color: Highcharts.getOptions().colors[0]}
	        	},		 
	        	min: minBuyout,
	        	max: maxBuyout
        	}],
        tooltip: {
            shared: true
        },
        series:[{
            name:'数量',
            type:series2Type,	
            data: chartQuantity,
            color: Highcharts.getOptions().colors[8],
            //dashStyle: 'shortdot',
            marker: {
                enabled: false
            },
            tooltip:{valueSuffix:'个'}
       	},{
            name:'价格',
            type:series1Type,
            yAxis:1,
            data: chartMinBuyout,
            color: Highcharts.getOptions().colors[0],
            marker: {
                enabled: false
            },
            tooltip:{valueSuffix:'G'}
        }]
    });
}
function loadHeatMapChart(container,title,heatMapLabels,heatMapData,minBuyout,seriesName,color) {
	$('#'+container).highcharts({
		chart: {
            type: 'heatmap',
            marginTop: 40,
            marginBottom: 80,
            inverted: true
        },
        title:{text: title},
        xAxis: {
            categories: ['0点-6点','6点-12点','12点-18点','18点-24点']
        },
        yAxis: {
            categories: heatMapLabels,
            title: null,
        },
        colorAxis: {
            min: minBuyout,
            minColor: '#FFFFFF',
            maxColor: Highcharts.getOptions().colors[color]
        },
        legend: {
            align: 'right',
            layout: 'vertical',
            margin: 0,
            verticalAlign: 'top',
            y: 25,
            symbolHeight: 280
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.yAxis.categories[this.point.y] + '</b>的<b>' 
                + this.series.xAxis.categories[this.point.x] + '</b><br>'+seriesName+'<b>' + this.point.value + '</b><br>';
            }
        },
        series: [{
            name: seriesName,
            borderWidth: 1,
            data: heatMapData,
            dataLabels: {
                enabled: true,
                color: '#000000'
            }
        }]
    });
}
function getPastWeek(realmId, realm, itemId, itemName){
	$('#pastWeekMsg').html("正在查询物品所有历史数据, 请稍等...");
	$.get("wow/auction/history/realm/" + realmId + "/item/" + itemId, function(data) {
		if (data.length === 0) {
			$('#pastWeekMsg').html("历史数据未找到");					
			$('#pastWeekCtlDiv').hide();
		} else {
			// 保存服务器名
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.realm.key, realm);
			$('#pastWeekCtlDiv').show();
			// 按更新时间排序
			data.sort(function(a, b){
				return a[2] - b[2];
			});
			// 图表价格数据
			var chartData = [];
			// 图表数量数据
			var chartQuantityData = [];
			// 用于计算价格
			var calData = []; 
			var quantitySum = 0;
			for (var i in data) {				
				var buyout = Bnade.getGold(data[i][0]);
				var quantity = data[i][1];
				var updated = data[i][2] + 8*60*60*1000;
				calData[i] = data[i][0];
				chartData[i] = [];
				chartData[i][0] = updated;
				chartData[i][1] = buyout;
				chartQuantityData[i] = [];
				chartQuantityData[i][0] = updated;
				chartQuantityData[i][1] = quantity;
				quantitySum += quantity;
			}			
			var calResult = Bnade.getResult(calData);	
			var maxBuy = Bnade.getGold(calResult.max);
			var minBuy = Bnade.getGold(calResult.min);
			var avgBuy = Bnade.getGold(calResult.avg);
			// 图表中显示的小数点后位数
			var valueDecimals = 2;
			if (avgBuy > 10) {
				valueDecimals = 0;
			}
			$('#historyMin').html(minBuy);
			$('#historyMax').html(maxBuy);
			$('#historyAvg').html(avgBuy);
			$('#historyAvgQuantity').html(parseInt(quantitySum/data.length));

			Highcharts.setOptions({lang:{
				months:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				shortMonths:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				weekdays:['星期日','星期一','星期二','星期三','星期四','星期五','星期六',]
			}});
			$('#pastWeekContainer').highcharts('StockChart', {
	            rangeSelector : {
	                selected : 4,
	                inputEnabled:false,
	                buttons: [{
	                	type: 'week',
	                	count: 1,
	                	text: '周'
	                },{
	                	type: 'month',
	                	count: 1,
	                	text: '月'
	                }, {
	                	type: 'month',
	                	count: 4,
	                	text: '季'
	                }, {
	                	type: 'year',
	                	count: 1,
	                	text: '年'
	                }, {
	                	type: 'all',
	                	text: '全部'
	                }]
	            },
	            credits:{enabled:false},
	            navigator:{enabled:false},
	            scrollbar:{enabled:false},
	            title : {
	                text : '['+itemName+']在服务器['+realm+']的历史价格信息'
	            },xAxis: {
	                type: 'datetime',
	                dateTimeLabelFormats: {
	                    second: '%H:%M:%S',
	                    minute: '%H:%M',
	                    hour: '%H:%M',
	                    day: '%m-%d',
	                    week: '%m-%d',
	                    month: '%m',
	                    year: '%Y'
	                }
	            },
	            yAxis:[{
	            	title:{
		           		text:'价格',style:{color: Highcharts.getOptions().colors[0]}
		        	},	
		        	labels:{
		        		format:'{value}G',style:{color: Highcharts.getOptions().colors[1]},
		        		align: 'right',
		        		x:-3
		        	},			           		
		        	height: '60%',
	                lineWidth: 2, 
		        	min: minBuy,
		        	max: maxBuy
	        	},{
	        		title:{
	               		text:'数量',style:{color: Highcharts.getOptions().colors[8]},
	           		},
	           		labels:{
	                	format:'{value}个',style:{color: Highcharts.getOptions().colors[1]},
		           		align: 'right',
		        		x:-3
		            },
		            top: '65%',
	                height: '35%',
	                offset: 0,
	                lineWidth: 2
		        }],
		        tooltip: {
		        	
                },
	            series : [{
	                name : '价格',
	                type : 'areaspline',
	                data : chartData,
	                tooltip: {
	                    valueDecimals: valueDecimals,		                
		                valueSuffix: 'G'
	                },
	                fillColor : {
	                    linearGradient : {
	                        x1: 0,
	                        y1: 0,
	                        x2: 0,
	                        y2: 1
	                    },
	                    stops : [
	                        [0, Highcharts.getOptions().colors[0]],
	                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                    ]
	                }
	            },{
		            name:'数量',
		            type:'column',	
		            yAxis:1,
		            data: chartQuantityData,
		            color: Highcharts.getOptions().colors[8],			 
		            tooltip:{valueSuffix:'个'}
		       	}]
	        });
			// 热力图标签								
			var heatMapLabels = [];
			// 热力图价格数据
			var heatMapBuyoutData = [];
			// 热力图数量数据
			var heatMapQuantityData = [];
			// 获取前一周的数据
			// 1. 一周前日期零点的时间
			var todayDate = new Date();
			var myDate = new Date(todayDate.getFullYear(), todayDate.getMonth(), todayDate.getDate() - 7, 0, 0, 0, 0);		
			var startTime = myDate.getTime();	
			// 2. 获取和计算一周内的数据
			var weekCalData = [];	// 一周内所有价格
			var weekQuantity = 0;	// 一周内所有数量
			var weekCount = 0;		// 一周内的数量
			var weekMinQuantity = 0; 	// 最低数量
			var heatI = 0;
			for (var i in data) {				
				var buyout = data[i][0];
				var buyoutGold = Bnade.getGold(buyout);
				var quantity = data[i][1];
				var updated = data[i][2];
				if (updated > startTime) {
					weekCalData[heatI] =  buyout;
					weekQuantity += quantity;
					weekCount++;
					if (weekMinQuantity === 0 || weekMinQuantity > quantity) {
						weekMinQuantity = quantity;
					}
					var tmpTime = new Date(updated).format('hh:mm');
					var week = new Date(updated - 1).getDay();
					if (week === 0) week = 7;
					if ("00:00" === tmpTime) {						
						heatMapBuyoutData[heatI] = [3,week-1,buyoutGold];
						heatMapQuantityData[heatI++] = [3, week-1, quantity];
					}						
					if ("06:00" === tmpTime) {
						heatMapBuyoutData[heatI] = [0,week-1,buyoutGold];
						heatMapQuantityData[heatI++] = [0, week-1, quantity];
					}						
					if ("12:00" === tmpTime) {
						heatMapBuyoutData[heatI] = [1,week-1,buyoutGold];
						heatMapQuantityData[heatI++] = [1, week-1, quantity];
					}						
					if ("18:00" === tmpTime) {
						heatMapBuyoutData[heatI] = [2,week-1,buyoutGold];
						heatMapQuantityData[heatI++] = [2, week-1, quantity];						
					}	
					heatMapLabels[week-1] = '星期' + weekFormat(week);
				}
			}
			var weekResult = Bnade.getResult(weekCalData);			
			$('#pastWeekMinBuyout').html(Bnade.getGold(weekResult.min));
			$('#pastWeekMaxBuyout').html(Bnade.getGold(weekResult.max));
			$('#pastWeekAvgBuyout').html(Bnade.getGold(weekResult.avg));
			$('#pastWeekAvgQuantity').html(parseInt(weekQuantity/weekCount));
			loadHeatMapChart('pastWeekBuyoutHeatMapContainer','一周内'+itemName+'价格热力图',heatMapLabels,heatMapBuyoutData,Bnade.getGold(weekResult.min),'一口价',0);
			loadHeatMapChart('pastWeekQuantityHeatMapContainer','一周内'+itemName+'数量热力图',heatMapLabels,heatMapQuantityData,weekMinQuantity,'数量',8);
			$('#pastWeekMsg').html("");
		}			
	}).fail(function() {
		$("#pastWeekMsg").html("历史数据查询出错");
    });
}
var Column = {
	minBuyout : 1,
	quantity : 2,
}

function sortData(column, data, orderByDesc) {
	data.sort(function(a,b){
		switch(column) {
			case Column.minBuyout :
				var result = a[1] - b[1];
				return orderByDesc ? -result : result;
				break;
			case Column.quantity :			
				var result = a[3] - b[3];
				return orderByDesc ? -result : result;
				break;			
		}	
	});	
}

function generateTableBody(itemId,data) {	
	var tblHtml="";
	var existedRealm = [];
	var count = 0;
	for (var i in data) {
		var itemArr=data[i];
		var realmId = itemArr[0];
		existedRealm[realmId] = 1;
		var realm=Realm.getConnectedById(realmId);
		var realmColumnClass="";								
		if ($("#realm").val() != "" && realm.indexOf($("#realm").val()) >= 0) {
			realmColumnClass = "class='danger'";									
		}
		var buyout=Bnade.getGold(itemArr[1]);	
		count++;
		tblHtml += "<tr "+realmColumnClass+"><td>"+(parseInt(i)+1)+"</td><td><a href='javascript:void(0)' class='queryRealm'>"+realm+"</a></td><td>"+buyout+"</td><td><a href='/ownerQuery.html?realm="+encodeURIComponent(Realm.getNameById(realmId))+"&owner="+encodeURIComponent(itemArr[2])+"'  target='_blank'>"+itemArr[2]+"</a></td><td>"+leftTimeMap[itemArr[5]]+"</td><td><a href='javascript:void(0)' data-toggle='modal' data-target='#itemAucsModal' data-realmid='"+realmId+"' data-itemid='"+itemId+"'>"+itemArr[3]+"</a></td><td>"+HotRealm[realmId]+"</td><td>"+new Date(itemArr[4]).format("MM-dd hh:mm:ss")+"</td></tr>";
	}
	var tmpArr = [];
	for (var i = 1; i <= 170; i++) {
		if (existedRealm[i] != 1) {
			tmpArr.push([i,HotRealm[i]]);
		}
	}
	tmpArr.sort(function(a,b){
		return -(a[1] - b[1]);
	});
	for (var i in tmpArr) {
		var arr = tmpArr[i];
		count++;
		var realm=Realm.getConnectedById(arr[0]);
		tblHtml += "<tr><td>"+count+"</td><td>"+realm+"</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>"+arr[1]+"</td><td>N/A</td></tr>";
	}
	return tblHtml;
}

function processRealmsData(data) {
	var existedRealm = [];
	for (var i in data) {
		var itemArr=data[i];
		var realmId = itemArr[0];
		itemArr.push(HotRealm[realmId]);
		existedRealm[realmId] = 1;
	}
	for (var i = 1; i <= 170; i++) {
		if (existedRealm[i] != 1) {
			data.push([i,0,"N/A",0,0,"N/A",HotRealm[i]]);
		}
	}
}

function getItemByAllRealms(itemId,itemName){
	$('#allRealmMsg').html("正在查询所有服务器数据,请稍等...");
	$.get('wow/auction/item/'+itemId,function(data){	
		if(data.code==404){
			$('#allRealmMsg').html("查询所有服务器失败:"+data.errorMessage);
			$('#allRealmCtlDiv').hide();
		}else{
			$('#allRealmCtlDiv').show();
			// 重置排序数据
			var isShowAll=true;
//			processRealmsData(data);
			gblData = data;			
			
			$("#showAllA").click(function(){
				if(isShowAll){
					isShowAll=false;
					$("#showAllTbl").show();
					$("#showAllA").html("显示全部-");
				}else{
					isShowAll=true;
					$("#showAllTbl").hide();
					$("#showAllA").html("显示全部+");
				}		
			});
			$('#sort'+Column.minBuyout).click();
			$("#showAllA").click();
			
			var chartLabels=[];
			var chartBuyoutData=[];
			var chartQuantityData=[];

			var calData=[];
			var quantitySum=0;
			for(var i in data){
				var realm=Realm.getConnectedById(data[i][0]);					
				var buyout=data[i][1];
				var buyoutOwner=data[i][2];
				var quantity=data[i][3];
				var updated=data[i][4];
				calData[i]=buyout;
				chartLabels[i]=realm;
				chartBuyoutData[i]=Bnade.getGold(buyout);
				chartQuantityData[i]=quantity;
				quantitySum+=quantity;
			}			
			var result = Bnade.getResult(calData);
			var minBuy = Bnade.getGold(result.min);
			var maxBuy = Bnade.getGold(result.max);
			var avgBuy = Bnade.getGold(result.avg);
			$('#allMinBuyout').html(minBuy);
			$('#allMaxBuyout').html(maxBuy);
			$('#allAvgBuyout').html(avgBuy);
			$('#allAvgQuantity').html(parseInt(quantitySum/data.length));				
			$('#allRealmMsg').html("所有服务器平均价格:"+avgBuy);				
			loadChart('allRealmContainer',itemName+'在各服务器的价格和数量',itemName,chartLabels,chartBuyoutData,minBuy,avgBuy * 2 < maxBuy ? avgBuy * 2 : maxBuy, false, 'spline',chartQuantityData,'column');
		}
	});
}
function checkCommand(){
	var code=$('#realm').val();
	var value=$('#itemName').val();
	if(code=="realmCount"){
		if(parseInt(value)==value&&value>=0){
			var obj=JSON.parse(localStorage.getItem(BnadeLocalStorage.lsItems.realm.key));
			obj.length=value;
			localStorage.setItem(BnadeLocalStorage.lsItems.realm.key,JSON.stringify(obj));
			BnadeLocalStorage.refresh();
			alert("服务器保存数量设置成功");
		}else{
			alert("请在物品名框输入正确的正整数");
		} 
	}else if(code=="itemCount"){
		if(parseInt(value)==value&&value>=0){
			var obj=JSON.parse(localStorage.getItem(BnadeLocalStorage.lsItems.item.key));
			obj.length=value;				
			localStorage.setItem(BnadeLocalStorage.lsItems.item.key,JSON.stringify(obj));
			BnadeLocalStorage.refresh();
			alert("物品名保存数量设置成功");
		}else{
			alert("请在物品名框输入正确的正整数");
		} 
	}else{
		return false;
	}
	return true;
}
function loadTopItems(){
	$.get('wow/item/hot', function(data) {
		if (data.length != 0){
			var dailyI = 1;
			var weeklyI = 1;
			var monthlyI = 1;
			$("#dailyHot").html("<div id='dailyHotList' class='list-group'></div>");
			$("#weeklyHot").html("<div id='weeklyHotList' class='list-group'></div>");			
			$("#monthlyHot").html("<div id='monthlyHotList' class='list-group'></div>");
			for(var i in data){
				if (data[i].type === 3) {
					var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].name+"'>"+(monthlyI++)+" "+data[i].name;
					if (monthlyI <= 4) {
						listItem +=" <span class='badge'>"+data[i].queried+"</span></a>";
					} else {
						listItem +="</a>";
					}					
					$("#monthlyHotList").append(listItem);
					$("#topItem"+i).click(function(){
						$("#itemName").val($(this).attr('itemName'));							
						$("#queryBtn").click();							
					});
				}
				if (data[i].type === 2) {
					var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].name+"'>"+(weeklyI++)+" "+data[i].name;
					if (weeklyI <= 4) {
						listItem +=" <span class='badge'>"+data[i].queried+"</span></a>";
					} else {
						listItem +="</a>";
					}
					$("#weeklyHotList").append(listItem);
					$("#topItem"+i).click(function(){
						$("#itemName").val($(this).attr('itemName'));							
						$("#queryBtn").click();							
					});
				}
				if (data[i].type === 1) {
					var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].name+"'>"+(dailyI++)+" "+data[i].name;
					if (dailyI <= 4) {
						listItem +=" <span class='badge'>"+data[i].queried+"</span></a>";
					} else {
						listItem +="</a>";
					}
					$("#dailyHotList").append(listItem);
					$("#topItem"+i).click(function(){
						$("#itemName").val($(this).attr('itemName'));							
						$("#queryBtn").click();							
					});
				}	
			}
		}
	});
}
function queryByUrl() {
	var realm = getUrlParam('realm');
	var itemName = getUrlParam('itemName');			
	if (itemName !== null && itemName !== "") {
		$('#realm').val(realm);
		$('#itemName').val(itemName);
		$("#queryBtn").click();
	}		
}
function fuzzyQueryItems(itemName) {
	clear();
	$('#msg').html("正在模糊查询物品信息,请稍等...");
	$.get('wow/item/name/' + encodeURIComponent(itemName) + '?fuzzy=true', function(data) {		
		if (data.length === 0) {
			$('#msg').html("找不到物品:" + itemName);
		} else {
			$("#fuzzyItemsList").show();
			$("#fuzzyItemsList").html("<li class='active'><a href='javascript:void(0)'>物品名</a></li>");					
			for (var i in data) {
				var id = "fuzzyItem" + i;						
				$("#fuzzyItemsList").append("<li><a href='javascript:void(0)' id='"+id+"'>"+data[i]+"</a></li>");
				$("#" + id).click(function() {
					$("#itemName").val($(this).html());							
					$("#queryBtn").click();						
				});
			}	
			$('#msg').html("");
		}		
	}).fail(function() {
		$("#msg").html("模糊查询出错");
    });
}
function loadItemDetail(itemId) {
	$('#itemDetail').html("");
	if (itemId.toString().indexOf("?") > -1) {
		itemId += "&tooltip=true";
	} else {
		itemId += "?tooltip=true";
	}
	$.get('wow/item/' + itemId, function(data) {
		if (data.code === 201) {
			$('#msg').append("物品信息查询失败:" + data.errorMessage);								
		} else {
			$('#itemDetail').html(data);
		}
	}).fail(function() {
		$("#msg").html("物品信息查询出错");
    });
}

$(document).ready(function() {
	$("#queryBtn").click(function() {
		itemQuery();
	});
    
   $( "#itemName" ).autocomplete({
     source: 'wow/item/names'
   });
	
   $("#itemFuzzyQueryBtn").click(function(){
		var itemName=$("#itemName").val();
		if(itemName==""){
			$('#msg').html("物品名不能为空");
		} else {			
			fuzzyQueryItems(itemName);			
		}
	});	
   
   $('#itemCreatedByBtn').click(function(){
	   var realm = $("#realm").val();
	   if (realm !== "") {
			var realmId = Realm.getIdByName(realm);
			if (realmId > 0) {
				var itemReagents = {};
				 var itemCreatedByHtml = "<div class='row'><div class='col-md-6'><table class='table table-bordered table-condensed table-hover'>";
				   for (var i in currentItem.createdBy) {
					   var itemCreatedBy = currentItem.createdBy[i];
					   var avgCount = (itemCreatedBy.minCount + itemCreatedBy.maxCount)/2;
					   var itemCreatedByTotalPrice = 0;
					   itemCreatedByHtml += "<tr class='info'><td>配方"+(parseInt(i)+1)+"</td><td>单价</td><td>总价</td></tr>";
					   if (itemCreatedBy.reagent != null && itemCreatedBy.reagent.length > 1) {
						   for (var j in itemCreatedBy.reagent) {
							   var itemReagent = itemCreatedBy.reagent[j];
							   var itemReagentSinglePrice;
							   var itemReagentTotalPrice;
							   if (itemReagent.buyPrice > 0) {
								   itemReagentSinglePrice = Bnade.getGold(itemReagent.buyPrice) + "(npc买)";
								   itemReagentTotalPrice = Bnade.getGold(itemReagent.buyPrice) * itemReagent.count;								   
							   } else {
								   if (itemReagents[itemReagent.itemId] >=0 ) {
									   itemReagentSinglePrice = itemReagents[itemReagent.itemId];
									   itemReagentTotalPrice = itemReagentSinglePrice * itemReagent.count;									   
								   } else {
									   itemReagentSinglePrice = itemQuery.getItemAvgPrice(realmId,itemReagent.itemId);
									   if (itemReagentSinglePrice == 0) {
										   var itemCreatedBys = itemQuery.getItemCreatedBy(itemReagent.itemId);
										   if (itemCreatedBys && itemCreatedBys.length > 0) {
											   var itemCreatedBy2 = itemCreatedBys[0];
											   itemReagentSinglePrice = itemQuery.getItemCreatedByPrice(realmId,itemCreatedBy2);
											   itemReagentTotalPrice = itemReagentSinglePrice * itemReagent.count;
										   } else {
											   itemReagentSinglePrice = 0;
											   itemReagentTotalPrice = 0;
										   }
									   } else {
										   itemReagentTotalPrice = itemReagentSinglePrice * itemReagent.count;										   
									   }
									   itemReagents[itemReagent.itemId] = itemReagentSinglePrice;
								   }								  
							   }
							   itemCreatedByTotalPrice +=itemReagentTotalPrice;
							   itemCreatedByHtml += "<tr><td>"+itemReagent.name+" * " + itemReagent.count+"</td><td>"+itemReagentSinglePrice+"</td><td>"+itemReagentTotalPrice+"</td></tr>";
						   }
					   }
					   itemCreatedByHtml += "<tr><td colspan='2'>成本 (平均可制造"+avgCount+"个)</td><td>"+toDecimal(itemCreatedByTotalPrice/avgCount)+"</td></tr>";
				   }
				   itemCreatedByHtml += "</table></div></div>";
				   $('#itemCreatedByDetailDiv').html(itemCreatedByHtml);
			} else {
				$('#itemCreatedByDetailDiv').html("找不到服务器：" + realm);
			}		
		} else {
			$('#itemCreatedByDetailDiv').html("请设置一个服务器");
		}	  
	});	
   
	!function() { // 页面加载运行
		$('#past24CtlDiv').hide();
		$('#pastWeekCtlDiv').hide();
		$('#allRealmCtlDiv').hide();		
		$("#fuzzyItemsList").hide();
		$('#itemCreatedByDiv').hide();
		queryByUrl();
		loadTopItems();
	}();
	$('#itemAucsModal').on('show.bs.modal', function (event) {
		var aLink = $(event.relatedTarget);
		var realmId = aLink.data('realmid');
		var itemId = aLink.data('itemid');
		var modal = $(this);
		modal.find('.modal-body-content').text("正在查询，请稍等...");
		$.get('wow/auction/realm/' + realmId + '/item/' + itemId, function(data) {		
			if (data.length === 0) {
				$('#msg').html("找不到物品:" + itemName);
			} else {
				data.sort(function(a,b){ 
					return a[3]/a[4] - b[3]/b[4];
				});
				var tmpItem;
				var count = 0;
				var tblHtml = "<table class='table table-striped table-condensed table-responsive'><thead><tr><th>#</th><th>玩家</th><th>服务器</th><th>竞价</th><th>一口价</th><th>数量</th><th>单价</th><th>剩余时间</th></tr></thead><tbody>";
				for (var i in data) {
					var item = data[i];
					if (i == "0") {
						tmpItem = item;
						if (i != (data.length - 1)) {
							continue;
						}						
					} else if (itemQuery.isSameItem(tmpItem, item)) {						
						tmpItem[2] +=item[2];
						tmpItem[3] +=item[3];
						tmpItem[4] +=item[4];
						continue;											
					}
					count++;
					tblHtml += "<tr><td>"+(count)+"</td><td>"+tmpItem[0]+"</td><td>"+tmpItem[1]+"</td><td>"+Bnade.getGold(tmpItem[2])+"</td><td>"+Bnade.getGold(tmpItem[3])+"</td><td>"+tmpItem[4]+"</td><td>"+Bnade.getGold(tmpItem[3]/tmpItem[4])+"</td><td>"+leftTimeMap[tmpItem[5]]+"</td></tr>";
					tmpItem = item;
					if (i != "0" && i == (data.length - 1)) {
						count++;
						tblHtml += "<tr><td>"+(count)+"</td><td>"+tmpItem[0]+"</td><td>"+tmpItem[1]+"</td><td>"+Bnade.getGold(tmpItem[2])+"</td><td>"+Bnade.getGold(tmpItem[3])+"</td><td>"+tmpItem[4]+"</td><td>"+Bnade.getGold(tmpItem[3]/tmpItem[4])+"</td><td>"+leftTimeMap[tmpItem[5]]+"</td></tr>";
					} 	
				}
				tblHtml += "</tbody></table>";				
				modal.find('.modal-body-content').html(tblHtml);
			}		
		}).fail(function() {
			$("#msg").html("查询物品所有拍卖出错");
	    });			  
	});
	
	for (var i in Column) {
		(function(){
			var column = Column[i];
			$('#sort'+column).click(function() {
				if ($(this).attr("id") == sortColumn) {
						orderByDesc = !orderByDesc;
					} else {
						orderByDesc = true;
						sortColumn = $(this).attr("id");
					}
					sortData(column, gblData, orderByDesc);	       					
					$("#showAllBody").html(generateTableBody(gblItemId,gblData));
					$(".queryRealm").click(function() {
						$("#realm").val($(this).html());
						$("#queryBtn").click();
					});
					// 修改排序图标
					for (var j in Column) {
						var col = Column[j];
						$("#sort"+col + " span").removeClass("glyphicon-sort glyphicon-sort-by-attributes glyphicon-sort-by-attributes-alt");
						if ("sort"+col == $(this).attr("id")) {
							if (orderByDesc) {
								$("#sort"+col + " span").addClass("glyphicon-sort-by-attributes-alt");
							} else {
								$("#sort"+col + " span").addClass("glyphicon-sort-by-attributes");
							}	       								       							
						} else {
							$("#sort"+col + " span").addClass("glyphicon-sort");	       							
						}
					}	      
			});
		})();
	}
});