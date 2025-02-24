interface DecodedToken {
  sub: string;
  permissions?: string[];
  [key: string]: any; // Allow other properties
}

export const DecodeToken = (token: string): DecodedToken | null => {
  try {
    const parts = token.split(".");

    if (parts.length !== 3) throw new Error("Invalid token structure");

    const payload = parts[1]; // Extract payload part
    const decodedPayload = JSON.parse(
      decodeURIComponent(
        atob(payload.replace(/-/g, "+").replace(/_/g, "/"))
          .split("")
          .map((c) => `%${("00" + c.charCodeAt(0).toString(16)).slice(-2)}`)
          .join(""),
      ),
    );

    return decodedPayload;
  } catch (error) {
    console.error("Failed to decode token:", error);
    return null;
  }
};
