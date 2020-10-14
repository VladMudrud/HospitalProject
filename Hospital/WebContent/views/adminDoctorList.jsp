<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
 <head>
    <meta charset="UTF-8">
    <title>Doctor List</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/login.css">
 </head>
 <body>   
    <form action="${pageContext.request.contextPath}/doctorsSort" method="POST">
 	<br>
    <label>Sort by:</label>
    <select name="sort" onchange="this.form.submit()">
        <option value="" selected disabled hidden="hidden">Choose here</option>
        <option value="alphabet">Alphabet</option>
        <option value="category">Category</option>
        <option value="patientCount">Number of patients</option>
    </select>
    <br>
    <br>
    </form>
    <p style="color: red;">${errorString}</p>
    <table border="1">
       <tr>
          <th>First Name</th>
          <th>Second Name</th>
          <th>Date of Birthday</th>
          <th>Gender</th>
          <th>Category</th>
          <th>Number of patients</th>
          
       </tr>
       
       <c:forEach items="${doctorList}" var="doctor">
          <tr>
             <td>${doctor.firstName}</td>
             <td>${doctor.secondName}</td>
             <td>${doctor.age}</td>
             <td>${doctor.gender}</td>
             <td>${doctor.category.title}</td>
             <td>${doctor.numberOfPatients}</td>
          </tr>
       </c:forEach>
    </table> 
 </body>
</html>