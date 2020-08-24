<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/8/23 0023
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function () {

        // 1.为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 2.调用执行分页的函数，显示分页效果
        generatePage();

        // 3.给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {

            // ①获取关键字数据赋值给对应的全局变量
            window.keyword = $("#keywordInput").val();

            // ②调用分页函数刷新页面
            generatePage();

        });

        // 4.点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {

            $("#addModal").modal("show");

        });

        // 5.给新增模态框中的保存按钮绑定单击响应函数
        $("#saveRoleBtn").click(function () {

            // ①获取用户在文本输入的角色名称
            // #addModal表示找到整个模态框
            // 空格表示在后代元素中继续查找
            // [name=roleName]表示匹配name属性等于roleName的元素
            var roleName = $.trim($("#addModal [name=roleName]").val());


            //②发送ajax请求
            $.ajax({
                "url": "role/save.json",
                "type": "post",
                "data": {
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {

                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功!");

                        // 将页码定位到最后一页
                        window.pageNum = 99999999;

                        // 重新加载分页数据
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }

                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);

                }
            });

            // 关闭模态框
            $("#addModal").modal("hide");

            // 清理模态框
            $("#addModal [name=roleName]").val("");

        })

    })

</script>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>

</body>
</html>