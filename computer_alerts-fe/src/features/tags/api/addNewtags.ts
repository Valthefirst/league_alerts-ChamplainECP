import { tagRequestModel } from "features/articles/models/tagRequestModel";
import axiosInstance from "./addTagAxiosInstance.ts/addTagAxiosInstance";

export const addNewTag = async (newTag: string): Promise<tagRequestModel> => {
  try {
    const response = await axiosInstance.post<tagRequestModel>("/tags", {
      tagName: newTag,
    });
    return response.data; // response.data should be a single object, not an array
  } catch (err) {
    console.error("Error creating category:", err);
    throw err;
  }
};
