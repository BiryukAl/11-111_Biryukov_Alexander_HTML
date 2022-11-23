<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <form action="<c:url value="upload"/>" method="post" enctype="multipart/form-data">
        <p>Title</p>
        <input name="title" type="text">
        <p>Description</p>
        <input name="description" type="text">
        <p>Public access</p>
        <input name="public_access" type="checkbox">
        <p>Choose file</p>
        <input name="file" type="file">
        <input type="submit" value="Upload">
    </form>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
