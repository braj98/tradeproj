<%--
  Created by IntelliJ IDEA.
  User: bkishore
  Date: 12/30/2017
  Time: 12:59 AM
--%>

%{-- File: grails-app/views/email/confirm.gsp --}%
<!doctype html>
<head>
    <title>Equivalent Trade </title>
</head>

<body>

<p>
    Thanks.
</p>

<g:each var="trade" in="${tradeList}">
    <p>${trade}</p>
</g:each>
</body>
</html>