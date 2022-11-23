<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="setting_little_title">Account</h2>
    <div class="setting">
        <nav class="side_bar_setting">
            <div class="nav_item_setting">
                <h2 class="nav_item_title">Registration</h2>
            </div>
        </nav>
        <div class="page_selected_setting">
            <form class="register_form" action="<c:url value="register"/>" method="post">
                <p>Name</p>
                <input class="input_register" name="name" type="text"  <c:if test="${not empty name}">value="<c:out value="${name}"/>"</c:if> >
                <p>Login</p>
                <input class="input_register" name="login">
                <p>Password</p>
                <input class="input_register" name="password" type="password">
                <br>
                <input class="register_btn" type="submit" value="Register">
            </form>
            <c:if test="${not empty message}">
                <h4 class="register_msg_error">${message}</h4>
            </c:if>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>