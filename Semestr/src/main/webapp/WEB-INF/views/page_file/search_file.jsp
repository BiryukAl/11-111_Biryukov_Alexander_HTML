<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>

<div class="page_layout _container">
    <h2 class="page_little_title">Search</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <form class="my_form" action="<c:url value="/files/search"/>" method="post">
                <p>Title</p>
                <input name="title" type="text" <c:if test="${not empty title}">value="<c:out value="${title}"/>"</c:if> >
                <p>Description</p>
                <input name="description" type="text" <c:if test="${not empty description}">value="<c:out value="${description}"/>"</c:if> >
                <br>
                <input type="submit" value="Search">
            </form>
            <c:if test="${not empty message}">
                <h4 class="for_server_msg">${message}</h4>
            </c:if>
            <c:if test="${not empty items_public_files}">
                <%@ include file="/WEB-INF/views/page_file/_forEach_public_files.jsp" %>
            </c:if>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
