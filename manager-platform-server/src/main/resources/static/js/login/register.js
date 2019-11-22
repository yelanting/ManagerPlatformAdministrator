layui.use([ 'form', 'layer', 'jquery' ], function() {
	var form = layui.form;
	var layer = (parent.layer === undefined ? layui.layer : top.layer);
	var $ = layui.jquery;

	// 表单输入效果
	$(".registreBody .input-item").click(function(e) {
		e.stopPropagation();
		$(this).addClass("layui-input-focus").find(".layui-input").focus();
	})
	$(".registreBody .layui-form-item .layui-input").focus(function() {
		$(this).parent().addClass("layui-input-focus");
	})
	$(".registreBody .layui-form-item .layui-input").blur(function() {
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

	form.verify({
		username : function(val) {
			var inputName = "帐号名称"
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},
		password : function(val) {
			var inputName = "登录密码";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},
		userNickname : function(val) {
			var inputName = "用户昵称";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 50, inputName)
		},
		confirmPassword : function(val) {
			console.log(val);
			let inputName = "确认密码";
			return checkInputEmpty(val, inputName)
					|| checkInputLength(val, null, 500, inputName)
					|| checkConfirmPassword($("#password").val(), val())
		}
	});

	function checkConfirmPassword(password, confirmPassword) {
		console.log(password);
		console.log(confirmPassword);
		if (password != confirmPassword) {
			return "确认密码必须和密码相同";
		}
	}

//	form.on("submit(toRegister)", function(data) {
//		// 弹出loading
//		var index = top.layer.msg('数据提交中，请稍候', {
//			icon : 16,
//			time : false,
//			shade : 0.8
//		});
//
//		var postDataSerialize = $("#registerForm").serialize();
//		var postData = serializeToJson(postDataSerialize);
//		$.ajax({
//			url : "/register",
//			type : "POST",
//			data : postData,
//			async : false,
//			dataType : "json",
//			success : function(result) {
//				console.log(result);
//				return false;
//			},
//			error : function(result) {
//				layer.msg("注册失败!!");
//				return false;
//			}
//		});
//		
//		
//		return false;
//	})
});
