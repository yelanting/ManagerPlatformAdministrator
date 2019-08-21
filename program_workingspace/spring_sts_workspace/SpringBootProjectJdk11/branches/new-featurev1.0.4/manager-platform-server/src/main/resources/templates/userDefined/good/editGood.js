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
						goodName : function(val) {
							var inputName = "物品名称"
							return checkInputEmpty(val, inputName)
									|| checkInputLength(val, null, 50,
											inputName)
						},

						goodType : function(val) {
							return checkInputEmpty(val, "物品类型")
						},

						goodDesc : function(val) {
							return checkInputLength(val, null, 100, "物品描述")
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
						var goodTypeListData = getGoodTypeList().data;
						let id = getParentId();

						var thisDetail = getDetailById(id);

						/**
						 * 刷新物品类型选择框
						 */
						var $goodTypeSelect = $("#goodTypeId");
						for ( var i in goodTypeListData) {
							var selectedResult = (thisDetail.data.goodTypeId == goodTypeListData[i].id) ? 'selected'
									: ''
							var element = "<option value='"
									+ goodTypeListData[i].id + "' "
									+ selectedResult + ">"
									+ goodTypeListData[i].typeName
									+ "</option>";
							$goodTypeSelect.append(element);
						}

						$("#goodName").val(thisDetail.data.goodName);
						$("#goodDesc").val(thisDetail.data.goodDesc);
						$("#goodCode").val(thisDetail.data.goodCode);
						form.render("select");
					});

					form.on("submit(editGoodSubmit)", function(data) {
						// 弹出loading
						var index = top.layer.msg('数据提交中，请稍候', {
							icon : 16,
							time : false,
							shade : 0.8
						});

						var postData = $("#editGoodForm").serialize();

						// 实际使用时的提交信息
						$.post("/good/updateGood", postData,
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