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
					|| checkInputLength(val, null, 50, inputName);
		},

		newerRemoteUrl : function(val) {
			return checkInputLength(val, null, 500, "最新版本库地址");
		},

		olderRemoteUrl : function(val) {
			return checkInputLength(val, null, 500, "待比较版本库地址");
		},
		username : function(val) {
			let inputName = "登录用户名";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName);
		},

		password : function(val) {
			let inputName = "登录密码";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName);
		},

		description : function(val) {
			return checkInputLength(val, null, 100, "备注");
		},
	});
	
	
	/**
	 * 获取页面的id
	 * 
	 */
	function getParentId() {
		let searchContent = window.location.search;
		let targetId = getValueOfKeyFromWindowLocationSearch("id" , searchContent);
		return targetId;
	}
	
	/**
	 * 监听构建类型
	 */
	form.on('radio(buildType)',function(data){
		if (data.value == "GRADLE") {
			$("#channelName").parent().parent().show();
			$("input[name='jdkVersion'][checked]").parent().parent().hide();
		}else{
			$("#channelName").parent().parent().hide();
			$("input[name='jdkVersion'][checked]").parent().parent().show();
		}
	})
	
	/**
	 * 监听构建类型
	 */
	form.on('radio(newSourceType)', function(data) {
		//(data.value == "VCS" || data.value == "UPLOAD")
		if (data.value == "SFTP") {
			$("#serverId").parent().parent().show();
			$("#sourceCodePath").parent().parent().show();
			$("#needCompile").parent().parent().hide();
			
			/**
			 * 如果是sftp的话，请求服务器信息刷新select
			 */
			let detail = getCurrentTableData(getParentId());
			
			if (!detail.data.serverId) {
				initServerIpSelect();
			}else{
				initServerIpSelectById(detail.data.serverId);
			}
			
		} else {
			$("#serverId").parent().parent().hide();
			$("#sourceCodePath").parent().parent().hide();
			$("#needCompile").parent().parent().show();
		}
	});
	
	/**
	 * 加载之后立即请求
	 */
	(function(){
		let editData = getCurrentTableData(getParentId());
		let edit = editData.data;
		$("#id").val(edit.id);
        $("#projectName").val(edit.projectName);
        let versionControlTypeSelect = $("#versionControlType+div dl");

        $(versionControlTypeSelect).children().each(function() {
            let thisValue = $(this).text();
            $(this).removeClass("layui-this");

            if (thisValue === edit.versionControlType) {
                $(this).addClass("layui-this");
                $(this).click();
            }
        })

        $("#newerRemoteUrl").val(edit.newerRemoteUrl);
        $("#newerVersion").val(edit.newerVersion);
        $("#username").val(edit.username);
        $("#password").val(edit.password);
        $("#createDate").val(edit.createDate);
        $("#updateDate").val(edit.updateDate);
        $("#description").val(edit.description);
        form.render();
	})();
	
	/**
	 * 监听提交请求
	 */
	form.on("submit(editJavaParserRecordSubmit)",
			function(data) {
				// 弹出loading
				var index = top.layer.msg('数据提交中，请稍候', {
					icon : 16,
					time : false,
					shade : 0.8
				});
				
				//先获取当前详情
				var postDataSerialize = $("#editJavaParserRecordForm").serialize();
				postDataSerialize = decodeURIComponent(postDataSerialize);
				let postData = serializeToObject(postDataSerialize);
				// 实际使用时的提交信息
				$.post("/javaParserRecord/updateJavaParserRecord", postData, function(
						response) {
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