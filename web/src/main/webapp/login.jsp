<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <title>Login</title>
</head>
<body>

<center>
    <div class="container mt-3">
        <h2 class="display-5"> Registration to CatCash: </h2>
        <form method="POST" action="/login">
            <div class="container mt-3">
                <label>Login <input name="login" class="form-control" required/></label>
            </div>
            <div class="container mt-3">
                <label>Password <input type="password" name="password" class="form-control" required/></label>
            </div>
            <input type="hidden" name="actionType" value="create">
            <br>
            <button type="submit" class="btn btn-outline-primary">Login</button>
            <br>
            You still don't have an account? <a href="/registration">Register!</a> It's free:)
        </form>
    </div>
</center>

</body>
</html>
