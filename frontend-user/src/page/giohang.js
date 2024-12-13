import { useState, useEffect } from "react";
import star from "../assest/images/icon_star.png";
import banner from "../assest/images/banner.jpg";
import banner1 from "../assest/images/banner1.png";
import banner2 from "../assest/images/banner2.jpg";
import newitem from "../assest/images/newitem.jpg";
import ReactPaginate from "react-paginate";
import {
  getMethod,
  uploadSingleFile,
  deleteMethod,
  postMethodPayload,
  postMethod,
} from "../services/request";
import { formatMoney } from "../services/money";
import Select from "react-select";
import { toast } from "react-toastify";
import iconvnpay from "../assest/images/Icon-VNPAY.png";
import deliver from "../assest/images/delivery.png";

var size = 15;
var url = "";
function GioHang() {
  const [giohang, setGioHang] = useState([]);
  const [sanPhamMoi, setSanPhamMoi] = useState([]);
  const [voucher, setVoucher] = useState([]);
  const [tongTien, setTongTien] = useState(0);
  const [diaChis, setDiaChis] = useState([]);
  const [selectDiaChi, setSelectDiaChi] = useState(null);
  const [selectVoucher, setselectVoucher] = useState(null);
  const [voucherSelect, setVoucherSelect] = useState(null);
  const [phuongthuctt, setphuongthuctt] = useState(null);
  const [tongTam, setTongTam] = useState(0);
  const [textGiamGia, settextGiamGia] = useState("");
  const [phiShip, setPhiShip] = useState(0);
  const [tienGiam, settienGiam] = useState(0);

  useEffect(() => {
    getGioHang();
    getDiaChi();
    getVoucher();
  }, []);

  const getGioHang = async () => {
    var response = await getMethod("/api/gio-hang/gio-hang-cua-toi");
    var list = await response.json();
    console.log(list);
    setGioHang(list);
    var tong = 0;
    for (var i = 0; i < list.length; i++) {
      tong =
        Number(tong) + Number(list[i].soLuong * list[i].sanPhamChiTiet.giaTien);
    }
    setTongTien(tong);
  };

  async function capNhatSl(id, soluong) {
    var res = await postMethod(
      "/api/gio-hang/cap-nhat-so-luong?idGioHang=" + id + "&soLuong=" + soluong
    );
    if (res.status < 300) {
      getGioHang();
    }
  }

  const getDiaChi = async () => {
    var response = await getMethod("/api/dia-chi");
    var list = await response.json();
    setDiaChis(list);
    console.log("diachi", list);
  };

  const getVoucher = async () => {
    var response = await getMethod("/api/phieu-giam-gia/kha-dung");
    var list = await response.json();
    setVoucher(list);
  };

  async function kiemTraVoucher() {
    var selectedCheckboxes = document.querySelectorAll(
      'input[name="sanphamchitiet"]:checked'
    );
    const selectedValues = Array.from(selectedCheckboxes).map(
      (checkbox) => checkbox.value
    );
    var res = await postMethodPayload(
      "/api/phieu-giam-gia/kiem-tra-phieu?id=" + selectVoucher.id,
      selectedValues
    );
    if (res.status < 300) {
      toast.success("Mã giảm giá đã được áp dụng");
      setVoucherSelect(selectVoucher);
      document.getElementById("blockmess").style.display = "block";
      document.getElementById("blockmessErr").style.display = "none";
    } else {
      var result = await res.json();
      toast.warning(result.defaultMessage);
      document.getElementById("messerr").innerHTML = result.defaultMessage;
      document.getElementById("blockmessErr").style.display = "block";
      document.getElementById("blockmess").style.display = "none";
      setVoucherSelect(null);
    }
  }

  async function kiemTraThanhToan() {
    if (selectDiaChi == null) {
      toast.error("Hãy chọn địa chỉ nhận hàng");
      return;
    }
    var selectedCheckboxes = document.querySelectorAll(
      'input[name="sanphamchitiet"]:checked'
    );
    const selectedValues = Array.from(selectedCheckboxes).map(
      (checkbox) => checkbox.value
    );
    if (selectedValues.length == 0) {
      toast.error("Hãy chọn ít nhất 1 sản phẩm");
      return;
    }
    var res = await postMethodPayload(
      "/api/gio-hang/tinh-tong",
      selectedValues
    );
    var tong = await res.text();
    setTongTam(tong);
    if (voucherSelect != null) {
      var strgiam = "";
      if (voucherSelect.loaiPhieu == true) {
        strgiam = "- " + formatMoney(voucherSelect.giaTriGiam);
        settienGiam(voucherSelect.giaTriGiam);
      } else {
        var giamtien =
          Number(tong) - Number((tong * voucherSelect.giaTriGiam) / 100);
        if (giamtien > voucherSelect.giaTriGiamToiDa) {
          giamtien = voucherSelect.giaTriGiamToiDa;
        }
        strgiam =
          "- " +
          voucherSelect.giaTriGiam +
          " % ( tối đa " +
          formatMoney(giamtien) +
          ")";
        settienGiam(giamtien);
      }
      settextGiamGia(strgiam);
    }
    var toDistrictId = selectDiaChi.quanHuyen.split("?")[0];
    var toWardCode = selectDiaChi.xaPhuong.split("?")[0];
    console.log(selectDiaChi);

    var res = await postMethodPayload(
      "/api/shipping/public/calculate-shipping-fee?toDistrictId=" +
        toDistrictId +
        "&toWardCode=" +
        toWardCode,
      selectedValues
    );
    var result = await res.json();
    console.log(result);
    setPhiShip(result.data.service_fee);
  }

  async function xacNhanDatHang() {
    var selectedCheckboxes = document.querySelectorAll(
      'input[name="sanphamchitiet"]:checked'
    );
    const selectedValues = Array.from(selectedCheckboxes).map(
      (checkbox) => checkbox.value
    );
    if (selectDiaChi == null) {
      toast.error("Hãy chọn địa chỉ nhận hàng");
      return;
    }
    if (selectedValues.length == 0) {
      toast.error("Hãy chọn ít nhất 1 sản phẩm");
      return;
    }
    var payload = {
      listIdGioHang: selectedValues,
      phieuGiamGia: voucherSelect,
      diaChi: selectDiaChi,
      ghiChu: document.getElementById("ghichudonhang").value,
    };
    if (phuongthuctt == "cod") {
      var res = await postMethodPayload(
        "/api/v1/hoa-don/dat-hang-cod",
        payload
      );
      if (res.status < 300) {
        toast.success("Success!");
        await new Promise((resolve) => setTimeout(resolve, 1000));
        window.location.href = "/taikhoan";
      } else {
        if (res.status == 417) {
          var result = await res.json();
          toast.error(result.defaultMessage);
        } else {
          toast.error("Đặt hàng thất bại");
        }
      }
    } else {
      var res = await postMethodPayload(
        "/api/v1/hoa-don/tao-link-thanh-toan",
        payload
      );
      var link = await res.text();
      window.localStorage.setItem("order", JSON.stringify(payload));
      window.open(link, "_blank");
    }
  }

  async function deleteCart(id) {
    var con = window.confirm("Xác nhận xóa sản phẩm khỏi giỏ hàng?");
    if (con == false) {
      return;
    }
    const response = await deleteMethod("/api/gio-hang/delete/" + id);
    if (response.status < 300) {
      toast.success("Xóa thành công!");
      getGioHang();
    }
    if (response.status == 417) {
      var result = await response.json();
      toast.warning(result.defaultMessage);
    }
  }

  return (
    <div class="maincontentweb">
      <div class="containercustom container">
        <div class="container-fluid">
          <div class="row contentgiohang">
            <div class="col-sm-8">
              <p class="tintuctieude">
                Giỏ hàng{" "}
                <span class="soluonggiohang">
                  (<span id="soluonggiohang">{giohang.length}</span>) sản phẩm
                </span>
              </p>
              <hr />
              <div class="divtableover">
                <table class="table tablecart">
                  <tr>
                    <th></th>
                    <th>Sản phẩm</th>
                    <th>Đơn giá</th>
                    <th>Số lượng</th>
                    <th>Tổng tiền</th>
                    <th>Xóa</th>
                  </tr>
                  <tbody id="listcartDes">
                    {giohang.map((item, index) => {
                      return (
                        <tr>
                          <td>
                            <label class="checkbox-custom">
                              <input
                                name="sanphamchitiet"
                                value={item.id}
                                type="checkbox"
                              />
                              <span class="checkmark-checkbox"></span>
                            </label>
                          </td>
                          <td>
                            <strong>
                              {item.sanPhamChiTiet.sanPham.tenSanPham}
                            </strong>
                            <br />
                            Kích thước: {item.sanPhamChiTiet.kichCo.tenKichCo}
                            <br />
                            Màu sắc: {item.sanPhamChiTiet.mauSac.tenMauSac}
                          </td>
                          <td>{formatMoney(item.sanPhamChiTiet.giaTien)}</td>
                          <td>
                            <div class="clusinp sl-container">
                              <button
                                onClick={() => capNhatSl(item.id, -1)}
                                class="cartbtn"
                              >
                                {" "}
                                -{" "}
                              </button>
                              <input value={item.soLuong} class="inputslcart" />
                              <button
                                onClick={() => capNhatSl(item.id, 1)}
                                class="cartbtn"
                              >
                                {" "}
                                +{" "}
                              </button>
                            </div>
                          </td>
                          <td>
                            {formatMoney(
                              item.soLuong * item.sanPhamChiTiet.giaTien
                            )}
                          </td>
                          <td>
                            <i
                              onClick={() => deleteCart(item.id)}
                              className="fa fa-remove pointer"
                            ></i>
                          </td>
                        </tr>
                      );
                    })}
                  </tbody>
                </table>
              </div>
              <hr />
              {/* <p class="tintuctieude">Gợi ý sản phẩm</p> */}
              <div id="goiysanpham" class="row"></div>
            </div>
            <div class="col-sm-4">
              <span class="tds">Tổng đơn: </span>
              <span class="tonggiatien" id="tonggiatien">
                {formatMoney(tongTien)}
              </span>
              <hr />
              <span class="lbthongtinnhanhang">Thông tin nhận hàng</span>
              <div>
                <Select
                  className="select-container"
                  options={diaChis}
                  onChange={setSelectDiaChi}
                  getOptionLabel={(option) =>
                    option.tenDuong +
                    ", " +
                    option.xaPhuong.split("?")[1] +
                    ", " +
                    option.quanHuyen.split("?")[1] +
                    ", " +
                    option.tinhThanhPho.split("?")[1]
                  }
                  getOptionValue={(option) => option.id}
                  name="tinh"
                  placeholder="Chọn địa chỉ của bạn"
                />
                <textarea
                  class="form-control formthanhtoan"
                  id="ghichudonhang"
                  rows="3"
                  placeholder="Ghi chú đơn hàng"
                ></textarea>
                <div class="row magg">
                  <div class="col-8">
                    <Select
                      className="select-container"
                      options={voucher}
                      onChange={setselectVoucher}
                      getOptionLabel={(option) =>
                        "- " +
                        (option.loaiPhieu == true
                          ? formatMoney(option.giaTriGiam)
                          : option.giaTriGiam + "%") +
                        ", tối thiểu " +
                        formatMoney(option.donToiThieu)
                      }
                      getOptionValue={(option) => option.id}
                      name="voucher"
                      placeholder="Chọn khuyến mại"
                    />
                  </div>
                  <div class="col-4">
                    <button
                      onClick={kiemTraVoucher}
                      type="button"
                      class="btnmagg"
                    >
                      Áp dụng
                    </button>
                  </div>
                  <div class="col-12" id="blockmess">
                    <span class="successvou">Mã giảm giá đã được áp dụng</span>
                  </div>
                  <div class="col-12" id="blockmessErr">
                    <br />
                    <i class="fa fa-warning"> </i>
                    <span id="messerr">Mã giảm giá không khả dụng</span>
                  </div>
                </div>
                <table class="table tablepay">
                  <tr onclick="vnpay.click()">
                    <td>
                      <label class="radiocustom">
                        <div>Thanh toán qua Ví Vnpay</div>
                        <input
                          onChange={() => setphuongthuctt("vnpay")}
                          type="radio"
                          name="paytype"
                        />
                        <span class="checkmark"></span>
                      </label>
                      <div className="logo-vnpay">
                        <img src={iconvnpay} />
                      </div>{" "}
                    </td>
                  </tr>

                  <tr onclick="code.click()">
                    <td>
                      <label class="radiocustom">
                        {" "}
                        Thanh toán khi nhận hàng (COD)
                        <input
                          onChange={() => setphuongthuctt("cod")}
                          type="radio"
                          name="paytype"
                        />
                        <span class="checkmark"></span>
                      </label>
                      <div className="logo-vnpay">
                        <img src={deliver} />
                      </div>{" "}
                    </td>
                  </tr>
                </table>
                <button
                  onClick={() => kiemTraThanhToan()}
                  data-bs-toggle="modal"
                  data-bs-target="#modaladd"
                  class="form-control btndathang"
                >
                  Xác nhận
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div
        class="modal fade"
        id="modaladd"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-lg modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">
                Xác nhận đặt hàng
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <table className="table table-bordered">
                <tbody>
                  <tr>
                    <th>Tổng dự tính</th>
                    <td>{formatMoney(tongTam)}</td>
                  </tr>
                  <tr>
                    <th>Giảm giá</th>
                    <td>{textGiamGia}</td>
                  </tr>
                  <tr>
                    <th>Phí vận chuyển</th>
                    <td>{formatMoney(phiShip)}</td>
                  </tr>
                  <tr>
                    <th>Phương thức thanh toán</th>
                    <td>{phuongthuctt}</td>
                  </tr>
                  <tr>
                    <th>Tổng tiền thanh toán</th>
                    <td>
                      {formatMoney(
                        Number(tongTam) + Number(phiShip) - Number(tienGiam)
                      )}
                    </td>
                  </tr>
                </tbody>
              </table>
              <button
                onClick={() => xacNhanDatHang()}
                class="form-control btndathang"
              >
                Xác nhận đặt hàng
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default GioHang;
