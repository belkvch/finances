<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
          <a class="nav-link" href="/archive-account?id=${sessionScope.id}">Archive</a>
        </li>
        <c:if test="${sessionScope.role == 2}">
          <li class="nav-item">
            <a class="nav-link" href="/admin">Users</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/admin-category">Categories</a>
          </li>
        </c:if>
      </ul>
    </div>
    <div class="dropdown">
      <button type="button" class="btn btn-outline-primary dropdown-toggle btn-sm" data-bs-toggle="dropdown">
        Account info
      </button>
      <ul class="dropdown-menu">
        <li><span class="dropdown-item-text">Login: ${sessionScope.login} </span></li>
        <li><span class="dropdown-item-text">ID: ${sessionScope.id} </span></li>
        <li><span class="dropdown-item-text"><a href="/edit-user?id=${sessionScope.id}">Edit profile</a> </span></li>
      </ul>
    </div>
    <div class="d-flex">
      <form method="post" action="/logout" class="d-flex" style="margin-bottom: 0">
        <input type="hidden">
        <button type="submit" class="btn btn-outline-primary btn-sm"
                onclick="return confirm('Are you sure to sign out?');">Sign Out
        </button>
      </form>
    </div>
  </div>
</nav>