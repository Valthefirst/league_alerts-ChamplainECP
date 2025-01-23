import { SaveModel } from "../model/SaveModel";
import axiosInstance from "../model/savesAxiosInstance";

// export const getAllSaves = async (readerId: string): Promise<SaveModel[]> => {
//     try {
//         const response = await axiosInstance.get<SaveModel[]>(`/${readerId}`);
//         return response.data;
//     } catch (err) {
//         console.error("Error fetching saves", err);
//         throw err;
//     }
// }

export const getAllSaves = async (readerId: string): Promise<SaveModel[]> => {
    try {
        const accessToken = localStorage.getItem("accessToken");
        const response = await axiosInstance.get<SaveModel[]>(`/${readerId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    } );
        return response.data;
    } catch (error: any) {
        if (error.response && error.response.status === 403) {
          // Redirect to unauthorized page if status is 401
          window.location.href = "/unauthorized";
        } else {
          console.error("Error in getAllSaves API call:", error);
        }
        throw error;
      }
}