<%@ page language="java" contentType="text/html; ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>

<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Start Page</title>
    <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css"/>

    <script src="bower_components/jquery/dist/jquery.min.js"></script>
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">

    <h1 class="form-inline">
        Distributed Net - Fastspark <input type="text" class="form-control btn btn-info" id="connect" value="Connect"
                                           readonly="readonly" style="margin-left: 50px"/><input type="text"
                                                                                                 class="form-control btn btn-info"
                                                                                                 id="leave"
                                                                                                 value="Leave"
                                                                                                 readonly="readonly"
                                                                                                 style="margin-left: 30px"/>
    </h1>

    <div class="row">
        <div class="col-md-12">
            <div class="col-md-9" style="margin-top: 50px; margin-left: -30px">
                <div class="col-md-3">
                    Enter Search String:
                </div>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="filesearchstring"
                           placeholder="File Name"/>
                </div>
                <div class="col-md-3">
                    <input type="button" class="form-control btn btn-success" id="search"
                           value="Search"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row well" style="margin-top: 50px">
        <div class="col-md-12">
            <div id="resultarea">

            </div>
        </div>
    </div>

    <div>
        <input type="text" value="${nodeIP}" id="ip"/>
        <input type="text" value="${nodePort}" id="port"/>
    </div>
</div>
<script src="handle_requests.js"></script>
</body>
</html>