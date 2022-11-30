<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
    <h2 class="page_little_title">Public Files</h2>
    <div class="page_container">
        <%@ include file="/WEB-INF/views/page_file/_page_files_side_bar_page.jsp" %>
        <div class="wrapper_selected_page">
            <div class="all_btn_item_file">
                <c:if test="${ page > 1}">
                    <a class="btn_item_file" href="<c:url value="/main?page=${page}&shift=1"/>">Previous page</a>
                </c:if>
                <a class="btn_item_file" >${page}</a>
                <c:if test="${empty is_next}">
                    <a class="btn_item_file" href="<c:url value="/main?page=${page}&shift=2"/>">Next page</a>
                </c:if>


            </div>
                <%@ include file="/WEB-INF/views/page_file/_forEach_public_files.jsp" %>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
