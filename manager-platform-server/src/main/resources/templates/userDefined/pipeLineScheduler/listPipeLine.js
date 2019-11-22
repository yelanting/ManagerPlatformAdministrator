/**
 * 
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui.use([ 'laypage', 'layer', 'table', 'element', 'form' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var $ = layui.jquery
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
	function getPipeLineList() {
		var getPipeLineListUrl = '/pipeLine/getList';
		var resultData = "";
		$.ajax({
			url : getPipeLineListUrl,
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
	function getPipeLineListWithCondition(searchContent) {
		if (searchContent == "undefined" || searchContent == "") {
			return getPipeLineList();
		} else {
			var resultData = "", postData = {};
			$.ajax({
				url : "/pipeLine/searchList?searchContent=" + searchContent,
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
	function renderPipeLineTableDefault() {
		var searchContent = "";
		var pipeLineListData = getPipeLineListWithCondition(searchContent);
		table.reload("pipeLineListTable", {
			page : {
				curr : 1
			},
			data : pipeLineListData.data
		})
	}

	// 执行一个 table 实例
	table.render({
		elem : '#pipeLineListTable',
		height : 400,
		// 数据接口
		// url : "/pipeLine/getList/",
		// method:"get",
		// 表头
		title : '任务',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar : true,
		defaultToolbar : [ 'filter', 'exports', 'print' ],
		// 是否显示加载条
		loading : true,
		// 默认排序
		initSort : "pipeLineName",
		even : true,
		// 最窄宽度
		// cellMinWidth: 60,
		size : 'lg',
		skin : 'row',
		page : {
			layout : [ 'prev', 'page', 'next', 'limit', 'count', 'skip' ],
			prev : "上一页",
			next : "下一页",
			first : "首页",
			last : "尾页",
			// 每页条数的选择项
			limits : [ 10, 20, 30, 40, 50, 100 ],
			limit : 100,
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
			field : 'id',
			title : '流水线Id',
			sort : true
		}, {
			field : 'projectName',
			title : '项目名称',
			sort : true,
		// width: 150
		}, {
			field : 'pipelineSetName',
			title : '流水线组名',
		},{
			field : 'name',
			title : '流水线名称',
			sort : true,
		// width: 150
		}, {
			fixed : 'right',
			align : 'center',
			title : '操作',
			toolbar : '#operationBar',
			width : '40%'
		} ] ],
		data : getPipeLineList().data
	});

	// 搜索任务
	// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$("#searchPipeLine").on("click", function() {
		var searchContent = $("#serachPipeLineContent").val();
		var pipeLineListData = getPipeLineListWithCondition(searchContent);
		// 搜索完之后清空搜索数据
		$("#serachPipeLineContent").val("")
		// 表格重载
		table.reload("pipeLineListTable", {
			data : pipeLineListData.data
		});
	});

	// 刷新功能
	$("#refreshPipeLineList").on("click", function() {
		$("#serachPipeLineContent").val("");
		renderPipeLineTableDefault();
	});

	// 监听行工具事件
	table.on('tool(pipeLineListTable)', function(obj) {
		// 注：tool
		// 是工具条事件名，test 是
		// table 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data;
		// 获得当前行数据
		var layEvent = obj.event;

		console.log(obj);
		// 获得 lay-event 对应的值
		if (layEvent === 'configTimerTask') {
			configTimerTask(data);
		}
	});
	
	/**
     * 配置定时任务
     */
    function configTimerTask(data) {
    	var url = "configScheduledTask.html?id=" + data.id;
    	layui.layer.open({
    		title: "配置定时任务",
    		type: 2,
    		area: [widthPercent, heightPercent],
    		content: url,
    		success: function(layero, index) {
    			setTimeout(function() {
    				layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
    					tips: 3
    				});
    			},
    			500)
    		}
    	});
    }
    
	/**
	 * 
	 */
	table.on('rowDouble(pipeLineListTable)', function(obj) {
		layer.msg("不好意思，不支持查看功能", {
			time : 1000
		});
	});
});
