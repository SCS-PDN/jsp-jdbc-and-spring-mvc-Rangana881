<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>
<form method="post" action="/login">
    Email: <input type="text" name="email" /><br>
    Password: <input type="password" name="password" /><br>
    <button type="submit">Login</button>
</form>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
</body>
</html>
