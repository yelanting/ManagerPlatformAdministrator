/**
 * 
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui
		.use(
				[ 'laypage', 'layer', 'table', 'element', 'form' ],
				function() {
					var form = layui.form;
					var layer = (parent.layer === undefined ? layui.layer
							: top.layer);
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
					function getTimerTaskMonitorList() {
						var getTimerTaskMonitorListUrl = '/timerTaskMonitor/getList';
						var resultData = "";
						$.ajax({
							url : getTimerTaskMonitorListUrl,
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
					function getTimerTaskMonitorListWithCondition(searchContent) {
						if (searchContent == "undefined" || searchContent == "") {
							return getTimerTaskMonitorList();
						} else {
							var resultData = "", postData = {};
							$
									.ajax({
										url : "/timerTaskMonitor/searchList?searchContent="
												+ searchContent,
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
					function renderTimerTaskMonitorTableDefault() {
						var searchContent = "";
						var timerTaskMonitorListData = getTimerTaskMonitorListWithCondition(searchContent);
						table.reload("timerTaskMonitorListTable", {
							page : {
								curr : 1
							},
							data : timerTaskMonitorListData.data
						})
					}

					// 执行一个 table 实例
					var timerTaskMonitorTable = table.render({
						elem : '#timerTaskMonitorListTable',
						height : 400,
						// 数据接口
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
							layout : [ 'prev', 'page', 'next', 'limit',
									'count', 'skip' ],
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
							field : 'id',
							title : 'ID',
							sort : true,
							width : "10%"
						}, {
							field : 'jobName',
							title : '名称',
							sort : true,
							width : "10%"
						}, {
							field : 'startDate',
							title : '开始',
							width : "10%"
						}, {
							field : 'endDate',
							title : '结束',
							sort : true,
							width : "8%",
						}, {
							field : 'success',
							title : '是否成功',
							width : "10%",
							templet : "#success"
						}, {
							field : 'description',
							title : '描述'
						} ] ],
						data : getTimerTaskMonitorList().data
					});

					// 搜索定时任务信息
					// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
					$("#searchTimerTaskMonitor")
							.on(
									"click",
									function() {
										var searchContent = $(
												"#serachTimerTaskMonitorContent")
												.val();
										var timerTaskMonitorListData = getTimerTaskMonitorListWithCondition(searchContent);
										// 搜索完之后清空搜索数据
										$("#serachTimerTaskMonitorContent")
												.val("")
										// 表格重载
										table
												.reload(
														"timerTaskMonitorListTable",
														{
															data : timerTaskMonitorListData.data
														});
									});

					// 刷新功能
					$("#refreshTimerTaskMonitorList").on("click", function() {
						$("#serachTimerTaskMonitorContent").val("");
						renderTimerTaskMonitorTableDefault();
					});

					table.on('rowDouble(timerTaskMonitorListTable)', function(
							obj) {
						layer.msg("不支持查看操作");
					});
				});
