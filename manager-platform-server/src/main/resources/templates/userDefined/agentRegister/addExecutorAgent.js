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

		newerRemoteUrl : function(val) {
			var inputName =  "最新版本库地址";
			return checkInputEmpty(val, inputName) ||checkInputLength(val, null, 500,inputName)
		},
		
		needCompile : function(val) {
			var inputName = "是否编译";
			return checkInputEmpty(val , inputName) 
		},
		
		olderRemoteUrl : function(val) {
			return checkInputLength(val, null, 500, "待比较版本库地址")
		},
		
		tcpServerIp : function(val) {
			var inputName = "tcpServerIp"
			return checkInputEmpty(val, inputName)
		},
		tcpServerPort : function(val) {
			var inputName = "tcpServerPort"
			return checkInputEmpty(val, inputName) || checkInputLength(val, null, 10, inputName)
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

	form.on("submit(addExecutorAgentSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16, time : false, shade : 0.8
		});
		
		var postData = $("#addExecutorAgent").serialize();

		// 实际使用时的提交信息
		$.post("/executorAgent/addExecutorAgent", postData, function(response) {
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