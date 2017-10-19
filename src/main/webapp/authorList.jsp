<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <h1>Author List</h1>
    <form id="authorAddEditForm" name="authorAddEditForm" method="POST" action="authorController?action=addEditAuthor">
        <input type="submit" value="Add/Edit">
    </form>
    <form id="authorDeleteForm" name="authorDeleteForm" method="POST" action="authorController?action=deleteAuthor">
        <input type="submit" value="Delete">        
        <table>
            
            
                <th></th>
                <th>ID</th>
                <th>Name</th>
                <th>Date Added</th>
               
            <c:forEach var="a" items="${authorList}" varStatus="varStatus">                
               
                <tr>        
                    <td>
                        <input type="checkbox" name="authorId" value="${a.authorId}">
                    </td>
                    <td>${a.authorId}</td>
                    <td>${a.authorName}</td>
                    <td>
                        <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"/>                                 
                    </td>
                </tr>  
            </c:forEach>  
           </table>                
    </form>     
    <br>
    <br>
    <a href="AuthorController?action=home">Go to Home Page</a>
</body>
</html>
