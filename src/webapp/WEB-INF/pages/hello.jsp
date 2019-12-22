<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<jsp:include page="logout.jsp"/>
<br>
<c:forEach var="msq" items="${messages}">
    <h1>${msq}</h1>
</c:forEach>
<br>
</body>
</html>