layui.use([ 'form', 'layer', 'jquery' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var $ = layui.jquery;

	$(".loginBody .seraph").click(function() {
		layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧", {
			time : 5000
		});
	})

	// 登录按钮
	form.on("submit(login)", function(data) {
		$(this).text("登录中...").attr("disabled", "disabled").addClass(
				"layui-disabled");
		setTimeout(function() {
			window.location.href = "/";
		}, 1000);
		return false;
	})

	// 监听表单提交事件
	form.on('submit(login)', function(data) {
		$.post("/login/loginAction", $("#loginForm").serialize(), function(
				result) {
			console.log(result);
			if (result.success) {
				window.location.href = "/index";
				// if (result.data.sysRole.length == 1) {
				// var roleId = result.roleList[0].id;
				// $.post("${basePath!}/user/saveRole", {
				// roleId : roleId
				// }, function(result) {
				// if (result.success) {
				// window.location.href = "${basePath!}/welcome";
				// }
				// });
				// } else {
				// $("#roleList").empty();
				// var roles = result.roleList;
				// for (var i = 0; i < roles.length; i++) {
				// if (i == 0) {
				// $("#roleList").append(
				// "<input type='radio' checked=true name='role' title='"
				// + roles[i].name + "' value='"
				// + roles[i].id + "'/>")
				//
				// } else {
				// $("#roleList").append(
				// "<input type='radio' name='role' title='"
				// + roles[i].name + "' value='"
				// + roles[i].id + "'/>")
				// }
				// layui.form.render();// 刷新所有表单的渲染效果
				// }
				//
				// layer.open({
				// type : 1,
				// title : '请选择一个角色登录系统',
				// content : $("#light"),
				// area : '500px',
				// offset : 'auto',
				// skin : 'layui-layer-molv',
				// shade : [ 0.8, '#393D49' ]
				// })
				//
				// /*
				// * document.getElementById('light').style.display='block';
				// * document.getElementById('fade').style.display='block';
				// */
				// }
			} else {
				layer.alert(result.data.msg);
			}
		});
		return false;
	});

	// // 监听角色选择提交
	// form.on('submit(choserolefilter)', function(data) {
	// saveRole();
	// return false;
	// });
	//
	// function saveRole() {
	// var roleId = $("input[name='role']:checked").val();
	// $.post("${basePath!}/user/saveRole", {
	// roleId : roleId
	// }, function(result) {
	// if (result.success) {
	// window.location.href = "${basePath!}/welcome";
	// }
	// });
	// }

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
});
