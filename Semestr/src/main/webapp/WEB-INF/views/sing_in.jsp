<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">


    <h2 class="page_little_title">Account</h2>
    <div class="page_container">
        <nav class="side_bar_page">
            <a class="nav_item_page" href="<c:url value="/register"/>">
                <h2 class="nav_item_title">Registration</h2>
            </a>
            <a class="nav_item_page" href="<c:url value="/signin"/>">
                <h2 class="nav_item_title">Sign In</h2>
            </a>

        </nav>
        <div class="wrapper_selected_page">
            <div class="sing_flex">

                <form class="sign_form" action="<c:url value="signin"/>" method="post">
                    <p>Login</p>
                    <input class="input_register" name="login">
                    <p>Password</p>
                    <input class="input_register" name="password" type="password">
                    <br>
                    <input class="register_btn" type="submit" value="Sing In">
                </form>
                <c:if test="${not empty message}">
                    <h4 class="sign_msg_error">${message}</h4>
                </c:if>
            </div>

        </div>
    </div>
</div>


</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
