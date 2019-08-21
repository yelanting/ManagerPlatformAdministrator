layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'],
function() {
    var form = layui.form;
    var layer = (parent.layer === undefined ? layui.layer: top.layer);
    var laypage = layui.laypage;
    var upload = layui.upload;
    var layedit = layui.layedit;
    var laydate = layui.laydate;
    var $ = layui.jquery;

    form.verify({
        serverName: function(val) {
            var inputName = "服务器名称"
            return checkInputEmpty(val, inputName) || checkInputLength(val, null, 50, inputName)
        },

        serverType: function(val) {
            return checkInputEmpty(val, "服务器类型")
        },

        username: function(val) {
            var inputName = "登录用户名"
            return checkInputEmpty(val, inputName) || checkInputLength(val, null, 20, inputName)
        },

        password: function(val) {
            var inputName = "登录密码"
            return checkInputEmpty(val, inputName) || checkInputLength(val, null, 20, inputName)
        },

        description: function(val) {
            return checkInputLength(val, null, 100, "服务器描述")
        },
    });

    /**
	 * 获取页面的id
	 * 
	 */
    function getParentId() {
        let searchContent = window.location.search;

        if (searchContent === "undefined" || searchContent.trim() === "") {
            return "";
        }

        searchContent = searchContent.substring(1, searchContent.length)

        let targetId = searchContent.split("=")[1];
        return targetId;
    }

    form.on("submit(editServerInfoSubmit)",
    function(data) {
        // 弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });

        var postData = $("#editServerInfoForm").serialize();

        // 实际使用时的提交信息
        $.post("/serverInfo/updateServerInfo", postData,
        function(response) {
            if (response.success == "false" || !response.success) {
                if (response.msg != "null") {
                    top.layer.msg("修改失败:" + response.msg);
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
                },
                500);
            }
        });
        return false;
    })
});