import React from "react"
import { AppRoutePaths } from "shared/models/path.routes";
import styles from "./MenuDetails.module.css"


const MenuDetails: React.FC = () => {

    return (
        <div
          style={{
            position: 'absolute',
            top: '60px', 
            width: '200px',
            left: '30',
            right: '40',
            backgroundColor: 'white',
            border: '1px solid #ccc',
            borderRadius: '4px',
            boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
            
            zIndex: 1000,
          }}
        >
          <p>This is your Email: Jessygjerek2@gmail.com</p>  
          <ul style={{ listStyle: 'none', margin: 0, padding: 0 }}>
            <li>
              <a href={AppRoutePaths.SavedArticles} style={{ textDecoration: 'none', color: 'black' }}>
                Saved Articles
              </a>
            </li>
            
            <li>
              <a href={AppRoutePaths.Authors} className={styles.listItems}>
                See All Authors
              </a>
            </li>
            <li>
              <a href={AppRoutePaths.ModifyAccountDetails} style={{ textDecoration: 'none', color: 'black' }}>
                Modify Account
              </a>
            </li>
            <li>
              <a href={AppRoutePaths.CREATE_ACCOUNT} style={{ textDecoration: 'none', color: 'black' }}>
                Create Account
              </a>
            </li>
          </ul>
        </div>
      );

}
export default MenuDetails;