import { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";
import {
  getMethod,
  deleteMethod,
  uploadMultipleFile,
  postMethodPayload,
  postMethod,
} from "../services/request";
import { formatMoney } from "../services/money";
import Select from "react-select";
import { toast } from "react-toastify";
// import rac from "../assest/images/"
function DonHang() {
  const [donhang, setDonHang] = useState([]);
  const [trangThai, setTrangThai] = useState([]);
  const [itemDetail, setItemDetail] = useState([]);
  const [item, setItem] = useState(null);

  useEffect(() => {
    getStatusInvoice();
    getDonHang();
  }, []);

  const getStatusInvoice = async () => {
    var response = await getMethod("/api/v1/hoa-don/danh-sach-trang-thai");
    var list = await response.json();
    setTrangThai(list);
  };
  const getDonHang = async () => {
    var response = await getMethod("/api/v1/hoa-don/hoa-don-cua-toi");
    var list = await response.json();

    console.log('sdfgsgegfa', list);
    setDonHang(list);
  };

  function getTrangThai(tt) {
    for (var i = 0; i < trangThai.length; i++) {
      if (trangThai[i].value == tt) {
        return trangThai[i].tenTrangThai;
      }
    }
    return "Không xác định";
  }

  const getInvoiceDetail = async (item) => {
    var response = await getMethod(
      "/api/hoa-don-chi-tiet/find-by-hoa-don?hoaDonId=" + item.id
    );
    var list = await response.json();
    console.log(list);

    setItemDetail(list);
    setItem(item);
  };


  const huydonhang  = async (id) => {
    var response = await postMethod(
      "/api/v1/hoa-don/huy-don-hang?hoaDonId=" + id
    );

    // var res = await response.json();
    // console.log("huy", res);
    if (response.status < 300) {
      toast.success("Thành công");
      // getDonHang();
    }
    if (response.status == 417) {
      var result = await response.json();
      toast.error(result.defaultMessage);
    }
  }
  return (
    <>
      <div class="headeraccount">
        <p class="fontyel">Đơn hàng của tôi</p>
      </div>
      <div class="contentacc" id="listaddacc">
        <table class="table table-bordered">
          <thead className="">
            <tr>
              <th>Mã hóa đơn</th>
              <th>Người nhận</th>
              <th>Địa chỉ</th>
              <th>Tổng tiền</th>
              <th>Phí vận chuyển</th>
              <th>Loại hóa đơn</th>
              <th>Ngày tạo</th>
              <th>Trạng thái</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            {donhang.map((item) => {
              return (
                <tr>
                  <td
                    className="pointer text-blue"
                    onClick={() => getInvoiceDetail(item)}
                    data-bs-toggle="modal"
                    data-bs-target="#modaldeail"
                  >
                    {item.maHoaDon}
                  </td>
                  <td>
                    Họ tên: {item.khachHang.hoVaTen}
                    <br />
                    Số điện thoại: {item.khachHang.soDienThoai}
                  </td>
                  <td>{item.diaChi}</td>
                  <td>{formatMoney(item.tongTien)}</td>
                  <td>{formatMoney(item.phiVanChuyen)}</td>
                  <td>
                    {item.loaiHoaDon == true
                      ? "Đặt hàng online"
                      : "Thanh toán tại quầy"}
                  </td>
                  <td>{item.ngayTao}</td>
                  <td>{getTrangThai(item.trangThai, trangThai)}</td>
                  <td>
                    <button
                      onClick={() => {
                        huydonhang(item.id);
                      }}
                      class="delete-btn"
                    >
                      <i className="fa fa-times-circle"></i>
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      <div
        class="modal fade"
        id="modaldeail"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Order Detail</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <div class="row shipinfor">
                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                  <span class="ttshipinfor">Địa chỉ nhận</span>
                  <div class="blockinfor">
                    <p class="reciverName">{item?.receiverName}</p>
                    <span>
                      Delivery address: <span>{item?.diaChi}</span>
                    </span>
                  </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                  <span class="ttshipinfor">Thanh toán</span>
                  <div class="blockinfor">
                    <span id="loaithanhtoan">
                      {item?.phuongThucThanhToans.length > 0 == true
                        ? "Đã thanh toán"
                        : "Chưa thanh toán"}
                    </span>
                  </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                  <span class="ttshipinfor">Note</span>
                  <div class="blockinfor">
                    <span id="ghichunh">{item?.ghiChu}</span>
                  </div>
                </div>
              </div>
              <br />
              <h5>Trạng thái đơn hàng</h5>
              <table className="table table-bordered">
                <thead>
                  <tr>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Ngày đặt</td>
                    <td>{item?.ngayTao}</td>
                  </tr>
                  {item?.ngayXacNhan && (
                    <tr>
                      <td>Ngày xác nhận</td>
                      <td>{item.ngayXacNhan}</td>
                    </tr>
                  )}
                  {item?.ngayVanChuyen && (
                    <tr>
                      <td>Ngày vận chuyển</td>
                      <td>{item.ngayVanChuyen}</td>
                    </tr>
                  )}
                  {item?.ngayNhanHang && (
                    <tr>
                      <td>Ngày nhận hàng</td>
                      <td>{item.ngayNhanHang}</td>
                    </tr>
                  )}
                  {item?.ngayNhanHang && (
                    <tr>
                      <td>Ngày hoàn thành</td>
                      <td>{item.ngayHoanThanh}</td>
                    </tr>
                  )}
                </tbody>
              </table>
              <table className="table table-bordered">
                <thead>
                  <tr>
                    <th>Created date</th>
                    <th>Status</th>
                    <th>Created by</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
              <table class="table table-cart table-order" id="detailInvoice">
                <thead class="thead-default theaddetail">
                  <tr>
                    <th>Ảnh</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá tiền</th>
                    <th>Số lượng</th>
                  </tr>
                </thead>
                <tbody>
                  {itemDetail.map((item, index) => {
                    return (
                      <tr>
                        <td>
                          <img
                            src={item.sanPhamChiTiet.sanPham.anh}
                            className="imgtable"
                          />
                        </td>
                        <td>{item.sanPhamChiTiet.sanPham.tenSanPham}</td>
                        <td>{formatMoney(item.sanPhamChiTiet.giaTien)}</td>
                        <td>{item.soLuong}</td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
              <br />
              <br />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default DonHang;
