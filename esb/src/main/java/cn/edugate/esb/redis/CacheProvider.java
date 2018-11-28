package cn.edugate.esb.redis;

public interface CacheProvider<T> {
	public void add(T en);
	public void update(T en);
	public void delete(T en);
	public void refreshAll();
	public void refreshOrg(String org_id);
}
