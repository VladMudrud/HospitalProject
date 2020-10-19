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
    <form action="${pageContext.request.contextPath}/myPatientsSort" method="POST">
 	<br>
    <label><fmt:message key="patientlist.lable.sort"/>:</label>
    <select name="sort" onchange="this.form.submit()">
        <option value="" selected disabled hidden="hidden"><fmt:message key="patientlist.lable.choose"/></option>
        <option value="alphabet"><fmt:message key="patientlist.select.alphabet"/></option>
        <option value="date"><fmt:message key="patientlist.select.date"/></option>
    </select>
    <br>
    <br>
    </form>
    <FORM action="${pageContext.request.contextPath}/myPatients" method="POST">
    <table border="1">
       <tr>
          <th><fmt:message key="patientlist.lable.firstname"/></th>
          <th><fmt:message key="patientlist.lable.secondname"/></th>
          <th><fmt:message key="patientlist.lable.dateofbirhday"/></th>
          <th><fmt:message key="patientlist.lable.gender"/></th>
          <th><fmt:message key="patientlist.lable.status"/></th>
          <th><fmt:message key="mypatientlist.lable.diagnosis"/></th>
          <th><fmt:message key="mypatientlist.lable.details"/></th>
          
       </tr>
       <c:forEach items="${patientList}" var="medicalCard" >
          <tr>
             <td>${medicalCard.patient.firstName}</td>
             <td>${medicalCard.patient.secondName}</td>
             <td>${medicalCard.patient.age}</td>
             <td><fmt:message key="patientlist.lable.${medicalCard.patient.gender}"/></td>
             <td>${medicalCard.patient.status}</td>
             <td>${medicalCard.diagnosis}</td>
             <td><button type="submit" name="id" value="${medicalCard.patient.id}"><fmt:message key="mypatientlist.lable.view"/></button></td> 
         
          </tr>
       </c:forEach>
    </table> 
    <p style="color: red;">${errorString}</p>
    </FORM>
 </body>
</html>