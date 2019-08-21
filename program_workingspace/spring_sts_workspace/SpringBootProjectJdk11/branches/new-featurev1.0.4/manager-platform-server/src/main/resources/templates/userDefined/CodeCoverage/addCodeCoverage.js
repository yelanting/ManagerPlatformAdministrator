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
		needCompile : function(val) {
			var inputName = "是否编译";
			return checkInputEmpty(val, inputName)
		},
		olderRemoteUrl : function(val) {
			return checkInputLength(val, null, 500, "待比较版本库地址")
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

	/**
	 * 监听构建类型
	 */
	form.on('radio(buildType)', function(data) {
		if (data.value == "GRADLE") {
			$("#channelName").parent().parent().show();
			$("input[name='jdkVersion'][checked]").parent().parent().hide();
		} else {
			$("#channelName").parent().parent().hide();
			$("input[name='jdkVersion'][checked]").parent().parent().show();
		}
	});

	/**
	 * 监听构建类型
	 */
	form.on('radio(newSourceType)', function(data) {
		// if (data.value == "VCS" || data.value == "UPLOAD") {
		// $("#serverIp").parent().parent().hide();
		// $("#sourceCodePath").parent().parent().hide();
		// } else if (data.value == "SFTP") {
		// $("#serverIp").parent().parent().show();
		// $("#sourceCodePath").parent().parent().show();
		// /**
		// * 如果是sftp的话，请求服务器信息刷新select
		// */
		// initServerIpSelect();
		// }
		// (data.value == "VCS" || data.value == "UPLOAD")
		if (data.value == "SFTP") {
			$("#serverId").parent().parent().show();
			$("#sourceCodePath").parent().parent().show();
			$("#needCompile").parent().parent().hide();
			/**
			 * 如果是sftp的话，请求服务器信息刷新select
			 */
			initServerIpSelect();
		} else {
			$("#serverId").parent().parent().hide();
			$("#sourceCodePath").parent().parent().hide();
			$("#needCompile").parent().parent().show();
		}
	});

	/**
	 * 立即执行buildType和jdk版本的监听，监听新代码获取方式
	 */
	(function() {
		$("input[name='buildType'][checked]").next().click();
		$("input[name='newSourceType'][checked]").next().click();
	})()

	form.on("submit(addCodeCoverageSubmit)",
			function(data) {
				// 弹出loading
				var index = top.layer.msg('数据提交中，请稍候', {
					icon : 16,
					time : false,
					shade : 0.8
				});

				var postDataSerialize = $("#addCodeCoverageForm").serialize();
				postDataSerialize = decodeURIComponent(postDataSerialize);

				let postData = serializeToObject(postDataSerialize);
				if (postData.tcpServerPort == ""
						|| postData.tcpServerPort == undefined) {
					postData.tcpServerPort = -1;
				}

				// 实际使用时的提交信息
				$.post("/codeCoverage/addCodeCoverage", postData, function(
						response) {
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