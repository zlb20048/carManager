/**
 * 
 */
package com.cars.simple.mode;

/**
 * @author liuzixiang
 * 
 */
public class AccountInfo {

	/**
	 * id
	 */
	public String accountid = "";

	/**
	 * 油品 1,#93 2,#97 3,柴油
	 */
	public String oil = "";

	/**
	 * 开始里程
	 */
	public String startmileage = "";

	/**
	 * 结束里程
	 */
	public String endmileage = "";

	/**
	 * 总里程
	 */
	public String allmileage = "";

	/**
	 * 人工费
	 */
	public String laborcosts = "";

	/**
	 * 材料费
	 */
	public String fittingcosts = "";

	/**
	 * 油单价
	 */
	public String unitprice = "";

	/**
	 * 总升数
	 */
	public String numbers = "";

	/**
	 * 总价
	 */
	public String allprice = "";

	/**
	 * 付款类型 1,现金 2,刷卡
	 */
	public String pricetype = "";

	/**
	 * 费用产生日期
	 */
	public String pricetime = "";

	/**
	 * 地址
	 */
	public String address = "";

	/**
	 * 备注
	 */
	public String remark = "";

	/**
	 * 用户车辆编号
	 */
	public String usercarid = "";

	/**
	 * 记账类型 1，加油 2，美容 3，洗车 4，维修 5，保养 6，保险 7，停车 8，通行 9，罚款 10，购置 11，规费 12，其他
	 */
	public String accounttype = "";

	/**
	 * 保养/保险 项目 选中的编号，号隔开 | 号隔开价格
	 */
	public String accountbase = "";

}
