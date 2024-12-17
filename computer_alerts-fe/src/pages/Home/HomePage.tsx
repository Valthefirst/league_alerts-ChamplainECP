import TrendingArticles from "../../features/articles/components/TrendingArticles/TrendingArticles";
import FetchAllArticlesBySport from "./Components/GetBySport";

export default function HomePage(): JSX.Element {
  return (
    <>
    <div className="container">
      <div className="row">
      <TrendingArticles />
      </div>

      <hr />
      <div className="row">

      <h3>NBA</h3>
      <FetchAllArticlesBySport prop="NBA"/>
      <hr />
      <h3>NHL</h3>
      <FetchAllArticlesBySport prop="NHL" />
      <hr />
      <h3>NFL</h3>
      <FetchAllArticlesBySport prop="NFL" />
      <hr />
      <h3>UFC</h3>
      <FetchAllArticlesBySport prop="UFC" />
      <hr />
      <h3>MLB</h3>
      <FetchAllArticlesBySport prop="MLB" />


      </div>
      
    </div>
      

  
    </>

    //Build Something that showcases the articles for each sport. 
  );
}
