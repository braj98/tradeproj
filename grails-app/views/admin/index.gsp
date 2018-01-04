<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Simple Chat</title>
</head>
<body>

<div id="upload-data" class="content scaffold-create" role="main">

        <h1>Upload Data</h1>
        <g:if test="${flash.message}"><div class="message" role="status">${flash.message}</div></g:if>
        <g:uploadForm action="uploadTradeExcel">
            <tr>
                <td>
                    <fieldset class="form">
                        <input type="file" name="file" />
                    </fieldset>
                </td>
                <td>
                    <fieldset class="buttons">
                        <g:submitButton name="doUpload" width="30" value="Upload" />
                    </fieldset>
                </td>
            </tr>
        </g:uploadForm>

</div>
<div>
    <g:link action="create" >Show Report</g:link>
</div>

</body>
</html>