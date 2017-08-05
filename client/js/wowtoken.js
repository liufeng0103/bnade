$(function(){
	var minBuy = 0;
	var maxBuy = 0;
	$("#msg").html("加载数据,请稍等...");
	$.get("wow/ah/wowtokens", function(data) {
		if (data.code === 201) {
			$("#msg").html("获取数据失败:" + data.errorMessage);
		} else {   
			if(data.length > 0) { // 获取24小时内最低和最高价
				var past24 = data[data.length-1][0] - 24*60*60*1000;

				for(var i in data) {
					var updated = data[i][0];
					var buy = data[i][1];
					if(updated >= past24) {
						if(minBuy === 0 || minBuy > buy) {
							minBuy = buy;
						}
						if(maxBuy === 0 || maxBuy < buy) {
							maxBuy = buy;
						}
					}
				}
				$("#msg").html("<h3>时光徽章</h3><h4>当前价格: " + data[data.length-1][1] + "G</h4><h4>更新时间: " + new Date(data[data.length-1][0]).format("MM-dd hh:mm:ss")+"</h4><h4>24小时内:</h4><h4>最低价格: "+minBuy+"G</h4><h4>最高价格: "+maxBuy+"G</h4>");
			}
			Highcharts.setOptions({lang:{
				months:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				shortMonths:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				weekdays:['星期日','星期一','星期二','星期三','星期四','星期五','星期六',]
			}});
			// 修正时间
			if(data.length > 0) {
				for(var j in data) {
					data[j][0] = data[j][0]+8*60*60*1000;           			
				}
			}
			var gaugeOptions = {
				chart: {
					type: 'solidgauge'
				},
				title: null,
				pane: {
					center: ['50%', '75%'],
					size: '100%',
					startAngle: -90,
					endAngle: 90,
					background: {
						backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
						innerRadius: '60%',
						outerRadius: '100%',
						shape: 'arc'
					}
				},
				tooltip: {
					enabled: false
				},
				// the value axis
				yAxis: {
					stops: [
						[0.1, '#55BF3B'], // green
						[0.5, '#DDDF0D'], // yellow
						[0.9, '#DF5353'] // red
					],
					lineWidth: 0,
					minorTickInterval: null,
					tickPixelInterval: 400,
					tickWidth: 0,
					title: {
						y: -110
					},
					labels: {
						y: 16
					}
				},
				plotOptions: {
					solidgauge: {
						dataLabels: {
							y: -50,
							borderWidth: 0,
							useHTML: true
						}
					}
				}
			};
			// The speed gauge
			$('#wowtokenBuyContainer').highcharts(Highcharts.merge(gaugeOptions, {
				yAxis: {
					min: minBuy,
					max: maxBuy,
					title: {
						text: '时光徽章'
					}
				},
				credits: {
					enabled: false
				},
				series: [{
					name: '时光徽章',
					data: [data[data.length-1][1]],
					dataLabels: {
						format: '<div style="text-align:center"><span style="font-size:25px;color:' +
							((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}G</span></div>'
					},
					tooltip: {
						valueSuffix: 'G'
					}
				}]

			}));
			$('#wowTokenContainer').highcharts('StockChart', {
				rangeSelector : {
					selected : 1,
					inputEnabled:false,
					buttons: [{
						type: 'day',
						count: 1,
						text: '日'
					},{
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
					text : '[时光徽章]的历史价格'
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
				series : [{
					name : '价格',
					type: 'area',
					data : data,		                
					tooltip: {
						valueDecimals: 0,
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
					},
					threshold: null
				}]
			});
		}
	}).fail(function() {
		$("#msg").html("数据加载出错");
	}); 
});