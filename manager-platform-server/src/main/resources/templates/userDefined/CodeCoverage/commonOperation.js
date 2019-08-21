/**
 * 获取页面的表格内容
 */
function getCurrentTableData(id) {
	var getCodeCoverageDetailUrl = "/codeCoverage/getDetail/" + id;
	let resultData = "";

	$.ajax({
		url : getCodeCoverageDetailUrl,
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

/**
 * 获取服务器信息数据
 */
function getServerIpList() {
	var getServerInfoListUrl = '/serverInfo/getList';
	var resultData = "";
	$.ajax({
		url : getServerInfoListUrl,
		async : false,
		method : "get",
		dataType : "json",
		success : function(result) {
			resultData = result;
		}
	});
	return resultData.data;
}

/**
 * 重新渲染select
 */
function initServerIpSelect() {
	let serverIpList = getServerIpList();
	let $serverIpSelect = $("#serverId");
	$serverIpSelect.children().remove();
	for (let i = 0; i < serverIpList.length; i++) {
		let selected = (i == 0 ? "selected" : "");
		var element = "<option value='" + serverIpList[i].id + "'"
				+ selected + ">" + serverIpList[i].serverIp + "</option>";
		$serverIpSelect.append(element);
		
		layui.use([ 'form']);
		layui.form.render("select");
	}
}

/**
 * 重新渲染select
 */
function initServerIpSelectById( serverId) {
	let serverIpList = getServerIpList();
	let $serverIpSelect = $("#serverId");
	$serverIpSelect.children().remove();
	
	let selectedIp;
	for (let i = 0; i < serverIpList.length; i++) {
		let selected = (serverIpList[i].id == serverId ? "selected" : "");
		
		if (selected) {
			selectedIp = serverIpList[i].serverIp;
		}
		var element = "<option value='" + serverIpList[i].id + "'"
				+ selected + ">" + serverIpList[i].serverIp + "</option>";
		
		$serverIpSelect.append(element);
		layui.use([ 'form']);
		layui.form.render("select");
	}
	return selectedIp;
}
