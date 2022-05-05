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
    <h2>Users: </h2>
    <table class="table table-borderless">
        <thead>
        <tr>
            <th><b>Id</b></th>
            <th><b>Username</b></th>
            <th><b>Role</b></th>
            <th><b>Edit</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.getId()}"/>
                </td>
                <td><c:out value="${user.getLogin()}"/>
                </td>
                <td><c:out value="${user.getRoleId().getName()}"/>
                </td>
                <td><a href="/update-user?id=${user.id}">Edit</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<br>


<jsp:include page="Footer.jsp" />

</body>
</html>
