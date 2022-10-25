<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/_header.jsp" %>



<div class="register">
    <div>
        <h2>Register</h2>
        <br>
        <form class="register_form">
            <p>Name</p>
            <input class="input_register">
            <p>Login</p>
            <input class="input_register">
            <p>Password</p>
            <input class="input_register">
        </form>
        <br>
        <button class="register_btn">
            Register
        </button>
    </div>
</div>



<%@include file="/WEB-INF/views/_footer.jsp" %>