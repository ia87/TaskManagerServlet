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
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<div class="container-fluid">
    <div>
        <a type="button" class="btn btn-primary btn-lg btn-block" href="app?command=addTask&userId=${userTasks.getId()}">Add Todo</a>
    </div>
    <br>
    <div class="panel panel-primary" style="flex: 1">
        <div class="panel-heading">
            <h3 style="text-align: center"><fmt:message key="title.tasks">List of Tasks</fmt:message> ${userTasks.getEmail()}</h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th style="width:5%">
                        <a href="app?command=tasks&userId=${userTasks.id}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage}&sortingBy=id">
                            <fmt:message key="label.user.id">ID</fmt:message>
                        </a>
                    </th>
                    <th style="width:30%">
                        <a href="app?command=tasks&userId=${userTasks.id}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage}&sortingBy=description">
                             <fmt:message key="todo.description">Description</fmt:message>
                        </a>
                    </th>
                    <th style="width:10%">
                        <a href="app?command=tasks&userId=${userTasks.id}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage}&sortingBy=target_date">
                            <fmt:message key="todo.targetDate">Target Date</fmt:message>
                        </a>
                    </th>
                    <th style="width:15%">
                        <a href="app?command=tasks&userId=${userTasks.id}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage}&sortingBy=started">
                            <fmt:message key="todo.started">Started</fmt:message>
                        </a>
                    </th>
                    <th style="width:15%">
                        <a href="app?command=tasks&userId=${userTasks.id}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage}&sortingBy=finished">
                            <fmt:message key="todo.finished">Finished</fmt:message>
                        </a>
                    </th>
                    <th style="width:25%"></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="task" items="${tasks}">
                <tr>
                    <td>${task.id}</td>
                    <td style="text-align: left">${task.description}</td>
                    <td><javatime:format value="${task.targetDate}" pattern="dd/MMM/yyyy"/></td>
                    <td><javatime:format value="${task.started}" pattern="dd/MMM/yyyy HH:mm"/></td>
                    <td><javatime:format value="${task.finished}" pattern="dd/MMM/yyyy HH:mm"/></td>
                    <td>
                        <c:if test="${isADMIN}">
                            <a type="button" class="btn btn-primary"
                               href="app?command=editTask&taskId=${task.id}">Edit</a>
                            <a type="button" class="btn btn-danger"
                               href="app?command=deleteTask&taskId=${task.id}&userId=${userTasks.getId()}">Delete</a>
                        </c:if>
                        <c:choose>
                            <c:when test="${task.getStarted()!=null}">
                                <a type="button" class="btn btn-success"
                                   href="#"  disabled>Start</a>
                            </c:when>
                            <c:otherwise>
                                <a type="button" class="btn btn-success"
                                    href="app?command=startTask&taskId=${task.id}&userId=${userTasks.getId()}">Start</a>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${not empty task.getFinished()}">
                                <a type="button" class="btn btn-warning"
                                   href="#"  disabled>Finish</a>
                            </c:when>
                            <c:when test="${empty task.getStarted()}">
                                <a type="button" class="btn btn-warning"
                                   href="#"  disabled>Finish</a>
                            </c:when>
                            <c:otherwise>
                                <a type="button" class="btn btn-warning"
                                    href="app?command=finishTask&taskId=${task.id}&userId=${userTasks.getId()}">Finish</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <nav aria-label="Navigation" style="text-align: center">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="app?command=tasks&userId=${user.id}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                                                 href="app?command=tasks&userId=${user.id}&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="app?command=tasks&userId=${user.id}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>