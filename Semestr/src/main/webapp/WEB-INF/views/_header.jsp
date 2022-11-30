<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title_page}</title>
<%--    <link rel="stylesheet" href="<c:url value="/style.css"/>">--%>
    <style rel="text/css">
        <%@ include file="/WEB-INF/fix_style.jsp" %>
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="<c:url value="../js/main.js"/>"></script>
    <script>
        <%@ include file="/WEB-INF/fix_script.jsp" %>
    </script>
</head>
<body>
<div class="page_wrapper">
    <header>
        <div class="header ">
            <div class="_container">
                <div class="header_nav">
                    <div class="header_logo">
                        <a class="link_nav_head" href="<c:url value="/"/>">
                            <h1 class="header_h1">ProduceDisk</h1>
                        </a>
                    </div>
                    <form action="<c:url value="/files/search"/>" method="post" >
                        <input name="title" type="text" placeholder="Title file">
                        <input type="submit" value="Search">
                    </form>
                    <nav class="header_nav_account">
                        <c:if test="${empty user_login}">
                                <a class="nav_item_header" href="<c:url value="/account/sign_in"/>">Sing In</a>
                                <a class="nav_item_header" href="<c:url value="/account/sign_up"/>">Sing Up</a>
                        </c:if>
                        <c:if test="${not empty user_login}">
                                <a class="nav_item_header" href="<c:url value="/profile"/>">${user_name}</a>
                        </c:if>
                    </nav>
                </div>
            </div>
        </div>
    </header>
