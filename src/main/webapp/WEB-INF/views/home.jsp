<%@ page language="java" contentType="text/html; ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Website CSS style -->
    <link href="../../resources/css/bootstrap.css" rel='stylesheet' type='text/css' />
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
    <div class="container">

            <div class="row" >

                <div class="col-lg-6" >

                    <div class="form-control" >
                        <label class="control-label col-sm-6" for="bootrapIp">bootrapIp</label>
                        <input class="col-sm-6" type="text" name="bootrapIp" id="bootrapIp" value="${Client.bootrapIp}"><br>
                    </div>
                    <div class="form-control">
                        <label class="col-sm-6" for="ip">Node Ip</label>
                        <input class="col-sm-6" type="text" name="ip" id="ip" value="${Client.ip}"><br>
                    </div>
                    <div class="form-control">
                        <label class="col-sm-6" for="port">Node Port</label>
                        <input class="col-sm-6" type="text"  name="port" id="port" value="${Client.port}"><br>
                    </div>

                    <div class="form-control">
                        <label class="col-sm-6" for="bucketId">Bucket Id</label>
                        <input class="col-sm-6" type="text" name="bucketId" id="bucketId" value="${Client.myBucketId}"><br>
                    </div>

                </div>

                <div class="col-lg-6">
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
            </div>

            <div class="row">

                <div class="col-lg-6">
                    <h3>File Contain In My Bucket</h3>
                    <table class="table table-bordered">
                        <thead >
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

                    <h2>My Bucket Table</h2>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>bucketId</th>
                                <th>Connected Neighbour</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="map" items="${Client.bucketTable}">
                                <tr>
                                    <td>"${map.key}"</td>
                                    <td>"${map.value.ip}"</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-6">
                    <div class="row">
                        <form:form method="post" action="/search" commandName="SearchResult">

                            <div class="form-group">
                                <label for="search" class="cols-sm-2 control-label">Search File </label>
                                <div class="cols-sm-10">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                        <form:input path="searchFile" type="text" class="form-control" name="search" id="search"  placeholder="${Client.currentSearch}" />
                                    </div>
                                </div>
                            </div>

                            <div class="form-group ">
                                <form:button class="btn btn-success" type="submit">Search</form:button>
                            </div>

                        </form:form>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="result">Result Of File Search</label>
                            <textarea class="form-control" rows="5" id="result" >${Client.searchResult}"</textarea>
                        </div>
                    </div>
                </div>

                <div class="col-lg-6">
                    <form:form method="get" action="/leave">
                        <div class="form-group ">
                            <button class="btn btn-success" type="submit">Leave</button>
                        </div>
                    </form:form>
                </div>

            </div>
        </div>
</body>
</html>