<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">My Files</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <c:if test="${not empty user_login }">
                <c:forEach items="${items_my_files}" var="file">
                    <div class="container_item_file">
                        <h3 class="item_file_title"> ${file.title}</h3>
                        <h4 class="item_file_description">${file.description}</h4>
                        <p class="item_file_link">Link for download?idFile=${file.id}</p>
                        <div class="all_btn_item_file">
                            <a class="btn_item_file" href="<c:url value="/download?idFile=${file.id}"/>">Download</a>
                            <a class="btn_item_file" href="<c:url value="/delete?idFile=${file.id}"/>">Delete</a>
                            <a class="btn_item_file" href="<c:url value="/editfile?idFile=${file.id}"/>">Edit</a>
                            <a class="btn_item_file" href="<c:url value="/editfileaccess?idFile=${file.id}"/>">Edit Access</a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty user_login}">
                <h3 class="selected_page_h3">Sign In or Sing Up</h3>
            </c:if>

        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
