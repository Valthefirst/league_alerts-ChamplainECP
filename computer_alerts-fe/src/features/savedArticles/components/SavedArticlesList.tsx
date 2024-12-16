import { useState, useEffect } from 'react';
import { fetchArticleByArticleIdWithNoPatch } from '../../articles/api/getSpecificArticle';
import { ArticleRequestModel } from '../../articles/models/ArticleRequestModel';
import { SaveModel } from '../model/SaveModel';
import { Button } from 'react-bootstrap';
import { deleteSave } from '../api/deleteSave';

interface SavedArticlesListProps {
  readerId: string ;
}

const SavedArticlesList: React.FC<SavedArticlesListProps> = ({ readerId }) => {
  const [saves, setSaves] = useState<SaveModel[]>([]);
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [removingArticles, setRemovingArticles] = useState<Set<string>>(new Set());

  // First useEffect for SSE connection
  useEffect(() => {
    let eventSource: EventSource;
    const seenCommentIds = new Set<string>();

    const connectToSSE = () => {
      eventSource = new EventSource(
        `http://localhost:8080/api/v1/interactions/saves/${readerId}`
      );

      eventSource.onopen = () => {
        setIsLoading(false);
      };

      eventSource.onmessage = (event) => {
        try {
          const newSave: SaveModel = JSON.parse(event.data);
          if (
            newSave.readerId === readerId &&
            !seenCommentIds.has(newSave.saveId)
          ) {
            seenCommentIds.add(newSave.saveId);
            setSaves((prevSaves) => [...prevSaves, newSave]);
          }
        } catch (err) {
          console.error("Error parsing SSE data:", err);
        }
      };
    };

    connectToSSE();

    return () => {
      if (eventSource) {
        eventSource.close();
      }
    };
  }, [readerId]);

  // Second useEffect to fetch articles when saves change
  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const articlePromises = saves.map(save => 
          fetchArticleByArticleIdWithNoPatch(save.articleId)
        );
        const fetchedArticles = await Promise.all(articlePromises);
        setArticles(fetchedArticles);
      } catch (err) {
        setError('Failed to fetch articles');
        console.error('Error fetching articles:', err);
      } finally {
        setIsLoading(false);
      }
    };

    if (saves.length > 0) {
      fetchArticles();
    }
  }, [saves]);

  if (isLoading) {
    return <div>Loading Saved Articles...</div>;
  }

  if (error) {
    return <div style={{ color: 'red' }}>{error}</div>;
  }

  const handleUnsave = async (articleId: string, saveId: string) => {
    try {
      setRemovingArticles(prev => new Set(Array.from(prev).concat(articleId)));
      await deleteSave(saveId);
      
      // Wait for animation
      await new Promise(resolve => setTimeout(resolve, 300));
      
      setSaves(prevSaves => prevSaves.filter(save => save.saveId !== saveId));
      setArticles(prevArticles => prevArticles.filter(a => a.articleId !== articleId));
    } catch (error) {
      console.error('Error unsaving article:', error);
      setRemovingArticles(prev => {
        const newSet = new Set(prev);
        newSet.delete(articleId);
        return newSet;
      });
    }
  };

  return (
    <div className="saved-articles-container">
      {articles.length > 0 ? (
        articles.map((article, index) => (
          <div key={article.articleId} className={`saved-article-item ${removingArticles.has(article.articleId) ? 'removing' : ''}`}
          >
            <div className="article-header">
              <h1>{article.title}</h1>
              <i className="bi bi-arrow-90deg-right"></i>
            </div>
            <div className="article-content">
              <h3>Article Preview: </h3>
              <p>
                {article.body.split(' ').length > 20 
                  ? article.body.split(' ').slice(0, 20).join(' ') + '...'
                  : article.body}
              </p>
            </div>
            <Button id="btn-1">Read More</Button>
            <Button 
          onClick={() => {
            const saveToDelete = saves.find(save => save.articleId === article.articleId);
            if (saveToDelete) {
              handleUnsave(article.articleId, saveToDelete.saveId);
            }
          }}
        >
          <i className="bi bi-bookmark"></i> Unsave
        </Button>
            {index < articles.length - 1 && <hr className="separator" />}
          </div>
        ))
      ) : (
        <p>No saved articles found.</p>
      )}
    </div>
  );
};

export default SavedArticlesList;