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
    <FORM action="${pageContext.request.contextPath}/adminAddDoctorToPatientMenu" method="POST">
	<input type="hidden" name="command" value="sorting"/>
    <br>
    <label>Sort by:</label>
    <select name="sort" onchange="this.form.submit()">
        <option value="" selected disabled hidden="hidden">Choose here</option>
        <option value="alphabet">Alphabet</option>
        <option value="date">Date</option>
    </select>
    </FORM>
    
    <br>
    <br>
    <FORM action="${pageContext.request.contextPath}/adminAddDoctorToPatientMenu" method="POST">
    <input type="hidden" name="command" value="doctorChoose"/>
    <table border="1" >
       <tr>
          <th>First Name</th>
          <th>Second Name</th>
          <th>Date of Birthday</th>
          <th>Gender</th>
          <th>Status</th>
          <th>Doctor</th>
          
          
       </tr>
       <c:forEach items="${medicalCardList}" var="madicalCard" >
          <tr>
             <td>${madicalCard.patient.firstName}</td>
             <td>${madicalCard.patient.secondName}</td>
             <td>${madicalCard.patient.age}</td>
             <td>${madicalCard.patient.gender}</td>
             <td>${madicalCard.patient.status}</td>
             
             <td><button type="submit" name="idPatient" value="${madicalCard.patient.id}">Choose</button></td> 
             
          </tr>
       </c:forEach>
    </table> 
    </FORM>
    <p style="color: red;">${errorString}</p>
    <p style="color: green;">${successString}</p>
    
 </body>
</html>