<!DOCTYPE  html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main"/>
    <title>Equivalent Trade</title>
</head>

<body>
<table class="stylized display">
    <tr>
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
    </tr>
    <g:each var="trade" status="i" in="${tradeList}">

        <tr  class="${(i % 2) == 0 ? 'odd' : 'even'}"><font size="1">

            <td>${trade.tradeDate} </td>
            <td>${trade.exchange}</td>
            <td>${trade.type}</td>
            <td>${trade.qty}</td>
            <td>${trade.rate}</td>
            <td>${trade.rate}</td>
            <td>${trade.orderNo}</td>
            <td>${trade.tradeNo}</td>
            <td>${trade.comment}</td>
            <td>${String.format("%.2f", trade.avgPrice)}</td>
        </font></tr>


    </g:each>
</table>
<g:link action="downLoadTradeExcel">Download as Excel</g:link>
</body>

</html>