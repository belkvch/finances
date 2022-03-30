<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.belkvch.finances.financesApp.entyti.Operations" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <title>Operation</title>
</head>
<body>

<div class="container mt-3">
    <% Operations operation = (Operations) request.getAttribute("operation");%>
    <h2>Information about operation ID#<%=operation.getId()%>
    </h2>
    <ul class="list-group">
        <li class="list-group-item"> Name: <%=operation.getNameOfOperation()%>
        </li>
        <li class="list-group-item"> Date: <%=operation.getDateOfOperation()%>
        </li>
        <li class="list-group-item"> Price: <%=operation.getPriceOfOperation()%>
        </li>
    </ul>
</div>

<div class="container mt-3">
    <h2>Update the operation</h2>
    <form method="POST" action="/update">
        <div class="mb-3 mt-3">
            <input type="hidden" value="<%=operation.getId()%>" name="id"/>
        </div>
        <div class="mb-3">
            <label>Operation name:
                <input name="name" value="<%=operation.getNameOfOperation()%>" class="form-control" required/>
            </label>
        </div>
        <div class="mb-3">
            <div class="form-group">
                <label>Operation date:
                    <input type="date" name="date" value="<%=operation.getDateOfOperation()%>"
                           data-date-format='yyyy-mm-dd'
                           class="form-control" required>
                </label>
            </div>
        </div>
        <div class="mb-3">
            <label>Operation salary:
                <input type="number" min="1" step=".01" name="salary" value="<%=operation.getPriceOfOperation()%>" class="form-control" required/>
            </label>
        </div>
        <div class="mb-3">
            <input type="hidden" name="actionType" value="update">
        </div>
        <div class="mb-3">
            <button type="submit" class="btn btn-outline-primary">Update the operation</button>
        </div>
        <div class="mb-3">
            <a href="/operations/">Main page</a>
        </div>
    </form>
</div>


</body>
</html>
