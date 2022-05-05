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
    <h2>Categories: </h2>
    <table class="table table-borderless">
        <thead>
        <tr>
            <th><b>Name</b></th>
            <th><b>Edit</b></th>
            <th><b>Delete</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categories}" var="categories">
            <tr>
                <td><c:out value="${categories.getName()}"/>
                </td>
                <td>
                    <form method="POST" action="/updateCategoryAdmin">
                        <div class="mb-3">
                            <c:if test="${categories.getId() != 1}">
                            <c:if test="${categories.getId() != 2}">
                            <label>
                                <input name="category_name" class="form-control" required/>
                            </label>
                            <input type="hidden" name="category_id" value="${categories.getId()}">
                            <button type="submit" class="btn btn-outline-primary">Change</button>
                            </c:if>
                            </c:if>
                        </div>
                    </form>
                </td>
                <td>
                    <form method="POST" action="/deleteCategoryAdmin">
                            <div class="mb-3">
                                <c:if test="${categories.getId() != 1}">
                                <c:if test="${categories.getId() != 2}">
                                <input type="hidden" name="category_id" value="${categories.getId()}">
                                <button type="submit" class="btn btn-outline-primary"
                                        onclick="return confirm('Are you sure you want to delete this item?');">Delete
                                </button>
                                </c:if>
                                </c:if>
                            </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="container mt-3 mb-3">
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdropAddNC">
        Add new category
    </button>
    <div class="modal fade" id="staticBackdropAddNC" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Add new category</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form method="POST" action="/admin-category" class="mb-0">
                        <div class="mb-3 mt-3">
                            <label class="d-flex">Category name:
                                <input name="categoryName" placeholder="Enter name" class="form-control" required/>
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


<jsp:include page="Footer.jsp" />

</body>
</html>
