"use strict";
var BN = window.BN || {};

BN.UserRealmUI = new function() {
	var self = this;
	
	var getRow = function(realmId,realmName,lastModified) {
		var row = "<tr><td>{realmName}</td><td>{lastModified}</td><td><button class='btn btn-sm btn-danger removeRealm' realmid='{realmId}'><span class='glyphicon glyphicon-remove'></span> 删除</button></td></tr>";
		return row.replace("{realmId}",realmId).replace("{realmName}",realmName).replace("{lastModified}",lastModified);
	}
	
	self.init = function() {
		var realmSelect = $("#realmSlt");
		// 加载服务器数据
		var realms = BN.Realm.getAll();				
		for (var i in realms) {
			var realm = realms[i];
			realmSelect.append("<option value='"+realm.id+"'>"+realm.connected+"</option>");
		}
		
		// 加载用户已经设置的服务器
		var userRealms = BN.Resource.getUserRealms();
		if (BN.Resource.checkResult(userRealms)) {
			if (Array.isArray(userRealms)) {
				for (var i in userRealms) {
					var userRealm = userRealms[i];
					var realmId = userRealm.realmId;
					var realm = BN.Realm.getRealmById(realmId);
					var realmName = realm !== null ? realm.connected : "null";
					$("#realmTbl tbody").append(getRow(realmId,realmName,parseInt((new Date().getTime() - userRealm.lastModified)/60000)+"分钟前"));
				}					
			}
		} else {
			alert("出错：" + userRealms.message);
		}
		$("#searchBtn").click(function(){
			$("#msg").html("");
			var realmName = $("#realmInput").val();
			if (realmName === "") {
				$("#msg").html("请设置一个服务器");
			} else {
				var machRealms = BN.Realm.getRealmsByName(realmName);
				if (machRealms.length === 0) {
					$("#msg").html("未找到服务器："+realmName);
				} else {
					realmSelect.html("");
					for (var i in machRealms) {
						var realm = machRealms[i];
						realmSelect.append("<option value='"+realm.id+"'>"+realm.connected+"</option>");
					}
				}
			}
		});
		$("#addRealmBtn").click(function(){
			var realmId = realmSelect.val();
			var realmName = realmSelect.find("option:selected").text();
			if (realmId === "") {
				$("#msg").html("出错：无法获取服务器信息");
			} else {
				var result = BN.Resource.addUserRealm(realmId);
				if (result.code === 0) {
					$("#msg").html(realmName+"添加成功");
					$("#realmTbl tbody").append(getRow(realmId,realmName,""));
					$(".removeRealm").click(function(){
						if (confirm("确定要删除该服务器吗？")) {
							var result = BN.Resource.deleteUserRealm($(this).attr("realmId"));
							if (result.code === 0) {
								$("#msg").html("删除成功");
								$(this).parent().parent().remove();
							} else {
								$("#msg").html("删除服务器出错："+result.message);
							}
						}
					});
				} else {
					$("#msg").html("添加服务器出错："+result.message);
				}
			}
		});
		$(".removeRealm").click(function(){
			if (confirm("确定要删除该服务器吗？")) {
				var result = BN.Resource.deleteUserRealm($(this).attr("realmId"));
				if (result.code === 0) {
					$("#msg").html("删除成功");
					$(this).parent().parent().remove();
				} else {
					$("#msg").html("删除服务器出错："+result.message);
				}
			}
		});
	};
	
	self.test = function() {};
};
BN.UserRealmUI.init();