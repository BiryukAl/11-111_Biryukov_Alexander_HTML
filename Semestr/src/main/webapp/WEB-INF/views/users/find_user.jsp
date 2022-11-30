<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>

<div class="page_layout _container">
    <h2 class="page_little_title">Find User</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <form class="my_form" action="<c:url value="/user/find"/>" method="post">
                <p>User ID</p>
                <input name="idUser" type="text" <c:if test="${not empty idUser}">value="<c:out value="${idUser}"/>"</c:if>>
                <br>
                <input type="submit" value="Search by Id">
            </form>
            <br>
            <form class="my_form" action="<c:url value="/user/find"/>" method="post">
                <p>Name user</p>
                <input name="find_name_user" type="text" <c:if test="${not empty find_name_user}">value="<c:out value="${find_name_user}"/>"</c:if>>
                <br>
                <input type="submit" value="Search by Name">
            </form>

            <c:if test="${not empty message}">
                <h4 class="for_server_msg">${message}</h4>
            </c:if>

            <c:if test="${not empty items_friends}">
                <c:forEach items="${items_friends}" var="friend">
                    <div class="container_item_file">
                        <h3 class="item_file_title">Name: ${friend.name}</h3>
                        <h4 class="item_file_description">Id: ${friend.id}</h4>
                        <div class="all_btn_item_file">
                            <a class="btn_item_file" href="<c:url value="/user?idUser=${friend.id}"/>">Go to Profile</a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>

        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/_footer.jsp" %>
