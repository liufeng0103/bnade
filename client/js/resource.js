(function(){
	var _Rs = function() {
		this.API_ROOT = "./wow";
	};
	
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
	
	_Rs.prototype.getRealmSummary = function() {
		return get(this.API_ROOT + "/auction/realms/summary");
	};
	
	window.resource = new _Rs();
})();