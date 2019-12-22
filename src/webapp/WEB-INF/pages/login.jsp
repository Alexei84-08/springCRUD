<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="POST">
    <dl>
        <dt>Login</dt>
        <dd><input type="text" name="login"></dd>
    </dl>
    <dl>
        <dt>Password</dt>
        <dd><input type="password" name="password"></dd>
    </dl>
    <button type="submit">Войти</button>
</form>
</body>
</html>
