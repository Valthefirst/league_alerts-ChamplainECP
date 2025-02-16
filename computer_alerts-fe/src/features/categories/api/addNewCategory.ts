import { categoryRequestModel } from "features/articles/models/categoriesResponseModel";
import axiosInstance from "./AxiosInstance/categoriesAxisoInstance";

export const addNewCategories = async (
  newCategory: string,
): Promise<categoryRequestModel> => {
  try {
    const response = await axiosInstance.post<categoryRequestModel>(
      "/categories",
      {
        categoryName: newCategory,
      },
    );
    return response.data; // response.data should be a single object, not an array
  } catch (err) {
    console.error("Error creating category:", err);
    throw err;
  }
};
