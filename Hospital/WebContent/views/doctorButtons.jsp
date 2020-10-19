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
       <head>
      <meta charset="UTF-8">
      <title>doctorMenu</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   </head>
   <body style="background: White">
   <div class="btn-group">
   <form method="GET" action="${pageContext.request.contextPath}/patientList?sort=alphabet">   
 	 <button><fmt:message key="doctorbuttons.lable.patientlist"/></button>
   </form>
    <form method="GET" action="${pageContext.request.contextPath}/myPatients?sort=alphabet">   
  	 <button><fmt:message key="doctorbuttons.lable.mypatientlist"/></button>
  	</form>
    <form method="GET" action="${pageContext.request.contextPath}/therapy">   
 	 <button><fmt:message key="doctorbuttons.lable.therapy"/></button>
 	</form>
   </div>
   </body>
   
</html>