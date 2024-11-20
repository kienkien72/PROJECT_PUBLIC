<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Đăng ký - Laptopshop</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

                <style>
                    .custom-background {
                        background-image: url('/client/img/modern.jpg');
                        background-size: cover;
                        background-position: center;
                        position: relative;
                        padding: 50px;
                    }

                    /* Lớp phủ đen mờ */
                    .custom-background::before {
                        content: '';
                        position: absolute;
                        top: 0;
                        left: 0;
                        right: 0;
                        bottom: 0;
                        background-color: rgba(0, 0, 0, 0.5);
                        /* Độ mờ của lớp phủ đen */
                        z-index: 1;
                    }

                    .custom-background-content {
                        position: relative;
                        z-index: 2;
                        /* Để nội dung nằm trên lớp phủ đen */
                        color: black;
                        /* Màu chữ nếu cần */
                    }

                    /* Card chính của form */
                    .card {
                        background-color: rgba(255, 255, 255, 0.2);
                        /* Nền trong suốt 20% */
                        backdrop-filter: blur(10px);
                        /* Làm mờ nền phía sau */
                        border-radius: 15px;
                        /* Bo góc mềm mại */
                        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
                        /* Đổ bóng cho card */
                        border: none;
                        /* Loại bỏ viền */
                    }

                    /* Header của form */
                    .card-header {
                        background-color: rgba(255, 255, 255, 0.1);
                        /* Header trong suốt nhẹ */
                        border-bottom: none;
                        /* Loại bỏ viền dưới của header */
                    }

                    .card-header h3 {
                        color: #fff;
                        /* Màu chữ trắng */
                        font-weight: bold;
                        /* Chữ đậm */
                    }

                    /* Body của form */
                    .card-body {
                        padding: 20px;
                    }

                    /* Input fields */
                    .form-control {
                        background-color: rgba(255, 255, 255, 0.3);
                        /* Nền input trong suốt */
                        border: none;
                        /* Loại bỏ viền */
                        border-radius: 10px;
                        /* Bo tròn các góc */
                        color: #fff;
                        /* Màu chữ trắng */
                    }

                    .form-control:focus {
                        box-shadow: 0 0 5px rgba(255, 255, 255, 0.5);
                        /* Đổ bóng khi input được chọn */
                        outline: none;
                        /* Loại bỏ khung outline khi chọn */
                    }

                    .form-floating label {
                        color: rgba(255, 255, 255, 0.7);
                        /* Màu chữ của label */
                    }

                    /* Nút Đăng nhập */
                    .btn-primary {
                        background-color: #ff6347;
                        /* Màu cam đỏ nổi bật */
                        border: none;
                        color: #fff;
                        /* Màu chữ trắng */
                        font-weight: bold;
                        padding: 10px 20px;
                        border-radius: 8px;
                        transition: background-color 0.3s ease;
                    }

                    .btn-primary:hover {
                        background-color: #ff4500;
                        /* Màu cam đậm hơn khi hover */
                    }

                    /* Link Quên mật khẩu */
                    a.small {
                        color: #1e90ff;
                        /* Màu xanh dương nổi bật */
                        text-decoration: none;
                        /* Loại bỏ gạch chân */
                        font-weight: bold;
                        /* Chữ đậm */
                        transition: color 0.3s ease;
                    }

                    a.small:hover {
                        color: #104e8b;
                        /* Màu xanh dương đậm hơn khi hover */
                        text-decoration: underline;
                        /* Thêm gạch chân khi hover */
                    }

                    /* Link Chưa có tài khoản */
                    .card-footer a {
                        color: #32cd32;
                        /* Màu xanh lá cây nổi bật */
                        font-weight: bold;
                        /* Chữ đậm */
                        text-decoration: none;
                        transition: color 0.3s ease;
                    }

                    .card-footer a:hover {
                        color: #228b22;
                        /* Màu xanh lá đậm hơn khi hover */
                        text-decoration: underline;
                    }
                </style>
            </head>

            <body class="">
                <div class="custom-background" id="layoutAuthentication">
                    <div class="custom-background-content" id="layoutAuthentication_content">
                        <main>
                            <div class="container">
                                <div class="row justify-content-center">
                                    <div class="col-lg-7">
                                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                                            <div class="card-header">
                                                <h3 class="text-center font-weight-light my-4">Tạo tài khoản</h3>
                                            </div>
                                            <div class="card-body">
                                                <form:form method="post" action="/register"
                                                    modelAttribute="registerUser">
                                                    <c:set var="errorPassword">
                                                        <form:errors path="confirmPassword"
                                                            cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <c:set var="errorEmail">
                                                        <form:errors path="email" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <c:set var="errorFirstName">
                                                        <form:errors path="firstName" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <div class="row mb-3">
                                                        <div class="col-md-6">
                                                            <div class="form-floating mb-3 mb-md-0">
                                                                <form:input
                                                                    class="form-control ${not empty errorFirstName ? 'is-invalid':''}"
                                                                    type="text" placeholder="Enter your first name"
                                                                    path="firstName" />
                                                                <label for="inputFirstName">Tên</label>
                                                                ${errorFirstName}
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-floating">
                                                                <form:input class="form-control" type="text"
                                                                    placeholder="Enter your last name"
                                                                    path="lastName" />
                                                                <label for="inputLastName">Họ</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <form:input
                                                            class="form-control ${not empty errorEmail ? 'is-invalid':''}"
                                                            type="email" placeholder="name@example.com" path="email" />
                                                        <label>Địa chỉ email</label>
                                                        ${errorEmail}
                                                    </div>
                                                    <div class="row mb-3">
                                                        <div class="col-md-6">
                                                            <div class="form-floating mb-3 mb-md-0">
                                                                <form:input
                                                                    class="form-control ${not empty errorPassword ? 'is-invalid':''}"
                                                                    type="password" placeholder="Create a password"
                                                                    path="password" />
                                                                <label>Mật khẩu</label>
                                                                ${errorPassword}
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-floating mb-3 mb-md-0">
                                                                <form:input class="form-control " type="password"
                                                                    placeholder="Confirm password"
                                                                    path="confirmPassword" />
                                                                <label>Nhập lại mật khẩu</label>

                                                                <!-- <form:errors path="confirmPassword" /> -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="mt-4 mb-0">
                                                        <div class="d-grid">
                                                            <button class="btn btn-primary btn-block">
                                                                Tạo tài khoản
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form:form>
                                            </div>
                                            <div class="card-footer text-center py-3">
                                                <div class="small">
                                                    <a href="/login">Đã có tài khoản ! Đăng nhập</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                    </div>

                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>