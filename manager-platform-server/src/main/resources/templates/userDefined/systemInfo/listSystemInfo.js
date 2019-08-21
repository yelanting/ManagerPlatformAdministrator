layui.config({// version : '1535898708509' // 为了更新 js 缓存，可忽略
});
layui.use([ 'laypage', 'layer', 'table', 'element', 'form' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var $ = layui.jquery;
	var table = layui.table;

	/**
	 * 获取系统信息
	 */
	function getSystemInfoList() {
		var getSystemInfoUrl = "/actuator";
		let resultData = "";

		$.ajax({
			url : getSystemInfoUrl,
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
	 * 把系统信息转换为layui可以解释的类型
	 */
	function changeSystemInfoIntoArray() {
		let returnData = {}
		returnData['success'] = true;
		returnData['code'] = 0;
		returnData['msg'] = '';
		returnData['data'] = [];

		let systemInfoData = getSystemInfoList();

		if (!systemInfoData) {
			return returnData;
		}

		let links = systemInfoData['_links'];

		Object.keys(links).forEach(function(key) {
			let systemInfoItem = {};
			systemInfoItem['systemInfoName'] = key;
			systemInfoItem['href'] = links[key]['href'];
			returnData['data'].push(systemInfoItem);
		});

		return returnData;
	}

	function viewSystemInfo(data) {
		let url = data.href;

		if (!url) {
			layer.msg("这个链接可能不太合法，不能打开");
			return false;
		}

		if (url.indexOf("{") != -1 && url.indexOf("}") != -1) {
			layer.msg("这个链接需要路径参数，不可直接打开");
			return false;

		}

		window.open(decodeURIComponent(data.href));
	}

	// 执行一个 table 实例
	var systemInfoTable = table.render({
		elem : '#systemInfoListTable',
		height : 400,
		title : '物品种类',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar : true,
		defaultToolbar : [ "filter" ],
		// 是否显示加载条
		loading : true,
		// 默认排序
		initSort : "id",
		even : true,
		// 最窄宽度
		cellMinWidth : 80,
		size : "lg",
		page : {
			layout : [ 'prev', 'page', 'next', 'limit', 'count', 'skip' ],
			prev : "上一页",
			next : "下一页",
			first : "首页",
			last : "尾页",
			// 每页条数的选择项
			limits : [ 5, 10, 20, 30, 40, 50, 100 ],
			limit : 10,
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
			field : 'systemInfoName',
			title : '指标名称',
			sort : true,
		}, {
			field : 'href',
			title : '指标链接',
			sort : true,
		}, {
			fixed : 'right',
			align : 'center',
			title : '操作',
			toolbar : '#operationBar',
		} ] ],
		data : changeSystemInfoIntoArray().data
	});

	// 监听行工具事件
	table.on('tool(systemInfoListTable)', function(obj) {
		// 注：tool
		// 是工具条事件名，test 是
		// table 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data;
		// 获得当前行数据
		var layEvent = obj.event;
		// 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			viewSystemInfo(data);
		}
	});

	table.on('rowDouble(systemInfoListTable)', function(obj) {
		layer.msg("你想干啥！别瞎点好不好", {
			time : 300
		});
	});

});
