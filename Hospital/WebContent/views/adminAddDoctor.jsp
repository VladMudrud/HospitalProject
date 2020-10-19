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
      <title>Add Patient</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   <body style="background: White">
   <form method="POST" action="${pageContext.request.contextPath}/adminAddDoctorMenu">   
        <p style="color: red;">${errorString}</p>  
        <p style="color: green;">${successString}</p>  
        <label><strong><fmt:message key="login.label.username"/>:</strong>    
        </label>    
        <input type="text" name="login" id="login" placeholder=<fmt:message key="login.label.username"/> required>    
        <br><br>    
        <label><strong><fmt:message key="login.label.password"/>:    
        </strong>    
        </label>    
        <input type="password" name="password" id="password" required>  
        <br><br>   
        <label><strong><fmt:message key="patientlist.lable.firstname"/>:</strong>    
        </label>    
        <input type="text" name="firstName" id="firstName" placeholder=<fmt:message key="patientlist.lable.firstname"/> required>    
        <br><br>    
        <label><strong><fmt:message key="patientlist.lable.secondname"/>:    
        </strong>    
        </label>    
        <input type="text" name="secondName" id="secondName" placeholder=<fmt:message key="patientlist.lable.secondname"/> required>  
        <br><br>   
		<label><strong><fmt:message key="patientlist.lable.dateofbirhday"/>:    
        </strong>    
        </label>    
        <input type="date"  max="2020-10-20" name="dateOfBirth" id="dateOfBirth" placeholder="YYYY-MM-DD" required>  
        <br><br> 
        <label><strong><fmt:message key="patientlist.lable.gender"/>:    
        </strong>    
        </label> 
        <select name="gender" id="gender">
        	<option value="male"><fmt:message key="patientlist.lable.male"/></option>
        	<option value="female"><fmt:message key="patientlist.lable.female"/></option>
   		</select>
   		<br><br> 
        <label><strong><fmt:message key="doctorlist.select.category"/>:    
        </strong>    
        </label> 
   		<select name="category" id="category">
        	<option value="Therapist"><fmt:message key="admin.select.therapist"/></option>
        	<option value="Surgeon"><fmt:message key="admin.select.surgeon"/></option>
        	<option value="Traumatologist"><fmt:message key="admin.select.traumatologist"/></option>
   		</select>
   		<br><br> 
        <input type="submit" name="button" id="button" value=<fmt:message key="adminbuttons.lable.adddoctor"/>>      
        <input type="hidden" name="command" value="Add doctor"/>
         
    </form>        
   </body>
   
</html>