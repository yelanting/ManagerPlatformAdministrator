/**
 *
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui.use(['laypage', 'layer', 'table', 'element', 'form'], function () {
	var form = layui.form,
	layer = parent.layer === undefined ? layui.layer : top.layer,
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
	function getExecutorAgentList() {
		var getExecutorAgentListUrl = '/executorAgent/getList';
		var resultData = "";
		$.ajax({
			url: getExecutorAgentListUrl,
			async: false,
			method: "get",
			dataType: "json",
			success: function (result) {
				resultData = result;
				let errorMsg = result.msg;
				if(errorMsg){
					layer.msg("请求出错了," + errorMsg);
				}
			}
		});
		return resultData;
	}

	/**
	 * 条件搜索
	 */
	function getExecutorAgentListWithCondition(searchContent) {
		if (searchContent == "undefined" || searchContent == "") {
			return getExecutorAgentList();
		} else {
			var resultData = "",
			postData = {};
			$.ajax({
				url: "/executorAgent/searchList?searchContent=" + searchContent,
				method: "get",
				data: postData,
				async: false,
				dataType: "json",
				success: function (result) {
					resultData = result;
				},
			});
			return resultData;
		}
	}

	/**
	 * 刷新表格
	 */
	function renderExecutorAgentTableDefault() {
		var searchContent = "";
		var executorAgentListData = getExecutorAgentListWithCondition(searchContent);
		table.reload("executorAgentListTable", {
			page: {
				curr: 1
			},
			data: executorAgentListData.data
		})
	}

	// 执行一个 table 实例
	table.render({
		elem: '#executorAgentListTable',
		height: 400,
		// 数据接口
		//        url : "/executorAgent/getList/",
		//        method:"get",
		// 表头
		title: '执行机',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar:true,
		defaultToolbar: ['filter','exports','print'],
		//是否显示加载条
		loading: true,
		//默认排序
		initSort: "executorName",
		even: true,
		// 最窄宽度
		//cellMinWidth: 60,
		size: 'lg',
		skin: 'row',
		page: {
			layout: ['prev', 'page', 'next', 'limit', 'count', 'skip'],
			prev: "上一页",
			next: "下一页",
			first: "首页",
			last: "尾页",
			//每页条数的选择项
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
					field: 'executorName',
					title: '执行机名称',
					sort: true,
					//	            width: 150
				}, {
					field: 'executorIp',
					title: '执行机IP',
					sort: true,
					//	            width: 150
				}, {
					field: 'offline',
					title: '是否离线',
					templet: '#offline',
					sort: true,
					//	            width: 220
				}, {
					field: 'idle',
					title: '是否空闲',
					templet: '#idle',
					sort: true,
					//	            width: 220
				},{
					fixed: 'right',
					align: 'center',
					title: '操作',
					toolbar: '#operationBar',
					width: '20%'
				}
			]],
		data: getExecutorAgentList().data
	});


	// 查看执行机
	function viewExecutorAgent(edit) {
		layui.layer.open({
			title: "查看执行机",
			type: 2,
			area: [widthPercent, heightPercent],
			content: "viewExecutorAgent.html",
			success: function (layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#executorName").val(edit.executorName);
				body.find("#executorIp").val(edit.executorIp)
				body.find("#offline").val(edit.offline);
				body.find("#idle").val(edit.idle);
				form.render();
				setTimeout(function () {
					layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		});
	}

	// 搜索执行机
	// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$("#searchExecutorAgent").on("click", function () {
		var searchContent = $("#serachExecutorAgentContent").val();
		var executorAgentListData = getExecutorAgentListWithCondition(searchContent);
		//搜索完之后清空搜索数据
		$("#serachExecutorAgentContent").val("")
		//表格重载
		table.reload("executorAgentListTable", {
			data: executorAgentListData.data
		});
	});

	//刷新功能
	$("#refreshExecutorAgentList").on("click", function () {
		$("#serachExecutorAgentContent").val("");
		renderExecutorAgentTableDefault();
	});

	// 监听行工具事件
	table.on('tool(executorAgentListTable)', function (obj) {
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
			viewExecutorAgent(data);
		} 
	});

	/**
	 *
	 */
	table.on('rowDouble(executorAgentListTable)', function (obj) {
		layer.msg("你想干啥！别瞎点好不好", {
			time: 300
		});
	});
});
