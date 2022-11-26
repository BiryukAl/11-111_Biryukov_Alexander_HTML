<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="side_bar_page">
    <c:if test="${not empty user_login }">
        <div class="nav_item_page">
            <a href="<c:url value="/profile"/>">
                <h2 class="nav_item_title">Personal data</h2>
            </a>
        </div>
        <div class="nav_item_page">
            <a href="<c:url value="/editprofile"/>">
                <h2 class="nav_item_title">Edit profile</h2>
            </a>
        </div>
        <div class="nav_item_page">
            <a href="<c:url value="/deleteprofile"/>">
                <h2 class="nav_item_title">Delete profile</h2>
            </a>
        </div>
        <div class="nav_item_page">
            <a href="<c:url value="/sign_out"/>">
                <h2 class="nav_item_title">Sign Out</h2>
            </a>
        </div>
    </c:if>
</nav>
