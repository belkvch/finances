<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.belkvch.finances.financesApp.entyti.Operations" %>

<html>
<head>
    <title>Operation</title>
</head>
<body>
    <% Operations operation = (Operations) request.getAttribute("operation");%>
  Name:      <%=operation.getNameOfOperation()%>
  ID:      <%=operation.getId()%>
  DATE:      <%=operation.getDateOfOperation()%>
  PRICE:      <%=operation.getPriceOfOperation()%>


<form method="POST" action="/update">
    <input type="hidden" value="<%=operation.getId()%>" name="id"/>
    <label>Operation name:</label>
    <br>
    <input name="name" value="<%=operation.getNameOfOperation()%>"/>
    <br>
    <div class="form-group">
        <label>Operation date:</label>
        <input type="date" name="date" value="<%=operation.getDateOfOperation()%>" data-date-format='yyyy-mm-dd'
               class="form-control">
    </div>
    <br>
    <label>Operation salary: </label>
    <input name="salary" value="<%=operation.getPriceOfOperation()%>"/>
    <br>
    <input type="hidden" name="actionType" value="update">
    <br>
    <button type="submit">Update the operation</button>
</form>

</body>
</html>
