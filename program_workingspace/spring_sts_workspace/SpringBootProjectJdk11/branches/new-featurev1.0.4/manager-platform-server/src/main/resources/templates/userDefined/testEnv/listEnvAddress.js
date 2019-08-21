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
    function getEnvAddressList(){
    	var getEnvAddressListUrl = '/envAddress/getList';
    	var resultData = "";
        $.ajax({
        	url:getEnvAddressListUrl,
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
    function getEnvAddressListWithCondition(searchContent){
    	if(searchContent == "undefined" || searchContent == ""){
    		return getEnvAddressList();
    	}else{
    		var resultData = "",
    		postData = {};
    		$.ajax({
    			url: "/envAddress/searchList?searchContent=" + searchContent,
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
    function renderEnvAddressTableDefault(){
    	var searchContent = "";
    	var envAddressListData = getEnvAddressListWithCondition(searchContent);
        table.reload("envAddressListTable", {
        	page : {curr : 1},
        	data : envAddressListData.data
        })
    }
    
    // 执行一个 table 实例
    var envAddressTable = table.render({
        elem: '#envAddressListTable',
        height: 400,
        // 数据接口
//        url : "/envAddress/getList/",
//        method:"get",
        // 表头
        title: '测试环境地址',
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
	        ,{
	            field: 'projectName',
	            title: '项目名称',
	            sort: true,
//	            width: 150
	        }, {
	            field: 'serverUrl',
	            title: '访问地址',
	            sort: true,
//	            width: 220
	        },
	        {
	            field: 'username',
	            title: '用户名',
//	            width: 80
	        }, {
	            field: 'password',
	            title: '密码',
//	            width: 80
	        },
	        {
	            field: 'description',
	            title: '备注',
//	            width: 100
	        }, {
	            fixed: 'right',
	            align: 'center',
	            title: '操作',
	            toolbar: '#operationBar',
//	            width: 300
	        }]],
        data : getEnvAddressList().data
    });

    // 监听头工具栏事件,批量删除操作
    $('#deleteInBatch').click(function() {
        var checkStatus = table.checkStatus('envAddressListTable');
        var data = checkStatus.data;
        console.log(data);
        var envAddressId = [];
        if (data.length > 0) {
            for (var eachDataIndex in data) {
                envAddressId.push(data[eachDataIndex].id);
            }

            layer.confirm('确定删除选中的数据吗？', {
                icon: 3,
                title: '提示信息'
            }, function(index) {
                var url = "/envAddress/deleteEnvAddressInBatch";

                var postData = {
                    "ids": envAddressId
                };

                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        setTimeout(function() {
                            top.layer.msg("批量删除成功！");
//                            envAddressTable.reload();
                            renderEnvAddressTableDefault()
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

    // 添加测试环境地址
    function addEnvAddress() {
        layui.layer.open({
            title: "添加测试环境地址",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "addEnvAddress.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    // 编辑测试环境地址
    function editEnvAddress(edit) {
        layui.layer.open({
            title: "修改测试环境地址",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "editEnvAddress.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#projectName").val(edit.projectName);
                body.find("#serverUrl").val(edit.serverUrl);
                body.find("#username").val(edit.username);
                body.find("#password").val(edit.password);
                body.find("#createDate").val(edit.createDate);
                body.find("#updateDate").val(edit.updateDate);
                body.find("#description").val(edit.description);
                form.render();
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    // 查看测试环境地址
    function viewEnvAddress(edit) {
        layui.layer.open({
            title: "查看测试环境地址",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "viewEnvAddress.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#projectName").val(edit.projectName);
                body.find("#serverUrl").val(edit.serverUrl);
                body.find("#username").val(edit.username);
                body.find("#password").val(edit.password);
                body.find("#createDate").val(edit.createDate);
                body.find("#updateDate").val(edit.updateDate);
                body.find("#description").val(edit.description);
                form.render();
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }
    $("#addEnvAddress_btn").click(function() {
        addEnvAddress();
    });

    // 搜索测试环境地址
    // 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $("#searchEnvAddress").on("click", function() {
        var searchContent = $("#serachEnvAddressContent").val();
        var envAddressListData = getEnvAddressListWithCondition(searchContent);
        //搜索完之后清空搜索数据
        $("#serachEnvAddressContent").val("")
        //表格重载
        table.reload("envAddressListTable",{data : envAddressListData.data});
    });

    //刷新功能
    $("#refreshEnvAddressList").on("click", function() {
    	$("#serachEnvAddressContent").val("");
    	renderEnvAddressTableDefault();
    });
    
    // 测试环境地址跳转
    function goToEnvAddress(edit) {
    	var toUrl = edit.serverUrl;
		if (toUrl == "" || toUrl.trim() == "" ) {
			layer.msg("服务器地址为空，无法实现跳转！！！");
			return false;
		}else if(toUrl.trim().indexOf("http") !=0){
			layer.msg("链接有些不对呢，去检查下格式吧！！！");
			return false;
		}else{
			window.open(toUrl.trim());
		}
    }
    
    // 监听行工具事件
    table.on('tool(envAddressListTable)', function(obj) {
        // 注：tool
        // 是工具条事件名，test 是
        // table 原始容器的属性
        // lay-filter="对应的值"
        var data = obj.data;
        // 获得当前行数据
        var layEvent = obj.event;
        // 获得 lay-event 对应的值
        if (layEvent === 'detail') {
            viewEnvAddress(data);
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function(index) {
                var url = "/envAddress/deleteEnvAddress";
                var postData = {
                    "id": data.id
                };
                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        layer.msg('删除成功！');
                        obj.del();
                        renderEnvAddressTableDefault();
                        // 删除对应行（tr）的DOM结构
                    } else {
                        layer.msg('删除失败：' + responseData.msg);
                    }
                });
                layer.close(index);
                // 向服务端发送删除指令
            });
        } else if (layEvent === 'edit') {
            editEnvAddress(data);
        }else if (layEvent === 'go') {
            goToEnvAddress(data);
        }
    });
    
    table.on('rowDouble(envAddressListTable)', function(obj){
    	layer.msg("你想干啥！别瞎点好不好",{time: 300});
    	});
});
