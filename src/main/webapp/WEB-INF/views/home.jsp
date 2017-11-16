<%@ page language="java" contentType="text/html; ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Website CSS style -->
    <link href="../../resources/css/bootstrap.css" rel='stylesheet' type='text/css'/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../../resources/js/jquery-1.11.0.min.js"></script>
    <!-- Custom Theme files -->
    <%--<link href="/WEB-INF/template/css/style.css" rel='stylesheet' type='text/css' />--%>
    <!-- Custom Theme files -->
    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
    <title>HomePage</title>
</head>

<body>
<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Distributed Net - Fastspark</a>
            <form:form method="get" action="/leave">
                <div class="form-group pull-right">
                    <button class="btn btn-success" type="submit">Leave</button>
                </div>
            </form:form>
        </div>
    </div><!-- /.container -->
</nav><!-- /.navbar -->

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-lg-3 sidebar-offcanvas" id="sidebar">
            <div class="list-group">
                <a href="#" class="list-group-item active">Bootstrap IP: ${Client.bootrapIp}</a>
                <a href="#" class="list-group-item active">Node IP: ${Client.ip}</a>
                <a href="#" class="list-group-item active">Node Port: ${Client.port}</a>
                <a href="#" class="list-group-item active">Bucket ID: ${Client.myBucketId}</a>
            </div>
        </div><!--/.sidebar-offcanvas-->

        <div class="col-lg-9">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>My File List</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="file" items="${Client.myFileList}">
                    <tr>
                        <td>"${file}"</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-lg-6">
            <h4>File Contain In My Bucket</h4>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>file</th>
                    <th>Node List</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="map" items="${Client.fileDictionary}">
                    <tr>
                        <td>"${map.key}"</td>
                        <td>"${map.value}"</td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-lg-6">
            <h4>My Bucket Table</h4>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>bucketId</th>
                    <th>Connected Neighbour Ip</th>
                    <th>Connected Port</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="map" items="${Client.bucketTable}">
                    <tr>
                        <td>"${map.key}"</td>
                        <td>"${map.value.ip}"</td>
                        <td>"${map.value.port}"</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div><!--/.col-xs-12.col-sm-9-->

    <div class="col-lg-6">
        <form:form method="post" action="/search" commandName="SearchResult">

            <div class="form-group">
                <label for="search" class="cols-sm-2 control-label">Search File </label>
                <div class="cols-sm-10">
                    <div class="input-group">
                        <form:input path="searchFile" type="text" class="form-control" name="search" id="search"
                                    placeholder="${Client.currentSearch}"/>
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <form:button class="btn btn-success" type="submit">Search</form:button>
            </div>

        </form:form>
        <div class="form-group">
            <label for="result">Result Of File Search</label>
            <textarea class="form-control" rows="5" id="result">${Client.searchResult}"</textarea>
        </div>
    </div>
</div><!--/row-->
</div><!--/.container-->

<hr>

<footer>
    <p style="margin-left: 50px">&copy; 2016 Company, Inc.</p>
</footer>
</body>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
<script src="offcanvas.js"></script>
</html>