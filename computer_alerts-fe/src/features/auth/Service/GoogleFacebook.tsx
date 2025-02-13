import { useEffect, useState } from "react";
import { DecodeToken } from "assets/DecodeToken";

const GoogleFacebook: React.FC = () => {
  const [auth0UserId, setAuth0UserId] = useState<string | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      console.log("Access Token Found:", token);
      const decodedToken = DecodeToken(token);
      if (decodedToken) {
        const userId = decodedToken.sub;
        console.log("Decoded Token:", decodedToken);
        setAuth0UserId(userId);

        // Check if the user signed in via Google
        if (userId.startsWith("google-")) {
          const goodAuthId = userId.replace(/\|/g, '%7C');
          console.log("Making fetch request for Google user:", goodAuthId);

          // Call your backend endpoint for Google Sign-In users
          fetch(`https://localhost:8080/api/create/${goodAuthId}/assign-role/Google`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}` // Use the access token for authentication
            },
            body: JSON.stringify({
              auth0UserId: userId
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
    } else {
      console.log("No access token found in localStorage");
    }
  }, []);

  return (
    <>
      {auth0UserId ? (
        <div>User ID: {auth0UserId}</div>
      ) : (
        <div>Loading...</div>
      )}
    </>
  );
};

export default GoogleFacebook;
