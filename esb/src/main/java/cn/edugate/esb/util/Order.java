package cn.edugate.esb.util;

public class Order {
	private String property;
	private Direction direction;
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public static Order asc(String property)
	{
		Order sort=new Order();
		sort.setProperty(property);
		sort.setDirection(Direction.ASC);
		return sort;
	}
	
	public static Order desc(String property)
	{
		Order sort=new Order();
		sort.setProperty(property);
		sort.setDirection(Direction.DESC);
		return sort;
	}
	
	public org.hibernate.criterion.Order toHOrder()
	{
		if(this.direction.equals(Direction.ASC))
		{
			return org.hibernate.criterion.Order.asc(this.property);
		}else
		{
			return org.hibernate.criterion.Order.desc(this.property);
		}
	}
}
