<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Subscriptions</title>
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
    <h2>Your subscriptions:</h2>
    <table class="table table-borderless">
        <thead>
        <tr>
            <th><b>Name</b></th>
            <th><b>Status</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${subscriptions}" var="subscriptions">
            <tr>
                <td><c:out value="${subscriptions.getName()}"/>
                </td>
                <td><c:out value="${subscriptions.isActive()}"/>
                </td>
                <td>
                    <form method="post" action="/stop-timer" style="display:inline;">
                        <input type="hidden" name="id" value="${subscriptions.getId()}">
                        <button type="submit" class="btn btn-outline-primary"
                                onclick="return confirm('Are you sure you want to stop this subscription?');">Change
                            status
                        </button>
                    </form>
                    <form method="post" action="/delete-timer" style="display:inline;">
                        <input type="hidden" name="id" value="${subscriptions.getId()}">
                        <button type="submit" class="btn btn-outline-primary"
                                onclick="return confirm('Are you sure you want to delete this item?');">Delete
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:forEach items="${operationsList}" var="operationsList">
    <div class="container mt-3 mb-3">
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdropAddS">
            Add new subscription
        </button>
        <div class="modal fade" id="staticBackdropAddS" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Add new subscription</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="/subscription" class="mb-0">
                            <div class="mb-3 mt-3">
                                <input type="hidden" name="id" value="${operationsList.getAccountId()}">
                                <label class="d-flex">Subscription and operation name:
                                    <input name="name" placeholder="Enter name" class="form-control" required/>
                                </label>
                            </div>
                            <div class="mb-3">
                                <label class="d-flex">Operation amount:
                                    <input type="number" min="0.01" step=".01" name="salary" placeholder="Enter price"
                                           class="form-control"
                                           required/>
                                </label>
                            </div>
                            <div class="mb-3">
                                <label class="form-label d-flex">Operation category:
                                    <select class="form-select" name="category_id">
                                        <c:forEach items="${categories}" var="categories">
                                            <option value="${categories.getId()}"><c:out value="${categories.getName()}"/></option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                            <div class="modal-footer justify-content-center">
                                <input type="hidden" name="actionType" value="create">
                                <button type="submit" class="btn btn-outline-primary" style="width: 120px;">Add</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<jsp:include page="Footer.jsp" />

</body>
</html>
