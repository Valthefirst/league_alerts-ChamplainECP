import axios from 'axios';

const patchArticleTrend = async (articleId: string) => {
    try {
        await axios.patch(`/articles/${articleId}`);
        console.log("Article trend updated successfully.");
    } catch (err) {
        console.error("Error updating article trend:", err);
    }
};