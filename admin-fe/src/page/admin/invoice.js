import React, { forwardRef } from "react";
import { formatMoney } from "../../services/money";

// Sử dụng forwardRef để truyền ref từ component cha
const Invoice = forwardRef(({ invoiceData, giamgia }, ref) => {
  return (
    <div
      ref={ref}
      className="invoice-container"
      style={{
        fontFamily: "Arial, sans-serif",
        width: "210mm",
        margin: "0 auto",
        padding: "20px",
        border: "1px solid #ddd",
      }}
    >
      {/* Header */}
      <div style={{ textAlign: "center", marginBottom: "20px" }}>
        <h2 style={{ margin: "0" }}>CỬA HÀNG BERRY SHOES</h2>
        {/* <p style={{ margin: '5px 0' }}>MST: 0315728889</p>
        <p style={{ margin: '5px 0' }}>Điện thoại: 0912.491.214 - 0975.937.979</p>
        <p style={{ margin: '5px 0' }}>ĐC: Số 8, Đường số 1, Khu Cityland Center Hill, P.7, Q.Gò Vấp</p> */}
        <h3 style={{ marginTop: "20px" }}>HÓA ĐƠN BÁN LẺ</h3>
      </div>

      {/* Thông tin khách hàng */}
      <div style={{ marginBottom: "20px" }}>
        <p>
          <b>MÃ HÓA ĐƠN:</b>{" "}
          {invoiceData?.maHoaDon || "_______________________"}
        </p>
        <p>
          <b>Người tạo hóa đơn:</b> {invoiceData?.nguoitao || "Nhân Viên"}
        </p>
        <p>
          <b>Khách hàng:</b>{" "}
          {invoiceData?.customerName || "_______________________"}
        </p>
        {/* <p>
          <b>Địa chỉ:</b> {invoiceData.customerAddress || '_______________________'}
        </p> */}
      </div>

      {/* Bảng sản phẩm */}
      <table
        style={{
          width: "100%",
          borderCollapse: "collapse",
          marginBottom: "20px",
        }}
      >
        <thead>
          <tr>
            <th
              style={{
                border: "1px solid black",
                padding: "5px",
                textAlign: "center",
              }}
            >
              STT
            </th>
            <th
              style={{
                border: "1px solid black",
                padding: "5px",
                textAlign: "center",
              }}
            >
              TÊN HÀNG
            </th>
            <th
              style={{
                border: "1px solid black",
                padding: "5px",
                textAlign: "center",
              }}
            >
              Số Lượng
            </th>
            <th
              style={{
                border: "1px solid black",
                padding: "5px",
                textAlign: "center",
              }}
            >
              Đơn Giá
            </th>
            <th
              style={{
                border: "1px solid black",
                padding: "5px",
                textAlign: "center",
              }}
            >
              Thành Tiền
            </th>
          </tr>
        </thead>
        <tbody>
          {invoiceData?.items.map((item, index) => (
            <tr key={index}>
              <td
                style={{
                  border: "1px solid black",
                  padding: "5px",
                  textAlign: "center",
                }}
              >
                {index + 1}
              </td>
              <td style={{ border: "1px solid black", padding: "5px" }}>
                {item.name}
              </td>
              <td
                style={{
                  border: "1px solid black",
                  padding: "5px",
                  textAlign: "center",
                }}
              >
                {item.quantity}
              </td>
              <td
                style={{
                  border: "1px solid black",
                  padding: "5px",
                  textAlign: "center",
                }}
              >
                {item.price}
              </td>
              <td
                style={{
                  border: "1px solid black",
                  padding: "5px",
                  textAlign: "center",
                }}
              >
                {item.quantity * item.price}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Tổng cộng */}
      <div style={{ textAlign: "right", marginTop: "20px" }}>
        <p>
          <b>Tổng cộng:</b>{" "}
          {formatMoney(Number(invoiceData?.total)) || "________________"}
        </p>
        <p>
          <b>Giảm giá:</b>{" "}
          {giamgia.loaiPhieu
            ? formatMoney(giamgia.giaTriGiam)
            : `${giamgia.giaTriGiam} %` || "________________"}
        </p>
        <p>
          <b>Thành tiền:</b>{" "}
          {formatMoney(
            Number(invoiceData?.total) -
              Number(
                giamgia.loaiPhieu
                  ? giamgia.giaTriGiam
                  : (invoiceData?.total * giamgia.giaTriGiam) / 100
              )
          ) || "________________"}
        </p>
      </div>

      {/* Footer */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          marginTop: "40px",
        }}
      >
        <div style={{ textAlign: "center" }}>
          {/* <p>
            <b>Khách hàng</b>
          </p>
          <p>(Ký và ghi rõ họ tên)</p> */}
        </div>
        <div style={{ textAlign: "center" }}>
          <p>{/* <b>Người viết hóa đơn</b> */}</p>
          {/* <p>(Ký và ghi rõ họ tên)</p> */}
        </div>
      </div>
    </div>
  );
});

export default Invoice;
