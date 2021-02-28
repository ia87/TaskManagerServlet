<html>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang.res"/>
<fmt:message key="title.signUp" var="title"/>
<fmt:message key="label.user.firstName" var="firstName"/>
<fmt:message key="label.user.lastName" var="lastName"/>
<fmt:message key="label.user.email" var="email"/>
<fmt:message key="label.user.password" var="password"/>
<fmt:message key="label.user.confirmPass" var="confirmPass"/>
<fmt:message key="buttons.signUp" var="signUp"/>

<c:set var="title" value="${title}" />

<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div class="container">
    <div class="row" style="margin-top:20px">
        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
            <form action="app" method="post">
                <input type="hidden" name="command" value="registration"/>
                <fieldset>
                    <h1 style="text-align: center">
                        <fmt:message key="title.login"/>
                    </h1>

                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">
                            Invalid username or password.
                        </div></c:if>
                    <c:if test="${not empty param.logout}">
                        <div class="alert alert-info">
                            You have been logged out.
                        </div></c:if>

                    <div class="form-group">
                        <input type="text" name="firstName" id="firstName" class="form-control input-lg"
                               placeholder="${firstName}" required="true" autofocus="true"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="lastName" id="lastName" class="form-control input-lg"
                               placeholder="${lastName}" required="true"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="email" id="email" class="form-control input-lg"
                               placeholder="${email}" required="true"/>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" id="password" class="form-control input-lg"
                               placeholder="${password}" required="true"/>
                    </div>
                    <div class="form-group">
                        <input type="password" name="confirmPass" id="confirmPass" class="form-control input-lg"
                               placeholder="${confirmPass}" required="true"/>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 col-sm-6 col-md-6">
                            <input type="submit" class="btn btn-lg btn-primary btn-block" value="${signUp}"/>
                        </div>
                        <div class="col-xs-6 col-sm-6 col-md-6">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>

</div>
</body>
</html>