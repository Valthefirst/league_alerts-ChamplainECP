import React from "react";
// import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from "chart.js";
import { TopArticleModel } from "../model/TopArticleModel";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

interface TopArticlesChartProps {
    articles: TopArticleModel[];
  }

  const TopArticlesChart: React.FC<TopArticlesChartProps> = ({ articles }) => {

    return null;
  //   const data = {
  //     labels: articles.map((article) => article.articleId),
  //     datasets: [
  //       {
  //         label: "Likes",
  //         data: articles.map((article) => article.likeCount),
  //         backgroundColor: "rgba(255, 99, 132, 0.5)",
  //       },
  //       {
  //         label: "Shares",
  //         data: articles.map((article) => article.shareCount),
  //         backgroundColor: "rgba(54, 162, 235, 0.5)",
  //       },
  //       {
  //         label: "Requests",
  //         data: articles.map((article) => article.requestCount),
  //         backgroundColor: "rgba(255, 206, 86, 0.5)",
  //       },
  //       {
  //         label: "Comments",
  //         data: articles.map((article) => article.commentCount),
  //         backgroundColor: "rgba(75, 192, 192, 0.5)",
  //       },
  //       {
  //         label: "Total Points",
  //         data: articles.map((article) => article.points),
  //         backgroundColor: "rgba(153, 102, 255, 0.5)",
  //       },
  //     ],
  //   };
  
  //   const options = {
  //     responsive: true,
  //     plugins: {
  //       legend: { position: "top" as const },
  //       title: { display: true, text: "Top Articles Interactions" },
  //     },
  //   };
  
  //   return <Bar data={data} options={options} />;
  // };
  
  export default TopArticlesChart;
  