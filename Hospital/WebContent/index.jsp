<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Login</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   <body >
    <h2 class="title">Hospital</h2><br>    
    <div class="login">    
    <form method="POST" action="${pageContext.request.contextPath}/login">   
    	<input type="hidden" name="command" value="login"/>
        <label><strong>Login <br> </strong>    
        </label>    
        <input type="text" name="login" id="Uname" placeholder="Login" value= "${user.login}" required="" autofocus="">    
        <br><br>    
        <label><strong>Password     
        </strong>    
        </label>    
        <input type="Password" name="password" id="Pass" value= "${user.password}" placeholder="Password" required="">  
         <p style="color: red;">${errorString}</p>  
        <br><br>    
        <input type="submit" name="log" id="log" value="Login">       
        <br><br>    
        <input type="checkbox" name="rememberMe" id="check" value= "Y">    
        <strong>Remember me</strong>    
        <br><br>    
    </form>     
</div>
   </body>
   
</html>