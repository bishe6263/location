<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp" %>
<%@ include file="./common/meta.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- <script src="../ref/jquery-1.9.1.min.js"></script>
<script src="../ref/json2.js"></script>  -->
<script type="text/javascript">
</script>
</head>
<body>	
	<form id="register_form" action="${ctx}/register.spring" enctype="multipart/form-data">
		手机号:<input type="text" name="phone" value=""/><br>
		密码:<input type="text" name="password" value=""/><br>
		昵称:<input type="text" name="name" value=""/><br>
		邮箱:<input type="text" name="email" value=""/><br>
		验证码:<input type="text" name="randnum" value=""/>
		<input id="registerBtn" type="submit" value="注册"/>
	</form>
	<form id="rundnum_form" action="${ctx}/foudrandnum.spring" enctype="multipart/form-data">
		手机号:<input type="text" name="phone" value=""/><br>
		<input id="findrandnum" type="submit"   value="获取验证码"/><br>
	</form>
</body>
</html>
