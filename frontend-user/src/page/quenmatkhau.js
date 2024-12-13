import React, { useState, useEffect } from 'react';
import {toast } from 'react-toastify';
import {postMethodPayload, postMethod} from '../services/request'
import Swal from 'sweetalert2'


function QuenMatKhau(){

    async function handleQuenPass(event) {
        event.preventDefault();
        const res = await postMethodPayload('/api/v1/auth/public/quen-mat-khau?email='+event.target.elements.email.value);
        var result = await res.json()
        if (res.status == 417) {
          toast.warning(result.defaultMessage);
        }
        if(res.status < 300){
            Swal.fire({
                title: "Thông báo",
                text: "Mật khẩu đã được gửi về email của bạn!",
                preConfirm: () => {
                    window.location.href = 'login'
                }
            });
        }
      };

    return(
        <div class="contentweb">
        <div class="container">
            <div class="dangnhapform">
                <div class="divctlogin">
                    <p class="labeldangnhap">Quên mật khẩu</p>
                    <form onSubmit={handleQuenPass} autocomplete="off">
                        <label class="lbform">Email</label>
                        <input required name='email' placeholder='Nhập email của bạn' type='email' class="inputlogin"/>
                        <button class="btndangnhap">XÁC NHẬN</button>
                        <button type="button" onClick={()=>window.location.href='login'} class="btndangky">ĐĂNG NHẬP</button>
                    </form><br/><br/><br/>
                </div>
            </div>
        </div>
    </div>
    );
}
export default QuenMatKhau;