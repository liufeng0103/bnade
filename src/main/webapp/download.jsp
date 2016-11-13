<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界插件下载</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<h3 class="sub-header">魔兽世界插件(每日凌晨4点会更新)</h3>
			<a href="./Bnade.zip" class="btn btn-primary">插件下载</a>
			<p>通过物品在所有服务器的价格计算的参考价格，玩家可以通过插件在游戏的提示框中查看到参考价格</p>
			<p>插件的一些说明：</p>
			<ul>
				<li>提示框中显示物品的参考价格</li>
				<li>目前不支持宠物价格和各阶制造业装备价格的查询</li>
				<li>由于价格文件是固定在插件中的，所以时间越久参考价值越低， 我会定期更新数据发布最新的插件到这个页面</li>
				<li>参考价格算法(跟网站所有服务器平均价格一样):
					<ol>
						<li>获取物品在所有服务器的价格，并从低到高排序</li>
						<li>计算前80%的价格的平均值作为参考价格，如果参考服务器数小于85时，比较历史价格，取低的那个</li>
						<li>如果平均价格的3倍小于80%的最高价格，则继续取80%数据的80%按第2步骤计算，直到获取到参考价格</li>
					</ol>
				</li>
			</ul>
			<p>安装说明：</p>
			<ol>
				<li>解压bnade.zip文件</li>
				<li>复制Bnade文件夹到魔兽世界安装目录的\Interface\AddOns中</li>
				<li>启动游戏，在聊天框看到打印的数据更新时间或鼠标移动到可拍卖物品的参考价格(如下图)，则表示安装成功</li>
				<li><img src="images/bnade-addon.png" alt="插件效果"
					class="img-rounded"></li>
			</ol>
			<h3 class="sub-header">插件客户端(11-11)</h3>
			<a href="./BnadeClient.zip" class="btn btn-primary">客户端下载</a>
			<p>插件客户端用于更新bnade插件以及tsm插件数据</p>
			<p>
				客户端使用java编写，所以需要java的运行环境，可以到<a href="http://www.java.com"
					target="_blank">http://www.java.com</a>下载
			</p>
			<p>下载后解压文件，打开bnade.bat或bnadec.bat即可运行程序</p>
			<p>tsm插件数据的说明:</p>
			<ul>
				<li>需要安装TradeSkillMaster_AppHelper插件</li>
				<li>目前不支持宠物价格和各阶制造业装备价格的数据</li>
				<li>每日凌晨2点更新数据</li>
				<li>数据组成说明，如果大家对数据的取法有更好的建议可以告诉我:
					<ol>
						<li>itemString - 物品ID</li>
						<li>marketValue - 市场价，这里用的更插件计算的价格一样</li>
						<li>minBuyout - 最低一口价，使用前一天的最低一口价</li>
						<li>historical - 历史价，使用前一天的平均价</li>
						<li>numAuctions - 拍卖数量，使用前一天的平均数量</li>
					</ol>
				</li>
			</ul>
			<h3>BNADE交流QQ群:518160038</h3>
			<h3 class="sub-header">微信公众号</h3>
			<div>
				<p>目前主要同于通知网站的更新</p>
				<img src="./images/weixin.jpg">
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>