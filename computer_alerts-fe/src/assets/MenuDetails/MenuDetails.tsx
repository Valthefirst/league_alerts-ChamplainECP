import {useEffect, useState} from "react"
import { AppRoutePaths } from "shared/models/path.routes";
import styles from "./MenuDetails.module.css"
import { DecodeToken } from "assets/DecodeToken";
import ReaderRequestDTO from "features/readers/models/ReaderRequestDTO";


const MenuDetails: React.FC = () => {
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const [auth0UserId, setAuth0UserId] = useState<string | null>(null);
    const [email, setEmail] = useState("");

   useEffect(() => {
      const token = localStorage.getItem("accessToken");
      if (token) {
        const decodedToken = DecodeToken(token);
        if (decodedToken) {
          setAuth0UserId(decodedToken.sub); 
        }
      }
    }, []);

    let URLDeploy = "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/";

    let URLDepTest = "http://localhost:8080/api/v1/";
  
    
    useEffect(() => {
      const fetchUserData = async () => {
        if (!auth0UserId) return; 
  
        let goodAuth0User = auth0UserId.replace(/\|/g, "%7C");
  
  
        try {
          const response = await fetch(`${URLDepTest}readers/${goodAuth0User}`); 
          if (!response.ok) {
            throw new Error("Failed to fetch user data");
          }
          const userData: ReaderRequestDTO = await response.json();
          setEmail(userData.emailAddress);
        
        } catch (error) {
          setErrorMessage("We are having issues getting your account details.");
          console.error(error);
        }
      };
  
      fetchUserData();
    }, [auth0UserId]); 

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
          <p>This is your Email: {email}</p>  
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