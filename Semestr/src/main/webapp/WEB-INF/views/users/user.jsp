<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">User</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <h3 class="selected_page_h3">User Name: </h3>
            <p class="setting_text">${friend_name}</p>
            <h3 class="selected_page_h3">Subscribers: </h3>
            <p class="setting_text">${subscribers}</p>
            <h3 class="selected_page_h3">User ID: </h3>
            <p class="setting_text">${friend_id}</p>
            <c:if test="${is_signed}">

                <div class="all_btn_item_file">
                    <button class="btn_item_file" id="btn_subscribe" onclick="subscribe(${friend_id})"   <c:if test="${is_friend}">hidden = "hidden"</c:if>    >Subscribe</button>
                    <button class="btn_item_file" id="btn_unsubscribe" onclick="unsubscribe(${friend_id})" <c:if test="${not is_friend}">hidden = "hidden"</c:if> >Unsubscribe</button>
                </div>
                
                


            </c:if>
            <c:forEach items="${items_friend_files}" var="file">
                <div class="container_item_file">
                    <h3 class="item_file_title"> ${file.title}</h3>
                    <h4 class="item_file_description">${file.description}</h4>
                    <p class="item_file_link">Link for DOWNLOAD: /file/download?idFile=${file.id}</p>
                    <div class="all_btn_item_file">
                        <a class="btn_item_file" href="<c:url value="/file/download?idFile=${file.id}"/>">Download</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
