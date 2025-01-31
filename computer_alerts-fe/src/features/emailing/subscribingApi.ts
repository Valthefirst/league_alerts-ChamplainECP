import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/v1/subscriptions";

export const subscribeToNotifications = async (email: string, category: string): Promise<string> => {
    try {
      const response = await axios.post(`${API_BASE_URL}/subscribe`, null, {
        params: { email, category },
      });
      return response.data;
    } catch (error: unknown) {
      if (axios.isAxiosError(error)) {
        console.error("Error subscribing to notifications:", error.response?.data || error.message);
        throw new Error(error.response?.data || "An error occurred while subscribing.");
      } else {
        throw new Error("An unknown error occurred.");
      }
    }
  };
  
export const unsubscribeFromNotifications = async (email: string, category: string): Promise<string> => {
    try {
      const response = await axios.delete(`${API_BASE_URL}/unsubscribe`, {
        params: { email, category },
      });
      return response.data;
    } catch (error: unknown) {
      if (axios.isAxiosError(error)) {
        console.error("Error unsubscribing from notifications:", error.response?.data || error.message);
        throw new Error(error.response?.data || "An error occurred while unsubscribing.");
      } else {
        throw new Error("An unknown error occurred.");
      }
    }
};