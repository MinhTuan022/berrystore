import logo from "../../assest/images/logo.svg";

function footer() {
  return (
    <div id="footer" className="mt-5">
      <div class="container">
        <footer class="text-center text-lg-start text-muted">
          <section class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
            <div class="me-5 d-none d-lg-block">
              <span>Theo dõi chúng tôi tại:</span>
            </div>
            <div>
              <a href="" class="me-4 text-reset">
                <i class="fab fa-facebook-f"></i>
              </a>
              <a href="" class="me-4 text-reset">
                <i class="fab fa-twitter"></i>
              </a>
              <a href="" class="me-4 text-reset">
                <i class="fab fa-google"></i>
              </a>
              <a href="" class="me-4 text-reset">
                <i class="fab fa-instagram"></i>
              </a>
              <a href="" class="me-4 text-reset">
                <i class="fab fa-linkedin"></i>
              </a>
              <a href="" class="me-4 text-reset">
                <i class="fab fa-github"></i>
              </a>
            </div>
          </section>
          <section class="">
            <div class=" text-center text-md-start mt-5">
              <div class="row mt-3">
                <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                  <a class="navbar-brand tagaheadersdt" href="/">
                    <img src={logo} className="logoheader" />
                    <span className="brand-name">BerryShoes</span>
                  </a>
                  <hr></hr>
                  {/* <h6 class="text-uppercase fw-bold mb-4"><i class="fa fa-mobile"></i> PhoneStore</h6> */}
                  <p>
                    Chúng tôi cung cấp các sản phẩm giày thể thao, chất lượng
                    cao đến người tiêu dùng
                  </p>
                </div>
                <div class="col-md-6 col-lg-2 col-xl-3 mx-auto mb-4">
                  <h6 class="text-uppercase fw-bold mb-4">Về chúng tôi</h6>
                  <div className="text-ft">
       
                      Hotline: 0869839386

                  </div>
                  <p>
                    <a href="#!" class="text-reset">
                      Email: studio.berryfoto@gmail.com
                    </a>
                  </p>
                  <p>
                    <a href="#!" class="text-reset">
                      Địa chỉ: Số 386, TVB, Nam Từ Liêm, Hà nội
                    </a>
                  </p>
                </div>
                <div class="col-md-2 col-lg-2 col-xl-3 mx-auto mb-4">
                  <h6 class="text-uppercase fw-bold mb-4">Hỗ trợ khách hàng</h6>
                  <p>
                    <a href="#!" class="text-reset">
                      Uy tín
                    </a>
                  </p>
                  <p>
                    <a href="#!" class="text-reset">
                      Chất lượng
                    </a>
                  </p>
                  <p>
                    <a href="#!" class="text-reset">
                      Nguồn gốc rõ ràng
                    </a>
                  </p>
                  {/* <p><a href="#!" class="text-reset">Giá rẻ</a></p> */}
                </div>
                <div class="col-md-1 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                  <h6 class="text-uppercase fw-bold mb-4">Liên hệ</h6>
                  <p>
                    <i class="fas fa-home me-3"></i> Hà nội, Việt Nam
                  </p>
                  <p>
                    <i class="fas fa-envelope me-3"></i>{" "}
                    studio.berryfoto@gmail.com
                  </p>
                  <p>
                    <i class="fas fa-phone me-3"></i> + 84 869 839 386
                  </p>
                  {/* <p><i class="fas fa-print me-3"></i> + 01 234 567 89</p> */}
                </div>
              </div>
            </div>
          </section>
        </footer>
      </div>
    </div>
  );
}

export default footer;
