package sng.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.CertifiationRecord;

/**
 * @类 名： CertificationRecordService
 * @功能描述：认证缴费记录服务接口 
 * @作者信息： LiuYang
 * @创建时间： 2018年12月11日下午8:36:34
 */
@Service
@Transactional
public interface CertificationRecordService {

	void savee(CertifiationRecord cfr);
}
