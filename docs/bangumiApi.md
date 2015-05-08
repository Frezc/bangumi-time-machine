根地址: http://api.bgm.tv 
本api参考了 https://github.com/jabbany/dhufufu/blob/master/bangumi/api.txt
对于比较大的json建议在请求头中加入`Accept-Encoding: gzip`进行压缩传输

Auth
---
	[POST] /auth?source=BGMbyYumeProject
    Authorization: Basic {BASE64 username:password}
    # Accept-Encoding: gzip (使用Gzip压缩)

	[返回信息] Json
    {
		id: 用户UID
		url: 用户空间URL
		username: 还是用户UID
		nickname: 用户昵称
		avatar:{
			large: 头像、大
			medium: 头像、中
			small: 头像、小
		}
		sign: 签名
		auth: 颁发的 {$AUTH_TOKEN}
		auth_encode: urlencode之后的 {$AUTH_TOKEN}
	}

Watching 追番信息
---
	[GET] /user/{$UID}/collection?cat=watching
    # PagingRows: 10 似乎没用
    cat的值只要不为空都会获得该结果
    Accept-Encoding: gzip 建议 这里的数据量可能会很大
    
    [return] Json
    [
		{
			name: 番剧名称 (原名，非中文)
			ep_status: 追番集数
			lasttouch: 上次更新时间
			subject: {
				id: 番剧ID
				url: 链接到番剧的URL
				type: 番剧类型 (2=动画，6=三次元)(这里似乎只会返回在看的这两种类型)
				name: 番剧名称
				name_cn: 番剧汉化名（可能没有）
				summary: 番剧介绍 (可能没有)
				eps: 总集数
				air_date: 放映开始日期
				air_weekday: 放映日（周）
					1 周一
					2 周二
					3 周三
					4 周四
					5 周五
					6 周六
					7 周日
				images: {
					large,common,medium,small,grid （不同大小的缩略图URL）
				}
				collection: {
					wish: 想要看的人数
					collect: 看过的人数
					doing: 正在看的人数
					on_hold: 搁置人数
					dropped: 弃番人数
				}
			}
		},...
	]

notifycount 消息数目
---
    GET http://api.bgm.tv/notify/count?source=BGMbyYumeProject&auth={$AUTH_TOKEN}
    # Accept-Encoding: gzip
    
    [return] json
    {
		count: 你的信消息提醒数目
	}
    
subject 番组详情详情
---
    GET /subject/{subjectID}?source=BGMbyYumeProject&responseGroup={$RGP}
    {$RGP} = 返回详细程度
		"simple" : (Default) 比较简略
		"large" : 详细
    # PagingRows: 10
    Accept-Encoding: gzip large的数据量较大
    
    [return] Json
    simple: 同追番信息里获得的subject相同，但是summary会有内容
    large: 
    {
		id: 番组ID {$ID}
		url: 番组URL
		type: 专题类型
			1 漫画/小说
			2 动画/二次元番
			3 音乐
			4 游戏
			6 三次元番
		summary: 简介
		name: 名称（原）
		name_cn: 名称（中）
		eps:
			[
				{
					id: 集ID
					url: 集URL
					type: 类型（目前只见到过0）
					sort: 集编号（序号，对应第N集，以1开始）
					name: 该集名称（原）
					name_cn: 该集名称（中）
					duration: 时长（字符串，格式：hh:MM:ss）
					airdate: 放送日
					comment: 评论数
					desc: 介绍
					status: 放映状况
						Air - 已放送
						NA - 未放送
						Today - 正在放送
				},...
			]
		air_date: 首映日期
		air_weekday: 放映日（见上 1-7为周一-周日）
		images: { large,common,medium,small,grid : 缩略图URI }
		collection: { wish,collect,doing,on_hold,dropped } 收藏数量
		crt:
			[
				{
					id: 角色词条ID
					url: 角色URL
					name: 角色姓名
					name_cn: 角色姓名（汉化）
					role_name: 角色类型字符串（主角、配角等等）
					images: { large,medium,grid,small : 缩略图 }
					comment: 评论数目
					collects: 收藏人数
					info: {
						name_cn: 角色姓名（汉化）
						alias: {
                        	en,
                            jp,
							kana,
                            nick,
                            romaji
						}
						gender: 性别（男，女）
                        birth: 生日
						bloodtype,height,weight,bwh: 血型、身高、体重、三围
						source: 信息来源
					}
					actors: [
						{
							id: （配音）演员ID
							url: （配音）演员资料URL
							name: （配音）演员姓名
							images: { large,medium,small,grid : 缩略图}
						}
					]
				}
			]
		staff:
			[
				{
					id: 制作者ID
					url: 制作者资料页
					name: 制作者名称
					name_cn: 中文的名称(很可能无)
					role_name: 扮演的制作团队角色(基本无)
					images: { large,medium,small,grid :  缩略图 }
					comment: 评论数目
					collects: 收藏数目
					info: {
						参考CRT的 INFO 区域
					}
					jobs: [
						"原作","监督" 。。。等等
					]
				}，...
			]
		topic:
			[
				{
					id: 讨论板主题ID
					url: 讨论板主题URL
					main_id: 讨论板ID
					timestamp: 时间
					lastpost: 最后回复时间
					replies: 回复数量
					user: { id,url,username,nickname,avatar,sign : 发帖用户数据，参考/auth返回 }
				},...
			]
		blog:
			[
				{
					id: 博客文ID
					url: 文章URL
					title: 文章标题
					summary: 文章简介
					image: { 缩略图 }
					replies: 回复数
					timestamp: 发帖时间
					dateline: 日期 Y-m-d H:i 格式
					user: { id,url,username,nickname,avatar,sign : 用户数据 }
				},...
			]
	}

用户对某个番组的追番信息及评价
---
	GET /collection/{subjectID}?source=BGMbyYumeProject&auth={auth_encode}
    source如果不是BGMbyYumeProject会出现Unauthorized错误
    
    [return] Json
    {
		status: {
			id: 整个番组的观看状态
				1 = 想看
				2 = 看过
				3 = 在看
				4 = 搁置
				5 = 抛弃
			type: 文字描述
				do = 在看
				on_hold = 搁置
				dropped = 弃番
				wish = 想看
				collect = 看过
			name: 中文汉字描述
		}
		rating: 用户评分 1-10 0为未评价
		comment: 用户评价
		tag: ["",""... : 标签字符串数组 ]
		ep_status: 最后观看到的集数序号（注意是序号，不是ID）
		lasttouch: 最后更改时间
		user: {
			id, url, username, nickname, avatar sign: 见 /auth 部分
		}
	}

搜索词条
---
	[GET] /search/subject/{$QUERY}?responseGroup={$RGP}&max_results={$LIMIT_END}&start={$LIMIT_START}
	{$QUERY} = 搜索的字符串
    {$RGP} = 返回详细程度
		"simple" : (Default) 比较简略
		"large" : 详细
	{$LIMIT_END} = 最后一条记录的位置
	{$LIMIT_START} = 第一条记录的位置（这两个用于分页），一次区间建议不要超过20
    
    [return] Json
    {
		results: 返回结果的实际数量
		list:[
			{
				同追番信息里的subject,
                如果 {$RPG} == large, 信息会填满
                如果 {$RPG} == simple, 不会给出 "summary","eps","air_date","air_weekday","collection" 的内容。
			},...
		]
	}
    
每日放送列表
---
	[GET] /calendar

	[返回信息]
	格式：JSON
	[
		{
			weekday: {
				cn: 中文星期名 [ 星期一 ... 星期日 ]
				en: 英语星期名 [ Mon ... Sun ]
				id: 1-7 对应周一到周日
				jp: 日语星期名 [ 月曜日 ... 日曜日 ]
			}
			items : [
				{
					air_date, air_weekday, collection, eps, images, name, name_cn, type, url: 
						参考(5)获取番组信息在{$RGP=simple}时得到的结果。
						注意：即便对这个API给出responseGroup={$RGP}参数，仍然无法诱发获取 {$RGP=large} 的 subject 结果
						目前还有观察到 type, summary 等一些字段有时无法正确返回，使用者最好先行检查
				}
			]
		}
		...一共7个条目对应每个日期...
	]
    
获取追番进度信息
---
只能得到设置过的ep

	[GET] /user/{$UID}/progress?source=BGMbyYumeProject&auth={auth_encode}[&subject_id={subject_id}]
    {$UID} 用户id
    {subject_id} 番组id 可以无
    
    [return] Json 
    [
    	{
		subject_id: 番组ID 同 {subject_id}
		eps: [
                {
                    id: 本集ID
                    status: {
                        id: 状态 ID 
                            1 = 想看
                            2 = 看过
                            3 = 在看
                            4 = 搁置
                            5 = 抛弃
                        css_name: 网站CSS标签名称 2-> Watched
                        url_name: 网站URL内显示的名称 2-> watched
                        cn_name: 中文字符串（见上面数字、字符串对应表）
                    }
                },...
			]
		},... //如果指定了{subject_id}只会返回一项
    ]
    
设置追番进度
	[GET] 
    
设置收藏信息
---
	[POST] /collection/{subjectID}/create[|update]?source=BGMbyYumeProject&auth={auth_encode}
    这里create也能改为update，似乎没什么区别
    
    [body]
    | name | value |
    |------|-------|
    |rating| 1~10  |
    |comment|{评论}|
    |status|{见下,未指定为wish}|
    |tags|{空格隔开的字符串}|
    
    status: wish(想看),collect(看过),do(在看)，on_hold(搁置),dropped(抛弃)
    
    [return] Json
    设置成功会返回
    {
		status: {
			id: 整个番组的观看状态
				1 = 想看
				2 = 看过
				3 = 在看
				4 = 搁置
				5 = 抛弃
			type: 文字描述
				do = 在看
				on_hold = 搁置
				dropped = 弃番
				wish = 想看
				collect = 看过
			name: 中文汉字描述
		}
		rating: 用户评分 1-10 0为未评价
		comment: 用户评价
		tag: ["",""... : 标签字符串数组 ]
		ep_status: 最后观看到的集数序号（注意是序号，不是ID）
		lasttouch: 最后更改时间
		user: {
			id, url, username, nickname, avatar sign: 见 /auth 部分
		}
	}
    
设置追番进度
---
	[POST] /ep/{$EPID}/status/{$STATUS_URL}?source=BGMbyYumeProject&auth={auth_encode}
    {$EPID} = 该集的 ID，在 collection 和 subject 下都可查询
	{$STATUS_URL} = 状态URL后缀，
		watched = 看过
		drop = 弃番
		queue = 想看
        
    [return] Json
    {
		request: 请求的URL
		code: HTTP返回代码，200为成功
		error: 错误的描述，没错误的话是 "OK"
	}