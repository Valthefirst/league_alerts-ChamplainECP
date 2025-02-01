// utils/decodeToken.ts
export const DecodeToken = (token: string): { sub: string } | null => {
  try {
    // Split the token into its three parts
    const [payload ] = token.split(".");

    // Decode the payload (base64url encoded)
    const decodedPayload = JSON.parse(
      atob(payload.replace(/-/g, "+").replace(/_/g, "/")),
    );

    return decodedPayload;
  } catch (error) {
    console.error("Failed to decode token:", error);
    return null;
  }
};
