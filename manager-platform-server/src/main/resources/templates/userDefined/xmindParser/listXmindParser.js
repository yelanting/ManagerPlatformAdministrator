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
    function getXmindParserList(){
    	var getXmindParserListUrl = '/xmindParser/getList';
    	var resultData = "";
        $.ajax({
        	url:getXmindParserListUrl,
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
    function getXmindParserListWithCondition(searchContent){
    	if(searchContent == "undefined" || searchContent == ""){
    		return getXmindParserList();
    	}else{
    		var resultData = "",
    		postData = {};
    		$.ajax({
    			url: "/xmindParser/searchList?searchContent=" + searchContent,
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
    function renderXmindParserTableDefault(){
    	var searchContent = "";
    	var xmindParserListData = getXmindParserListWithCondition(searchContent);
        table.reload("xmindParserListTable", {
        	page : {curr : 1},
        	data : xmindParserListData.data
        })
    }
    
    // 执行一个 table 实例
    var xmindParserTable = table.render({
        elem: '#xmindParserListTable',
        height: 400,
        // 数据接口
//        url : "/xmindParser/getList/",
//        method:"get",
        // 表头
        title: '方案解析',
        // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        toolbar: true,
        defaultToolbar: ["filter"],
        //是否显示加载条
        loading: true,
        //默认排序
        initSort: "xmindFileName",
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
	            field: 'xmindFileName',
	            title: '方案名称',
	            sort: true,
//	            width: 150
	        }, {
	            field: 'xmindFileUrl',
	            title: '方案地址',
	            sort: true,
//	            width: 220
	        },{
	            field: 'accessUrl',
	            title: '访问地址',
	            sort: true,
//	            width: 220
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
        data : getXmindParserList().data
    });

    // 监听头工具栏事件,批量删除操作
    $('#deleteInBatch').click(function() {
        var checkStatus = table.checkStatus('xmindParserListTable');
        var data = checkStatus.data;
        console.log(data);
        var xmindParserId = [];
        if (data.length > 0) {
            for (var eachDataIndex in data) {
                xmindParserId.push(data[eachDataIndex].id);
            }

            layer.confirm('确定删除选中的数据吗？', {
                icon: 3,
                title: '提示信息'
            }, function(index) {
                var url = "/xmindParser/deleteXmindParserInBatch";

                var postData = {
                    "ids": xmindParserId
                };

                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        setTimeout(function() {
                            top.layer.msg("批量删除成功！");
//                            xmindParserTable.reload();
                            renderXmindParserTableDefault()
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

    // 添加方案解析
    function addXmindParser() {
        layui.layer.open({
            title: "添加方案解析",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "addXmindParser.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    }

    // 编辑方案解析
    function editXmindParser(edit) {
        layui.layer.open({
            title: "修改方案解析",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "editXmindParser.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#xmindFileName").val(edit.xmindFileName);
                body.find("#accessUrl").val(edit.accessUrl);
                body.find("#xmindFileUrl").val(edit.xmindFileUrl);
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

    // 查看方案解析
    function viewXmindParser(edit) {
        layui.layer.open({
            title: "查看方案解析",
            type: 2,
            area: [widthPercent, heightPercent],
            content: "viewXmindParser.html",
            success: function(layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#id").val(edit.id);
                body.find("#xmindFileName").val(edit.xmindFileName);
                body.find("#accessUrl").val(edit.accessUrl);
                body.find("#xmindFileUrl").val(edit.xmindFileUrl);
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
    $("#addXmindParser_btn").click(function() {
        addXmindParser();
    });

    // 搜索方案解析
    // 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $("#searchXmindParser").on("click", function() {
        var searchContent = $("#serachXmindParserContent").val();
        var xmindParserListData = getXmindParserListWithCondition(searchContent);
        //搜索完之后清空搜索数据
        $("#serachXmindParserContent").val("")
        //表格重载
        table.reload("xmindParserListTable",{data : xmindParserListData.data});
    });

    //刷新功能
    $("#refreshXmindParserList").on("click", function() {
    	$("#serachXmindParserContent").val("");
    	renderXmindParserTableDefault();
    });
    
    // 监听行工具事件
    table.on('tool(xmindParserListTable)', function(obj) {
        // 注：tool
        // 是工具条事件名，test 是
        // table 原始容器的属性
        // lay-filter="对应的值"
        var data = obj.data;
        // 获得当前行数据
        var layEvent = obj.event;
        // 获得 lay-event 对应的值
        if (layEvent === 'detail') {
            viewXmindParser(data);
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function(index) {
                var url = "/xmindParser/deleteXmindParser";
                var postData = {
                    "id": data.id
                };
                $.post(url, postData, function(responseData) {
                    if (responseData.success == "true" || responseData.success) {
                        layer.msg('删除成功！');
                        obj.del();
                        renderXmindParserTableDefault();
                        // 删除对应行（tr）的DOM结构
                    } else {
                        layer.msg('删除失败：' + responseData.msg);
                    }
                });
                layer.close(index);
                // 向服务端发送删除指令
            });
        } else if (layEvent === 'downloadCsv') {
            downloadCsvFile(data);
        }else if(layEvent === 'downloadXmindFile'){
        	downloadXmindFile(data);
        }
    });
    
    table.on('rowDouble(xmindParserListTable)', function(obj){
    	layer.msg("你想干啥！别瞎点好不好",{time: 300});
    	});
});
