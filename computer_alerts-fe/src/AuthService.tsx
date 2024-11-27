import { createAuth0Client, Auth0Client } from "@auth0/auth0-spa-js";
import axios from "axios";
import UserRequestDTO from "./features/readers/models/UserRequestDTO";
export class AuthService {


  URL = "http://localhost:8080/api/"; 

  private auth0Client: Auth0Client | null = null;

  constructor() {
    this.initializeAuth0();
  }

  private async initializeAuth0() {
    console.log("Initializing Auth0...");
    this.auth0Client = await createAuth0Client({
      domain: "dev-im24qkb6l7t2yhha.ca.auth0.com",
      clientId: "COuKmAH95MAHPN2irCzsuOearf2gdsOH",
      authorizationParams: {
        redirect_uri: "http://localhost:3000",
        audience: "http://localhost:8080/api/userInfo",
        scope: "openid profile email roles",
      },
    });
    console.log("Auth0 initialized.");
  }

  private async ensureAuth0Client(): Promise<void> {
    if (!this.auth0Client) {
      await this.initializeAuth0();
    }
  }

  public async isAuthenticated(): Promise<boolean> {
    await this.ensureAuth0Client();
    const authenticated = await this.auth0Client!.isAuthenticated();
    console.log("Is authenticated:", authenticated);
    return authenticated;
  }

  public async login() {
    await this.ensureAuth0Client();
    await this.auth0Client!.loginWithPopup();
    const isAuthenticated = await this.isAuthenticated();
    if (isAuthenticated) {
      console.log("Logged in successfully.");

      const token = await this.getToken();
      console.log("Access token:", token);
    } else {
      console.log("Login failed.");
    }
  }

  public async logout(): Promise<void> {
    await this.ensureAuth0Client();
    console.log("Logging out...");
    await this.auth0Client!.logout({
      logoutParams: {
        returnTo: window.location.origin,
      },
    });
  }

  // Get the access token for making API requests
  public async getToken(): Promise<string> {
    await this.ensureAuth0Client();
    const token = await this.auth0Client!.getTokenSilently();
    console.log("Retrieved token:", token);
    return token;
  }

  // Get user roles from the ID token claims
  public async getUserRoles(): Promise<string[]> {
    await this.ensureAuth0Client();
    const claims = await this.auth0Client!.getIdTokenClaims();
    console.log("User roles:", claims?.["roles"]);
    return claims?.["roles"] || [];
  }


  async createUser(userRequest: UserRequestDTO): Promise<any> {
    try {
      const response = await axios.post( this.URL + 'create', userRequest, {
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true,
      });
      return response.data;
    } catch (error: any) {
      console.error('Full error:', error);
      console.error('Response:', error.response);
      console.error('Request:', error.request);
      throw new Error(error.response?.data || "Failed to create user");
    }
}
}

const authTokenService = new AuthService();
export default authTokenService;
