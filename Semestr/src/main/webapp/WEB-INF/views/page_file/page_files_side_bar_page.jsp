<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="side_bar_page">
    <a class="nav_item_page" href="<c:url value="/main"/>">
        <h2 class="nav_item_title">Public File</h2>
    </a>
    <a class="nav_item_page" href="<c:url value="/myfiles"/>">
        <h2 class="nav_item_title">My File</h2>
    </a>
    <a class="nav_item_page" href="<c:url value="/upload"/>">
        <h2 class="nav_item_title">Upload</h2>
    </a>
    <a class="nav_item_page" href="<c:url value="/search"/>">
        <h2 class="nav_item_title">Search</h2>
    </a>
</nav>
