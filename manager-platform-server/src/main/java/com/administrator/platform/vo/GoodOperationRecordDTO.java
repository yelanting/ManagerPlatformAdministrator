/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月5日 下午1:49:31
 * @see:
 */
package com.administrator.platform.vo;

import com.administrator.platform.model.GoodOperationRecord;

public class GoodOperationRecordDTO extends GoodOperationRecord {

	@Override
	public String toString() {
		return "GoodOperationRecordDTO [getGoodId()=" + getGoodId()
		        + ", getBorrowDate()=" + getBorrowDate()
		        + ", getExpectedGiveBackDate()=" + getExpectedGiveBackDate()
		        + ", getBorrowUser()=" + getBorrowUser()
		        + ", getGiveBackUser()=" + getGiveBackUser()
		        + ", getRealisticGiveBackDate()=" + getRealisticGiveBackDate()
		        + ", getOperationType()=" + getOperationType()
		        + ", getDealPerson()=" + getDealPerson() + ", getKeepPeriod()="
		        + getKeepPeriod() + ", isKeepOvertime()=" + isKeepOvertime()
		        + ", toString()=" + super.toString() + ", getId()=" + getId()
		        + ", getCreateUser()=" + getCreateUser() + ", getUpdateUser()="
		        + getUpdateUser() + ", getSortField()=" + getSortField()
		        + ", getOrder()=" + getOrder() + ", getCreateDate()="
		        + getCreateDate() + ", getUpdateDate()=" + getUpdateDate()
		        + ", getMobile()=" + getMobile() + ", getDescription()="
		        + getDescription() + ", getClass()=" + getClass()
		        + ", hashCode()=" + hashCode() + "]";
	}

}
