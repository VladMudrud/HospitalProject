<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
       <head>
      <meta charset="UTF-8">
      <title>myPatientView</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   </head>
   <body style="background: White">
   <jsp:include page="header.jsp"></jsp:include>
   <jsp:include page="doctorButtons.jsp"></jsp:include>
   <div class="patientView">    
    <form method="GET" action="${pageContext.request.contextPath}/patientView"> 
        <h1><strong>My Patient</strong></h1> 
        <label>First Name: "${medicalCard.patient.firstName}"</label>    
        <br>
        <br>
        <label>Second Name: "${medicalCard.patient.secondName}"</label>    
        <br>  
        <br>
        <label>Date of Birthday: "${medicalCard.patient.age}"</label>    
        <br>  
        <br>
        <label>Gender: "${medicalCard.patient.gender}"</label>
        <br>  
        <br>
        <label>Status: "${medicalCard.patient.status}"</label>    
        <br>  
        <br>
        
        <label>Diagnosis: </label>                    
        <input type="hidden" name="id" id="id" value="${medicalCard.patient.id}"> 
        </form>     
        
        <form method="POST" action="${pageContext.request.contextPath}/patientView">
         <input type="hidden" name="type" id="type" value="diagnosis"> 
         <input type="text" name="diagnosis" id="diagnosis" value="${medicalCard.diagnosis}" placeholder="Unknown"> 
         <button type="submit" name="id" value="${medicalCard.patient.id}">Edit</button>
        </form>     
 		<br> 
 		<hr> 
 		<br>  
        <label>New Therapy: </label>
        <br>   
        <br>  
        <form method="POST" action="${pageContext.request.contextPath}/patientView">
         <label>Title: </label>
         <input type="hidden" name="type" id="type" value="therapy"> 
         <input type="hidden" name="medicalCardId" id="medicalCardId" value="${medicalCard.id}"> 
         <input type="text" name="title" id="title" required="" placeholder="Title"> 
         <br>
         <br>
         <label>Type:  </label>
         <select name="therapyType">
  			<option>medicine</option>
  			<option>procedure</option>
  			<option>operation</option>
		 </select>
         <br>  
         <br>  
         <button type="submit" name="id" value="${medicalCard.patient.id}">Add treatment</button>
         </form>
         <br> 
 		 <hr> 
 		 <br>  
 		 <form method="POST" action="${pageContext.request.contextPath}/patientView">
 		 <input type="hidden" name="type" id="type" value="dishcharge"> 
 		 <input type="hidden" name="diagnosis" id="diagnosis" value="${medicalCard.diagnosis}"> 
 		 <button type="submit" name="id" value="${medicalCard.patient.id}">Discharge from hospital</button>
 		 <p style="color: red;">${errorString}</p>  
 		 </form>
 		 
</div>
   </body>
   
</html>