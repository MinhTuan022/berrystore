import { useState, useEffect } from "react";
import star from "../assest/images/icon_star.png";
import banner from "../assest/images/banner.jpg";
import banner1 from "../assest/images/banner1.png";
import banner2 from "../assest/images/banner2.jpg";
import newitem from "../assest/images/newitem.jpg";
import rightarrow from "../assest/images/right-arrow.png";
import arrow from "../assest/images/arrow.png";

import ReactPaginate from "react-paginate";
import {
  getMethod,
  uploadSingleFile,
  uploadMultipleFile,
  postMethodPayload,
} from "../services/request";
import { formatMoney } from "../services/money";

var size = 12;
var url = "";
function Home() {
  const [thuongHieu, setThuongHieu] = useState([]);
  const [sanPhamMoi, setSanPhamMoi] = useState([]);
  const [itemBlog, setItemBlog] = useState([]);
  const [pageCount, setpageCount] = useState(0);
  // const [dotGiamgia, setDotGiamgia] = useState(0);

  useEffect(() => {
    const getSelect = async () => {
      var response = await getMethod("/api/thuong-hieu");
      var list = await response.json();
      setThuongHieu(list);
    };
    getSelect();
    getSanPham();
  }, []);

  const getSanPham = async () => {
    var response = await getMethod(
      "/api/san-pham/public/all?&size=" + size + "&sort=id,desc&page=" + 0
    );
    var result = await response.json();
    console.log("???sdf", result);

    setSanPhamMoi(result.content);
    setpageCount(result.totalPages);
    url = "/api/san-pham/public/all?&size=" + size + "&sort=id,desc&page=";
  };

  const handlePageClick = async (data) => {
    var currentPage = data.selected;
    var response = await getMethod(url + currentPage);
    var result = await response.json();
    setSanPhamMoi(result.content);
    setpageCount(result.totalPages);
  };

  return (
    <div class="maincontentweb">
      <div class="containercustom container">
        <div class="container-fluid">
          <div class="row">
            <div class="col-sm-12">
              <div
                id="carouselExampleControls"
                class="carousel slide bannerindex"
                data-bs-ride="carousel"
              >
                <div class="carousel-inner">
                  <div class="carousel-item active">
                    <img src={banner} class="d-block w-100" alt="..." />
                  </div>
                  <div class="carousel-item">
                    <img src={banner1} class="d-block w-100" alt="..." />
                  </div>
                  <div class="carousel-item">
                    <img src={banner2} class="d-block w-100" alt="..." />
                  </div>
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
          </div>

          <div class="sectionmain cat_item_store cat_special">
            <div class="cat-titles">
              <h5 className="titlespmoi">
                Sản phẩm mới <img src={newitem} />
              </h5>
            </div>
            <hr />
            <div id="sanphammoinhat" class="listproduct row">
              {sanPhamMoi.map((item) => {
                var linkanh = "";
                var giaban = 0;
                var dotGiamGia = null;
                if (item.sanPhamChiTiets.length > 0) {
                  giaban = item.sanPhamChiTiets[0].giaTien;
                  if (item.sanPhamChiTiets[0].dotGiamGia != null) {
                    dotGiamGia = item.sanPhamChiTiets[0].dotGiamGia;
                  }
                }
                for (var i = 0; i < item.sanPhamChiTiets.length; i++) {
                  if (
                    item.sanPhamChiTiets[i].giaTien < giaban &&
                    item.sanPhamChiTiets[i].giaTien != null &&
                    item.sanPhamChiTiets[i].giaTien > 0
                  ) {
                    giaban = item.sanPhamChiTiets[i].giaTien;
                  }
                  if (
                    item.sanPhamChiTiets[i].anhs != null &&
                    item.sanPhamChiTiets[i].anhs.length > 0
                  ) {
                    linkanh = item.sanPhamChiTiets[i].anhs[0].tenAnh;
                    break;
                  }
                }

                return (
                  <div class="col-lg-20p col-md-3 col-sm-6 col-6 d-flex">
                    <div class="singleproduct w-100">
                      <a href={"chitietsanpham?id=" + item.id}>
                        <div className="productimg-container">
                          <img src={linkanh} class="productimg" />
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
                        <div class="price-view">
                          <div
                            style={{
                              display: "flex",
                              gap: "30px",
                              justifyContent: "center",
                              alignItems: "center",
                            }}
                            class="priceproduct"
                          >
                            <strong class="newprice">
                              {formatMoney(giaban)}
                            </strong>
                            {dotGiamGia == null ? (
                              ""
                            ) : (
                              <strong class="oldprice">
                                {formatMoney(giaban + dotGiamGia.giaTriGiam)}
                              </strong>
                            )}
                          </div>
                          {/* <div class="priceproduct">
                            <strong class="newprice">
                              {formatMoney(giaban)}
                            </strong>
                          </div>
                          <div class="priceproduct">
                            <strong class="oldprice">
                              {formatMoney(giaban)}
                            </strong>
                          </div> */}
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
              previousLabel={<img src={arrow}></img>}
              nextLabel={<img src={rightarrow}></img>}
              activeClassName="active"
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
