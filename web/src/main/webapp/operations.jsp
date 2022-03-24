<%@ page import="com.belkvch.finances.financesApp.entyti.Operations" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Cats</title>
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


</body>
</html>
