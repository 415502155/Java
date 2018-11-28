/**
 * 判断问卷是否已被删除
 * @param {string} "res.message"
 */
function isDel(mes){
    if(mes!=="deleted"){
        return false;
    }else{
        return true;
    }
}
/**
 * 兼容移动端Date.parse();
 * @param {str} 日期 '年-月-日' 
 * @return {number} 返回时间戳数字
 */
function transformTime(str) {
    str ? str = str.replace(/-/g, '/') : void(0);
    var time = (new Date(str)).getTime();
    return time;
}