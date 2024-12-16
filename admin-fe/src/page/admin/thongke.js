import { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getMethod,
  postMethodPayload,
  deleteMethod,
} from "../../services/request";
import Swal from "sweetalert2";
import Chart from "chart.js/auto";
import * as XLSX from "xlsx";

const AdminThongKe = () => {
  const [thongKe, setThongKe] = useState(null);
  useEffect(() => {
    revenueYear(new Date().getFullYear());
    getMauSac();
    getThongKe();
    veBieuDoTron();
  }, []);
  async function revenueYear(nam) {
    if (nam < 2000) {
      nam = new Date().getFullYear();
    }
    const response = await getMethod(
      "/api/thong-ke/admin/doanhthuall?nam=" + nam
    );
    var list = await response.json();
    console.log(list);
    var main = "";
    for (var i = 0; i < list.length; i++) {
      if (list[i] == null) {
        list[i] = 0;
      }
    }

    var lb = "doanh thu năm " + nam;
    const ctx = document.getElementById("chart").getContext("2d");
    let chartStatus = Chart.getChart("chart"); // <canvas> id
    if (chartStatus != undefined) {
      chartStatus.destroy(); // Hủy biểu đồ cũ nếu có
    }
    var myChart = new Chart(ctx, {
      type: "bar",
      data: {
        labels: [
          "tháng 1",
          "tháng 2",
          "tháng 3",
          "tháng 4",
          "tháng 5",
          "tháng 6",
          "tháng 7",
          "tháng 8",
          "tháng 9",
          "tháng 10",
          "tháng 11",
          "tháng 12",
        ],
        datasets: [
          {
            label: lb,
            backgroundColor: "rgba(161, 198, 247, 1)",
            borderColor: "rgb(47, 128, 237)",
            data: list,
          },
        ],
      },
      options: {
        scales: {
          yAxes: [
            {
              ticks: {
                callback: function (value) {
                  return formatmoney(value);
                },
              },
            },
          ],
        },
      },
    });
  }

  async function revenueTheoThuTrongTuan() {
    const response = await getMethod(
      "/api/thong-ke/admin/doanhthuthutrongtuan"
    );
    const data = await response.json();
    console.log("thutrongtuan", data);

    if (Array.isArray(data)) {
      var doanhThuTheoThu = Array(7).fill(0);

      data.forEach((item) => {
        var thu = item[0];
        var doanhThu = item[1];

        doanhThuTheoThu[thu % 7] = doanhThu;
      });

      const labels = [
        "Thứ Hai",
        "Thứ Ba",
        "Thứ Tư",
        "Thứ Năm",
        "Thứ Sáu",
        "Thứ Bảy",
        "Chủ Nhật",
      ];

      // Vẽ biểu đồ
      const ctx = document.getElementById("chartTuan").getContext("2d");
      let chartStatus = Chart.getChart("chartTuan");
      if (chartStatus != undefined) {
        chartStatus.destroy();
      }

      var myChart = new Chart(ctx, {
        type: "bar",
        data: {
          labels: labels,
          datasets: [
            {
              label: "Doanh thu theo thứ trong tuần",
              backgroundColor: "rgba(161, 198, 247, 1)",
              borderColor: "rgb(47, 128, 237)",
              data: doanhThuTheoThu,
            },
          ],
        },
        options: {
          scales: {
            yAxes: [
              {
                ticks: {
                  callback: function (value) {
                    return formatmoney(value);
                  },
                },
              },
            ],
          },
        },
      });
    } else {
      console.error("Dữ liệu trả về không phải là mảng:", data);
    }
  }

  async function revenueTheoGioTrongNgay() {
    const response = await getMethod("/api/thong-ke/admin/doanhthungay");
    const data = await response.json();
    console.log("gingay", data);

    if (!Array.isArray(data)) {
      console.error("Dữ liệu không phải là mảng:", data);
      return;
    }

    var doanhThuTheoGio = Array(24).fill(0); // Khởi tạo mảng với 24 phần tử (1 cho mỗi giờ trong ngày)

    data.forEach((item) => {
      var gio = item[0];
      var doanhThu = item[1];
      doanhThuTheoGio[gio] = doanhThu;
    });

    // Nhãn cho các giờ trong ngày (0h, 1h, ..., 23h)
    const labels = Array.from({ length: 24 }, (_, i) => `${i}h`);

    // Vẽ biểu đồ Line
    const ctx = document.getElementById("chartGio").getContext("2d");
    let chartStatus = Chart.getChart("chartGio");
    if (chartStatus != undefined) {
      chartStatus.destroy();
    }

    var myChart = new Chart(ctx, {
      type: "line",
      data: {
        labels: labels,
        datasets: [
          {
            label: "Doanh thu theo giờ trong ngày",
            fill: false,
            borderColor: "rgb(47, 128, 237)",
            backgroundColor: "rgba(161, 198, 247, 1)",
            data: doanhThuTheoGio,
          },
        ],
      },
      options: {
        scales: {
          yAxes: [
            {
              ticks: {
                callback: function (value) {
                  return formatmoney(value);
                },
              },
            },
          ],
        },
      },
    });
  }

  async function getThongKe() {
    const response = await getMethod("/api/thong-ke/admin/thong-ke-tong-quat");
    var result = await response.json();
    setThongKe(result);
  }

  function loadByNam() {
    var nam = document.getElementById("nams").value;
    revenueYear(nam);
  }

  const VND = new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  });

  function formatmoney(money) {
    return VND.format(money);
  }

  function getMauSac() {
    var arr = [
      "#4e73df",
      "#1cc88a",
      "#36b9cc",
      "#eb9534",
      "#ed00c6",
      "#edd500",
    ];
    var act = document.getElementsByClassName("border-left");
    var lbcard = document.getElementsByClassName("lbcard");
    for (var i = 0; i < act.length; i++) {
      act[i].style.borderLeft = ".25rem solid " + arr[i];
    }
    for (var i = 0; i < lbcard.length; i++) {
      lbcard[i].style.color = arr[i];
    }
  }

  async function veBieuDoTron() {
    const response = await getMethod("/api/thong-ke/admin/thong-ke-trang-thai");
    var result = await response.json();
    console.log(result);

    var arrTen = Object.keys(result);
    var arrvalue = Object.values(result);
    const ctx = document.getElementById("myPieChart").getContext("2d");

    // Kiểm tra và hủy biểu đồ nếu có
    let chartStatus = Chart.getChart("myPieChart"); // <canvas> id
    if (chartStatus != undefined) {
      chartStatus.destroy(); // Hủy biểu đồ cũ nếu có
    }

    // Dữ liệu biểu đồ
    const data = {
      labels: arrTen,
      datasets: [
        {
          label: "Số lượng đơn hàng",
          data: arrvalue, // Giá trị
          backgroundColor: [
            "rgba(255, 99, 132, 0.6)", // Màu sắc từng phần
            "rgba(54, 162, 235, 0.6)",
            "rgba(255, 206, 86, 0.6)",
            "rgba(75, 192, 192, 0.6)",
            "rgba(168, 142, 69, 0.6)",
            "rgba(86, 55, 196, 0.6)",
            "rgba(58, 152, 161, 0.6)",
            "rgba(171, 91, 55, 0.6)",
          ],
          borderWidth: 1,
        },
      ],
    };

    const config = {
      type: "pie",
      data: data,
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: "right",
          },
          tooltip: {
            callbacks: {
              label: function (context) {
                const label = context.label || "";
                const value = context.raw || 0;
                return `${label}: ${value}`;
              },
            },
          },
        },
      },
    };

    new Chart(ctx, config);
  }

  useEffect(() => {
    revenueTheoGioTrongNgay();
    revenueTheoThuTrongTuan();
  }, []);

  const exportToExcel = () => {
    if (!thongKe) {
      toast.error("Không có dữ liệu để xuất.");
      return;
    }


    const data = [
      { "Thống kê": "Số đơn hôm nay", "Giá trị": thongKe.soDonTrongNgay },
      { "Thống kê": "Số đơn tuần này", "Giá trị": thongKe.soDonTuanNay },
      { "Thống kê": "Số đơn tháng này", "Giá trị": thongKe.soDonThangNay },

    ];


    const ws = XLSX.utils.json_to_sheet(data);

    // Tạo workbook từ sheet
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Thống Kê");

    // Xuất file Excel
    XLSX.writeFile(wb, "ThongKe.xlsx");
  };
  return (
    <div>
      <div className="card-container">
        <div class="card-day">
          <div class="col-xl-3 col-md-6 mb-4 card-view">
            <div class="card border-left shadow h-100 py-2">
              <span class="lbcard">Số đơn hôm nay</span>
              <span class="solieudoanhthu" id="soDonTrongNgay">
                {thongKe?.soDonTrongNgay}
              </span>
            </div>
          </div>
          <div class="col-xl-3 col-md-6 mb-4 card-view">
            <div class="card border-left shadow h-100 py-2">
              <span class="lbcard">Số đơn tuân này</span>
              <span class="solieudoanhthu" id="soDonTuanNay">
                {thongKe?.soDonTuanNay}
              </span>
            </div>
          </div>
          <div class="col-xl-3 col-md-6 mb-4 card-view">
            <div class="card border-left shadow h-100 py-2">
              <span class="lbcard">Số đơn tháng này</span>
              <span class="solieudoanhthu" id="soDonThangNay">
                {thongKe?.soDonThangNay}
              </span>
            </div>
          </div>
        </div>
      </div>

      <div className="export-excel-button">
        <button onClick={exportToExcel} className="btn btn-success">
          Xuất Excel
        </button>
      </div>
      <div className="tron-line-container">
        <div className="col-sm-4 tron-view">
          <canvas id="myPieChart"></canvas>
        </div>
        <div class="col-sm-12 line-view">
          <div class="card chart-container divtale">
            <canvas id="chartGio"></canvas>
          </div>
        </div>
      </div>

      <div class="col-sm-12 header-sp row ">
        <div class="col-md-3">
          <label class="lbbooking">Chọn năm cần xem</label>
          <select id="nams" class="form-control">
            <option id="2024">2024</option>
            <option id="2025">2025</option>
            <option id="2026">2026</option>
            <option id="2027">2027</option>
            <option id="2028">2028</option>
          </select>
        </div>
        <div class="col-md-2">
          <label
            class="lbbooking whitespace"
            dangerouslySetInnerHTML={{ __html: "<span>&ThinSpace;</span>" }}
          ></label>
          <button
            onClick={() => loadByNam()}
            class="btn btn-primary form-control"
          >
            <i class="fa fa-filter"></i> Lọc
          </button>
        </div>
      </div>
      <div className="cot-container">
        <div className="cot">
          <div class="col-sm-12 divtale">
            <div class="card chart-container divtale">
              <canvas id="chart"></canvas>
            </div>
          </div>
        </div>
        <div className="cot">
          <div class="col-sm-12 divtale">
            <div class="card chart-container divtale">
              <canvas id="chartTuan"></canvas>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminThongKe;
