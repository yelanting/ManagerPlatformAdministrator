function getOperationRecordDetailById(id) {
	var getDetailUrl = "/goodOperationRecord/getDetailById?id=" + id;
	var resultData = "";
	$.ajax({
		url : getDetailUrl,
		async : false,
		method : "get",
		dataType : "json",
		success : function(result) {
			resultData = result;
		}
	});
	return resultData;
}

function renderForm() {
	layui.use('form', function() {
		var form = layui.form;
		form.render();
	})
}


/**
 * 获取页面的id
 * 
 */
function getGoodId() {
	let searchContent = window.location.search;
	let targetId = getValueOfKeyFromWindowLocationSearch("goodId" , searchContent);
	return targetId;
}

/**
 * 获取指定物品id下的操作记录
 * 
 * @param goodId
 * @returns
 */
function getGoodOperationRecordList(goodId) {
	
	let targetId = getGoodId();
	
	var getDetailUrl = "/goodOperationRecord/getGoodOperationRecordByGoodId?goodId="
			+ targetId;
	var resultData = "";
	$.ajax({
		url : getDetailUrl,
		async : false,
		method : "get",
		dataType : "json",
		success : function(result) {
			resultData = result;
		}
	});
	return resultData;
}
