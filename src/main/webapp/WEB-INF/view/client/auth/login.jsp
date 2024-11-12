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
                <title>Đăng nhập - KITSHOP</title>
                <link href="css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <style>
                .layoutAuthentication {
                    background-image: url('/client/img/modern.jpg');
                    background-size: cover;
                    background-position: center;
                    position: relative;
                    padding: 50px;
                }

                /* Lớp phủ đen mờ */
                .layoutAuthentication::before {
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

                .layoutAuthentication_content {
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


            <body class="layoutAuthentication">
                <div>
                    <div class="layoutAuthentication_content">
                        <main>
                            <div class="container">
                                <div class="row justify-content-center">
                                    <div class="col-lg-5">
                                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                                            <div class="card-header">
                                                <h3 class="text-center font-weight-light my-4">Đăng nhập</h3>
                                            </div>
                                            <div class="card-body">
                                                <form method="post" action="/login">
                                                    <c:if test="${param.error != null}">
                                                        <div class="my-2" style="color: red;">Sai email hoặc mật khẩu
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${param.logout != null}">
                                                        <div class="my-2" style="color: green;">Đăng xuất thành công
                                                        </div>
                                                    </c:if>
                                                    <div class="form-floating mb-3">
                                                        <input class="form-control" id="inputEmail" type="email"
                                                            placeholder="name@example.com" name="username" />
                                                        <label for="inputEmail">Địa chỉ email</label>
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <input class="form-control" id="inputPassword" type="password"
                                                            placeholder="Password" name="password" />
                                                        <label for="inputPassword">Mật khẩu</label>
                                                    </div>
                                                    <div>
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />

                                                    </div>
                                                    <div
                                                        class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                                        <a class="small" href="password.html">Quên mật khẩu</a>
                                                        <button type="submit" class="btn btn-primary">Đăng nhập</button>

                                                    </div>
                                                </form>
                                            </div>
                                            <div class="card-footer text-center py-3">
                                                <div class="small"><a href="/register">Chưa có tài khoản ! Đăng ký</a>
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
                <script src="js/scripts.js"></script>
            </body>

            </html>