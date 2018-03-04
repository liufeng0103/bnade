function query() {
	clear();
	var petName = $("#petName").val();
	if (petName === "") {
		$('#msg').html("宠物名不能为空");
	} else {	
		petQuery(petName);
	}
}
function petQuery(name) {
	$.get('wow/pet/name/' + encodeURIComponent(name), function(data) {
		if (data.length > 0) {
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.pet.key, name);
			var count = 0;
			var tblHtml = "<table class='table table-striped'><thead><tr><th>#</th><th>ID</th><th>宠物名</th><th>成长类型</th><th>生命值</th><th>攻击</th><th>速度</th></tr></thead><tbody>";
			for (var i in data) {
				var pet = data[i];
				for (var  j in pet.petStatsList) {
					count++;
					var petStats = pet.petStatsList[j];
					tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+pet.id+"</td><td><a href='javascript:void(0)' id='petQuery"+count+"' petId='"+pet.id+"' breed='"+petStats.breedId+"'>"+name+"</a></td><td>"+petStats.breedId+"</td><td>"+petStats.health+"</td><td>"+petStats.power+"</td><td>"+petStats.speed+"</td></tr>";
				}				
			}
			tblHtml += "</tbody></table>";
			tblHtml = "<p>该宠物有" + count + "种,请选择一种查找:</p>" + tblHtml;
			$('#petResult').html(tblHtml);			
			count = 0;
			for (var i in data) {
				var pet = data[i];
				for (var  j in pet.petStatsList) {
					count++;
					$('#petQuery' + count).click(function() {
						accurateQuery($(this).attr('petId'), $(this).attr('breed'), name);
					});				
				}				
			}			
			var url = window.location.protocol + "//" + window.location.host + window.location.pathname + "?name=" + encodeURIComponent(name);		
			$("#queryByUrl").html("快速查询URL: <a href='" + url + "'>" + url + "</a>");
		} else {
			$('#msg').html("找不到宠物:" + name);
		}		
	}).fail(function() {
		$('#msg').html("查询出错");
    });
}
function accurateQuery(id, breed, name) {
	$('#msg').html("正在查询，请稍等...");
	isShowAll = true;
	$("#showAllTbl").hide();
	$("#showAllA").html("所有服务器+");	
	getItemByAllRealms(id, breed, name);
}
function getItemByAllRealms(id, breed, name) {	
	$('#allRealmMsg').show();
	$('#allRealmMsg').html("查询所有服务器数据,请稍等...");
	$.get('wow/auction/pet/' + id + '/breed/' + breed, function(data) {
		if (data.length === 0) {
			$('#allRealmMsg').html("未找到:" + name);
			$('#allRealmCtlDiv').hide();
		} else {	
			data.sort(function(a,b) {
				return a[1] - b[1];
			});
			var isShowAll = true;
			$("#showAllA").click(function() {
				if(isShowAll){
					isShowAll = false;
					$("#showAllTbl").show();
					$("#showAllA").html("所有服务器-");			
					$("#showAllBody").empty();
					for (var i in data) {
						var item = data[i];
						var buyout = Bnade.getGold(item[1]);
						var realm = Realm.getConnectedById(item[0]);								
						$("#showAllBody").append("<tr><td>"+(parseInt(i)+1)+"</td><td>"+realm+"</td><td>"+buyout+"</td><td>"+item[2]+"</td><td>"+leftTimeMap[item[3]]+"</td><td>"+item[4]+"</td><td>"+item[5]+"</td></tr>");
					}
				} else {
					isShowAll = true;
					$("#showAllTbl").hide();
					$("#showAllA").html("所有服务器+");
				}		
			});
			$('#allRealmMsg').html("");
			$('#allRealmCtlDiv').show();
			var calData=[];
			for (var i in data) {
				calData[i] = data[i][1];
			}
			$('#allRealmMsg').html("所有服务器平均价格:"+Bnade.getGold(Bnade.getResult(calData).avg));					
			$("#showAllA").click();
		}
		$('#msg').html("");
	});
}
function fuzzyQueryItems(realm, petName) {
	$.get('wow/pet/name/' + encodeURIComponent(petName) + '?fuzzy=true', function(data) {		
		if (data.length === 0) {
			$('#msg').html("找不到物品:" + $("#petName").val());
		} else {
			$("#fuzzyItemsList").show();
			$("#fuzzyItemsList").html("<li class='active'><a href='javascript:void(0)'>物品名</a></li>");					
			for (var i in data) {
				var id = "fuzzyItem" + i;						
				$("#fuzzyItemsList").append("<li><a href='javascript:void(0)' id='" + id + "'>" + data[i] + "</a></li>");
				$("#" + id).click(function() {
					$("#petName").val($(this).html());
					$("#queryBtn").click();
				});
			}	
			$('#msg').html("");
		}		
	}).fail(function() {
		$('#msg').html("查询出错");
    });
}	
function clear(){
	$('#past24CtlDiv').hide();
	$('#past24Msg').hide();
	$('#pastWeekCtlDiv').hide();
	$('#pastWeekMsg').hide();
	$('#allRealmCtlDiv').hide();
	$('#allRealmMsg').hide();
	$("#fuzzyItemsTbl").hide();
}
function queryByUrl(){
	var name = getUrlParam("name");
	if (name !== null && name !== "") {
		$('#petName').val(name);
		$("#queryBtn").click();
	}		
}	
$(document).ready(function() {
	$("#queryBtn").click(function() {
		query();
	});
	$("#fuzzyQueryBtn").click(function() {
		var realm = $("#realm").val();
		var petName = $("#petName").val();
		if(petName === ""){
			$('#msg').html("宠物名不能为空");
		} else {	
			$('#msg').html("正在查询，请稍等...");
			$('#pastWeekMsg').html("");	
			$('#allRealmMsg').html("");
			fuzzyQueryItems(realm, petName);			
		}
	});		
	!function(){
		$('#past24CtlDiv').hide();
		$('#pastWeekCtlDiv').hide();
		$('#allRealmCtlDiv').hide();
		$("#fuzzyItemsTbl").hide();
		queryByUrl();
	}();	
});