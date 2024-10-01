<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Document</title>

            <!-- Latest compiled and minified CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

            <!-- Latest compiled JavaScript -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            <!-- <link rel="stylesheet" href="css/demo.css"> -->
        </head>

        <body>
            <div class="m-5 ">
                <div class="d-flex justify-content-between align-items-center">
                    <h2>Table Users</h2>
                    <a class="btn btn-primary" href="/admin/user/create">Create user</a>
                </div>
                <hr>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Full Name</th>
                            <th scope="col">Email</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <th>1</th>
                            <td>Duy Kien</td>
                            <td>ndkien</td>
                            <td>
                                <button class="btn btn-success">View</button>
                                <button class="btn btn-warning">Update</button>
                                <button class="btn btn-danger">Delete</button>
                            </td>
                        </tr>
                    </tbody>

                    <tbody>
                        <tr>
                            <th>1</th>
                            <td>Duy Kien</td>
                            <td>ndkien</td>
                            <td>
                                <button class="btn btn-success">View</button>
                                <button class="btn btn-warning">Update</button>
                                <button class="btn btn-danger">Delete</button>
                            </td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </body>

        </html>