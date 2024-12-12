import React, { useEffect } from "react";
import { fetchAllsArticles } from "features/articles/api/getAllArticles";

const AuthHomePage: React.FC = () => {
  // const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  // const [loading, setLoading] = useState<boolean>(true);
  // const [error, setError] = useState<string | null>(null);

  
  // useEffect(() =>{
  //   const fetchArticles = async () => {
  //     try{

  //       const allArticles = await fetchArticles();
  //       // const articlesToReview =   allArticles.fil

  //     }
  //     return 
  //   };
  // })
 

  return (
    

    <div className="container">
      <div className="row">
        <h1>Recently Added Articles</h1>
      </div>
      <div className="row">
        

      </div>
    </div>
  );
};

export default AuthHomePage;