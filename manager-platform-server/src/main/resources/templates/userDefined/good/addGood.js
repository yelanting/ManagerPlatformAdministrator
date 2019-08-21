layui.use([
		'form', 'layer', 'layedit', 'laydate', 'upload'
], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var laypage = layui.laypage;
	var upload = layui.upload;
	var layedit = layui.layedit; 
	var laydate = layui.laydate;
	var $ = layui.jquery;

	form.verify({
		goodName : function(val) {
			var inputName = "物品名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 20, inputName)
		},

		goodType : function(val) {
			console.log(val);
			if(val == "请选择" || val == '-1' || val == -1){
				console.log("未选择");
				return false;
			}
			return checkInputEmpty(val, "物品类型")
		},

		goodDesc : function(val) {
			return checkInputLength(val, null, 100, "物品描述")
		},
	});

	function initSelect() {
		var goodTypeListData = getGoodTypeList().data;
		/**
		 * 刷新物品类型选择框
		 */
		var $goodTypeSelect = $("#goodTypeId");
		for ( var i in goodTypeListData) {
			let selected = (i == 0 ?"selected" : "");
			var element = "<option value='" + goodTypeListData[i].id + "'" + selected +">"
					+ goodTypeListData[i].typeName + "</option>";
			$goodTypeSelect.append(element);
		}

		form.render("select");
	}

	initSelect();

	/**
	 * 提交表单
	 */
	form.on("submit(addGoodSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16, time : false, shade : 0.8
		});

		var postData = $("#addGoodForm").serialize();

//		console.log(postData);
		// 实际使用时的提交信息
		$.post("/good/addGood", postData, function(response) {
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