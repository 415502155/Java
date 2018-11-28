/*Mock.mock('http://test.com', {
    "user|1-3": [{   // 随机生成1到3个数组元素
        'name': '@cname',  // 中文名称
        'id|+1': 88,    // 属性值自动加 1，初始值为88
        'age|18-28': 0,   // 18至28以内随机整数, 0只是用来确定类型
        'birthday': '@date("yyyy-MM-dd")',  // 日期
        'city': '@city(true)',   // 中国城市
        'color': '@color',  // 16进制颜色
        'isMale|1': true,  // 布尔值
        'isFat|1-2': true,  // true的概率是1/3
        'fromObj|2': obj,  // 从obj对象中随机获取2个属性
        'fromObj2|1-3': obj,  // 从obj对象中随机获取1至3个属性
        'brother|1': ['jack', 'jim'], // 随机选取 1 个元素
        'sister|+1': ['jack', 'jim', 'lily'], // array中顺序选取元素作为结果
        'friends|2': ['jack', 'jim'] // 重复2次属性值生成一个新数组
    },{
        'gf': '@cname'
    }]
});*/

//判断是否报名
Mock.mock('../../../api/userInfo', {
    "baseUser": [{   // 随机生成1到3个数组元素
        'isCertification':1,
    }],
    code:0
});


//判断是否报名获取班级信息
Mock.mock('../../../api/recruit/getRenewList', {
    "data|3-7": [{   // 随机生成1到3个数组元素
        'id|1-100':0,
        //类目
        'categoryId1-100':0,
		//所属类目ID
		'categoryName|1':[
					    "低幼类",
						"联合办学类",
						"美术类",
						"棋类",
						"器乐类",
						"声乐类",
						"书法类",
						"文化类",
						"舞蹈类"
					  ],
		//所属类目名称

		//课程
        'courseId|1-9':0,
		'courseName|1':[
					    "低幼类",
						"联合办学类",
						"美术类",
						"棋类",
						"器乐类",
						"声乐类",
						"书法类",
						"文化类",
						"舞蹈类"
					  ],
		'termNumber|1-5':0,
		'className':'@cname',
		//班级名称
		'teacherInfo':'@cname',
		//教师
		'ageRange|1':[
					    "5-6岁",
					    "7-8岁",
					    "4-7岁"
					  ],
		//学段
		'tuition|1000-2000':0,
		//学费
		'hourNumber|1-3':0,
		//课时
		'classStartDate':'@date("yyyy-MM-dd")',
		//开课时间
		//'classDate':"周六"+Mock.Random.time('HH:mm')+"-"+Mock.Random.time('HH:mm'),
		'classDate|1':[
					    "周六  17:00-18:30",
					    "周六 18:00-19:30",
					    "周日 17:30-18:30",
					    "周日  17:00-18:30",
					    "周日 18:00-19:30",
					    "周六 17:30-18:30",
					    "周一 20:00-20:30",
					    "周二 19:00-20:30",
					    "周三 19:00-20:30",
					    "周四 19:00-20:30",
					    "周五 19:00-20:30"
					  ],
	 /*   'classDate':Mock.Random.pick(["周一","周二","周三","周四","周五","周六","周日",]+" "+Mock.Random.pick([  "17:00-18:30",
		    "18:00-19:30",
		    "17:30-18:30",
		    "20:00-20:30",
		    "19:00-20:30"
		  ]),*/
		//每周几上课
		'campusName|1':[
					    "炫联",
					    "东马路"
					  ],
		//校区
		'classroom|+1':200,
		//教室
		'classSize|22-28':0,
		//班级容量
		'isbm|1': [0,1],  // 布尔值
		//是否报名
		'bmCount|':23,
    }],
    "busTrainRecruit":{
    	'id|1-20': 0,
		'title':'@cname',
		//计划名称
		'startTime':Mock.Random.now('year'),
		//开始时间
		'endTime':"2018-03-04 00:00:00",
		//结束时间
		'shouldKnow':Mock.mock('@cparagraph()'),
		//报名须知
		'expirationDuration|1': true,
		//过期时长
		'isOpen|1': 1,
		//招生是否开启 0关闭 1开启
    },
    'code': 0,
	'msg':'@cname',
	'success|1': true,
	'sysTime': Mock.Random.now('second'),
});




//通用保存
Mock.mock('../../../api/message/backMsg', {
    'code': 0,
	'msg':Mock.mock('@csentence(3, 5)'),
	'success|1': true,
	'sysTime': Mock.Random.now('second'),
});


//保存报名
Mock.mock('../../../api/message/getMsgInfo', {
    'data':{
        'id|22-3328':0,
        'content':Mock.mock('@cparagraph()'),
        'createTime': Mock.Random.now('minute'),
		'isReceipt|1':[0,1],
        'teacherName':'@cname',
        'isReaded|1':[0,1],
        'imgList':[{small:Mock.Random.dataImage(),big:Mock.Random.dataImage()},{small:Mock.Random.dataImage(),big:Mock.Random.dataImage()}]
    },
    'code': 0,
    'msg':Mock.mock('@csentence(3, 5)'),
    'success|1': true,
    'sysTime': Mock.Random.now('second'),
});
