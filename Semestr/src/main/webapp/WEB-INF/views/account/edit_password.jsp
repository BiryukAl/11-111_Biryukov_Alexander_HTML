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
                    <form class="sign_form" action="<c:url value="editpassword"/>" method="post">
                        <p>Old Password</p>
                        <input class="input_register" name="old_password" type="password">
                        <p>New Password</p>
                        <input class="input_register" name="new_password" type="password">
                        <br>
                        <input class="register_btn" type="submit" value="Edit">
                    </form>
                    <c:if test="${not empty message}">
                        <h4 class="for_server_msg">${message}</h4>
                    </c:if>
                    <a class="btn_item_file" href="<c:url value="/editpassword"/>">Edit password</a>

                </div>

            </c:if>
            <c:if test="${empty user_login}">
                <h3 class="selected_page_h3">Sign In or Sing Up</h3>
            </c:if>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
