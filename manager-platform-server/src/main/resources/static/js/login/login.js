layui.use([ 'form', 'layer', 'jquery' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var $ = layui.jquery;

	$(".loginBody .seraph").click(function() {
		layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧", {
			time : 5000
		});
	})

	// 表单输入效果
	$(".loginBody .input-item").click(function(e) {
		e.stopPropagation();
		$(this).addClass("layui-input-focus").find(".layui-input").focus();
	})
	$(".loginBody .layui-form-item .layui-input").focus(function() {
		$(this).parent().addClass("layui-input-focus");
	})
	$(".loginBody .layui-form-item .layui-input").blur(function() {
		$(this).parent().removeClass("layui-input-focus");
		if ($(this).val() != '') {
			$(this).parent().addClass("layui-input-active");
		} else {
			$(this).parent().removeClass("layui-input-active");
		}
	});

	(function() {
		$("#username").focus();
	})();

	$("#forgetPassowrd").click(function() {
		layer.msg("暂不支持自助修改密码,请联系管理员！！！");
		return false;
	})

	$("#register").click(function() {
		window.location.href = "/register";
		return false;
	})
});
