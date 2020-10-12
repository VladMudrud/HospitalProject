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
    <form action="${pageContext.request.contextPath}/myPatientsSort" method="POST">
 	<br>
    <label>Sort by:</label>
    <select name="sort" onchange="this.form.submit()">
        <option value="" selected disabled hidden="hidden">Choose here</option>
        <option value="alphabet">Alphabet</option>
        <option value="date">Date</option>
    </select>
    <br>
    <br>
    </form>
    <FORM action="${pageContext.request.contextPath}/myPatients" method="POST">
    <p style="color: red;">${errorString}</p>
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>First Name</th>
          <th>Second Name</th>
          <th>Date of Birthday</th>
          <th>Gender</th>
          <th>Status</th>
          <th>Diagnosis</th>
          <th>Details</th>
          
       </tr>
       <c:forEach items="${patientList}" var="medicalCard" >
          <tr>
             <td>${medicalCard.patient.firstName}</td>
             <td>${medicalCard.patient.secondName}</td>
             <td>${medicalCard.patient.age}</td>
             <td>${medicalCard.patient.gender}</td>
             <td>${medicalCard.patient.status}</td>
             <td>${medicalCard.diagnosis}</td>
             
             <td><button type="submit" name="id" value="${medicalCard.patient.id}">View</button></td> 
         
          </tr>
       </c:forEach>
    </table> 
    </FORM>
 </body>
</html>