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

<center>
    <h2 class="display-5 mt-3"> Welcome, ${sessionScope.login}</h2>
</center>

<br>

<div class="container mt-3">
    <h2>Your accounts:</h2>
    <table class="table table-borderless">
        <thead>
        <tr>
            <th><b>Id</b></th>
            <th><b>Balance</b></th>
            <th><b>Currency</b></th>
            <th><b>Operations</b></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${accounts}" var="accounts">
            <c:if test="${accounts != null}">
                <tr>

                    <td><c:out value="${accounts.getId()}"/>
                    <td><c:out value="${accounts.getAmount()}"/>
                    <td><c:out value="${accounts.getCurrencyId().getName()}"/>
                    <td><a href="/operations?id=${accounts.id}">Show</a>

            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>


<jsp:include page="Footer.jsp" />


</body>
</html>

