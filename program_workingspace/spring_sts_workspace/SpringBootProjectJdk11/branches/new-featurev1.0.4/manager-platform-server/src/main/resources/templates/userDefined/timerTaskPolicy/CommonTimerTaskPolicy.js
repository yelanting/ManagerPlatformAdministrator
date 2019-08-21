function getDetailById(id) {
	var getDetailUrl = "/timerTaskPolicy/getTimerTaskPolicyById?id=" + id;
	var resultData = "";
	$.ajax({
		url : getDetailUrl,
		async : false,
		method : "post",
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
