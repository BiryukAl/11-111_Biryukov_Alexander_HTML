<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">My Files</h2>
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


        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
