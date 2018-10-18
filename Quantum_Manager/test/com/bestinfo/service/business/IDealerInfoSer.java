//package com.bestinfo.service.business;
//
//import com.bestinfo.bean.business.DealerInfo;
//import com.bestinfo.bean.business.DealerPrivilege;
//import java.util.List;
//
///**
// * 代销商信息
// *
// * @author chenliping
// */
//public interface IDealerInfoSer {
//
//    /**
//     * 代销商注册
//     *
//     * @param dealer 代销商信息
//     * @param dpList 代销商游戏特权
//     * @return -1代销商入库失败 -2特权入库失败 -3代销商入缓存失败 -4特权入缓存失败 0成功
//     */
//    public int addDealerInfo(DealerInfo dealer, List<DealerPrivilege> dpList);
//    
//    /**
//     * 代销商修改
//     *
//     * @param dealer 代销商信息
//     * @param dpList 代销商游戏特权
//     * @return -1代销商修改数据库或缓存失败 -2特权数据库修改失败 -3特权修改缓存失败 -4未知错误 0成功
//     */
//    public int updateDealerInfo(DealerInfo dealer, List<DealerPrivilege> dpList);
//
//}
