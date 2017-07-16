<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>开发者 - BNADE魔兽世界</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-md-8">
					<h1 class="page-header">开发者</h1>
					<p>如果你是一位开发人员，期待你加入我们的开发者交流QQ群: 546818902。</p>
					<p>网站API文档: <a href="https://www.bnade.com/api-doc">https://www.bnade.com/api-doc</a>， 老的API文档: <a href="https://www.bnade.com/api-docs">https://www.bnade.com/api-docs</a></p>
					<p>网站源码已开源到GitHub,主要使用java编写，如果您感兴趣可以查看：</p>
					<ul>
						<li><a href="https://github.com/liufeng0103/bnade-catcher" target="_blank">bnade-catcher</a> 后台数据抓取维护程序</li>
						<li><a href="https://github.com/liufeng0103/bnade-web-ssh" target="_blank">bnade-web-ssh</a> 网站API</li>
						<li><a href="https://github.com/liufeng0103/bnade" target="_blank">bnade</a> 还有部分功能在老的项目</li>
					</ul>
					<p>常见的问题我已总结到下面的Q&amp;A,请查看是否有你想到的答案，如果找不到你的问题，请联系我。</p>
					<h2>Q&amp;A</h2>
					<h3>拍卖行数据来源</h3>
					<p>本网站的拍卖行数据来源官方的数据更新，虽然国服的战网API已关闭，但发现之前保存的拍卖行数据源地址的数据任然在周期性的更新，所以开发者任然可以用它开发拍卖行相关的应用，请<a href="https://github.com/liufeng0103/bnade/blob/master/RealmUrl.md" target="_blank">参考</a>。</p>
					<hr>
					<h3>拍卖行数据中的各属性表示什么</h3>
					<p>以下是我的一些总结，希望对您有所帮助：</p>
					<ul>
						<li>context - 我理解的是物品的出处，比如表示来自制造业，还是某个难度的副本，还是世界掉落等。</li>
						<li>bonusLists - 这个跟装备奖励有关，比如某个装备加多少装等，是否带孔等等，请参考<a href="https://gist.github.com/erorus/35705144b1a4ad015924" target="_blank">itembonus.csv</a>,这个文件由<a href="https://theunderminejournal.com" target="_blank">The Undermine Journal</a>作者Erorus分享，稍后我也会分享我的总结。 </li>
						<li>rand,seed不理解也不需要关心，modifiers也不理解，猜测可能跟竞价有关。剩下的属性应该都很好理解了。如果您有什么问题或者发现属性的含义请告诉我们。</li>
					</ul>
					<hr>
					<h3>物品信息来源</h3>
					<p>由于国服API关闭，只能通过战网API获取台服或者美服的物品信息，物品的中文名没法获取。现在网站使用的wowhead的物品API，请参考查看<a href="http://cn.wowhead.com/item=147429&xml&bonus=1532" target="_blank">950的倾颓圣殿腰带</a>的API，我对这个接口做了Java的封装，有兴趣您也可以参考<a href="https://github.com/liufeng0103/wow-client" target="_blank">wow-client</a>。</p>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>