import { AxiosResponse } from "axios";
import axiosInstance from "../models/articlesAxiosInstance";

export const editArticleImage = async (
  articleId: string,
  imageFile : File
): Promise<AxiosResponse<void>> => {
    const formData = new FormData();
    formData.append('image', imageFile);
  try {
    return await axiosInstance.put<void>(`/articles/${articleId}/image`, formData, {

        headers: {
          'Content-Type': 'multipart/form-data',
        },
    });
  } catch (err) {
    console.error("error editing article", err);
    throw err;
  }
};
