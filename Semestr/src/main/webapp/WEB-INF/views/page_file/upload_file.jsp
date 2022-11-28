<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>

<div class="page_layout _container">
    <h2 class="page_little_title">Upload</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <c:if test="${not empty user_login }">
                <form class="sign_form" action="<c:url value="/file/upload"/>" method="post" enctype="multipart/form-data">
                    <p>Title</p>
                    <input name="title" type="text">
                    <p>Description</p>
                    <input name="description" type="text">
                    <p>Public access</p>
                    <input name="public_access" type="checkbox">
                    <p>User access <b>(input id separated by a space)</b></p>
                    <input name="user_access" type="text">
                    <p>Choose file</p>
                    <input name="file" type="file">
                    <br>
                    <input type="submit" value="Upload">
                </form>
            </c:if>
            <c:if test="${empty user_login}">
                <h3 class="selected_page_h3">Sign In or Sing Up</h3>
            </c:if>

        </div>
    </div>
</div>


<%@include file="/WEB-INF/views/_footer.jsp" %>
