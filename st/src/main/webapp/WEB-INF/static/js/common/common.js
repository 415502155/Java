/**
 * 小写转大写
 * 
 * @param num
 * @returns
 */
var getChinseNum = function(num){
	var c_arr = ['一','二','三','四','五','六','七','八','九','十','十一','十二','十三','十四','十五','十六','十七','十八','十九','二十'];
	if(!isNaN(num) && num != undefined){
		return c_arr[num-1];
	}else{
		return -1;
	}
}