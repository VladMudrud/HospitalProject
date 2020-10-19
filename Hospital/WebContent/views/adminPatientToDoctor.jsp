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
    <FORM action="${pageContext.request.contextPath}/adminAddDoctorToPatientMenu" method="POST">
	<input type="hidden" name="command" value="sorting"/>
    <br>
    <label><fmt:message key="patientlist.lable.sort"/>:</label>
    <select name="sort" onchange="this.form.submit()">
        <option value="" selected disabled hidden="hidden"><fmt:message key="patientlist.lable.choose"/></option>
        <option value="alphabet"><fmt:message key="patientlist.select.alphabet"/></option>
        <option value="date"><fmt:message key="patientlist.select.date"/></option>
    </select>
    </FORM>
    
    <br>
    <br>
    <FORM action="${pageContext.request.contextPath}/adminAddDoctorToPatientMenu" method="POST">
    <input type="hidden" name="command" value="doctorChoose"/>
    <table border="1" >
       <tr>
          <th><fmt:message key="patientlist.lable.firstname"/></th>
          <th><fmt:message key="patientlist.lable.secondname"/></th>
          <th><fmt:message key="patientlist.lable.dateofbirhday"/></th>
          <th><fmt:message key="patientlist.lable.gender"/></th>
          <th><fmt:message key="patientlist.lable.status"/></th>
          <th><fmt:message key="patientlist.lable.doctor"/></th>
          
          
       </tr>
       <c:forEach items="${medicalCardList}" var="madicalCard" >
          <tr>
             <td>${madicalCard.patient.firstName}</td>
             <td>${madicalCard.patient.secondName}</td>
             <td>${madicalCard.patient.age}</td>
             <td><fmt:message key="patientlist.lable.${madicalCard.patient.gender}"/></td>
             <td>${madicalCard.patient.status}</td>
             
             <td><button type="submit" name="idPatient" value="${madicalCard.patient.id}"><fmt:message key="admin.lable.choose"/></button></td> 
             
          </tr>
       </c:forEach>
    </table> 
    </FORM>
    <p style="color: red;">${errorString}</p>
    <p style="color: green;">${successString}</p>
    
 </body>
</html>