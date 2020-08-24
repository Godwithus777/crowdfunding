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
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async": false,
        "dataType": "json"
    });

    console.log(ajaxResult);

    var statusCode = ajaxResult.status;

    if (statusCode != 200) {
        layer.msg("失败!响应状态码=" + statusCode + "说明信息=" + ajaxResult.statusText);
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


    // 清除tbody中的旧的内容
    $("#rolePageBody").empty();

    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td>")

        return;
    }

    // 使用pageInfo的list属性填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {

        var role = pageInfo.list[i];

        var roleId = role.id;

        var roleName = role.name;

        var numberId = "<td>" + (i + 1) + "</td>";
        var checkboxId = "<td><input type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";

        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";

        var tr = "<tr>" + numberId + checkboxId + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);

    }

    // 生成分页导航条
    generateNavigator(pageInfo);

}

// 生成分页页码导航条
function generateNavigator(pageInfo) {

    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }

    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {

    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();

    // 取消页码超链接的默认行为
    return false;

}