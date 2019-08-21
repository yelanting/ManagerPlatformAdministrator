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
    function getManuList(){
    	var getManuListUrl = '/menu/getList';
    	var resultData = "";
        $.ajax({
        	url:getManuListUrl,
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
    function getManuListWithCondition(searchContent){
    	if(searchContent == "undefined" || searchContent == ""){
    		return getManuList();
    	}else{
    		var resultData = "",
    		postData = {};
    		$.ajax({
    			url: "/menu/searchList?searchContent=" + searchContent,
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
    function renderManuTableDefault(){
    	var searchContent = "";
    	var menuListData = getManuListWithCondition(searchContent);
        table.reload("menuListTable", {
        	page : {curr : 1},
        	data : menuListData.data
        })
    }
    
    // 执行一个 table 实例
    var menuTable = table.render({
        elem: '#menuListTable',
        height: 400,
        // 数据接口
//        url : "/menu/getList/",
//        method:"get",
        // 表头
        title: '菜单',
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
        data : getManuList().data
    });

    // 监听头工具栏事件,批量删除操作
    $('#deleteInBatch').click(function() {
        var checkStatus = table.checkStatus('menuListTable');
        var data = checkStatus.data;
        console.log(data);
        var menuId = [];
        if (data.length > 0) {
            for (var eachDataIndex in data) {
                menuId.push(data[eachDataIndex].id);
            }

            layer.confirm('确定删除选中的数据吗？', {
                icon: 3,
                title: '提示信息'
            }, function(index) {
                var url = "/menu/deleteManuInBatch";

                var postData = {
                    "ids": menuId
                };

                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        setTimeout(function() {
                            top.layer.msg("批量删除成功！");
//                            menuTable.reload();
                            renderManuTableDefault()
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

    // 添加菜单
    function addManu() {
        layui.layer.open({
            title: "添加菜单",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "addManu.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    // 编辑菜单
    function editManu(edit) {
        layui.layer.open({
            title: "修改菜单",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "editManu.html",
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

    // 查看菜单
    function viewManu(edit) {
        layui.layer.open({
            title: "查看菜单",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "viewManu.html",
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
    $("#addManu_btn").click(function() {
        addManu();
    });

    // 搜索菜单
    // 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $("#searchManu").on("click", function() {
        var searchContent = $("#serachManuContent").val();
        var menuListData = getManuListWithCondition(searchContent);
        //搜索完之后清空搜索数据
        $("#serachManuContent").val("")
        //表格重载
        table.reload("menuListTable",{data : menuListData.data});
    });

    //刷新功能
    $("#refreshManuList").on("click", function() {
    	$("#serachManuContent").val("");
    	renderManuTableDefault();
    });
    
    // 菜单跳转
    function goToManu(edit) {
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
    table.on('tool(menuListTable)', function(obj) {
        // 注：tool
        // 是工具条事件名，test 是
        // table 原始容器的属性
        // lay-filter="对应的值"
        var data = obj.data;
        // 获得当前行数据
        var layEvent = obj.event;
        // 获得 lay-event 对应的值
        if (layEvent === 'detail') {
            viewManu(data);
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function(index) {
                var url = "/menu/deleteManu";
                var postData = {
                    "id": data.id
                };
                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        layer.msg('删除成功！');
                        obj.del();
                        renderManuTableDefault();
                        // 删除对应行（tr）的DOM结构
                    } else {
                        layer.msg('删除失败：' + responseData.msg);
                    }
                });
                layer.close(index);
                // 向服务端发送删除指令
            });
        } else if (layEvent === 'edit') {
            editManu(data);
        }else if (layEvent === 'go') {
            goToManu(data);
        }
    });
    
    table.on('rowDouble(menuListTable)', function(obj){
    	layer.msg("你想干啥！别瞎点好不好",{time: 300});
    	});
});
