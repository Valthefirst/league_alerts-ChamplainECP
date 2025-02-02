import axiosInstance from "../model/savesAxiosInstance";

// export async function deleteSave(saveId: string): Promise<void> {
//     try {
//         await axiosInstance.delete<void>(`/${saveId}`);
//     } catch (err) {
//         console.error("Error deleting save", err);
//         throw err;
//     }
// }

export const deleteSave = async (saveId: string): Promise<void> => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    const response = await axiosInstance.delete<void>(`/${saveId}`, {
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
      console.error("Error in deleteSave API call:", error);
    }
    throw error;
  }
};
