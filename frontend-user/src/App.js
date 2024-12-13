import { ToastContainer } from 'react-toastify';
import { Routes, Route, BrowserRouter as Router, Navigate } from 'react-router-dom';
import { publicRoutes } from './router/index';
import DefaultLayout from './layout/defaultLayout/defaultLayout';
import LoginPage from './page/login';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          {publicRoutes.map((route, index) => {
            const Layout = route.layout || DefaultLayout;
            const Page = route.component;
            const Authen = route.authen;
            
            if (Authen) {
              const token = localStorage.getItem('token');
              // Kiểm tra nếu không có token, chuyển hướng về LoginPage
              if (!token) {
                return (
                  <Route
                    key={index}
                    path={route.path}
                    element={<Navigate to="/login" replace />}
                  />
                );
              }
            }

            return (
              <Route
                key={index}
                path={route.path}
                element={
                  <Layout>
                    <Page />
                  </Layout>
                }
              />
            );
          })}
          {/* Thêm tuyến đường mặc định */}
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </div>
      <ToastContainer />
    </Router>
  );
}

export default App;
