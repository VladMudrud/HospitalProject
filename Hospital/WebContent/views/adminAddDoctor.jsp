<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Add Patient</title>
      <link rel="stylesheet" type="text/css" href="resources/css/login.css">
   </head>
   <body style="background: White">
   <form method="POST" action="${pageContext.request.contextPath}/adminAddDoctorMenu">   
        <p style="color: red;">${errorString}</p>  
        <p style="color: green;">${successString}</p>  
        <label><strong>Login:</strong>    
        </label>    
        <input type="text" name="login" id="login" placeholder="Login" required>    
        <br><br>    
        <label><strong>Password:    
        </strong>    
        </label>    
        <input type="password" name="password" id="password" required>  
        <br><br>   
        <label><strong>First Name:</strong>    
        </label>    
        <input type="text" name="firstName" id="firstName" placeholder="First Name" required>    
        <br><br>    
        <label><strong>Second name:    
        </strong>    
        </label>    
        <input type="text" name="secondName" id="secondName" placeholder="Second name" required>  
        <br><br>   
		<label><strong>Date of Birth:    
        </strong>    
        </label>    
        <input type="date" name="dateOfBirth" id="dateOfBirth" placeholder="YYYY-MM-DD" required>  
        <br><br> 
        <label><strong>Gender:    
        </strong>    
        </label> 
        <select name="gender" id="gender">
        	<option value="male">Male</option>
        	<option value="female">Female</option>
   		</select>
   		<br><br> 
        <label><strong>Category:    
        </strong>    
        </label> 
   		<select name="category" id="category">
        	<option value="Therapist">Therapist</option>
        	<option value="Surgeon">Surgeon</option>
        	<option value="Traumatologist">Traumatologist</option>
   		</select>
   		<br><br> 
        <input type="submit" name="command" id="addDoctor" value="Add doctor">       
    </form>        
   </body>
   
</html>