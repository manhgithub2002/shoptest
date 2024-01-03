<%-- Document : report Created on : Jan 2, 2024, 9:13:31 PM Author : admin --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
      integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
      integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link
      href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"
      rel="stylesheet"
    />

    <link rel="stylesheet" href="assets/css/adminDashboard.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <title>Admin</title>
  </head>

  <body>
    <!-- header -->
    <div class="header">
      <div class="header__logo">
        <img src="assets/image/logo.png" alt="" />
        <span>Furnish</span>
      </div>
      <div class="header__nav">
        <ul class="header__icon">
          <li>
            <i class="fa-solid fa-magnifying-glass"></i>
          </li>
          <li>
            <i class="fa-regular fa-bell"></i>
          </li>
          <li>
            <i class="fa-regular fa-envelope"></i>
          </li>
        </ul>
        <div class="header__manager">
          <div class="header__avatar">
            <img src="assets/image/ava1.jpg" alt="" />
          </div>
          <div class="header__info">
            <div class="header__name">Nguyen An Phat</div>
            <span>Manager</span>
          </div>
        </div>
      </div>
    </div>
    <!-- End header -->

    <!-- main -->
    <div class="main">
      <div class="main__sidebar">
        <ul>
          <li>
            <a href="admin.html">
              <span><i class="bx bx-home"></i></span>
              <h3>Dashboard</h3>
            </a>
          </li>
          <li>
            <a href="manageProduct.html">
              <span><i class="bx bx-shopping-bag"></i></span>
              <h3>Product</h3>
            </a>
          </li>
          <li>
            <a href="manageCategory.html">
              <span><i class="bx bx-category"></i></span>
              <h3>Category</h3>
            </a>
          </li>
          <li>
            <a href="manageCustomers.html">
              <span><i class="bx bx-user"></i></span>
              <h3>Customers</h3>
            </a>
          </li>
          <li>
            <a href="manageOrders.html">
              <span><i class="bx bx-cart"></i></span>
              <h3>Orders</h3>
            </a>
          </li>
        </ul>
        <div class="main__logout">
          <a href="#">
            <span>
              <i class="fa-solid fa-arrow-right-to-bracket"></i>
            </span>
            <h3>Logout</h3>
          </a>
        </div>
      </div>
      <div class="main__right">
        <div class="main__box">
          <div class="box__item">
            <h4>Total revenue</h4>
            <h3>40$</h3>
          </div>

          <div class="box__item">
            <h4>Month revenue</h4>
            <h3>40$</h3>
          </div>

          <div class="box__item">
            <h4>Total items sold</h4>
            <h3>40</h3>
          </div>
        </div>

        <div class="canvas">
          <div class="canvas_header">
            <h4>Revenue chart by day</h4>
            <h4>
              <a href="reportDetails">Details</a>
            </h4>
          </div>
          
          <canvas id="myChart" style="height: 400px; width: 100%"></canvas>
        </div>
      </div>
      </div>
    </div>
    <!-- main -->
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
  </body>
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      fetch("admin?month=-1")
        .then((response) => response.json())
        .then((data) => {
          const days = data.chartData.map((item) => item.day);
          const totals = data.chartData.map((item) => item.total);
          const revenue = [
            data.totalRevenue,
            data.monthTotal,
            data.totalItemSold,
          ];

          const h3Data = document.querySelectorAll(".main__box .box__item h3");
          h3Data.forEach((element, index) => {
            if (revenue[index] > 0) {
              element.innerHTML = revenue[index];
            } else {
              element.innerHTML = 0;
            }
          });

          var ctx = document.getElementById("myChart").getContext("2d");
          var myChart = new Chart(ctx, {
            type: "line",
            data: {
              labels: days,
              datasets: [
                {
                  label: "Daily Revenue",
                  data: totals,
                  fill: true,
                  borderColor: "#b88e2f",
                  borderWidth: 2,
                },
              ],
            },
            options: {
              scales: {
                y: {
                  beginAtZero: true,
                },
              },
            },
          });
        })
        .catch((error) => console.error("Error:", error));
    });
  </script>
</html>
