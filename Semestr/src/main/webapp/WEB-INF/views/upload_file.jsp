<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>

<div class="page_layout _container">
    <h2 class="page_little_title">Public Files</h2>
    <div class="page_container">
        <nav class="side_bar_page">
            <a class="nav_item_page" href="<c:url value="/main"/>">
                <h2 class="nav_item_title">Public File</h2>
            </a>
            <a class="nav_item_page" href="<c:url value="/myfiles"/>">
                <h2 class="nav_item_title">My File</h2>
            </a>
            <a class="nav_item_page" href="<c:url value="/upload"/>">
                <h2 class="nav_item_title">Upload</h2>
            </a>
        </nav>
        <div class="wrapper_selected_page">


            <c:if test="${not empty user_login }">
                <form class="sign_form" action="<c:url value="upload"/>" method="post" enctype="multipart/form-data">
                    <p>Title</p>
                    <input name="title" type="text">
                    <p>Description</p>
                    <input name="description" type="text">
                    <p>Public access</p><input name="public_access" type="checkbox">
                    <p>Choose file</p>
                    <input name="file" type="file">
                    <br>
                    <input type="submit" value="Upload">
                </form>
            </c:if>
            <c:if test="${empty user_login}">
                <h3 class="setting_h3">Sign In or Sing Up</h3>
            </c:if>

        </div>
    </div>
</div>


<%@include file="/WEB-INF/views/_footer.jsp" %>
