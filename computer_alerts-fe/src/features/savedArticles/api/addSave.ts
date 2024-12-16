import { SaveModel } from "../model/SaveModel";
import axiosInstance from "../model/savesAxiosInstance";

export async function addSave(save: Partial<SaveModel>): Promise<void> {
    try {
        await axiosInstance.post<void>('', save);
    } catch (err) {
        console.error("Error saving article", err);
        throw err;
    }
}