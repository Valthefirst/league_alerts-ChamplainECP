import { CommentModel } from "../model/CommentModel";
import axiosInstance from "../model/commentsAxiosInstance";

export async function addComment(
  comment: Partial<CommentModel>,
): Promise<void> {
  try {
    const response = await axiosInstance.post<void>(
      "",
      comment, 
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`, 
          "Content-Type": "application/json", 
        },
      }
    );
    return response.data;
  } 
  catch (error: any) {
      
      if (error.response && error.response.status === 401) {
        
        
        window.location.href = "/unauthorized"; 
      } else {
        console.error("Error in unlikeArticle API call:", error);
      }
      throw error; 
    }
}

// export const addComment = async (): Promise<void> => {
//   try {
//     const response = await axiosInstance.post<void>('');
//     return response.data;
//   } catch (err) {
//     console.error("Error posting comments", err);
//     throw err;
//   }
// };
