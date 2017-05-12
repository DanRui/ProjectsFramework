<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
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
	<div id = "classrecord-list" class="easyui-panel easyui-panel-style" data-options="title: '测试'" style = "height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="classrecord-list-grid-toolbar">
				<table id="classrecord-tool-table">
				</table>
		</div>
		<table id="classrecord-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#classrecord-list #classrecord-grid").datagrid({
			toolbar : "#classrecord-list-grid-toolbar",
			url : basePath+"/classrecord/list.do",
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			}, {
				field : "insname",
				title : "培训机构",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "stuname",
				title : "学员姓名",
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "idcard",
				title : "学员身份证号码",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "coachname",
				title : "教练员",
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "carcode",
				title : "教练车车牌号码",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "simunum",
				title : "计时终端编号",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "recnum",
				title : "教学日志编号",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "starttime",
				title : "开始时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				formatter:function(value, row ,index){
					var day = new Date(value.time);
					return getNowFormatDate(day,true);
				}
			}, {
				field : "endtime",
				title : "结束时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				formatter:function(value, row ,index){
					var day = new Date(value.time);
					return getNowFormatDate(day,true);
				}
			}, {
				field : "total",
				title : "总累计学时",
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "part1",
				title : "第一部分",
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "part2",
				title : "第二部分",
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "part3",
				title : "第三部分",
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true
			}, {
				field : "part4",
				title : "第四部分",
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true
			}] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:820,height:550,url:basePath+"/classrecord/view.do?id="+rowData.id});
			}
		}).datagrid("initSearch",{
			columns:[{field:"inscode",title:"企业名称：",url:basePath+"/data/corp.json",text:"insname",value:"inscode",type:"combobox"},
					{field:"stuname",title:"学员姓名：",type:"text"},
					{field:"idcard",title:"学员身份号码：",type:"text"},
					{field:"carcode",title:"教练车车牌号码：",type:"text"},
					{field:"starttime",title:"培训开始时间：",type:"datetime"},
					{field:"endtime",title:"培训结束时间：",type:"date"}],
			tools:[{type:"DELETE",url:basePath+"/classrecord/delete.do"},
				   {type:"CLEAR"},
				   {type:"QUERY"}],
			module:"M_CLASS_RECORD_MANAGER",
			shownum:4
		}).datagrid("columnMoving").datagrid("columnHiding");
	
	})
	
	</script>
</body>
</html>