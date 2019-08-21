/**
 */

function getDateOfCertainDays(days) {
	let nowDate = new Date();
	
	let ms = nowDate.getTime();
	
	let ms_new = ms + days * 24 * 60 * 60 * 1000;
	nowDate.setTime(ms_new);
	
	return nowDate;
}

function getFormattedDate(date, formattedPattern) {
	if (!formattedPattern) {
		formattedPattern = "yyyy-MM-dd hh:mm:ss";
	}

	if (typeof date === "number") {
		var d = new Date();
		d.setTime(date);
		date = d;
	}

	if (!date) {
		date = new Date();
	}
	var arr = formattedPattern.split(/\/|-|:| /); // 分割字符串,- / : 空格
	var timeArr = [];
	for (var i = 0; i < arr.length; i++) { // 按照需要将日期放入数组timeArr
		switch (arr[i]) {
		case "yyyy":
			timeArr.push(date.getFullYear());
			break;
		case "MM":
			timeArr.push(date.getMonth() + 1);
			break;
		case "dd":
			timeArr.push(date.getDate());
			break;
		case "hh":
			timeArr.push(date.getHours());
			break;
		case "mm":
			timeArr.push(date.getMinutes());
			break;
		case "ss":
			timeArr.push(date.getSeconds());
			break;
		}
	}
	
	for (let i = 0; i < arr.length; i++) {
		formattedPattern = formattedPattern.replace(arr[i], timeArr[i]);
	}
	return formattedPattern;
}
