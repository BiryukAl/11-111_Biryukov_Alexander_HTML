<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>

<div class="page_layout _container">
    <h2 class="page_little_title">My Subscriptions</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <div class="header_nav_account">
                <a class="btn_item_file" href="<c:url value="/user/find"/>">
                    Find User
                </a>
            </div>
            <h3 class="selected_page_h3">Count subscriptions: ${count_subscriptions}</h3>
            <%@ include file="/WEB-INF/views/users/_forEach_user_friends.jsp" %>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/_footer.jsp" %>
