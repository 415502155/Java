package sng.pojo;

public class CampusAndClassRoomPojo {
	
	/**
	 * 校区ID
	 */
	private Integer cam_id;
	/**
	 * 校区名称
	 */
	private String cam_name;
	/**
	 * 教室id
	 */
	private Integer classroom_id;
	/***
	 * 教室名称
	 */
	private String classroom_name;
	
	/**
	 * 校区地址
	 */
	private String cam_address;
	
	/**
	 * 校区类型
	 */
	private Integer cam_type;
	/***
	 * 所在教学楼
	 */
	private String building;
	/***
	 * 所在楼层
	 */
	private String floor;
	
	
	public Integer getCam_id() {
		return cam_id;
	}
	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}
	public String getCam_name() {
		return cam_name;
	}
	public void setCam_name(String cam_name) {
		this.cam_name = cam_name;
	}
	public Integer getClassroom_id() {
		return classroom_id;
	}
	public void setClassroom_id(Integer classroom_id) {
		this.classroom_id = classroom_id;
	}
	public String getClassroom_name() {
		return classroom_name;
	}
	public void setClassroom_name(String classroom_name) {
		this.classroom_name = classroom_name;
	}
	public String getCam_address() {
		return cam_address;
	}
	public void setCam_address(String cam_address) {
		this.cam_address = cam_address;
	}
	public Integer getCam_type() {
		return cam_type;
	}
	public void setCam_type(Integer cam_type) {
		this.cam_type = cam_type;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	@Override
	public String toString() {
		return "CampusAndClassRoomPojo [cam_id=" + cam_id + ", cam_name=" + cam_name + ", classroom_id=" + classroom_id
				+ ", classroom_name=" + classroom_name + ", cam_address=" + cam_address + ", cam_type=" + cam_type
				+ ", building=" + building + ", floor=" + floor + "]";
	}
	
	
	
	

}
