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
        #message p {
            font-size: 18px;
        }

        .valid {
            color: green;
        }

        .invalid {
            color: tomato;
        }

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
    </ul>
</div>
<div class="container mt-3">
    <h2>Update you're profile</h2>
    <form method="POST" action="/edit-user">
        <div class="mb-3 mt-3">
            <input type="hidden" value="<c:out value="${user.getId()}"/>" name="id"/>
        </div>
        <div class="mb-3">
            <label>Change username:
                <input name="loginNew" value="<c:out value="${user.getLogin()}"/>" class="form-control" required
                       min="3"/>
            </label>
        </div>
        <div class="mb-3">
            <label for="psw">Change password:
                <input type="password" id="psw" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                       title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                       class="form-control" required/>
            </label>
        </div>
        <div class="mb-3">
            <label for="oldPsw">For edit enter you're current password:
                <input type="password" id="oldPsw" name="oldPassword" class="form-control" required/>
            </label>
        </div>
        <div class="mb-3">
            <input type="hidden" name="actionType" value="edit">
        </div>
        <div class="mb-3">
            <button type="submit" class="btn btn-outline-primary"
                    onclick="showAlert()"> Edit
            </button>
        </div>
        <div id="message">
            <h4>Password must contain the following:</h4>
            <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
            <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
            <p id="number" class="invalid">A <b>number</b></p>
            <p id="length" class="invalid">Minimum <b>8 characters</b></p>
        </div>
        <br>
        <a href="/accounts">Return</a>
    </form>
</div>
</c:forEach>

<br>

<jsp:include page="Footer.jsp" />

<script>
    function showAlert() {
        <c:if test="${alert}">
        alert("Information was changed");
        </c:if>
        <c:if test="${!alert}">
        alert("Information wasn't changed");
        </c:if>
    }
</script>

<script>
    var myInput = document.getElementById("psw");
    var letter = document.getElementById("letter");
    var capital = document.getElementById("capital");
    var number = document.getElementById("number");
    var length = document.getElementById("length");

    // When the user clicks on the password field, show the message box
    myInput.onfocus = function () {
        document.getElementById("message").style.display = "block";
    }

    // When the user clicks outside of the password field, hide the message box
    myInput.onblur = function () {
        document.getElementById("message").style.display = "none";
    }

    // When the user starts to type something inside the password field
    myInput.onkeyup = function () {
        // Validate lowercase letters
        var lowerCaseLetters = /[a-z]/g;
        if (myInput.value.match(lowerCaseLetters)) {
            letter.classList.remove("invalid");
            letter.classList.add("valid");
        } else {
            letter.classList.remove("valid");
            letter.classList.add("invalid");
        }

        // Validate capital letters
        var upperCaseLetters = /[A-Z]/g;
        if (myInput.value.match(upperCaseLetters)) {
            capital.classList.remove("invalid");
            capital.classList.add("valid");
        } else {
            capital.classList.remove("valid");
            capital.classList.add("invalid");
        }

        // Validate numbers
        var numbers = /[0-9]/g;
        if (myInput.value.match(numbers)) {
            number.classList.remove("invalid");
            number.classList.add("valid");
        } else {
            number.classList.remove("valid");
            number.classList.add("invalid");
        }

        // Validate length
        if (myInput.value.length >= 8) {
            length.classList.remove("invalid");
            length.classList.add("valid");
        } else {
            length.classList.remove("valid");
            length.classList.add("invalid");
        }
    }
</script>

</body>
</html>

