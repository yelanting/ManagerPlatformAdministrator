layui.use([ 'form', 'layer', 'layedit', 'laydate', 'upload' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var laypage = layui.laypage;
	var upload = layui.upload;
	var layedit = layui.layedit;
	var laydate = layui.laydate;
	var $ = layui.jquery;

	/**
	 * 表单验证
	 */
	form.verify({
		dealPerson : function(val) {
			var inputName = "经手人"
			return checkInputEmpty(val, inputName)
		}
	});

	var goodId = getGoodId();
	var detail = getDetailById(goodId);

	var borrowType = "borrow";
	var giveBackType = "giveBack";
	/**
	 * 根据详情决定应该激活哪一个按钮
	 */
	function getCurrentActiveOperationType() {
		if (!detail.data.goodStatus || detail.data.goodStatus == "NORMAL") {
			return borrowType;
		}

		return giveBackType;
	}

	/**
	 * 自动判定借还按钮的动作
	 */
	(function() {
		let checkedValue = getCurrentActiveOperationType();
		dealBorrowAndGiveBackInput(checkedValue);
		let operationTypeCheck = "input[name='operationType'][value='"
				+ checkedValue + "']";
		$(operationTypeCheck).next().click();
	})();

	/**
	 * 控制借还输入框
	 */
	function dealBorrowAndGiveBackInput(operationType) {

		let disabledValue;

		if ("giveBack" == operationType) {
			$("#borrowUser").parent().parent().hide();
			$("#expectedGiveBackDate").parent().parent().hide();
			$("#giveBackUser").parent().parent().show();
			disabledValue = "borrow";
		} else {
			$("#borrowUser").parent().parent().show();
			$("#expectedGiveBackDate").parent().parent().show();
			$("#giveBackUser").parent().parent().hide();
			disabledValue = "giveBack";
		}

		let disabledElement = "input[name='operationType'][value='"
				+ disabledValue + "']";
		$(disabledElement).attr("disabled", "disabled");
		form.render();
	}

	form.on('radio(operationType)', function(data) {
		dealBorrowAndGiveBackInput(data.value);
	});

	/**
	 * 提交表单
	 */
	form.on("submit(addGoodOperationRecordSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});

		var postData = $("#addGoodOperationRecordForm").serialize();

		// console.log(postData);
		// 实际使用时的提交信息
		$.post("/goodOperationRecord/addGoodOperationRecord?goodId=" + goodId,
				postData, function(response) {
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
	
	/**
	 * 时间选择器初始化
	 */

	laydate.render({
		elem : "#expectedGiveBackDate",
		format : "yyyy-MM-dd HH:mm:ss",
		value : getFormattedDate(getDateOfCertainDays(3)),
		// 默认最低借一小时
		min : new Date().getTime() + 60 * 60 * 1000,
		// 默认最多借一周
		max : new Date().getTime() + 7 * 24 * 60 * 60 * 1000,
		trigger : 'click', // 采用click弹出
		type : "datetime",
		btns : [ 'clear', 'confirm' ],
		lang : 'cn',
		theme : 'grid'
	})
});