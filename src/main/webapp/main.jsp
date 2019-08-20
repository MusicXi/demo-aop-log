<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>main page</title>
<script src="common/js/jquery/jquery-2.1.4.js"></script>
<script src="common/js/json/json2.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

	});
	function logout() {
		debugger;
		$.ajax({
			url : "resetPassword",
			type:"post",
			contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    data: JSON.stringify({
		    	"username":"ddd",
		    	"password":"333"
		    }),
			success : function(data) {
				alert(data.msg);
			}
		});
	}
	
	function testException() {
		debugger;
		$.ajax({
			url : "testException",
			success : function(data) {
				alert(data.msg);
			},
			failure: function(data) {
				
			}
		});
	}
</script>
</head>
<body>
<button onclick="logout()">重置密码</button>	
<button onclick="testException()">测试产出异常</button>	
	<form action="logout">
	  	<input type="submit" value="注销" />
	</form>
</body>
</html>