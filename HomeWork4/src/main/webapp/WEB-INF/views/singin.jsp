<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>


<div class="register">
    <div>

        <h2 class="register_title">Register</h2>

        <form class="register_form" action="<c:url value="register"/>" method="post">
            <p>Name</p>
            <input class="input_register" name="name" type="text">
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


<%@include file="/WEB-INF/views/_footer.jsp" %>