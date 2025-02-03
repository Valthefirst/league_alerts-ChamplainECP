import { TopArticleModel } from "./TopArticleModel";

export interface ReportModel {
    reportId: string;
    timestamp: Date;
    topArticles: TopArticleModel[];
}
