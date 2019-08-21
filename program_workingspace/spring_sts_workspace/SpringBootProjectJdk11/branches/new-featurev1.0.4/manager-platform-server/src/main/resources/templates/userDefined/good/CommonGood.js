function getDetailById(id) {
	var getDetailUrl = "/good/getDetailById?id=" + id;
	var resultData = "";
	$.ajax({
		url : getDetailUrl, async : false, method : "get", dataType : "json",
		success : function(result) {
			resultData = result;
		}
	});
	return resultData;
}
function renderForm(){
	layui.use('form',function(){
		var form = layui.form;
		form.render();
	})
}




