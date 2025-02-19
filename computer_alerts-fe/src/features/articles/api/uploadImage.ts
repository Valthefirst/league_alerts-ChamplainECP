import { AxiosResponse } from "axios";
import axiosInstance from "../models/articlesAxiosInstance";

export const uploadImage = async (
  imageFile: File,
): Promise<AxiosResponse<string>> => {
  const formData = new FormData();
  formData.append("file", imageFile);

  try {
    const accessToken = localStorage.getItem("accessToken");

    const response = await axiosInstance.post<string>(
      `/articles/uploadImage`,
      formData,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      },
    );

    return response;
  } catch (err) {
    console.error("Error editing article image", err);
    throw err;
  }
};
