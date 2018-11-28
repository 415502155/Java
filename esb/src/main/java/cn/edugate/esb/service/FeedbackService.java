package cn.edugate.esb.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.entity.Feedback;
import cn.edugate.esb.util.Paging;


@Service
@Transactional
public interface FeedbackService {

	/**
	 * 查询意见反馈带分页
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

	/**
	 * 保存
	 * @param feedback
	 */
	void save(Feedback feedback);
}
