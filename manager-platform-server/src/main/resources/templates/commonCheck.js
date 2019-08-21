/**
 * 校验方法
 */

/**
 * 校验不能为空
 * 
 * @param value
 * @param inputName
 * @returns
 */
function checkInputEmpty(value, inputName) {
	if (value == "") {
		return inputName + "不能为空！"
	}
}

/**
 * 校验最大长度和最小长度
 * 
 * @param value
 *            待校验的内容
 * @param minLength
 *            最小长度
 * @param maxLength
 *            最大长度
 * @param inputName
 *            提示信息的字符串
 * @returns
 */

function checkInputLength(value, minLength, maxLength, inputName) {
	if (minLength != null && minLength != "undefined"
			&& value.length < minLength) {
		return inputName + "长度不能少于" + minLength
	}

	if (maxLength != null && maxLength != "undefined"
			&& value.length > maxLength) {
		return inputName + "长度不能大于" + maxLength
	}
}
