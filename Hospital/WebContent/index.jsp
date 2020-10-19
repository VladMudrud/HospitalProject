<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="com.kharkov.epam.vmudrud.hospital.resources.text" />
<!DOCTYPE html>
<html lang="${language}">
   <head>
      <meta charset="UTF-8">
      <title>Login</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   <body >
    <h2 class="title">Hospital</h2><br>    
    <div class="login">    
    <form>
             <select id="language" name="language" style="position: absolute; top: 5px; right: 5px;" onchange="submit()">
                 <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                 <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
             </select>
    </form>
    <form method="POST" action="${pageContext.request.contextPath}/login">   
    	<input type="hidden" name="command" value="login"/>
        <label for="username"><strong><fmt:message key="login.label.username"/>: <br></strong>    
        </label>    
        <input type="text" name="login" id="Uname" placeholder=<fmt:message key="login.label.username"/> value= "${user.login}" required autofocus>    
        <br><br>    
        <label for="password"><strong><fmt:message key="login.label.password"/>: <br>
        </strong>    
        </label>            
        <input type="Password" name="password" id="Pass" value= "${user.password}" placeholder=<fmt:message key="login.label.password"/> required>  
         <p style="color: red;">${errorString}</p>  
        <br><br>    
        <input type="submit" name="log" id="log" value=<fmt:message key="login.button.submit"/>>       
        <br><br>    
        <input type="checkbox" name="rememberMe" id="check" value= "Y">    
        <strong><fmt:message key="login.checkbox.remember"/></strong>    
        <br><br>    
    </form>     
	</div>
   </body>
   
</html>