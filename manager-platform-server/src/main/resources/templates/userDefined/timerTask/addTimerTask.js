layui.use([ 'form', 'layer', 'layedit', 'laydate', 'upload' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var laypage = layui.laypage;
	var upload = layui.upload;
	var layedit = layui.layedit;
	var laydate = layui.laydate;
	var $ = layui.jquery;

	form.verify({
		taskName : function(val) {
			var inputName = "任务名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 20, inputName)
		},

		policyId : function(val) {
			return checkInputEmpty(val, "关联策略")
		},

		description : function(val) {
			return checkInputLength(val, null, 100, "描述")
		},
	});

	function initSelect() {
		var policyIdListData = getTimerTaskPolicyList().data;
		/**
		 * 刷新物品类型选择框
		 */
		var $policyIdSelect = $("#policyId");
		for ( var i in policyIdListData) {
			let selected = (i == 0 ? "selected" : "");
			var element = "<option value='" + policyIdListData[i].id + "'"
					+ selected + ">" + policyIdListData[i].cname + "</option>";
			$policyIdSelect.append(element);
		}

		form.render("select");
	}

	initSelect();

	/**
	 * 提交表单
	 */
	form.on("submit(addTimerTaskSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});

		var postData = $("#addTimerTaskForm").serialize();

		// console.log(postData);
		// 实际使用时的提交信息
		$.post("/timerTask/addTimerTask", postData, function(response) {
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
	
	$("#reset").click(function (){
		$("#addTimerTaskForm")[0].reset();
	})
});