/**
 * 
 */
layui.config({// version : '1535898708509' // 为了更新 js 缓存，可忽略
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
	function getServerInfoList() {
		var getServerInfoListUrl = '/serverInfo/getList';
		var resultData = "";
		$.ajax({
			url : getServerInfoListUrl,
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
	function getServerInfoListWithCondition(searchContent) {
		if (searchContent == "undefined" || searchContent == "") {
			return getServerInfoList();
		} else {
			var resultData = "", postData = {};
			$.ajax({
				url : "/serverInfo/searchList?searchContent=" + searchContent,
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
	function renderServerInfoTableDefault() {
		var searchContent = "";
		var serverInfoListData = getServerInfoListWithCondition(searchContent);
		table.reload("serverInfoListTable", {
			page : {
				curr : 1
			},
			data : serverInfoListData.data
		})
	}

	// 执行一个 table 实例
	var serverInfoTable = table.render({
		elem : '#serverInfoListTable',
		height : 400,
		// 数据接口
		// url : "/serverInfo/getList/",
		// method:"get",
		// 表头
		title : '服务器列表',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar : true,
		defaultToolbar : [ "filter" ],
		// 是否显示加载条
		loading : true,
		// 默认排序
		initSort : "id",
		even : true,
		// 最窄宽度
		cellMinWidth : 150,
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
			none : '暂无相关数据'// 默认：无数据。
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
			field : 'serverName',
			title : '服务器名称',
			sort : true,
		}, {
			field : 'serverIp',
			title : '服务器IP',
			sort : true
		}, {
			field : 'serverType',
			title : '服务器类型',
			sort : true
		}, {
			fixed : 'right',
			align : 'center',
			title : '操作',
			toolbar : '#operationBar',
		// width: 300
		} ] ],
		data : getServerInfoList().data
	});

	// 监听头工具栏事件,批量删除操作
	$('#deleteInBatch').click(
			function() {
				var checkStatus = table.checkStatus('serverInfoListTable');
				var data = checkStatus.data;
				// console.log(data);
				var serverInfoId = [];
				if (data.length > 0) {
					for ( var eachDataIndex in data) {
						serverInfoId.push(data[eachDataIndex].id);
					}

					layer.confirm('确定删除选中的数据吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						var url = "/serverInfo/deleteServerInfoInBatch";

						var postData = {
							"ids" : serverInfoId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("批量删除成功！");
									renderServerInfoTableDefault();
									layer.close(index);
								}, 500);
							} else {
								layer.msg('批量删除失败：' + responseData.msg);
							}
						});

					})
				} else {
					layer.msg("请选择需要删除的数据");
					return false;
				}
			});

	// 监听头工具栏事件,批量删除操作
	$('#executeShellInBatch').click(function() {
		var checkStatus = table.checkStatus('serverInfoListTable');
		var data = checkStatus.data;
		console.log(data);
		var serverInfoIds = [];
		if (data.length > 0) {
			for ( var eachDataIndex in data) {
				serverInfoIds.push(data[eachDataIndex].id);
			}

			layer.confirm('确定要对这些服务器执行命令吗？', {
				icon : 3,
				title : '提示信息'
			}, function(index) {
				layer.close(index);
				executeShell(undefined, serverInfoIds);
			})
		} else {
			layer.msg("请选择需要操作的数据");
			return false;
		}
	});

	// 批量检测服务器连通性
	$('#checkServerCanBeConnectedInBatch').click(function() {
		var checkStatus = table.checkStatus('serverInfoListTable');
		var data = checkStatus.data;
		console.log(data);
		var serverInfoIds = [];
		if (data.length > 0) {
			for ( var eachDataIndex in data) {
				serverInfoIds.push(data[eachDataIndex].id);
			}

			layer.confirm('可能耗时较久,确定要对这些服务器进行检测吗?', {
				icon : 3,
				title : '提示信息'
			}, function(index) {
				layer.close(index);
				checkServerCanBeConnectedInBatch(serverInfoIds);
			})
		} else {
			layer.msg("请选择需要操作的数据");
			return false;
		}
	});

	// 添加服务器信息
	function addServerInfo() {
		layui.layer.open({
			title : "添加服务器",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "addServerInfo.html",
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

	// 编辑服务器信息
	function editServerInfo(edit) {
		var id = edit.id;
		layui.layer.open({
			title : "修改服务器",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "editServerInfo.html?id=" + id,
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#serverName").val(edit.serverName);
				let serverTypeSelect = body.find("#serverType+div dl");
				$(serverTypeSelect).children().each(function() {
					let thisValue = $(this).text();
					$(this).removeClass("layui-this");

					if (thisValue === edit.serverType) {
						$(this).addClass("layui-this");
						$(this).click();
					}
				})

				body.find("#description").val(edit.description);
				body.find("#username").val(edit.username);
				body.find("#password").val(edit.password);
				body.find("#serverIp").val(edit.serverIp);
				body.find("#createDate").val(edit.createDate);
				body.find("#updateDate").val(edit.updateDate);
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

	// 查看服务器信息
	function viewServerInfo(edit) {
		layui.layer.open({
			title : "查看服务器",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "viewServerInfo.html",
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#serverName").val(edit.serverName);
				body.find("#serverType").val(edit.serverType);
				body.find("#description").val(edit.description);
				body.find("#username").val(edit.username);
				body.find("#password").val(edit.password);
				body.find("#serverIp").val(edit.serverIp);
				body.find("#createDate").val(edit.createDate);
				body.find("#updateDate").val(edit.updateDate);
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
	 * 执行命令
	 */
	function executeShell(edit, ids) {
		if (!ids) {
			ids = edit.id;
		}

		layui.layer.open({
			title : "执行命令",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "executeShell.html?id=" + ids,
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

	/**
	 * 检测是否连通
	 */
	function checkServerCanBeConnected(edit) {
		let postData = edit;
		let url = "/serverInfo/checkServerCanBeConnected"
		// 实际使用时的提交信息
		$.post(url, postData, function(response) {
			console.log(response.data);
			if (response.success == "false" || !response.success) {
				if (response.msg != "null") {
					layer.msg("执行命令失败:" + response.msg);
				} else {
					layer.msg("执行命令失败,原因未知！");
				}
			} else {
				layer.msg(response.data.result);
			}
		});
	}

	/**
	 * 检测是否连通
	 */
	function checkServerCanBeConnectedInBatch(ids) {
		layui.layer.open({
			title : "批量检测",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "checkServerCanBeConnected.html?id=" + ids,
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
	$("#addServerInfo_btn").click(function() {
		addServerInfo();
	});

	// 搜索服务器信息
	$("#searchServerInfo").on("click", function() {
		var searchContent = $("#serachServerInfoContent").val();
		var serverInfoListData = getServerInfoListWithCondition(searchContent);
		// 搜索完之后清空搜索数据
		// $("#serachServerInfoContent").val("")
		// 表格重载
		table.reload("serverInfoListTable", {
			data : serverInfoListData.data
		});
	});

	// 刷新功能
	$("#refreshServerInfoList").on("click", function() {
		$("#serachServerInfoContent").val("");
		renderServerInfoTableDefault();
	});

	// 监听行工具事件
	table.on('tool(serverInfoListTable)', function(obj) {
		// 注：tool
		// 是工具条事件名，test 是
		// table 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data;
		// 获得当前行数据
		var layEvent = obj.event;
		// 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			viewServerInfo(data);
		} else if (layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				var url = "/serverInfo/deleteServerInfo";
				var postData = {
					"id" : data.id
				};
				$.post(url, postData,
						function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								layer.msg('删除成功！');
								obj.del();
								renderServerInfoTableDefault();
								// 删除对应行（tr）的DOM结构
							} else {
								layer.msg('删除失败：' + responseData.msg);
							}
						});
				layer.close(index);
				// 向服务端发送删除指令
			});
		} else if (layEvent === 'edit') {
			editServerInfo(data);
		} else if (layEvent === 'executeShell') {
			executeShell(data);
		} else if (layEvent === 'checkServerCanBeConnected') {
			checkServerCanBeConnected(data);
		}
	});

	table.on('rowDouble(serverInfoListTable)', function(obj) {
		viewServerInfo(obj.data);
	});
});
