<%@ page import="com.belkvch.finances.financesApp.entyti.Operations" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Operations</title>
</head>
<body>
<table>
    <tr>
        <th><b>Name</b></th>
        <th><b>Id</b></th>
        <th><b>Date</b></th>
        <th><b>Price</b></th>
    </tr>
    <%
        List<Operations> operations = (List<Operations>)request.getAttribute("operations");
        for(Operations operation:operations){%>
    <tr>
        <td><%=operation.getNameOfOperation()%></td>
        <td><%=operation.getId()%></td>
        <td><%=operation.getDateOfOperation()%></td>
        <td><%=operation.getPriceOfOperation()%></td>
        <%}%>
    </tr>
</table>

<br>
<form method="POST" action="/operations/">
    Creation form
    <br>
    <label>Operation name: </label><input name="name"/>
    <br>
    <label>Operation date: </label><input name="date"/>
    <br>
    <label>Operation salary: </label><input name="salary"/>
    <br>
    <input type="hidden" name="actionType" value="create">
    <br>
    <button type="submit">Create a new operation</button>
</form>



</body>
</html>
