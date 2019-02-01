package sng.dao;

import java.math.BigInteger;
import java.util.List;

import sharding.entity.Notice;

public interface NoticeDao extends ShardingDBBaseDao<Notice> {

	public abstract Notice getNoticeRecord(String org_id, String recordId);
	
	public abstract Notice getNoticeRecord(String org_id, String recordId, String type);

	public abstract BigInteger getTotalNumOfStudentNoticeSendHistory(String org_id, String userId, String beginDate, String endDate, String keyWord);
	
	public abstract List<Notice> getNoticeList4Paging(String org_id, String userId, String beginDate, String endDate,
			String keyWord, int currentPageNum, int pageSize);
	
	/**
	 * 更新某条通知的发送状态
	 * @param org_id
	 * @param recordId
	 * @param status
	 * @return
	 */
	public abstract int updateNoticeStatus(String org_id, String recordId, int status);
	
	/**
	 * 更新某条通知的发送总人数
	 * @param org_id
	 * @param recordId
	 * @param totalNum
	 * @return
	 */
	public abstract int updateNoticetotalNum(String org_id, String recordId, int totalNum);
	
	
	public abstract List<Notice> getReceivingNoticeList(String org_id, String user_id, String recordId, String direction, String length, String type);
}
