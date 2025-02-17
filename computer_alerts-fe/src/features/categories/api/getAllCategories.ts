import { categoryRequestModel } from "features/articles/models/categoriesResponseModel";
import axiosInstance from "../api/AxiosInstance/categoriesAxisoInstance";


export const fetchAllsArticles = async (): Promise<categoryRequestModel[]> => {
  try {
    const response =
      await axiosInstance.get<categoryRequestModel[]>(`/categories`);
    return response.data;
  } catch (err) {
    console.error("error fetching categories", err);
    throw err;
  }
};
