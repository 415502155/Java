package cn.edugate.esb.dao;

import cn.edugate.esb.dao.DBListener.ChangeType;

public interface DBListenerService {

	public abstract void register(DBListener listener, Class<?>... clazzes);

	public abstract void unregister(DBListener listener, Class<?>... clazzes);
	
	public void fireEvent(ChangeType changeType,Class<?> enType,Object en);

}