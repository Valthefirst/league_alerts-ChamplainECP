import { useEffect,useState } from "react";
import { DecodeToken } from "assets/DecodeToken";

const GoogleFacebook: React.FC = () => {
const [auth0UserId, setAuth0UserId] = useState<string | null>(null);
  
    // let URLDepTest = "http://localhost:8080/api/v1/";
  
    useEffect(() => {
        const token = localStorage.getItem("accessToken");
        if (token) {
          const decodedToken = DecodeToken(token);
          if (decodedToken) {
            const auth0UserId = decodedToken.sub;
            setAuth0UserId(auth0UserId);
      
            // Check if the user signed in via Google
            if (auth0UserId.startsWith("google-")) {
                const goodAuthId = auth0UserId.replace(/\|/g, '%7C');
              // Call your backend endpoint for Google Sign-In users
              fetch(`https://localhost:8080/api/create/${goodAuthId}/assign-role/Google`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${token}` // Use the access token for authentication
                },
                body: JSON.stringify({
                  auth0UserId: auth0UserId
                })
              })
              .then(response => {
                if (!response.ok) {
                  throw new Error('Failed to create user in the database');
                }
                return response.json();
              })
              .then(data => {
                console.log('User created/fetched:', data);
              })
              .catch(error => {
                console.error('Error:', error);
              });
            }
          }
        }
      }, []);


    return(
        <>
        </>
    );
}
export default GoogleFacebook;