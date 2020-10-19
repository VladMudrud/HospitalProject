<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="com.kharkov.epam.vmudrud.hospital.resources.text" />
<html>
 <head>
    <meta charset="UTF-8">
    <title>Doctor List</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/login.css">
 </head>
 <body>   
    <form action="${pageContext.request.contextPath}/adminDoctorChoose" method="GET">
 	<br>
    <label><fmt:message key="patientlist.lable.sort"/>:</label>
    <select name="sort" onchange="this.form.submit()">
        <option value="" selected disabled hidden="hidden"><fmt:message key="patientlist.lable.choose"/></option>
        <option value="alphabet"><fmt:message key="patientlist.select.alphabet"/></option>
        <option value="category"><fmt:message key="doctorlist.select.category"/></option>
        <option value="patientCount"><fmt:message key="doctorlist.select.numberofpatiens"/></option>
    </select>
    <br>
    <br>
    </form>
    <form action="${pageContext.request.contextPath}/adminDoctorChoose" method="POST">
    <input type="hidden" name="command" value="confirmDoctor"/>
    <table border="1">
       <tr>
          <th><fmt:message key="patientlist.lable.firstname"/></th>
          <th><fmt:message key="patientlist.lable.secondname"/></th>
          <th><fmt:message key="patientlist.lable.dateofbirhday"/></th>
          <th><fmt:message key="patientlist.lable.gender"/></th>
          <th><fmt:message key="doctorlist.select.category"/></th>
          <th><fmt:message key="doctorlist.label.numberofpatiens"/></th>
          <th><fmt:message key="doctorlist.select.confirm"/></th>
          
          
       </tr>
       
       <c:forEach items="${doctorList}" var="doctor">
          <tr>
             <td>${doctor.firstName}</td>
             <td>${doctor.secondName}</td>
             <td>${doctor.age}</td>
             <td><fmt:message key="patientlist.lable.${doctor.gender}"/></td>
             <td>${doctor.category.title}</td>
             <td>${doctor.numberOfPatients}</td>   
             <td><button type="submit" name="doctor" value="${doctor.id}">OK</button></td> 
                       
          </tr>
       </c:forEach>
    </table> 
    </form>
    <p style="color: red;">${errorString}</p>
    
 </body>
</html>