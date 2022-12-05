<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath = request.getContextPath();
%>
<head>
    <meta charset="UTF-8">
    <title>Auto-showcase</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://bootstrap-4.ru/docs/5.2/assets/css/docs.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="icon" type="image/svg+xml" href="https://svgsilh.com/svg_v2/2386838.svg">
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
    <script src="https://kit.fontawesome.com/bbf64bb483.js" crossorigin="anonymous"></script>
    <style>
        /*.price:before{ content:'$'; }*/
        /*.price:after{ content: '0'; }*/
        .model:after{ content: ' '; }
        .hidden{
            visibility: hidden;
        }
        #overlay {
            height: 100%;
            width: 100%;
            position: absolute;
            top: 0;
            left: 0;
            background-color: #000000;
            opacity: .5;
        }
    </style>
</head>
