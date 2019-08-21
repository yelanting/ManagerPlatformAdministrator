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

			/**
			 * 获取页面的id
			 * 
			 */
			function getParentId() {
				let searchContent = window.location.search;
				let targetId = getValueOfKeyFromWindowLocationSearch("id",
						searchContent);

				if (targetId.indexOf(",") != -1) {
					targetId = targetId.split(',');
				}
				return targetId;
			}

			form.on("submit(checkServerCanBeConnectedSubmit)", function(data) {
				// 弹出loading
				var index = top.layer.msg('数据提交中，请稍候', {
					icon : 16,
					time : false,
					shade : 0.8
				});

				let targetIds = getParentId();
				let postData;
				let url;
				
				url = "/serverInfo/checkServerCanBeConnectedInBatch";
				postData = {};
				if (typeof (targetIds) == "object") {
					postData['ids'] = targetIds;
				} else {
					postData['ids'] = [targetIds];
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
						table.render({
							elem : '#checkResultTable',
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
								title : '检测结果',
								style : "word-wrap:break-word;overflow:hidden;"
							} ] ],
							data : response.data
						});
						top.layer.msg("检测完成！");
					}
				});

				return false;
			})
		});