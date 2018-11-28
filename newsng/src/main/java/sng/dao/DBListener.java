package sng.dao;

public interface DBListener {
	public void changed(ChangeType changeType,Class<?> enType,Object en);
	
	public enum ChangeType
	{
		Insert,
		Update,
		Delete,
		PreInsert,
		PreUpdate,
		PreDelete
	}
}
