import React, { useState, useEffect } from 'react';
import {toast } from 'react-toastify';
import {postMethodPayload, postMethod} from '../services/request'
import Swal from 'sweetalert2'


function DangKy(){

    async function handleRegis(event) {
        event.preventDefault();
        if(event.target.elements.matkhau.value != event.target.elements.repassword.value){
            toast.error("Mật khẩu không trùng khớp")
            return;
        }
        const payload = {
            hoVaTen: event.target.elements.hoten.value,
            ngaySinh: event.target.elements.ngaysinh.value,
            gioiTinh: event.target.elements.gioitinh.value,
            soDienThoai: event.target.elements.sdt.value,
            email: event.target.elements.email.value,
            matKhau: event.target.elements.matkhau.value,
        };
        const res = await postMethodPayload('/api/v1/auth/public/register', payload);
        var result = await res.json()
        if (res.status == 417) {
          toast.warning(result.defaultMessage);
        }
        if(res.status < 300){
            Swal.fire({
                title: "Thông báo",
                text: "Đăng ký tài khoản thành công!",
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
                    <p class="labeldangnhap">Đăng Ký</p>
                    <form onSubmit={handleRegis} autocomplete="off">
                        <div className='row'>
                            <div className='col-sm-6'>
                                <label class="lbform">Email</label>
                                <input required name='email' type='email' class="inputlogin"/>
                                <label class="lbform">Họ tên</label>
                                <input required name='hoten' class="inputlogin"/>
                                <label class="lbform">Ngày sinh</label>
                                <input required name='ngaysinh' type='date' class="inputlogin"/>
                                <label class="lbform">Số điện thoại</label>
                                <input required name='sdt' class="inputlogin"/>
                            </div>
                            <div className='col-sm-6'>
                                <label class="lbform">Mật khẩu</label>
                                <input required name='matkhau' type="password" id="password" class="inputlogin"/>
                                <label class="lbform">Nhập lại mật khẩu</label>
                                <input required name='repassword' type="password" id="password" class="inputlogin"/>
                                <label class="lbform">Giới tính</label>
                                <div className='d-flex' style={{marginTop:'15px'}}>
                                <label class="radiocustom"> Nam
                                    <input type="radio" name="gioitinh" value={true} defaultChecked={true}/>
                                <span class="checkmark"></span></label>
                                <label class="radiocustom" style={{marginLeft:'20px'}}> Nữ
                                    <input type="radio" name="gioitinh" value={false}/>
                                <span class="checkmark"></span></label>
                                </div>
                            </div>
                        </div>
                        <button class="btndangnhap">ĐĂNG KÝ</button>
                        <button type="button" onClick={()=>window.location.href='login'} class="btndangky">ĐĂNG NHẬP</button>
                    </form><br/><br/><br/>
                </div>
            </div>
        </div>
    </div>
    );
}
export default DangKy;