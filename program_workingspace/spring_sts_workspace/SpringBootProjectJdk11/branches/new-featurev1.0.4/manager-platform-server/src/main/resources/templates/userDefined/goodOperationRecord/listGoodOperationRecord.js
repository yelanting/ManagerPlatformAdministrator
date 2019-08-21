/**
 * 
 */
layui.config({// version : '1535898708509' // 为了更新 js 缓存，可忽略
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
					function getGoodOperationRecordList() {
						var getGoodOperationRecordListUrl = '/goodOperationRecord/getList';
						var resultData = "";
						$.ajax({
							url : getGoodOperationRecordListUrl,
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
					function getGoodOperationRecordListWithCondition(
							searchContent) {
						if (searchContent == "undefined" || searchContent == "") {
							return getGoodOperationRecordList();
						} else {
							var resultData = "", postData = {};
							$
									.ajax({
										url : "/goodOperationRecord/searchList?searchContent="
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
					function renderGoodOperationRecordTableDefault() {
						var searchContent = "";
						var goodOperationRecordListData = getGoodOperationRecordListWithCondition(searchContent);
						table.reload("goodOperationRecordListTable", {
							page : {
								curr : 1
							},
							data : goodOperationRecordListData.data
						})
					}

					var goodOperationRecordTypeListData = getGoodOperationRecordTypeList();
					// console.log(goodOperationRecordTypeListData);
					// 执行一个 table 实例
					var goodOperationRecordTable = table
							.render({
								elem : '#goodOperationRecordListTable',
								height : 400,
								// 数据接口
								// url : "/goodOperationRecord/getList/",
								// method:"get",
								// 表头
								title : '物品列表',
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
									layout : [ 'prev', 'page', 'next', 'limit',
											'count', 'skip' ],
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
								cols : [ [
										{
											type : 'checkbox',
											fixed : 'left',
											unresize : true
										},
										{
											type : 'numbers',
											title : "序号",
											fixed : "left",
											unresize : true
										},
										{
											field : 'goodOperationRecordName',
											title : '物品名称',
											sort : true,
										},
										{
											field : 'goodOperationRecordTypeId',
											title : '物品类型',
											sort : true,
											templet : function(data) {
												// console.log(data);
												return getGoodOperationRecordTypeNameById(
														data.goodOperationRecordTypeId,
														goodOperationRecordTypeListData);
											}
										}, {
											field : 'goodOperationRecordDesc',
											title : '物品描述',
										// width: 100
										}, {
											fixed : 'right',
											align : 'center',
											title : '操作',
											toolbar : '#operationBar',
										// width: 300
										} ] ],
								data : getGoodOperationRecordList().data
							});

					// 监听头工具栏事件,批量删除操作
					$('#deleteInBatch')
							.click(
									function() {
										var checkStatus = table
												.checkStatus('goodOperationRecordListTable');
										var data = checkStatus.data;
										// console.log(data);
										var goodOperationRecordId = [];
										if (data.length > 0) {
											for ( var eachDataIndex in data) {
												goodOperationRecordId
														.push(data[eachDataIndex].id);
											}

											layer
													.confirm(
															'确定删除选中的数据吗？',
															{
																icon : 3,
																title : '提示信息'
															},
															function(index) {
																var url = "/goodOperationRecord/deleteGoodOperationRecordInBatch";

																var postData = {
																	"ids" : goodOperationRecordId
																};

																$
																		.post(
																				url,
																				postData,
																				function(
																						responseData) {
																					if (responseData.success == "true"
																							|| responseData.success) {
																						setTimeout(
																								function() {
																									top.layer
																											.msg("批量删除成功！");
																									renderGoodOperationRecordTableDefault();
																									layer
																											.close(index);
																								},
																								500);
																					} else {
																						layer
																								.msg('批量删除失败：'
																										+ responseData.msg);
																					}
																				});

															})
										} else {
											layer.msg("请选择需要删除的数据");
										}
									});

					// 添加物品信息
					function addGoodOperationRecord() {
						layui.layer
								.open({
									title : "添加物品",
									type : 2,
									area : [ widthPercent, heightPercent ],
									content : "addGoodOperationRecord.html",
									success : function(layero, index) {
										setTimeout(
												function() {
													layui.layer
															.tips(
																	'点击此处返回列表',
																	'.layui-layer-setwin .layui-layer-close',
																	{
																		tips : 3
																	});
												}, 500)
									}
								});
					}

					// 编辑物品信息
					function editGoodOperationRecord(edit) {
						var id = edit.id;
						layui.layer
								.open({
									title : "修改物品",
									type : 2,
									area : [ widthPercent, heightPercent ],
									content : "editGoodOperationRecord.html?id="
											+ id,
									success : function(layero, index) {
										var body = layui.layer.getChildFrame(
												'body', index);
										body.find("#id").val(edit.id);
										setTimeout(
												function() {
													layui.layer
															.tips(
																	'点击此处返回列表',
																	'.layui-layer-setwin .layui-layer-close',
																	{
																		tips : 3
																	});
												}, 500)
									}
								});

					}

					// 查看物品信息
					function viewGoodOperationRecord(edit) {
						layui.layer
								.open({
									title : "查看物品",
									type : 2,
									area : [ widthPercent, heightPercent ],
									content : "viewGoodOperationRecord.html",
									success : function(layero, index) {
										var body = layui.layer.getChildFrame(
												'body', index);
										body.find("#id").val(edit.id);
										body
												.find(
														"#goodOperationRecordName")
												.val(
														edit.goodOperationRecordName);
										body
												.find(
														"#goodOperationRecordDesc")
												.val(
														edit.goodOperationRecordListTable);

										body
												.find(
														"#goodOperationRecordTypeId")
												.val(
														getGoodOperationRecordTypeNameById(
																edit.goodOperationRecordTypeId,
																goodOperationRecordTypeListData));
										body.find("#createDate").val(
												edit.createDate);
										body.find("#updateDate").val(
												edit.updateDate);
										form.render();
										setTimeout(
												function() {
													layui.layer
															.tips(
																	'点击此处返回列表',
																	'.layui-layer-setwin .layui-layer-close',
																	{
																		tips : 3
																	});
												}, 500)
									}
								});
					}
					$("#addGoodOperationRecord_btn").click(function() {
						addGoodOperationRecord();
					});

					// 搜索物品信息
					// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
					$("#searchGoodOperationRecord")
							.on(
									"click",
									function() {
										var searchContent = $(
												"#serachGoodOperationRecordContent")
												.val();
										var goodOperationRecordListData = getGoodOperationRecordListWithCondition(searchContent);
										// 搜索完之后清空搜索数据
										$("#serachGoodOperationRecordContent")
												.val("")
										// 表格重载
										table
												.reload(
														"goodOperationRecordListTable",
														{
															data : goodOperationRecordListData.data
														});
									});

					// 刷新功能
					$("#refreshGoodOperationRecordList").on("click",
							function() {
								$("#serachGoodOperationRecordContent").val("");
								renderGoodOperationRecordTableDefault();
							});

					// 监听行工具事件
					table
							.on(
									'tool(goodOperationRecordListTable)',
									function(obj) {
										// 注：tool
										// 是工具条事件名，test 是
										// table 原始容器的属性
										// lay-filter="对应的值"
										var data = obj.data;
										// 获得当前行数据
										var layEvent = obj.event;
										// 获得 lay-event 对应的值
										if (layEvent === 'detail') {
											viewGoodOperationRecord(data);
										} else if (layEvent === 'del') {
											layer
													.confirm(
															'真的删除行么',
															function(index) {
																var url = "/goodOperationRecord/deleteGoodOperationRecord";
																var postData = {
																	"id" : data.id
																};
																$
																		.post(
																				url,
																				postData,
																				function(
																						responseData) {
																					if (responseData.success == "true"
																							|| responseData.success) {
																						layer
																								.msg('删除成功！');
																						obj
																								.del();
																						renderGoodOperationRecordTableDefault();
																						// 删除对应行（tr）的DOM结构
																					} else {
																						layer
																								.msg('删除失败：'
																										+ responseData.msg);
																					}
																				});
																layer
																		.close(index);
																// 向服务端发送删除指令
															});
										} else if (layEvent === 'edit') {
											editGoodOperationRecord(data);
										}
									});

					table.on('rowDouble(goodOperationRecordListTable)',
							function(obj) {
								viewGoodOperationRecord(obj.data);
							});
				});
