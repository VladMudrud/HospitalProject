<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="com.kharkov.epam.vmudrud.hospital.resources.text" />
 <!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>header</title>
      <link rel="stylesheet" type="text/css" href="/resources/css/login.css">
   </head>
   <body >
<div style="background: LightGrey; height: 70px; padding: 5px;  border-bottom-width: 4px; 
    border-bottom-style: solid; 
    border-bottom-color: black; ">
  <div style="float: left">
     <h1>Hospital</h1>
  </div>
 
  <div style="float: right; padding: 10px; text-align: right; "> 
     <form action="${pageContext.request.contextPath}/header" method="POST">
     <input type="hidden" name="command" value="Logout"/>
     
     <label> <strong> <fmt:message key="header.label.hi"/> <fmt:message key="header.label.${user.role}"/> ${user.login} &nbsp; </strong>  </label>
     
     <input type="submit" name="logout" id="logout" value=<fmt:message key="header.button.logout"/> >  
      </form>
     
     <label style="float: left; "> <strong> <fmt:message key="header.label.lang"/>: &nbsp; </strong>  </label>
     <form>
          <select id="language" name="language" onchange="submit()"  style="float: left;">
              <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
              <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
          </select>
    </form>    
     
 
  </div>
 
</div>
   </body>
</html>