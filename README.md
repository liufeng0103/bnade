# bnade

## 项目介绍
网站www.bnade.com用于魔兽世界拍卖行相关数据的查询和分析，这个计划重写整个[项目](https://github.com/liufeng0103/bnade-old)的前后端以及后台爬虫程序

## Wiki
[Wiki](https://github.com/liufeng0103/bnade/wiki/BNADE-Wiki)保存网站相关的文档

## 网站功能：
- 物品在所有服务器的最低价
- 物品在某个服务器的历史价格
- 时光徽章的价格查询
- 各种类型的宠物在所有服务器的最低价
- 玩家拍卖的所有物品查询
- 魔兽世界插件用于游戏中查看物品的参考价格 

## 开发环境
- 开发语言: Java 1.8, JavaScript, html, css
- 使用的框架： JQuery, Jersey, HightChart, Bootstrap
- 数据库： MySQL
- Web容器： Tomcat7
- 构建工具：Maven, gulp

## 目录结构说明
- catcher  
后台爬虫程序
- client  
通过战网api获取数据
- dao  
数据库操作
- service  
所有程序通过service获取和操作数据
- po  
数据库映射
- rs  
Restful api
- util  
工具类