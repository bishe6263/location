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
$(document).ready(function(){
	
	$.ajax({
		url:"${ctx}/getuser.spring?t="+new Date().getTime(),
		type:"POST",
		contentType:"application/json",
		data:null,
		success:function(jd){
			if(jd.success){
				alert(jd.msg);
				$('#phone').val(jd.data['phone']);
				$('#password').val(jd.data['password']); 
				$('#name').val(jd.data['name']); 
				$('#email').val(jd.data['email']); 
			}else{
				alert(jd.msg);
			}
		}
	});
})
</script>
</head>
<body>	
	<form id="register_form" action="${ctx}/updateuser.spring" enctype="multipart/form-data">
		手机号:<input id="phone" type="text" name="phone" value="" readonly="readonly"/><br>
		密码:<input id="password" type="text" name="password" value=""/><br>
		昵称:<input id="name" type="text" name="name" value=""/><br>
		邮箱:<input id="email" type="text" name="email" value=""/><br>
		<input id="registerBtn" type="submit" value="修改"/>
	</form>
</body>
</html>
