import axiosInstance from "../model/savesAxiosInstance";

export async function deleteSave(saveId: string): Promise<void> {
    try {
        await axiosInstance.delete<void>(`/${saveId}`);
    } catch (err) {
        console.error("Error deleting save", err);
        throw err;
    }
}