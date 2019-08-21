/**
 * 
 */
layui.config({// version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui.use(['laypage', 'layer', 'table', 'element', 'form'], function() {
    var form = layui.form
      , layer = parent.layer === undefined ? layui.layer : top.layer
      , $ = layui.jquery
      , laydate = layui.laydate
      , laytpl = layui.laytpl
      , table = layui.table
      , laypage = layui.laypage
      , element = layui.element;
    // 元素操作

    var widthPercent = "70%"
      , heightPercent = "90%";
    
    /**
     * 获取所有数据
     */
    function getDubboTestList(){
    	var getDubboTestListUrl = '/dubboTest/getList';
    	var resultData = "";
        $.ajax({
        	url:getDubboTestListUrl,
        	async:false,
        	method:"get",
        	dataType:"json",
        	success:function(result){
        		resultData = result;
        }});
        return resultData;
    }
    
    /**
     * 条件搜索
     */
    function getDubboTestListWithCondition(searchContent){
    	if(searchContent == "undefined" || searchContent == ""){
    		return getDubboTestList();
    	}else{
    		var resultData = "",
    		postData = {};
    		$.ajax({
    			url: "/dubboTest/searchList?searchContent=" + searchContent,
                method: "get",
        		data : postData ,
        		async : false ,
        		dataType : "json",
        		success:function(result){
        			resultData = result;
        		}
        	});
    		return resultData;
    	}
    }
    
    /**
     * 刷新表格
     */
    function renderDubboTestTableDefault(){
    	var searchContent = "";
    	var dubboTestListData = getDubboTestListWithCondition(searchContent);
        table.reload("dubboTestListTable", {
        	page : {curr : 1},
        	data : dubboTestListData.data
        })
    }
    
    // 执行一个 table 实例
    var dubboTestTable = table.render({
        elem: '#dubboTestListTable',
        height: 400,
        // 数据接口
//        url : "/dubboTest/getList/",
//        method:"get",
        // 表头
        title: 'dubbo接口测试',
        // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        toolbar: true,
        defaultToolbar: ["filter"],
        //是否显示加载条
        loading: true,
        //默认排序
        initSort: "projectName",
        even: true,
        // 最窄宽度
        cellMinWidth: 60,
        size:"lg",
        page:{
        	layout: ['prev', 'page', 'next', 'limit', 'count', 'skip'],
	        prev: "上一页" ,
	        next: "下一页" ,
	        first: "首页",
	        last: "尾页",
	        //每页条数的选择项
	        limits: [5, 10, 20, 30, 40, 50, 100],
	        limit : 5,
	        groups: 3,
	        skin: '#1E9FFF',
	        // 自定义选中色值
	        skip: true,
        },
        text: {
            none: '暂无相关数据'// 默认：无数据。
        },
        // 表头
        cols: [[
        	{
	            type: 'checkbox',
	            fixed: 'left',
	            unresize :true
	        },{
            	type:'numbers',
            	title:"序号",
            	fixed:"left",
            	unresize :true
            }
	        /*,{
	            field: 'id',
	            title: 'ID',
	            sort: true,
	            width: 50
	        }*/
	        ,{
	            field: 'caseName',
	            title: '用例名称',
	            sort: true,
//	            width: 150
	        }, {
	            field: 'protocolName',
	            title: '协议地址',
	            sort: true,
//	            width: 220
	        },
	        {
	            field: 'methodName',
	            title: '方法名称',
	            sort: true,
//	            width: 200
	        }, {
	            field: 'interfaceName',
	            title: '接口名称',
	            sort: true,
//	            width: 170
	        }, 
	        {
	            field: 'incomeParams',
	            title: '入参',
//	            width: 80
	        }, {
	            field: 'dubboContextParams',
	            title: '上下文参数',
//	            width: 80
	        },
	        {
	            field: 'caseDesc',
	            title: '备注',
//	            width: 100
	        }, {
	            fixed: 'right',
	            align: 'center',
	            title: '操作',
	            toolbar: '#operationBar',
	            width: 200
	        }]],
        data : getDubboTestList().data
    });

    // 监听头工具栏事件,批量删除操作
    $('#deleteInBatch').click(function() {
        var checkStatus = table.checkStatus('dubboTestListTable');
        var data = checkStatus.data;
        console.log(data);
        var dubboTestId = [];
        if (data.length > 0) {
            for (var eachDataIndex in data) {
                dubboTestId.push(data[eachDataIndex].id);
            }

            layer.confirm('确定删除选中的数据吗？', {
                icon: 3,
                title: '提示信息'
            }, function(index) {
                var url = "/dubboTest/deleteDubboTestInBatch";

                var postData = {
                    "ids": dubboTestId
                };

                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        setTimeout(function() {
                            top.layer.msg("批量删除成功！");
                            renderDubboTestTableDefault()
                            layer.close(index);
                        }, 500);
                    } else {
                        layer.msg('批量删除失败：' + responseData.msg);
                    }
                });

            })
        } else {
            layer.msg("请选择需要删除的数据");
        }
    });

    // 添加dubbo接口测试
    function addDubboTest() {
        layui.layer.open({
            title: "添加dubbo接口测试",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "addDubboTest.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    // 编辑dubbo接口测试
    function editDubboTest(edit) {
        layui.layer.open({
            title: "修改dubbo接口测试",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "editDubboTest.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#caseName").val(edit.caseName);
                body.find("#protocolName").val(edit.protocolName);
                body.find("#address").val(edit.address);
                body.find("#groupName").val(edit.groupName);
                body.find("#createDate").val(edit.createDate);
                body.find("#updateDate").val(edit.updateDate);
                body.find("#methodName").val(edit.methodName);
                body.find("#client").val(edit.client);
                body.find("#version").val(edit.version);
                body.find("#incomeParams").val(edit.incomeParams);
                body.find("#dubboTestResponse").val(edit.dubboTestResponse);
                body.find("#dubboTestCheck").val(edit.dubboTestCheck);
                body.find("#dubboContextParams").val(edit.dubboContextParams);
                body.find("#dubboTestResult").val(edit.dubboTestResult);
                body.find("#caseDesc").val(edit.caseDesc);
                form.render();
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    // 查看dubbo接口测试
    function viewDubboTest(edit) {
        layui.layer.open({
            title: "查看dubbo接口测试",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "viewDubboTest.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#caseName").val(edit.caseName);
                body.find("#protocolName").val(edit.protocolName);
                body.find("#address").val(edit.address);
                body.find("#groupName").val(edit.groupName);
                body.find("#createDate").val(edit.createDate);
                body.find("#updateDate").val(edit.updateDate);
                body.find("#methodName").val(edit.methodName);
                body.find("#client").val(edit.client);
                body.find("#version").val(edit.version);
                body.find("#incomeParams").val(edit.incomeParams);
                body.find("#dubboTestResponse").val(edit.dubboTestResponse);
                body.find("#dubboTestCheck").val(edit.dubboTestCheck);
                body.find("#dubboContextParams").val(edit.dubboContextParams);
                body.find("#dubboTestResult").val(edit.dubboTestResult);
                body.find("#caseDesc").val(edit.caseDesc);
                form.render();
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }
    $("#addDubboTest_btn").click(function() {
        addDubboTest();
    });

    // 搜索dubbo接口测试
    // 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $("#searchDubboTest").on("click", function() {
        var searchContent = $("#serachDubboTestContent").val();
        var dubboTestListData = getDubboTestListWithCondition(searchContent);
        //搜索完之后清空搜索数据
        $("#serachDubboTestContent").val("")
        //表格重载
        table.reload("dubboTestListTable",{data : dubboTestListData.data});
    });

    //刷新功能
    $("#refreshDubboTestList").on("click", function() {
    	$("#serachDubboTestContent").val("");
//    	var searchContent = "";
//    	var dubboTestListData = getdubboTestListWithCondition(searchContent);
//        table.reload("dubboTestListTable", {
//        	page : {curr : 1},
//        	data : dubboTestListData.data
//        })
    	renderDubboTestTableDefault();
    });
    
    // dubbo接口测试跳转
    function goToDubboTest(edit) {
    	var toUrl = edit.protocolName;
		if (toUrl == "" || toUrl.trim() == "") {
			layer.msg("服务器地址为空或者不合法，无法实现跳转！！！");
		}else{
			window.open(toUrl);
		}
    }
    
    // 监听行工具事件
    table.on('tool(dubboTestListTable)', function(obj) {
        // 注：tool
        // 是工具条事件名，test 是
        // table 原始容器的属性
        // lay-filter="对应的值"
        var data = obj.data;
        // 获得当前行数据
        var layEvent = obj.event;
        // 获得 lay-event 对应的值
        if (layEvent === 'detail') {
            viewDubboTest(data);
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function(index) {
                var url = "/dubboTest/deleteDubboTest";
                var postData = {
                    "id": data.id
                };
                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        layer.msg('删除成功！');
                        obj.del();
                        renderDubboTestTableDefault();
                        // 删除对应行（tr）的DOM结构
                    } else {
                        layer.msg('删除失败：' + responseData.msg);
                    }
                });
                layer.close(index);
                // 向服务端发送删除指令
            });
        } else if (layEvent === 'edit') {
            editDubboTest(data);
        }else if (layEvent === 'go') {
            goToDubboTest(data);
        }
    });
    
    table.on('rowDouble(dubboTestListTable)', function(obj){
    	layer.msg("你想干啥！别瞎点好不好",{time: 300});
    	});
});
