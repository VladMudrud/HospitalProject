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
    <FORM action="${pageContext.request.contextPath}/patientList" method="POST">
    <br>
    <label>Sort by:</label>
    <select name="sort" onchange="this.form.submit()">
        <option value="" selected disabled hidden="hidden">Choose here</option>
        <option value="alphabet">Alphabet</option>
        <option value="date">Date</option>
    </select>
    <br>
    <br>
    <p style="color: red;">${errorString}</p>
    <table border="1" >
       <tr>
          <th>First Name</th>
          <th>Second Name</th>
          <th>Date of Birthday</th>
          <th>Gender</th>
          <th>Status</th>
          
       </tr>
       <c:forEach items="${patientList}" var="patient" >
          <tr>
             <td>${patient.firstName}</td>
             <td>${patient.secondName}</td>
             <td>${patient.age}</td>
             <td>${patient.gender}</td>
             <td>${patient.status}</td>
             
          </tr>
       </c:forEach>
    </table> 
    </FORM>
 </body>
</html>