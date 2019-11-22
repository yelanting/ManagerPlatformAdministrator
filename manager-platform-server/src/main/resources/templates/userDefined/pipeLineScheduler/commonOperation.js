/**
 * 获取页面的表格内容
 */
function getCurrentTableData(id) {
	var getPipeLineDetailUrl = "/pipeLine/getDetail/" + id;
	let resultData = "";

	$.ajax({
		url : getPipeLineDetailUrl,
		async : false,
		type : "get",
		dataType : "json",
		success : function(result) {
			resultData = result;
		}
	});
	proccessObject(resultData);
	console.log(`id为${id}的记录详情为:${JSON.stringify(resultData)}`);

	return resultData;
}
