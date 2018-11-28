package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Card;

public interface ICardDao extends BaseDAO<Card>{

	List<Card> getCardsByStudentId(Integer stud_id);
	
	/**
	 * 查询班级中所有学生的考勤卡
	 * @param classId
	 * @return
	 */
	public abstract List<Card> getCardListByClassId(int classId);

}
