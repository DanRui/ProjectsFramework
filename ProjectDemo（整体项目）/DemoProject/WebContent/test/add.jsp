<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test-add</title>
</head>
<body>
	<form action="test/save.do"  method="post"  enctype="multipart/form-data">
		<div class="datagrid-header" id="test-add-table-toolbar" style="width: 100%;">
			<table class="datagrid-htable" border="0" cellspacing="0" cellpadding="0" style="width: 100%; padding-top:4px">
				<tr  class="datagrid-header-row"  style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<tr  class="datagrid-header-row"  style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;" >名称：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "name" class = "textbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<tr class="datagrid-header-row"   style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;">脚本</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "scription" class = "textbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<!-- <tr>
					<td style = "padding-rigth: 5px;text-align: right;">附件</td>
					<td style = "padding-left: 5px; text-align: left;"><input type = "file" name = "file"/></td>
				</tr> -->
				<tr></tr>
				<tr  class="datagrid-header-row"  style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<tr  class="datagrid-header-row"  style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;" >名称：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "name" class = "textbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<tr class="datagrid-header-row"   style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;">脚本</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "scription" class = "textbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<!-- <tr>
					<td style = "padding-rigth: 5px;text-align: right;">附件</td>
					<td style = "padding-left: 5px; text-align: left;"><input type = "file" name = "file"/></td>
				</tr> -->
				<tr></tr>
				<tr  class="datagrid-header-row"  style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<tr  class="datagrid-header-row"  style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;" >名称：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "name" class = "textbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
				<tr class="datagrid-header-row"   style = "height:35px">
					<td style = "padding-right: 5px;text-align: right;">脚本</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "scription" class = "textbox"/></td>
					<td style = "padding-right: 5px;text-align: right;" >ID：</td>
					<td style = "padding-left: 5px;text-align: left;"><input type = "text" name = "id" class = "numberbox"/></td>
				</tr>
			
				
			</table>
		</div>
	</form>
</div>
</body>
</html>