<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Operations</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/main">
            <img src="https://avatanplus.com/files/resources/original/593c1cca299df15c92d075ba.png" alt="Logo"
                 style="width:40px;"
                 class="rounded-pill">
        </a>
        <a class="navbar-brand" href="/main">CatCash</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/accounts">Accounts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/currency">Currency</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin">Users</a>
                </li>
            </ul>
        </div>
        <div class="d-flex">
            <form method="post" action="/logout" style="display:inline;" class="d-flex">
                <input type="hidden">
                <button type="submit" class="btn btn-outline-primary btn-sm"
                        onclick="return confirm('Are you sure to sign out?');">Sign Out
                </button>
            </form>
        </div>
    </div>
</nav>

<center>
    <h2 class="display-5"> Welcome, ${sessionScope.login}</h2>
</center>
<br>

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
                            <c:if test="${categories.getId() != 47}">
                            <c:if test="${categories.getId() != 52}">
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
                                <c:if test="${categories.getId() != 47}">
                                <c:if test="${categories.getId() != 52}">
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

<div class="container mt-3">
    <h2>Add new necessary category:</h2>
    <form method="POST" action="/admin-category">
        <div class="mb-3 mt-3">
            <label>Category name:
                <input name="categoryName" placeholder="Enter name" class="form-control" required/>
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


<footer class="text-center text-lg-start bg-light text-muted">
    <section
            class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
        <div class="me-5 d-none d-lg-block">
            <span>Get connected with us on social networks:</span>
        </div>
        <div>
            <a href="https://www.instagram.com/belkvch/" class="me-4 text-reset">
                Instagram
            </a>
            <a href="https://www.linkedin.com/in/polina-belkevich-a28123230/" class="me-4 text-reset">
                Linkedin
            </a>
            <a href="https://gitlab.com/belkvch" class="me-4 text-reset">
                GitLab
            </a>
        </div>
    </section>
    <section class="">
        <div class="container text-center text-md-start mt-5">
            <div class="row mt-3">
                <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                    <h6 class="text-uppercase fw-bold mb-4">
                        CatCash
                    </h6>
                    <p>We are small Belarusian studio, which develops financial web2 apps.
                        Our mission is to make your operations accurate and life is easier.
                    <p>Inspiring leader, that builds amazing products and bring innovative ideas to life.
                    </p>
                </div>
                <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                    <h6 class="text-uppercase fw-bold mb-4">
                        Contact
                    </h6>
                    <p> Belarus, Minsk</p>
                    <p> pbelkevich@gmail.com</p>
                    <p> + 375(29)844-65-98</p>
                </div>
            </div>
        </div>
    </section>
    <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
        © 2022 Copyright:
        <a class="text-reset fw-bold" href="https://www.netcracker.com/">netcracker.com</a>
    </div>
</footer>

</body>
</html>
