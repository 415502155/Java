package cn.edugate.esb.dao;

import cn.edugate.esb.entity.Feedback;
import cn.edugate.esb.util.Paging;

/**
 * Title: FeedbackDao
 * Description:意见反馈接口 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月3日下午4:07:05
 */
public interface FeedbackDao  extends BaseDAO<Feedback>{

	/**
	 * 获取带分页的家长意见反馈
	 * @param feedback
	 * @param page
	 * @return
	 */
	Paging<Feedback> getList(Feedback feedback, Paging<Feedback> page);

	/**
	 * 修改已读未读状态
	 * @param ids
	 * @param status
	 */
	void readed(String ids, Integer status);


}
