layui.use([ 'form', 'layer', 'layedit', 'laydate', 'upload', 'table' ],
		function() {
			var form = layui.form;
			var layer = (parent.layer === undefined ? layui.layer : top.layer);
			var laypage = layui.laypage;
			var upload = layui.upload;
			var layedit = layui.layedit;
			var laydate = layui.laydate;
			var $ = layui.jquery;
			var table = layui.table;

			form.verify({
				executeShells : function(val) {
					var inputName = "执行命令"
					return checkInputEmpty(val, inputName)
							|| checkInputLength(val, null, 1000, inputName)
				},
			});

			/**
			 * 获取页面的id
			 * 
			 */
			function getParentId() {
				let searchContent = window.location.search;
				let targetId = getValueOfKeyFromWindowLocationSearch("id",
						searchContent);

				console.log(targetId);

				if (targetId.indexOf(",") != -1) {
					targetId = targetId.split(',');
				}

				return targetId;
			}

			/**
			 * 获取详情
			 */

			function getCurrentDetail(id) {
				let detail = getDetailById(id);
				console.log(detail);
				return detail.data;
			}

			form.on("submit(executeShellSubmit)", function(data) {
				var hidden = $("#executeResultParent").is(":hidden");
				if (!hidden) {
					$("#executeResult").val("");
				}
				// 弹出loading
				var index = top.layer.msg('数据提交中，请稍候', {
					icon : 16,
					time : false,
					shade : 0.8
				});

				let targetIds = getParentId();
				let postData;
				let url;

				console.log(typeof (targetIds));
				if (typeof (targetIds) == "object") {
					postData = {};
					postData['ids'] = targetIds;
					postData['executeShells'] = $("#executeShells").val();
					url = "/serverInfo/executeShellInBatch";
				} else {
					let detail = getCurrentDetail(targetIds);
					proccessObject(detail);
					postData = detail;
					postData['executeShells'] = $("#executeShells").val();
					console.log(postData);
					url = "/serverInfo/executeShell";
				}

				// 实际使用时的提交信息
				$.post(url, postData, function(response) {
					if (response.success == "false" || !response.success) {
						if (response.msg != "null") {
							top.layer.msg("执行命令失败:" + response.msg);
						} else {
							top.layer.msg("执行命令失败,原因未知！");
						}
					} else {
						$("#executeResultParent").show();
						$("#executeResult").val(response.data);
						table.render({
							elem : '#executeResultTable',
							height : 400,
							title : '执行结果',
							// 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
							// 是否显示加载条
							loading : true,
							// 默认排序
							initSort : "id",
							even : true,
							// 最窄宽度
							text : {
								none : '暂无相关数据'// 默认：无数据。
							},
							// 表头
							cols : [ [ {
								field : 'serverIp',
								title : '服务器IP',
								width : "15%"
							}, {
								field : 'result',
								title : '执行结果',
								style : "word-wrap:break-word;overflow:hidden;"
							} ] ],
							data : response.data
						});
						top.layer.msg("执行命令成功！");
					}
				});

				return false;
			})
		});