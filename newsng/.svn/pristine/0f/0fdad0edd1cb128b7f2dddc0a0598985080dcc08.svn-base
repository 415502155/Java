//定义折线图所有日期出勤百分比(100 - num)
$('.slide-item').css('width',(document.body.offsetWidth - 40) + 'px');
var attenLine = {
  attenLineArr: [],
  attenLineDate: []
}
var val = [];
$.ajax({
  url: require_url.attendance_url,
  data: {
    token: JSON.parse(sessionStorage.baseUser).token,
    udid: JSON.parse(sessionStorage.baseUser).udid,
    version: 3
  },
  success: function(res){
    //console.log(res.data);
    for(var d=0;d<res.data.length;d++){
      for(var k in res.data[d]){
        typeof res.data[d][k] != 'number' ? attenLine.attenLineArr.push(res.data[d][k]) : attenLine.attenLineArr.push(100 - res.data[d][k]);
        attenLine.attenLineDate.push(k.substring(5, k.length));
      }
    }
    //出勤率折线图
    var attenArr = attenLine.attenLineArr;
    var attenDate = attenLine.attenLineDate;
    var table_nodes = '';
    for (var j = 0; j < attenArr.length; j++) {
        val.push(attenArr[j]);
        table_nodes += '<div class="echarts-slide" >' +
        '<p class="echarts-date">' + attenDate[j] + '</p>' +
        '<span data-point></span>' +
        '</div>';
    }
        $('#echarts_table .table-box').append(
            '<main ng-app="myApp">' +
            '<figure ng-controller="graphCtrl">' +
            '<div graph data="sampleData">' + table_nodes + '</div>' +
            '<div style="text-align:center;clear:both"></div>' +  
            '</figure>' +
            '</main>' 
        )
        var slide_item = $('.echarts-slide').css('width');
        attenArr.length>7?attenArr.length = attenArr.length : attenArr.length = 7;
        var slideWidth = (slide_item.substring(0, slide_item.length - 2)) * attenArr.length;
        $('.slide-item').css('width', slideWidth + 'px');
        $('.echarts-slide[title]:nth-of-type(' + $(".echarts-slide[title]").length + ') [data-point]').css('height', '0');
        $('.slide-box').scrollLeft(($('.slide-item')[0].offsetWidth));
        var etop = 0;

        $('.echarts-slide span').css('top','')
  },
  error: '',
  complete:function(){
    var temp = [];
    var pt = [];
    
    $('[data-point]').each(function (index) {
      temp.push($(this))
    })
    for(var o=0;o<temp.length;o++){
      pt.push(temp[o][0]);
    }
    // console.log(temp);
    // console.log(pt);
    // console.log(val);
    
    for(var k=0;k<temp.length;k++){
      temp[k].css('top',val[k]+'%');
      var psty = pt[k] && pt[k].style;
      if(psty){
        var sect = pt[k].parentNode,
          sectWidth = sect.offsetWidth,
          sectHeight = sect.offsetHeight;
          typeof val[k] != 'number' ? sect.title = '无' : sect.title = Math.round(100 - val[k]) + '%';
        //pt[k].top = (val[k] * sectHeight / 100) + 'px';
        // psty.top = 
        var next = val[k + 1]
        if(typeof next === 'number'){
          var delta = (next - val[k]) * sectHeight / 100;
          if(typeof val[k] != 'number'){

          }else{
            psty.height = Math.sqrt(Math.pow(sectWidth, 2) + Math.pow(delta, 2)) + 'px';
            psty.webkitTransform =
              psty.msTransform =
              psty.transform =
              'rotate(' + (-Math.PI / 2 + Math.atan2(delta, sectWidth)) + 'rad)';
          }
          
        }
      }
    }
    for(var ll=0;ll<$('[title]').length;ll++){
      if($('[title]')[ll].title == '无'){
        $('[title] [data-point]')[ll].style.display = 'none';
      }
    }
  }
})