import React, { useState } from "react";
// import './App.css'; // Thêm CSS để tùy chỉnh giao diện popup



const TermsPopup = (props) => {
  const [isOpen, setIsOpen] = useState(false);

  const openPopup = () => {
    setIsOpen(true);
  };

  const closePopup = () => {
    setIsOpen(false);
  };

  const handleAgreeTerm = () => {
    props.onAgreeTerm(true);
    closePopup();
  };
  return (
    <div>
      <div>
        Chấp nhận
        <button
          onClick={openPopup}
          className="open-popup-button"
        >
          Điều khoản và Chính sách
        </button>
        của chúng tôi
      </div>

      {isOpen && (
        <div className="popup-overlay">
          <div className="popup-content">
            <h2 className="tieude">Điều khoản và Chính sách</h2>

            <div className="content-view">
              <p>
                Cảm ơn bạn đã sử dụng dịch vụ của BerryShoes. Khi bạn sử dụng
                website của chúng tôi để mua sắm, bạn đồng ý tuân thủ các điều
                khoản và điều kiện sau đây:
              </p>

              <h3>1. Thông tin sản phẩm</h3>
              <p>
                Chúng tôi cam kết cung cấp thông tin sản phẩm chính xác nhất có
                thể. Tuy nhiên, màu sắc và hình ảnh sản phẩm trên website có thể
                hơi khác biệt so với thực tế do ánh sáng hoặc thiết bị hiển thị.
              </p>
              <p>
                Kích thước sản phẩm được mô tả dựa trên bảng size chuẩn. Vui
                lòng kiểm tra kỹ thông tin trước khi đặt hàng.
              </p>

              <h3>2. Chính sách thanh toán</h3>
              <p>
                Tất cả các đơn hàng đều cần được thanh toán qua các phương thức
                thanh toán được cung cấp trên website.
              </p>
              <p>
                Giá sản phẩm tại thời điểm đặt hàng sẽ là giá cuối cùng, không
                áp dụng bất kỳ thay đổi giá nào sau đó.
              </p>

              <h3>3. Chính sách vận chuyển</h3>
              <p>
                Thời gian giao hàng dao động từ 3-7 ngày làm việc tùy khu vực.
              </p>
              <p>
                Khách hàng có trách nhiệm cung cấp địa chỉ nhận hàng chính xác.
                BerryShoes không chịu trách nhiệm nếu đơn hàng không đến được do
                thông tin không chính xác.
              </p>

              <h3>4. Chính sách đổi trả</h3>
              <p>BerryShoes hiện không hỗ trợ trả hàng.</p>

              <h3>5. Quyền riêng tư</h3>
              <p>
                Chúng tôi cam kết bảo vệ thông tin cá nhân của bạn. Mọi dữ liệu
                được thu thập sẽ chỉ được sử dụng để xử lý đơn hàng hoặc cải
                thiện dịch vụ.
              </p>
              <p>Xem chi tiết tại Chính sách bảo mật.</p>

              <h3>6. Chấp nhận điều khoản</h3>
              <p>
                Khi đặt hàng trên website của chúng tôi, bạn đã đồng ý với tất
                cả các điều khoản và điều kiện nêu trên.
              </p>
              <p>
                BerryShoes có quyền cập nhật và thay đổi nội dung điều khoản mà
                không cần thông báo trước. Vui lòng kiểm tra lại điều khoản
                trước khi đặt hàng.
              </p>

              <h3>Liên hệ hỗ trợ:</h3>
              <p>Hotline: 0869839386</p>
              <p>Email: studio.berryfoto@gmail.com</p>
              <p>Địa chỉ: Số 386, TVB, Nam Từ Liêm, Hà nội</p>
            </div>
            <div className="btn-view">
              <button onClick={closePopup} className="close-popup-button">
                Đóng
              </button>
              <button onClick={handleAgreeTerm} className="agree-popup-button">
                Chấp nhận
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default TermsPopup;
