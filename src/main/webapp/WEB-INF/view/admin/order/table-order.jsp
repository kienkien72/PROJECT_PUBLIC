<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

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
                                <h1 class="mt-4">Manage Order</h1>
                                <ol class="breadcrumb mb-4">
                                    <a href="/admin" class="breadcrumb-item active">Dashboard </a>
                                    <div>/ Order</div>
                                </ol>
                                <div>
                                    <div class="container mt-5">
                                        <div class="col-12 mx-auto">

                                            <hr>
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">ID</th>
                                                        <th scope="col">Total price</th>
                                                        <th scope="col">User</th>
                                                        <th scope="col">Status</th>
                                                        <th scope="col">Action</th>
                                                    </tr>
                                                </thead>

                                                <tbody>

                                                    <c:forEach var="order" items="${order}">
                                                        <!-- // Var= name sẽ tương ứng với biến trong service -->
                                                        <tr>
                                                            <th>${order.id}</th>
                                                            <td>
                                                                <fmt:formatNumber type="number"
                                                                    value="${order.totalPrice}" /> đ
                                                            </td>

                                                            <td>${order.user.role.name} </td>
                                                            <td>${order.status}</td>


                                                            <td>
                                                                <a href="/admin/order/${order.id}"
                                                                    class="btn btn-success">View</a>
                                                                <a href="/admin/order/update/${order.id}"
                                                                    class="btn btn-warning mx-2">Update</a>
                                                                <a href="/admin/order/delete/${order.id}"
                                                                    class="btn btn-danger">Delete</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>

                                                </tbody>
                                            </table>
                                            <ul class="pagination justify-content-center">
                                                <li class="page-item">
                                                    <a class=" ${1 eq currentPage ? 'disabled page-link':'page-link'}"
                                                        href="/admin/order?page=${currentPage -1}"
                                                        aria-label="Previous">
                                                        <span aria-hidden="true">&laquo;</span>
                                                    </a>
                                                </li>
                                                <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                                                    <li class="page-item">
                                                        <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                                                            href="/admin/order?page=${loop.index + 1}">
                                                            ${loop.index + 1}
                                                        </a>
                                                    </li>
                                                </c:forEach>


                                                <li class="page-item">
                                                    <a class="${ totalPages eq currentPage ? 'disabled page-link':'page-link'}"
                                                        href="/admin/order?page=${currentPage +1}" aria-label="Next">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </li>

                                            </ul>

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