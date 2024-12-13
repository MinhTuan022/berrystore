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

function DiaChi() {
  const [diaChis, setDiaChis] = useState([]);
  const [tinh, setTinh] = useState([]);
  const [huyen, setHuyen] = useState([]);
  const [xa, setXa] = useState([]);
  const [selecttinh, setselecttinh] = useState(null);
  const [selecthuyen, setselecthuyen] = useState(null);
  const [selectxa, setselectxa] = useState(null);
  useEffect(() => {
    getDiaChi();
    getTinh();
  }, []);

  const getDiaChi = async () => {
    var response = await getMethod("/api/dia-chi");
    var list = await response.json();
    setDiaChis(list);
  };

  const getTinh = async () => {
    var response = await getMethod("/api/shipping/public/province");
    var list = await response.json();
    setTinh(list.data);
    loadHuyen(list.data[0]);
  };

  async function loadHuyen(item) {
    setselecttinh(item);
    var response = await getMethod(
      "/api/shipping/public/district?provinceId=" + item.ProvinceID
    );
    var list = await response.json();
    setHuyen(list.data);
    loadXa(list.data[0]);
  }

  async function loadXa(item) {
    setselecthuyen(item);
    var response = await getMethod(
      "/api/shipping/public/wards?districtId=" + item.DistrictID
    );
    var list = await response.json();
    setXa(list.data);
    if (list.data != null) {
      setselectxa(list.data[0]);
    } else {
      setselectxa(null);
    }
  }

  async function handleAddDiaChi(event) {
    event.preventDefault();
    const payload = {
      tenDuong: event.target.elements.stressadd.value,
      xaPhuong: selectxa.WardCode + "?" + selectxa.WardName,
      quanHuyen: selecthuyen.DistrictID + "?" + selecthuyen.DistrictName,
      tinhThanhPho: selecttinh.ProvinceID + "?" + selecttinh.ProvinceName,
      tenNguoiNhan: event.target.elements.fullnameadd.value,
      sdtNguoiNhan: event.target.elements.phoneadd.value,
    };
    console.log(payload);
    const res = await postMethodPayload("/api/dia-chi", payload);
    if (res.status < 300) {
      toast.success("Success!");
      await new Promise((resolve) => setTimeout(resolve, 1000));
      window.location.reload();
    }
    if (res.status == 417) {
      var result = await res.json();
      toast.error(result.defaultMessage);
    }
  }

  async function deleteDiaChi(id) {
    var con = window.confirm("Confirm?");
    if (con == false) {
      return;
    }
    const response = await deleteMethod("/api/dia-chi/" + id);
    if (response.status < 300) {
      toast.success("Xóa thành công!");
      getDiaChi();
    }
    if (response.status > 300) {
      var result = await response.json();
      toast.warning(result.message);
    }
  }

  return (
    <>
      <div class="headeraccount">
        <p class="fontyel">Địa chỉ của bạn</p>
        <div class="right_flex">
          <button
            onclick="clearData()"
            data-bs-toggle="modal"
            data-bs-target="#modaladd"
            class="btnsuathongtin"
          >
            + Thêm địa chỉ mới
          </button>
        </div>
      </div>
      <div class="contentacc" id="listaddacc">
        {diaChis.map((item) => {
          return (
            <div class="row singleadd">
              <div class="col-lg-11 col-md-11 col-sm-12 col-12">
                <table class="table tableadd">
                  <tr>
                    <td class="tdleft">Họ tên:</td>
                    <td class="tdright">{item.tenNguoiNhan}</td>
                  </tr>
                  <tr>
                    <td class="tdleft">Địa chỉ:</td>
                    <td class="tdright">
                      {item.tenDuong} , {item.xaPhuong.split("?")[1]},{" "}
                      {item.quanHuyen.split("?")[1]},{" "}
                      {item.tinhThanhPho.split("?")[1]}
                    </td>
                  </tr>
                  <tr>
                    <td class="tdleft">Số điện thoại:</td>
                    <td class="tdright">{item.sdtNguoiNhan}</td>
                  </tr>
                  <tr>
                    <td class="tdleft">Ngày tạo:</td>
                    <td class="tdright">{item.ngayTao}</td>
                  </tr>
                </table>
              </div>
              <div class="col-lg-1 col-md-1 col-sm-12 col-12">
                <span
                  onClick={() => deleteDiaChi(item.id)}
                  class="actionacc acdel"
                >
                  Xóa
                </span>
              </div>
            </div>
          );
        })}
      </div>

      <div
        class="modal fade"
        id="modaladd"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-lg modal-fullscreen-sm-down modeladdres">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">
                Thêm địa chỉ mới
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <form onSubmit={handleAddDiaChi} class="modal-body">
              <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-12 col-12 formadd">
                  <input
                    name="fullnameadd"
                    class="form-control fomd"
                    placeholder="Họ tên"
                  />
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-12 formadd">
                  <input
                    name="phoneadd"
                    class="form-control fomd"
                    placeholder="Số điện thoại"
                  />
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-12 formadd">
                  <input
                    name="stressadd"
                    class="form-control fomd"
                    placeholder="Tên đường, số nhà"
                  />
                </div>
                <div class="col-lg-4 col-md-4 col-sm-12 col-12 formadd">
                  <label class="lbsatop">Tỉnh thành</label>
                  <Select
                    className="select-container"
                    options={tinh}
                    value={selecttinh}
                    onChange={loadHuyen}
                    getOptionLabel={(option) => option.ProvinceName}
                    getOptionValue={(option) => option.ProvinceID}
                    name="tinh"
                    placeholder="Chọn tỉnh"
                  />
                </div>
                <div class="col-lg-4 col-md-4 col-sm-12 col-12 formadd">
                  <label class="lbsatop">Quận/ huyện</label>
                  <Select
                    className="select-container"
                    options={huyen}
                    value={selecthuyen}
                    onChange={loadXa}
                    getOptionLabel={(option) => option.DistrictName}
                    getOptionValue={(option) => option.DistrictID}
                    name="huyen"
                    placeholder="Chọn huyện"
                  />
                </div>
                <div class="col-lg-4 col-md-4 col-sm-12 col-12 formadd">
                  <label class="lbsatop">Phường/ xã</label>
                  <Select
                    className="select-container"
                    options={xa}
                    value={selectxa}
                    onChange={setselectxa}
                    getOptionLabel={(option) => option.WardName}
                    getOptionValue={(option) => option.WardCode}
                    name="xa"
                    placeholder="Chọn xã"
                  />
                </div>
              </div>
              <div class="col-lg-6 col-md-6">
                <button onclick="addAddressUser()" class="btntt">
                  LƯU
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}

export default DiaChi;
