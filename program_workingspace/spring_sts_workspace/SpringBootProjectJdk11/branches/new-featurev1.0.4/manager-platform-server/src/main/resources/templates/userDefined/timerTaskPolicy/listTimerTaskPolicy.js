/**
 * 
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

/**
 * 获取所有数据
 */
function getTimerTaskPolicyList() {
    var getTimerTaskPolicyListUrl = '/timerTaskPolicy/getList';
    var resultData = "";
    $.ajax({
        url: getTimerTaskPolicyListUrl,
        async: false,
        method: "get",
        dataType: "json",
        success: function(result) {
            resultData = result;
        }
    });
    return resultData;
}

/**
 * 根据ID查找名称
 * 
 * @returns
 */
function getTimerTaskPolicyNameById(id, responseData) {
    for (var i in responseData.data) {
        if (id == responseData.data[i].id) {
            return responseData.data[i].cname;
        }
    }
}

layui.use(['laypage', 'layer', 'table', 'element', 'form'],
function() {
    var form = layui.form;
    var layer = (parent.layer === undefined ? layui.layer: top.layer);
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laypage = layui.laypage;
    var element = layui.element;
    // 元素操作
    var widthPercent = "70%",
    heightPercent = "90%";

    /**
	 * 条件搜索
	 */
    function getTimerTaskPolicyListWithCondition(searchContent) {
        if (searchContent == "undefined" || searchContent == "") {
            return getTimerTaskPolicyList();
        } else {
            var resultData = "",
            postData = {};
            $.ajax({
                url: "/timerTaskPolicy/searchList?searchContent=" + searchContent,
                method: "get",
                data: postData,
                async: false,
                dataType: "json",
                success: function(result) {
                    resultData = result;
                }
            });
            return resultData;
        }
    }

    /**
	 * 刷新表格
	 */
    function renderTimerTaskPolicyTableDefault() {
        var searchContent = "";
        var timerTaskPolicyListData = getTimerTaskPolicyListWithCondition(searchContent);
        table.reload("timerTaskPolicyListTable", {
            page: {
                curr: 1
            },
            data: timerTaskPolicyListData.data
        })
    }

    // 执行一个 table 实例
    var timerTaskPolicyTable = table.render({
        elem: '#timerTaskPolicyListTable',
        height: 400,
        title: '策略列表',
        // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        toolbar: true,
        defaultToolbar: ["filter"],
        // 是否显示加载条
        loading: true,
        // 默认排序
        initSort: "id",
        even: true,
        // 最窄宽度
        cellMinWidth: 80,
        size: "lg",
        page: {
            layout: ['prev', 'page', 'next', 'limit', 'count', 'skip'],
            prev: "上一页",
            next: "下一页",
            first: "首页",
            last: "尾页",
            // 每页条数的选择项
            limits: [5, 10, 20, 30, 40, 50, 100],
            limit: 20,
            groups: 3,
            skin: '#1E9FFF',
            // 自定义选中色值
            skip: true,
        },
        text: {
            none: '暂无相关数据' // 默认：无数据。
        },
        // 表头
        cols: [[{
            type: 'checkbox',
            fixed: 'left',
            unresize: true
        },
        {
            type: 'numbers',
            title: "序号",
            fixed: "left",
            unresize: true
        },
        {
            field: 'cname',
            title: '中文名称',
            sort: true,
        },
        {
            field: 'ename',
            title: '英文名称',
            sort: true,
        },
        {
            field: 'code',
            title: '执行代码',
            sort: true,
        },
        {
            fixed: 'right',
            align: 'center',
            title: '操作',
            toolbar: '#operationBar',
        }]],
        data: getTimerTaskPolicyList().data
    });

    // 监听头工具栏事件,批量删除操作
    $('#deleteInBatch').click(function() {
        var checkStatus = table.checkStatus('timerTaskPolicyListTable');
        var data = checkStatus.data;
        var timerTaskPolicyId = [];
        if (data.length > 0) {
            for (var eachDataIndex in data) {
                timerTaskPolicyId.push(data[eachDataIndex].id);
            }

            layer.confirm('确定删除选中的数据吗？将会删除其下关联的所有物品信息！', {
                icon: 3,
                title: '提示信息'
            },
            function(index) {
                var url = "/timerTaskPolicy/deleteTimerTaskPolicyInBatch";

                var postData = {
                    "ids": timerTaskPolicyId
                };

                $.post(url, postData,
                function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        setTimeout(function() {
                            top.layer.msg("批量删除成功！");
                            renderTimerTaskPolicyTableDefault();
                            layer.close(index);
                        },
                        500);
                    } else {
                        layer.msg('批量删除失败：' + responseData.msg);
                    }
                });

            })
        } else {
            layer.msg("请选择需要删除的数据");
        }
    });

    // 添加策略
    function addTimerTaskPolicy() {
        layui.layer.open({
            title: "添加策略",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "addTimerTaskPolicy.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },
                500)
            }
        });
    }

    // 编辑策略
    function editTimerTaskPolicy(edit) {
        layui.layer.open({
            title: "修改策略",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "editTimerTaskPolicy.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#cname").val(edit.cname);
                body.find("#ename").val(edit.ename);
                body.find("#code").val(edit.code);
                body.find("#createUser").val(edit.createUser);
                body.find("#updateUser").val(edit.updateUser);
                body.find("#description").val(edit.description);
                body.find("#createDate").val(edit.createDate);
                body.find("#updateDate").val(edit.updateDate);
                form.render();
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },
                500)
            }
        });
    }

    // 查看策略
    function viewTimerTaskPolicy(edit) {
        layui.layer.open({
            title: "查看策略",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "viewTimerTaskPolicy.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#cname").val(edit.cname);
                body.find("#ename").val(edit.ename);
                body.find("#code").val(edit.code);
                body.find("#description").val(edit.description);
                body.find("#createDate").val(edit.createDate);
                body.find("#updateDate").val(edit.updateDate);
                body.find("#createUser").val(edit.createUser);
                body.find("#updateUser").val(edit.updateUser);
                form.render();
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },
                500)
            }
        });
    }
    $("#addTimerTaskPolicy_btn").click(function() {
        addTimerTaskPolicy();
    });

    // 搜索策略
    // 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $("#searchTimerTaskPolicy").on("click",
    function() {
        var searchContent = $("#serachTimerTaskPolicyContent").val();
        var timerTaskPolicyListData = getTimerTaskPolicyListWithCondition(searchContent);
        // 搜索完之后清空搜索数据
        $("#serachTimerTaskPolicyContent").val("")
        // 表格重载
        table.reload("timerTaskPolicyListTable", {
            data: timerTaskPolicyListData.data
        });
    });

    // 刷新功能
    $("#refreshTimerTaskPolicyList").on("click",
    function() {
        $("#serachTimerTaskPolicyContent").val("");
        renderTimerTaskPolicyTableDefault();
    });

    // 监听行工具事件
    table.on('tool(timerTaskPolicyListTable)',
    function(obj) {
        // 注：tool
        // 是工具条事件名，test 是
        // table 原始容器的属性
        // lay-filter="对应的值"
        var data = obj.data;
        // 获得当前行数据
        var layEvent = obj.event;
        // 获得 lay-event 对应的值
        if (layEvent === 'detail') {
            viewTimerTaskPolicy(data);
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么？其下关联的物品信息也会删除！',
            function(index) {
                var url = "/timerTaskPolicy/deleteTimerTaskPolicy";
                var postData = {
                    "id": data.id
                };
                $.post(url, postData,
                function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        layer.msg('删除成功！');
                        obj.del();
                        renderTimerTaskPolicyTableDefault();
                        // 删除对应行（tr）的DOM结构
                    } else {
                        layer.msg('删除失败：' + responseData.msg);
                    }
                });
                layer.close(index);
                // 向服务端发送删除指令
            });
        } else if (layEvent === 'edit') {
            editTimerTaskPolicy(data);
        }
    });

    table.on('rowDouble(timerTaskPolicyListTable)',
    function(obj) {
        viewTimerTaskPolicy(obj.data);
    });
});