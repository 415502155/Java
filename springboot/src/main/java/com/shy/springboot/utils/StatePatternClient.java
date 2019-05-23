package com.shy.springboot.utils;

public class StatePatternClient {
	public static void main(String[] args) {
		plus(1,100);
        VoteManager vm = new VoteManager();
        for(int i = 0; i < 9; i++){
            vm.vote("admin","投票AAAAA");
            vm.vote("admin","投票BBBBB");
            vm.vote("admin","投票CCCCC");
            if (i == 5) {
            	vm.vote("vote", "投票AAAAA");
            	vm.vote("vote", "投票BBBBB");
            }
            if (i == 6) {
            	vm.vote("state", "投票AAAAA");
            	vm.vote("state", "投票CCCCC");
            }
            if (i == 7) {
            	vm.vote("dao", "投票AAAAA");
            	vm.vote("dao", "投票CCCCC");
            	vm.vote("dao", "投票DDDDD");
            	vm.vote("dao", "投票EEEEE");
            	vm.vote("dao", "投票FFFFF");
            }
        }
	}
	
	public static void plus(int a, int b) {
		int result = (a + b)*b/2;
		System.out.println(a + "+ ..." + b + " = " + result);
		/***
		 * public static int compare(int x, int y) {
        	return (x < y) ? -1 : ((x == y) ? 0 : 1);
    		}
		 */
		Integer num = 100;
		/***
		 * compareTo函数 a.compareTo(b)
		 * 0 ： 相等
		 * -1 ： a < b
		 * 1 ： a > b
		 */
		int compareTo = num.compareTo(88);
		System.out.println(compareTo);
	} 
}
