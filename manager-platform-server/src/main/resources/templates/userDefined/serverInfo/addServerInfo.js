layui.use([ 'form', 'layer', 'layedit', 'laydate', 'upload' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var laypage = layui.laypage;
	var upload = layui.upload;
	var layedit = layui.layedit;
	var laydate = layui.laydate;
	var $ = layui.jquery;

	form.verify({
		serverName : function(val) {
			var inputName = "服务器名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 20, inputName)
		},

		serverType : function(val) {
			return checkInputEmpty(val, "服务器类型")
		},
		
		username : function(val) {
			var inputName = "登录用户名"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 20, inputName)
		},
		
		password : function(val) {
			var inputName = "登录密码"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 20, inputName)
		},
		
		serverInfoDesc : function(val) {
			return checkInputLength(val, null, 100, "服务器描述")
		},
	});

	/**
	 * 提交表单
	 */
	form.on("submit(addServerInfoSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});

		var postData = $("#addServerInfoForm").serialize();

		// console.log(postData);
		// 实际使用时的提交信息
		$.post("/serverInfo/addServerInfo", postData, function(response) {
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