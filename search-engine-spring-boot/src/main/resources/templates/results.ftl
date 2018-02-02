<#import "/spring.ftl" as spring/>
<html>
<head>

<title>Hello ${name}!</title>
</head>
<body>
	  <h2 class="hello-title">Hello ${name}!</h2>
    <table>
    	<tr>
    		<td><label><spring:message code="input.email"></spring:message></label></td>
    		<td> </td>
    	</tr>
    	<tr>
    		<td></td>
    		<td> <input type="submit" name="Search"></td>
    	</tr>
    </table>

	 <div>
         <fieldset>
            <legend>Search Person</legend>
             <table border="1">
	            <tr>
	               <th>First Name</th>
	               <th>Last Name</th>
	            </tr>
	            <#list persons as person>
	            <tr>
	               <td>${person.person.forename}</td>
	               <td>${person.person.surname}</td>
	            </tr>
	            </#list>
	         </table>
         </fieldset>
      </div>

</body>
</html>