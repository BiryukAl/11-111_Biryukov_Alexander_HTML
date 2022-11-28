<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>

<div class="page_layout _container">
    <h2 class="page_little_title">My Subscriptions</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <form class="sign_form" action="<c:url value="/files/search"/>" method="post">
                <p>User ID</p>
                <input name="idUser" type="text" <c:if test="${not empty idUser}">value="<c:out value="${idUser}"/>"</c:if>>
                <br>
                <input type="submit" value="Search">
            </form>
            <c:if test="${not empty message}">
                <h4 class="for_server_msg">${message}</h4>
            </c:if>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/_footer.jsp" %>
