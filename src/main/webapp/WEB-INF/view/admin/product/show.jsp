<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Product Detail ${id} </title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <!-- Latest compiled and minified CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

            <!-- Latest compiled JavaScript -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            <!-- <link rel="stylesheet" href="css/demo.css"> -->
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">

                                <div>
                                    <div class="container mt-5">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h2>Table Detail Product </h2>
                                            </div>
                                            <hr>
                                            <div class="card" style="width: 60%">
                                                <div class="card-header">
                                                    <h3>Product detail ${id}</h3>
                                                </div>
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">Id: ${infoPr.id} </li>
                                                    <li class="list-group-item">Name: ${infoPr.name} </li>
                                                    <li class="list-group-item">Price: <td>
                                                            <fmt:formatNumber type="number" value="${infoPr.price}" />
                                                            đ
                                                        </td>
                                                    </li>
                                                    <li class="list-group-item">Detail description: ${infoPr.detailDesc}
                                                    </li>
                                                    <li class="list-group-item">Short description: ${infoPr.shortDesc}
                                                    </li>
                                                    <li class="list-group-item">Factory: ${infoPr.factory} </li>
                                                    <li class="list-group-item">Target: ${infoPr.target} </li>
                                                    <li class="list-group-item">Avatar: ${infoPr.avatar} </li>
                                                </ul>
                                            </div>
                                            <a href="/admin/product" class="btn btn-success mt-3">Back</a>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </main>

                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
            </body>


            </html>