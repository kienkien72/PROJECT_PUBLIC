<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Order detail </title>
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
                                <h1 class="mt-4">Order Detail {id}</h1>
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
                                                    <c:forEach var="orderDetail" items="${orderDetail}">
                                                        <tr>
                                                            <th scope="col">Sản phẩm</th>
                                                            <th scope="col">Tên </th>
                                                            <th scope="col">Giá tiền</th>
                                                            <th scope="col">Số lượng</th>
                                                            <th scope="col">Thành tiền</th>
                                                            <th scope="col">Trạng thái</th>
                                                        </tr>
                                                </thead>

                                                <tbody>


                                                    <!-- // Var= name sẽ tương ứng với biến trong service -->
                                                    <tr>
                                                        <td><img src="/images/product/${orderDetail.product.image}"
                                                                class="img-fluid me-5 rounded-circle"
                                                                style="width: 80px; height: 80px;" alt=""></td>
                                                        <td>
                                                            <p class="mb-0 mt-4">
                                                                <a href="/product/${orderDetail.product.id}"
                                                                    target="_blank">
                                                                    ${orderDetail.product.name}
                                                                </a>
                                                            </p>
                                                        </td>
                                                        <td>
                                                            <p class="mb-0 mt-4">
                                                                <fmt:formatNumber type="number"
                                                                    value="${orderDetail.price}" /> đ
                                                            </p>
                                                        </td>
                                                        <td>
                                                            <div class="input-group quantity mt-4"
                                                                style="width: 100px;">
                                                                <input type="text"
                                                                    class="form-control form-control-sm text-center border-0"
                                                                    value="${orderDetail.quantity}">
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <p class="mb-0 mt-4"
                                                                data-cart-detail-id="${orderDetail.id}">
                                                                <fmt:formatNumber type="number"
                                                                    value="${orderDetail.price * orderDetail.quantity}" />
                                                                đ
                                                            </p>
                                                        </td>
                                                        <td>
                                                            <p class="mb-0 mt-4">

                                                                ${orderDetail.order.status}

                                                            </p>
                                                        </td>
                                                    </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <a href="/admin/order" class="btn btn-success mt-3">Back</a>
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