import { useState, useEffect } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import { Navigation, Pagination } from "swiper/modules";
import {
  getMethod,
  uploadSingleFile,
  uploadMultipleFile,
  postMethodPayload,
} from "../services/request";
import { formatMoney } from "../services/money";
import { toast } from "react-toastify";
import { colors } from "@mui/material";

function ChiTietSanPham() {
  const [product, setProduct] = useState(null);
  const [selectChiTiet, setSelectChiTiet] = useState(null);
  const [indexmausac, setIndexmausac] = useState(-1);
  const [indexkichthuoc, setindexkichthuoc] = useState(-1);
  const [sanPhamChiTiet, setSanPhamChiTiet] = useState([]);
  const [kichthuoc, setKichthuoc] = useState([]);
  const [mausac, setMauSac] = useState([]);
  const [anhs, setAnhs] = useState([]);
  const [sanPhamLienQuan, setsanPhamLienQuan] = useState([]);
  const [giaTien, setGiaTien] = useState("");
  const [productAnh, setProductAnh] = useState("");
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    getProduct();
    getChiTietSanPham();
    getProductLienQuan();
  }, []);

  const getProduct = async () => {
    var uls = new URL(document.URL);
    var id = uls.searchParams.get("id");
    if (id != null) {
      var response = await getMethod("/api/san-pham/" + id);
      var result = await response.json();

      setProduct(result);
    }
  };

  const getProductLienQuan = async () => {
    var uls = new URL(document.URL);
    var id = uls.searchParams.get("id");
    if (id != null) {
      var response = await getMethod(
        "/api/san-pham/san-pham-lien-quan?id=" + id
      );
      var result = await response.json();
      var responseLq = await getMethod(
        "/api/san-pham-chi-tiet/findBySanPham?sanpham=" + id
      );
      var resultLq = await responseLq.json();
      if (resultLq.length > 0) {
        const minGiaTien = Math.min(...resultLq.map((item) => item.giaTien));
        result = result.map((item) => ({
          ...item,
          giaBan: minGiaTien,
        }));
      }
      setsanPhamLienQuan(result);
    }
  };

  async function getChiTietSanPham() {
    var uls = new URL(document.URL);
    var id = uls.searchParams.get("id");
    var response = await getMethod(
      "/api/san-pham-chi-tiet/findBySanPham?sanpham=" + id
    );
    var result = await response.json();
    setSanPhamChiTiet(result);
    if (result.length > 0) {
      const minGiaTien = Math.min(...result.map((item) => item.giaTien));
      setGiaTien(minGiaTien);
    }

    var listmausac = [];
    var listAnh = [];
    for (var i = 0; i < result.length; i++) {
      var checked = false;
      for (var j = 0; j < listmausac.length; j++) {
        if (listmausac[j].mauSac.id == result[i].mauSac.id) {
          checked = true;
        }
      }
      if (checked == false) {
        listmausac.push(result[i]);
      }
      listAnh = listAnh.concat(result[i].anhs);
    }
    setMauSac(listmausac);
    setAnhs(listAnh);
    if (listAnh.length > 0) {
      setProductAnh(listAnh[0].tenAnh);
    }
  }

  function loadKichThuoc(item, index) {
    setGiaTien(item.giaTien);
    setIndexmausac(index);
    var listkichthuoc = [];
    for (var i = 0; i < sanPhamChiTiet.length; i++) {
      if (sanPhamChiTiet[i].mauSac.id == item.mauSac.id) {
        listkichthuoc.push(sanPhamChiTiet[i]);
      }
    }
    setKichthuoc(listkichthuoc);
    setindexkichthuoc(-1);

    setSelectChiTiet(null);
    console.log("ksdf", listkichthuoc);
  }

  function setChiTietSpChon(item, index) {
    setindexkichthuoc(index);
    setSelectChiTiet(item);
  }

  async function addToCart() {
    var sl = document.getElementById("soluongaddgiohang").value;
    if (sl > selectChiTiet.soLuong) {
      toast.warning(
        "Số lượng thêm không được vượt quá " + selectChiTiet.soLuong
      );
      return;
    }
    console.log("id", selectChiTiet.id);
    const res = await postMethodPayload(
      `/api/gio-hang/add?idChiTietSanPham=${selectChiTiet.id}&soLuong=${sl}`
    );
    if (res.status < 300) {
      toast.success("Đã thêm sản phẩm vào giỏ gàng!");
    }
    if (res.status == 417) {
      var result = await res.json();
      toast.error(result.defaultMessage);
    }
  }

  function upDownQuantity(value) {
    var sl = document.getElementById("soluongaddgiohang").value;
    sl = Number(sl) + Number(value);
    if (sl < 1) {
      toast.warning("Số lượng nhỏ nhất là 1");
      return;
    }
    document.getElementById("soluongaddgiohang").value = sl;
  }
  function checkSoLuong() {
    var sl = document.getElementById("soluongaddgiohang").value;
    if (isNumeric(sl) == false) {
      toast.warning("Số không được nhập kiểu ký tự");
      document.getElementById("soluongaddgiohang").value = 1;
      return;
    }
  }

  function isNumeric(value) {
    return !isNaN(value) && !isNaN(parseFloat(value));
  }

  return (
    <div class="maincontentweb">
      <div class="containercustom container">
        <div class="container-fluid">
          <div class="bgwhite">
            <div class="row">
              <div class="col-sm-12">
                <div class="row">
                  <div class="col-sm-7">
                    <div
                      id="carouselExampleControls"
                      class="carousel slide bannerindex"
                      data-bs-ride="carousel"
                    >
                      <div class="carousel-inner carousel-chitiet">
                        <div class="carousel-item  active">
                          <img
                            src={productAnh}
                            class="d-block w-100"
                            alt="..."
                          />
                        </div>
                        {anhs.map((item, index) => {
                          return (
                            <div class="carousel-item">
                              <img
                                src={item.tenAnh}
                                class="d-block w-100"
                                alt="..."
                              />
                            </div>
                          );
                        })}
                      </div>
                      <button
                        class="carousel-control-prev"
                        type="button"
                        data-bs-target="#carouselExampleControls"
                        data-bs-slide="prev"
                      >
                        
                        <span
                          class="carousel-control-prev-icon"
                          aria-hidden="true"
                        ></span>
                      </button>
                      <button
                        class="carousel-control-next"
                        type="button"
                        data-bs-target="#carouselExampleControls"
                        data-bs-slide="next"
                      >
                        <span
                        
                          class="carousel-control-next-icon"
                          aria-hidden="true"
                        ></span>
                      </button>
                    </div>
                  </div>
                  <div class="col-sm-5">
                    <h4 class="pronamedetail" id="detailnamepro">
                      {product?.tenSanPham}
                    </h4>
                    <div>
                      <strong
                        class="newpricestr"
                        id="pricedetail"
                        style={{ color: "red" }}
                      >
                        {formatMoney(giaTien)}
                      </strong>
                    </div>
                    <span id="storagedetaillable" class="storagedetaillable">
                      Lựa chọn màu sắc
                    </span>
                    <div class="listsize row" id="listcolor">
                      {mausac.map((item, index) => {
                        const divClass = `${"colorcdiv"} ${
                          index === indexmausac ? "activecolor" : ""
                        }`;
                        return (
                          <div
                            className="col-lg-2 col-md-3 col-sm-6 col-6 mb-3 color-item"
                            key={index}
                          >
                            <div
                              className={divClass}
                              onClick={() => loadKichThuoc(item, index)}
                            >
                              <span className="storagedetail">
                                {item.mauSac.tenMauSac}
                              </span>
                              <br />
                            </div>
                          </div>
                        );
                      })}
                    </div>
                    {kichthuoc.length != 0 && (
                      <span
                        id="storagedetaillable"
                        class={`storagedetaillable`}
                      >
                        Lựa chọn kích thước
                      </span>
                    )}
                    <div class="listsize row" id="listcolor">
                      {kichthuoc.map((item, index) => {
                        const divClass = `${
                          item.soLuong === 0 ? "colorcdiv hetsp" : "colorcdiv"
                        } ${index === indexkichthuoc ? "activecolor" : ""}`;
                        return (
                          <div>
                            <div
                              className="col-lg-3 col-md-3 col-sm-6 col-6"
                              key={index}
                            >
                              <div
                                className={divClass}
                                onClick={() => {
                                  setGiaTien(item?.giaTien);
                                  if (item.soLuong > 0) {
                                    setChiTietSpChon(item, index);
                                  }
                                }}
                              >
                                <span className="storagedetail">
                                  {item.kichCo.tenKichCo}
                                </span>
                                <br />
                                {/* <span className="pricestorage">{formatMoney(item.giaTien)}</span> */}
                              </div>
                            </div>
                            <div className="mt-2" style={{ fontSize: "12px" }}>
                              Còn:{" "}
                              <span
                                style={{ color: "green", fontWeight: "800" }}
                              >
                                {item.soLuong}
                              </span>{" "}
                              sản phẩm
                            </div>
                          </div>
                        );
                      })}
                    </div>
                    <span id="storagedetaillable" class="storagedetaillable">
                      Lựa chọn số lượng
                    </span>
                    <div className="sl-container">
                      <button
                        onClick={() => upDownQuantity(-1)}
                        class="cartbtn"
                      >
                        {" "}
                        -{" "}
                      </button>
                      <input
                        onKeyUp={checkSoLuong}
                        defaultValue={1}
                        id="soluongaddgiohang"
                        class="inputslcart"
                      />
                      <button onClick={() => upDownQuantity(1)} class="cartbtn">
                        {" "}
                        +{" "}
                      </button>
                    </div>
                    <br />
                    <br />
                    <button
                      disabled={selectChiTiet == null}
                      onClick={() => addToCart()}
                      class={
                        selectChiTiet != null
                          ? "btnthemgiohang"
                          : "btnthemgiohang disablethemgh"
                      }
                    >
                      <i className="fa fa-shopping-bag"></i> Thêm giỏ hàng
                    </button>
                    <div class="cauhinhchitiet">
                      <p class="tieudesanpham">Thông tin sản phẩm</p>
                      <div id="thongtincauhinh" class="thongtincauhinhdv">
                        <span class="congnghect">
                          Mã sản phẩm:{" "}
                          <span class="chitietcongnghe">
                            {product?.maSanPham}
                          </span>
                        </span>
                        <span class="congnghect">
                          Thương hiệu:{" "}
                          <span class="chitietcongnghe">
                            {product?.thuongHieu?.tenThuongHieu}
                          </span>
                        </span>
                        <span class="congnghect">
                          Chất liệu:{" "}
                          <span class="chitietcongnghe">
                            {product?.chatLieu?.tenChatLieu}
                          </span>
                        </span>
                        <span class="congnghect">
                          Đế giày:{" "}
                          <span class="chitietcongnghe">
                            {product?.deGiay?.tenDeGiay}
                          </span>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="sanphamtuongtudetail">
                  <p class="tieudesanpham">Sản phẩm cùng thương hiệu</p>
                  <div id="listsanphamlienquan" class="row">
                    {sanPhamLienQuan.map((item) => {
                      return (
                        <div class="col-lg-3 col-md-12 col-sm-12 col-12">
                          <div class="singleproduct">
                            <a href={"chitietsanpham?id=" + item.id}>
                              <div className="productimg-container">
                                <img
                                  src={item.sanPhamChiTiets[0]?.anhs[0]?.tenAnh || ""}
                                  class="productimg"
                                />
                              </div>
                            </a>
                            <div class="contentsinglepro">
                              <p class="productname">
                                <a
                                  class="productname"
                                  href={"chitietsanpham?id=" + item.id}
                                >
                                  {item.tenSanPham}
                                </a>
                              </p>
                              <div class="priceproduct">
                                <strong class="newprice">
                                  {formatMoney(item.giaBan)}
                                </strong>
                              </div>
                            </div>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ChiTietSanPham;
