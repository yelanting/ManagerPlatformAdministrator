/**
 *
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui
.use(
	['laypage', 'layer', 'table', 'element', 'form'],
	function () {
	var form = layui.form,
	layer = parent.layer === undefined ? layui.layer
		 : top.layer,
	$ = layui.jquery,
	laydate = layui.laydate,
	laytpl = layui.laytpl,
	table = layui.table,
	laypage = layui.laypage,
	element = layui.element;
	// 元素操作

	var widthPercent = "70%",
	heightPercent = "90%";

	/**
	 * 获取所有数据
	 */
	function getTaskList() {
		var getTaskListUrl = '/task/getList';
		var resultData = "";
		$.ajax({
			url: getTaskListUrl,
			async: false,
			method: "get",
			dataType: "json",
			success: function (result) {
				resultData = result;
			}
		});
		return resultData;
	}

	/**
	 * 条件搜索
	 */
	function getTaskListWithCondition(searchContent) {
		if (searchContent == "undefined" || searchContent == "") {
			return getTaskList();
		} else {
			var resultData = "",
			postData = {};
			$.ajax({
				url: "/task/searchList?searchContent="
				 + searchContent,
				method: "get",
				data: postData,
				async: false,
				dataType: "json",
				success: function (result) {
					resultData = result;
				}
			});
			return resultData;
		}
	}

	/**
	 * 刷新表格
	 */
	function renderTaskTableDefault() {
		var searchContent = "";
		var taskListData = getTaskListWithCondition(searchContent);
		table.reload("taskListTable", {
			page: {
				curr: 1
			},
			data: taskListData.data
		})
	}

	// 执行一个 table 实例
	table.render({
		elem: '#taskListTable',
		height: 400,
		// 数据接口
		// url : "/task/getList/",
		// method:"get",
		// 表头
		title: '任务',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar: true,
		defaultToolbar: ['filter', 'exports', 'print'],
		// 是否显示加载条
		loading: true,
		// 默认排序
		initSort: "taskName",
		even: true,
		// 最窄宽度
		// cellMinWidth: 60,
		size: 'lg',
		skin: 'row',
		page: {
			layout: ['prev', 'page', 'next', 'limit',
				'count', 'skip'],
			prev: "上一页",
			next: "下一页",
			first: "首页",
			last: "尾页",
			// 每页条数的选择项
			limits: [5, 10, 20, 30, 40, 50, 100],
			limit: 5,
			groups: 3,
			skin: '#1E9FFF',
			// 自定义选中色值
			skip: true,
		},
		text: {
			none: '暂无相关数据' // 默认：无数据。
		},
		// 表头
		cols: [[{
					type: 'checkbox',
					fixed: 'left',
					unresize: true
				}, {
					type: 'numbers',
					title: "序号",
					fixed: "left",
					unresize: true
				}, {
					field: 'taskName',
					title: '任务名称',
					sort: true,
					// width: 150
				}, {
					field: 'toExecuteScriptName',
					title: '执行脚本',
					sort: true,
					// width: 220
				}, {
					field: 'comments',
					title: '备注',
					// width: 100
				}, {
					fixed: 'right',
					align: 'center',
					title: '操作',
					toolbar: '#operationBar',
					width: '40%'
				}
			]],
		data: getTaskList().data
	});

	// 监听头工具栏事件,批量删除操作
	$('#deleteInBatch').click(
		function () {
		var checkStatus = table
			.checkStatus('taskListTable');
		var data = checkStatus.data;
		console.log(data);
		var taskId = [];
		if (data.length > 0) {
			for (var eachDataIndex in data) {
				taskId.push(data[eachDataIndex].id);
			}

			layer.confirm('确定删除选中的数据吗？', {
				icon: 3,
				title: '提示信息'
			}, function (index) {
				var url = "/task/deleteTaskInBatch";

				var postData = {
					"ids": taskId
				};

				$.post(url, postData, function (
						responseData) {
					if (responseData.success == "true"
						 || responseData.success) {
						setTimeout(function () {
							top.layer.msg("批量删除成功！");
							// taskTable.reload();
							renderTaskTableDefault()
							layer.close(index);
						}, 500);
					} else {
						layer.msg('批量删除失败：'
							 + responseData.msg);
					}
				});

			})
		} else {
			layer.msg("请选择需要删除的数据");
		}
	});

	// 添加任务
	function addTask() {
		layui.layer
		.open({
			title: "添加任务",
			type: 2,
			area: [widthPercent, heightPercent],
			content: "addTask.html",
			success: function (layero, index) {
				setTimeout(
					function () {
					layui.layer
					.tips(
						'点击此处返回列表',
						'.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		});
	}

	// 编辑任务
	function editTask(edit) {
		console.log(edit)
		layui.layer
		.open({
			title: "修改任务",
			type: 2,
			area: [widthPercent, heightPercent],
			content: "editTask.html",
			success: function (layero, index) {
				var body = layui.layer.getChildFrame(
						'body', index);
				body.find("#id").val(edit.id);
				body.find("#taskName").val(
					edit.taskName);
				body.find("#executorIp").val(
					edit.executorIp)
				body.find("#serverPort").val(
					edit.serverPort);
				body.find("#toExecuteScriptName").val(
					edit.toExecuteScriptName);
				body.find("#executeResultUrl").val(
					edit.executeResultUrl);
				body.find("#comments").val(
					edit.comments);

				form.render();
				setTimeout(
					function () {
					layui.layer
					.tips(
						'点击此处返回列表',
						'.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		});
	}

	// 查看任务
	function viewTask(edit) {
		layui.layer
		.open({
			title: "查看任务",
			type: 2,
			area: [widthPercent, heightPercent],
			content: "viewTask.html",
			success: function (layero, index) {
				var body = layui.layer.getChildFrame(
						'body', index);
				body.find("#id").val(edit.id);
				body.find("#taskName").val(
					edit.taskName);
				body.find("#executorIp").val(
					edit.executorIp)
				body.find("#serverPort").val(
					edit.serverPort);
				body.find("#toExecuteScriptName").val(
					edit.toExecuteScriptName);
				body.find("#executeResultUrl").val(
					edit.executeResultUrl);
				body.find("#comments").val(
					edit.comments);
				form.render();
				setTimeout(
					function () {
					layui.layer
					.tips(
						'点击此处返回列表',
						'.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		});
	}

	/**
	 * 执行任务
	 */
	function executeTask(data) {
		layer.confirm('确定要执行吗？可能会需要很久', function (index) {
			var url = "/task/executeTask";
			$.post(url, data, function (responseData) {
				if (responseData.success == "true"
					 || responseData.success) {
					setTimeout(function () {
						top.layer.msg("操作成功！");
						renderTaskTableDefault()
					}, 500);
				} else {
					layer.msg('操作失败：' + responseData.msg);
				}
			});
			layer.close(index);
		});
	}
	
	/**
	 * 取消执行任务
	 */
	function cancelTask(data) {
		layer.confirm('确定要取消吗？', function (index) {
			var url = "/task/cancelTask";
			$.post(url, data, function (responseData) {
				if (responseData.success == "true"
					 || responseData.success) {
					setTimeout(function () {
						top.layer.msg("操作成功！");
						renderTaskTableDefault()
					}, 500);
				} else {
					layer.msg('操作失败：' + responseData.msg);
				}
			});
			layer.close(index);
		});
	}

	/**
	 * 下载执行结果
	 */
	function downloadResult(data) {
		let url = "/task/downloadExecutionReport";
		$.ajax({
			type: "post",
			url: url,
			data: data,
			async: false,
			success: function (responseData) {
				console.log(responseData);
				let ifResultFalse = (responseData.success === false || responseData.success == "false")
				console.log(ifResultFalse);
				let hasErrorMsg = responseData.msg
				let responseResult = (ifResultFalse && hasErrorMsg)
				console.log(responseResult);
				if (responseResult) {
					layer.msg('操作失败：' + responseData.msg);
					return;
				}
			}
		});
	}
	
	/**
	 * 获取执行详情
	 */
	function getExecutionProcessData(data){
		let url = "/task/getExecutionProcess";
		let resultData = "";
		$.ajax({
			type:"post",
			url:url,
			data:data,
			async:false,
			success:function(responseData){
				resultData = responseData;
			},
			error:function(responseData){
				resultData = responseData;
			}
		});
		
		console.log(resultData);
		return resultData;
	}
	/**
	 * 查看进度
	 */
	function getExecutionProcess(data) {
		const resultData = getExecutionProcessData(data);
		console.log(resultData);
		layui.layer
		.open({
			title: "查看执行进度",
			type: 2,
			area: [widthPercent, heightPercent],
			content: "log.html",
			success: function (layero, index) {
				var body = layui.layer.getChildFrame(
						'body', index);
				body.find("#executeLog").val(resultData.data.executeDetailOutPut);
				form.render();
				setTimeout(
					function () {
					layui.layer
					.tips(
						'点击此处返回列表',
						'.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		});
	}
	
	$("#addTask_btn").click(function () {
		addTask();
	});

	// 搜索任务
	// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$("#searchTask")
	.on(
		"click",
		function () {
		var searchContent = $(
				"#serachTaskContent").val();
		var taskListData = getTaskListWithCondition(searchContent);
		// 搜索完之后清空搜索数据
		$("#serachTaskContent").val("")
		// 表格重载
		table.reload("taskListTable", {
			data: taskListData.data
		});
	});

	// 刷新功能
	$("#refreshTaskList").on("click", function () {
		$("#serachTaskContent").val("");
		renderTaskTableDefault();
	});

	// 监听行工具事件
	table.on('tool(taskListTable)', function (obj) {
		// 注：tool
		// 是工具条事件名，test 是
		// table 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data;
		// 获得当前行数据
		var layEvent = obj.event;

		console.log(obj);
		// 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			viewTask(data);
		} else if (layEvent === 'del') {
			layer.confirm('真的删除行么', function (index) {
				var url = "/task/deleteTask";
				var postData = {
					"id": data.id
				};
				$.post(url, postData, function (responseData) {
					if (responseData.success == "true"
						 || responseData.success) {
						layer.msg('删除成功！');
						obj.del();
						renderTaskTableDefault();
						// 删除对应行（tr）的DOM结构
					} else {
						layer.msg('删除失败：' + responseData.msg);
					}
				});
				layer.close(index);
				// 向服务端发送删除指令
			});
		} else if (layEvent === 'edit') {
			editTask(data);
		} else if (layEvent === 'executeTask') {
			executeTask(data);
		}else if (layEvent === 'cancelTask') {
			cancelTask(data);
		} else if (layEvent === 'downloadResult') {
			downloadResult(data);
		} else if (layEvent === 'getExecutionProcess') {
			getExecutionProcess(data);
		}
	});

	/**
	 *
	 */
	table.on('rowDouble(taskListTable)', function (obj) {
		layer.msg("你想干啥！别瞎点好不好", {
			time: 300
		});
	});
});
