/**
 *
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

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
    var widthPercent = "85%",
    heightPercent = "90%";

    /**
     * 获取所有数据
     */
    function getCodeCoverageList() {
        var getCodeCoverageListUrl = '/codeCoverage/getList';
        var resultData = "";
        $.ajax({
            url: getCodeCoverageListUrl,
            async: false,
            method: "get",
            contentType : "application/json",
            dataType: "json",
            success: function(result) {
                resultData = result;
            }
        });
        return resultData;
    }

    /**
     * 条件搜索
     */
    function getCodeCoverageListWithCondition(searchContent) {
        if (searchContent == "undefined" || searchContent == "") {
            return getCodeCoverageList();
        } else {
            var resultData = "",
            postData = {};
            $.ajax({
                url: "/codeCoverage/searchList?searchContent=" + searchContent,
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
    function renderCodeCoverageTableDefault() {
        var searchContent = "";
        var codeCoverageListData = getCodeCoverageListWithCondition(searchContent);
        table.reload("codeCoverageListTable", {
            page: {
                curr: 1
            },
            data: codeCoverageListData.data
        })
    }

    // 执行一个 table 实例
    table.render({
        elem: '#codeCoverageListTable',
        height: 400,
        // 数据接口
        // url : "/codeCoverage/getList/",
        // method:"get",
        // 表头
        title: '代码覆盖率',
        // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        toolbar: true ,
        defaultToolbar: ['filter', 'exports'],
        // 是否显示加载条
        loading: true,
        // 默认排序
        initSort: "projectName",
        even: true,
        // 最窄宽度
        // cellMinWidth: 60,
//        size: '',
        skin: 'row',
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
            field: 'projectName',
            title: '项目名称',
            sort: true,
            width: "15%"
        },
        // {
        // field: 'newerRemoteUrl',
        // title: '新版本路径',
        // sort: true,
        // // width: 220
        // },
        {
            field: 'tcpServerIp',
            title: 'tcp服务IP',
            sort: true,
            width: "15%"
        },
        {
            field: 'tcpServerPort',
            title: 'tcp服务端口',
            sort: true,
            templet: '#tcpServerPort',
            width: "8%"
        },
        // {
        // field: 'needCompile',
        // title: '是否需要编译',
        // templet: '#needCompile',
        // unresize: true
        // // width: 80
        // },
        // {
        // field: 'buildType',
        // title: '构建类型',
        // // width: 80
        // },
        // {
        // field: 'jdkVersion',
        // title: 'jdk版本',
        // // width: 80
        // },
        // {
        // field: 'description',
        // title: '备注',
        // // width: 100
        // },
        {
            fixed: 'right',
            align: 'left',
            title: '操作',
            toolbar: '#operationBar',
//            width: '40%'
        }]],
        data: getCodeCoverageList().data,
        done: function(){
        	setTimeout(function() {
        		layer.msg("列表刷新完成！");
            },
            500);
        	
        }
    });

    // 监听头工具栏事件,批量删除操作
    $('#deleteInBatch').click(function() {
        var checkStatus = table.checkStatus('codeCoverageListTable');
        var data = checkStatus.data;
        console.log(data);
        var codeCoverageId = [];
        if (data.length > 0) {
            for (var eachDataIndex in data) {
                codeCoverageId.push(data[eachDataIndex].id);
            }

            layer.confirm('确定删除选中的数据吗？', {
                icon: 3,
                title: '提示信息'
            },
            function(index) {
                var url = "/codeCoverage/deleteCodeCoverageInBatch";

                var postData = {
                    "ids": codeCoverageId
                };

                $.post(url, postData,
                function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        setTimeout(function() {
                            top.layer.msg("批量删除成功！");
                            // codeCoverageTable.reload();
                            renderCodeCoverageTableDefault();
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
            return false;
        }
    });

    // 添加代码覆盖率
    function addCodeCoverage() {
        layui.layer.open({
            title: "添加代码覆盖率",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "addCodeCoverage.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },
                500);
            }
        });
    }

    // 编辑代码覆盖率
    function editCodeCoverage(edit) {
        var itemId = edit.id;
        layui.layer.open({
            title: "修改代码覆盖率",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "editCodeCoverage.html?id=" + itemId,
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

    // 查看代码覆盖率
    function viewCodeCoverage(edit) {
        layui.layer.open({
            title: "查看代码覆盖率",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "viewCodeCoverage.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#projectName").val(edit.projectName);
                body.find("#versionControlType").val(edit.versionControlType);
                body.find("#newerRemoteUrl").val(edit.newerRemoteUrl);
                body.find("#olderRemoteUrl").val(edit.olderRemoteUrl);
                body.find("#newerVersion").val(edit.newerVersion);
                body.find("#olderVersion").val(edit.olderVersion);
                body.find("#tcpServerIp").val(edit.tcpServerIp);
                body.find("#tcpServerPort").val(edit.tcpServerPort);
                body.find("#username").val(edit.username);
                body.find("#password").val(edit.password);
                body.find("#buildType").val(edit.buildType);
                body.find("#jdkVersion").val(edit.jdkVersion ? edit.jdkVersion: "1.8");
                if (edit.needCompile === "true") {
                    body.find("#needCompile").val("是");
                } else {
                    body.find("#needCompile").val("否");
                }

                body.find("#dependencyProjects").val(edit.dependencyProjects);
                body.find("#createDate").val(edit.createDate);
                body.find("#updateDate").val(edit.updateDate);
                body.find("#description").val(edit.description);
                body.find("#channelName").val(edit.channelName);
                let serverInfo = getDetailById(edit.serverId);
                
                body.find("#newSourceType").val(edit.newSourceType);
                body.find("#sourceCodePath").val(edit.sourceCodePath);
                body.find("#serverId").val(serverInfo.data.serverIp);
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

    /**
     * 获取全量覆盖率信息
     */
    function getWholeCodeCoverageData(data) {
        let wholeCodeCoverageDataUrl = data.wholeCodeCoverageDataUrl;

        if (null === wholeCodeCoverageDataUrl || wholeCodeCoverageDataUrl.trim() === "") {
            layer.msg("还没有生成报告呢，先去生成吧！");
            return false;
        }

        window.open(wholeCodeCoverageDataUrl)
    }

    /**
     * 获取增量覆盖率信息
     */
    function getIncrementCodeCoverageData(data) {
        let incrementCodeCoverageDataUrl = data.incrementCodeCoverageDataUrl;
        if (null === incrementCodeCoverageDataUrl || incrementCodeCoverageDataUrl.trim() === "") {
            layer.msg("还没有生成增量报告呢，先去生成把或者去联系管理员看下！");
            return false;
        }

        window.open(incrementCodeCoverageDataUrl)
    }

    /**
     * 生成全量覆盖率信息
     */
    function createAllCodeCoverageData(data) {
        layer.confirm('确定要开始生成全量报告吗？会删除旧报告且可能耗时很久',
        function(index) {
            var url = "/codeCoverage/createAllCodeCoverageData";
            $.post(url, data,
            function(responseData) {
                if (responseData.success == "true" || responseData.success) {
                    setTimeout(function() {
                        top.layer.msg("操作成功！");
                        renderCodeCoverageTableDefault();
                    },
                    500);
                } else {
                    layer.msg('操作失败：' + responseData.msg);
                }
            });
            layer.close(index);
        });
    }

    /**
     * 生成增量覆盖率信息
     */
    function createIncrementCodeCoverageData(data) {
        layer.confirm('确定要开始生成增量报告吗？会删除旧报告且可能耗时很久',
        function(index) {
            var url = "/codeCoverage/createIncrementCodeCoverageData";
            $.post(url, data,
            function(responseData) {
                if (responseData.success == "true" || responseData.success) {
                    setTimeout(function() {
                        top.layer.msg("操作成功！");
                        renderCodeCoverageTableDefault()
                    },
                    500);
                } else {
                    layer.msg('操作失败：' + responseData.msg);
                }
            });
            layer.close(index);
        });
    }
    /**
     * 重置覆盖率数据
     */
    function resetCodeCoverageData(data) {
        layer.confirm('确定要重置覆盖率报告吗？会删除增量和全量数据哦',
        function(index) {
            var url = "/codeCoverage/resetCodeCoverageData";
            $.post(url, data,
            function(responseData) {
                if (responseData.success == "true" || responseData.success) {
                    setTimeout(function() {
                        top.layer.msg("操作成功！");
                        renderCodeCoverageTableDefault()
                    },
                    500);
                } else {
                    layer.msg('操作失败：' + responseData.msg);
                }
            });
            layer.close(index);
        });
    }

    $("#addCodeCoverage_btn").click(function() {
        addCodeCoverage();
    });

    // 搜索代码覆盖率
    // 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $("#searchCodeCoverage").on("click",
    function() {
        var searchContent = $("#serachCodeCoverageContent").val();
        var codeCoverageListData = getCodeCoverageListWithCondition(searchContent);
        // 搜索完之后清空搜索数据
        $("#serachCodeCoverageContent").val("")
        // 表格重载
        table.reload("codeCoverageListTable", {
            data: codeCoverageListData.data
        });
    });

    // 刷新功能
    $("#refreshCodeCoverageList").on("click",
    function() {
        $("#serachCodeCoverageContent").val("");
        renderCodeCoverageTableDefault();
    });

    /**
     * 删除一条记录
     */
    function delteCodeCoverageItem(data, obj) {
        layer.confirm('真的删除行么',
        function(index) {
            var url = "/codeCoverage/deleteCodeCoverage";
            var postData = {
                "id": data.id
            };
            $.post(url, postData,
            function(responseData) {
                if (responseData.success == "true" || responseData.success) {
                    layer.msg('删除成功！');
                    obj.del();
                    renderCodeCoverageTableDefault();
                    // 删除对应行（tr）的DOM结构
                } else {
                    layer.msg('删除失败：' + responseData.msg);
                }
            });
            layer.close(index);
            // 向服务端发送删除指令
        });
    }

    /**
     * 上传文件
     */
    function uploadExecFile(data) {
        var url = "uploadCodeCoverage.html?id=" + data.id;
        layui.layer.open({
            title: "上传代码覆盖率文件",
            type: 2,
            area: [widthPercent, heightPercent],
            content: url,
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

    /**
     * 上传Class文件
     */
    function uploadClasses(data) {
        var url = "uploadClasses.html?id=" + data.id;
        layui.layer.open({
            title: "上传Class文件",
            type: 2,
            area: [widthPercent, heightPercent],
            content: url,
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
    
    /**
     * 上传文件
     */
    function uploadFiles(data) {
        var url = "uploadFiles.html?id=" + data.id;
        layui.layer.open({
            title: "上传文件(点击或拖拽文件至区域可上传)",
            type: 2,
            area: [widthPercent, heightPercent],
            content: url,
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
    /**
     * 配置定时任务
     */
    function configCheduledTask(data) {
    	var url = "configScheduledTask.html?id=" + data.id;
    	layui.layer.open({
    		title: "配置定时任务",
    		type: 2,
    		area: [widthPercent, heightPercent],
    		content: url,
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

    // 监听行工具事件
    table.on('tool(codeCoverageListTable)',
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
            viewCodeCoverage(data);
        } else if (layEvent === 'del') {
            delteCodeCoverageItem(data, obj);
        } else if (layEvent === 'edit') {
            editCodeCoverage(data);
        } else if (layEvent === 'getWholeData') {
            getWholeCodeCoverageData(data);
        } else if (layEvent === 'getIncrementData') {
            getIncrementCodeCoverageData(data);
        } else if (layEvent === 'createAllData') {
            createAllCodeCoverageData(data);
        } else if (layEvent === 'createIncrementData') {
            createIncrementCodeCoverageData(data);
        } else if (layEvent === 'resetCodeCoverageData') {
            resetCodeCoverageData(data);
        } else if (layEvent === 'uploadExecFile') {
            uploadExecFile(data);
        } else if (layEvent === 'uploadClasses') {
            uploadClasses(data);
        }else if (layEvent === 'uploadFiles') {
            uploadFiles(data);
        }else if(layEvent === 'configScheduledTask'){
        	configCheduledTask(data);
        }
    });

    /**
     * 双击查看
     */
    table.on('rowDouble(codeCoverageListTable)',
    function(obj) {
        viewCodeCoverage(obj.data);
        // layer.msg("你想干啥！别瞎点好不好", {
        // time: 300
        // });
    });
});