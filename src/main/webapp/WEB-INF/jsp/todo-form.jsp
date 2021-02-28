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

<div class="container">
    <div class="row" style="margin-top:20px">
        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
            <form action="app" method="post">
                <c:if test="${todo != null}">
                    <input type="hidden" name="command" value="editTask"/>
                </c:if>
                <c:if test="${todo == null}">
                    <input type="hidden" name="command" value="addTask"/>
                </c:if>
                <caption>
                    <h2 style="text-align: center">
                        <c:if test="${todo != null}">Edit Todo</c:if>
                        <c:if test="${todo == null}">Add New Todo</c:if>
                    </h2>
                </caption>

                <c:if test="${todo != null}">
                    <input type="hidden" name="taskId" value="<c:out value='${todo.id}' />" />
                    <input type="hidden" name="userId" value="<c:out value='${todo.user.id}' />" />
                    <input type="hidden" name="createdAt" value="<c:out value='${todo.createdAt}' />" />
                    <input type="hidden" name="started" value="<c:out value='${todo.started}' />" />
                    <input type="hidden" name="finished" value="<c:out value='${todo.finished}' />" />
                </c:if>
                <c:if test="${todo == null}">
                    <input type="hidden" name="userId" value="<c:out value='${userTaskId}' />" />
                </c:if>

                <fieldset class="form-group">
                    <label>Todo Decription</label>
                    <input type="text" value="<c:out value='${todo.description}' />"
                           class="form-control" name="description" minlength="5">
                </fieldset>

                <fieldset class="form-group">
                    <label>Todo Target Date</label>
                    <input type="date" value="<c:out value='${todo.targetDate}' />"
                           class="form-control" name="targetDate" required="required">
                </fieldset>

                <button type="submit" class="btn btn-success btn-lg btn-block">Save</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>