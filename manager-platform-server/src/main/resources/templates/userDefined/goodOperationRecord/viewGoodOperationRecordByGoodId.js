
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

	var widthPercent = "85%", heightPercent = "95%";

	/**
	 * 获取所有数据
	 */
	var goodOperationRecordListData = getGoodOperationRecordList();
	// console.log(goodOperationRecordTypeListData);
	// 执行一个 table 实例
	var goodOperationRecordTable = table.render({
		elem : '#viewGoodOperationRecordsTable',
		height : 400,
		// 数据接口
		// 表头
		title : '物品列表',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		// 是否显示加载条
		loading : true,
		// 默认排序
		initSort : "id",
		even : true,
		// 最窄宽度
		cellMinWidth : 60,
		size : "lg",
		text : {
			none : '暂无相关数据'// 默认：无数据。
		},
		// 表头
		cols : [ [ {
			field : 'id',
			title : 'ID',
			width : "4%"
		}, {
			field : 'operationType',
			title : '操作',
			templet : "#operationType",
			width : "8%"
		}, {
			field : 'dealPerson',
			title : '经办人',
			width : "8%"
		}, {
			field : 'borrowUser',
			title : '借用人',
			width : "8%"
		}, {
			field : 'giveBackUser',
			title : '归还人',
			width : "8%"
		}, {
			field : 'expectedGiveBackDate',
			title : '预期归还',
			width : "18%"
		}, {
			field : 'realisticGiveBackDate',
			title : '实际归还',
			width : "18%"
		}, {
			field : 'keepPeriod',
			title : '借用时间',
			width : "10%"
		}, {
			field : 'keepOvertime',
			title : '超期',
			templet : "#keepOvertime",
			width : "8%"
		}, {
			field : 'description',
			title : '备注',
		} ] ],
		data : goodOperationRecordListData.data
	});

});
