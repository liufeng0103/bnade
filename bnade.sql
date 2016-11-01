USE bnade;

-- 服务器信息
CREATE TABLE IF NOT EXISTS t_realm (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,		-- 服务器ID	
	name VARCHAR(8) NOT NULL,						-- 服务器名
	type VARCHAR(3) NOT NULL,						-- 服务器类型PVP，PVE
	url VARCHAR(128) NOT NULL,						-- 拍卖行文件地址
	lastModified BIGINT NOT NULL,					-- 文件更新时间	
	maxAucId INT NOT NULL,							-- 文件最大拍卖id
	auctionQuantity INT NOT NULL,					-- 拍卖总数量
	playerQuantity INT NOT NULL,					-- 玩家数量
	itemQuantity INT NOT NULL,						-- 物品种类数量	
	lastUpdateTime BIGINT NOT NULL,					-- 行更新时间
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_realm ADD INDEX(name);

-- 物品信息
CREATE TABLE IF NOT EXISTS t_item (
	id	INT UNSIGNED NOT NULL,			-- ID
	description VARCHAR(255) NOT NULL,	-- 描述
	name VARCHAR(80) NOT NULL,			-- 物品名
	icon VARCHAR(64) NOT NULL,			-- 图标名
	itemClass INT NOT NULL,				-- 类型
	itemSubClass INT NOT NULL,			-- 子类型
	inventoryType INT NOT NULL,			-- 装备位置
	itemLevel INT NOT NULL,				-- 物品等级
	hot INT NOT NULL,					-- 物品热度
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 数据导入使用items.sql
-- ALTER TABLE t_item ADD hot INT NOT NULL; -- 20160813,列已添加到建表语句里，单独添加用

-- 物品信息内存表， 由于经常使用数据保存到内存中
CREATE TABLE mt_item (
  id int(10) unsigned NOT NULL,			-- ID
  name varchar(80) NOT NULL,			-- 物品名
  icon varchar(64) NOT NULL,			-- 图标名
  itemClass int(11) NOT NULL,			-- 分类
  itemSubClass int(11) NOT NULL,		-- 子类
  inventoryType int(11) NOT NULL,		-- 部位
  itemLevel int(11) NOT NULL,			-- 物品等级
  hot int(11) NOT NULL,					-- 物品热度
  PRIMARY KEY (id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8
ALTER TABLE mt_item ADD INDEX(name); -- 通过物品名查询物品信息时使用
truncate mt_item;
-- 数据导入到内存中
insert into mt_item (id,name,icon,itemClass,itemSubClass,inventoryType,itemLevel,hot) select id,name,icon,itemClass,itemSubClass,inventoryType,itemLevel,hot from t_item;
-- 手动更新那些通过api找不到的物品
-- insert into t_item (id,description,name,icon,itemLevel)values(732,'','成熟的秋葵','inv_misc_herb_09',10);

-- 装备奖励表
-- 制造业和fb物品都是拥有相同的itemId但不同的等级，副属性等通过bonus来表示
CREATE TABLE IF NOT EXISTS t_item_bonus (
	itemId	INT UNSIGNED NOT NULL,		-- 物品ID
	bonusList VARCHAR(20) NOT NULL, 	-- 装备奖励
	PRIMARY KEY(itemId, bonusList)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 制造业物品配方
CREATE TABLE IF NOT EXISTS t_item_created_by (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,-- 自增ID，便于插入数据
	itemId	INT UNSIGNED NOT NULL,			-- 物品ID
	spellId	INT UNSIGNED NOT NULL,			-- 物品的spell id
	name VARCHAR(80) NOT NULL,				-- 物品名
	icon varchar(64) NOT NULL,				-- 图标名
	minCount INT UNSIGNED NOT NULL,			-- 最少制作
	maxCount INT UNSIGNED NOT NULL,			-- 最多制作
	PRIMARY KEY (id)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_item_created_by ADD INDEX(itemId);
-- ALTER TABLE t_item_created_by ADD minCount INT UNSIGNED NOT NULL;
-- ALTER TABLE t_item_created_by ADD maxCount INT UNSIGNED NOT NULL;

-- 制造业物品组成材料
CREATE TABLE IF NOT EXISTS t_item_reagent (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,-- 自增ID，便于插入数据
	spellId	INT UNSIGNED NOT NULL,			-- 物品spell id
	itemId INT UNSIGNED NOT NULL,			-- 物品id
	name VARCHAR(80) NOT NULL,				-- 物品名
	quality INT UNSIGNED NOT NULL,			-- 物品品质
	icon varchar(64) NOT NULL,				-- 图标名
	count INT UNSIGNED NOT NULL,			-- 需要的数量
	buyPrice BIGINT UNSIGNED NOT NULL,		-- npc出售价格
	PRIMARY KEY (id)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_item_reagent ADD INDEX(spellId);
-- ALTER TABLE t_item_reagent ADD buyPrice BIGINT UNSIGNED NOT NULL;
-- update t_item_reagent set buyPrice=100 where itemId=3371 -- 手动添加可以在npc那买到的物品

-- 已经处理过的item，由于各种item处理程序使用
CREATE TABLE IF NOT EXISTS t_item_processed (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,-- 自增ID，便于插入数据
	type INT UNSIGNED NOT NULL,				-- 操作类型
	itemId	INT UNSIGNED NOT NULL,			-- 物品ID
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_item_processed ADD INDEX(type,itemId);

-- 物品和宠物信息表
CREATE VIEW v_item as 
select id,name,icon,itemLevel,1 as type,hot from mt_item 
union all
select id,name,icon,0 as itemLevel,2 as type,0 as hot from t_pet

-- 超值的物品
CREATE TABLE IF NOT EXISTS t_item_worthbuy (	
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,-- 自增ID，便于插入数据
	realmId INT UNSIGNED NOT NULL,			-- 服务器ID	
	itemId	INT UNSIGNED NOT NULL,			-- 物品ID
	buy BIGINT UNSIGNED NOT NULL,			-- 价格
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 物品价格规则，满足条件的将被保存
CREATE TABLE IF NOT EXISTS t_item_rule (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	itemId	INT UNSIGNED NOT NULL,			-- 物品ID
	ltBuy BIGINT UNSIGNED NOT NULL,			-- 小于某个价
	gtBuy BIGINT UNSIGNED NOT NULL,			-- 大于某个价
	PRIMARY KEY(id)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 宠物信息表
CREATE TABLE IF NOT EXISTS t_pet (	
	id INT UNSIGNED NOT NULL,		-- 宠物id				
	name VARCHAR(16) NOT NULL,		-- 宠物名
	icon VARCHAR(64) NOT NULL,		-- 宠物图标
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_pet ADD INDEX(name); -- 通过宠物名查询宠物信息
-- 数据导入使用pets.sql

-- 宠物类型以及属性值
CREATE TABLE IF NOT EXISTS t_pet_stats (
	speciesId INT UNSIGNED NOT NULL,				-- 宠物id
	breedId	INT UNSIGNED NOT NULL,					-- 成长类型
	petQualityId INT UNSIGNED NOT NULL default 3,	-- 品质
	level	INT UNSIGNED NOT NULL default 25,		-- 等级
	health INT UNSIGNED NOT NULL,					-- 生命值
	power	INT UNSIGNED NOT NULL,					-- 攻击力
	speed INT NOT NULL,								-- 速度
	PRIMARY KEY(speciesId,breedId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 数据导入petStats.sql
 
-- x服务器拍卖行最新的所有数据，由程序创建
CREATE TABLE IF NOT EXISTS t_ah_data_x (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 自增ID，便于插入数据
	auc INT UNSIGNED NOT NULL,					-- 拍卖ID
	item INT UNSIGNED NOT NULL,					-- 物品ID
	owner VARCHAR(12) NOT NULL,					-- 玩家
	ownerRealm VARCHAR(8) NOT NULL,				-- 玩家所在服务器
	bid BIGINT UNSIGNED NOT NULL,				-- 竞价
	buyout BIGINT UNSIGNED NOT NULL,			-- 一口价
	quantity INT UNSIGNED NOT NULL,				-- 数量
	timeLeft VARCHAR(12) NOT NULL,				-- 剩余时间
	petSpeciesId INT UNSIGNED NOT NULL,			-- 宠物ID
	petLevel INT UNSIGNED NOT NULL,				-- 宠物等级
	petBreedId INT UNSIGNED NOT NULL,			-- 宠物类型
	context INT UNSIGNED NOT NULL,				-- 物品出处
	bonusLists VARCHAR(20) NOT NULL,			-- 奖励
	lastModifed BIGINT UNSIGNED NOT NULL,		-- 数据更新时间
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_ah_data_x ADD INDEX(owner); -- 查询某个玩家拍卖的所有物品

-- 服务器拍卖行最低一口价最新的所有数据
CREATE TABLE IF NOT EXISTS t_ah_min_buyout_data (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 自增ID，便于插入数据
	auc INT UNSIGNED NOT NULL,					-- 拍卖ID
	item INT UNSIGNED NOT NULL,					-- 物品ID
	owner VARCHAR(12) NOT NULL,					-- 玩家
	ownerRealm VARCHAR(8) NOT NULL,				-- 玩家所在服务器
	bid BIGINT UNSIGNED NOT NULL,				-- 竞价
	buyout BIGINT UNSIGNED NOT NULL,			-- 一口价
	quantity INT UNSIGNED NOT NULL,				-- 总数量
	timeLeft VARCHAR(12) NOT NULL,				-- 剩余时间
	petSpeciesId INT UNSIGNED NOT NULL,			-- 宠物ID
	petLevel INT UNSIGNED NOT NULL,				-- 宠物等级
	petBreedId INT UNSIGNED NOT NULL,			-- 宠物类型
	context INT UNSIGNED NOT NULL,				-- 物品出处
	bonusLists VARCHAR(20) NOT NULL,			-- 奖励
	lastModifed BIGINT UNSIGNED NOT NULL,		-- 数据更新时间
	realmId INT UNSIGNED NOT NULL,				-- 服务器ID
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 查询物品在所有服务器的最低一口价  
ALTER TABLE t_ah_min_buyout_data ADD INDEX(item);
-- 查询宠物在所有服务器的最低一口价  
ALTER TABLE t_ah_min_buyout_data ADD INDEX(petSpeciesId);
-- 删除某个服务器所有数据
ALTER TABLE t_ah_min_buyout_data ADD INDEX(realmId);

-- x服务器拍卖行最低一口价yyyyMMdd天的所有数据，由程序创建
CREATE TABLE IF NOT EXISTS t_ah_min_buyout_data_yyyyMMdd_x (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 自增ID，便于插入数据	
	item INT UNSIGNED NOT NULL,					-- 物品ID
	owner VARCHAR(12) NOT NULL,					-- 玩家
	ownerRealm VARCHAR(8) NOT NULL,				-- 玩家所在服务器
	bid BIGINT UNSIGNED NOT NULL,				-- 竞价
	buyout BIGINT UNSIGNED NOT NULL,			-- 一口价
	quantity INT UNSIGNED NOT NULL,				-- 总数量	
	petSpeciesId INT UNSIGNED NOT NULL,			-- 宠物ID
	petBreedId INT UNSIGNED NOT NULL,			-- 宠物类型
	bonusLists VARCHAR(20) NOT NULL,			-- 奖励
	lastModifed BIGINT UNSIGNED NOT NULL,		-- 数据更新时间
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 查询物品或宠物在所有服务器yyyyMMdd天的最低一口价  
ALTER TABLE t_ah_min_buyout_data_yyyyMMdd_x ADD INDEX(item);
ALTER TABLE t_ah_min_buyout_data_yyyyMMdd_x ADD INDEX(petSpeciesId);

-- x服务器拍卖行最低一口价yyyyMM月的N个时段的数据，由程序创建
CREATE TABLE IF NOT EXISTS t_ah_min_buyout_data_yyyyMM_x (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 自增ID，便于插入数据	
	item INT UNSIGNED NOT NULL,					-- 物品ID	
	buyout BIGINT UNSIGNED NOT NULL,			-- 一口价
	quantity INT UNSIGNED NOT NULL,				-- 总数量	
	petSpeciesId INT UNSIGNED NOT NULL,			-- 宠物ID
	petBreedId INT UNSIGNED NOT NULL,			-- 宠物类型
	bonusLists VARCHAR(20) NOT NULL,			-- 奖励
	lastModifed BIGINT UNSIGNED NOT NULL,		-- 数据更新时间
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 查询物品或宠物在所有服务器yyyyMM月的最低一口价  
ALTER TABLE t_ah_min_buyout_data_yyyyMM_x ADD INDEX(item);
ALTER TABLE t_ah_min_buyout_data_yyyyMM_x ADD INDEX(petSpeciesId);

-- 玩家拍卖的物品数据
CREATE TABLE IF NOT EXISTS t_ah_owner_item_x (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 自增ID，便于插入数据
	item INT UNSIGNED NOT NULL,					-- 物品ID
	owner VARCHAR(12) NOT NULL,					-- 玩家	
	quantity INT UNSIGNED NOT NULL,				-- 数量	
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 时光徽章
CREATE TABLE IF NOT EXISTS t_wowtoken (	
	buy INT UNSIGNED NOT NULL,
	updated BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY(updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- task控制表
CREATE TABLE IF NOT EXISTS t_task (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	type INT UNSIGNED NOT NULL,				-- task类型
	realmId INT UNSIGNED NOT NULL,			-- 服务器ID
	date VARCHAR(10) NOT NULL,				-- 处理日期
	lastUpdated BIGINT UNSIGNED NOT NULL, 	-- 上次运行时间
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 归档程序用于查看服务器是否处理过某个日期
ALTER TABLE t_task ADD INDEX(realmId,date);
-- 热门物品task更新处理时间
ALTER TABLE t_task ADD INDEX(type);

------------------- 搜索排行榜功能相关表 -------------------
-- 用户查询的物品
CREATE TABLE IF NOT EXISTS t_query_item (
	itemId INT UNSIGNED NOT NULL,		-- 物品ID
	ip VARCHAR(15) NOT NULL,			-- 用户IP
	createdAt BIGINT UNSIGNED NOT NULL,	-- 创建时间		
	PRIMARY KEY(itemId,ip)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 每天物品查询的次数
CREATE TABLE IF NOT EXISTS t_hot_item (
	itemId INT UNSIGNED NOT NULL,		-- 物品ID
	queried	INT UNSIGNED NOT NULL,  	-- 查询次数
	dateTime BIGINT UNSIGNED NOT NULL,  -- 查询的日期时间
	PRIMARY KEY(dateTime,itemId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

------------------- 参考价格功能相关表 -------------------
-- 记录处理过的item id
CREATE TABLE IF NOT EXISTS t_item_analysis (
	itemId	INT UNSIGNED NOT NULL,			-- 物品ID
	PRIMARY KEY(itemId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 物品的参考价格
CREATE TABLE IF NOT EXISTS t_item_market (
	itemId	INT UNSIGNED NOT NULL,			-- 物品ID
	petSpeciesId INT UNSIGNED NOT NULL,		-- 宠物ID
	petBreedId INT UNSIGNED NOT NULL,		-- 宠物类型
	bonusLists VARCHAR(20) NOT NULL,		-- 奖励
	buy BIGINT UNSIGNED NOT NULL,			-- 市场价
	realmQuantity INT UNSIGNED NOT NULL,	-- 参考服务器数
	PRIMARY KEY(itemId,petSpeciesId,petBreedId,bonusLists)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

------------------- 淘宝功能相关表 -------------------
-- 物品价格配置
CREATE TABLE IF NOT EXISTS t_item_rule (
	itemId	INT UNSIGNED NOT NULL,		-- 物品ID
	ltBuy BIGINT UNSIGNED NOT NULL,		-- 低于
	gtBuy BIGINT UNSIGNED NOT NULL,		-- 高于
	PRIMARY KEY(itemId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 满足规则的物品
CREATE TABLE IF NOT EXISTS t_item_rule_match (
	realmId INT UNSIGNED NOT NULL,			-- 服务器ID
	itemId	INT UNSIGNED NOT NULL,			-- 物品ID
	buyout BIGINT UNSIGNED NOT NULL,		-- 一口价
	lastModified BIGINT UNSIGNED NOT NULL,	-- 数据更新时间
	PRIMARY KEY(realmId,itemId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

------------------- 插件相关表 -------------------
-- 插件信息表
CREATE TABLE IF NOT EXISTS t_addon (
    version VARCHAR(20) NOT NULL,	-- 插件版本
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into t_addon (version) values ("TBD");

-- 物价信息表，用于tsm app数据的更新
CREATE TABLE IF NOT EXISTS t_tsm_app_data (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	realmId INT UNSIGNED NOT NULL,			-- 服务器id
	itemId INT UNSIGNED NOT NULL,			-- 物品ID
	minBuyout BIGINT UNSIGNED NOT NULL,		-- 最低一口价(近期)
    historical BIGINT UNSIGNED NOT NULL,	-- 历史价格(近期)
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_tsm_app_data ADD INDEX(realmId);

-- View, 包含tsm需要的所有数据
CREATE VIEW v_tsm_app_data as 
select a.realmId,a.itemId,buy,minBuyout,historical,quantity 
from t_tsm_app_data a 
join t_ah_min_buyout_data b on
a.realmId=b.realmId and a.itemId=b.item
join t_item_market c on 
c.itemId=a.itemId

-- 服务器数据的版本信息
CREATE TABLE IF NOT EXISTS t_tsm_realm_data_version (	
	realmId INT UNSIGNED NOT NULL,			-- 服务器id
    version VARCHAR(20) NOT NULL,			-- 插件版本
    PRIMARY KEY(realmId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

------------------- 用户管理 -------------------
CREATE TABLE IF NOT EXISTS t_user (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	openId VARCHAR(32) NOT NULL,
	email VARCHAR(20) NOT NULL default '',
	nickname VARCHAR(20) NOT NULL,
	createTime BIGINT UNSIGNED NOT NULL,
	updateTime timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,	
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_user ADD INDEX(openId);
