<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>header</title>
      <link rel="stylesheet" type="text/css" href="/resources/css/login.css">
   </head>
   <body >
<div style="background: LightGrey; height: 70px; padding: 5px;  border-bottom-width: 4px; /* Толщина линии внизу */
    border-bottom-style: solid; 
    border-bottom-color: black; ">
  <div style="float: left">
     <h1>Hospital</h1>
  </div>
 
  <div style="float: right; padding: 10px; text-align: right; "> 
     <!-- User store in session with attribute: loginedUser -->
     <form action="${pageContext.request.contextPath}/header" method="POST">
     
     <label> <strong> Hello ${user.role} ${user.login} &nbsp; </strong>  </label>
     
     <input type="submit" name="command" id="logout" value="Logout">  
     
     <br/> 
     <label style="float: left; "> <strong> Language: &nbsp; </strong>  </label>
     
     <select name="lang" style="float: left;">
  		<option>EN</option>
  		<option>RU</option>
	</select>    
     </form>
     
   <br/>
 
  </div>
 
</div>
   </body>
</html>