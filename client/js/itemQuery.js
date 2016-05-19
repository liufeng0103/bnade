function itemQueryByName(realm, itemName) {
	$.get('wow/item/name/' + encodeURIComponent(itemName), function(data) {					
		if (data.length === 0) {
			$('#msg').html("找不到物品:" + itemName);
		} else if(data.length === 1) {
			var item = data[0];
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.item.key, itemName);
			if (item.bonusList.length === 0 || item.bonusList.length === 1) {							
				accurateQuery(realm, item.id, itemName);
			} else {
				$('#msg').html('物品:' + itemName + ' 发现' + item.bonusList.length + '种版本,请选择下列表中的一种来查询');
				var tableHtml = "<table class='table table-striped'><thead><tr><th>ID</th><th>物品名</th><th>物品说明</th></tr></thead><tbody>";
				for (var i in item.bonusList) {
					var itemBonus = item.bonusList[i];
					tableHtml += "<tr><td>"+item.id+"</td><td><a href='javascript:void(0)' id='itemBonus"+i+item.id+"' itemId='"+item.id+"' bl='"+itemBonus+"'>"+item.name+"</a></td><td>"+Bnade.getBonusDesc(itemBonus)+"</td></tr>";
				}
				tableHtml += '</tbody></table>';
				$('#itemListByName').html(tableHtml);	
				for (var i in item.bonusList) {
					var itemBonus = item.bonusList[i];
					$("#itemBonus" + i + item.id).click(function() {
						var regItemId = $(this).attr('itemId')+"?bl="+$(this).attr('bl');
						var regItemName = $(this).html();
						accurateQuery(realm, regItemId, regItemName);									
					});
				}
			}						
		}else if (data.length > 1) {
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.item.key, itemName);
			$('#msg').html('发现' + data.length + '个有相同名字的物品,请选择下列表中的一个物品来查询');
			var tableHtml = "<table class='table table-striped'><thead><tr><th>ID</th><th>物品名</th><th>物品等级</th></tr></thead><tbody>";
			for(var i in data) {
				var item = data[i];
				tableHtml += "<tr><td>"+item.id+"</td><td><a href='javascript:void(0)' id='"+item.id+"'>"+item.name+"</a></td><td>"+item.itemLevel+"</td></tr>";
			}
			tableHtml += '</tbody></table>';
			$('#itemListByName').html(tableHtml);	
			for(var i in data){
				var item = data[i];
				$("#" + item.id).click(function() {
					var regItemId = $(this).attr('id');
					var regItemName = $(this).html();
					accurateQuery(realm, regItemId, regItemName);								
				});
			}
		} 		
	}).fail(function() {
		$("#msg").html("查询出错");
    });	
}
function itemQuery() {		
	clear();		
	if (!checkCommand()) {
		var itemName = $("#itemName").val();
		var realm = $("#realm").val();
		if (itemName === "") {
			$("#msg").html("物品名不能为空");
		} else {	
			$('#itemListByName').empty();
			// 如果物品时数字则当作物品ID来查询
			if (parseInt(itemName, 10) == itemName) {
				var id = itemName;
				itemQueryById(realm, id);
			} else {
				itemQueryByName(realm, itemName);
			}
		}
	}
}
function itemQueryById(realm, id) {
	$.get('wow/item/' + id, function(data) {
		if (data != null) {
			itemQueryByName(realm, data.name);			
		} else {
			$('#msg').html("通过ID找不到物品:" + id);
		}						
	});
}

function clear(){
	$('#past24CtlDiv').hide();
	$("#past24Msg").html("");
	$('#pastWeekCtlDiv').hide();
	$("#pastWeekMsg").html("");
	$('#allRealmCtlDiv').hide();	
	$("#allRealmMsg").html("");			
	$('#msg').html("");
	$('#itemDetail').html("");
}
var isShowAll=true;
function accurateQuery(realm, itemId, itemName) {		
	$("#showAllTbl").hide();
	$("#showAllA").html("显示全部+");
	loadItemDetail(itemId);
	if (realm !== "") {
		var realmId = Realm.getIdByName(realm);
		if (realmId > 0) {
			getPast24(realmId, realm, itemId, itemName);
			getPastWeek(realmId, realm, itemId, itemName);
		} else {
			$('#msg').html("找不到服务器：" + realm);
		}		
	}		
	getItemByAllRealms(itemId, itemName);
	var url = window.location.protocol + "//" + window.location.host + window.location.pathname + "?itemName=" + encodeURIComponent(itemName);
	if (realm != null && realm != '') {
		url += "&realm=" + encodeURIComponent(realm);
	}			
	$("#queryByUrl").html("快速查询URL: <a href='" + url + "'>" + url + "</a>");
}

function getPast24(realmId, realm, itemId, itemName) {		
	$('#past24Msg').html("正在查询24小时内数据,请稍等...");
	$.get("wow/auction/past/realm/" + realmId + "/item/" + itemId,function(data) {
		if (data.length === 0) {
			$('#past24Msg').html("24小时内数据找不到");
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
			$('#past24MinBuyout').html(tmpMinBuyout);
			$('#past24MaxBuyout').html(tmpMaxBuyout);
			$('#past24AvgBuyout').html(avgBuyout);
			$('#past24AvgQuantity').html(avgQuantity);
			$('#past24LatestBuyout').html(latestBuyout);				
			loadChart('past24Container','24小时内价格走势',itemName,chartLabels,chartMinBuyout,tmpMinBuyout,tmpMaxBuyout,true,'areaspline',chartQuantity,'spline');
			$('#past24Msg').html("");
		}			
	}).fail(function() {
		$("#msg").html("24小时数据查询出错");
    });
}
function loadChart(containerId,title,subtitle,chartLabels,chartMinBuyout,minBuyout,maxBuyout,showxAxisLabel,series1Type,chartQuantity,series2Type){
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
function getPastWeek(realmId, realm, itemId, itemName){
	$('#pastWeekMsg').html("正在查询物品所有历史数据, 请稍等...");
	$.get("wow/auction/history/realm/" + realmId + "/item/" + itemId, function(data) {
		if (data.length === 0) {
			$('#pastWeekMsg').html("历史数据未找到");					
			$('#pastWeekCtlDiv').hide();
		} else {
			// 保存服务器名
			BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.realm.key, realm);
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
			$('#historyMin').html(minBuy);
			$('#historyMax').html(maxBuy);
			$('#historyAvg').html(avgBuy);
			$('#historyAvgQuantity').html(parseInt(quantitySum/data.length));

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
	                text : '['+itemName+']在服务器['+realm+']的历史价格信息'
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
			$('#pastWeekMinBuyout').html(Bnade.getGold(weekResult.min));
			$('#pastWeekMaxBuyout').html(Bnade.getGold(weekResult.max));
			$('#pastWeekAvgBuyout').html(Bnade.getGold(weekResult.avg));
			$('#pastWeekAvgQuantity').html(parseInt(weekQuantity/weekCount));
			loadHeatMapChart('pastWeekBuyoutHeatMapContainer','一周内'+itemName+'价格热力图',heatMapLabels,heatMapBuyoutData,Bnade.getGold(weekResult.min),'一口价',0);
			loadHeatMapChart('pastWeekQuantityHeatMapContainer','一周内'+itemName+'数量热力图',heatMapLabels,heatMapQuantityData,weekMinQuantity,'数量',8);
			$('#pastWeekMsg').html("");
		}			
	}).fail(function() {
		$("#pastWeekMsg").html("历史数据查询出错");
    });
}
function getItemByAllRealms(itemId,itemName){
	$('#allRealmMsg').html("正在查询所有服务器数据,请稍等...");
	$.get('wow/auction/item/'+itemId,function(data){	
		if(data.code==404){
			$('#allRealmMsg').html("查询所有服务器失败:"+data.errorMessage);
			$('#allRealmCtlDiv').hide();
		}else{
			$('#allRealmCtlDiv').show();					
			data.sort(function(a,b){
				return a[1]-b[1];
			});
			var isShowAll=true;
			$("#showAllA").click(function(){
				if(isShowAll){
					isShowAll=false;
					$("#showAllTbl").show();
					$("#showAllA").html("显示全部-");			
					$("#showAllBody").empty();
					var ownerItemUrl = "";
					for(var i in data){				
						var itemArr=data[i];
						var realm=Realm.getConnectedById(itemArr[0]);
						var realmColumnClass="";								
						if ($("#realm").val() != "" && realm.indexOf($("#realm").val()) >= 0) {
							realmColumnClass = "class='danger'";									
						}
						var buyout=Bnade.getGold(itemArr[1]);						
						$("#showAllBody").append("<tr "+realmColumnClass+"><td>"+(parseInt(i)+1)+"</td><td>"+realm+"</td><td>"+buyout+"</td><td><a href='/ownerQuery.html?realm="+encodeURIComponent(Realm.getNameById(itemArr[0]))+"&owner="+encodeURIComponent(itemArr[2])+"'  target='_blank'>"+itemArr[2]+"</a></td><td>"+leftTimeMap[itemArr[5]]+"</td><td>"+itemArr[3]+"</td><td>"+new Date(itemArr[4]).format("MM-dd hh:mm:ss")+"</td></tr>");
					}
				}else{
					isShowAll=true;
					$("#showAllTbl").hide();
					$("#showAllA").html("显示全部+");
				}		
			});
							
			var chartLabels=[];
			var chartBuyoutData=[];
			var chartQuantityData=[];

			var calData=[];
			var quantitySum=0;
			for(var i in data){
				var realm=Realm.getConnectedById(data[i][0]);					
				var buyout=data[i][1];
				var buyoutOwner=data[i][2];
				var quantity=data[i][3];
				var updated=data[i][4];
				calData[i]=buyout;
				chartLabels[i]=realm;
				chartBuyoutData[i]=Bnade.getGold(buyout);
				chartQuantityData[i]=quantity;
				quantitySum+=quantity;
			}			
			var result = Bnade.getResult(calData);
			var minBuy = Bnade.getGold(result.min);
			var maxBuy = Bnade.getGold(result.max);
			var avgBuy = Bnade.getGold(result.avg);
			$('#allMinBuyout').html(minBuy);
			$('#allMaxBuyout').html(maxBuy);
			$('#allAvgBuyout').html(avgBuy);
			$('#allAvgQuantity').html(parseInt(quantitySum/data.length));				
			$('#allRealmMsg').html("所有服务器平均价格:"+avgBuy);				
			loadChart('allRealmContainer',itemName+'在各服务器的价格和数量',itemName,chartLabels,chartBuyoutData,minBuy,avgBuy * 2 < maxBuy ? avgBuy * 2 : maxBuy, false, 'spline',chartQuantityData,'column');
			$("#showAllA").click();				
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
			localStorage.setItem(BnadeLocalStorage.lsItems.realm.key,JSON.stringify(obj));
			BnadeLocalStorage.refresh();
			alert("服务器保存数量设置成功");
		}else{
			alert("请在物品名框输入正确的正整数");
		} 
	}else if(code=="itemCount"){
		if(parseInt(value)==value&&value>=0){
			var obj=JSON.parse(localStorage.getItem(BnadeLocalStorage.lsItems.item.key));
			obj.length=value;				
			localStorage.setItem(BnadeLocalStorage.lsItems.item.key,JSON.stringify(obj));
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
function loadTopItems(){
	$.get('wow/item/hot', function(data) {
		if (data.length != 0){
			var dailyI = 1;
			var weeklyI = 1;
			var monthlyI = 1;
			$("#dailyHot").html("<div id='dailyHotList' class='list-group'></div>");
			$("#weeklyHot").html("<div id='weeklyHotList' class='list-group'></div>");			
			$("#monthlyHot").html("<div id='monthlyHotList' class='list-group'></div>");
			for(var i in data){
				if (data[i].type === 3) {
					var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].name+"'>"+(monthlyI++)+" "+data[i].name;
					if (monthlyI <= 4) {
						listItem +=" <span class='badge'>"+data[i].queried+"</span></a>";
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
					var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].name+"'>"+(weeklyI++)+" "+data[i].name;
					if (weeklyI <= 4) {
						listItem +=" <span class='badge'>"+data[i].queried+"</span></a>";
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
					var listItem = "<a id='topItem"+i+"' class='list-group-item' href='javascript:void(0)' itemName='"+data[i].name+"'>"+(dailyI++)+" "+data[i].name;
					if (dailyI <= 4) {
						listItem +=" <span class='badge'>"+data[i].queried+"</span></a>";
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
	$('#msg').html("正在模糊查询物品信息,请稍等...");
	$.get('wow/item/name/' + encodeURIComponent(itemName) + '?fuzzy=true', function(data) {		
		if (data.length === 0) {
			$('#msg').html("找不到物品:" + itemName);
		} else {
			$("#fuzzyItemsList").show();
			$("#fuzzyItemsList").html("<li class='active'><a href='javascript:void(0)'>物品名</a></li>");					
			for (var i in data) {
				var id = "fuzzyItem" + i;						
				$("#fuzzyItemsList").append("<li><a href='javascript:void(0)' id='"+id+"'>"+data[i]+"</a></li>");
				$("#" + id).click(function() {
					$("#itemName").val($(this).html());							
					$("#queryBtn").click();						
				});
			}	
			$('#msg').html("");
		}		
	}).fail(function() {
		$("#msg").html("模糊查询出错");
    });
}
function loadItemDetail(itemId) {
	$('#itemDetail').html("");
	if (itemId.toString().indexOf("?") > -1) {
		itemId += "&tooltip=true";
	} else {
		itemId += "?tooltip=true";
	}
	$.get('wow/item/' + itemId, function(data) {
		if (data.code === 201) {
			$('#msg').append("物品信息查询失败:" + data.errorMessage);								
		} else {
			$('#itemDetail').html(data);
		}
	}).fail(function() {
		$("#msg").html("物品信息查询出错");
    });
}
$(document).ready(function() {
	$("#queryBtn").click(function() {
		itemQuery();
	});
	$("#itemFuzzyQueryBtn").click(function(){
		var itemName=$("#itemName").val();
		if(itemName==""){
			$('#msg').html("物品名不能为空");
		} else {			
			fuzzyQueryItems(itemName);			
		}
	});	
	!function() { // 页面加载运行
		$('#past24CtlDiv').hide();
		$('#pastWeekCtlDiv').hide();
		$('#allRealmCtlDiv').hide();		
		$("#fuzzyItemsList").hide();	
		queryByUrl();
		loadTopItems();
	}();	
});