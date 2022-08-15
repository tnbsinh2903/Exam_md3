<%--
  Created by IntelliJ IDEA.
  User: BAO SINH
  Date: 7/26/2022
  Time: 10:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div  >
    <ol  >
        <li  ><a href="products?action=create">ADD</a></li>
    </ol>
</div>
<div class="table-responsive">
    <input type="text" placeholder="search" id="myInput">
    <table style = "border: 1px solid ">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>PRICE</th>
            <th>QUANTITY</th>
            <th>COLOR</th>
            <th>DESCRIPTION</th>
            <th>CATEGORY</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody class="table-bordered" id="myTable">
        <c:forEach var="product" items="${requestScope.productList}">
            <tr>
                <td><c:out value="${product.getId()}"/></td>
                <td><c:out value="${product.getName()}"/></td>
                <td><c:out value="${product.getPrice()}"/></td>
                <td><c:out value="${product.getQuantity()}"/></td>
                <td><c:out value="${product.getColor()}"/></td>
                <td><c:out value="${product.getDescription()}"/></td>
                <td>
                    <c:forEach items="${applicationScope.categoryList}" var="category">
                        <c:if test="${category.getId() == product.getIdCate()}">
                            <c:out value="${category.getName()}"/>
                        </c:if>
                    </c:forEach>
                </td>
                <td colspan="2">
                    <a href="products?action=edit&id=${product.getId()}"
                       class="btn btn-outline-brown">Edit</a>
                    <a href="products?action=delete&id=${product.getId()}"
                       class="btn btn-outline-brown" onclick="return confirm('Are you sure you want to delete this User')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>


    </table>
    <script>
        $(document).ready(function(){
            $("#myInput").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $("#myTable tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
    </script>
</div>
</body>
</html>
