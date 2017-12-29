<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Simple Chat</title>
</head>
<body>
<g:link action="downLoadTradeExcel">Download Sample Excel</g:link>

<div id="upload-data" class="content scaffold-create" role="main">

        <h1>Upload Data</h1>
        <g:if test="${flash.message}"><div class="message" role="status">${flash.message}</div></g:if>
        <g:uploadForm action="uploadTradeExcel">
            <fieldset class="form">
                <input type="file" name="file" />
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="doUpload" value="Upload" />
            </fieldset>
        </g:uploadForm>

</div>

</body>
</html>