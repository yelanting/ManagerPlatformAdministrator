/**
 * 把表单serialize生成的方法转换成json对象
 */

function serializeToJson(formDataString) {
    let serializeDataAfterArrayDeal = changeSerializeDataToArray(formDataString);
    let serializeToJson = serializeArrayToJson(serializeDataAfterArrayDeal);
    console.log(`changing form data to json result:${serializeToJson}`)
    return serializeToJson;
}

/**
 * 把表单的serializeData转换成object
 * 
 * @param formDataString
 * @returns
 */
function serializeToObject(formDataString) {
    let serializeDataAfterArrayDeal = changeSerializeDataToArray(formDataString);
    let serializeToObject = serializeArrayToObject(serializeDataAfterArrayDeal);
    console.log(`changing form data to object result: ${serializeToObject} `);
    return serializeToObject;
}

/**
 * 把serializeDataArray转换成Json
 * 
 * @param formDataArray
 * @returns
 */
function serializeArrayToJson(formDataArray) {
    let object = serializeArrayToObject(formDataArray);
    let jsonData = JSON.parse(object);
    console.log(`changing form data array to json result: ${jsonData}`);
    return jsonData;
}

/**
 * 把数组转换成json对象，去重
 * 
 * @param formDataArray
 * @returns
 */
function serializeArrayToObject(formDataArray) {
    var o = {};
    let name = "name";
    let value = "value";

    for (let eachItem of formDataArray) {
        let nameValue = eachItem[name];
        let valueValue = eachItem[value];

        // 如果当前没有这个key
        if (!o[nameValue]) {
            o[nameValue] = valueValue || undefined;
        } else {
            // 如果已经有这个key了，则转换成数组
            if (!o[nameValue].push) {
                o[nameValue] = [o[nameValue]];
            }
            // 转换成数组之后，再追进来
            o[nameValue].push(valueValue || undefined);
        }
    }

//    console.log(`changing data array to object ,after changed : ${JSON.stringify(o)}`);
    return o;
}

/**
 * 把serializeDATA转换成数组
 * 
 * @param serializeData
 * @returns
 */

function changeSerializeDataToArray(serializeData) {
    let dataArrayAfterSplit = serializeData.split("&");

    let toReturnArray = [];
    for (let eachData of dataArrayAfterSplit) {
        let eachDataAfterSplit = eachData.split("=");

        let key = undefined;
        let value = undefined;

        if (eachDataAfterSplit.length <= 0) {
            continue;
        }

        key = eachDataAfterSplit[0];
        if (eachDataAfterSplit.length > 1) {
            value = eachDataAfterSplit[1]
        }

        toReturnArray.push({
            "name": key,
            "value": value
        });
    }
    console.log(`changing serialize data to array result:${JSON.stringify(toReturnArray)}`);
    return toReturnArray;
}
/**
 * 从一串字符串中，提取某些key值 ：类似id=1&name=2这样的
 * 
 * @param key
 * @param searchGroups
 * @param firstSeparateChar:默认是&
 * @param secondSeparateChar:默认是=
 * @returns
 */
function getValueOfKeyFromWindowLocationSearch(key , searchGroups , firstSeparateChar , secondSeparateChar){
	console.log(`在：${searchGroups}中查找${key}`);
	if (!firstSeparateChar) {
		firstSeparateChar = "&";
	}
	
	if (!secondSeparateChar) {
		secondSeparateChar = "=";
	}
	
	if (!key) {
		return undefined;
	}
	
	if (searchGroups === undefined
			|| searchGroups.trim() === "") {
		return undefined;
	}

	if (searchGroups[0] == "?") {
		searchGroups = searchGroups
		.substring(1, searchGroups.length);
	}
	
	/**
	 * 以第一个字符串分割
	 */
	let searchPairArrayAfterSplit = searchGroups.split(firstSeparateChar);
	
	/**
	 * 遍历键值对
	 */
	for(let eachSearchPair of searchPairArrayAfterSplit){
		// 以第二分隔符分割
		let eachSearchPairArray = eachSearchPair.split(secondSeparateChar);
		
		if (eachSearchPairArray.length < 1) {
			continue;
		}
		
		/**
		 * 如果key命中 ，则取值
		 */
		if (eachSearchPairArray[0] == key) {
			// 如果只有key则返回undefined
			if (eachSearchPairArray.length == 1 ) {
				return undefined;
			}else{
				// 否则返回value值
				return eachSearchPairArray[1];
			}
		}
	}
	
	return undefined;
}

// 判断对象是否没有属性，如{}或者[]
function isEmptyObj(o) { for (var attr in o) return !1; return !0 }
function processArray(arr) {
    for (var i = arr.length - 1; i >= 0; i--) {
        if (arr[i] === null || arr[i] === undefined) arr.splice(i, 1);
        else if (typeof arr[i] == 'object') removeNullItem(arr[i], arr, i);
    }
    return arr.length == 0
}
function proccessObject(o) {
    for (var attr in o) {
        if (o[attr] === null || o[attr] === undefined) delete o[attr];
        else if(typeof o[attr]=='object') {
            removeNullItem(o[attr]);
            if (isEmptyObj(o[attr])) delete o[attr];
        }
    }
}
function removeNullItem(o,arr,i) {
    var s = ({}).toString.call(o);
    if (s == '[object Array]') {
        if (processArray(o) === true) {// o也是数组，并且删除完子项，从所属数组中删除
            if (arr) arr.splice(i, 1);
        }
    }
    else if (s == '[object Object]') {
        proccessObject(o);
        if (arr&&isEmptyObj(o)) arr.splice(i, 1);
    }
}