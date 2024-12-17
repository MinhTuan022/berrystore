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
import "bootstrap/dist/css/bootstrap.min.css";
import Slider from "@mui/material/Slider";
import Typography from "@mui/material/Typography";
import Select from "react-select";
import ReactPaginate from "react-paginate";

var size = 6;
var url = "";
function Product() {
  const [thuongHieu, setThuongHieu] = useState([]);
  const [deGiay, setDeGiay] = useState([]);
  const [chatlieu, setChatLieu] = useState([]);
  const [price, setPrice] = useState([100000, 50000000]);
  const [selectThuongHieu, setselectThuongHieu] = useState([]);
  const [selectDeGiay, setselectDeGiay] = useState([]);
  const [selectChatLieu, setselectChatLieu] = useState([]);
  const [pageCount, setpageCount] = useState(0);
  const [sanPham, setSanPham] = useState([]);
  const [payload, setPayload] = useState(null);

  useEffect(() => {
    const getSelect = async () => {
      var response = await getMethod("/api/thuong-hieu");
      var list = await response.json();
      setThuongHieu(list);

      var response = await getMethod("/api/chat-lieu");
      var list = await response.json();
      setChatLieu(list);

      var response = await getMethod("/api/de-giay");
      var list = await response.json();
      setDeGiay(list);
    };
    getSelect();
    getInitProduct();
  }, []);
  const handleChange = (event, newValue) => {
    setPrice(newValue);
  };

  async function getInitProduct() {
    console.log("dấda");
    var uls = new URL(document.URL);
    var thuonghieu = uls.searchParams.get("thuonghieu");
    var search = uls.searchParams.get("search");
    var urls = "/api/san-pham/public";
    if (thuonghieu == null && search == null) {
      filterProduct();
      console.log("a??");
      return;
    }
    if (thuonghieu != null) {
      urls +=
        "/tim-theo-thuong-hieu?thuongHieu=" +
        thuonghieu +
        "&size=" +
        size +
        "&sort=id,desc" +
        "&page=";
    }
    if (search != null) {
      urls +=
        "/tim-theo-ten?search=" +
        search +
        "&size=" +
        size +
        "&sort=id,desc" +
        "&page=";
    }
    console.log("ủl", uls);
    var response = await getMethod(urls + 0);
    var result = await response.json();
    console.log("ád", result);
    setSanPham(result.content);
    setpageCount(result.totalPages);
    url = urls;
  }

  async function filterProduct() {
    var arrThuongHieu = [];
    var arrDeGiay = [];
    var arrChatLieu = [];
    for (var i = 0; i < selectThuongHieu.length; i++) {
      arrThuongHieu.push(selectThuongHieu[i].id);
    }
    for (var i = 0; i < selectDeGiay.length; i++) {
      arrDeGiay.push(selectDeGiay[i].id);
    }
    for (var i = 0; i < selectChatLieu.length; i++) {
      arrChatLieu.push(selectChatLieu[i].id);
    }
    var obj = {
      idThuongHieu: arrThuongHieu,
      idDeGiay: arrDeGiay,
      idChatLieu: arrChatLieu,
      small: price[0],
      large: price[1],
    };
    console.log(obj);

    setPayload(obj);
    var response = await postMethodPayload(
      "/api/san-pham/public/loc-san-pham?size=" +
        size +
        "&sort=id,desc&page=" +
        0,
      obj
    );
    var result = await response.json();
    console.log("res", result);

    setSanPham(result.content);
    setpageCount(result.totalPages);
    url =
      "/api/san-pham/public/loc-san-pham?size=" + size + "&sort=id,desc&page=";
  }

  const handlePageClick = async (data) => {
    var currentPage = data.selected;
    if (payload == null) {
      var response = await getMethod(url + currentPage);
      var result = await response.json();
      setSanPham(result.content);
      setpageCount(result.totalPages);
    } else {
      var response = await postMethodPayload(url + currentPage, payload);
      var result = await response.json();
      setSanPham(result.content);
      setpageCount(result.totalPages);
    }
  };

  return (
    <div class="maincontentweb">
      <div class="containercustom container">
        <div class="container-fluid">
          <div className="row">
            <div className="col-sm-4">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0 listheadermenu">
                <h3>Tìm kiếm sản phẩm</h3>
                <form class="mb-3" action="product">
                  <input
                    class="form-control"
                    type="search"
                    name="search"
                    placeholder="Tìm kiếm sản phẩm"
                    aria-label="Search"
                  />
                </form>
              </ul>
              <div className="headerloc">
                <span>Bộ lọc sản phẩm</span>
              </div>
              <div>
                <Typography variant="h6">Chọn khoảng giá</Typography>
                <Slider
                  value={price}
                  onChange={handleChange}
                  valueLabelDisplay="auto"
                  min={100000}
                  max={50000000}
                />
                <Typography>
                  Khoảng giá: {formatMoney(price[0])} - {formatMoney(price[1])}
                </Typography>
              </div>
              <br />
              <h5>Chọn thương hiệu</h5>
              <Select
                className="select-container selectheader"
                options={thuongHieu}
                onChange={setselectThuongHieu}
                value={selectThuongHieu}
                getOptionLabel={(option) => option.tenThuongHieu}
                getOptionValue={(option) => option.id}
                isMulti
                placeholder="Chọn thương hiệu"
              />
              <br />
              <h5>Chọn chất liệu</h5>
              <Select
                className="select-container selectheader"
                options={chatlieu}
                onChange={setselectChatLieu}
                value={selectChatLieu}
                getOptionLabel={(option) => option.tenChatLieu}
                getOptionValue={(option) => option.id}
                isMulti
                placeholder="Chọn chất liệu"
              />
              <br />
              <h5>Chọn loại đế giày</h5>
              <Select
                className="select-container selectheader"
                options={deGiay}
                onChange={setselectDeGiay}
                value={selectDeGiay}
                getOptionLabel={(option) => option.tenDeGiay}
                getOptionValue={(option) => option.id}
                isMulti
                placeholder="Chọn loại đế giày"
              />
              <br />

              <button
                style={{
                  backgroundColor: "rgb(236, 221, 198)",
                  color: "#212529",
                  borderColor: "rgb(236, 221, 198)",
                }}
                onClick={() => filterProduct()}
                className="form-control btn btn-primary"
              >
                Tìm kiếm sản phẩm
              </button>
              <br />
              <br />
              <br />
            </div>
            <div className="col-sm-8">
              <div className="row">
                {sanPham.map((item) => {
                  console.log("item", item);
                  return (
                    <div class="col-lg-4 col-md-4 col-sm-6 col-6">
                      <div class="singleproduct">
                        <a href={"chitietsanpham?id=" + item.id}>
                          <div className="productimg-container">
                            <img
                              src={item?.sanPhamChiTiets[0]?.anhs[0]?.tenAnh}
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
                              {formatMoney(item.sanPhamChiTiets[0].giaTien)}
                            </strong>
                          </div>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
              <br />
              <br />
              <ReactPaginate
                marginPagesDisplayed={2}
                pageCount={pageCount}
                onPageChange={handlePageClick}
                containerClassName={"pagination"}
                pageClassName={"page-item"}
                pageLinkClassName={"page-link"}
                previousClassName="page-item"
                previousLinkClassName="page-link"
                nextClassName="page-item"
                nextLinkClassName="page-link"
                breakClassName="page-item"
                breakLinkClassName="page-link"
                previousLabel="Trang trước"
                nextLabel="Trang sau"
                activeClassName="active"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Product;
