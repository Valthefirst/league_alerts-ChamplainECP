import { DecodeToken } from "./DecodeToken";

interface DecodedPermissions {
  permissions?: string[];
}

export const decodeTokenPermissions = (token: string): string[] => {
  try {
    const decoded: DecodedPermissions | null = DecodeToken(token);
    console.log("Decoded Token:", decoded); // Debugging log
    console.log("Permissions:", decoded?.permissions || []); // Log permissions
    return decoded?.permissions || [];
  } catch (error) {
    console.error("Error decoding token:", error);
    return [];
  }
};
