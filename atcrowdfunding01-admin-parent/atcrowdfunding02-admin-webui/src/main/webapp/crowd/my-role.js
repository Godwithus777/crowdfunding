// 执行分页,生成页面效果,任何时候调用这个函数都会重新加载页面
function generatePage() {

    // 1.获取分页数据
    var pageInfo = getPageInfoRemote();

    // 2.填充表格

    fillTableBody(pageInfo);

}

// 远程访问服务端程序获取pageInfo数据
function getPageInfoRemote() {

    var ajaxResult = $.ajax({
        "url":"role/get/page/info.json",
        "type":"post",
        "data":{
            "pageNum":window.pageNum,
            "pageSize":window.pageSize,
            "keyword":window.keyword
        },
        "async":false,
        "dataType":"json"
    });

    console.log(ajaxResult);

    var statusCode = ajaxResult.status;

    if (statusCode != 200) {
        layer.msg("失败!响应状态码="+statusCode+"说明信息="+ajaxResult.statusText);
        return null;
    }

    var resultEntity = ajaxResult.responseJSON;


    var result = resultEntity.result;

    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    var pageInfo = resultEntity.data;

    return pageInfo;

}

// 填充表格
function fillTableBody(pageInfo) {





}