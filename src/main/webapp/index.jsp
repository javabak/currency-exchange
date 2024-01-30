<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
    <h1>
        <%
            Date now = new Date();
            String dateText = "Текущее время: " + now;
        %>
        <%= dateText %>
    </h1>
</body>
</html>
