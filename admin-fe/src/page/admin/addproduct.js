import { useState, useEffect } from "react";
import { toast } from "react-toastify";
import {
  getMethod,
  uploadSingleFile,
  uploadMultipleFile,
  postMethodPayload,
} from "../../services/request";
import Swal from "sweetalert2";
import Select from "react-select";
import { Editor } from "@tinymce/tinymce-react";

var linkbanner = "";
const AdminAddProduct = () => {
  const [product, setProduct] = useState({});
  const [thuonghieu, setThuongHieu] = useState([]);
  const [chatlieu, setChatLieu] = useState([]);
  const [degiay, setDeGiay] = useState([]);
  const [selectedThuongHieu, setSelectedThuongHieu] = useState(null);
  const [selectedChatLieu, setSelectedChatLieu] = useState(null);
  const [selectedDeGiay, setSelectedDeGiay] = useState(null);
  const [label, setLabel] = useState("Thêm sản phẩm");
  useEffect(() => {
    const fetchProductData = async () => {
      var uls = new URL(document.URL);
      var id = uls.searchParams.get("id");

      if (id != null) {
        setLabel("Cập nhật sản phẩm");
        const response = await getMethod("/api/san-pham/" + id);
        const result = await response.json();
        setProduct(result);
        setSelectedThuongHieu(result.thuongHieu);
        setSelectedChatLieu(result.chatLieu);
        setSelectedDeGiay(result.deGiay);
        linkbanner = result.anh;
      } else {
        // Tạo mã sản phẩm mới khi thêm sản phẩm
        try {
          const listResponse = await getMethod("/api/san-pham");
          const listResult = await listResponse.json();
          console.log("sdgshgsdhgshs", listResult);

          const maMoi = `SP${String(Number(listResult.length) + 1).padStart(
            3,
            "0"
          )}`;
          setProduct((prev) => ({ ...prev, maSanPham: maMoi }));
        } catch (error) {
          toast.error("Không thể tạo mã sản phẩm mới.");
        }
      }
    };

    const fetchSelectData = async () => {
      const responseDeGiay = await getMethod("/api/de-giay");
      setDeGiay(await responseDeGiay.json());
      const responseThuongHieu = await getMethod("/api/thuong-hieu");
      setThuongHieu(await responseThuongHieu.json());
      const responseChatLieu = await getMethod("/api/chat-lieu");
      setChatLieu(await responseChatLieu.json());
    };

    fetchProductData();
    fetchSelectData();
  }, []);

  async function handleAddSanPham(event) {
    event.preventDefault();

    // Lấy thông tin người dùng từ localStorage
    var user = JSON.parse(localStorage.getItem("user"));
    console.log("useer", user);
    if (!user) {
      toast.error("Người dùng không hợp lệ.");
      return;
    }

    // Lấy giá trị từ form
    const masp = event.target.elements.masp.value;
    const tensp = event.target.elements.tensp.value.trim();
    const trangThai = event.target.elements.trangThai.value; // Lấy giá trị từ radio button
    const giaBan = parseFloat(event.target.elements.giaBan.value);

    // Kiểm tra hợp lệ
    if (!masp) {
      toast.error("Mã sản phẩm không được để trống.");
      return;
    }
    if (!tensp) {
      toast.error("Tên sản phẩm không được để trống.");
      return;
    }
    if (!trangThai || (trangThai !== "1" && trangThai !== "2")) {
      toast.error(
        "Trạng thái không hợp lệ. Vui lòng chọn Còn hàng hoặc Hết hàng."
      );
      return;
    }
    if (isNaN(giaBan) || giaBan <= 0) {
      toast.error("Giá bán phải là một số hợp lệ và lớn hơn 0.");
      return;
    }

    // Upload hình ảnh
    var ims = await uploadSingleFile(document.getElementById("imgbanner"));
    let linkbanner = ims || null;

    // Tạo payload
    const payload = {
      maSanPham: masp || null,
      tenSanPham: tensp || null,
      idThuongHieu: selectedThuongHieu?.id || null,
      idChatLieu: selectedChatLieu?.id || null,
      idDeGiay: selectedDeGiay?.id || null,
      nguoiTao: user.maNhanVien,
      nguoiCapNhat: user.maNhanVien,
      trangThai: parseInt(trangThai), // Đảm bảo chuyển thành số hoặc null
      giaBan: giaBan,
      anh: linkbanner || null,
    };
    console.log("push", payload);
    // Kiểm tra các trường ID bắt buộc
    if (!payload.idThuongHieu) {
      toast.error("Vui lòng chọn thương hiệu.");
      return;
    }
    if (!payload.idChatLieu) {
      toast.error("Vui lòng chọn chất liệu.");
      return;
    }
    if (!payload.idDeGiay) {
      toast.error("Vui lòng chọn đế giày.");
      return;
    }

    // Nếu đang cập nhật, giữ nguyên người tạo ban đầu
    if (product.idThuongHieu != null) {
      payload.nguoiTao = product.nguoiTao;
    }

    // Xác định URL
    var url = "/api/san-pham";
    if (product.idThuongHieu != null) {
      url += "/" + product.id;
    }

    // Gửi payload qua API
    const res = await postMethodPayload(url, payload);

    // Xử lý phản hồi
    if (res.status < 300) {
      toast.success("Thêm Sản Phẩm Thành công!");
      await new Promise((resolve) => setTimeout(resolve, 1000));
      window.location.href = "product";
    } else if (res.status === 417) {
      const result = await res.json();
      toast.error(result.defaultMessage);
    } else if (res.status > 300) {
      const result = await res.json();
      toast.error(result.message);
    }
  }
  console.log('products', product);

  return (
    <div>
      <div class="col-sm-12 header-sps">
        <div class="title-add-admin">
          <h4>{label}</h4>
        </div>
      </div>
      <div class="col-sm-12">
        <div class="form-add">
          <div class="form-add">
            <form class="row" onSubmit={handleAddSanPham} method="post">
              <div class="col-md-5 col-sm-12 col-12">
                <label class="lb-form">Mã sản phẩm</label>
                <input
                  name="masp"
                  value={product?.maSanPham || ""}
                  class="form-control"
                />
                <label class="lb-form">Tên sản phẩm</label>
                <input
                  name="tensp"
                  defaultValue={product?.tenSanPham || ""}
                  class="form-control"
                />
                <label class="lb-form">Giá bán</label>
                <input
                  name="giaBan"
                  defaultValue={product?.sanPhamChiTiets?.[0]?.giaTien || 0}
                  class="form-control"
                />
                <label className="lb-form">Trạng thái</label>
                <div>
                  <label className="lb-form">
                    <input
                      type="radio"
                      name="trangThai"
                      value="1"
                      defaultChecked={
                        !product?.trangThai || product?.trangThai === 1
                      }
                    />
                    Còn hàng
                  </label>
                  <label className="lb-form">
                    <input
                      type="radio"
                      name="trangThai"
                      value="2"
                      defaultChecked={product?.trangThai === 2}
                    />
                    Hết hàng
                  </label>
                </div>

                <label
                  class="lb-form"
                  dangerouslySetInnerHTML={{ __html: "&ThinSpace;" }}
                ></label>
                <button class="btn btn-primary form-control">{label}</button>
              </div>
              <div class="col-md-5 col-sm-12 col-12">
                <label class="lb-form">
                  Chọn ảnh {product != null ? " (Bỏ trống để dùng ảnh cũ)" : ""}
                </label>
                <input id="imgbanner" type="file" class="form-control" />
                <label class="lb-form">Thương hiệu</label>
                <Select
                  className="select-container"
                  options={thuonghieu}
                  value={selectedThuongHieu || null}
                  onChange={setSelectedThuongHieu}
                  getOptionLabel={(option) => option.tenThuongHieu}
                  getOptionValue={(option) => option.id}
                  name="thuonghieu"
                  placeholder="Chọn thương hiệu"
                />
                <label class="lb-form">Chất liệu</label>
                <Select
                  className="select-container"
                  options={chatlieu}
                  value={selectedChatLieu || null}
                  onChange={setSelectedChatLieu}
                  getOptionLabel={(option) => option.tenChatLieu}
                  getOptionValue={(option) => option.id}
                  name="chatlieu"
                  placeholder="Chọn chất liệu"
                />
                <label class="lb-form">Đế giày</label>
                <Select
                  className="select-container"
                  options={degiay}
                  value={selectedDeGiay || null}
                  onChange={setSelectedDeGiay}
                  getOptionLabel={(option) => option.tenDeGiay}
                  getOptionValue={(option) => option.id}
                  name="degiay"
                  placeholder="Chọn đế giày"
                />
              </div>
              {/* <div class="modal-body">
                <form
                  class="col-sm-5 marginauto"
                  // onSubmit={saveData}
                  method="post"
                >
                  <label class="lb-form">Mã sản phẩm chi tiết</label>
                  <input
                    //   defaultValue={item?.maSanPhamChiTiet}
                    name="maSanPhamChiTiet"
                    type="text"
                    class="form-control"
                  />
                  <label class="lb-form">Số lượng</label>
                  <input
                    //   defaultValue={item?.soLuong}
                    name="soLuong"
                    type="text"
                    class="form-control"
                  />
                  <label class="lb-form">Giá tiền</label>
                  <input
                    //   defaultValue={item?.giaTien}
                    name="giaTien"
                    type="text"
                    class="form-control"
                  />
                  <label className="lb-form">Trạng thái</label>
                  <div>
                    <label>
                      <input
                        type="radio"
                        name="trangThai"
                        value="1"
                        //   defaultChecked={!item?.trangThai || item?.trangThai === 1}
                      />
                      Còn hàng
                    </label>
                    <label style={{ marginLeft: "10px" }}>
                      <input
                        type="radio"
                        name="trangThai"
                        value="2"
                        //   defaultChecked={item?.trangThai === 2}
                      />
                      Hết hàng
                    </label>
                  </div>

                  <label class="lb-form">Màu sắc</label>
                  <Select
                    className="select-container"
                    //   options={mausac}
                    //   value={selectedMauSac}
                    //   onChange={setSelectedMauSac}
                    //   getOptionLabel={(option) => option.tenMauSac}
                    //   getOptionValue={(option) => option.id}
                    name="mausac"
                    placeholder="Chọn màu sắc"
                  />
                  <label class="lb-form">Kích cỡ</label>
                  <Select
                    className="select-container"
                    //   options={kichco}
                    //   value={selectedKichCo}
                    //   onChange={setSelectedKichCo}
                    //   getOptionLabel={(option) => option.tenKichCo}
                    //   getOptionValue={(option) => option.id}
                    name="kichco"
                    placeholder="Chọn kích cỡ"
                  />
                  <br />
                  <br />
                  <button class="btn btn-success form-control action-btn">
                    Xác nhận
                  </button>
                </form>
              </div> */}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminAddProduct;
