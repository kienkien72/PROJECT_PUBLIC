<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Update Order</title>
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- <link rel="stylesheet" href="css/demo.css"> -->
            </head>

            <body>
                <div class="container mt-3">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h2 class=" ms-3">Update Order
                            </h2>
                            <hr>
                            <form:form class=" ms-3" method="post" action="/admin/order/update" modelAttribute="order">
                                <!-- <input type="hidden" name="id" value="${order.id}" /> -->
                                <div class="mb-3" style="display: none;">
                                    <label class="form-label">Id:</label>
                                    <form:input type="text" class="form-control" path="id" />
                                </div>
                                <div class="mb-3 col-12 col-md-6">
                                    <label class="form-label">Status:</label>
                                    <form:select class="form-select" path="status">
                                        <form:option value="PENDING">PENDING</form:option>
                                        <form:option value="SHIPPING">SHIPPING</form:option>
                                        <form:option value="COMPLETE">COMPLETE</form:option>
                                        <form:option value="CANCEL">CANCEL</form:option>
                                    </form:select>
                                </div>


                                <button type="submit" class="btn btn-warning">Save</button>
                            </form:form>
                        </div>
                    </div>
                </div>

            </body>

            </html>