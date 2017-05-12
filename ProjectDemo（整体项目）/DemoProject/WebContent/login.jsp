<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String basePath = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上港货局</title>
<link rel="shortcut icon" href="<%=basePath%>/images/webSite/login/logo.png" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/login.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/encoder.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/login.js"></script>
</head>
<body>
<div id="back_login">
	<div class="login">
		<form action="login/login.do" method="post">
			<p>
				<span>用户名：</span>
				<input type="text" name="username" autocomplete="off" class = "login-input"/>
			</p>
			
			<p>
				<span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
				<input type="password" name="password" autocomplete="off" class = "login-input"/>
			</p>
			
			<p>
				<span>校验码：</span>
				<input type="text" name="verifycode" autocomplete="off"  class="pass"/>
				<img src="<%=basePath %>/login/getCode.do">
			</p>
			
			<p>
				<a href="javascript:void(0)" class="logining">登录</a>
				<a href="javascript:void(0)" class="look_for">找回密码</a>
			</p>
		</form>
	</div>
	<div class="login-error">
		<label style = "color:red;">${sessionScope['org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS'][0]['errormessage']}  </label>
	</div>
</div>
</body>
</html>