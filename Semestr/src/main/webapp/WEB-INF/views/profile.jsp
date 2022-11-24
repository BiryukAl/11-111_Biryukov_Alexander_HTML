<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">Account</h2>
    <div class="page_container">
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
        <div class="wrapper_selected_page">
            <c:if test="${not empty user_login }">

                <h3 class="setting_h3">Your Name:</h3>
                <p class="setting_text">${user_name}</p>
                <h3 class="setting_h3">Your login:</h3>
                <p class="setting_text">${user_login}</p>
            </c:if>
            <c:if test="${empty user_login}">
                <h3 class="setting_h3">Sign In or Sing Up</h3>
            </c:if>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
