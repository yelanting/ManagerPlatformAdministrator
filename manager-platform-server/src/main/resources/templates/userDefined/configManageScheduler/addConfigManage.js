layui.use([
		'form', 'layer', 'layedit', 'laydate', 'upload'
], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
			laypage = layui.laypage, upload = layui.upload,
			layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery;

	form.verify({
		taskName : function(val) {
			var inputName = "任务名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},

		executorIp : function(val) {
			var inputName =  "执行机IP";
			return checkInputEmpty(val, inputName) ||checkInputLength(val, null, 500,inputName)
		},
		
		toExecuteScriptName : function(val) {
			var inputName = "待执行脚本";
			return checkInputEmpty(val , inputName) 
		},

		description : function(val) {
			return checkInputLength(val, null, 100, "备注")
		},
	});

	form.on("submit(addTaskSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16, time : false, shade : 0.8
		});
		
		var postData = $("#addTaskForm").serialize();

		// 实际使用时的提交信息
		$.post("/task/addTask", postData, function(response) {
			if (response.success == "false" || !response.success) {
				if (response.msg != "null") {
					top.layer.msg("添加失败:" + response.msg);
				} else {
					top.layer.msg("添加失败,原因未知！");
				}
			} else {
				setTimeout(function() {
					top.layer.close(index);
					top.layer.msg("添加成功！");
					layer.closeAll("iframe");
					// 刷新父页面
					parent.location.reload();
				}, 500);
			}
		});
		return false;
	})
});