layui.use(['form', 'layer', 'jquery'], function () {
	var form = layui.form,
	layer = parent.layer === undefined ? layui.layer
		 : top.layer
		$ = layui.jquery;

	//监听表单提交事件
	form.on('submit(login)', function (data) {
		$.post("/loginAction", {
			userName: $("#userName").val(),
			password: $("#password").val(),
		}, function (result) {
			if (result.success) {
				if (result.roleSize == 1) {
					var roleId = result.roleList[0].id;
					$.post("${basePath!}/user/saveRole", {
						roleId: roleId
					}, function (result) {
						if (result.success) {
							window.location.href = "${basePath!}/welcome";
						}
					});
				} else {
					$("#roleList").empty();
					var roles = result.roleList;
					for (var i = 0; i < roles.length; i++) {
						if (i == 0) {
							$("#roleList").append("<input type='radio' checked=true  name='role' title='" + roles[i].name + "' value='" + roles[i].id + "'/>")

						} else {
							$("#roleList").append("<input type='radio' name='role'  title='" + roles[i].name + "' value='" + roles[i].id + "'/>")
						}
						layui.form.render(); //刷新所有表单的渲染效果
					}

					layer.open({
						type: 1,
						title: '请选择一个角色登录系统',
						content: $("#light"),
						area: '500px',
						offset: 'auto',
						skin: 'layui-layer-molv',
						shade: [0.8, '#393D49']
					})

					/*document.getElementById('light').style.display='block';
					document.getElementById('fade').style.display='block';*/
				}
			} else {
				layer.alert(result.errorInfo);
			}
		});

		return false;
	});

	//监听角色选择提交
	form.on('submit(choserolefilter)', function (data) {
		saveRole();
		return false;
	});

	function saveRole() {
		var roleId = $("input[name='role']:checked").val();
		$.post("${basePath!}/user/saveRole", {
			roleId: roleId
		}, function (result) {
			if (result.success) {
				window.location.href = "${basePath!}/welcome";
			}
		});
	}

	// 表单输入效果
	$(".loginBody .input-item").click(function (e) {
		e.stopPropagation();
		$(this).addClass("layui-input-focus").find(".layui-input").focus();
	});
	$(".loginBody .layui-form-item .layui-input").focus(function () {
		$(this).parent().addClass("layui-input-focus");
	});
	$(".loginBody .layui-form-item .layui-input").blur(function () {
		$(this).parent().removeClass("layui-input-focus");
		if ($(this).val() != '') {
			$(this).parent().addClass("layui-input-active");
		} else {
			$(this).parent().removeClass("layui-input-active");
		}
	});
	

//	//监听表单提交事件
//	form.on('submit(login)', function (data) {
//		$.post("/login", {
//			userName: $("#userName").val(),
//			password: $("#password").val(),
//		}, function (result) {
//			if (result.success) {
//				if (result.roleSize == 1) {
//					var roleId = result.roleList[0].id;
//					$.post("${basePath!}/user/saveRole", {
//						roleId: roleId
//					}, function (result) {
//						if (result.success) {
//							window.location.href = "${basePath!}/welcome";
//						}
//					});
//				} else {
//					$("#roleList").empty();
//					var roles = result.roleList;
//					for (var i = 0; i < roles.length; i++) {
//						if (i == 0) {
//							$("#roleList").append("<input type='radio' checked=true  name='role' title='" + roles[i].name + "' value='" + roles[i].id + "'/>")
//
//						} else {
//							$("#roleList").append("<input type='radio' name='role'  title='" + roles[i].name + "' value='" + roles[i].id + "'/>")
//						}
//						layui.form.render(); //刷新所有表单的渲染效果
//					}
//
//					layer.open({
//						type: 1,
//						title: '请选择一个角色登录系统',
//						content: $("#light"),
//						area: '500px',
//						offset: 'auto',
//						skin: 'layui-layer-molv',
//						shade: [0.8, '#393D49']
//					})
//
//					/*document.getElementById('light').style.display='block';
//					document.getElementById('fade').style.display='block';*/
//				}
//			} else {
//				layer.alert(result.errorInfo);
//			}
//		});
//
//		return false;
//	});
//
//	//监听角色选择提交
//	form.on('submit(choserolefilter)', function (data) {
//		saveRole();
//		return false;
//	});
//
//	function saveRole() {
//		var roleId = $("input[name='role']:checked").val();
//		$.post("${basePath!}/user/saveRole", {
//			roleId: roleId
//		}, function (result) {
//			if (result.success) {
//				window.location.href = "${basePath!}/welcome";
//			}
//		});
//	}
//
//	// 表单输入效果
//	$(".loginBody .input-item").click(function (e) {
//		e.stopPropagation();
//		$(this).addClass("layui-input-focus").find(".layui-input").focus();
//	});
//	$(".loginBody .layui-form-item .layui-input").focus(function () {
//		$(this).parent().addClass("layui-input-focus");
//	});
//	$(".loginBody .layui-form-item .layui-input").blur(function () {
//		$(this).parent().removeClass("layui-input-focus");
//		if ($(this).val() != '') {
//			$(this).parent().addClass("layui-input-active");
//		} else {
//			$(this).parent().removeClass("layui-input-active");
//		}
//	});
});
