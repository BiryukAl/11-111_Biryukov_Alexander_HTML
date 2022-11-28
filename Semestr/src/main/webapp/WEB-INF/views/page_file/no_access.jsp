<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_header.jsp" %>
<div class="page_layout _container">
  <h2 class="page_little_title">NO ACCESS</h2>
  <div class="page_container">
    <nav class="side_bar_page">
      <a class="nav_item_page" href="<c:url value="/"/>">
        <h2 class="nav_item_title">Back to main</h2>
      </a>
    </nav>
    <div class="wrapper_selected_page">
      <h3 class="selected_page_h3">NO ACCESS</h3>
    </div>
  </div>
</div>
<%@include file="/WEB-INF/views/_footer.jsp" %>
