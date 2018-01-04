<%--
  Created by IntelliJ IDEA.
  User: bkishore
  Date: 12/30/2017
  Time: 12:59 AM
--%>

%{-- File: grails-app/views/email/confirm.gsp --}%

<!DOCTYPE  html>
<html>
%{--<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main"/>
    <title>Equivalent Trade</title>
</head>--}%

<body>
<table>
%{--    <tr>
        <th>trade Date</th>
        <th>exchange</th>
        <th>type</th>
        <th>qty</th>
        <th>rate</th>
        <th>rate</th>
        <th>order</th>
        <th>trade</th>
        <th>comment</th>
        <th>avgPrice</th>
    </tr>--}%
    <g:each var="trade" in="${tradeList}">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
            <td>${trade.tradeDate}</td>
            <td>${trade.exchange}</td>
            <td>${trade.type}</td>
            <td>${trade.qty}</td>
            <td>${trade.rate}</td>
            <td>${trade.rate}</td>
            <td>${trade.orderNo}</td>
            <td>${trade.tradeNo}</td>
            <td>${trade.comment}</td>
            <td>${trade.avgPrice}</td>
        </tr>

    </g:each>
</table>
</body>

</html>