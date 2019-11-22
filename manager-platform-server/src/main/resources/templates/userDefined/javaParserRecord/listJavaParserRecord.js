/**
 *
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui
.use(
    ['laypage', 'layer', 'table', 'element', 'form'],
    function () {
    var form = layui.form;
    var layer = (parent.layer === undefined ? layui.layer
         : top.layer);
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laypage = layui.laypage;
    var element = layui.element;
    // 元素操作
    var widthPercent = "85%",
    heightPercent = "90%";

    /**
     * 获取所有数据
     */
    function getJavaParserRecordList() {
        var getJavaParserRecordListUrl = '/javaParserRecord/getList';
        var resultData = "";
        $.ajax({
            url: getJavaParserRecordListUrl,
            async: false,
            method: "get",
            contentType: "application/json",
            dataType: "json",
            success: function (result) {
                resultData = result;
            }
        });
        return resultData;
    }

    /**
     * 条件搜索
     */
    function getJavaParserRecordListWithCondition(searchContent) {
        if (searchContent == "undefined" || searchContent == "") {
            return getJavaParserRecordList();
        } else {
            var resultData = "",
            postData = {};
            $
            .ajax({
                url: "/javaParserRecord/searchList?searchContent="
                 + searchContent,
                method: "get",
                data: postData,
                async: false,
                dataType: "json",
                success: function (result) {
                    resultData = result;
                }
            });
            return resultData;
        }
    }

    /**
     * 刷新表格
     */
    function renderJavaParserRecordTableDefault() {
        var searchContent = "";
        var javaParserRecordListData = getJavaParserRecordListWithCondition(searchContent);
        table.reload("javaParserRecordListTable", {
            page: {
                curr: 1
            },
            data: javaParserRecordListData.data
        })
    }

    // 执行一个 table 实例
    table.render({
        elem: '#javaParserRecordListTable',
        height: 400,
        // 数据接口
        // url : "/javaParserRecord/getList/",
        // method:"get",
        // 表头
        title: 'java文件解析',
        // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        toolbar: true,
        defaultToolbar: ['filter', 'exports'],
        // 是否显示加载条
        loading: true,
        // 默认排序
        initSort: "projectName",
        even: true,
        // 最窄宽度
        // cellMinWidth: 60,
        // size: '',
        skin: 'row',
        page: {
            layout: ['prev', 'page', 'next', 'limit',
                'count', 'skip'],
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
                }, {
                    type: 'numbers',
                    title: "序号",
                    fixed: "left",
                    unresize: true
                }, {
                    field: 'projectName',
                    title: '项目名称',
                    sort: true,
                    width: "15%"
                }, {
                    field: 'versionControlType',
                    title: '代码管理',
                    sort: true,
                    width: "15%"
                },  {
                    field: 'newerRemoteUrl',
                    title: 'Url路径',
                    sort: true,
                    width: "15%"
                },{
                    fixed: 'right',
                    align: 'left',
                    title: '操作',
                    toolbar: '#operationBar',
                     width: '40%'
                }
            ]],
        data: getJavaParserRecordList().data
    });

    // 监听头工具栏事件,批量删除操作
    $('#deleteInBatch')
    .click(
        function () {
        var checkStatus = table
            .checkStatus('javaParserRecordListTable');
        var data = checkStatus.data;
        console.log(data);
        var javaParserRecordId = [];
        if (data.length > 0) {
            for (var eachDataIndex in data) {
                javaParserRecordId
                .push(data[eachDataIndex].id);
            }

            layer
            .confirm(
                '确定删除选中的数据吗？', {
                icon: 3,
                title: '提示信息'
            },
                function (index) {
                var url = "/javaParserRecord/deleteJavaParserRecordInBatch";

                var postData = {
                    "ids": javaParserRecordId
                };

                $
                .post(
                    url,
                    postData,
                    function (
                        responseData) {
                    if (responseData.success == "true"
                         || responseData.success) {
                        setTimeout(
                            function () {
                            top.layer
                            .msg("批量删除成功！");
                            // javaParserRecordTable.reload();
                            renderJavaParserRecordTableDefault();
                            layer
                            .close(index);
                        },
                            500);
                    } else {
                        layer
                        .msg('批量删除失败：'
                             + responseData.msg);
                    }
                });

            })
        } else {
            layer.msg("请选择需要删除的数据");
            return false;
        }
    });

    // 添加java文件解析
    function addJavaParserRecord() {
        layui.layer
        .open({
            title: "添加java文件解析",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "addJavaParserRecord.html",
            success: function (layero, index) {
                setTimeout(
                    function () {
                    layui.layer
                    .tips(
                        '点击此处返回列表',
                        '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500);
            }
        });
    }

    // 编辑java文件解析
    function editJavaParserRecord(edit) {
        var itemId = edit.id;
        layui.layer
        .open({
            title: "修改java文件解析",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "editJavaParserRecord.html?id="
             + itemId,
            success: function (layero, index) {
                setTimeout(
                    function () {
                    layui.layer
                    .tips(
                        '点击此处返回列表',
                        '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    // 查看java文件解析
    function viewJavaParserRecord(edit) {
        layui.layer
        .open({
            title: "查看java文件解析",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "viewJavaParserRecord.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame(
                        'body', index);
                body.find("#id").val(edit.id);
                body.find("#projectName").val(
                    edit.projectName);
                body.find("#versionControlType").val(
                    edit.versionControlType);
                body.find("#newerRemoteUrl").val(
                    edit.newerRemoteUrl);
                body.find("#newerVersion").val(
                    edit.newerVersion);
                body.find("#username").val(
                    edit.username);
                body.find("#password").val(
                    edit.password);

                body.find("#createDate").val(
                    edit.createDate);
                body.find("#updateDate").val(
                    edit.updateDate);
                body.find("#description").val(
                    edit.description);
                form.render();
                setTimeout(
                    function () {
                    layui.layer
                    .tips(
                        '点击此处返回列表',
                        '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    /**
     * 重置覆盖率数据
     */
    function resetJavaParserRecordData(data) {
        layer
        .confirm(
            '确定要重置数据吗？会删除相关的生成数据哦',
            function (index) {
            var url = "/javaParserRecord/resetJavaParserRecordData";
            $
            .post(
                url,
                data,
                function (
                    responseData) {
                if (responseData.success == "true"
                     || responseData.success) {
                    setTimeout(
                        function () {
                        top.layer
                        .msg("操作成功！");
                        renderJavaParserRecordTableDefault()
                    },
                        500);
                } else {
                    layer
                    .msg('操作失败：'
                         + responseData.msg);
                }
            });
            layer.close(index);
        });
    }

    $("#addJavaParserRecord_btn").click(function () {
        addJavaParserRecord();
    });

    // 搜索java文件解析
    $("#searchJavaParserRecord")
    .on(
        "click",
        function () {
        var searchContent = $(
                "#serachJavaParserRecordContent")
            .val();
        var javaParserRecordListData = getJavaParserRecordListWithCondition(searchContent);
        // 搜索完之后清空搜索数据
        $("#serachJavaParserRecordContent")
        .val("");
        // 表格重载
        table
        .reload(
            "javaParserRecordListTable", {
            data: javaParserRecordListData.data
        });
    });

    // 刷新功能
    $("#refreshJavaParserRecordList").on("click", function () {
        $("#serachJavaParserRecordContent").val("");
        renderJavaParserRecordTableDefault();
    });

    /**
     * 删除一条记录
     */
    function delteJavaParserRecordItem(data, obj) {
        layer
        .confirm(
            '真的删除行么',
            function (index) {
            var url = "/javaParserRecord/deleteJavaParserRecord";
            var postData = {
                "id": data.id
            };
            $
            .post(
                url,
                postData,
                function (
                    responseData) {
                if (responseData.success == "true"
                     || responseData.success) {
                    layer
                    .msg('删除成功！');
                    obj.del();
                    renderJavaParserRecordTableDefault();
                    // 删除对应行（tr）的DOM结构
                } else {
                    layer
                    .msg('删除失败：'
                         + responseData.msg);
                }
            });
            layer.close(index);
            // 向服务端发送删除指令
        });
    }
    /**
     * 生成解析报告
     */
    function createRequestMappingSource(data){
		// 实际使用时的提交信息
    	let postData = data;
		$.post("/javaParserRecord/createRequestMappingSource", postData,
				function(response) {
					if (response.success == "false"
							|| !response.success) {
						if (response.msg != "null") {
							top.layer.msg("生成失败:" + response.msg);
						} else {
							top.layer.msg("生成失败,原因未知！");
						}
					} else {
						layer.msg("生成成功！！！");
						
					}
					renderJavaParserRecordTableDefault();
					return false;
				});
    }
    
    /**
     * 下载报告
     */
    function downloadResult(data){
    	let resultDataUrl = data.resultUrl;
        if (null === resultDataUrl || resultDataUrl.trim() === "") {
            layer.msg("还没有生成呢，先去生成吧！");
            return false;
        }
        window.open(resultDataUrl);
    }
    
    // 监听行工具事件
    table.on('tool(javaParserRecordListTable)', function (obj) {
        // 注：tool
        // 是工具条事件名，test 是
        // table 原始容器的属性
        // lay-filter="对应的值"
        var data = obj.data;
        // 获得当前行数据
        var layEvent = obj.event;
        // 获得 lay-event 对应的值
        if (layEvent === 'detail') {
            viewJavaParserRecord(data);
        } else if (layEvent === 'del') {
            delteJavaParserRecordItem(data, obj);
        } else if (layEvent === 'edit') {
            editJavaParserRecord(data);
        } else if (layEvent === 'resetJavaParserRecordData') {
            resetJavaParserRecordData(data);
        }else if (layEvent === 'createRequestMappingSource') {
        	createRequestMappingSource(data);
        }else if (layEvent === 'downloadResult') {
        	downloadResult(data);
        }
    });

    /**
     * 双击查看
     */
    table.on('rowDouble(javaParserRecordListTable)', function (
            obj) {
        viewJavaParserRecord(obj.data);
    });
});
