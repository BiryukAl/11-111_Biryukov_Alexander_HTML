<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="side_bar_page">
    <div class="nav_item_page">
        <a href="<c:url value="/profile"/>">
            <h2 class="nav_item_title">Personal data</h2>
        </a>
    </div>
    <div class="nav_item_page">
        <h2 class="nav_item_title">Edit data</h2>
    </div>
    <div class="nav_item_page">
        <h2 class="nav_item_title">Logs</h2>
    </div>
    <div class="nav_item_page">
        <a href="<c:url value="/sign_out"/>">
            <h2 class="nav_item_title">Sign Out</h2>
        </a>
    </div>
</nav>
