package sharding.dao;

import java.io.Serializable;

import org.hibernate.criterion.Criterion;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public interface ShardingDBBaseDao<T> {

	@Transactional("transactionManager4Sharding")
	public void deleteById(Class<T> clazz, Serializable... ids);

	@SuppressWarnings("unchecked")
	@Transactional("transactionManager4Sharding")
	public void delete(T... ts);

	@Transactional("transactionManager4Sharding")
	public int delete(Class<T> clazz, Criterion criterion);

	@SuppressWarnings("unchecked")
	@Transactional("transactionManager4Sharding")
	public void saveOrUpdate(T... ts);

	@SuppressWarnings("unchecked")
	@Transactional("transactionManager4Sharding")
	public void save(T... ts);

	@SuppressWarnings("unchecked")
	@Transactional("transactionManager4Sharding")
	public void update(T... ts);

	@Transactional("transactionManager4Sharding")
	public T get(Class<T> clazz, Serializable id);

}
