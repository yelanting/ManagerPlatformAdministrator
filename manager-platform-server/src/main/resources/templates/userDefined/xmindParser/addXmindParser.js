layui.use([
		'form', 'layer', 'layedit', 'laydate', 'upload'
], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
			laypage = layui.laypage, upload = layui.upload,
			layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery;

	form.verify({
		uploadFile : function(val) {
			var fileFolder = "本地文件名称"
			return checkInputEmpty(val, fileFolder)
					|| checkInputLength(val, null, 800, fileFolder)
		},

	// fileToStore : function(val) {
	// return checkInputLength(val, null, 800, "待保存的文件名称")
	// }
	});

	/*
	 * form.on("submit(addXmindParserSubmit)", function(data) { // 弹出loading var
	 * index = top.layer.msg('数据提交中，请稍候', { icon : 16, time : false, shade : 0.8
	 * });
	 * 
	 * var postData = $("#addXmindParserForm").serialize(); // 实际使用时的提交信息
	 * $.post("/xmindParser/addXmindParser", postData, function(response) { if
	 * (response.success == "false" || !response.success) { if (response.msg !=
	 * "null") { top.layer.msg("添加失败:" + response.msg); } else {
	 * top.layer.msg("添加失败,原因未知！"); } } else { setTimeout(function() {
	 * top.layer.close(index); top.layer.msg("添加成功！"); layer.closeAll("iframe"); //
	 * 刷新父页面 parent.location.reload(); }, 500); } }); return false; })
	 */
	// 选完文件后不自动上传
	upload.render({
		elem : '#uploadFile', url : '/fileOperation/uploadFile', auto : false
		,method:"post"
		// ,multiple: true
//		,accept :'file',
		,exts: 'xmind'
		,field:'uploadFile'
		, bindAction : '#startUpload', done : function(res) {
			console.log(res)
			layer.msg("文件上传成功")
		},error:function(res){
			layer.msg("上传文件失败")
		}
	});
});