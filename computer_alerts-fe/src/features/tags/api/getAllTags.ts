import { tagRequestModel } from "features/articles/models/tagRequestModel";
import axiosInstance from "./addTagAxiosInstance.ts/addTagAxiosInstance";




export const getAllTags = async (): Promise<tagRequestModel[]> => {
  try {
    const response =
      await axiosInstance.get<tagRequestModel[]>(`/tags`);
    return response.data;
  } catch (err) {
    console.error("error fetching categories", err);
    throw err;
  }
};
