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
    <c:forEach items="${users}" var="user">
    <h2>Information about user ID#<c:out value="${user.getId()}"/>
    </h2>
    <ul class="list-group">
        <li class="list-group-item"> Username: <c:out value="${user.getLogin()}"/>
        </li>
        <li class="list-group-item"> Role: <c:out value="${user.getRoleId().getName()}"/>
        </li>
    </ul>
</div>


<c:choose>
    <c:when test="${user.getRoleId().getId() == 2}">
        <div class="container mt-3">
            <div class="mb-3">
                <a href="/admin">Users</a>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container mt-3">
            <h2>Update the user</h2>
            <form method="POST" action="/update-user">
                <div class="mb-3 mt-3">
                    <input type="hidden" value="<c:out value="${user.getId()}"/>" name="id"/>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="radio1" name="role_id" value="1" checked>
                    <label class="form-check-label" for="radio1">USER</label>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="radio2" name="role_id" value="2">
                    <label class="form-check-label" for="radio2">ADMIN</label>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="radio3" name="role_id" value="3">
                    <label class="form-check-label" for="radio3">BAN</label>
                </div>
                <div class="mb-3">
                    <input type="hidden" name="actionType" value="update">
                </div>
                <div class="mb-3">
                    <button type="submit" class="btn btn-outline-primary">Update the user</button>
                </div>
                <div class="mb-3">
                    <a href="/admin">Users</a>
                </div>
            </form>
        </div>
    </c:otherwise>
</c:choose>
</c:forEach>


<br>

<jsp:include page="Footer.jsp" />

</body>
</html>
