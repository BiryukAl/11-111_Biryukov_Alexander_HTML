<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">Account</h2>
    <div class="page_container">
        <%@include file="/WEB-INF/views/account/_account_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <c:if test="${not empty user_login }">
                <h3 class="selected_page_h3">Your Name:</h3>
                <p class="setting_text">${user_name}</p>
                <h3 class="selected_page_h3">Your login:</h3>
                <p class="setting_text">${user_login}</p>
                <h3 class="selected_page_h3">Your ID:</h3>
                <p class="setting_text">${user_id}</p>
            </c:if>
            <c:if test="${empty user_login}">
                <h3 class="selected_page_h3">Sign In or Sing Up</h3>
            </c:if>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
