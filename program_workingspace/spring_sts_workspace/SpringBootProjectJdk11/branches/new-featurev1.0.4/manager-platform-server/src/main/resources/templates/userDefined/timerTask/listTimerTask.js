/**
 * 
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui.use([ 'laypage', 'layer', 'table', 'element', 'form' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var laytpl = layui.laytpl;
	var table = layui.table;
	var laypage = layui.laypage;
	var element = layui.element;
	// 元素操作
	var widthPercent = "70%", heightPercent = "90%";

	/**
	 * 获取所有数据
	 */
	function getTimerTaskList() {
		var getTimerTaskListUrl = '/timerTask/getList';
		var resultData = "";
		$.ajax({
			url : getTimerTaskListUrl,
			async : false,
			method : "get",
			dataType : "json",
			success : function(result) {
				resultData = result;
			}
		});
		return resultData;
	}

	/**
	 * 条件搜索
	 */
	function getTimerTaskListWithCondition(searchContent) {
		if (searchContent == "undefined" || searchContent == "") {
			return getTimerTaskList();
		} else {
			var resultData = "", postData = {};
			$.ajax({
				url : "/timerTask/searchList?searchContent=" + searchContent,
				method : "get",
				data : postData,
				async : false,
				dataType : "json",
				success : function(result) {
					resultData = result;
				}
			});
			return resultData;
		}
	}

	/**
	 * 刷新表格
	 */
	function renderTimerTaskTableDefault() {
		var searchContent = "";
		var timerTaskListData = getTimerTaskListWithCondition(searchContent);
		table.reload("timerTaskListTable", {
			page : {
				curr : 1
			},
			data : timerTaskListData.data
		})
	}

	var timerTaskPolicyListData = getTimerTaskPolicyList();
	// console.log(timerTaskPolicyListData);
	// 执行一个 table 实例
	var timerTaskTable = table.render({
		elem : '#timerTaskListTable',
		height : 400,
		// 数据接口
		// url : "/timerTask/getList/",
		// method:"get",
		// 表头
		title : '定时任务列表',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar : true,
		defaultToolbar : [ "filter" ],
		// 是否显示加载条
		loading : true,
		// 默认排序
		initSort : "id",
		even : true,
		// 最窄宽度
		cellMinWidth : 60,
		size : "lg",
		page : {
			layout : [ 'prev', 'page', 'next', 'limit', 'count', 'skip' ],
			prev : "上一页",
			next : "下一页",
			first : "首页",
			last : "尾页",
			// 每页条数的选择项
			limits : [ 5, 10, 20, 30, 40, 50, 100 ],
			limit : 20,
			groups : 3,
			skin : '#1E9FFF',
			// 自定义选中色值
			skip : true,
		},
		text : {
			none : '暂无相关数据' // 默认：无数据。
		},
		// 表头
		cols : [ [ {
			type : 'checkbox',
			fixed : 'left',
			unresize : true
		}, {
			type : 'numbers',
			title : "序号",
			fixed : "left",
			unresize : true
		}, {
			field : 'taskName',
			title : '名称',
			sort : true,
			width : "10%"
		}, {
			field : 'taskGroup',
			title : '任务组',
			sort : true,
			width : "10%"
		}, {
			field : 'policyId',
			title : '策略id',
			sort : true,
			width : "8%",
		}, {
			field : 'config',
			title : '定时配置',
			width : "10%"
		}, {
			field : 'closed',
			title : '当前状态',
			width : "8%",
			templet : "#closed"
		}, {
			field : 'otherParams',
			title : '其他参数',
			width : "8%",
		}, {
			fixed : 'right',
			align : 'center',
			title : '操作',
			toolbar : '#operationBar',
		// width: 300
		} ] ],
		data : getTimerTaskList().data
	});

	// 监听头工具栏事件,批量删除操作
	$('#deleteTimerTasksInBatch').click(
			function() {
				let checkStatus = table.checkStatus('timerTaskListTable');
				let data = checkStatus.data;
				let timerTaskId = [];
				if (data.length > 0) {
					for ( let eachDataIndex in data) {
						timerTaskId.push(data[eachDataIndex].id);
					}

					layer.confirm('确定删除选中的数据吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						const url = "/timerTask/deleteTimerTaskInBatch";

						let postData = {
							"ids" : timerTaskId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("批量删除成功！");
									renderTimerTaskTableDefault();
									layer.close(index);
								}, 500);
							} else {
								layer.msg('批量删除失败：' + responseData.msg);
							}
						});
					});
				} else {
					layer.msg("请选择需要删除的数据!!!");
					return false;
				}
			});

	// 监听头工具栏事件,批量开启操作
	$('#openInBatch').click(
			function() {
				var checkStatus = table.checkStatus('timerTaskListTable');
				var data = checkStatus.data;
				var timerTaskId = [];
				if (data.length > 0) {
					for ( var eachDataIndex in data) {
						timerTaskId.push(data[eachDataIndex].id);
					}

					layer.confirm('确定批量开启定时任务吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						var url = "/timerTask/openTimerTaskInBatch";
						var postData = {
							"ids" : timerTaskId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("批量开启成功！");
									renderTimerTaskTableDefault();
									layer.close(index);
								}, 500);
							} else {
								layer.msg('批量开启失败：' + responseData.msg);
							}
						});

					});
				} else {
					layer.confirm('您没有选择指定定时任务，将一键开启所有定时任务，确定吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						var url = "/timerTask/openAllTimerTaskInBatch";
						var postData = {
							"ids" : timerTaskId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("全部开启成功！");
									renderTimerTaskTableDefault();
									layer.close(index);
								}, 500);
							} else {
								layer.msg('全部开启失败：' + responseData.msg);
							}
						});

					});
				}
			});

	// 监听头工具栏事件,批量开启操作
	$('#closeInBatch').click(
			function() {
				var checkStatus = table.checkStatus('timerTaskListTable');
				var data = checkStatus.data;
				var timerTaskId = [];
				if (data.length > 0) {
					for ( var eachDataIndex in data) {
						timerTaskId.push(data[eachDataIndex].id);
					}

					layer.confirm('确定批量关闭定时任务吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						var url = "/timerTask/closeTimerTaskInBatch";
						var postData = {
							"ids" : timerTaskId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("批量关闭成功！");
									renderTimerTaskTableDefault();
									layer.close(index);
								}, 500);
							} else {
								layer.msg('批量关闭失败：' + responseData.msg);
							}
						});

					});
				} else {
					layer.confirm('您没有选择指定定时任务，将一键关闭所有定时任务，确定吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						var url = "/timerTask/openAllTimerTaskInBatch";
						var postData = {
							"ids" : timerTaskId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("全部关闭成功！");
									renderTimerTaskTableDefault();
									layer.close(index);
								}, 500);
							} else {
								layer.msg('全部关闭失败：' + responseData.msg);
							}
						});

					});
				}
			});

	// 添加定时任务信息
	function addTimerTask() {
		layui.layer.open({
			title : "添加定时任务",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "addTimerTask.html",
			success : function(layero, index) {
				setTimeout(function() {
					layui.layer.tips('点击此处返回列表',
							'.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
				}, 500)
			}
		});
	}

	// 编辑定时任务信息
	function editTimerTask(edit) {
		var id = edit.id;
		layui.layer.open({
			title : "修改定时任务",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "editTimerTask.html?id=" + id,
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				setTimeout(function() {
					layui.layer.tips('点击此处返回列表',
							'.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
				}, 500)
			}
		});

	}

	// 查看定时任务信息
	function viewTimerTask(edit) {
		layui.layer.open({
			title : "查看定时任务",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "viewTimerTask.html",
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#taskName").val(edit.taskName);
				body.find("#description").val(edit.description);
				body.find("#config").val(edit.config);
				body.find("#taskGroup").val(edit.taskGroup);
				body.find("#otherParams").val(edit.otherParams);
				body.find("#closed").val(edit.closed?"否":"是");
				body.find("#policyId").val(
						getTimerTaskPolicyNameById(edit.policyId,
								timerTaskPolicyListData));
				body.find("#createDate").val(edit.createDate);
				body.find("#updateDate").val(edit.updateDate);
				body.find("#createUser").val(edit.createUser);
				body.find("#updateUser").val(edit.updateUser);
				form.render();
				setTimeout(function() {
					layui.layer.tips('点击此处返回列表',
							'.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
				}, 500)
			}
		});
	}

	/**
	 * 执行定时任务
	 */
	function executeTimerTask(data) {
		changeTaskStatus(data, "open");
	}

	/**
	 * 变更状态
	 */
	function changeTaskStatus(data, operation) {
		let url = "/timerTask/changeTimerTaskStatus?id=" + data.id
				+ "&operation=" + operation;
		$.post(url, {}, function(responseData) {
			if (responseData.success == "true" || responseData.success) {
				setTimeout(function() {
					top.layer.msg("操作成功");
				}, 500);
			} else {
				layer.msg('操作失败：' + responseData.msg);
			}
		});
	}

	$("#addTimerTask_btn").click(function() {
		addTimerTask();
	});

	// 搜索定时任务信息
	// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$("#searchTimerTask").on("click", function() {
		var searchContent = $("#serachTimerTaskContent").val();
		var timerTaskListData = getTimerTaskListWithCondition(searchContent);
		// 搜索完之后清空搜索数据
		$("#serachTimerTaskContent").val("")
		// 表格重载
		table.reload("timerTaskListTable", {
			data : timerTaskListData.data
		});
	});

	// 刷新功能
	$("#refreshTimerTaskList").on("click", function() {
		$("#serachTimerTaskContent").val("");
		renderTimerTaskTableDefault();
	});

	// 监听行工具事件
	table.on('tool(timerTaskListTable)', function(obj) {
		// 注：tool
		// 是工具条事件名，test 是
		// table 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data;
		// 获得当前行数据
		var layEvent = obj.event;
		// 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			viewTimerTask(data);
		} else if (layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				var url = "/timerTask/deleteTimerTask";
				var postData = {
					"id" : data.id
				};
				$.post(url, postData,
						function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								layer.msg('删除成功！');
								obj.del();
								renderTimerTaskTableDefault();
								// 删除对应行（tr）的DOM结构
							} else {
								layer.msg('删除失败：' + responseData.msg);
							}
						});
				layer.close(index);
				// 向服务端发送删除指令
			});
		} else if (layEvent === 'edit') {
			editTimerTask(data);
		} else if (layEvent === 'execute') {
			executeTimerTask(data);
		} else if (layEvent === 'open') {
			changeTaskStatus(data, "open");
		} else if (layEvent === 'close') {
			changeTaskStatus(data, "close");
		}
	});

	table.on('rowDouble(timerTaskListTable)', function(obj) {
		viewTimerTask(obj.data);
	});
});
