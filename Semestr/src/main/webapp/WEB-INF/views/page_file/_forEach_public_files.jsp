<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${items_public_files}" var="file">
    <div class="container_item_file">
        <h3 class="item_file_title"> ${file.title}</h3>
        <h4 class="item_file_description">${file.description}</h4>
        <a class="btn_item_file"
           href="<c:url value="/user?idUser=${file.holderId}"/>">Owner: ${file.nameHolder}</a>
        <div class="all_btn_item_file">
            <a class="btn_item_file" href="<c:url value="/file/download?idFile=${file.id}"/>">Download</a>
            <c:if test="${user_id} == ${file.holderId}">
                <a class="btn_item_file" href="<c:url value="/file/delete?idFile=${file.id}"/>">Delete</a>
                <a class="btn_item_file" href="<c:url value="/file/edit?idFile=${file.id}"/>">Edit</a>
                <a class="btn_item_file" href="<c:url value="/file/edit/access?idFile=${file.id}"/>">Edit
                    Access</a>
            </c:if>
        </div>
        <p class="item_file_link">Link for DOWNLOAD: /file/download?idFile=${file.id}</p>
    </div>
</c:forEach>
