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
        /*button {*/
        /*    margin: 5px 5px 5px 5px*/
        /*}*/
    </style>
</head>
<body>

<jsp:include page="NavBar.jsp" />

<c:forEach items="${accounts}" var="accounts">
<div class="container mt-3">
    <form method="POST" action="/history" class="d-flex">
        <div class="mb-3 mt-3">
            <c:forEach items="${operationsList}" var="operationsList">
                <input type="hidden" name="id" value="${operationsList.getAccountId()}">
            </c:forEach>
        </div>
        <div class="mb-3">
            <div class="form-group">
                <label class="d-flex align-items-center">
                    <span class="flex-shrink-0" style="margin-left: 1rem;"> <h6> Find by date:</h6> </span>
                    <input type="date" name="date" data-date-format='yyyy-mm-dd' class="form-control">
                </label>
            </div>
        </div>
        <div class="mb-3">
            <input type="hidden" name="actionType" value="find">
        </div>
        <div class="mb-3" style="margin-left: 1rem;">
            <button type="submit" class="btn btn-outline-primary">Find</button>
        </div>
    </form>
</div>

<div class="container mt-3">
    <h2>Your operations:</h2>
    <table class="table table-borderless">
        <thead>
        <tr>
            <th><b>Name</b></th>
            <th><b>Date</b></th>
            <th><b>Price</b></th>
            <th><b>Category</b></th>
            <c:if test="${accounts.isActiveAccount()}">
            <th><b>Edit</b></th>
            <th><b>Delete</b></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${operations}" var="operation">
            <tr>
                <td><c:out value="${operation.getNameOfOperation()}"/>
                </td>
                <td><c:out value="${operation.getDateOfOperation()}"/>
                </td>
                <td><c:out value="${operation.getPriceOfOperation()}"/>
                </td>
                <td><c:out value="${operation.getCategoryId().getName()}"/>
                </td>
                <c:if test="${accounts.isActiveAccount()}">
                <td><a href="/update?id=${operation.id}">Edit</a></td>
                <td>
                    <c:if test="${operation.getCategoryId().getId() != 2}">
                    <form method="post" action="/delete" style="display:inline;">
                        <input type="hidden" name="id" value="${operation.getId()}">
                        <button type="submit" class="btn btn-outline-primary"
                                onclick="return confirm('Are you sure you want to delete this item?');">Delete
                        </button>
                    </form>
                    </c:if>
                </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</c:forEach>

<jsp:include page="Footer.jsp" />

</body>
</html>

