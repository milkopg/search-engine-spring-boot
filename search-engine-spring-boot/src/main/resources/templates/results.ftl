<#import "/spring.ftl" as spring/>
<@spring.bind "customer" />
<html>
<head>

<title>Hello ${name}!</title>
</head>
<body>
	  <h2 class="hello-title">Hello ${name}!</h2>
   

	 <div>
         <fieldset>
            <legend><@spring.message "legend.search"/></legend>
             <h2><@spring.message "customer.email"/>  ${customer.emailAddress?if_exists}</h2>
             <table border="1">
             	<tr>
	               <th><@spring.message "person.forename"/></th>
	               <th><@spring.message "person.middlename"/></th>
	               <th><@spring.message "person.surname"/></th>
	               <th><@spring.message "person.telephone"/></th>
	               
	               <th><@spring.message "person.address.buildnumber"/></th>
	               <th><@spring.message "person.address.street"/></th>
	               <th><@spring.message "person.address.postcode"/></th>
	               <th><@spring.message "person.address.town"/></th>
	            </tr>
	            <#list persons as record>
	            <tr>
	             <td>${record.person.forename?if_exists}</td>
	               <td>${record.person.middlename?if_exists}</td>
	               <td>${record.person.surname?if_exists}</td>
	               <td>${record.person.telephone?if_exists}</td>
	               <td>${record.person.address.buildnumber?if_exists}</td>
	               <td>${record.person.address.street?if_exists}</td>
	               <td>${record.person.address.postcode?if_exists}</td>
	               <td>${record.person.address.town?if_exists}</td>
	            </tr>11111
	            </#list>
	         </table>
         </fieldset>
      </div>

</body>
</html>