/**
 * @author : 孙留平
 * @since : 2018年10月15日 上午9:14:59
 * @see:
 */
package com.administrator.platform.model;

import java.util.Date;

import com.administrator.platform.enums.GoodStatus;
import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年10月15日 上午9:14:59
 * @see :// @Entity
 *      // @Table(name = "tb_good")
 */

public class Good extends BaseDomain {

	/**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = 5204811784352306605L;

	private String goodName;
	private String goodDesc;

	/**
	 * @ManyToOne(optional = false)
	 * 
	 * @JoinTable(name = "tb_good_type")
	 * 
	 * @JoinColumn(name = "id")
	 */
	private Long goodTypeId;

	/**
	 * 2019年8月5日 12:36:57 新增字段开始
	 */
	/**
	 * 型号或者编号
	 */
	private String goodCode;

	/**
	 * 借用次数
	 */
	private int borrowedTimes;

	/**
	 * 归还次数
	 */
	private int giveBackTimes;

	/**
	 * 当前状态
	 */
	private GoodStatus goodStatus;

	/**
	 * 当前持有人
	 */

	private String currentOwner;

	/**
	 * 2019年8月5日 12:37:06 新增字段结束
	 */

	/**
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @Override
	 */
	@Override
	public Long getId() {
		return super.getId();
	}

	/**
	 * @see 想要以jpa的方式自动初始化数据库表，则需要显式集成方法
	 */
	@Override
	public Date getCreateDate() {
		return super.getCreateDate();
	}

	/**
	 * @see 想要以jpa的方式自动初始化数据库表，则需要显式集成方法
	 */
	@Override
	public Date getUpdateDate() {
		return super.getUpdateDate();
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodDesc() {
		return goodDesc;
	}

	public void setGoodDesc(String goodDesc) {
		this.goodDesc = goodDesc;
	}

	public Long getGoodTypeId() {
		return goodTypeId;
	}

	public void setGoodTypeId(Long goodTypeId) {
		this.goodTypeId = goodTypeId;
	}

	public String getGoodCode() {
		return goodCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public int getBorrowedTimes() {
		return borrowedTimes;
	}

	public void setBorrowedTimes(int borrowedTimes) {
		this.borrowedTimes = borrowedTimes;
	}

	public int getGiveBackTimes() {
		return giveBackTimes;
	}

	public void setGiveBackTimes(int giveBackTimes) {
		this.giveBackTimes = giveBackTimes;
	}

	public GoodStatus getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(GoodStatus goodStatus) {
		this.goodStatus = goodStatus;
	}

	public String getCurrentOwner() {
		return currentOwner;
	}

	public void setCurrentOwner(String currentOwner) {
		this.currentOwner = currentOwner;
	}

	@Override
	public String toString() {
		return "Good [goodName=" + goodName + ", goodDesc=" + goodDesc
		        + ", goodTypeId=" + goodTypeId + ", goodCode=" + goodCode
		        + ", borrowedTimes=" + borrowedTimes + ", giveBackTimes="
		        + giveBackTimes + ", goodStatus=" + goodStatus
		        + ", currentOwner=" + currentOwner + "]";
	}

}
