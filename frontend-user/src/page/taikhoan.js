import { useState, useEffect } from 'react'
import star from '../assest/images/icon_star.png';
import banner from '../assest/images/banner.jpg';
import banner1 from '../assest/images/banner1.png';
import banner2 from '../assest/images/banner2.jpg';
import newitem from '../assest/images/newitem.jpg';
import ReactPaginate from 'react-paginate';
import { getMethod ,uploadSingleFile, uploadMultipleFile, postMethodPayload, postMethod} from '../services/request';
import { formatMoney} from '../services/money';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import * as React from 'react';
import DiaChi from './diachi'
import DonHang from './donhang'
import DoiMatKhau from './doimatkhau'
import ThongTinTaiKhoan from './thongtintaikhoan'

function TaiKhoan(){
    const [value, setValue] = React.useState(0);
    useEffect(()=>{
    }, []);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    useEffect(()=>{
    }, []);

    return(
    <div class="maincontentweb">
    <div class="containercustom container">
    <div className='container-fluid'>
        <div className='container-web acctaikhoan'>
            <Box sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', minHeight:500 }} >
                <Tabs
                    orientation="vertical"
                    variant="scrollable"
                    value={value}
                    onChange={handleChange}
                    aria-label="Vertical tabs example"
                    sx={{ borderRight: 1, borderColor: 'divider' }}>

                    <Tab label="Thông tin tài khoản" {...a11yProps(0)} />
                    <Tab label="Đơn hàng" {...a11yProps(1)} />
                    <Tab label="Địa chỉ" {...a11yProps(2)} />
                    <Tab label="Đổi mật khẩu" {...a11yProps(3)} />
                </Tabs>
                <TabPanel value={value} index={0} style={{width:"100%"}}>
                  <ThongTinTaiKhoan/>
                </TabPanel>
                <TabPanel value={value} index={1} style={{width:"100%"}}>
                  <DonHang/>
                </TabPanel>
                <TabPanel value={value} index={2} style={{width:"100%"}}>
                  <DiaChi/>
                </TabPanel>
                <TabPanel value={value} index={3} style={{width:"100%"}}>
                  <DoiMatKhau/>
                </TabPanel>
            </Box>
        </div>
     </div>
     </div>
     </div>
    );
}
function TabPanel(props) {
    const { children, value, index, ...other } = props;
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`vertical-tabpanel-${index}`}
        aria-labelledby={`vertical-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box sx={{ p: 3 }}>
            <Typography>{children}</Typography>
          </Box>
        )}
      </div>
    );
}
  
TabPanel.propTypes = {
children: PropTypes.node,
index: PropTypes.number.isRequired,
value: PropTypes.number.isRequired,
};
  
function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}
export default TaiKhoan;
