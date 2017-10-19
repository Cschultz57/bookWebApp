<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            Author List
        </title>
    </head>
    <div class="container"> 
        <body>
            <h1>
                Author List
            </h1>
            <br>
            <form id="authorFormDelete" name="authorFormDelete" method="POST" action="authorController?action=delete">  
                <button type="submit" formaction="authorController?action=addEdit" name="add">Add</button>
                <input type="submit" name="delete" value="Delete">
            <br>            
            <br>
            <table class="table">
                <tr>
                    <th>
                       
                    </th>
                    <th>
                        ID
                    </th>
                    <th>
                        Name
                    </th>
                    <th>
                        Date Added
                    </th>
                    <th>
                       
                    </th>
                </tr>    
                <c:forEach var="a" items="${authorList}" varStatus="varStatus">
                    <c:choose>
                        <c:when test="${varStatus.count%2 == 0}">
                            <tr style="background-color: #99e699;">                                 
                        </c:when>
                        <c:otherwise>
                            <tr>    
                        </c:otherwise>     
                    </c:choose>
                                <td>
                                    <input type="checkbox" name="authorIdChk" value="${a.authorId}">
                                </td>
                                <td>
                                    ${a.authorId}
                                </td>
                                <td>
                                    ${a.authorName}
                                </td>
                                <td>
                                    <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"/>                                 
                                </td>
                                <td>
                                    <button type="submit" formaction="authorController?action=AddEdit&authorId=${a.authorId}" value="${a.authorId}" name="edit">Edit</button>
                                </td>
                            </tr>  
                </c:forEach>             
            </table>
            </form>  
            </form>       
            <br>
            <br>
            <a href="authorController?action=home">Go to Home Page</a>
</body>
</html>
