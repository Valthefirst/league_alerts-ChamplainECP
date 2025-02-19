import { useEffect, useState } from "react";
import { DecodeToken } from "assets/DecodeToken";

const GoogleFacebook: React.FC = () => {
  const [auth0UserId, setAuth0UserId] = useState<string | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      const decodedToken = DecodeToken(token);
      if (decodedToken) {
        const userId = decodedToken.sub;
        setAuth0UserId(userId);

        // Check if the user signed in via Google
        if (userId.startsWith("google-")) {
          const goodAuthId = userId.replace(/\|/g, "%7C");

          // Call your backend endpoint for Google Sign-In users
          fetch(
            `https://dolphin-app-sxvxi.ondigitalocean.app/api/create/${goodAuthId}/assign-role/Google`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
              },
              body: JSON.stringify({
                auth0UserId: userId,
              }),
            },
          )
            .then((response) => {
              if (!response.ok) {
                throw new Error("Failed to create user in the database");
              }
              return response.json();
            })
            .catch((error) => {
              console.error("Error:", error);
            });
        }
      }
    }
  }, []);

  return (
    <>
      {auth0UserId ? <div>User ID: {auth0UserId}</div> : <div>Loading...</div>}
    </>
  );
};

export default GoogleFacebook;
