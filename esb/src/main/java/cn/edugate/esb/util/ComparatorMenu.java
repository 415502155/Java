package cn.edugate.esb.util;

import java.util.Comparator;

import cn.edugate.esb.entity.Menu;

public class ComparatorMenu implements Comparator<Menu> {

	@Override
	public int compare(Menu menu1, Menu menu2) {
		int flag=menu1.getMenu_order().compareTo(menu2.getMenu_order());
		return flag;
	}

}
