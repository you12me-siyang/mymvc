<%@ page language="java" import="java.util.*,com.wbh.mymvc.ui.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    test view success <br>
    ${msg}<br>
    ${testInterceptor}
    <form action="login" method="post">
  		<input type="text" name="username">
  		<input type="text" name="password">
  		<input type="submit" value="submit">
  	</form>
  </body>
</html>
