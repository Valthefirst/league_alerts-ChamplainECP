interface DecodedToken {
  sub: string;
  permissions?: string[];
  [key: string]: any; // Allow other properties
}

export const DecodeToken = (token: string): DecodedToken | null => {
  try {
    const [payload] = token.split(".");

    if (!payload) throw new Error("Invalid token structure");

    const decodedPayload: DecodedToken = JSON.parse(
      atob(payload.replace(/-/g, "+").replace(/_/g, "/")),
    );

    return decodedPayload;
  } catch (error) {
    console.error("Failed to decode token:", error);
    return null;
  }
};
