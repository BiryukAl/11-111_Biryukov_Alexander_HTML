<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="side_bar_page">
    <a class="nav_item_page" href="<c:url value="/"/>">
        <h2 class="nav_item_title">Public File</h2>
    </a>
    <a class="nav_item_page" href="<c:url value="/files/my"/>">
        <h2 class="nav_item_title">My File</h2>
    </a>
    <a class="nav_item_page" href="<c:url value="/user/find"/>">
        <h2 class="nav_item_title">Find User</h2>
    </a>
    <a class="nav_item_page" href="<c:url value="/subscriptions"/>">
        <h2 class="nav_item_title">Subscription</h2>
    </a>
    <a class="nav_item_page" href="<c:url value="/files/search"/>">
        <h2 class="nav_item_title">Search</h2>
    </a>
</nav>
