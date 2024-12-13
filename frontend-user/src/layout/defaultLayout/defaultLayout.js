import Headers from "../header/header";
import Footer from "../footer/footer"

function DefaultLayout({children}){
    return (
        <div>
            <Headers/>
            <div className="content-container">
            {children}

            </div>
            <Footer/>
        </div>
    );
}

export default DefaultLayout;