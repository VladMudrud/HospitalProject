<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
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
    <p style="color: red;">${errorString}</p>
    <table border="1">
       <tr>
          <th>Patient</th>
          <th>Diagnosis</th>
          <th>Treatment</th>
          <th>Type</th>
          <th>Action</th>
       </tr>
       <c:forEach items="${therapyList}"  var="therapy" >
          <tr>
             <td>${therapy.medicalCard.patient.firstName} ${therapy.medicalCard.patient.secondName}</td>
             <td>${therapy.medicalCard.diagnosis}</td>
             <td>${therapy.title}</td>
             <td>${therapy.type}</td>    
             <td><button type="submit" name="id" value="${therapy.id}">Complete the treatment</button></td>                       
          </tr>
          
       </c:forEach>
    </table> 
    </FORM>
 </body>
</html>