<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">


    <h2 class="page_little_title">Account</h2>
    <div class="page_container">
        <%@include file="/WEB-INF/views/sign/_sign_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <div class="sing_flex">

                <form class="sign_form" action="<c:url value="signin"/>" method="post">
                    <p>Login</p>
                    <input class="input_register" name="login" <c:if test="${not empty login}">value="<c:out value="${login}"/>"</c:if>>
                    <p>Password</p>
                    <input class="input_register" name="password" type="password">
                    <br>
                    <input class="register_btn" type="submit" value="Sing In">
                </form>
                <c:if test="${not empty message}">
                    <h4 class="for_server_msg">${message}</h4>
                </c:if>
            </div>

        </div>
    </div>
</div>


</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
