import { DecodeToken } from "./DecodeToken";

interface DecodedPermissions {
  permissions?: string[];
}

export const decodeTokenPermissions = (token: string): string[] => {
  try {
    const decoded: DecodedPermissions | null = DecodeToken(token);
    return decoded?.permissions || [];
  } catch (error) {
    console.error("Error decoding token:", error);
    return [];
  }
};
