package cn.edugate.esb.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.FeedbackDao;
import cn.edugate.esb.entity.Feedback;
import cn.edugate.esb.service.FeedbackService;
import cn.edugate.esb.util.Paging;


@Component
@Transactional("transactionManager")
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackDao feedbackDao;

	@Override
	public Paging<Feedback> getList(Feedback feedback, Paging<Feedback> page) {
		return feedbackDao.getList(feedback,page);
	}

	@Override
	public void save(Feedback feedback) {
		feedbackDao.save(feedback);
	}

	@Override
	public void readed(String ids, Integer status) {
		feedbackDao.readed(ids,status);
	}

	
}
