<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="setting_little_title">Account</h2>
    <div class="setting">
        <nav class="side_bar_setting">
            <div class="nav_item_setting">
                <h2 class="nav_item_title">Personal data</h2>
            </div>
            <div class="nav_item_setting">
                <h2 class="nav_item_title">Edit data</h2>
            </div>
            <div class="nav_item_setting">
                <h2 class="nav_item_title">Logs</h2>
            </div>
        </nav>
        <div class="page_selected_setting">
            <h3 class="setting_h3">Your Name:</h3>
            <p class="setting_text">${user_name}</p>
            <h3 class="setting_h3">Your login:</h3>
            <p class="setting_text">${user_login}</p>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
