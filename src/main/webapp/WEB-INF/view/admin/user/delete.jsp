<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Delete User ${id} </title>

                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- <link rel="stylesheet" href="css/demo.css"> -->
            </head>

            <body>
                <div class="container mt-5">
                    <div class="col-12 mx-auto">
                        <div class="d-flex justify-content-between align-items-center">
                            <h2>Delete Data Users ${id} </h2>
                        </div>
                        <hr>
                        <div class="card">
                            User delete id: ${id}
                        </div>
                        <div class="alert alert-danger" role="alert">
                            A simple danger alert—check it out!
                        </div>
                        <form:form method="post" action="/admin/user/delete" modelAttribute="newUser">
                            <div class="mb-3" style="display: none;">
                                <label class="form-label">Id</label>
                                <form:input type="text" class="form-control" path="id" value="${id}" />
                            </div>
                            <button class="btn btn-danger mt-3">Confirm</button>
                        </form:form>

                    </div>

                </div>
            </body>

            </html>