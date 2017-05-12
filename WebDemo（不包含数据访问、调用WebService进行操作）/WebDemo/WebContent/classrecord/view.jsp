<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored = "false" %>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test-update</title>
</head>
<body>
		<div class="datagrid-header datagrid-height-default" id="classrecord-view-toolbar">
			
		<div id="aa" class="easyui-accordion" data-options="fit:true" >   
			    <div title="基础信息" >   
			        <table class=" datagrid-table-s datagrid-htable">
						<tr  class="datagrid-header-row" >
							<td class = "view_table_left" >课堂编号：</td>
							<td class = "view_table_right" colspan = "3">${v.classid}</td>
						</tr>
						<tr class = "datagrid-head-row">
							<td class = "view_table_left" >培训机构代码：</td>
							<td class = "view_table_right">${v.inscode }</td>
							<td class = "view_table_left">培训机构名称：</td>
							<td class = "view_table_right">${v.insname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">学员编号：</td>
							<td class = "view_table_right">${v.stunum }</td>
							<td class = "view_table_left">学员姓名：</td>
							<td class = "view_table_right">${v.stuname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">学员身份证号码：</td>
							<td class = "view_table_right">${v.idcard }</td>
							<td class = "view_table_left">教练员编号：</td>
							<td class = "view_table_right">${v.coachnum}</td>
						</tr>
						<tr class = "datagrid-head-row">
							<td class = "view_table_left" >培训机构代码：</td>
							<td class = "view_table_right">${v.inscode }</td>
							<td class = "view_table_left">培训机构名称：</td>
							<td class = "view_table_right">${v.insname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">学员编号：</td>
							<td class = "view_table_right">${v.stunum }</td>
							<td class = "view_table_left">学员姓名：</td>
							<td class = "view_table_right">${v.stuname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">学员身份证号码：</td>
							<td class = "view_table_right">${v.idcard }</td>
							<td class = "view_table_left">教练员编号：</td>
							<td class = "view_table_right">${v.coachnum}</td>
						</tr>
						<tr class = "datagrid-head-row">
							<td class = "view_table_left" >培训机构代码：</td>
							<td class = "view_table_right">${v.inscode }</td>
							<td class = "view_table_left">培训机构名称：</td>
							<td class = "view_table_right">${v.insname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">学员编号：</td>
							<td class = "view_table_right">${v.stunum }</td>
							<td class = "view_table_left">学员姓名：</td>
							<td class = "view_table_right">${v.stuname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">学员身份证号码：</td>
							<td class = "view_table_right">${v.idcard }</td>
							<td class = "view_table_left">教练员编号：</td>
							<td class = "view_table_right">${v.coachnum}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">教练员名称：</td>
							<td class = "view_table_right">${v.coachname }</td>
							<td class = "view_table_left">教练车编号：</td>
							<td class = "view_table_right">${v.carnum}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">教练车车牌号码：</td>
							<td class = "view_table_right">${v.carcode }</td>
							<td class = "view_table_left">计时终端号码：</td>
							<td class = "view_table_right">${v.simunum}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">教学日志编号：</td>
							<td class = "view_table_right">${v.subjcode }</td>
							<td class = "view_table_left">课程方式：</td>
							<td class = "view_table_right">${v.subjname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">培训部分：</td>
							<td class = "view_table_right">${v.partname }</td>
							<td class = "view_table_left">培训项目：</td>
							<td class = "view_table_right">${v.projectname}</td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">培训开始时间：</td>
							<td class = "view_table_right"><fmt:formatDate value="${v.starttime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td class = "view_table_left">培训结束时间：</td>
							<td class = "view_table_right"><fmt:formatDate value="${v.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</table>  
			    </div>   
			    <div title="图片信息"  class = "accordion-center">   
			    	<dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo1}"/></dt>
			    		<dd class = "dl_title">签到照片</dd>
			    	</dl>
			       <dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo2}"/></dt>
			    		<dd class = "dl_title">随机照片</dd>
			    	</dl>
			    	<dl  class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo3}"/></dt>
			    		<dd class = "dl_title">签退照片</dd>
			    	</dl>
			    	<dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo1}"/></dt>
			    		<dd class = "dl_title">签到照片</dd>
			    	</dl>
			       <dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo2}"/></dt>
			    		<dd class = "dl_title">随机照片</dd>
			    	</dl>
			    	<dl  class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo3}"/></dt>
			    		<dd class = "dl_title">签退照片</dd>
			    	</dl>
			    	<dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo1}"/></dt>
			    		<dd class = "dl_title">签到照片</dd>
			    	</dl>
			       <dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo2}"/></dt>
			    		<dd class = "dl_title">随机照片</dd>
			    	</dl>
			    	<dl  class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo3}"/></dt>
			    		<dd class = "dl_title">签退照片</dd>
			    	</dl>
			    	<dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo1}"/></dt>
			    		<dd class = "dl_title">签到照片</dd>
			    	</dl>
			       <dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo2}"/></dt>
			    		<dd class = "dl_title">随机照片</dd>
			    	</dl>
			    	<dl  class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo3}"/></dt>
			    		<dd class = "dl_title">签退照片</dd>
			    	</dl>
			    	<dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo1}"/></dt>
			    		<dd class = "dl_title">签到照片</dd>
			    	</dl>
			       <dl class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo2}"/></dt>
			    		<dd class = "dl_title">随机照片</dd>
			    	</dl>
			    	<dl  class = "dl-float-left">
			    		<dt><img class = "img-default-250" src="${v.photo3}"/></dt>
			    		<dd class = "dl_title">签退照片</dd>
			    	</dl>
			    </div>   
			    <div title="轨迹信息" >   
			        <table class="datagrid-htable  datagrid-table-s" >
						<tr  class="datagrid-header-row" >
							<td class = "view_table_left" >ID：</td>
							<td class = "view_table_right"><input type = "text" name = "id" class = "numberbox"/></td>
						</tr>
						<tr  class="datagrid-header-row" >
							<td class = "view_table_left" >名称：</td>
							<td class = "view_table_right"><input type = "text" name = "name" class = "textbox"/></td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">脚本</td>
							<td class = "view_table_right"><input type = "text" name = "scription" class = "textbox"/></td>
						</tr>
						<tr class="datagrid-header-row"  >
							<td class = "view_table_left">附件</td>
							<td class = "view_table_right"><input type = "file" name = "file"/></td>
						</tr>
						<tr></tr>
					</table>  
		    </div>
		</div>
</body>
</html>