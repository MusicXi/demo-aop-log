<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<script src="common/js/jquery/jquery-2.1.4.js"></script>
<script src="common/js/vue/vue.min.js"></script>

</head>
<body>
	<h3>演示登入行为，触发日志记录,请求参数中password将会被隐藏,不被记录</h3>
	<form action="login">
		用户名:<input type="text" name="username" />
		密码：<input type="password" name="password" />
	  	<input type="submit" value="登入" />
	</form>
	<h3>演示登入行为，触发日志记录,请求参数中password将会被隐藏,不被记录</h3>
<div id="app">
  <p>{{ message }}</p>
</div>

<script>
new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue.jsddddd!'
  }
})
</script>  
</body>
</html>