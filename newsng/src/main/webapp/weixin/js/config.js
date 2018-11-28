/*
 * 保存获取接口
 * 参数说明：
 * param:[url] 请求地址
 * param:[data] 参数
 * param:[okCallback] 回调函数
 * param:[post|get]方法
 * 使用方法：getData("dep/getAllTeachers.htm?a="+Math.random(),{},function(data){console.info(data);})
 */
function getData(url, data, okCallback, method, loadText, failback) {
    var ajaxmethod = method || "get";
    var tipText = loadText || "加载中";
    $.ajax({
        url: url,
        data: data,
        async: true,
        type: ajaxmethod,
        dataType: 'json',
        beforeSend: function () {
            $.showPreloader(tipText);
        },
        success: function (result, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                //回调
                okCallback(result);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //alert(textStatus);
            //console.info(XMLHttpRequest.status);
            if (failback) {
                failback();
            }
        },
        complete: function () {
            $.hidePreloader();
        },
    });
};


function getDataNoLoading(url, data, okCallback, method) {
    var ajaxmethod = method || "get";
    $.ajax({
        url: url,
        data: data,
        async: true,
        type: ajaxmethod,
        dataType: 'json',
        success: function (result, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                //回调
                okCallback(result);
            }
        }
    });
};




/**
 * 名称：时间格式转换
 * @param参数 startDiffTime yyyy-mm-dd 需要转换的时间
 * @returns {*} yyyy/mm/dd
 * 描述：处理浏览器时间不兼容问题（-变/）
 * */
function GetDateDiff(startDiffTime) {
    startTime = startDiffTime.replace(/\-/g, "/");
    return startTime
};



/**
 * 根据时长转换成天小时分钟秒
 * @param参数 mss毫秒
 * @returns {*} 格式化后的天、小时、分钟、秒
 * */
function formatDuring(mss) {
    var days = parseInt(mss / (1000 * 60 * 60 * 24));
    var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = parseInt((mss % (1000 * 60)) / 1000);
    return (days < 0 ? "0" : days) + "天" + (hours < 0 ? "0" : hours) + "小时" + (minutes < 0 ? "0" : minutes) + "分钟" + (seconds < 0 ? "0" : seconds) + "秒";
}

/**
 * 时分秒转秒
 * @param time 需要格式化的时间
 * @returns {*} 格式化后的秒
 * @使用  time_to_sec("01:01:01")
 */
var time_to_sec = function (time) {
    var hour = parseInt(time.split(":")[0] * 3600);
    var min = parseInt(time.split(":")[1] * 60);
    var sec = parseInt(time.split(":")[2] == undefined ? "00" : time.split(":")[2]);
    return hour + min + sec;
}






/**
 * 函数isEqual：判断两个对象是否相等
 * 用法：arr.removeByValue(value)
 * 描述：判断两个对象是否相等，相等返回true，否则false
 * isEqual：判断两个对象是否键值对应相等
 */

function isEqual(a, b) {
    //如果a和b本来就全等
    if (a === b) {
        //判断是否为0和-0
        return a !== 0 || 1 / a === 1 / b;
    }
    //判断是否为null和undefined
    if (a == null || b == null) {
        return a === b;
    }
    //接下来判断a和b的数据类型
    var classNameA = toString.call(a),
        classNameB = toString.call(b);
    //如果数据类型不相等，则返回false
    if (classNameA !== classNameB) {
        return false;
    }
    //如果数据类型相等，再根据不同数据类型分别判断
    switch (classNameA) {
        case '[object RegExp]':
        case '[object String]':
            //进行字符串转换比较
            return '' + a === '' + b;
        case '[object Number]':
            //进行数字转换比较,判断是否为NaN
            if (+a !== +a) {
                return +b !== +b;
            }
            //判断是否为0或-0
            return +a === 0 ? 1 / +a === 1 / b : +a === +b;
        case '[object Date]':
        case '[object Boolean]':
            return +a === +b;
    }
    //如果是对象类型
    if (classNameA == '[object Object]') {
        //获取a和b的属性长度
        var propsA = Object.getOwnPropertyNames(a),
            propsB = Object.getOwnPropertyNames(b);
        if (propsA.length != propsB.length) {
            return false;
        }
        for (var i = 0; i < propsA.length; i++) {
            var propName = propsA[i];
            //如果对应属性对应值不相等，则返回false
            if (a[propName] !== b[propName]) {
                return false;
            }
        }
        return true;
    }
    //如果是数组类型
    if (classNameA == '[object Array]') {
        if (a.toString() == b.toString()) {
            return true;
        }
        return false;
    }
}



/*
 *函数： 获取cookie的值
 *
 */
function getCookie(name) {
    var cookies = document.cookie.split(";");
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var cookieStr = cookie.split("=");
        if (cookieStr && cookieStr[0].trim() == name) {
            return decodeURI(cookieStr[1]);
        }
    }
}



/*
 * 函数：图片加载失败统一选用默认图片处理
 * 全局函数：所有图片加载失败时默认都调用
 */
/*$("img").error(function(e){
    var  $this=$(this);
    setTimeout(function () {
        $this.attr('src', "http://yun.5tree.cn/shijiwxy/weixin/images/error.gif");
    },100)

});*/

$('img').error(function () {
    $(this).attr('src', "http://yun.5tree.cn/shijiwxy/weixin/images/error.gif");
})


/**
 * 函数：链接JSON成为一个新对象
 * 描述：a,b 连个JSON祝贺成一个新的JSON
 */
function extend(destination, source) {
    for (var property in source)
        destination[property] = source[property];
    return destination;
}

/*
 * 函数：获取jssdk权限
 * 描述：通过服务器获取微信硬件访问权限
 */
function getJPermissions(orgid) {
    var returnObj = "";
    $.ajax({
        "url": domainName + '/shijiwxy/wechat/portal/getWxJsConfig.json',
        "method": "POST",
        "data": {
            org_id: orgid,
            url: window.location.href,
            token: JSON.parse(sessionStorage.baseUser).token,
            version: 3,
            udid: JSON.parse(sessionStorage.baseUser).udid
        },
        "cache": false,
        "async": false,
        success: function (result, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                //回调
                returnObj = result.data;
            }
        }
    });
    return returnObj
}

/*
 * 函数：图片大图浏览
 * 使用场景：通知详情/作业详情/校园通知/博客
 * 使用方法：initPhotoSwipeFromDOM('.my-gallery'); zepto对象
 *
 */
var initPhotoSwipeFromDOM = function (gallerySelector) {

    var parseThumbnailElements = function (el) {
        var thumbElements = el.childNodes,
            numNodes = thumbElements.length,
            items = [],
            figureEl,
            linkEl,
            size,
            item;

        for (var i = 0; i < numNodes; i++) {
            figureEl = thumbElements[i]; // <figure> element
            if (figureEl.nodeType !== 1) {
                continue;
            }
            linkEl = figureEl.children[0]; // <a> element
            size = linkEl.getAttribute('data-size').split('x');
            item = {
                src: linkEl.getAttribute('href'),
                w: parseInt(size[0], 10),
                h: parseInt(size[1], 10)
            };

            console.info(item);
            if (figureEl.children.length > 1) {
                item.title = figureEl.children[1].innerHTML;
            }
            if (linkEl.children.length > 0) {
                item.msrc = linkEl.children[0].getAttribute('src');
            }
            item.el = figureEl; // save link to element for getThumbBoundsFn
            items.push(item);
        }
        return items;
    };

    // find nearest parent element
    var closest = function closest(el, fn) {
        return el && (fn(el) ? el : closest(el.parentNode, fn));
    };

    var onThumbnailsClick = function (e) {
        e = e || window.event;
        e.preventDefault ? e.preventDefault() : e.returnValue = false;
        var eTarget = e.target || e.srcElement;
        // find root element of slide
        var clickedListItem = closest(eTarget, function (el) {
            return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
        });
        if (!clickedListItem) {
            return;
        }
        var clickedGallery = clickedListItem.parentNode,
            childNodes = clickedListItem.parentNode.childNodes,
            numChildNodes = childNodes.length,
            nodeIndex = 0,
            index;
        for (var i = 0; i < numChildNodes; i++) {
            if (childNodes[i].nodeType !== 1) {
                continue;
            }
            if (childNodes[i] === clickedListItem) {
                index = nodeIndex;
                break;
            }
            nodeIndex++;
        }
        if (index >= 0) {
            openPhotoSwipe(index, clickedGallery);
        }
        return false;
    };

    // parse picture index and gallery index from URL (#&pid=1&gid=2)
    var photoswipeParseHash = function () {
        var hash = window.location.hash.substring(1),
            params = {};
        if (hash.length < 5) {
            return params;
        }
        var vars = hash.split('&');
        for (var i = 0; i < vars.length; i++) {
            if (!vars[i]) {
                continue;
            }
            var pair = vars[i].split('=');
            if (pair.length < 2) {
                continue;
            }
            params[pair[0]] = pair[1];
        }
        if (params.gid) {
            params.gid = parseInt(params.gid, 10);
        }
        return params;
    };

    var openPhotoSwipe = function (index, galleryElement, disableAnimation, fromURL) {
        var pswpElement = document.querySelectorAll('.pswp')[0],
            gallery,
            options,
            items;
        items = parseThumbnailElements(galleryElement);
        options = {
            galleryUID: galleryElement.getAttribute('data-pswp-uid'),
            getThumbBoundsFn: function (index) {
                var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
                    pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
                    rect = thumbnail.getBoundingClientRect();
                return {
                    x: rect.left,
                    y: rect.top + pageYScroll,
                    w: rect.width
                };
            },
            isClickableElement: function (el) {
                return true;
            }

        };
        if (fromURL) {
            if (options.galleryPIDs) {
                for (var j = 0; j < items.length; j++) {
                    if (items[j].pid == index) {
                        options.index = j;
                        break;
                    }
                }
            } else {
                options.index = parseInt(index, 10) - 1;
            }
        } else {
            options.index = parseInt(index, 10);
        }
        if (isNaN(options.index)) {
            return;
        }
        if (disableAnimation) {
            options.showAnimationDuration = 0;
        }
        gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
        gallery.init();
    };

    var galleryElements = document.querySelectorAll(gallerySelector);
    for (var i = 0, l = galleryElements.length; i < l; i++) {
        galleryElements[i].setAttribute('data-pswp-uid', i + 1);
        galleryElements[i].onclick = onThumbnailsClick;
    }
    var hashData = photoswipeParseHash();
    if (hashData.pid && hashData.gid) {
        openPhotoSwipe(hashData.pid, galleryElements[hashData.gid - 1], true, true);
    }
};

/*
 * 函数：获取照片的原始尺寸
 * 参数为dom对象
 * 返回宽高
 */

function getNaturalSize(Domlement) {
        var natureSize = {};
        //检测图片的宽度
    if (typeof Domlement.naturalWidth !== "undefined") {
        natureSize.width = Domlement.naturalWidth;
        natureSize.height = Domlement.naturalHeight;
    } else {
        var img = new Image();
        img.src = Domlement.src;
        natureSize.width = img.width;
        natureSize.height = img.height;
    }
    return natureSize;
}

/*
 *函数：判断图片是否完成并且获取原始尺寸
 */
function getWH(obj) {
    var natural = getNaturalSize(obj);
    $(obj).show();
    if ($(obj).next().length > 0) {
        $(obj).next().hide();
    }
    $(obj).parent().attr("data-size", natural.width + "x" + natural.height);
}




/**
 * 预加载图片||加载无内容&网络请求错误显示图片
 */
function noContentImg(divId, imgurl) {
    var getRootPath = function () {
        //获取当前网址，如： http://localhost:8083/uim/********/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uim/******/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        return localhostPaht;
    }
    /*预加载图片*/
    // var url = getRootPath();
    var url = '../../../';
    var urlImg = url + imgurl; //警告图标路径
    var imgPreLoadBox = [];
    var preLoadImgs = function (i, url) {
        imgPreLoadBox[i] = new Image(); //创建一个Image对象，实现图片的预下载
        imgPreLoadBox[i].src = url;
        return imgPreLoadBox[i];
    }
    var img = preLoadImgs(0, urlImg);
    /*将预加载图片插入到div*/
    var appendImgTo = function (divId, img) {
        if (img.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数
            $(divId).before(img);
            return; // 直接返回，不用再处理onload事件
        }
        img.onload = function () {
            $(divId).before(img);
        }
    }
    return {
        preLoadImgs: img,
        appendImgTo: appendImgTo(divId, img),
    }
}

/*
 * 清空输入框按钮&&输入手机号只能输入数字(在不改变当前dom结构的情况下)
 * 使用场景:登录页,忘记密码等与手机号输入框相关页面
 * 使用方法,定义selector[输入框1ID,2ID...]对selector进行forEach
 * selector.forEach(function (item) {
		$(item).after('<span class="clear-btn" style="display:none;">×</span>'); 循环遍历每个输入框后方添加清空按钮
		$(item).on('input propertychange', function () {
				clearInputText(item);
			}).on('blur', function () {
				$(item + '+.clear-btn').hide();
			}).on('focus', function () {
				clearInputText(item);
			})
	})
 */
function clearInputText(selector) {
    var is_msg, is_tel, val = $.trim($(selector).val());
    var span = $(selector + '+.clear-btn');
    $(selector).siblings().length > 1 ? is_msg = 1 : is_msg = 0;
    $(selector).attr('type') == 'tel' ? is_tel = 1 : is_tel = 0;
    is_tel != 0 ? $(selector).val($(selector)[0].value.replace(/\D/g, "")) : '';
    if (val.length > 0) {
        var btnHeight = $(selector).height() / 2 + 'px';
        span.show();
        $(selector).css({
            'background': 'url(../../../images/delBtn.png)  no-repeat right',
            'background-size': btnHeight
        })
    } else {
        span.hide();
        $(selector).css('background', 'none');
    }
    if (is_msg != 0) {
        var rightWidth = window.getComputedStyle($(selector).siblings()[1], null)['width'];
        rightWidth = 10 + (+rightWidth.substring(0, rightWidth.length - 2)) + 'px';
        span.css('right', rightWidth);
        $(selector).parent().css('margin-right', rightWidth);
    }
    span.css('top', '50%');
    span.css('margin-top', -(span[0].offsetHeight / 2));
    $(selector + '+.clear-btn').on('touchstart', function (e) {
        e.preventDefault();
        $(selector).val('');
        $(this).hide();
        $(selector).css('background', 'none');
    }).on('click', function () {
        $(selector).focus();
    })
}


/*
 *统计代码
 */
var _mtac = {};
(function () {
    var mta = document.createElement("script");
    mta.src = "https://pingjs.qq.com/h5/stats.js?v2.0.2";
    mta.setAttribute("name", "MTAH5");
    mta.setAttribute("sid", "500592445");
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(mta, s);
})();

//处理url地址获取图片的路径
function changeImgUrl(url){
    var imgName = url.split(".")[0];
    imgName = domainName+ "/esb/res/pic/" + Math.floor(+imgName / 10000) + "/" + Math.floor(+imgName / 100) + "/" + url;
    return imgName;
}
