<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 14.12.2016
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="/WEB-INF/" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals List</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meals list</h2>
<h1>
    <table border="1" cellpadding="5">
        <tr>
            <th>DateTime</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach var="meal" items="${list}">
            <c:choose>
                <c:when test="${meal.exceed eq true}">
                    <tr style="color: red;">
                </c:when>
                <c:otherwise>
                    <tr style="color: green";>
                </c:otherwise>
            </c:choose>
            <td><c:out value="${f:matches(meal.dateTime, 'yyyy-MM-dd HH:mm')}" />
            </td>
            <td><c:out value="${meal.description}" />
            </td>
            <td><c:out value="${meal.calories}" />
            </td>
        </tr>
        </c:forEach>
    </table>
</h1>
</body>
</html>
