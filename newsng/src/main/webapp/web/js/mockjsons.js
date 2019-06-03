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
		'shouldK type_w':Mock.mock('@cparagraph()'),
		//报名须知
		'expirationDuration|1': true,
		//过期时长
		'isOpen|1': 1,
		//招生是否开启 0关闭 1开启
    },
    'code': 0,
	'msg':'@cname',
	'success|1': true,
	'sysTime': Mock.Random. now('second'),
});




//通用保存
Mock.mock('../../../api/message/backMsg', {
    'code': 0,
	'msg':Mock.mock('@csentence(3, 5)'),
	'success|1': true,
	'sysTime': Mock.Random. now('second'),
});


//保存报名
Mock.mock('../../../api/message/getMsgInfo', {
    'data':{
        'id|22-3328':0,
        'content':Mock.mock('@cparagraph()'),
        'createTime': Mock.Random. now('minute'),
		'isReceipt|1':[0,1],
        'teacherName':'@cname',
        'isReaded|1':[0,1],
        'imgList':[{small:Mock.Random.dataImage(),big:Mock.Random.dataImage()},{small:Mock.Random.dataImage(),big:Mock.Random.dataImage()}]
    },
    'code': 0,
    'msg':Mock.mock('@csentence(3, 5)'),
    'success|1': true,
    'sysTime': Mock.Random. now('second'),
});


/**
 * 基础设置
 * 校区管理
 * 校区（编辑和创建）通用保存
 */

Mock.mock(domain+'/baseSet/campusManage/createAndUpdateCampus.json', {
    'code': 200,
    'msg':Mock.mock('@csentence(3, 5)'),
    'success|1-2': true,
    'sysTime': Mock.Random. now('second'),
});


/**
 * 基础设置
 * -校区管理
 * --校区列表
 */

Mock.mock(domain+"/baseSet/campusManage/queryCampusListInfo.json", {
   'data':{
   	'data|5':[{
        "cam_id|+1":1,
        "org_id":"167",
        "cam_name|1":[
            "炫联",
            "保山道",
            "卫津路",
            "西青道",
            "东马路"
        ],
        "cam_type|1" :[0,1],
        "cam_address":Mock.Random.county()+Mock.Random.increment()+"号",
        "cam_mobile": /^1[3785][1-9]\d{8}/,
        "navigation_info":"117.20 39.12",
        "note":Mock.mock('@cparagraph()'),
    }],

   "total|1-100": 100,
   "limit": 20,
   "start": 0,
   "page": 1,
   "totalPage": 0,
   "size|1-100": 100
   },
    'code': 200,
    'msg':Mock.mock('@csentence(3, 5)'),
    'success': true,
    'sysTime': Mock.Random. now('second'),
});


//删除校区
Mock.mock(domain+"/baseSet/campusManage/deleteCampus.json", {
    "data":"",
    "code":200,
    "msg":"",
    "success":true
});



/*科目列表*/
Mock.mock(domain+"/baseSet/categoryManage/queryCategoryListInfo.json", {
    "message": " 科目管理列表",
    "data": {
    "total": 5,
        "limit": 25,
        "start": 0,
        "page": 1,
        "data": [
        {
            "org_id": 1,
            "subject_name": "现代舞",
            "category_name": "舞蹈类",
            "subject_id": 1,
            "category_id": 1
        },
        {
            "org_id": 1,
            "subject_name": "民族舞",
            "category_name": "舞蹈类",
            "subject_id": 2,
            "category_id": 1
        },
        {
            "org_id": 46,
            "subject_name": "素描",
            "category_name": "美术类",
            "subject_id": 9,
            "category_id": 5
        },
        {
            "org_id": 46,
            "subject_name": "国画",
            "category_name": "美术类",
            "subject_id": 5,
            "category_id": 5
        },
        {
            "org_id": 1,
            "subject_name": "街舞",
            "category_name": "舞蹈类",
            "subject_id": 4,
            "category_id": 1
        }
    ],
	"totalPage": 1,
	"size": 1
},
    "code": 200,
    "success": true
});

//科目类目：下拉不列表
Mock.mock(domain+"/baseSet/categoryManage/querySubjectListInfo.json", {
    "message": "科目类目搜索条件",
    "data": [
        {
            "category_name": "舞蹈类",
            "category_id": 1,
            "list": [
                {
                    "org_id": 1,
                    "subject_name": "舞蹈班",
                    "category_name": "舞蹈类",
                    "subject_id": 1,
                    "category_id": 1,
                    "list": []
                },
                {
                    "org_id": 1,
                    "subject_name": "拉丁舞",
                    "category_name": "舞蹈类",
                    "subject_id": 2,
                    "category_id": 1,
                    "list": []
                },
                {
                    "org_id": 1,
                    "subject_name": "街舞",
                    "category_name": "舞蹈类",
                    "subject_id": 3,
                    "category_id": 1,
                    "list": []
                }
            ]
        },
        {
            "category_name": "儿童美术类",
            "category_id": 2,
            "list": [
                {
                    "org_id": 1,
                    "subject_name": "卡通画",
                    "category_name": "美术类",
                    "subject_id": 4,
                    "category_id": 2,
                    "list": []
                },
                {
                    "org_id": 1,
                    "subject_name": "纸艺班",
                    "category_name": "美术类",
                    "subject_id": 5,
                    "category_id": 2,
                    "list": []
                },
                {
                    "org_id": 1,
                    "subject_name": "国画班",
                    "category_name": "美术类",
                    "subject_id": 6,
                    "category_id": 2,
                    "list": []
                },
                {
                    "org_id": 1,
                    "subject_name": "书法班",
                    "category_name": "美术类",
                    "subject_id": 7,
                    "category_id": 2,
                    "list": []
                }
            ]
        },
        {
            "category_name": "幼儿艺术综合",
            "category_id": 3,
            "list": []
        }
    ],
    "code": 200,
    "success": true
});



/*创建编辑科目*/
Mock.mock(domain+"/baseSet/categoryManage/createAndUpdateSubjectInfo.json", {
    "data":"",
    "code":200,
    "msg":"",
    "success":true
});

/*创建编辑类目*/
Mock.mock(domain+"/baseSet/categoryManage/createAndUpdateCategoryInfo.json", {
    "data":"",
    "code":200,
    "msg":"",
    "success":true
});


/*删除科目*/
Mock.mock(domain+"/baseSet/categoryManage/deleteSubjectInfo.json", {
    "data":"",
    "code":200,
    "msg":"",
    "success":true
});



/*创建编辑学期*/
Mock.mock(domain+"/baseSet/termManage/createAndUpdateTerm.json", {
    "data":"",
    "code":200,
    "msg":Mock.mock('@csentence(3, 5)'),
    "success|1":true
});

//获取学期列表
Mock.mock(domain+"/baseSet/termManage/queryTermListInfo.json", {
    "message": "",
    "data": {
        "total": 1,
        "limit": 25,
        "start": 0,
        "page": 1,
        "data": [
            {
                "term_id": 16,
                "start_time": "2018-03-01 02:32:00",
                "term_name": "2018年第一学期",
                "cur_year": "2018",
                "term_type": 1
            },
            {
                "term_id": 17,
                "start_time": "2018-11-07 02:32:00",
                "term_name": "2018年暑期",
                "cur_year": "2018",
                "term_type": 2
            },
            {
                "term_id": 18,
                "start_time": "2018-11-07 02:32:00",
                "term_name": "2018年第二学期",
                "cur_year": "2018",
                "term_type": 1
            },
            {
                "term_id": 19,
                "start_time": "2018-11-07 02:32:00",
                "term_name": "2018年第二学期寒假",
                "cur_year": "2018",
                "term_type": 2
            }
        ],
        "code": 200,
        "totalPage": 1,
        "success": true,
        "size": 1
    },
    "code": 200,
    "success": true
});



/*获取教室列表*/
Mock.mock(domain+"/baseSet/classRoomManage/queryClassRoomListInfo.json", {
    "message": "获取教室信息",
    "data": {
        "total": 3,
        "limit": 25,
        "start": 0,
        "page": 1,
        "data": [
            {
                "classroom_name": "舞蹈教室",
                "building": "第五教学楼",
                "floor": "13",
                "cam_id": 1,
                "cam_name": "炫联校区",
                "classroom_id": 3
            },
            {
                "classroom_name": "多媒体教师",
                "building": "第四教学楼",
                "floor": "7",
                "cam_id": 2,
                "cam_name": "东马路校区",
                "classroom_id": 4
            },
            {
                "classroom_name": "绘画室",
                "building": "逸夫楼",
                "floor": "3",
                "cam_id":3,
                "cam_name": "保山道校区",
                "classroom_id": 1,
                "cam_type": 1,
                "cam_address": "天津市保山道48号"
            }
        ],
        "code": 200,
        "totalPage": 1,
        "success": true,
        "size": 1
    },
    "code": 200,
    "success": true
});


/**
 * 基础设置
 * 教室
 * 创建/编辑教室
 * */
Mock.mock(domain+"/baseSet/classRoomManage/createAndUpdateClassRoomInfo.json", {
    "data":"",
    "code":200,
    "msg":Mock.mock('@csentence(3, 5)'),
    "success|1":true
});



/**
 * 基础设置
 * 合作机构
 * 获取合作机构列表
 *
 * */

Mock.mock(domain+"/baseSet/cooperativeManage/queryCooperativeListInfo.json", {
    "message": "合作机构信息列表",
    "data": {
        "total": 2,
        "limit": 25,
        "start": 0,
        "page": 1,
        "data": [
            {
                "org_id": 1,
                "cooperative_id": 1,
                "name": "星巴克[上海星巴克咖啡经营有限公司]",
                "phone": "400-820-6998",
                "linkman": "星巴克中国顾客关怀中心",
                "note":"目前，星巴克已经在中国140多个城市开设了超过3,400家门店，拥有超过45,000名星巴克伙伴。这一独特优势使我们能够在每一天，通过每一家星巴克门店，践行我们的承诺。",
                "insert_time": 1541640018000,
                "is_del": 0
            },
            {
                "org_id": 1,
                "cooperative_id": 2,
                "name": "光大金控（天津）投资管理有限公司",
                "phone": "+86 (10) 88013200",
                "linkman": "客服",
                "insert_time": 1541640043000,
                "is_del": 0
            },
            {
                "org_id": 1,
                "cooperative_id": 3,
                "name": "农夫山泉股份有限公司",
                "phone": "95077",
                "linkman": "客服",
                "note":"农夫山泉股份有限公司是中国饮料20强之一，专注于研发、推广饮用天然水、果蔬汁饮料、特殊用途饮料和茶饮料等各类软饮料。",
                "insert_time": 1541640043000,
                "is_del": 0
            }
        ],
        "code": 200,
        "totalPage": 1,
        "success": true,
        "size": 1
    },
    "code": 200,
    "success": true
});


/**
 * 基础设置
 * 合作机构
 * -- 创建合作结构接口
 * */

Mock.mock(domain+"/baseSet/cooperativeManage/createAndUpdateCooperativeInfo.json", {
    "data":"",
    "code":200,
    "msg":Mock.mock('@csentence(3, 5)'),
    "success|1":true
});

/**
 * 基础设置
 * 合作机构
 * 删除合作结构*/
Mock.mock(domain+"/baseSet/cooperativeManage/deleteCooperativeInfo.json", {
    "data":"",
    "code":200,
    "msg":Mock.mock('@csentence(3, 5)'),
    "success|1":true
});



/**
 * 基础设置
 *
 * 获取缴费规则
 *
 * */
Mock.mock(domain+"/baseSet/payRuleManage/queryPaySetInfo.json", {
    "message": "获取缴费设置信息成功",
    "data": {
        "org_id": 48,
        "rule_id": 5,
        "payment_period":4,
        "audit_switch" : 0,
        "refund_instructions":"退费审核开启时，不论家长手机退费或者教务后台退费都经过人工审核状态，学生会相应增加“退费待审核”和“退费已审核”状态，同时增加批量审核、撤销审核、财务手动操作退费等逻辑",
        "update_time": 1541641777000,
        "insert_time": 1541641086000,
        "is_del": 0
    },
    "code":200,
    "success": true
});

/**
 * 基础设置
 *  合作伙伴
 * --删除
 * */
Mock.mock(domain+"/baseSet/cooperativeManage/deleteCooperativeInfo.json", {
    "data":"",
    "code":200,
    "msg":Mock.mock('@csentence(3, 5)'),
    "success|1":true
});

/**
 * 基础设置
 * --认证规则
 * ----获取认证规则
 * */
Mock.mock(domain+"/baseSet/authManage/queryAuthSetInfo.json", {
    "message": "获取缴费设置信息成功",
    "data": {
        "org_id": 46,
        "rule_id": 3,
        "update_time": 1541640899000,
        "insert_time": 1541640675000,
        "auth_status":1,
        "certification_requirements":"2018年12月12日请携带本人身份证到炫联校区2楼201窗口办理。地址：天津市南开区东马路111号",
        "is_del": 0
    },
    "code":200,
    "success": true
});

/**
 * 基础设置
 * --认证规则
 * ----保存认证规则
 *
 * */
Mock.mock(domain+"/baseSet/authManage/createAndUpdateAuthSetInfo.json", {
    "data":"",
    "code":200,
    "msg":Mock.mock('@csentence(3, 5)'),
    "success|1":true
});




/*获取年份*/
Mock.mock(domain+"/plan/yearList.json", {
    "data": [
        "2015",
        "2016",
        "2017",
        "2018",
        "2019",
    ],
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});



/**
 * 基础设置
 * 打印凭证设置
 */

Mock.mock(domain+"/voucher/model/info.htm", {
    "message": "获取缴费设置信息成功",
    "data": {
        voucher_name: 46,
       // 模板名称
        voucher_content:{
             type_1:{top: 0,left: 0,content:"天津市少年宫智慧教育云平台"},//主标题
             type_2:{top: 0,left: 0,content:"这里是副标题信息"},//副标题

             type_3:{top: 0,left: 0,content:"2018年第二学期"},//学期
             type_4:{top: 0,left: 0,content:"刘墉"},//学员姓名
             type_5:{top: 0,left: 0,content:"152223201610105932"},//证件号
             type_6:{top: 0,left: 0,content:"李斯，王五"},//教师姓名
             type_7:{top: 0,left: 0,content:"舞蹈类"},//科目班级
             type_8:{top: 0,left: 0,content:"老生班"},//班级类型
             type_9:{top: 0,left: 0,content:"20"},//课时数
             type_10:{top: 0,left: 0,content:"2018-12-12"},//开课日期
             type_11:{top: 0,left: 0,content:"每周五晚17：00-19:00"},//上课时间
             type_12:{top: 0,left: 0,content:"5600.00"},//学费标准
             type_13:{top: 0,left: 0,content:"东马路校区"},//校区
             type_14:{top: 0,left: 0,content:"301"},//教室
             type_15:{top: 0,left: 0,content:"20"},//班容量
             type_16:{top: 0,left: 0,content:"微信支付"},//缴费方式
             type_17:{top: 386,left:50,content:"1200.00"},//缴费金额
             type_18:{top: 386,left:161,content:"壹仟贰佰圆正"},//缴费金额大写
             type_19:{top: 0,left: 0,content:"2018-11-11"},//缴费时间
             type_20:{top: 0,left: 0,content:"2018sng12453"},//凭证编号
             type_21:{top: 193,left: -734,content:"2018-11-12"},//凭证日期
             type_22:{top: 0,left: 0,content:"辛某某"},//操作人

             type_23:{top: 0,left: 0,content:"开始时间：2018-10-10"},//自定义1
             type_24:{top: 0,left: 0,content:"自定义内容二测试信心"},//自定义2
             type_25:{top: 0,left: 0,content:"自定义内容三测试信心"},//自定义3
             type_26:{top: 0,left: 0,content:"自定义内容四测试信心"},//自定义4
             type_27:{top: 0,left: 0,content:"自定义内容五测试信心"},//自定义5
             type_28:{top: 0,left: 0,content:"天津市少年儿童艺术培训学校"},//自定义6
            // type_29:{top: 0,left: 0,content:""},//自定义7
            // type_30:{top: 0,left: 0,content:"天津市少年儿童艺术培训学校"},//开票方 自定义8
        },
        //模板内容
        serial_number_format: 46,
        //编号格式
        background_length: 140,
        //背景长度
        background_width: 216,
        //背景宽度
        img_url: "00155.jpg",

    },
    "code":200,
    "success": true
});


/**
 * 网上招生模块
 * 创建招生
 * --统计班级人数
 *（班级数从分页里面获取）
 * */

Mock.mock(domain+"/plan/classData.json", {
    "data": {
        "studCount": 1600
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null

});


/**
 * 网上招生模块
 * 创建招生
 * 获取班级列表
 *
Mock.mock(domain+"/plan/selectCampusClass.json", {
    "data": {
        "code": 200,
        "size": 1,
        "total": 1,
        "page": 1,
        "start": 0,
        "limit": 10,
        "data": [
            {
                "classId": 1,
                "className": "一年1二班",
                "classType": 2,
                "classTypeStr": "新生班",
                "classStartDate": 1541756633000,
                "classStartDateStr": "18-11-09",
                "classBeginTime": 1541756633000,
                "classBeginTimeStr": "17:43",
                "classOverTime": 1541756633000,
                "classOverTimeStr": "17:43",
                "classWeek": 1,
                "classWeekStr": "周一",
                "camName": null,
                "classroomName": "302",
                "building": "第三教学楼",
                "floor": "3",
                "classSize": 25,
                "tuitionFees": "320",
                "subjectName": "儿童画",
                "teachers": "于俊新,李明艳",
                "appplyCount": 26,
                "ageRange": null,
                "usableNum": null,
                "classUnsetTime": null,
                "isApply": null
            },
            {
                "classId": 2,
                "className": "一年二班",
                "classType": 1,
                "classTypeStr": "老生班",
                "classStartDate": 1541756633000,
                "classStartDateStr": "18-11-09",
                "classBeginTime": 1541756633000,
                "classBeginTimeStr": "17:43",
                "classOverTime": 1541756633000,
                "classOverTimeStr": "17:43",
                "classWeek": 1,
                "classWeekStr": "周一",
                "camName": null,
                "classroomName": "302",
                "building": "第三教学楼",
                "floor": "3",
                "classSize": 25,
                "tuitionFees": "320",
                "subjectName": "儿童画",
                "teachers": "于俊新,李明艳",
                "appplyCount": 26,
                "ageRange": null,
                "usableNum": null,
                "classUnsetTime": null,
                "isApply": null
            }
        ],
        "success": false,
        "totalPage": 1
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});*/
/**
 * 网上招生模块
 * 招生记录
 * 获取计划列表
 * */
Mock.mock(domain+"/plan/list.json", {
    "data": {
        "code": 200,
        "size": 1,
        "total": 2,
        "page": 1,
        "start": 0,
        "limit": 10,
        "data|20": [
            {
                "planId|+1": 2,
                "orgId": null,
                "termId": null,
                "classType": 1,
                "planType": null,
                "title|+1": '@cword(3)'+"-计划",
                "beginTime": 1514779200000,
                "endTime": 1518400800000,
                "content": null,
                "planSwitch|1": [0,1],
                "isDel": null,
                "insertTime": null,
                "updateTime": null,
                "delTime": null,
                "creater": null,
                "progress": '@integer(10, 50)'+"/"+'@integer(50, 60)',
                "classCount": "1",
                "endTimeStr": Mock.Random.datetime('yyyy-MM-dd HH:mm:ss'),
                "tuition": '@integer(5000, 6000)',
                "beginTimeStr": Mock.Random.datetime('yyyy-MM-dd HH:mm:ss'),
            }
        ],
        "success": false,
        "totalPage": 1
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 * 网上招生模块
 * 招生记录
 * 获取备选班级
 * */
Mock.mock(domain+"/plan/selectCampusClass.json", {
    "data": {
        "code": 200,
        "size": 1,
        "total": 1,
        "page": 1,
        "start": 0,
        "limit": 10,
        "data|20": [
            {
                "classId|+1": 1,
                "className": '@cword()'+"162-1-2B",
                "classType|1": ["1","2"],
                "classTypeStr|1": ["新生班","老生班"],
                "classStartDate": 1541756633000,
                "classStartDateStr|+1": Mock.Random.datetime('yyyy-MM-dd'),
                "classBeginTime": 1541756633000,
                "classBeginTimeStr": "17:43",
                "classOverTime": 1541756633000,
                "classOverTimeStr": '@time("HH:mm")',
                "classWeek": 1,
                "classWeekStr|1": ["周六","周五","周四"],
                "camName|1":["炫联","东马路","黄河道","西青道"],
                "classroomName": '@integer(1, 9)'+'@integer(0, 9)'+'@integer(0, 9)',
                "building": "第"+ '@cword("一二三四五六七八九十")' + "教学楼",
                "floor": "3",
                "classSize": 25,
                "tuitionFees": '@integer(500, 6000)',
                "subjectName|1": ["舞蹈类","美术类","播音类","书法类"],
                "teachers": '@cname()'+",李明艳",
                "appplyCount": 26,
                "ageRange": null,
                "usableNum": null,
                "classUnsetTime": null,
                "isApply": null
            }
        ],
        "success": false,
        "totalPage": 1
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 * 网上招生模块
 * 招生记录
 * 获取计划--详情
 * */
Mock.mock(domain+"/plan/info.json", {
    "data": {
        "planId|1": [16,17,18,19],
        "orgId": 46,
        "termId|1": [16,17,18,19],
        "classType": 1,
        "planType": 1,
        "title": Mock.mock('@cword(3)')+"-计划",
        "beginTime": 1514779200000,
        "endTime": 1518400800000,
        "content": Mock.mock('@cword(100)'),
        "planSwitch|1": [0,1],
        "isDel": 0,
        "termName":"2018年第二学期",
        "insertTime": 1540522978000,
        "updateTime": 1540522978000,
        "delTime": null,
        "creater": "",
        "endTimeStr": "2018-02-12 10:00:00",
        "classIds": Mock.mock('@range(1, 10, 2)').join(","),
        "serverTime": "2018-11-12 11:21:18",
        "beginTimeStr": "2018-01-01 12:00:00"
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});

/**
 * 网上招生模块
 * 招生记录
 * 保存（创建和编辑）--详情
 * */
Mock.mock(domain+"/plan/save.json", {
    "data":"",
    "code":200,
    "msg":Mock.mock('@csentence(3, 5)'),
    "success|1":true
});
/**
 *
 * 网上招生模块
 * 招生创建
 * 统计学生数
 * */
Mock.mock(domain+"/plan/classData.json", {
    "data": {
        "studCount|18-28":0
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 *
 * 网上招生模块
 * 招生创建
 * 获取分页班级id
 * */
Mock.mock(domain+"/plan/selectAllClass.json", {
    "data":Mock.mock('@range(5,100,3)'),
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});



/**
 * 日志列表
 * */

Mock.mock(domain+"/logger/list.htm", {
    "data": {
        "code": 200,
        "size": 50,
        "total": 1000,
        "page": 1,
        "start": 0,
        "limit": 20,
        "data|20": [
            {
                "user_type_id": 88,
                "operation_id|+1":1,
                "target_name":  '@cname',
                "user_type": 88,
                "operation_time": Mock.mock('@datetime'),
                "insert_time":Mock.mock('@datetime'),
                "target_id": 123,
                "org_id": 1,
                "content": '@cname' + "  ("+ Mock.mock('@integer(10000)')   + "）  " +"创建班级",
                "user_type_name":  '@cname',
                "update_time": null,
                "cam_id": 1,
                "del_time": null,
                "action|1": [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18],
                "is_del": 0,
                "target_type": 2
            }
        ],
        "success": false,
        "totalPage": 2
    },
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});


/**
 *
 * 教师管理
 * 教师列表
 * 获取分页班级id
 * */
Mock.mock(domain+"/protal/teacherManage/queryTeacherListInfo.json", {
    "message": "",
    "data": {
        "total": 100,
        "limit": 20,
        "start": 0,
        "page": 1,
        "size": 5,
        "data|15": [
            {
                "tech_id|+1":1,
                "org_id|1":[1,2,3,167],
                "cooperative_id" :"23",
                "teacher_name" : '@cname',
                "type|1":[1,2,3],//"教师类型 1专职 2外聘 3联合办学"
                "mobile":/^1[3785][1-9]\d{8}/,
                "note":'@csentence()',
            },
]
},
"code": 200,
    "success": true
});

/**
 *
 * 教师管理
 * 教师列表
 * 获取分页班级id
 * */
Mock.mock(domain+"/protal/teacherManage/deleteTeacherInfo.json", {
    "data":Mock.mock('@range(5,100,3)'),
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});

/**
 *
 * 教师管理
 * 教师列表
 * 停用教师
 * */
Mock.mock(domain+"/protal/teacherManage/stopTeacherInfo.json", {
    "data":Mock.mock('@range(5,100,3)'),
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 *
 * 教师管理
 * 教师列表
 * 添加编辑教师
 * */
Mock.mock(domain+"/protal/teacherManage/createAndUpdateTeacherInfo.json", {
    "data":Mock.mock('@range(5,100,3)'),
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 * 班级管理
 * 批量上传--（通过Excel上传）
 *
 */
 Mock.mock(domain + "/class/import/classOrStu.htm", {
     "data": {
         "errNum|0-10": 10,
         "total|1-100": function() {
             return this.errNum + this.sucNum
         },
         "sucNum|1-100": 100
     },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 * 班级管理
 * 获取班级信息--编辑的时候使用
 *
 */
Mock.mock(domain + "/class/toUpdate.htm", {
    "data": {
        "tech_ids": "2,12,13",
        "tech_names": "王岩,李会文",
        "start_birthday": '@date("yyyy-MM-dd")',
        "classroom_id":3,
        "subject_id": 3,
        "term_id": 17,
        "class_unset_time": null,
        "category_id": 1,
        "class_week": 1,
        "age_range": "3-6",
        "size": 55,
        "class_start_date": "2018-11-11",
        "end_birthday": "2015-01-01",
        "class_begin_time": "08:11",
        "class_over_time": "10:11",
        "cam_id":1,
        "cooperation_id|1":[1,2,3],
        "clas_id": 35,
        "clas_name": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
        "total_hours": "@integer(10, 100)",
        "tuition_fees": "@integer(600, 10000)",
        "clas_type": 1
    },
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 班级管理
 * --更新班级
 */

Mock.mock(domain+"/class/update.htm", {
    "data":Mock.mock('@range(5,100,3)'),
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});

/**
 * 班级管理
 * --添加新班级
 *
 */
Mock.mock(domain+"/class/add.htm", {
    "data":Mock.mock('@range(5,100,3)'),
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});

/**
 * 班级管理
 * --班级列表
 */
Mock.mock(domain+"/class/class/list.htm", {
    "data": {
        "code": 200,
        "size": 3,
        "total": 22,
        "page": 1,
        "start": 0,
        "limit": 10,
        "data|20": [
            {
                "clas_id|+1": 1,
                "category_id": null,
                "subject_id": null,
                "cam_id": null,
                "clas_name": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                "clas_type": '@natural(1, 2)',
                "clas_type_name": null,
                "cam_name": '@county()'+"校区",
                "age_range": "3-9",
                "class_start_date": 1542187264000,
                "class_start_date_str": '@date("yyyy-MM-dd")',
                "tuition_fees": '@natural(500, 1000)',
                "size": 30,
                "ss_num": 0,
                "num": 50,
                "ss_money": "0",
                "st_money": "0",
                "category_name": "舞蹈",
                "subject_name|1": ["民族舞","儿童画","国学","主持","国学"],
                "classroom_id": 29,
                "classroom_name": '@integer(1, 9)'+"0"+'@integer(0, 9)'+"教室",
                "building": "第"+'@cword(\"一二三四五六七八九十\")'+"教学楼",
                "floor": '@integer(1, 10)',
                "start_birthday": 1542187264000,
                "start_birthday_str": "2018-11-14 17:21:04",
                "end_birthday": 1542187264000,
                "end_birthday_str": "2018-11-14 17:21:04",
                "total_hours": 5,
                "class_week": 3,
                "class_week_name": null,
                "class_begin_time": '@time("HH:mm")',
                "class_over_time": '@time("HH:mm")',
                "class_unset_time": null,
                "cooperation_id": 5,
                "name": "合作机构21",
                "tech_names": "郭琳_13302151330",
                "tech_name|2": '@cname()'+",",
                "tech_id": "3562",
                "user_id": "11498",
                "divide": '@natural(0, 50)'+"/50",
                "ss_tuition": '@natural(0, 100050)',
                "error_msg": null,
                "plan_id": 1,
                "plan_switch|1": ["0","1"]
            }
        ],
        "success": false,
        "totalPage": 3
    },
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 *
 * 班级管理
 * 统计班级（含分页）学生数，缴费总额，班级总数
 * 获取分页班级id
 * */
Mock.mock(domain+"/class/statistics/list.htm", {
    "data": {
        ss_num:'@integer(60, 200)',
        sjs_money:'@integer(1060, 100000)',
        clas_id: "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,26,28,29,30,31,32,35",
     },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});

/**
 * 班级管理
 * 班级管理
 * 查询学员信息
 * */
Mock.mock(domain+"/class/queryByCard.htm", {
    "data": {
        "user_idnumber": /^[1-9]\d{17}/,
        "user_type": 1,
        "stud_id": 3839,
        "stud_name": '@cname'
    },
    "success": true,
    "message": '@csentence(3, 5)',
    "code": 200,
    "jsessionid": null
});




/**
 * 班级管理
 * 班级管理
 * 查询班级详情---学员信息
 * */
//上半句风起云涌，逐浪滔天，下半句银鞍白马，虎骑千重
Mock.mock(domain+"/class/student/list.htm", {
    "data": {
        "code": 200,
        "size": 2,
        "total": 17,
        "page": 2,
        "start": 10,
        "limit": 10,
        "data|20": [
            {
                "user_idnumber":/^[1-9]\d{17}/,
                "is_print|1": ["未打印","已打印"],
                "stud_id": 3852,
                "stud_name": '@cname',
                "txnAmt": '@integer(1000, 5000)',
                "pay_method|1": [1,2,3],
                "online_pay": 0,
                "tf_money": '@integer(1000, 5000)',
                "voucher_no": null,
                "money":'@integer(1000, 5000)',
                "clas_id|+1": 1,
                "offline_pay": 0,
                "subject_name|1": ["民族舞","儿童画","国学","主持","国学"],
                "clas_name": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                "stu_status|1": [1,2,3,5,4,6,7,8]
            }
        ],
        "success": false,
        "totalPage": 2
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 * 班级管理
 * 班级管理
 * 插班保存
 * */
Mock.mock(domain+"/class/joinClass.htm", {
    "data":"",
    "success": true,
    "message": '@csentence(3, 5)',
    "code": 200,
    "jsessionid": null
});

/**
 * 学员通知模块
 * 发送通知
 * 获取班级列表
 * */
Mock.mock(domain+"/notice/getClassList.json", {
    "data": {
        "code": 200,
        "size": 1,
        "total": 20,
        "page": 1,
        "start": 0,
        "limit": 10,
        "data|20": [
            {
                "classId|+1": 1,
                "className": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                "classType": 1,
                "classTypeStr|1": ["老生班","新生班"],
                "classStartDate": 1541756633000,
                "classStartDateStr": "2018-11-09",
                "classBeginTime": 1541756633000,
                "classBeginTimeStr": "17:43",
                "classOverTime": 1541756633000,
                "classOverTimeStr": "17:43",
                "classWeek": 1,
                "classWeekStr": "周"+'@cword("一二三四五六七八")',
                "camName": '@county()'+"校区",
                "classroomName": '@integer(1, 9)'+"0"+'@integer(0, 9)'+"室",
                "building": "第"+'@cword(\"一二三四五六七八九十\")'+"教学楼",
                "floor": '@integer(1, 10)',
                "classSize":'@integer(10, 20)',
                "tuitionFees": '@natural(560, 2000)',
                "subjectName|1": ["民族舞","儿童画","国学","主持","国学"],
                "teachers|1": ['@cname()','@cname()','@cname()'],
                "appplyCount": 26,
                "ageRange": null,
                "usableNum": null,
                "classUnsetTime": null,
                "isApply": null
            }
        ],
        "success": false,
        "totalPage": 1
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 * 学员通知模块
 * 发送通知
 * 发送通知接口
 * */
Mock.mock(domain+"/notice/submitStudentNoticeForm.htm", {
    "data":"",
    "success": true,
    "message": '@csentence(3, 5)',
    "code": 200,
    "jsessionid": null
});


/**
 * 学员通知模块
 * 创建招生
 * --统计班级人数
 *（班级数从分页里面获取）
 * */

Mock.mock(domain+"/notice/classData.json", {
    "data": {
        "studCount": 1600
    },
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});


/**
 * 用户中心
 * --用户列表
 * */

Mock.mock(domain+"/userAuth/queryUserDetailsInfo.json", {
    "data": {
        "code": 200,
        "size": 50,
        "total": 1000,
        "page": 1,
        "start": 0,
        "limit": 20,
        "data|20": [
            {
                "org_id": 46,
                "auth_status|1": [0,1],
                "card_type|1": [1,2,3],
                "user_register_id|+1":'@natural(20000, 30000)',
                "relation|1": [1,2,3,4,5,6],
                "brithday": "2011-11-27",
                "card_num": /^[1-9]\d{17}/,
                "stud_name": '@cname',
                "telephone": /^[1][3578]\d{9}/,
            }
        ],
        "success": false,
        "totalPage":20
    },
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 用户中心
 * --获取验证码
 * */
Mock.mock(domain+"/api/sendValidateCode4Register", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 用户中心
 * --修改手机号
 * */
Mock.mock(domain+"/baseuser/updateMobile", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 用户中心
 * --删除用户
 * */
Mock.mock(domain+"/userAuth/delUserDetailsInfo.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 學員管理
 * --Del student
 * */
Mock.mock(domain+"/student/batchDelStuInfo.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});


/**
 * 用户中心
 * --保存信息
 * */
Mock.mock(domain+"/userAuth/updateUserDetailsInfo.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});


/**
 * 学员管理
 * 获取学生列表
 */
Mock.mock(domain+"/student/getSutdentList.json", {
    "data": {
        money:'@natural(20000, 201000)',
        page:{
            "data|20": [
                {
                    "stud_id|+1": 1,
                    "stud_name": '@cname()',
                    "user_idnumber": /^[1-9]\d{17}/,
                    "clas_id|+1": 1,
                    "clas_name": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                    "tech_name": '@cname()'+",",
                    "stu_status|1": [1,2,3,4,5,6,7,8],
                    "tuition_fees": '@natural(0, 100050)',
                    "pay_method|1":[1,2,3],
                    "cd_id|+1": 1,
                    "cid|+1": 1,
                    "subject_name|1": ["民族舞","儿童画","国学","主持","国学"],
                    "is_print|1":[0,1],
                    "status|1":[1,2,3],
                    "money": '@natural(200, 1000)',
                    "txnAmt": '@natural(200, 1000)',
                    "tf_money":'@natural(200, 1000)',
                    "stu_clas_id": 1
                }
            ],
            "size": 3,
            "total": 22,
            "page": 1,
            "start": 0,
            "limit": 10,
            "success": false,
            "totalPage": 3
        },
    },
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 学员管理
 * --缴费操作
 * */
Mock.mock(domain+"/portal/charge/paid.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 学员管理
 * --名额保留
 *
 */
Mock.mock(domain+"/portal/charge/orderPay.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 学员管理
 * --催款接口
 *
 */
Mock.mock(domain+"/portal/charge/remind.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});


/**
 * 学员管理
 * --申请退费接口
 *
 */
Mock.mock(domain+"/portal/charge/refund.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 学员管理
 * --取消退费接口
 *
 */
Mock.mock(domain+"/portal/charge/tryCancelRefund.json", {
    "data":"",
    "success": true,
    "message": "成功",
    "code": 200,
    "jsessionid": null
});

/**
 * 学员管理
 * --转班可转班查询
 *         domain+"/student/queryMoveClassListInfo.json"
 */
Mock.mock(domain+"/student/queryMoveClassListInfo.json", {
    "data|3": [
        {
            "clas2stud_id": null,
            "stud_id": null,
            "clas_id": null,
            "org_id": null,
            "stud_name": null,
            "clas_name": null,
            "grade_id": null,
            "grade_name": null,
            "user_id": null,
            "term_id": null,
            "plan_id": null,
            "category_id": null,
            "subject_id": null,
            "cam_id": 20,
            "classroom_id": null,
            "size": null,
            "age_range": null,
            "start_birthday": null,
            "end_birthday": null,
            "class_start_date": null,
            "class_week": null,
            "class_begin_time": null,
            "class_over_time": null,
            "tuition_fees": null,
            "stu_status": null,
            "tech_id": null,
            "tech_name": null,
            "cam_name": '@county()'+"校区",
            "queryContext": null,
            "errorMessage": null,
            "usable_num": null,
            "user_mobile": null,
            "isExist": 0,
            "user_idnumber": null,
            "class_unset_time": null,
            "birthday": null,
            "category_name": null,
            "subject_name": null,

            "list|5": [
                {
                    "clas2stud_id": null,
                    "stud_id": null,
                    "clas_id": 2,
                    "org_id": 46,
                    "stud_name": null,
                    "clas_name":'@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                    "grade_id": null,
                    "grade_name": null,
                    "user_id": null,
                    "term_id": 17,
                    "plan_id": 3,
                    "category_id": 31,
                    "subject_id": 33,
                    "cam_id": 20,
                    "classroom_id": 28,
                    "size": 58,
                    "age_range": "3-6",
                    "start_birthday": "2018-11-14",
                    "end_birthday": "2018-11-14",
                    "class_start_date": "2018-11-14",
                    "class_week": 2,
                    "class_begin_time": "17:21",
                    "class_over_time": "17:21",
                    "tuition_fees": "500",
                    "stu_status": null,
                    "tech_id": "3558,3561",
                    "tech_name": '@cname()'+","+'@cname()',
                    "cam_name": '@county()'+"校区",
                    "queryContext": null,
                    "errorMessage": "学员:王梓仲在班级中存在",
                    "usable_num": null,
                    "user_mobile": "18702279578",
                    "isExist": 1,
                    "user_idnumber": null,
                    "class_unset_time": null,
                    "birthday": null,
                    "category_name": "舞蹈",
                    "subject_name|1": ["芭蕾舞","水彩班","国画班","拉丁舞","声乐","珠心算","打击乐","英语","主持人"],
                    "list": []
                }
            ]
        }
    ],
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});

/**
 * 学员管理
 * --撤销退费
 *
 */
Mock.mock(domain+"/portal/charge/tryCancelRefund.json", {
    "data":"",
    "success": true,
    "message": "",
    "code": 200,
    "jsessionid": null
});

/**
 * 财务管理
 * --退费处理
 *----列表
 */
Mock.mock(domain+"/portal/charge/getRefundApplyList.json", {
    "data": {
        money:'@natural(20000, 201000)',
        page:{
            "data|20": [
                {
                    "stud_id|+1":1,
                    "stud_name":'@cname()',
                    //学生姓名
                        "user_idnumber":  /^[1-9]\d{17}/,
                   // 学生身份证号
                    "clas_name": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                   // 班级名称": 1,
                        "tech_name": '@cname()'+","+'@cname()',
                   // 教师姓名
                    "tuition_fees":'@natural(0, 100050)',
                   // 学费标准
                        "pay_method|1": [1,2,3],
                  //  缴费方式
                    "txnAmt": '@natural(200, 1000)',
                 //   缴费金额
                    "tf_money": '@natural(200, 1000)',
                  //  退费金额
                    "txnTime":'@date("yyyy-MM-dd hh:mm:ss")',
                  //  支付时间
                     "insert_time": '@date("yyyy-MM-dd hh:mm:ss")',  // 日期
                 //   退费申请时间
                    "subject_name|1": ["民族舞","儿童画","国学","主持","国学"],
                 //   类目名称
                }
            ],
            "size": 3,
            "total": 22,
            "page": 1,
            "start": 0,
            "limit": 10,
            "success": false,
            "totalPage": 3
        },
    },
    "success": true,
    "message": "成功",
    "code": 200,


});




/**
 * 财务管理
 * --财务统计
 *----列表
 */
Mock.mock(domain+"/portal/charge/getRecordList.json", {
    "data": {
        money:'@natural(20000, 201000)',
        page:{
            "data|20": [
                {
                    "stud_id|+1":1,
                   /* "stud_name":'@cname()',
                    //学生姓名
                    "user_idnumber":  /^[1-9]\d{17}/,
                    // 学生身份证号
                    "clas_name": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                    // 班级名称": 1,
                    "tech_name": '@cname()'+","+'@cname()',
                    // 教师姓名
                    "tuition_fees":'@natural(0, 100050)',
                    // 学费标准
                    "pay_method|1": [1,2,3],
                    //  缴费方式
                    "txnAmt": '@natural(200, 1000)',
                    //   缴费金额
                    "tf_money": '@natural(200, 1000)',
                    //  退费金额
                    "txnTime":'@date("yyyy-MM-dd hh:mm:ss")',
                    //  支付时间
                    "insert_time": '@date("yyyy-MM-dd hh:mm:ss")',  // 日期
                    //   退费申请时间
                    "subject_name|1": ["民族舞","儿童画","国学","主持","国学"],*/
                    //   类目名称
                    /*"queryId":  /]\d{12}/,*/
                    "stud_name":'@cname()',
                    //学生姓名
                    "user_idnumber":'@cname()',
                    //学生身份证号
                    "category_name":  /^[1-9]\d{17}/,

                    //类目名称
                    "subject_name|1": ["民族舞","儿童画","国学","主持","国学"],
                   // 科目名称
                    "clas_name": '@cword("一二三四五六七八九十")'+"年"+'@cword("一二三四五六七八九十")'+"班",
                    //班级名称
                    "tech_name":'@cname()'+","+'@cname()',

                    //教师名称
                    "tuition_fees":'@natural(200, 1000)',

                    //学费标准
                    "txnMethod|1": [1,2],
                    //交易类型
                    "pay_method|1": [1,2,3],
                    //支付方式
                    "txnAmt":'@natural(200, 1000)',
                    //交易金额
                    "queryId":/^[9]\d{19}/,
                    //流水号
                    "txnTime":'@date("yyyy-MM-dd hh:mm:ss")'
                    //交易时间
                }
            ],
            "size": 3,
            "total": 22,
            "page": 1,
            "start": 0,
            "limit": 10,
            "success": false,
            "totalPage": 3
        },
    },
    "success": true,
    "message": "成功",
    "code": 200,


});