layui
		.use(
				[ 'form', 'layer', 'layedit', 'laydate', 'upload' ],
				function() {
					var form = layui.form;
					var layer = (parent.layer === undefined ? layui.layer
							: top.layer);
					var laypage = layui.laypage;
					var upload = layui.upload;
					var layedit = layui.layedit;
					var laydate = layui.laydate;
					var $ = layui.jquery;

					form.verify({
						taskName : function(val) {
							var inputName = "任务名称"
							return checkInputEmpty(val, inputName)
									|| checkInputLength(val, null, 50,
											inputName)
						},

						policy : function(val) {
							return checkInputEmpty(val, "任务关联策略")
						},

						description : function(val) {
							return checkInputLength(val, null, 1000, "任务描述")
						},
					});

					/**
					 * 获取页面的id
					 * 
					 */
					function getParentId() {
						let searchContent = window.location.search;

						if (searchContent === "undefined"
								|| searchContent.trim() === "") {
							return "";
						}

						searchContent = searchContent.substring(1,
								searchContent.length)

						let targetId = searchContent.split("=")[1];
						return targetId;
					}

					$(function initData() {
						var policyListData = getTimerTaskPolicyList().data;
						let id = getParentId();

						var thisDetail = getDetailById(id);

						/**
						 * 刷新物品类型选择框
						 */
						var $policySelect = $("#policyId");
						for ( var i in policyListData) {
							var selectedResult = (thisDetail.data.policyId == policyListData[i].id) ? 'selected'
									: '';
							var element = "<option value='"
									+ policyListData[i].id + "' "
									+ selectedResult + ">"
									+ policyListData[i].cname + "</option>";
							$policySelect.append(element);
						}

						$("#taskName").val(thisDetail.data.taskName);
						$("#description").val(thisDetail.data.description);
						$("#taskGroup").val(thisDetail.data.taskGroup);
						$("#config").val(thisDetail.data.config);
						$("#closed").val(thisDetail.data.closed);
						$("#otherParams").val(thisDetail.data.otherParams);
						form.render("select");
					});

					form.on("submit(editTimerTaskSubmit)", function(data) {
						// 弹出loading
						var index = top.layer.msg('数据提交中，请稍候', {
							icon : 16,
							time : false,
							shade : 0.8
						});

						var postData = $("#editTimerTaskForm").serialize();

						// 实际使用时的提交信息
						$.post("/timerTask/updateTimerTask", postData,
								function(response) {
									if (response.success == "false"
											|| !response.success) {
										if (response.msg != "null") {
											top.layer.msg("修改失败:"
													+ response.msg);
										} else {
											top.layer.msg("修改失败,原因未知！");
										}
									} else {
										setTimeout(function() {
											top.layer.close(index);
											top.layer.msg("修改成功！");
											layer.closeAll("iframe");
											// 刷新父页面
											parent.location.reload();
										}, 500);
									}
								});
						return false;
					})
				});