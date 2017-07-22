var API_HOST = "https://api.bnade.com";

/**
 * 物品相关
 */
var itemResource = {
		
	/**
	 * 通过ajax get url内容
	 * @param url
	 * @returns 物品列表
	 */
	search : function(url) {
		var result;
		$.ajax({
			url : url,
			async : false, // 同步
			crossDomain : true == !(document.all), // 解决IE9跨域访问问题
			success : function(data) {
				result = data;
			},
			error : function(xhr) {
				if (xhr.status === 404) {
					$('#msg').text("物品找不到");
				} else if (xhr.status === 500) {
					$('#msg').text("服务器错误");
				} else {
					$('#msg').text("未知错误");
				}
			}
		});
		return result;
	},
	
	/**
	 * 通过物品名搜索
	 * @param name 物品名
	 */
	searchByName : function(name) {
		var url = API_HOST + "/items?name=" + encodeURIComponent(name);
		return itemResource.search(url);
	},
	
	/**
	 * 通过物品id搜索
	 * @param id 物品id
	 */
	searchById : function(id) {
		var url = API_HOST + "/items/" + id;
		return itemResource.search(url);
	}
}

var auctionResource = {
	searchCheapestAuctions : function() {
		
	}
}

var gblItemId = 0;

function clear(){
	$('#past24CtlDiv').hide();
	$("#past24Msg").text("");
	$('#pastWeekCtlDiv').hide();
	$("#pastWeekMsg").text("");
	$('#allRealmCtlDiv').hide();	
	$("#allRealmMsg").text("");			
	$('#msg').text("");
	$('#itemDetail').text("");
	$("#queryByUrl").text("");
}

function getPast24(realm, item) {
	if (realm === undefined || realm === null) {
		return;
	}
	$('#past24Msg').text("正在查询24小时内数据,请稍等...");
	var bonusList = "";
	if (item.bonusList != "" && item.bonusList != null) {
		bonusList += "?bl=" + item.bonusList;
	}
	$.get("/wow/auction/past/realm/" + realm.id + "/item/" + item.id + bonusList,function(data) {
		if (data.length === 0) {
			$('#past24Msg').text("24小时内数据找不到");
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
			$('#past24MinBuyout').text(tmpMinBuyout);
			$('#past24MaxBuyout').text(tmpMaxBuyout);
			$('#past24AvgBuyout').text(avgBuyout);
			$('#past24AvgQuantity').text(avgQuantity);
			$('#past24LatestBuyout').text(latestBuyout);				
			loadChart('past24Container','24小时内价格走势',item.name,chartLabels,chartMinBuyout,tmpMinBuyout,tmpMaxBuyout,true,'areaspline',chartQuantity,'spline');
			$('#past24Msg').text("");
		}			
	}).fail(function() {
		$("#msg").text("24小时数据查询出错");
    });
}
function loadChart(containerId,title,subtitle,chartLabels,chartMinBuyout,minBuyout,maxBuyout,showxAxisLabel,series1Type,chartQuantity,series2Type){
	minBuyout = 0;
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
function getPastWeek(realm, item){
	if (realm === undefined || realm === null) {
		return;
	}
	$('#pastWeekMsg').text("正在查询物品所有历史数据, 请稍等...");
	var bonusList = "";
	if (item.bonusList != "" && item.bonusList != null) {
		bonusList += "?bl=" + item.bonusList;
	}
	$.get("/wow/auction/history/realm/" + realm.id + "/item/" + item.id + bonusList, function(data) {
		if (data.length === 0) {
			$('#pastWeekMsg').text("历史数据未找到");					
			$('#pastWeekCtlDiv').hide();
		} else {
			// 保存服务器名
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.realm.key, realm.name);
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
			$('#historyMin').text(minBuy);
			$('#historyMax').text(maxBuy);
			$('#historyAvg').text(avgBuy);
			$('#historyAvgQuantity').text(parseInt(quantitySum/data.length));

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
	                text : '['+item.name+']在服务器['+realm.name+']的历史价格信息'
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
			$('#pastWeekMinBuyout').text(Bnade.getGold(weekResult.min));
			$('#pastWeekMaxBuyout').text(Bnade.getGold(weekResult.max));
			$('#pastWeekAvgBuyout').text(Bnade.getGold(weekResult.avg));
			$('#pastWeekAvgQuantity').text(parseInt(weekQuantity/weekCount));
			loadHeatMapChart('pastWeekBuyoutHeatMapContainer','一周内'+item.name+'价格热力图',heatMapLabels,heatMapBuyoutData,Bnade.getGold(weekResult.min),'一口价',0);
			loadHeatMapChart('pastWeekQuantityHeatMapContainer','一周内'+item.name+'数量热力图',heatMapLabels,heatMapQuantityData,weekMinQuantity,'数量',8);
			$('#pastWeekMsg').text("");
		}			
	}).fail(function() {
		$("#pastWeekMsg").text("历史数据查询出错");
    });
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

function getItemByAllRealms(item) {
	$('#allRealmMsg').text("正在查询所有服务器数据,请稍等...");
	var url = API_HOST + "/cheapest-auctions?itemId=" + item.id + (item.bonusList === null || item.bonusList === "" ? "" : "&bonusList=" + item.bonusList);
	
	$.ajax({
		url : url,
		crossDomain : true == !(document.all), // 解决IE9跨域访问问题
		success : function(data) {
			// 排序，价格有低到高
			data.sort(function(a, b) { 
				return a.buyout - b.buyout;
			});
			
			$('#allRealmCtlDiv').show();

			// 构建table的数据
			var columns = ["#", "服务器", "最低一口价", "数量", "总数量", "卖家", "服务器人气", "剩余时间"];
			var dataSet = [];
			// ，存在的服务器标记1，0表示服务器不存在
			/**
			 * 标记服务器
			 * 数组从0开始，所以定义171长度
			 * 遍历数据把数组值设为1
			 * 遍历值为0的下标，表示该服务器没有该物品数据
			 */
			for(var realmMark  = [], n = 1; n <= 170; realmMark[n++] = 0);
			// 找到用户搜索的服务器的行
			var realmIndex = -1;
			var num = 0;
			for (var i in data) {
				var auc = data[i];
				num =parseInt(i) + 1;
				var realmName = Realm.getConnectedById(auc.realmId);
				// 对查询服务器标红，直接获取realm值不太好，有机会重构考虑修改
				if ($("#realm").val() != "" && realmName.indexOf($("#realm").val()) >= 0) {
					realmIndex = i;
				}
				var realmColumn = "<a href='/itemQuery.jsp?itemName="
						+ item.name
						+ "&realm="
						+ encodeURIComponent(realmName)
						+ "'>"
						+ realmName + "</a>"
				var buyout = Bnade.getGold(auc.buyout);
				var ownerColumn = "<a href='/page/auction/owner/"
						+ encodeURIComponent(auc.owner) + "/" + auc.realmId
						+ "' target='_blank'>" + auc.owner + "</a>";
				var timeLeft = leftTimeMap[auc.timeLeft];
				var totalQuantityColumn = "<a href='javascript:void(0)' data-toggle='modal' data-target='#itemAucsModal' data-realm-id='"
						+ auc.realmId
						+ "' data-item-id='"
						+ item.id
						+ "' data-bonus-list='"
						+ item.bonusList
						+ "' data-item-level='"
						+ item.level
						+ "'>" + auc.totalQuantity + "</a>";
				dataSet.push( [num, realmColumn, buyout, auc.quantity, totalQuantityColumn, ownerColumn, auc.ownerQuantity,timeLeft]);
				
				realmMark[auc.realmId] = 1;
			}
			for (var i = 1; i < realmMark.length; i++) {
				if (realmMark[i] == 0) {
					var ownerQuantity = Realm.getOwnerQuantityById(i);
					dataSet.push( [++num, Realm.getConnectedById(i), "N/A", "N/A", "N/A", "N/A", ownerQuantity === -1 ? "N/A" : ownerQuantity,"N/A"]);
				}
			}
			$('#tableContainer').html('<table id="cheapestAuctionTbl" class="table table-hover"></table>');
			/**
			 * 生成table的header和body
			 */
			var tableGenerator = {
				/**
				 * 生成table的header
				 * @param columns 字符串数组
				 * @returns {String}
				 */
				header : function(columns) {
					var header = "<thead><tr>";
					for (var i in columns) {
						header += "<th>" + columns[i] + "</th>";
					}
					header += "</tr></thead>";
					return header;
				},
				/**
				 * 生成table的body
				 * @param dataSet 二维数组
				 * @returns {String}
				 */
				body : function(dataSet) {
					var body = "<tbody>";
					for (var i in dataSet) {
						body += realmIndex == i ? "<tr class='danger'>" : "<tr>";
						for (var j in dataSet[i]) {
							body += "<td>" + dataSet[i][j] + "</td>";
						}
						body += "</tr>";
					}
					body += "</tbody>";
					return body
				}
			}
			$("#cheapestAuctionTbl").html(tableGenerator.header(columns) + tableGenerator.body(dataSet));
			sortableTable();
			$('th').css("cursor", "pointer");
			
			// 构建图表
			var chartLabels = [];
			var chartBuyoutData = [];
			var chartQuantityData = [];
			// 严重扰乱市场的人，计算时剔除这些人计算
			var fuckOwner = [ "贼面贼霸", "冲进女澡堂", "漏屁屁火星人" ];
			var calData = [];
			var calDataCount = 0;
			var quantitySum = 0;
			for (var i in data) {
				var auc = data[i];
				var realm = Realm.getConnectedById(auc.realmId);
				var buyout = auc.buyout;
				var buyoutOwner = auc.owner;
				var quantity = auc.quantity;
				var totalQuantity = auc.totalQuantity;
				if (fuckOwner.indexOf(buyoutOwner) == -1) {
					calData[calDataCount++] = buyout;
				}
				chartLabels[i] = realm;
				chartBuyoutData[i] = Bnade.getGold(buyout);
				chartQuantityData[i] = totalQuantity;
				quantitySum += totalQuantity;
			}
			var result = Bnade.getResult(calData);
			var minBuy = Bnade.getGold(result.min);
			var maxBuy = Bnade.getGold(result.max);
			var avgBuy = Bnade.getGold(result.avg);
			$('#allMinBuyout').text(minBuy);
			$('#allMaxBuyout').text(maxBuy);
			$('#allAvgBuyout').text(avgBuy);
			$('#allAvgQuantity').text(parseInt(quantitySum / data.length));
			$('#allRealmMsg').text("所有服务器平均价格:" + avgBuy);
			loadChart('allRealmContainer', item.name + '在各服务器的价格和数量',
					item.name, chartLabels, chartBuyoutData, minBuy,
					avgBuy * 2 < maxBuy ? avgBuy * 2 : maxBuy, false,
					'spline', chartQuantityData, 'column');
		
		
		},
		error : function(xhr) {
			$('#allRealmCtlDiv').hide();
			if (xhr.status === 404) {
				$('#allRealmMsg').text("数据找不到");
			} else if (xhr.status === 500) {
				$('#allRealmMsg').text("服务器错误");
			} else {
				$('#allRealmMsg').text("未知错误");
			}
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
			store.set(BnadeLocalStorage.lsItems.realm.key, obj);
			BnadeLocalStorage.refresh();
			alert("服务器保存数量设置成功");
		}else{
			alert("请在物品名框输入正确的正整数");
		} 
	}else if(code=="itemCount"){
		if(parseInt(value)==value&&value>=0){
			var obj=JSON.parse(localStorage.getItem(BnadeLocalStorage.lsItems.item.key));
			obj.length=value;				
			store.set(BnadeLocalStorage.lsItems.item.key, obj);
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

function loadTopItems() {
	$.get(API_HOST + '/items/search-statistics', function(data) {
		var dailyI = 1;
		var weeklyI = 1;
		var monthlyI = 1;
		$("#dailyHot").html("<div id='dailyHotList' class='list-group'></div>");
		$("#weeklyHot").html("<div id='weeklyHotList' class='list-group'></div>");			
		$("#monthlyHot").html("<div id='monthlyHotList' class='list-group'></div>");
		for(var i in data){
			if (data[i].type === 3) {
				var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].itemName+"'>"+(monthlyI++)+" "+data[i].itemName;
				if (monthlyI <= 4) {
					listItem +=" <span class='badge'>"+data[i].searchCount+"</span></a>";
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
				var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].itemName+"'>"+(weeklyI++)+" "+data[i].itemName;
				if (weeklyI <= 4) {
					listItem +=" <span class='badge'>"+data[i].searchCount+"</span></a>";
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
				var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].itemName+"'>"+(dailyI++)+" "+data[i].itemName;
				if (dailyI <= 4) {
					listItem +=" <span class='badge'>"+data[i].searchCount+"</span></a>";
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
	$('#msg').text("正在模糊查询物品信息,请稍等...");
	$.get('/wow/item/name/' + encodeURIComponent(itemName) + '?fuzzy=true', function(data) {		
		if (data.length === 0) {
			$('#msg').text("找不到物品:" + itemName);
		} else {
			$("#fuzzyItemsList").show();
			$("#fuzzyItemsList").html("<li class='active'><a href='javascript:void(0)'>物品名</a></li>");					
			for (var i in data) {
				var id = "fuzzyItem" + i;						
				$("#fuzzyItemsList").append("<li><a href='javascript:void(0)' id='"+id+"'>"+data[i]+"</a></li>");
				$("#" + id).click(function() {
					$("#itemName").val($(this).text());							
					$("#queryBtn").click();						
				});
			}	
			$('#msg').text("");
		}		
	}).fail(function() {
		$("#msg").text("模糊查询出错");
    });
}

function loadItemDetail(item) {
	$('#itemDetail').text("");
	var bl = item.bonusList === "" ? "" : "?bonusList=" + item.bonusList;
	var url = API_HOST + "/items/" + item.id + "/tooltips" + bl;
	$.ajax({
		url : url,
		crossDomain : true == !(document.all), // 解决IE9跨域访问问题
		success : function(data) {
			$('#itemDetail').html("<p>物品ID：" + item.id + "</p>" + data);
		},
		error : function(xhr) {
			if (xhr.status === 404) {
				$('#itemDetail').text("物品信息找不到");
			} else if (xhr.status === 500) {
				$('#itemDetail').text("服务器错误");
			} else {
				$('#itemDetail').text("未知错误");
			}
		}
	});
}

/**
 * 物品查询框自动补全
 * 用到了jquery ui的autocomplete插件
 */
$("#itemName").autocomplete({
	source : function(request, response) {
		// 判断是否全中文， 减少不必要的搜索
		var reg=/^[\u4E00-\u9FA5]+$/;
		// 包含：时，如设计图：魔钢长剑
		var reg2 = /：|\+/; 
		if (reg.test(request.term) || reg2.test(request.term)) {
			$.ajax({
				url : API_HOST + "/items/names",
				data : {
					search : request.term
				},
				dataType : "json",
				crossDomain : true == !(document.all), // 解决IE9跨域访问问题
				success : response,
				error : function() {
					response([]);
				}
			});
		} else {
			response([]);
		}
	}
});
/**
 * 查询
 */
function query() {
clear(); // 隐藏所有div
	
	/**
	 * 获取服务器信息
	 */
	var realmName = $("#realm").val();
	var realm = null;
	if (realmName !== "") {
		var realmId = Realm.getIdByName(realmName);
		if (realmId > 0) {
			realm = {
				id : realmId,
				name : realmName
			};
		} else {
			$("#msg").text("找不到务器: " + realmName);
			return;
		}
	}
	
	/**
	 * 物品查询
	 */
	var nameOrId = $("#itemName").val();	
	if (nameOrId === "") {
		$("#msg").text("物品名不能为空");
	} else {	
		$('#itemListByName').empty();
		var items;
		if (parseInt(nameOrId, 10) == nameOrId) { // 数字为物品id
			var item = itemResource.searchById(nameOrId);
			if (item !== null) {
				items = [item];
			}
		} else {
			items = itemResource.searchByName(nameOrId);
		}
		if (items !== null || items !== undefined) {
			/**
			 * 如果查询的物品唯一，直接查询拍卖信息
			 * 如果多类型，需要用户选择后查询
			 */
			if (items.length === 1) {
				var item = items[0];
				BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.item.key, item.name); // 保存到物品名到localStorage
				if (item.bonusLists.length === 0 || item.bonusLists.length === 1) {
					item.bonusList = item.bonusLists.length === 0 ? "" : item.bonusLists[0];
					
					loadItemDetail(item);
					getItemByAllRealms(item);
					
					

					getPast24(realm, item);
					getPastWeek(realm, item);
					
					var url = window.location.protocol + "//" + window.location.host + window.location.pathname + "?itemName=" + encodeURIComponent(item.name);
					if (realm !== null) {
						url += "&realm=" + encodeURIComponent(realm.name);
					}			
					$("#queryByUrl").html("快速查询URL: <a href='" + url + "'>" + url + "</a>");
				} else { // 多bonusList
					$('#msg').text("发现" + item.bonusLists.length + "种物品类型(*表示带插槽)");
					// 拼接html
					// 格式分带槽和不带槽分类
					var itemsHtml = "<ul class='list-inline'>";
					for (var i in item.bonusLists) {
						item.bonusList = item.bonusLists[i];
						itemsHtml += "<li><a class='auctionQuery' href='javascript:void(0)' data-item='" + JSON.stringify(item) + "'>" + Bnade.getBonusDesc(item.bonusList, item.level).trim() + "</a></li>";
					}
					itemsHtml += "</ul>";
					$('#itemListByName').html(itemsHtml);
				}
			} else if (items.length > 1) { // 相同物品名，不同id
				BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.item.key, items[0].name);
				$('#msg').text("发现" + items.length + "个同名物品,请选择一个查询");
				var itemsHtml = "<ul class='list-inline'>";
				for (var i in items) {
					var item = items[i];
					itemsHtml += "<li><a class='auctionQuery' href='javascript:void(0)' data-item-id='" + item.id + "' data-item-name='" + item.name + "' data-item-bonus-list=''>" + item.name + item.id + "</a></li>"; 
				}
				itemsHtml += "</ul>";
				$('#itemListByName').html(itemsHtml);
			} else { // 小于1的情况，正常不会出现，以防万一
				$('#msg').text("物品找不到");
			}
			
			$('.auctionQuery').click(function() {
				var item = $(this).data("item");				
				loadItemDetail(item);
				getItemByAllRealms(item);
				getPast24(realm, item);
				getPastWeek(realm, item);
			});
		}
	}
}
/**
 * 查询按钮单击事件
 */
$("#queryBtn").click(function() {
	query();
});

$("#itemFuzzyQueryBtn").click(function(){
	var itemName=$("#itemName").val();
	if(itemName==""){
		$('#msg').text("物品名不能为空");
	} else {			
		fuzzyQueryItems(itemName);			
	}
});

$('#past24CtlDiv').hide();
$('#pastWeekCtlDiv').hide();
$('#allRealmCtlDiv').hide();		
$("#fuzzyItemsList").hide();
queryByUrl();
loadTopItems();

/**
 * 把同一卖家，一口价相同的同一类物品整合到一起
 */
function foldAuctionsByOwnerAndBuyoutAndBonusList(data) {
	if (data.length <= 1) {
		return data;
	}
	data.sort(function(a, b) {
		return a.buyout / a.quantity - b.buyout / b.quantity;
	});
	var result = [];
	var preAuc = data[0];
	for (var i = 1, j = data.length; i < j; i++) {
		var auc = data[i];
		if (preAuc.owner === auc.owner //同一卖家
				&& Bnade.getGold(preAuc.buyout / preAuc.quantity) === Bnade.getGold(auc.buyout / auc.quantity) // 一口单价一样
				&& preAuc.bonusList === auc.bonusList) { // 类型一样
			preAuc.bid += auc.bid;
			preAuc.buyout += auc.buyout;
			preAuc.quantity += auc.quantity;
		} else {
			result.push(preAuc);
			preAuc = auc;
		}
		if (i === data.length - 1) {
			result.push(preAuc);
		}
	}
	return result;
}

// 当模态框打开时获取物品的所有拍卖数据
$('#itemAucsModal').on('show.bs.modal', function (event) {
	var aLink = $(event.relatedTarget);
	var realmId = aLink.data('realm-id');
	var itemId = aLink.data('item-id');
	var itemLevel = aLink.data('item-level');
	var bonusList = aLink.data('bonus-list');
	var modal = $(this);
	var $modalBodyContent = modal.find('.modal-body-content');
	$modalBodyContent.text("正在查询，请稍等...");
	var url = API_HOST + "/auctions?realmId=" + realmId + "&itemId=" + itemId + "&bonusList=" + bonusList;	
	$.ajax({
		url : url,
		crossDomain : true == !(document.all), // 解决IE9跨域访问问题
		success : function(data) {
			if (data.length === 0) {
				$modalBodyContent.text("找不到物品:" + itemName);
			} else {
				var result = foldAuctionsByOwnerAndBuyoutAndBonusList(data);
				var tblHtml = "<table class='table table-striped table-condensed table-responsive'><thead><tr><th>#</th><th>玩家</th><th>服务器</th><th>竞价</th><th>一口价</th><th>数量</th><th>单价</th><th>剩余时间</th><th>说明</th></tr></thead><tbody>";
				for (var i in result) {
					var auc = result[i];
					tblHtml += "<tr><td>"+(parseInt(i) + 1)+"</td><td>"+auc.owner+"</td><td>" + auc.ownerRealm + "</td><td>" + Bnade.getGold(auc.bid) + "</td><td>"+Bnade.getGold(auc.buyout)+"</td><td>"+auc.quantity+"</td><td>"+Bnade.getGold(auc.buyout/auc.quantity)+"</td><td>"+leftTimeMap[auc.timeLeft]+"</td><td>"+Bnade.getBonusDesc(auc.bonusList, itemLevel)+"</td></tr>";
				}
				tblHtml += "</tbody></table>";				
				$modalBodyContent.html(tblHtml);
			}		
		},
		error : function(xhr) {
			if (xhr.status === 404) {
				$modalBodyContent.text("数据找不到");
			} else if (xhr.status === 500) {
				$modalBodyContent.text("服务器错误");
			} else {
				$modalBodyContent.text("未知错误");
			}
		}
	});
});