import axiosInstance from "../model/reportAxiosInstance";
import { ReportModel } from "../model/ReportModel";

export const createReport = async (): Promise<ReportModel> => {
    try {
        const accessToken = localStorage.getItem("accessToken");
        const response = await axiosInstance.get<ReportModel>('', {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        } );
        return response.data;
    } catch (error: any) {
        if (error.response && error.response.status === 403) {
          // Redirect to unauthorized page if status is 401
          window.location.href = "/unauthorized";
        } else {
          console.error("Error fetching report:", error);
        }
        throw error;
      }
}