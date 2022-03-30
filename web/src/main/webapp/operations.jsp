<%@ page import="com.belkvch.finances.financesApp.entyti.Operations" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Operations</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-3">
    <h2>Your operations:</h2>
    <table class="table table-borderless">
        <thead>
        <tr>
            <th><b>Name</b></th>
            <th><b>Id</b></th>
            <th><b>Date</b></th>
            <th><b>Price</b></th>
            <th><b>Edit</b></th>
            <th><b>Delete</b></th>
        </tr>
        </thead>
        <%
            List<Operations> operations = (List<Operations>) request.getAttribute("operations");
            for (Operations operation : operations) {%>
        <tbody>
        <tr>
            <td><%=operation.getNameOfOperation()%>
            </td>
            <td><%=operation.getId()%>
            </td>
            <td><%=operation.getDateOfOperation()%>
            </td>
            <td><%=operation.getPriceOfOperation()%>
            </td>
            <td><a href="/update?id=<%=operation.getId()%>">Edit</a></td>
            <td>
                <form method="post" action="/delete" style="display:inline;">
                    <input type="hidden" name="id" value="<%=operation.getId()%>">
                    <button type="submit" class="btn btn-outline-primary">Delete</button>
                </form>
            </td>
            <%}%>
        </tr>
        </tbody>
    </table>
</div>

<br>

<div class="container mt-3">
    <h2>Add new operation</h2>
    <form method="POST" action="/operations/">
        <div class="mb-3 mt-3">
            <label>Operation name:
                <input name="name" placeholder="Enter name" class="form-control" required/>
            </label>
        </div>
        <div class="mb-3">
            <div class="form-group">
                <label>Operation date:
                    <input type="date" name="date" data-date-format='yyyy-mm-dd' class="form-control" required>
                </label>
            </div>
        </div>
        <div class="mb-3">
            <label>Operation salary:
                <input type="number" min="1" step=".01" name="salary" placeholder="Enter price" class="form-control" required/>
            </label>
        </div>
        <div class="mb-3">
            <input type="hidden" name="actionType" value="create">
        </div>
        <div class="mb-3">
            <button type="submit" class="btn btn-outline-primary">Add</button>
        </div>
    </form>
</div>

</body>
</html>
