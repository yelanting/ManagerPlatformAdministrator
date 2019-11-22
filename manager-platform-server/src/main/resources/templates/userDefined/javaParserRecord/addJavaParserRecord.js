layui.use([ 'form', 'layer', 'layedit', 'laydate', 'upload' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var laypage = layui.laypage;
	var upload = layui.upload;
	var layedit = layui.layedit;
	var laydate = layui.laydate;
	var $ = layui.jquery;

	form.verify({
		projectName : function(val) {
			var inputName = "项目名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},
		newerRemoteUrl : function(val) {
			var inputName = "最新版本库地址";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 500, inputName)
		},
		username : function(val) {
			let inputName = "登录用户名";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},

		password : function(val) {
			let inputName = "登录密码";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},
		description : function(val) {
			return checkInputLength(val, null, 100, "备注")
		},
	});

	form.on("submit(addJavaParserRecordSubmit)",
			function(data) {
				// 弹出loading
				var index = top.layer.msg('数据提交中，请稍候', {
					icon : 16,
					time : false,
					shade : 0.8
				});

				var postDataSerialize = $("#addJavaParserRecordForm")
						.serialize();
				postDataSerialize = decodeURIComponent(postDataSerialize);
				let postData = serializeToObject(postDataSerialize);
				// 实际使用时的提交信息
				$.post("/javaParserRecord/addJavaParserRecord", postData,
						function(response) {
							if (response.success == "false"
									|| !response.success) {
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