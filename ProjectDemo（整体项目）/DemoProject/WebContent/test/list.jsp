<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id = "test-list" class="easyui-panel  easyui-panel-style" data-options="title: '测试'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="testList-grid-toolbar">
				<table id="test-tool-table" style = "width:100%;">
					<%-- <tr class = "datagrid-header-row" style = "height:30px"><td colspan = "10" >
							<shiro:hasPermission name="M_TEST_MANAGER:ADD">
								<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls: 'icon-add'" style="margin: 2px 2px; height: 26px;width:70px">新增</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="M_TEST_MANAGER:UPDATE">
								<a href="javascript:void(0);" class="easyui-linkbutton"  data-options="iconCls: 'icon-edit'" style="margin: 2px 2px; height: 26px;width:70px">修改</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="M_TEST_MANAGER:DELETE">
								<a href="javascript:void(0);" class="easyui-linkbutton"  data-options="iconCls: 'icon-remove'" style="margin: 2px 2px; height: 26px;width:70px">删除</a>
							</shiro:hasPermission>
						</td>
					</tr> --%>
				</table>
		</div>
		<table id="test-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#test-list #test-grid").datagrid({
			toolbar : "#testList-grid-toolbar",
			url : basePath+"/test/list.do",
			method : "post",
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			}, {
				field : "name",
				title : "名称",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "scription",
				title : "脚本",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}] ]
		}).datagrid("initSearch",{
			columns:[{field:"name",title:"姓名",type:"text"},{field:"scription",title:"脚本",type:"date"},
					{field:"name",title:"名称",type:"text"},{field:"scription",title:"脚本",type:"date"},
					{field:"name",title:"名称",type:"text"},{field:"scription",title:"脚本",type:"date"},
					{field:"name",title:"名称",type:"text"},{field:"scription",title:"脚本",type:"date"},
					{field:"name",title:"名称",type:"text"},{field:"scription",title:"脚本",type:"date"},
					{field:"name",title:"名称",type:"text"},{field:"scription",title:"脚本",type:"date"}],
			tools:[{type:"CLEAR"},
				   {type:"ADD",width:600,height:400,url:basePath+"/test/add.jsp"},
				   {type:"UPDATE" ,width:600,height:400,url:basePath+"/test/view.do"},
				   {type:"DELETE",url:basePath+"/test/delete.do"},
				   {type:"QUERY"},
				   {type:"CHECK",title:"审核",icon:"icon-check",fn:function(){
					   alert("这是自定义审核的事件");
				   }}],
			module:"M_TEST_MANAGER",
			shownum:5
		})

	})
	
	</script>
</body>
</html>