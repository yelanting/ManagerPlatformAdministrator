layui.use([ 'form', 'layer' ], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
			$ = layui.jquery;

	form.verify({
		userAccount : function(val) {
			var inputName = "登录帐号"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},

		userPassword : function(val) {
			var inputName = "登录密码";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},

		userNickname : function(val) {
			var inputName = "用户昵称";
			return checkInputEmpty(val, inputName)
		},
	});

	form.on("submit(addSysUserSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});
		var postData = $("#addSysUserForm").serialize();

		console.log(postData);

		// 实际使用时的提交信息
		$.post("/sys/sysUser/addSysUser", postData, function(response) {
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
})