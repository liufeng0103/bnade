var Column = {
	auctionQuantity:1,
	playerQuantity:2,
	itemQuantity:3,
	lastModified:4,
	type:5,
};

function sortData(column, data, orderByDesc) {
	var result;
	data.sort(function(a,b){
		switch(column) {
			case Column.auctionQuantity : 
				result = a.auctionQuantity - b.auctionQuantity;
				return orderByDesc ? -result : result;
			case Column.playerQuantity : 
				result = a.playerQuantity - b.playerQuantity;
				return orderByDesc ? -result : result;
			case Column.itemQuantity : 
				result = a.itemQuantity - b.itemQuantity;
				return orderByDesc ? -result : result;
			case Column.lastModified : 
				result = a.lastModified - b.lastModified;
				return orderByDesc ? -result : result;
			case Column.type : 
				result = a.type + a.playerQuantity < b.type + b.playerQuantity? -1 : 1;
				return orderByDesc ? -result : result;
		}	
	});	
}

// 生成表格
function generateTable(data,realmType) {	
	var tblHtml="";
	for (var i in data) {
		var realmAuction = data[i];
		var realm = Realm.getConnectedById(realmAuction.id);
		var type = realmAuction.type;
		var realmTypeClass = "text-danger";
		if (type === 'pve') {
			realmTypeClass = "text-primary";
		}
		if (realmType !== 'pve' && realmType !== 'pvp') {			
			tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+realm+"</td><td class='"+realmTypeClass+"'>"+type+"</td><td>"+realmAuction.auctionQuantity+"</td><td>"+realmAuction.playerQuantity+"</td><td>"+realmAuction.itemQuantity+"</td><td>"+new Date(realmAuction.lastModified).format("MM-dd hh:mm:ss")+"</td></tr>";
		} else if (realmType === type) {
			tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+realm+"</td><td class='"+realmTypeClass+"'>"+type+"</td><td>"+realmAuction.auctionQuantity+"</td><td>"+realmAuction.playerQuantity+"</td><td>"+realmAuction.itemQuantity+"</td><td>"+new Date(realmAuction.lastModified).format("MM-dd hh:mm:ss")+"</td></tr>";
		}		
	}
	return tblHtml;
}

function addTypeRow() {
	return "<th><select class='tblHeadSelect'><option>类型</option><option>pve</option><option>pvp</option></select></th>";
}

function addRow(id, name) {
	return "<th><div id='"+id+"' class='tblHeadBtn'>"+name+"<span class='glyphicon' aria-hidden='true'></span></div></th>";
}

var sortColumn = "sort1";
var orderByDesc = false;
$(document).ready(function() {
	!function(){
		$('#auctionQuantityContainer').html("加载数据,请稍等...");
		$.get("wow/auction/realms/summary", function(data) {
       		if (data.length === 0) {
       			$('#auctionQuantityContainer').html("数据未找到");
       		} else {
       			$('#auctionQuantityContainer').html("<table class='table table-striped table-condensed'><thead><tr><th>#</th><th>服务器</th>"
       					+ addTypeRow()
       					+ addRow("sort" + Column.auctionQuantity, "拍卖总数")
       					+ addRow("sort" + Column.playerQuantity, "拍卖行玩家数")
       					+ addRow("sort" + Column.itemQuantity, "物品种类")
       					+ addRow("sort" + Column.lastModified, "更新时间")       					
       					+ "</tr></thead><tbody id='tblBody'></tbody></table>");
       			$('.tblHeadSelect').change(function() {       				
       				$('#tblBody').html(generateTable(data, $('.tblHeadSelect option:selected').text()));
       			});
       			for (var i in Column) {
//       				console.log(Column[i]);       				
       				(function(){
       					var column = Column[i];
       					$("#sort"+column).click(function(){
       						if ($("#sort"+column).attr("id") == sortColumn) {
       							orderByDesc = !orderByDesc;
       						} else {
       							orderByDesc = true;
       							sortColumn = $("#sort"+column).attr("id");
       						}
	       					sortData(column, data, orderByDesc);	       					
	       					$('#tblBody').html(generateTable(data, $('.tblHeadSelect option:selected').text()));
	       					// 修改排序图标
	       					for (var j in Column) {
	       						var col = Column[j];
	       						$("#sort"+col + " span").removeClass("glyphicon-sort glyphicon-sort-by-attributes glyphicon-sort-by-attributes-alt");
	       						if ("sort"+col == $("#sort"+column).attr("id")) {
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
       			$("#sort"+Column.auctionQuantity).click();
       		}
       	}).fail(function() {
			$("#auctionQuantityContainer").html("数据加载出错");
	    }); 
	}();
});