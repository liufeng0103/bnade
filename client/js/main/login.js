(function(BN) {
	BN.Login = {};
	BN.Login.check = function() {
		var nickname = BN.Cookie.get("nickname");
		if (nickname) {
			return true;
		} else {
			return false;
		}
	};
	BN.Login.refreshUI = function() {
		if (BN.Login.check()) {
			var html = "<a class='dropdown-toggle' href='#' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>";
			html += decodeURIComponent(BN.Cookie.get("nickname"));
			html += "<span class='caret'></span></a>";
			html += "<ul class='dropdown-menu'>";
			html += "<li><a href='signOut.do'>退出</a></li>";
			html += "</ul>";
			$("#login").addClass("dropdown");
			$("#login").html(html);
		} else {
			$("#login").html("<a href='login.do'>QQ登录</a>");
			$("#login").removeClass("dropdown");
		}
	};
})(window.BN = window.BN || {});

BN.Login.refreshUI();