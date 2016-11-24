"use strict";
var BN = window.BN || {};

BN.Resource = new function() {
	var self = this;
	var API_ROOT = "/wow";

	var ERROR = -1;
	var msg = function(code,msg) {
		return "{'code':"+code+",'message':'"+msg+"'}";
	}
	
	var get = function(url) {
		var result;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
				result = data;
			},
			error : function(xhr) {
				result = msg(ERROR, "错误提示： " + xhr.status + " " + xhr.statusText);
			},
		});
		return result;
	};
	
	var post = function(url,data) {
		var result;
		$.ajax({
			url : url,
			type : "POST",
			data : data,
			async : false,
			success : function(data) {
				result = data;
			},
			error : function(xhr) {
				result = msg(ERROR, "错误提示： " + xhr.status + " " + xhr.statusText);
			},
		});
		return result;
	};
	
	self.checkResult = function(result) {
		if (!result || (typeof result === "object" && result.code === ERROR)) {
			return false;
		}
		return true;
	};

	self.getRealmSummary = function() {
		return get(API_ROOT + "/auction/realms/summary");
	};

	self.signOut = function() {
		return get(API_ROOT + "/users/signOut");
	};
	
	self.getUserRealms = function(success,error) {
		var url = API_ROOT + "/user/getRealms";
		if (success) {
			$.get(url, success).fail(error);
		} else {
			return get(url);
		}		
	};
	
	self.addUserRealm = function(realmId) {
		return post(API_ROOT + "/user/addRealm",{realmId:realmId});
	};
	
	self.deleteUserRealm = function(realmId) {
		return post(API_ROOT + "/user/deleteRealm",{realmId:realmId});
	};
	
	self.getItemsByName = function(name) {
		return get(API_ROOT + "/items?name="+name);
	};
	
	self.addUserItemNotification = function(data) {
		return post(API_ROOT + "/user/addItemNotification", data);
	};
	
	self.getUserItemNotifications = function(success,error) {
		var url = API_ROOT + "/user/getItemNotifications";		
		$.get(url, success).fail(error);		
	};
	
	self.deleteUserItemNotifications = function(data,success,error) {
		var url = API_ROOT + "/user/deleteItemNotifications";		
		$.ajax({
			url : url,
			type : "POST",
			data : data,
			contentType: "application/json",
			success : success,
			error : error,
		});
	};
	
	self.updateUserItemNotification = function(data,success,error) {
		var url = API_ROOT + "/user/updateItemNotification";		
		$.post(url,data,success).fail(error);		
	};
	
	self.updateEmailNotifications = function(data,success,error) {
		var url = API_ROOT + "/user/updateEmailNotifications";		
		$.ajax({
			url : url,
			type : "POST",
			data : data,
			contentType: "application/json",
			success : success,
			error : error,
		});
	};
	
	self.sendMailValidation = function(success,error) {
		var url = API_ROOT + "/user/sendMailValidation";		
		$.get(url, success).fail(error);		
	};
	
	self.addUserCharacter = function(character) {
		return post(API_ROOT + "/user/addCharacter",character);
	};
	
	self.deleteUserCharacter = function(character) {
		return post(API_ROOT + "/user/deleteCharacter",character);
	};
};