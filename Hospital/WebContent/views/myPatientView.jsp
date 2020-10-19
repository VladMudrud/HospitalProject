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
      <title>myPatientView</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   </head>
   <body style="background: White">
   <jsp:include page="header.jsp"></jsp:include>
   <jsp:include page="doctorButtons.jsp"></jsp:include>
   <div class="patientView">    
    <form method="GET" action="${pageContext.request.contextPath}/patientView"> 
        <h1><strong><fmt:message key="patientview.label.mypatient"/></strong></h1> 
        <label><fmt:message key="patientlist.lable.firstname"/>: "${medicalCard.patient.firstName}"</label>    
        <br>
        <br>
        <label><fmt:message key="patientlist.lable.secondname"/>: "${medicalCard.patient.secondName}"</label>    
        <br>  
        <br>
        <label><fmt:message key="patientlist.lable.dateofbirhday"/>: "${medicalCard.patient.age}"</label>    
        <br>  
        <br>
        <label><fmt:message key="patientlist.lable.gender"/>: <fmt:message key="patientlist.lable.${medicalCard.patient.gender}"/></label>
        <br>  
        <br>
        <label><fmt:message key="patientlist.lable.status"/>: "${medicalCard.patient.status}"</label>    
        <br>  
        <br>
        
        <label><fmt:message key="mypatientlist.lable.diagnosis"/>: </label>                    
        <input type="hidden" name="id" id="id" value="${medicalCard.patient.id}"> 
        </form>     
        
        <form method="POST" action="${pageContext.request.contextPath}/patientView">
         <input type="hidden" name="type" id="type" value="diagnosis"> 
         <input type="text" name="diagnosis" id="diagnosis" value="${medicalCard.diagnosis}" placeholder="Unknown"> 
         <button type="submit" name="id" value="${medicalCard.patient.id}"><fmt:message key="patientview.label.edit"/> </button>
        </form>     
 		<br> 
 		<hr> 
 		<br>  
        <label><fmt:message key="patientview.label.newtherapy"/>:</label>
        <br>   
        <br>  
        <form method="POST" action="${pageContext.request.contextPath}/patientView">
         <label><fmt:message key="patientview.label.title"/>:</label>
         <input type="hidden" name="type" id="type" value="therapy"> 
         <input type="hidden" name="medicalCardId" id="medicalCardId" value="${medicalCard.id}"> 
         <input type="text" name="title" id="title" required placeholder=<fmt:message key="patientview.label.title"/>> 
         <br>
         <br>
         <label><fmt:message key="patientview.label.type"/>: </label>
         <select name="therapyType">
  			<option value="medicine"><fmt:message key="patientview.selecttherapy.medicine"/></option>
  			<option value="procedure"><fmt:message key="patientview.selecttherapy.procedure"/></option>
  			<option value="operation"><fmt:message key="patientview.selecttherapy.operation"/></option>
		 </select>
         <br>  
         <br>  
         <button type="submit" name="id" value="${medicalCard.patient.id}"><fmt:message key="patientview.button.addtreatment"/></button>
         </form>
         <br> 
 		 <hr> 
 		 <br>  
 		 <form method="POST" action="${pageContext.request.contextPath}/patientView">
 		 <input type="hidden" name="type" id="type" value="dishcharge"> 
 		 <input type="hidden" name="diagnosis" id="diagnosis" value="${medicalCard.diagnosis}"> 
 		 <button type="submit" name="id" value="${medicalCard.patient.id}"><fmt:message key="patientview.button.discharge"/></button>
 		 <p style="color: red;">${errorString}</p>  
		 <p style="color: green;">${successString}</p>  
 		 
 		 </form>
 		 
</div>
   </body>
   
</html>