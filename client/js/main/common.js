"use strict";
var BN = window.BN || {};

BN.Cookie = new function() {
	var self = this;
	self.get = function(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i].trim();
			if (c.indexOf(name) === 0)
				return c.substring(name.length, c.length);
		}
		return "";
	};

	self.remove = function(name) {
		document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	};
};

BN.Util = new function() {
	var self = this;
	
	self.toDecimal = function(x) {
		var f=parseFloat(x);
		if(isNaN(f)){
			return;
		}
		return Math.floor(x*100)/100;// 舍位
	};
	
	self.getGold = function(value) {
		return value/10000;
	};
};

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};	

function isDomExist(obj) {
	if (obj.length > 0) {
		return true;
	}
	return false;
}

function toDecimal(x){
	var f=parseFloat(x);
	if(isNaN(f)){
		return;
	}
	return Math.floor(x*100)/100;// 舍位
}
function weekFormat(week){
	switch(week){
		case 1:return '一';
		case 2:return '二';
		case 3:return '三';
		case 4:return '四';
		case 5:return '五';
		case 6:return '六';
		default:return '日';
	}
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r !== null) return decodeURI (r[2]); return null;
}

/**
 * 简单封装localStorage
 */
var store = {
	set : function(key, val) {
		var str = val;
		if (typeof str === 'object') {
			str = JSON.stringify(val);
		}
		localStorage.setItem(key, str);
	},
	get : function(key) {
		return JSON.parse(localStorage.getItem(key));
	}
}

// 预防iframe盗用
if(window.top !== window.self){ window.top.location = window.location;}  

/**
 * 设置页面中的table列排序
 */
function sortableTable() {
	$(document).on('click','th',function(){
	    var table = $(this).parents('table').eq(0);
	    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));
	    this.asc = !this.asc;
	    if (!this.asc){rows = rows.reverse();}
	    table.children('tbody').empty().html(rows);
	});
	function comparer(index) {
	    return function(a, b) {
	        var valA = getCellValue(a, index), valB = getCellValue(b, index);
	        return $.isNumeric(valA) && $.isNumeric(valB) ?
	            valA - valB : valA.localeCompare(valB);
	    };
	}
	function getCellValue(row, index){
	    return $(row).children('td').eq(index).text();
	}
}
