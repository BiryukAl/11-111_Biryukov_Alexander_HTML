<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${items_friends}" var="friend">
  <div class="container_item_file">
    <h3 class="item_file_title">Name: ${friend.name}</h3>
    <h4 class="item_file_description">Id: ${friend.id}</h4>
    <div class="all_btn_item_file">
      <a class="btn_item_file" href="<c:url value="/subscriptions/unsubscribe?idUser=${friend.id}"/>">Unsubscribe</a>
      <a class="btn_item_file" href="<c:url value="/user?idUser=${friend.id}"/>">Go to Profile</a>

    </div>




  </div>
</c:forEach>
