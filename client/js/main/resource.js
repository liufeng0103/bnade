(function(BN) {
	var API_ROOT = "./wow";
	var get = function(url) {
		var result;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
				result = data;
			},
		});
		return result;
	};

	BN.Resource = {};

	BN.Resource.getRealmSummary = function() {
		return get(API_ROOT + "/auction/realms/summary");
	};
	
	BN.Resource.signOut = function() {
		return get(API_ROOT + "/users/signOut");
	};
	
})(window.BN = window.BN || {});