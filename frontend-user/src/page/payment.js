import { useState, useEffect } from 'react'
import ReactPaginate from 'react-paginate';
import { getMethod ,deleteMethod, uploadMultipleFile, postMethodPayload, postMethod} from '../services/request';
import { formatMoney} from '../services/money';
import Select from 'react-select';
import {toast } from 'react-toastify';

function Payment(){
    useEffect(()=>{
        const checkPayment= async() =>{
            var payload = JSON.parse(localStorage.getItem("order"));
            var uls = new URL(document.URL)
            var vnpOrderInfo = uls.searchParams.get("vnp_OrderInfo");
            const currentUrl = window.location.href;
            const parsedUrl = new URL(currentUrl);
            const queryStringWithoutQuestionMark = parsedUrl.search.substring(1);
            payload.vnpOrderInfo = vnpOrderInfo
            payload.vnpayUrl = queryStringWithoutQuestionMark

            var res = await postMethodPayload('/api/v1/hoa-don/kiem-tra-thanh-toan', payload)
            if (res.status < 300) {
                document.getElementById("thanhcong").style.display = 'block'
                document.getElementById("thatbai").style.display = 'none'
            } else {
                document.getElementById("thanhcong").style.display = 'none'
                document.getElementById("thatbai").style.display = 'block'
                if(res.status == 417){
                    var result = await res.json();
                    document.getElementById("errormess").innerHTML = result.defaultMessage
                }
                else{
                    document.getElementById("errormess").innerHTML = result.defaultMessage
                }
            }
        };
        checkPayment();
    }, []);
    return(
        <div class="maincontentweb">
    <div class="containercustom container">
         <div className='container-fluid'>
        <div className='container-web'>
            <br/><br/>
            <div>
                <div id="thanhcong">
                    <h3 className='headthanhcong'>Đặt hàng thành công</h3>
                    <p className='notithanhcong'>Cảm ơn bạn đã tin tưởng dịch vụ của chúng tôi.</p>
                    <p className='notithanhcong'>Hãy kiểm tra thông tin lịch đặt của bạn trong mục tài khoản</p>
                    <br/><br/>
                    <a href="taikhoan" class="btn btn-danger">Xem lịch sử lịch đặt</a>
                </div>

                <div id="thatbai">
                    <h3 sclassName='notithanhcong'>Thông báo</h3>
                    <p className='notithanhcong' id="errormess">Bạn chưa hoàn thành thanh toán.</p>
                    <br/><br/>
                    <p>Quay về <a href="/">trang chủ</a></p>
                </div>
            </div>
        </div>
     </div>
     </div>
     </div>
        
    );
}

export default Payment;
