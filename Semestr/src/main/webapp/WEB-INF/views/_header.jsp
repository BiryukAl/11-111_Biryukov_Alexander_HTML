<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title_page}</title>
    <link rel="stylesheet" href="<c:url value="/style.css"/>">
</head>
<body>
<div class="page_wrapper">
    <header>
        <div class="header ">
            <div class="_container">
                <div class="header_nav">
                    <div class="header_logo">
                        <a class="link_nav_head" href="<c:url value="/main"/>">
                            <h1 class="header_h1">ProduceDisk</h1>
                        </a>
                    </div>
                    <nav class="header_nav_account">
                        <c:if test="${empty user_login}">
                                <a class="nav_item_header" href="<c:url value="/signin"/>">Sing In</a>
                                <a class="nav_item_header" href="<c:url value="/register"/>">Sing Up</a>
                        </c:if>
                        <c:if test="${not empty user_login}">
                            <div class="nav_item_header">
                                <a class="link_nav_head" href="<c:url value="/profile"/>">${user_name}</a>
                            </div>
                        </c:if>
                    </nav>
                </div>
            </div>
        </div>
    </header>
