layui.use([
		'form', 'layer', 'layedit', 'laydate', 'upload'
], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
			laypage = layui.laypage, upload = layui.upload,
			layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery;

	form.verify({
		projectName : function(val) {
			var inputName = "项目名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},

		serverUrl : function(val) {
			return checkInputLength(val, null, 50, "服务器地址URL")
		},

		username : function(val) {
			return checkInputLength(val, null, 50, "登录用户名")
		},

		password : function(val) {
			return checkInputLength(val, null, 50, "登陆密码")
		},

		description : function(val) {
			return checkInputLength(val, null, 100, "备注")
		},
	});

	form.on("submit(editManuSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16, time : false, shade : 0.8
		});

		/*var postData = {
			id : $("#id").val(),
			// 项目名称
			projectName : $("#projectName").val(),
			// 服务器地址
			serverUrl : $("#serverUrl").val(),
			// 用户名
			username : $("#username").val(),
			// 密码
			password : $("#password").val(),
			//创建时间
			createDate : $("#createDate").val(),
			// 备注
			description : $("#description").val(),
		};*/
		
		var postData = $("#editManuForm").serialize();
		
		// 实际使用时的提交信息
		$.post("/menu/updateManu", postData, function(response) {
			if (response.success == "false" || !response.success) {
				if (response.msg != "null") {
					top.layer.msg("修改失败:" + response.msg);
				} else {
					top.layer.msg("修改失败,原因未知！");
				}
			} else {
				setTimeout(function() {
					top.layer.close(index);
					top.layer.msg("修改成功！");
					layer.closeAll("iframe");
					// 刷新父页面
					parent.location.reload();
				}, 500);
			}
		});
		return false;
	})
});