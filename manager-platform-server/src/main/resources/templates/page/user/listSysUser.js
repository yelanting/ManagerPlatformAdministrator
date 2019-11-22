//layui
//		.use(
//				[ 'form', 'layer', 'table', 'laytpl' ],
//				function() {
//					var form = layui.form, layer = parent.layer === undefined ? layui.layer
//							: top.layer, $ = layui.jquery, laytpl = layui.laytpl, table = layui.table;
//					
//					var widthPercent = "70%"
//					      , heightPercent = "90%";
//					
//					function getUserList() {
//						var getUserListUrl = '/sys/sys/sysUser/getList';
//						var resultData = "";
//						$.ajax({
//							url : getUserListUrl,
//							async : false,
//							method : "get",
//							dataType : "json",
//							success : function(result) {
//								resultData = result;
//							}
//						});
//						return resultData;
//					}
//
//					// 用户列表
//					var tableIns = table.render({
//						elem : '#userList',
//						cellMinWidth : 95,
//						page : true,
//						height : "full-125",
//						limits : [ 10, 15, 20, 25 ],
//						limit : 20,
//						id : "userListTable",
//						cols : [ [ {
//							type : "checkbox",
//							fixed : "left",
//							width : 50
//						}, {
//							type : 'numbers',
//							title : "序号",
//							fixed : "left",
//							unresize : true
//						}, {
//							field : 'userAccount',
//							title : '帐号',
//							minWidth : 100,
//							align : "center"
//						}, {
//							field : 'userNickname',
//							title : '昵称',
//							minWidth : 200,
//							align : 'center',
//						}, {
//							field : 'mobilePhone',
//							title : '手机号',
//							align : 'center'
//						}, {
//							field : 'sex',
//							title : '性别',
//							align : 'center',
//						}, {
//							field : 'userStatus',
//							title : '用户状态',
//							align : 'center',
//							templet : function(d) {
//								return d.userStatus == "0" ? "正常使用" : "限制使用";
//							}
//						}, {
//							field : 'admin',
//							title : '是否是管理员',
//							align : 'center',
//							templet : function(d) {
//								return d.admin == "true" ? "是" : "否";
//							}
//						}, {
//							title : '操作',
//							minWidth : 175,
//							templet : '#userListBar',
//							fixed : "right",
//							align : "center"
//						} ] ],
//						data : getUserList().data
//					});
//
//					// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
//					$(".search_btn").on("click", function() {
//						if ($(".searchVal").val() != '') {
//							table.reload("newsListTable", {
//								page : {
//									curr : 1
//								// 重新从第 1 页开始
//								},
//								where : {
//									key : $(".searchVal").val()
//								// 搜索的关键字
//								}
//							})
//						} else {
//							layer.msg("请输入搜索的内容");
//						}
//					});
//
//					// 添加用户
//					function addUser(edit) {
//						var index = layui.layer
//								.open({
//									title : "添加用户",
//									type : 2,
//									content : "userAdd.html",
//									success : function(layero, index) {
//										var body = layui.layer.getChildFrame(
//												'body', index);
//										if (edit) {
//											body.find(".userName").val(
//													edit.userName); // 登录名
//											body.find(".userEmail").val(
//													edit.userEmail); // 邮箱
//											body.find(
//													".userSex input[value="
//															+ edit.userSex
//															+ "]").prop(
//													"checked", "checked"); // 性别
//											body.find(".userGrade").val(
//													edit.userGrade); // 会员等级
//											body.find(".userStatus").val(
//													edit.userStatus); // 用户状态
//											body.find(".userDesc").text(
//													edit.userDesc); // 用户简介
//											form.render();
//										}
//										setTimeout(
//												function() {
//													layui.layer
//															.tips(
//																	'点击此处返回用户列表',
//																	'.layui-layer-setwin .layui-layer-close',
//																	{
//																		tips : 3
//																	});
//												}, 500)
//									}
//								})
//						layui.layer.full(index);
//						window.sessionStorage.setItem("index", index);
//						// 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
//						$(window).on(
//								"resize",
//								function() {
//									layui.layer.full(window.sessionStorage
//											.getItem("index"));
//								})
//					}
//					$(".addNews_btn").click(function() {
//						addUser();
//					})
//
//					// 批量删除
//					$(".delAll_btn")
//							.click(
//									function() {
//										var checkStatus = table
//												.checkStatus('userListTable'), data = checkStatus.data, newsId = [];
//										if (data.length > 0) {
//											for ( var i in data) {
//												newsId.push(data[i].newsId);
//											}
//											layer.confirm('确定删除选中的用户？', {
//												icon : 3,
//												title : '提示信息'
//											}, function(index) {
//												// $.get("删除文章接口",{
//												// newsId : newsId
//												// //将需要删除的newsId作为参数传入
//												// },function(data){
//												tableIns.reload();
//												layer.close(index);
//												// })
//											})
//										} else {
//											layer.msg("请选择需要删除的用户");
//										}
//									})
//
//					// 列表操作
//					table
//							.on(
//									'tool(userList)',
//									function(obj) {
//										var layEvent = obj.event, data = obj.data;
//
//										if (layEvent === 'edit') { // 编辑
//											addUser(data);
//										} else if (layEvent === 'usable') { // 启用禁用
//											var _this = $(this), usableText = "是否确定禁用此用户？", btnText = "已禁用";
//											if (_this.text() == "已禁用") {
//												usableText = "是否确定启用此用户？",
//														btnText = "已启用";
//											}
//											layer.confirm(usableText, {
//												icon : 3,
//												title : '系统提示',
//												cancel : function(index) {
//													layer.close(index);
//												}
//											}, function(index) {
//												_this.text(btnText);
//												layer.close(index);
//											}, function(index) {
//												layer.close(index);
//											});
//										} else if (layEvent === 'del') { // 删除
//											layer.confirm('确定删除此用户？', {
//												icon : 3,
//												title : '提示信息'
//											}, function(index) {
//												// $.get("删除文章接口",{
//												// newsId : data.newsId
//												// //将需要删除的newsId作为参数传入
//												// },function(data){
//												tableIns.reload();
//												layer.close(index);
//												// })
//											});
//										}
//									});
//
//				})
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
	function getSysUserList() {
		var getSysUserListUrl = '/sys/sysUser/getList';
		var resultData = "";
		$.ajax({
			url : getSysUserListUrl,
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
	function getSysUserListWithCondition(searchContent) {
		if (searchContent == "undefined" || searchContent == "") {
			return getSysUserList();
		} else {
			var resultData = "", postData = {};
			$.ajax({
				url : "/sys/sysUser/searchList?searchContent=" + searchContent,
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
	function renderSysUserTableDefault() {
		var searchContent = "";
		var sysUserListData = getSysUserListWithCondition(searchContent);
		table.reload("sysUserListTable", {
			page : {
				curr : 1
			},
			data : sysUserListData.data
		})
	}

	// 执行一个 table 实例
	var sysUserTable = table.render({
		elem : '#sysUserListTable',
		height : 400,
		// 数据接口
		// url : "/sys/sysUser/getList/",
		// method:"get",
		// 表头
		title : '系统用户',
		// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		toolbar : toolbar,
		defaultToolbar : [ "filter" ],
		// 是否显示加载条
		loading : true,
		// 默认排序
		initSort : "userAccount",
		even : true,
		// 最窄宽度
		cellMinWidth : 60,
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
			type : "checkbox",
			fixed : "left",
			width : 50
		}, {
			type : 'numbers',
			title : "序号",
			fixed : "left",
			unresize : true
		}, {
			field : 'userAccount',
			title : '帐号',
			minWidth : 100,
			align : "center",
			sort : true
		}, {
			field : 'userNickname',
			title : '昵称',
			minWidth : 200,
			align : 'center',
			sort : true
		}, {
			field : 'mobilePhone',
			title : '手机',
			align : 'center',
			sort : true
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			sort : true
		}, {
			field : 'userStatus',
			title : '状态',
			align : 'center',
			sort : true,
			templet : function(d) {
				return d.userStatus ? "冻结" : "正常";
			}
		}, {
			field : 'admin',
			title : '管理员',
			align : 'center',
			templet : function(d) {
				return !d.admin ? "否" : "是";
			}
		}, {
			title : '操作',
			minWidth : 175,
			templet : '#optionBar',
			fixed : "right",
			align : "center",
			width : "30%"
		} ] ],
		data : getSysUserList().data
	});

	// 监听头工具栏事件,批量删除操作
	$('#deleteInBatch').click(
			function() {
				var checkStatus = table.checkStatus('sysUserListTable');
				var data = checkStatus.data;
				console.log(data);
				var sysUserId = [];
				if (data.length > 0) {
					for ( var eachDataIndex in data) {
						sysUserId.push(data[eachDataIndex].id);
					}

					layer.confirm('确定删除选中的数据吗？', {
						icon : 3,
						title : '提示信息'
					}, function(index) {
						var url = "/sys/sysUser/deleteSysUserInBatch";

						var postData = {
							"ids" : sysUserId
						};

						$.post(url, postData, function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								setTimeout(function() {
									top.layer.msg("批量删除成功！");
									// sysUserTable.reload();
									renderSysUserTableDefault()
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

	// 添加系统用户
	function addSysUser() {
		layui.layer.open({
			title : "添加系统用户",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "addSysUser.html",
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

	// 编辑系统用户
	function editSysUser(edit) {
		layui.layer.open({
			title : "修改系统用户",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "editSysUser.html",
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#userAccount").val(edit.userAccount);
				body.find("#userNickname").val(edit.userNickname);
				body.find("#mobilePhone").val(edit.mobilePhone);
				body.find("#sex").val(edit.sex);
				body.find("#createDate").val(edit.createDate);
				body.find("#updateDate").val(edit.updateDate);
				body.find("#userStatus").val(edit.userStatus);
				body.find("#admin").val(edit.admin);
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

	// 查看系统用户
	function viewSysUser(edit) {
		console.log(edit);
		layui.layer.open({
			title : "查看系统用户",
			type : 2,
			area : [ widthPercent, heightPercent ],
			content : "viewSysUser.html",
			success : function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				body.find("#id").val(edit.id);
				body.find("#userAccount").val(edit.userAccount);
				body.find("#userNickname").val(edit.userNickname);
				body.find("#mobilePhone").val(edit.mobilePhone);
				body.find("#sex").val(edit.sex);
				body.find("#createDate").val(edit.createDate);
				body.find("#updateDate").val(edit.updateDate);
				body.find("#userStatus").val(!edit.userStatus ? "冻结" : "启用");
				body.find("#admin").val(!edit.admin ? "否" : "是");
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
	$("#addSysUser_btn").click(function() {
		addSysUser();
	});

	// 搜索系统用户
	// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$("#searchSysUser").on("click", function() {
		var searchContent = $("#serachSysUserContent").val();
		var sysUserListData = getSysUserListWithCondition(searchContent);
		// 搜索完之后清空搜索数据
		$("#serachSysUserContent").val("")
		// 表格重载
		table.reload("sysUserListTable", {
			data : sysUserListData.data
		});
	});

	// 刷新功能
	$("#refreshSysUserList").on("click", function() {
		$("#serachSysUserContent").val("");
		renderSysUserTableDefault();
	});

	// 监听行工具事件
	table.on('tool(sysUserListTable)', function(obj) {
		// 注：tool
		// 是工具条事件名，test 是
		// table 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data;
		// 获得当前行数据
		var layEvent = obj.event;
		// 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			viewSysUser(data);
		} else if (layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				var url = "/sys/sysUser/deleteSysUser";
				var postData = {
					"id" : data.id
				};
				$.post(url, postData,
						function(responseData) {
							if (responseData.success == "true"
									|| responseData.success) {
								layer.msg('删除成功！');
								obj.del();
								renderSysUserTableDefault();
								// 删除对应行（tr）的DOM结构
							} else {
								layer.msg('删除失败：' + responseData.msg);
							}
						});
				layer.close(index);
				// 向服务端发送删除指令
			});
		} else if (layEvent === 'edit') {
			editSysUser(data);
		}
	});

	table.on('rowDouble(sysUserListTable)', function(obj) {
		viewSysUser(obj.data);
	});
});
