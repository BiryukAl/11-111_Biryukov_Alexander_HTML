<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">Search</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">

            <c:forEach items="${items_search_files}" var="file">
                <div class="container_item_file">
                    <h3 class="item_file_title"> ${file.title}</h3>
                    <h4 class="item_file_description">${file.description}</h4>
                    <p class="item_file_owner">Owner: ${file.holderId} </p>

                    <div class="all_btn_item_file">
                        <a class="btn_item_file" href="<c:url value="/file/download?idFile=${file.id}"/>">Download</a>
                        <c:if test="${user_id} == ${file.holderId}">
                            <a class="btn_item_file" href="<c:url value="/file/delete?id/file/download=${file.id}"/>">Delete</a>
                            <a class="btn_item_file" href="<c:url value="/file/edit?idFile=${file.id}"/>">Edit</a>
                            <a class="btn_item_file" href="<c:url value="/file/edit/access?idFile=${file.id}"/>">Edit Access</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>


        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
