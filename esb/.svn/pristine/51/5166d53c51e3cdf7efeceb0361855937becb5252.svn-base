package cn.edugate.esb.dao;

import org.hibernate.cfg.DefaultNamingStrategy;

public class LSOANamingStrategy extends DefaultNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8753272522109952663L;

	/**
	 * 设置表名前缀。
	 */
	private String tablePrefix = "";

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	private String addPrefixes(String name, String prefix) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		buffer.append(name);
		return buffer.toString();
	}

	public String classToTableName(String className) {
		return addPrefixes(super.classToTableName(className).toLowerCase(), tablePrefix);
	}

	public String propertyToColumnName(String propertyName) {
		return super.propertyToColumnName(propertyName).toLowerCase();
	}

}
