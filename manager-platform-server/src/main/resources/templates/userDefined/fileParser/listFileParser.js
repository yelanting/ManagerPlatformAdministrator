/**
 * 
 */
layui.config({// version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui.use([ 'laypage', 'layer', 'table', 'element', 'form' ], function() {
	var form = layui.form
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var laytpl = layui.laytpl;
	var table = layui.table;
	var laypage = layui.laypage;
	var lement = layui.element;
	// 元素操作

	var widthPercent = "70%", heightPercent = "90%";

	/**
	 * 获取所有数据
	 */
	function getFileParserList() {
		var getFileParserListUrl = '/fileParser/getList';
		var resultData = "";
		$.ajax({
			url : getFileParserListUrl,
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
	function getFileParserListWithCondition(searchContent) {
		if (searchContent == "undefined" || searchContent == "") {
			return getFileParserList();
		} else {
			var resultData = "", postData = {};
			$.ajax({
				url : "/fileParser/searchList?searchContent=" + searchContent,
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
	function renderFileParserTableDefault() {
		var searchContent = "";
		var fileParserListData = getFileParserListWithCondition(searchContent);
		table.reload("envListTable", {
			page : {
				curr : 1
			},
			data : fileParserListData.data
		})
	}

	// 执行一个 table 实例
	var fileParserTable = table.render({
		elem : '#fileParserListTable',
		height : 400,
		title : '源代码管理',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar : true,
		defaultToolbar : [ "filter" ],
		// 是否显示加载条
		loading : true,
		// 默认排序
		initSort : "projectName",
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
			limit : 5,
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
			field : 'projectName',
			title : '项目名称',
			sort : true,
		// width: 150
		}, {
			field : 'serverUrl',
			title : '访问地址',
			sort : true,
		// width: 220
		}, {
			field : 'description',
			title : '备注',
		// width: 100
		}, {
			fixed : 'right',
			align : 'center',
			title : '操作',
			toolbar : '#operationBar',
		// width: 300
		} ] ],
		data : getFileParserList().data
	});

	// 监听头工具栏事件,批量删除操作
	$('#deleteInBatch').click(
			function() {
				var checkStatus = table.checkStatus('envListTable');
				var data = checkStatus.data;
				console.log(data);
				var fileParserId = [];
				if (data.length > 0) {
					for ( var eachDataIndex in data) {
						fileParserId.push(data[eachDataIndex].id);
					}

					layer.confirm('确定删除选中的数据吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						var url = "/fileParser/deleteFileParserInBatch";

						var postData = {
							"ids" : fileParserId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("批量删除成功！");
									renderFileParserTableDefault()
									layer.close(index);
								}, 500);
							} else {
								layer.msg('批量删除失败：' + responseData.msg);
							}
						});

					})
				} else {
					layer.msg("请选择需要删除的数据");
				}
			});

	// 添加源代码管理
	function addFileParser() {
		layui.layer.open({
			title : "添加源代码管理",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "addFileParser.html",
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

	// 编辑源代码管理
	function editFileParser(edit) {
		layui.layer.open({
			title : "修改源代码管理",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "editFileParser.html",
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#projectName").val(edit.projectName);
				body.find("#serverUrl").val(edit.serverUrl);
				body.find("#username").val(edit.username);
				body.find("#password").val(edit.password);
				body.find("#createDate").val(edit.createDate);
				body.find("#updateDate").val(edit.updateDate);
				body.find("#description").val(edit.description);
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

	// 查看源代码管理
	function viewFileParser(edit) {
		layui.layer.open({
			title : "查看源代码管理",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "viewFileParser.html",
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#projectName").val(edit.projectName);
				body.find("#serverUrl").val(edit.serverUrl);
				body.find("#username").val(edit.username);
				body.find("#password").val(edit.password);
				body.find("#createDate").val(edit.createDate);
				body.find("#updateDate").val(edit.updateDate);
				body.find("#description").val(edit.description);
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
	$("#addFileParser_btn").click(function() {
		addFileParser();
	});

	// 搜索源代码管理
	// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$("#searchFileParser").on("click", function() {
		var searchContent = $("#serachFileParserContent").val();
		var fileParserListData = getFileParserListWithCondition(searchContent);
		// 搜索完之后清空搜索数据
		$("#serachFileParserContent").val("")
		// 表格重载
		table.reload("fileParserListTable", {
			data : fileParserListData.data
		});
	});

	// 刷新功能
	$("#refreshFileParserList").on("click", function() {
		$("#serachFileParserContent").val("");
		renderFileParserTableDefault();
	});

	// 源代码管理跳转
	function goToFileParser(edit) {
		var toUrl = edit.serverUrl;
		if (toUrl == "" || toUrl.trim() == "") {
			layer.msg("服务器地址为空或者不合法，无法实现跳转！！！");
		} else {
			window.open(toUrl);
		}
	}

	// 监听行工具事件
	table.on('tool(fileParserListTable)', function(obj) {
		// 注：tool
		// 是工具条事件名，test 是
		// table 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data;
		// 获得当前行数据
		var layEvent = obj.event;
		// 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			viewFileParser(data);
		} else if (layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				var url = "/fileParser/deleteFileParser";
				var postData = {
					"id" : data.id
				};
				$.post(url, postData,
						function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								layer.msg('删除成功！');
								obj.del();
								renderFileParserTableDefault();
								// 删除对应行（tr）的DOM结构
							} else {
								layer.msg('删除失败：' + responseData.msg);
							}
						});
				layer.close(index);
				// 向服务端发送删除指令
			});
		} else if (layEvent === 'edit') {
			editFileParser(data);
		} else if (layEvent === 'go') {
			goToFileParser(data);
		}
	});

	table.on('rowDouble(fileParserListTable)', function(obj) {
		layer.msg("你想干啥！别瞎点好不好", {
			time : 300
		});
	});
});
