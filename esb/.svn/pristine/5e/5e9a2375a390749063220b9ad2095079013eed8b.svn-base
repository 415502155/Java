package cn.edugate.esb.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.edugate.esb.dao.DBListener;
import cn.edugate.esb.dao.DBListener.ChangeType;
import cn.edugate.esb.dao.DBListenerService;

@Component
public class DBListenerServiceImpl implements PostInsertEventListener,PostUpdateEventListener,PostDeleteEventListener, DBListenerService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger=LoggerFactory.getLogger(DBListenerServiceImpl.class);
	
	private Map<Class<?>,List<DBListener>> listeners=new HashMap<Class<?>,List<DBListener>>();
	
	@Autowired
	private SessionFactory sessionFactory;


	@PostConstruct
	public void registerListeners() {
	    EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
	            EventListenerRegistry.class);
	    
	    registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(this);
	    registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(this);
	    registry.getEventListenerGroup(EventType.POST_COMMIT_DELETE).appendListener(this);
	    
//	    registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);
//	    registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(this);
//	    registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(this);
	}
	
	public void fireEvent(ChangeType changeType,Class<?> enType,Object en) {
		List<DBListener> list=getListeners(enType);
		for(DBListener listner:list)
		{
			try
			{
				listner.changed(changeType,enType,en);
			}catch(Exception ex)
			{
				logger.error("",ex);
			}
		}
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		// TODO Auto-generated method stub
		fireEvent(ChangeType.Insert,event.getPersister().getMappedClass(),event.getEntity());
		
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		// TODO Auto-generated method stub
		fireEvent(ChangeType.Update,event.getPersister().getMappedClass(),event.getEntity());
		
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		// TODO Auto-generated method stub
		fireEvent(ChangeType.Delete,event.getPersister().getMappedClass(),event.getEntity());
		
	}

	/* (non-Javadoc)
	 * @see cn.edugate.document.rest.cache.HibernateListener#register(cn.edugate.document.rest.cache.DBListener, java.lang.Class)
	 */
	@Override
	public void register(DBListener listener,Class<?>... clazzes)
	{
		if(clazzes!=null)
		{
			for(Class<?> clazz:clazzes)
			{
				List<DBListener> list=getListeners(clazz);
				list.add(listener);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.edugate.document.rest.cache.HibernateListener#unregister(cn.edugate.document.rest.cache.DBListener, java.lang.Class)
	 */
	@Override
	public void unregister(DBListener listener,Class<?>... clazzes)
	{
		if(clazzes!=null)
		{
			for(Class<?> clazz:clazzes)
			{
				List<DBListener> list=getListeners(clazz);
				list.remove(listener);
			}
		}
	}

	private List<DBListener> getListeners(Class<?> clazz) {
		List<DBListener> list=listeners.get(clazz);
		if(list==null)
		{
			list=new ArrayList<DBListener>();
			listeners.put(clazz, list);
		}
		return list;
	}
}
