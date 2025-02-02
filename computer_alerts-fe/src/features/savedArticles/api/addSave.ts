import { SaveModel } from "../model/SaveModel";
import axiosInstance from "../model/savesAxiosInstance";

// export async function addSave(save: Partial<SaveModel>): Promise<SaveModel> {
//     try {
//         const response = await axiosInstance.post<SaveModel>('', save);
//         return response.data;
//     } catch (err) {
//         console.error("Error saving article", err);
//         throw err;
//     }
// }

export const addSave = async (save: Partial<SaveModel>): Promise<SaveModel> => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    const response = await axiosInstance.post<SaveModel>("", save, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    return response.data;
  } catch (error: any) {
    if (error.response && error.response.status === 403) {
      // Redirect to unauthorized page if status is 401
      window.location.href = "/unauthorized";
    } else {
      console.error("Error in addSave API call:", error);
    }
    throw error;
  }
};
