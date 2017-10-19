<%-- 
    Document   : addEditPage
    Created on : Oct 19, 2017, 5:54:29 PM
    Author     : Carson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            Add/Edit Author
        </title>
    </head>
    <div class="container"> 
        <body>
            <h1>
                Add/Edit Author
            </h1>
            <form id="addEditAuthorForm" name="addEditAuthorForm" method="POST" action="authorController?action=saveAuthor">
                <table class="table">  
                    <c:if test="${not empty authorId}">
                        <tr>
                            <td>
                                Author ID     
                            </td>
                            <td>
                                <input type="text" id="authorId" name="authorId" readonly="readonly" value="${authorId}">
                            </td>
                        </tr>
                    </c:if>                     
                    <tr>
                        <td>
                            Author Name   
                        </td>
                        <td>
                            <input type="text" id="authorName" name="authorName" value="${authorName}">
                        </td>
                    </tr>
                    <c:if test="${not empty dateAdded}">
                        <tr>
                            <td>
                                Date Added
                            </td>
                            <td>
                                <input type="text" id="dateAdded" name="dateAdded" readonly="readonly" value="${dateAdded}">
                            </td>
                        </tr>  
                    </c:if>
                </table>
                <br>
                <input type="submit" name="submit" formaction="authorController?action=add" value="Submit Changes">
                <button type="submit" formaction="authorController?action=authorList" name="cancel">Cancel</button>
            </form>
        </body>
    </div> 
    <link href="frankSinatra.css" rel="stylesheet" type="text/css"/>
</html>
