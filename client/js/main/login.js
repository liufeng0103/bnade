"use strict";
var BN = window.BN || {};

BN.Login = new function() {
	var self = this;
	self.check = function() {
		var nickname = BN.Cookie.get("nickname");
		if (nickname) {
			return true;
		} else {
			return false;
		}
	};
	self.refreshUI = function() {
		if (BN.Login.check()) {
			var html = "<a class='dropdown-toggle' href='#' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>";
			html += decodeURIComponent(BN.Cookie.get("nickname"));
			html += "(测试中)<span class='caret'></span></a>";
			html += "<ul class='dropdown-menu'>";
			html += "<li><a href='/page/user/mail '>修改邮箱</a></li>";
			html += "<li><a href='/userRealm.jsp'>我的服务器</a></li>";
			html += "<li><a href='/userItemNotification.jsp'>我的物品提醒</a></li>";
			html += "<li><a href='/signOut.do'>退出</a></li>";
			html += "</ul>";
			$("#login").addClass("dropdown");
			$("#login").html(html);
		} else {
			$("#login").html("<a href='/login.do'>QQ登录</a>");
			$("#login").removeClass("dropdown");
		}
	};
};

BN.Login.refreshUI();