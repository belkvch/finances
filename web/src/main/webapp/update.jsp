<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Operations</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        button {
            margin: 5px 5px 5px 5px
        }
    </style>
</head>
<body>

<jsp:include page="NavBar.jsp" />

<div class="container mt-3">
    <c:forEach items="${operations}" var="operation">
    <h2>Information about operation ID#<c:out value="${operation.getId()}"/>
    </h2>
    <ul class="list-group">
        <li class="list-group-item"> Name: <c:out value="${operation.getNameOfOperation()}"/>
        </li>
        <li class="list-group-item"> Date: <c:out value="${operation.getDateOfOperation()}"/>
        </li>
        <li class="list-group-item"> Amount: <c:out value="${operation.getPriceOfOperation()}"/>
        </li>
        <li class="list-group-item"> Category: <c:out value="${operation.getCategoryId().getName()}"/>
        </li>
    </ul>
</div>

<c:if test="${operation.getCategoryId().getId() != 2}">
<div class="container mt-3">
    <h2>Update the operation</h2>
    <form method="POST" action="/update">
        <div class="mb-3 mt-3">
            <input type="hidden" value="<c:out value="${operation.getId()}"/>" name="id"/>
        </div>
        <div class="mb-3">
            <label>Operation name:
                <input name="name" value="<c:out value="${operation.getNameOfOperation()}"/>" class="form-control"
                       required/>
            </label>
        </div>
        <div class="mb-3">
            <div class="form-group">
                <label>Operation date:
                    <input type="date" name="date" value="<c:out value="${operation.getDateOfOperation()}"/>"
                           data-date-format='yyyy-mm-dd'
                           class="form-control" required>
                </label>
            </div>
        </div>
        <div class="mb-3">
            <label>Operation amount:
                <input type="number" min="0.01" step=".01" name="salary" value="<c:out
        value="${operation.getPriceOfOperation()}"/>" class="form-control" required/>
            </label>
        </div>
        <div class="mb-3">
            <label class="form-label">Operation category:
                <select class="form-select"  name="category_id">
                    <c:forEach items="${categories}" var="categories">
                        <option value="${categories.getId()}"> <c:out value="${categories.getName()}"/></option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div class="mb-3">
            <input type="hidden" name="actionType" value="update">
        </div>
        <div class="mb-3">
            <button type="submit" class="btn btn-outline-primary">Update the operation</button>
        </div>
        <div class="mb-3">
            <a href="/operations?id=${operation.accountId}">Your operations</a>
        </div>
    </form>
</div>
</c:if>
</c:forEach>
<br>

<jsp:include page="Footer.jsp" />

</body>
</html>
