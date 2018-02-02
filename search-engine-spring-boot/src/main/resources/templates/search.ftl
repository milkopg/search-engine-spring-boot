<#import "/spring.ftl" as spring/>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
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
            
            <form name="search" action="search" method="POST">
           
               Email: <@spring.formInput "searchForm.email" "" "text"/>    <br/>
               Surname: <@spring.formInput "searchForm.surname" "" "text"/>    <br/>
			   Postcode: <@spring.formInput "searchForm.postcode" "" "text"/>    <br/>
               <input type="submit" value="Search" />
            </form>
         </fieldset>
      </div>

</body>
</html>