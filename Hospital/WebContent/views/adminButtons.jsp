<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
       <head>
      <meta charset="UTF-8">
      <title>doctorMenu</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   </head>
   <body style="background: White">
   <div class="btn-group">
   <form method="GET" action="${pageContext.request.contextPath}/patientList?sort=alphabet">   
 	 <button>Patient List</button>
   </form>
      <form method="GET" action="${pageContext.request.contextPath}/doctorList?sort=alphabet">   
 	 <button>Doctor List</button>
   </form>
   <form method="GET" action="${pageContext.request.contextPath}/adminAddPatientMenu">   
 	 <button>Add Patient</button>
   </form>
   </div>
   </body>
   
</html>