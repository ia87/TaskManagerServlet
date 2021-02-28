<html>
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang.res"/>
<fmt:message key="title.login" var="title"/>
<fmt:message key="label.user.email" var="email"/>
<fmt:message key="label.user.password" var="password"/>
<fmt:message key="buttons.login" var="loginButton"/>

<c:set var="title" value="${title}" />

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container-fluid">
    <br>
    <div class="panel panel-primary panel-wizard">
        <div class="panel-heading">
            <h3 style="text-align: center"><fmt:message key="title.users"> List of Users</fmt:message></h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th style="width:5%"><fmt:message key="label.user.id">ID</fmt:message></th>
                    <th style="width:20%"><fmt:message key="label.user.firstName">FIRST NAME</fmt:message></th>
                    <th style="width:20%"><fmt:message key="label.user.lastName">LAST NAME</fmt:message></th>
                    <th style="width:20%"><fmt:message key="label.user.email">EMAIL</fmt:message></th>
                    <th style="width:35%"></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>
                        <a type="button" class="btn btn-primary"
                           href="app?command=tasks&userId=${user.id}">Tasks</a>
                        <a type="button" class="btn btn-success"
                           href="#">Edit</a>
                        <a type="button" class="btn btn-warning"
                           href="#">Delete</a>
                         </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>