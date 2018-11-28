package cn.edugate.esb.util;


public interface TreeBuilder<T,E> {	
	public T getRootId();
	public T getId(E e);
	public T getParentId(E e);
	public void addChild(E parent,E child);
}
