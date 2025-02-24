import { tagRequestModel } from "features/articles/models/tagRequestModel";
import axiosInstance from "./addTagAxiosInstance.ts/addTagAxiosInstance";

export const searchTags = async (
  tagName: string,
): Promise<tagRequestModel[]> => {
  try {
    const response = await axiosInstance.get<tagRequestModel[]>(
      `/tags/search`,
      {
        params: { tagName },
      },
    );
    return response.data;
  } catch (err) {
    console.error("Error searching for tags", err);
    throw err;
  }
};
