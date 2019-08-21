layui.use([ 'form', 'layer', 'layedit', 'laydate', 'upload' ], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
			laypage = layui.laypage, upload = layui.upload,
			layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery;

	form.verify({
		goodOperationRecordName : function(val) {
			var inputName = "物品名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},

		goodOperationRecordType : function(val) {
			return checkInputEmpty(val, "物品类型")
		},

		goodOperationRecordDesc : function(val) {
			return checkInputLength(val, null, 100, "物品描述")
		},
	});
	
	/**
	 * 获取页面的id
	 * 
	 */
	function getParentId() {
		let searchContent = window.location.search;

		if (searchContent === "undefined"
				|| searchContent.trim() === "") {
			return "";
		}

		searchContent = searchContent
				.substring(1, searchContent.length)

		let targetId = searchContent.split("=")[1];
		return targetId;
	}
	
	$(function initData() {
		var goodOperationRecordTypeListData = getGoodOperationRecordTypeList().data;
		let id = getParentId();
		
		var thisDetail = getDetailById(id);
		
		/**
		 * 刷新物品类型选择框
		 */
		var $goodOperationRecordTypeSelect = $("#goodOperationRecordTypeId");
		for ( var i in goodOperationRecordTypeListData) {
			var selectedResult = (thisDetail.data.goodOperationRecordTypeId == goodOperationRecordTypeListData[i].id)?'selected':''
			var element = "<option value='" + goodOperationRecordTypeListData[i].id + "' " + selectedResult + ">"
					+ goodOperationRecordTypeListData[i].typeName + "</option>";
			$goodOperationRecordTypeSelect.append(element);
		}
		
		$("#goodOperationRecordName").val(thisDetail.data.goodOperationRecordName);
		$("#goodOperationRecordDesc").val(thisDetail.data.goodOperationRecordDesc);
		form.render("select");
	});

	form.on("submit(editGoodOperationRecordSubmit)", function(data) {
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});

		var postData = $("#editGoodOperationRecordForm").serialize();

		// 实际使用时的提交信息
		$.post("/goodOperationRecord/updateGoodOperationRecord", postData, function(response) {
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