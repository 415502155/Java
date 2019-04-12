package com.shihy.springboot.designmode_builder;

/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月21日 下午2:36:48
 *
 */
public class BuilderClient {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() + 36000;
		//创建构建器对象
		InsuranceContract.ConcreteBuilder builder = new InsuranceContract.ConcreteBuilder("9527", startTime, endTime);
	        //设置需要的数据，然后构建保险合同对象
		InsuranceContract contract = builder.setOtherData("test123").setCompanyName("999公司").build();
	        //操作保险合同对象的方法
		contract.someOperation();
	}

}
