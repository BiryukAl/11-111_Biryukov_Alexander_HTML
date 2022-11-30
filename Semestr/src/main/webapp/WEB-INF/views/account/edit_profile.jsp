<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">Account</h2>
    <div class="page_container">
        <%@include file="/WEB-INF/views/account/_account_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <c:if test="${not empty user_login }">

                <div class="sing_flex">
                    <form class="my_form" action="<c:url value="/profile/edit"/>" method="post">
                        <p>Name</p>
                        <input class="input_register" name="name" type="text"  <c:if test="${not empty name}">value="<c:out value="${name}"/>"</c:if> >
                        <p>Login</p>
                        <input class="input_register" name="login" <c:if test="${not empty login}">value="<c:out value="${login}"/>"</c:if> >
                        <p>Password</p>
                        <input class="input_register" name="old_password" type="password">
                        <br>
                        <input class="register_btn" type="submit" value="Edit">
                    </form>
                    <c:if test="${not empty message}">
                        <h4 class="for_server_msg">${message}</h4>
                    </c:if>
                    <a class="btn_item_file" href="<c:url value="/profile/edit/password"/>">Edit password</a>
                    <a class="btn_item_file" href="<c:url value="/profile/edit/delete/delete"/>">Delete profile</a>

                </div>

            </c:if>
            <c:if test="${empty user_login}">
                <h3 class="selected_page_h3">Sign In or Sing Up</h3>
            </c:if>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
