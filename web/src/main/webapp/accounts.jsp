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
            <th><b>Categories</b></th>
            <th><b>Add</b></th>
            <th><b>Transfer</b></th>
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
                    <td><a href="/category?id=${accounts.id}">Show</a>
                    <td>
                        <form method="POST" action="/updateAccount">
                            <div class="d-flex align-items-center">
                                <label>
                                    <input type="number" min="0.01" step=".01" name="amount" class="form-control"
                                           required/>
                                </label>
                                <input type="hidden" name="id" value="${accounts.getId()}">
                                <button type="submit" class="btn btn-outline-primary">Add</button>
                            </div>
                        </form>
                    </td>

                    <td>
                        <form method="POST" action="/transfer">
                            <div class="d-flex align-items-center">
                                <label style="min-width: 100px;margin-right: 5px;">
                                    <input type="number" min="0.01" step=".01" name="transfer_amount" class="form-control"
                                           required/>
                                </label>
                                <label class="form-label" style="margin-bottom: 0">
                                    <select class="form-select" name="account_id">
                                        <c:forEach items="${accountsList}" var="accountsList">
                                            <c:if test="${accountsList.getId() != accounts.getId()}">
                                                <option value="${accountsList.getId()}">
                                                    <c:out value="${accountsList.getId()}"/></option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </label>
                                <input type="hidden" name="id" value="${accounts.getId()}">
                                <input type="hidden" name="actionType" value="transfer">
                                <button type="submit" class="btn btn-outline-primary">Add</button>
                            </div>
                        </form>
                    </td>
                </tr>

            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="container mt-3 mb-3">
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
        Add new account
    </button>
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Add new account</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form method="POST" action="/accounts" class="mb-0">
                        <div class="mb-3">
                            <label class="d-flex">
                                Account balance:
                                <input type="number" min="0.01" step=".01" name="amount" placeholder="Enter price" class="form-control"
                                       required/>
                            </label>
                        </div>

                        <div class="d-flex align-items-center">
                            <p class="mb-0" style="margin-right: 1rem;">Account currency:</p>
                            <div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="radio1" name="currency_id" value="1" checked>
                                    <label class="form-check-label" for="radio1">BYN</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="radio2" name="currency_id" value="2">
                                    <label class="form-check-label" for="radio2">EUR</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="radio3" name="currency_id" value="3">
                                    <label class="form-check-label" for="radio3">USD</label>
                                </div>
                            </div>
                        </div>

                        <div class="container mt-3 mb-3 p-0 d-flex justify-content-center flex-column">
                            <button type="button" class="btn btn-primary" data-bs-toggle="collapse" data-bs-target="#demo">Add account with other user</button>
                            <div id="demo" class="collapse">
                                <div class="mb-3">
                                    <label>Enter the user's id:
                                        <input type="number" min="0" step="1" name="with_user_id" value="0" placeholder="Enter id" class="form-control" required/>
                                    </label>
                                </div>
                            </div>
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

<jsp:include page="Footer.jsp" />


</body>
</html>

