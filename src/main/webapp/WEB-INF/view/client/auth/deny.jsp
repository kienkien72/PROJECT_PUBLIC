<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Access Denied</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <meta charset="UTF-8">
            <link rel="stylesheet" href="style.css">
            <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
            <link rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap&subset=vietnamese">

        </head>
        <style>
            *,
            *:before,
            *:after {
                box-sizing: border-box;
            }

            html {
                height: 100%;
            }

            body {
                font-family: 'Raleway', sans-serif;
                background-color: #342643;
                height: 100%;
                padding: 10px;
            }

            a {
                color: #EE4B5E !important;
                text-decoration: none;
            }

            a:hover {
                color: #FFFFFF !important;
                text-decoration: none;
            }

            .text-wrapper {
                height: 100%;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }

            .title {
                font-size: 5em;
                font-weight: 700;
                color: #EE4B5E;
            }

            .subtitle {
                font-size: 40px;
                font-weight: 700;
                color: #1FA9D6;
            }

            .isi {
                font-size: 18px;
                text-align: center;
                margin: 30px;
                padding: 20px;
                color: white;
            }

            .buttons {
                margin: 30px;
                font-weight: 700;
                border: 2px solid #EE4B5E;
                text-decoration: none;
                padding: 15px;
                text-transform: uppercase;
                color: #EE4B5E;
                border-radius: 26px;
                transition: all 0.2s ease-in-out;
                display: inline-block;

                .buttons:hover {
                    background-color: #EE4B5E;
                    color: white;
                    transition: all 0.2s ease-in-out;
                }
            }
        </style>

        <body>
            <div class="text-wrapper">
                <div class="title" data-content="404">
                    403 - TRUY CẬP TỪ CHỐI
                </div>

                <div class="subtitle">
                    Rất tiếc, Bạn không có quyền truy cập trang này.
                </div>


                <div class="buttons">
                    <a class="button" href="/">Trở về trang chủ</a>
                </div>
            </div>
        </body>

        </html>