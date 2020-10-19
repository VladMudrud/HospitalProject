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
    <title>Patient List</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/login.css">
 </head>
 <body>   
 	<FORM action="${pageContext.request.contextPath}/therapy" method="POST">
    <input type="hidden" name="type" id="type" value="compTreatment"> 
    <table border="1">
       <tr>
          <th><fmt:message key="therapylist.lable.patient"/></th>
          <th><fmt:message key="mypatientlist.lable.diagnosis"/></th>
          <th><fmt:message key="therapylist.lable.treatment"/></th>
          <th><fmt:message key="patientview.label.type"/></th>
          <th><fmt:message key="therapylist.button.action"/></th>
       </tr>
       <c:forEach items="${therapyList}"  var="therapy" >
          <tr>
             <td>${therapy.medicalCard.patient.firstName} ${therapy.medicalCard.patient.secondName}</td>
             <td>${therapy.medicalCard.diagnosis}</td>
             <td>${therapy.title}</td>
             <td><fmt:message key="patientview.selecttherapy.${therapy.type}"/></td>    
             <td><button type="submit" name="id" value="${therapy.id}"><fmt:message key="therapylist.button.complete"/></button></td>                       
          </tr>
          
       </c:forEach>
       
    </table> 
    <p style="color: red;">${errorString}</p>
    <p style="color: green;">${successString}</p>  
    
    </FORM>
    
 </body>
</html>