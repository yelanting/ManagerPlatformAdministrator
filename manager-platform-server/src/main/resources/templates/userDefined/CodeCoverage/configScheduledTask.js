/**
 *
 */
layui.config({ // version : '1535898708509' // 为了更新 js 缓存，可忽略
});

layui
.use(
    ['laypage', 'layer', 'table', 'element', 'form', 'upload'],
    function () {
    var form = layui.form;
    var layer = (parent.layer === undefined ? layui.layer
         : top.layer);
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laypage = layui.laypage;
    var upload = layui.upload;
    var element = layui.element;
    // 元素操作
    var widthPercent = "70%",
    heightPercent = "90%";

    /**
     * 获取页面的id
     *
     */
    function getParentId() {
        let searchContent = window.location.search;
        let targetId = getValueOfKeyFromWindowLocationSearch(
                "id", searchContent);
        return targetId;
    }

    /**
     * 获取详情
     */

    function getCodeCoverageDetail() {
        let targetId = getParentId();
        let detail = getCurrentTableData(targetId);

        console.log(detail);
        return detail.data;
    }

    var detail = getCodeCoverageDetail();
    proccessObject(detail);

    /**
     * 获取cron表达式
     */
    function getCronExpress() {
        let formData = $("#addScheduledTask").serialize();
        let formDataObject = serializeToObject(formData);
        let cronExpress = "";
        cronExpress += decodeURIComponent(formDataObject['second'])
         + " ";
        cronExpress += decodeURIComponent(formDataObject['minute'])
         + " ";
        cronExpress += decodeURIComponent(formDataObject['hour'])
         + " ";
        cronExpress += decodeURIComponent(formDataObject['dayOfMonth'])
         + " ";
        cronExpress += decodeURIComponent(formDataObject['month'])
         + " ";
        cronExpress += decodeURIComponent(formDataObject['dayOfWeek'])
         + " ";
        cronExpress += decodeURIComponent(formDataObject['year']);
        return cronExpress;
    }
    /**
     * 检测时，把生成的表达式写到后方
     */
    $("#checkCron").click(function () {
        $("#scheduledConfigExpress").html(getCronExpress());
    })

    /**
     * 监听提交按钮
     */
    form
    .on(
        "submit(configScheduleTaskSubmit)",
        function (data) {
        // 弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });

        var postDataSerialize = $(
                "#addScheduledTask")
            .serialize();
        postDataSerialize = decodeURIComponent(postDataSerialize);
        let postData = serializeToObject(postDataSerialize);
        if (!postData['enableScheduledCollectingExec']) {
            postData['enabled'] = false;
        } else {
            postData['enabled'] = (postData['enableScheduledCollectingExec'] == "yes"?true:false);
        }
        postData['scheduledConfigExpress'] = getCronExpress();

        // 实际使用时的提交信息
        $
        .post(
            "/codeCoverage/configTimerTaskAndChangeStatus?id="
             + getParentId(),
            postData,
            function (response) {
            if (response.success == "false"
                 || !response.success) {
                if (response.msg != "null") {
                    top.layer
                    .msg("配置失败:"
                         + response.msg);
                } else {
                    top.layer
                    .msg("配置失败,原因未知！");
                }
            } else {
                setTimeout(
                    function () {
                    top.layer
                    .close(index);
                    top.layer
                    .msg("配置成功！");
                    layer
                    .closeAll("iframe");
                    // 刷新父页面
                    parent.location
                    .reload();
                }, 500);
            }
        });
        return false;
    })
});
