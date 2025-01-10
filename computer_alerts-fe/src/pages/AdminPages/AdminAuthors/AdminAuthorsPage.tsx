import React from "react";
import { Link } from "react-router-dom";

const AdminAuthorsPage: React.FC = () =>{


return(
    <div className="container">
        <div className="row">
            <h1>Hello</h1>
            <Link to="/admin/createAuthor">
              Create Author
            </Link>
        </div>
    </div>
)
}
export default AdminAuthorsPage;