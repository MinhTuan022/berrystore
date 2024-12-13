import styles from "./header.scss";
import logo from "../../assest/images/logo.svg";
import { useState, useEffect } from "react";
import { getMethod } from "../../services/request";
import cart from "../../assest/images/cart.png";
import user from "../../assest/images/user.png";

import React, { createContext, useContext } from "react";
export const HeaderContext = createContext();

var token = localStorage.getItem("token");
function Header() {
  var [numCart, setNumCart] = useState(0);

  useEffect(() => {
    const getNumCart = async () => {
      const response = await getMethod("/api/gio-hang/count");
      if (response.status > 300) {
        setNumCart(0);
        return;
      }
      var numc = await response.text();
      setNumCart(numc);
    };
    if (token != null) {
      getNumCart();
    }
  }, []);

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.replace("login");
  }

  var token = localStorage.getItem("token");
  var authen = (
    <div class="itemiconheader">
      <div class="icon">
        <a href="">
          <i className="fa fa-sign-in"></i>
        </a>
      </div>
      <a href="login" class="txtheader">
        <span class="text-bg">Đăng nhập</span>
      </a>
    </div>
  );
  if (token != null) {
    authen = (
      <div class="itemiconheader">
        <span class="nav-item dropdown txtheader">
          <a
            class="nav-link txtheader"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <div class="icon">
              <img src={user}></img>
            </div>
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li>
              <a class="dropdown-item" href="taikhoan">
                Tài khoản
              </a>
            </li>
            <li>
              <hr class="dropdown-divider" />
            </li>
            <li>
              <a class="dropdown-item" href="#" onClick={() => logout()}>
                Đăng xuất
              </a>
            </li>
          </ul>
        </span>
      </div>
    );
  }
  return (
    <div id="menumain">
      <div class="containercustom container">
        <nav class="navbar navbar-expand-lg">
          <div class="container-fluid">
            <a class="navbar-brand tagaheadersdt" href="/">
              <img src={logo} className="logoheader" />
              <span className="brand-name">BerryShoes</span>
            </a>
            <button
              class="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span class="navbar-toggler-icon"></span>
            </button>
            <div
              class="collapse navbar-collapse justify-content-end "
              id="navbarSupportedContent"
            >
              {/* <ul class="navbar-nav me-auto mb-2 mb-lg-0 listheadermenu">
                        <form class="searchheader" action="product">
                            <input class="form-control" type="search" name="search" placeholder="Tìm kiếm sản phẩm" aria-label="Search"/>
                        </form>
                    </ul> */}
              <div class="d-flex menu-header">
                <div class="itemiconheader">
                  {/* <div class="icon">
                    <a href="">
                      <i className="fa fa-search"></i>
                    </a>
                  </div> */}
                  <a href="/" class="txtheader">
                    <span class="text-bg">Trang Chủ</span>
                  </a>
                </div>
                {/* <div class="itemiconheader">
                  <div class="icon">
                    <a href="">
                      <i className="fa fa-search"></i>
                    </a>
                  </div>
                  <a href="product" class="txtheader">
                    <span class="text-bg">Sản Phẩm</span>
                  </a>
                </div> */}
                <div class="itemiconheader">
                  {/* <div class="icon">
                    <a href="">
                      <i className="fa fa-search"></i>
                    </a>
                  </div> */}
                  <a href="vechungtoi" class="txtheader">
                    <span class="text-bg">Về Chúng Tôi</span>
                  </a>
                </div>
                <div class="itemiconheader">
                  {/* <div class="icon">
                    <a href="">
                      <i className="fa fa-search"></i>
                    </a>
                  </div> */}
                  <a href="lienhe" class="txtheader">
                    <span class="text-bg">Liên Hệ</span>
                  </a>
                </div>
                <a style={{textDecoration:"none"}} href="product">
                  <div className="search-container">
                    <i className="fa fa-search"></i>
                    Bạn cần tìm gì?
                  </div>
                </a>

                <div class="itemiconheader">
                  <a href="giohang">
                    <div className="icon">
                      <img src={cart}></img>
                      {numCart != 0 && (
                        <div class="quantitycartheader" id="totalcartheader">
                          {numCart}
                        </div>
                      )}
                    </div>
                  </a>
                </div>
                {authen}
              </div>
            </div>
          </div>
        </nav>
      </div>
    </div>
  );
}

export default Header;
