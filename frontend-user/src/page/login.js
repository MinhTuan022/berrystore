import React, { useState, useEffect } from 'react';
import {toast } from 'react-toastify';
import {postMethodPayload, postMethod} from '../services/request'
import Swal from 'sweetalert2'


function login(){

    async function getKhachHang(token) {
        const res = await fetch('http://localhost:8080/api/khach-hang/dang-dang-nhap', {
          method: 'POST',
          headers: new Headers({
              'Authorization': 'Bearer ' + token
          })
        });
        var result = await res.json();
        localStorage.setItem("user", JSON.stringify(result));
      }

    async function handleLogin(event) {
        event.preventDefault();
        const payload = {
            email: event.target.elements.email.value,
            password: event.target.elements.password.value,
        };
        const res = await postMethodPayload('/api/v1/auth/public/login', payload);
        
        var result = await res.json()
        console.log(result);
        if (res.status == 417) {
          toast.warning(result.defaultMessage);
        }
        if(res.status < 300){
          if(result.role != "ROLE_CUSTOMER"){
            toast.warning("Chỉ hỗ trợ đăng nhập tài khoản khách hàng");
            return;
          }
          else{
            toast.success('Đăng nhập thành công!');
            await new Promise(resolve => setTimeout(resolve, 100));
            localStorage.setItem("token", result.token);
            await getKhachHang(result.token);
            window.location.href = '/'
          }
        }
      };

    return(
        <div class="contentweb">
        <div class="container">
            <div class="dangnhapform">
                <div class="divctlogin">
                    <p class="labeldangnhap">Đăng Nhập</p>
                    <form onSubmit={handleLogin} autocomplete="off">
                        <label class="lbform">Tên tài khoản</label>
                        <input required name='email' type='email' class="inputlogin"/>
                        <label class="lbform">Mật khẩu</label>
                        <input required name='password' type="password" id="password" class="inputlogin"/>
                        <button class="btndangnhap">ĐĂNG NHẬP</button>
                        <button type="button" onClick={()=>window.location.href='regis'} class="btndangky">ĐĂNG KÝ</button>
                    </form><br/><br/><br/>
                    <hr/>
                    <p class="quenmk"><a href="forgot">
                        Quên mật khẩu ?
                    </a></p>
                </div>
            </div>
        </div>
    </div>
    );
}
export default login;