layui.use([ 'form', 'layer', 'layedit', 'laydate', 'upload' ], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
			laypage = layui.laypage, upload = layui.upload,
			layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery;

	form.verify({
		cname : function(val) {
			var inputName = "中文名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 20, inputName)
		},
		ename : function(val) {
			var inputName = "英文名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 20, inputName)
		},
		code: function(val) {
			var inputName = "执行代码"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 500, inputName)
		},

		description : function(val) {
			return checkInputLength(val, null, 100, "描述")
		},
	});

	form.on("submit(addTimerTaskPolicySubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});

		/*
		 * var postData = { cname : $("#cname").val(), description :
		 * $("#description").val(), };
		 */

		var postData = $("#addTimerTaskPolicyForm").serialize();

		// 实际使用时的提交信息
		$.post("/timerTaskPolicy/addTimerTaskPolicy", postData, function(
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