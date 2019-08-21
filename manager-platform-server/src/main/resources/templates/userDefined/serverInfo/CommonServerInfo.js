function getDetailById(id) {
	var getDetailUrl = "/serverInfo/getDetail/" + id;
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




