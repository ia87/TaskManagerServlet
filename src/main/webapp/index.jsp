
<html>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<c:set var="title" value="Home" scope="page" />

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<c:if test="${isADMIN}">
    <h1>This is Admin</h1><br>
</c:if>
<c:if test="${isUSER}">
    <h1>This is User</h1><br>
</c:if>
<c:if test="${unfinishedTasks!=null}">
    <h1 style="text-align: center"><a href="app?command=tasks">You have ${unfinishedTasks} unfinished tasks !! </a></h1><br>
</c:if>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>