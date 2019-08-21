/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月5日 下午12:42:46
 * @see:
 */
package com.administrator.platform.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.administrator.platform.core.base.util.CalendarUtil;
import com.administrator.platform.model.base.BaseDomain;

public class GoodOperationRecord extends BaseDomain {
	private Long goodId;
	/**
	 * 借用日期
	 */
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	private Date borrowDate;

	/**
	 * 预计归还日期
	 */
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	private Date expectedGiveBackDate;

	/**
	 * 借用人
	 */
	private String borrowUser;

	/**
	 * 归还人
	 */
	private String giveBackUser;

	/**
	 * 实际归还日期
	 */
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	private Date realisticGiveBackDate;

	private String dealPerson;

	/**
	 * 操作类型
	 * 
	 * @see :
	 * @param :
	 * @return : Long
	 * @return
	 */
	private String operationType;

	/**
	 * 持有时间
	 */
	private String keepPeriod;

	/**
	 * 是否持有超时
	 */
	private boolean keepOvertime;

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getExpectedGiveBackDate() {
		return expectedGiveBackDate;
	}

	public void setExpectedGiveBackDate(Date expectedGiveBackDate) {
		this.expectedGiveBackDate = expectedGiveBackDate;
	}

	public String getBorrowUser() {
		return borrowUser;
	}

	public void setBorrowUser(String borrowUser) {
		this.borrowUser = borrowUser;
	}

	public String getGiveBackUser() {
		return giveBackUser;
	}

	public void setGiveBackUser(String giveBackUser) {
		this.giveBackUser = giveBackUser;
	}

	public Date getRealisticGiveBackDate() {
		return realisticGiveBackDate;
	}

	public void setRealisticGiveBackDate(Date realisticGiveBackDate) {
		this.realisticGiveBackDate = realisticGiveBackDate;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public String getKeepPeriod() {
		return keepPeriod;
	}

	public void setKeepPeriod(String keepPeriod) {
		this.keepPeriod = keepPeriod;
	}

	public boolean isKeepOvertime() {
		return keepOvertime;
	}

	public void setKeepOvertime(boolean keepOvertime) {
		this.keepOvertime = keepOvertime;
	}

	@Override
	public String toString() {
		return "GoodOperationRecord [goodId=" + goodId + ", borrowDate="
		        + borrowDate + ", expectedGiveBackDate=" + expectedGiveBackDate
		        + ", borrowUser=" + borrowUser + ", giveBackUser="
		        + giveBackUser + ", realisticGiveBackDate="
		        + realisticGiveBackDate + ", dealPerson=" + dealPerson
		        + ", operationType=" + operationType + ", keepPeriod="
		        + keepPeriod + ", keepOvertime=" + keepOvertime + "]";
	}
}
