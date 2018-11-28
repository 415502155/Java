package cn.edugate.esb.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.edugate.esb.dao.ICardDao;
import cn.edugate.esb.dao.IClassesDao;
import cn.edugate.esb.dao.IGrade2ClassDao;
import cn.edugate.esb.dao.IGradeDao;
import cn.edugate.esb.dao.IGroupMemberDao;
import cn.edugate.esb.dao.IOrganizationDao;
import cn.edugate.esb.dao.ITechRangeDao;
import cn.edugate.esb.dao.UpgradeHistoryDao;
import cn.edugate.esb.entity.Card;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.entity.UpgradeHistory;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;


/**
 * 
 * @author sw
 * 
 */
@Service
@Transactional
public class ClassUpgradeService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IOrganizationDao orgDao;
	
	@Autowired
	private UpgradeHistoryDao upgradeHistoryDao;
	
	@Autowired
	private IClassesDao classDao;
	
	@Autowired  
    private ApplicationContext ctx;
	
	@Autowired
	private IGrade2ClassDao g2cDao;
	
	@Autowired
	private IGradeDao gradeDao;
	
	@Autowired
	private ITechRangeDao teachRangeDao;
	
	@Autowired
	private ICardDao cardDao;
	
	@Autowired 
	private RedisClassStudentDao redisClassStudentDao;
	
	@Autowired 
	private Class2StudentService class2StudentService;
	
	@Autowired
	private IGroupMemberDao groupMemberDao;
	
	
	/**
	 * 获取总记录条数
	 * @param type
	 * @param orgName
	 * @param area
	 * @return
	 */
	public int getTotalRecordNum4ClassUpgrade(int type, String orgName, String area, String upgradeStatus, int year) {
		return orgDao.getTotalRecordNum4ClassUpgrade(type, orgName, area, upgradeStatus, year);
	}
	
	/**
	 * 根据类型，名称，所在区域，升级状态获取机构集合的分页数据
	 * @param type
	 * @param orgName
	 * @param area
	 * @param upgradeStatus
	 * @param year
	 * @param offset
	 * @param rows
	 * @return
	 */
	public List<Organization> getOrgList4Paging(int type, String orgName, String area, String upgradeStatus, int year, int offset, int rows) {
		
		return orgDao.getOrgList4Paging(type, orgName, area, upgradeStatus, year, offset, rows);
	}
	
	/**
	 * 根据类型，名称，所在区域，升级状态获取机构集合数据
	 * @param type
	 * @param orgName
	 * @param area
	 * @param upgradeStatus
	 * @param year
	 * @param offset
	 * @param rows
	 * @return
	 */
	public List<Organization> getOrgList(int type, String orgName, String area, String upgradeStatus, int year) {
		
		return orgDao.getOrgList(type, orgName, area, upgradeStatus, year);
	}
	
	/**
	 * 根据机构id和年份查询是否存在升级记录
	 * @param orgId
	 * @param year
	 * @return
	 */
	public UpgradeHistory getUpgradeHistory(int orgId, int year) {
		return upgradeHistoryDao.getUpgradeHistory(orgId, year);
	}
	
	
	public Map<String, List<String>> batchUpgrade(String[] orgIdArr) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();

		if (orgIdArr != null && orgIdArr.length > 0) {
			List<String> orgUpgradeInfo = null;
			for (String orgId : orgIdArr) {
				Organization org = orgDao.get(Organization.class, Integer.valueOf(orgId));
				if (org != null && org.getOrg_id() != null && org.getOrg_id().intValue() > 0 && org.getType().intValue() == 0
						&& !org.getIs_del()) {
					orgUpgradeInfo = this.classUpgrade(Integer.valueOf(orgId));
					resultMap.put(orgId + "_" + org.getOrg_name_cn(), orgUpgradeInfo);
				} else {
					List<String> errorInfo = new ArrayList<String>();
					errorInfo.add("机构ID=【" + orgId + "】的学校不存在或已被删除！");
					String orgName = "";
					if (org != null && StringUtils.isNotBlank(org.getOrg_name_cn())) {
						orgName = org.getOrg_name_cn();
					}
					resultMap.put(orgId + "_" + orgName, errorInfo);
				}
			}
		}

		return resultMap;
	}
	
	
	public List<String> classUpgrade(int org_id) {
		List<String> messageList = new ArrayList<String>();

		Organization org = orgDao.get(Organization.class, org_id);
		if (org != null && org.getOrg_id() != null && org.getOrg_id().intValue() > 0 && org.getType().intValue() == 0 && !org.getIs_del()) {
			// 查询此机构是否在今年已经升过年级
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			UpgradeHistory upgradeHistory = upgradeHistoryDao.getUpgradeHistory(org_id, year);
			// 没有升级记录的才能执行升级操作
			if (upgradeHistory == null || upgradeHistory.getId() == null || upgradeHistory.getId().intValue() <= 0) {
				// 查询机构下的班级，遍历， 对每个班级进行升级
				List<Classes> classList = classDao.getClassList4ClassUpgrade(org_id);
				if (classList != null && classList.size() > 0) {
					Date currentDate = new Date();
					// 可以进行升级的班级map
					Map<Integer, String> canUpgradeClassMap = new HashMap<Integer, String>();
					int errorNum = 0;

					for (Classes c : classList) {
						String message = null;
						// 查询班级对应的关联记录
						List<Grade2Clas> g2cList = g2cDao.getGrade2ClassListByClassId(c.getClas_id());
						if (g2cList != null && g2cList.size() > 0) {
							if (g2cList.size() == 1) {
								// 正常情况
								// 查询年级记录
								Grade2Clas g2c = g2cList.get(0);
								Grade gradeEntity = gradeDao.get(Grade.class, g2c.getGrade_id().intValue());
								if (gradeEntity != null && gradeEntity.getGrade_id() != null
										&& gradeEntity.getGrade_id().intValue() > 0) {
									if (gradeEntity.getIs_del().intValue() == 0) {
										// 获取升级后的年级num
										int nextGradeNum = this.getGradeNumOfNextYear(gradeEntity.getGrade_type().intValue(),
												gradeEntity.getGrade_number().intValue());
										if (0 == nextGradeNum) {
											message = "班级【" + c.getClas_name() + "】，ID=【" + c.getClas_id().intValue()
													+ "】：未找到适合的升年级后的年级num！";
											errorNum++;
										} else {
											// -1的情况为已经是毕业生年级，不需要再进行升级
											if (nextGradeNum != -1) {
												// 查询是否有此年级num的年级记录， 但年级num为31是大港几个特殊学校才有的年级，需要特殊处理
												if (nextGradeNum != 31) {
													// 正常处理
													Grade nextGrade = gradeDao.getGrade(org_id, gradeEntity.getGrade_type()
															.intValue(), nextGradeNum);
													if (nextGrade != null && nextGrade.getGrade_id() != null
															&& nextGrade.getGrade_id().intValue() > 0) {
														// 升级之后的年级存在，放入canUpgradeClassMap
														canUpgradeClassMap.put(c.getClas_id(),
																g2c.getGra2cls_id() + "_" + gradeEntity.getGrade_id().intValue()
																		+ "_" + nextGrade.getGrade_id().intValue() + "_"
																		+ nextGrade.getGrade_name() + "_" + nextGradeNum);

													} else {
														// 年级不存在，判断升级之后的年级是否为小学6年级，如果是则跳过6年级直接毕业（可能为大港特殊学校，到初中去上6年级），如果不是，则提示年级不存在的错误
														if (nextGradeNum == 26) {
															// 将班级升级到小学应届毕业 GradeNum:28
															nextGradeNum = 28;
															nextGrade = gradeDao.getGrade(org_id, gradeEntity.getGrade_type()
																	.intValue(), nextGradeNum);

															if (nextGrade != null && nextGrade.getGrade_id() != null
																	&& nextGrade.getGrade_id().intValue() > 0) {
																// 升级之后的年级存在，放入canUpgradeClassMap
																canUpgradeClassMap.put(c.getClas_id(),
																		g2c.getGra2cls_id() + "_"
																				+ gradeEntity.getGrade_id().intValue() + "_"
																				+ nextGrade.getGrade_id().intValue() + "_"
																				+ nextGrade.getGrade_name() + "_" + nextGradeNum);
															} else {
																message = "班级【" + c.getClas_name() + "】，ID=【"
																		+ c.getClas_id().intValue() + "】：要升级到的【小学应届毕业】年级不存在！";
																errorNum++;
															}
														} else if (nextGradeNum == 11) {
															// 幼儿园有不存在小小班年级的情况，则升级到12小班年级
															nextGradeNum = 12;
															nextGrade = gradeDao.getGrade(org_id, gradeEntity.getGrade_type().intValue(), nextGradeNum);
															
															if (nextGrade != null && nextGrade.getGrade_id() != null
																	&& nextGrade.getGrade_id().intValue() > 0) {
																// 升级之后的年级存在，放入canUpgradeClassMap
																canUpgradeClassMap.put(c.getClas_id(),
																		g2c.getGra2cls_id() + "_"
																				+ gradeEntity.getGrade_id().intValue() + "_"
																				+ nextGrade.getGrade_id().intValue() + "_"
																				+ nextGrade.getGrade_name() + "_" + nextGradeNum);
															} else {
																message = "班级【" + c.getClas_name() + "】，ID=【"
																		+ c.getClas_id().intValue() + "】：要升级到的【小班】年级不存在！";
																errorNum++;
															}
														} else if (nextGradeNum == 15) {
															// 幼儿园有不存在15学前班年级的情况，则升级到18幼儿园应届毕业年级
															nextGradeNum = 18;
															nextGrade = gradeDao.getGrade(org_id, gradeEntity.getGrade_type().intValue(), nextGradeNum);
															
															if (nextGrade != null && nextGrade.getGrade_id() != null
																	&& nextGrade.getGrade_id().intValue() > 0) {
																// 升级之后的年级存在，放入canUpgradeClassMap
																canUpgradeClassMap.put(c.getClas_id(),
																		g2c.getGra2cls_id() + "_"
																				+ gradeEntity.getGrade_id().intValue() + "_"
																				+ nextGrade.getGrade_id().intValue() + "_"
																				+ nextGrade.getGrade_name() + "_" + nextGradeNum);
															} else {
																message = "班级【" + c.getClas_name() + "】，ID=【"
																		+ c.getClas_id().intValue() + "】：要升级到的【幼儿园应届毕业】年级不存在！";
																errorNum++;
															}
														} else {
															message = "班级【" + c.getClas_name() + "】，ID=【"
																	+ c.getClas_id().intValue() + "】：适合的升年级后的年级num对应的年级记录不存在！";
															errorNum++;
														}
													}
												} else {
													nextGradeNum = 31;
													// 查询此机构是否有初中的六年级记录存在
													Grade nextGrade = gradeDao.getGrade(org_id, gradeEntity.getGrade_type()
															.intValue(), nextGradeNum);
													if (nextGrade != null && nextGrade.getGrade_id() != null
															&& nextGrade.getGrade_id().intValue() > 0) {
														// 机构存在初中六年级，进行正常升级
														// 升级之后的年级存在，放入canUpgradeClassMap
														canUpgradeClassMap.put(c.getClas_id(),
																g2c.getGra2cls_id() + "_" + gradeEntity.getGrade_id().intValue()
																		+ "_" + nextGrade.getGrade_id().intValue() + "_"
																		+ nextGrade.getGrade_name() + "_" + nextGradeNum);
													} else {
														nextGradeNum = 32;
														// 不存在初中六年级，则查询七年级是否存在
														nextGrade = gradeDao.getGrade(org_id, gradeEntity.getGrade_type()
																.intValue(), nextGradeNum);
														if (nextGrade != null && nextGrade.getGrade_id() != null
																&& nextGrade.getGrade_id().intValue() > 0) {
															// 升级之后的年级存在，放入canUpgradeClassMap
															canUpgradeClassMap.put(c.getClas_id(),
																	g2c.getGra2cls_id() + "_"
																			+ gradeEntity.getGrade_id().intValue() + "_"
																			+ nextGrade.getGrade_id().intValue() + "_"
																			+ nextGrade.getGrade_name() + "_" + nextGradeNum);
														} else {
															message = "班级【" + c.getClas_name() + "】，ID=【"
																	+ c.getClas_id().intValue() + "】：升年级后的七年级对应的年级记录不存在！";
															errorNum++;
														}
													}
												}
											}
										}
									} else {
										message = "班级【" + c.getClas_name() + "】，ID=【" + c.getClas_id().intValue() + "】：关联的年级【"
												+ gradeEntity.getGrade_name() + "】，ID=【" + gradeEntity.getGrade_id().intValue()
												+ "】已被删除！";
										errorNum++;
									}
								} else {
									message = "班级【" + c.getClas_name() + "】，ID=【" + c.getClas_id().intValue() + "】：关联的年级ID=【"
											+ g2c.getGrade_id().intValue() + "】不存在对应的年级记录！";
									errorNum++;
								}
							} else {
								// 班级关联了多个年级
								message = "班级【" + c.getClas_name() + "】，ID=【" + c.getClas_id().intValue() + "】：关联了多个年级！";
								errorNum++;
							}
						} else {
							// 班级没有关联年级
							message = "班级【" + c.getClas_name() + "】，ID=【" + c.getClas_id().intValue() + "】：没有关联任何年级！";
							errorNum++;
						}

						if (StringUtils.isNotBlank(message)) {
							messageList.add(message);
						}
					}

					if (errorNum == 0) {
						// 没有错误，可以进行升级
						messageList.clear();

						// 遍历canUpgradeClassMap
						if (canUpgradeClassMap.size() > 0) {

							// 手动控制事务
							HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
							DefaultTransactionDefinition def = new DefaultTransactionDefinition();
							def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
							TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态

							try {
								for (Map.Entry<Integer, String> entry : canUpgradeClassMap.entrySet()) {
									// System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
									Integer classId = entry.getKey();
									String value = entry.getValue();
									String[] valueArr = value.split("_");
									String grade2ClassId = valueArr[0];
									String originalGradeId = valueArr[1];
									String nextGradeId = valueArr[2];
									String nextGradeName = valueArr[3];
									int nextGradeNum = Integer.valueOf(valueArr[4]);

									Grade2Clas originalG2C = g2cDao.get(Grade2Clas.class, Integer.valueOf(grade2ClassId));
									// 更新原年级和班级的关联
									originalG2C.setIs_del(false);
									originalG2C.setGrade_id(Integer.valueOf(nextGradeId));
									originalG2C.setClas_id(classId);
									originalG2C.setInsert_time(currentDate);
									g2cDao.update(originalG2C);


									// 更新班级，以便更新缓存
									Classes c = classDao.get(Classes.class, classId);
									c.setGrade_id(Integer.valueOf(nextGradeId));
									c.setGrade_name(nextGradeName);
									if (nextGradeNum == 18 || nextGradeNum == 19 || nextGradeNum == 28 || nextGradeNum == 29
											|| nextGradeNum == 38 || nextGradeNum == 39 || nextGradeNum == 48
											|| nextGradeNum == 49) {
										// 已毕业或毕业生年级，设置班级已毕业
										c.setIs_graduated(1);
									} else {
										c.setIs_graduated(0);
									}
									c.setUpdate_time(currentDate);
									classDao.update(c);

									if (c.getIs_graduated().intValue() == 0) {
										// 先查询原有的班级年级授课信息
										List<TechRange> originalRangeList = teachRangeDao.getTechRangeList(org_id,
												Integer.valueOf(originalGradeId), classId);
										if (originalRangeList != null && originalRangeList.size() > 0) {
											// 更新班级的授课信息
											for (TechRange tr : originalRangeList) {
												tr.setGrade_id(Integer.valueOf(nextGradeId));
												tr.setGrade_name(nextGradeName);
												tr.setUpdate_time(currentDate);

												teachRangeDao.update(tr);
											}
										}
									} else if (c.getIs_graduated().intValue() == 1) {
										// 查询所有班级和教师的关联
										List<TechRange> rangeList = teachRangeDao.getTechRangeList(org_id, classId);
										if (rangeList != null && rangeList.size() > 0) {
											for (TechRange tr : rangeList) {
												tr.setDel_time(currentDate);
												tr.setIs_del(1);
												teachRangeDao.update(tr);
											}
										}

										// 查询班级中的学生
										List<Card> cardList = cardDao.getCardListByClassId(classId);
										if (cardList != null && cardList.size() > 0) {
											for (Card card : cardList) {
												card.setStud_id(null);
												card.setUpdate_time(currentDate);
												cardDao.update(card);
											}
										}

										// 查询班级里的学生，并删除学生与学生组的关联
										List<GroupMember> groupMemberList = groupMemberDao.getGroupMemberListByClassId(org_id, c
												.getClas_id().intValue());
										if (groupMemberList != null && groupMemberList.size() > 0) {
											for (GroupMember gm : groupMemberList) {
												gm.setIs_del(1);
												gm.setDel_time(currentDate);

												groupMemberDao.update(gm);
											}
										}
									}
								}

								// 全部成功没有错误，向升级历史记录表新增一条记录
								upgradeHistory = new UpgradeHistory();
								upgradeHistory.setOrg_id(org_id);
								upgradeHistory.setYear(year);
								upgradeHistory.setUpgrade_date(currentDate);
								upgradeHistory.setInsert_time(currentDate);

								upgradeHistoryDao.save(upgradeHistory);

								txManager.commit(txStatus);

								messageList.add("机构【" + org.getOrg_name_cn() + "】下所有班级升级成功！");
							} catch (Exception ex) {
								logger.error(ex.toString());
								ex.printStackTrace();
								messageList.add(ex.getMessage());

								txManager.rollback(txStatus);

								for (Classes c4Backup : classList) {
									// 查询班级对应的关联记录
									List<Grade2Clas> g2cList4Backup = g2cDao.getGrade2ClassListByClassId(c4Backup.getClas_id());
									if (g2cList4Backup != null && g2cList4Backup.size() > 0) {
										if (g2cList4Backup.size() == 1) {
											// 正常情况
											// 查询年级记录
											Grade2Clas g2c4Backup = g2cList4Backup.get(0);
											Grade gradeEntity4Backup = gradeDao.get(Grade.class, g2c4Backup.getGrade_id().intValue());
											if (gradeEntity4Backup != null && gradeEntity4Backup.getGrade_id() != null
													&& gradeEntity4Backup.getGrade_id().intValue() > 0) {
												if (gradeEntity4Backup.getIs_del().intValue() == 0) {
													c4Backup.setGrade_id(gradeEntity4Backup.getGrade_id());
													c4Backup.setGrade_name(gradeEntity4Backup.getGrade_name());
													int gradeNum = gradeEntity4Backup.getGrade_number().intValue();
													if (gradeNum == 18 || gradeNum == 19 || gradeNum == 28 || gradeNum == 29
															|| gradeNum == 38 || gradeNum == 39 || gradeNum == 48
															|| gradeNum == 49) {
														// 已毕业或毕业生年级，设置班级已毕业
														c4Backup.setIs_graduated(1);
													} else {
														c4Backup.setIs_graduated(0);
													}
													c4Backup.setUpdate_time(currentDate);
													classDao.update(c4Backup);
												}
											}
										}
									}
								}

								// 可能存在已经升级成功的班级，虽然数据库进行了回滚，但是缓存中的授课信息没有回滚，用备份的授课信息重新更新一遍
								List<TechRange> techRangeList4Backup = teachRangeDao.getTechRangeList(org_id);
								if (techRangeList4Backup != null && techRangeList4Backup.size() > 0) {
									for (TechRange tr : techRangeList4Backup) {
										// 只更新同时有年级和班级的记录就可以
										if (tr.getClas_id() != null && tr.getGrade_id() != null && tr.getClas_id().intValue() > 0
												&& tr.getGrade_id().intValue() > 0) {
											tr.setUpdate_time(currentDate);

											teachRangeDao.update(tr);
										}
									}
								}
							}

							// 全部成功后刷新班级学生关联的缓存，因为这个缓存中也有年级相关的信息
							List<Clas2Student> list = redisClassStudentDao.getByOrgId(String.valueOf(org_id));
							for (Clas2Student cs : list) {
								redisClassStudentDao.delete(cs);
							}
							List<Clas2Student> clas2Students = class2StudentService.getClas2StudentByOrgId(org_id);
							this.redisClassStudentDao.adds(clas2Students);
						}
					}
				} else {
					messageList.add("机构【" + org.getOrg_name_cn() + "】下没有任何班级！");
				}
			} else {
				messageList.add("机构【" + org.getOrg_name_cn() + "】今年已经执行过升年级的操作！");
			}
		} else {
			messageList.add("机构ID=【" + org_id + "】的学校不存在或已被删除！");
		}

		return messageList;
	}
	
	
	
	/**
	 * 获取下一年级的年级num
	 * @param gradeType
	 * @param gradeNum
	 * @return
	 */
	private int getGradeNumOfNextYear(int gradeType, int gradeNum) {
		int nextGradeNum = 0;

		// 根据年级类型进行不同处理
		// 年级类型(1幼儿2小学3初中4高中5特殊类)，特殊类暂不处理

		if (1 == gradeType) {
			if (10 == gradeNum) {
				nextGradeNum = 11;
			} else if (11 == gradeNum) {
				nextGradeNum = 12;
			} else if (12 == gradeNum) {
				nextGradeNum = 13;
			} else if (13 == gradeNum) {
				nextGradeNum = 14;
			} else if (14 == gradeNum) {
				nextGradeNum = 15;
			} else if (15 == gradeNum) {
				nextGradeNum = 18;
			} else if (18 == gradeNum) {
				nextGradeNum = 19;
			} else if (19 == gradeNum) {
				// 已经是历史毕业年级，不用再升年级
				nextGradeNum = -1;
			}
		} else if (2 == gradeType) {
			if (20 == gradeNum) {
				nextGradeNum = 21;
			} else if (21 == gradeNum) {
				nextGradeNum = 22;
			} else if (22 == gradeNum) {
				nextGradeNum = 23;
			} else if (23 == gradeNum) {
				nextGradeNum = 24;
			} else if (24 == gradeNum) {
				nextGradeNum = 25;
			} else if (25 == gradeNum) {
				nextGradeNum = 26;
			} else if (26 == gradeNum) {
				nextGradeNum = 28;
			} else if (28 == gradeNum) {
				nextGradeNum = 29;
			} else if (29 == gradeNum) {
				// 已经是历史毕业年级，不用再升年级
				nextGradeNum = -1;
			}
		} else if (3 == gradeType) {
			if (30 == gradeNum) {
				nextGradeNum = 31;
			} else if (31 == gradeNum) {
				nextGradeNum = 32;
			} else if (32 == gradeNum) {
				nextGradeNum = 33;
			} else if (33 == gradeNum) {
				nextGradeNum = 34;
			} else if (34 == gradeNum) {
				nextGradeNum = 38;
			} else if (38 == gradeNum) {
				nextGradeNum = 39;
			} else if (39 == gradeNum) {
				// 已经是历史毕业年级，不用再升年级
				nextGradeNum = -1;
			}
		} else if (4 == gradeType) {
			if (40 == gradeNum) {
				nextGradeNum = 41;
			} else if (41 == gradeNum) {
				nextGradeNum = 42;
			} else if (42 == gradeNum) {
				nextGradeNum = 43;
			} else if (43 == gradeNum) {
				nextGradeNum = 48;
			} else if (48 == gradeNum) {
				nextGradeNum = 49;
			} else if (49 == gradeNum) {
				// 已经是历史毕业年级，不用再升年级
				nextGradeNum = -1;
			}
		}
		return nextGradeNum;
	}
	
	
	public Map<String, List<String>> validateAndUpgrade(List<String[]> excelData) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>(excelData.size());
		
		for (String[] rowData : excelData) {
			String orgId = rowData[0];
			String orgName = rowData[1];
			
			Organization org = orgDao.get(Organization.class, Integer.valueOf(orgId));
			if (org != null && org.getOrg_id() != null && org.getOrg_id().intValue() > 0 && org.getType().intValue() == 0
					&& !org.getIs_del()) {
				if (orgName.equals(org.getOrg_name_cn())) {
					List<String> orgUpgradeInfo = this.classUpgrade(Integer.valueOf(orgId));
					resultMap.put(orgId + "_" + org.getOrg_name_cn(), orgUpgradeInfo);
				} else {
					List<String> errorInfo = new ArrayList<String>(1);
					errorInfo.add("机构ID=【" + orgId + "】的数据库记录中学校名称与Excel中的学校名称不一致！");
					resultMap.put(orgId + "_" + orgName, errorInfo);
				}
			} else {
				List<String> errorInfo = new ArrayList<String>(1);
				errorInfo.add("机构ID=【" + orgId + "】的学校不存在或已被删除！");
				resultMap.put(orgId + "_" + orgName, errorInfo);
			}
		}
		
		return resultMap;
	}
	
	
	/**
	 * 机构下的班级降级
	 * @param orgId
	 * @param classIdArr
	 * @return
	 */
	public List<String> classesDegrade(int orgId, String[] degradeClassInfoArr) {
		List<String> messageList = new ArrayList<String>();

		Organization org = orgDao.get(Organization.class, orgId);
		if (org != null && org.getOrg_id() != null && org.getOrg_id().intValue() > 0 && org.getType().intValue() == 0 && !org.getIs_del()) {
			Date currentDate = new Date();
			for (String degradeClassInfo : degradeClassInfoArr) {
				String message = null;
				String[] infoArr = degradeClassInfo.split("_");
				String classId = infoArr[0];
				String currentGradeId = infoArr[1];
				String prevGradeId = infoArr[2];
				int gradeNumber = 0;
				String gradeName = "";

				Classes classEntity = classDao.get(Classes.class, Integer.valueOf(classId));
				if (classEntity != null && classEntity.getClas_id() != null && classEntity.getClas_id().intValue() > 0
						&& classEntity.getIs_del() != null && classEntity.getIs_del().intValue() == 0
						&& classEntity.getOrg_id() != null && classEntity.getOrg_id().intValue() == orgId) {

				} else {
					message = "班级【" + classId + "】未查询到数据库记录或已被删除或者班级不属于输入的机构ID！";
				}

				if (StringUtils.isBlank(message)) {
					Grade gradeEntity = gradeDao.get(Grade.class, Integer.valueOf(prevGradeId));
					if (gradeEntity != null && gradeEntity.getGrade_id() != null && gradeEntity.getGrade_id().intValue() > 0
							&& gradeEntity.getIs_del() != null && gradeEntity.getIs_del().intValue() == 0
							&& gradeEntity.getOrg_id() != null && gradeEntity.getOrg_id().intValue() == orgId) {
						gradeNumber = gradeEntity.getGrade_number();
						gradeName = gradeEntity.getGrade_name();
					} else {
						message = "降级的年级【" + prevGradeId + "】未查询到数据库记录或已被删除或者年级不属于输入的机构ID！";
					}
				}

				if (StringUtils.isBlank(message)) {
					Grade2Clas currentG2C = g2cDao.getGrade2Clas(Integer.valueOf(currentGradeId), Integer.valueOf(classId));
					if (currentG2C != null && currentG2C.getGra2cls_id() != null && currentG2C.getGra2cls_id().intValue() > 0) {
						// 更新年级班级关联表
						currentG2C.setGrade_id(Integer.valueOf(prevGradeId));
						currentG2C.setInsert_time(currentDate);
						currentG2C.setIs_del(false);
						g2cDao.update(currentG2C);

						// 更新班级，以便更新缓存
						Classes c = classDao.get(Classes.class, Integer.valueOf(classId).intValue());
						c.setGrade_id(Integer.valueOf(prevGradeId));
						c.setGrade_name(gradeName);
						if (gradeNumber == 18 || gradeNumber == 19 || gradeNumber == 28 || gradeNumber == 29 || gradeNumber == 38
								|| gradeNumber == 39 || gradeNumber == 48 || gradeNumber == 49) {
							// 已毕业或毕业生年级，设置班级已毕业
							c.setIs_graduated(1);
						} else {
							c.setIs_graduated(0);
						}
						c.setUpdate_time(currentDate);
						classDao.update(c);

						if (c.getIs_graduated().intValue() == 0) {
							// 先查询现有的班级年级授课信息
							List<TechRange> originalRangeList = teachRangeDao.getTechRangeList(orgId,
									Integer.valueOf(currentGradeId), Integer.valueOf(classId).intValue());
							if (originalRangeList != null && originalRangeList.size() > 0) {
								// 更新班级的授课信息
								for (TechRange tr : originalRangeList) {
									tr.setGrade_id(Integer.valueOf(prevGradeId));
									tr.setGrade_name(gradeName);
									tr.setUpdate_time(currentDate);

									teachRangeDao.update(tr);
								}
							}
						} else if (c.getIs_graduated().intValue() == 1) {
							// 查询所有班级和教师的关联
							List<TechRange> rangeList = teachRangeDao
									.getTechRangeList(orgId, Integer.valueOf(classId).intValue());
							if (rangeList != null && rangeList.size() > 0) {
								for (TechRange tr : rangeList) {
									tr.setDel_time(currentDate);
									tr.setIs_del(1);
									teachRangeDao.update(tr);
								}
							}

							// 查询班级中的学生
							List<Card> cardList = cardDao.getCardListByClassId(Integer.valueOf(classId).intValue());
							if (cardList != null && cardList.size() > 0) {
								for (Card card : cardList) {
									card.setStud_id(null);
									card.setUpdate_time(currentDate);
									cardDao.update(card);
								}
							}

							// 查询班级里的学生，并删除学生与学生组的关联
							List<GroupMember> groupMemberList = groupMemberDao.getGroupMemberListByClassId(orgId, c.getClas_id()
									.intValue());
							if (groupMemberList != null && groupMemberList.size() > 0) {
								for (GroupMember gm : groupMemberList) {
									gm.setIs_del(1);
									gm.setDel_time(currentDate);

									groupMemberDao.update(gm);
								}
							}
						}

						message = "班级【" + classId + "】降级成功！";
					} else {
						message = "班级【" + classId + "】未在年级班级关联表中查询到记录！";
					}
				}

				if (StringUtils.isNotBlank(message)) {
					messageList.add(message);
				}
			}
		} else {
			messageList.add("机构【" + orgId + "】不存在！");
		}

		return messageList;
	}
}
