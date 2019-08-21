layui.use([
		'form', 'layer', 'layedit', 'laydate', 'upload'
], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
			laypage = layui.laypage, upload = layui.upload,
			layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery;

	form.verify({
		caseName : function(val) {
			var inputName = "用例名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},
		
		interfaceName : function(val) {
			var inputName = "接口名称"
			return checkInputEmpty(val, inputName) || checkInputLength(val, null, 255, inputName)
		},
		
		methodName : function(val) {
			var inputName = "方法名称"
			return checkInputEmpty(val, inputName) || checkInputLength(val, null, 255, inputName)
		},

		caseDesc : function(val) {
			return checkInputLength(val, null, 100, "备注")
		},
	});

	form.on("submit(addDubboTestSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16, time : false, shade : 0.8
		});

		/*var postData = {
			projectName : $("#projectName").val(),
			// 项目名称
			serverUrl : $("#serverUrl").val(),
			// 服务器地址
			username : $("#username").val(),
			// 用户名
			password : $("#password").val(),
			// 密码
			description : $("#description").val(),
		// 备注
		};*/
		
		var postData = $("#addDubboTestForm").serialize();

		// 实际使用时的提交信息
		$.post("/dubboTest/addDubboTest", postData, function(response) {
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