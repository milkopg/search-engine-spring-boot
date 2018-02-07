<#import "/spring.ftl" as spring/>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
</head>
<body>
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
           
               <@spring.message "input.email"/><@spring.formInput "searchForm.email" "" "text"/>    <br/>
               <@spring.message "input.surname"/><@spring.formInput "searchForm.surname" "" "text"/>    <br/>
			   <@spring.message "input.postcode"/><@spring.formInput "searchForm.postcode" "" "text"/>    <br/>
               <input type="submit" value="Search" />
            </form>
         </fieldset>
      </div>

</body>
</html>