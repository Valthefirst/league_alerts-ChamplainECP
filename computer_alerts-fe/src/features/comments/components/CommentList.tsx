import { useEffect, useState } from "react";
import { CommentModel } from "../model/CommentModel";
import "./CommentList.css";

interface CommentListProps {
  articleId: { articleId: string };
}

const CommentList: React.FC<CommentListProps> = ({
  articleId: { articleId },
}) => {
  const [comments, setComments] = useState<CommentModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    let eventSource: EventSource;
    const seenCommentIds = new Set<string>();

    const connectToSSE = () => {
      eventSource = new EventSource(
        "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/interactions/comments",
        // "http://localhost:8080/api/v1/interactions/comments",
      );

      eventSource.onopen = () => {
        setIsLoading(false);
      };

      eventSource.onmessage = (event) => {
        try {
          const newComment: CommentModel = JSON.parse(event.data);
          if (
            newComment.articleId === articleId &&
            !seenCommentIds.has(newComment.commentId)
          ) {
            seenCommentIds.add(newComment.commentId);
            setComments((prevComments) => [...prevComments, newComment]);
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
  }, [articleId]);

  const filteredComments = comments
    .filter((comment) => comment.articleId === articleId)
    .sort(
      (a, b) =>
        new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime(),
    );

  if (isLoading) {
    return <div>Loading comments...</div>;
  }

  return (
    <div className="comments-container">
      <div className="comments-box">
        {filteredComments.length > 0 ? (
          filteredComments.map((comment) => (
            <div key={comment.commentId} className="comment-item">
              <p>
                {/* <strong>Reader Name:</strong>{" "} */}
                <span className="reader-id">John</span>
              </p>
              <p>{comment.content}</p>
              {/* <p><strong>Timestamp:</strong> {new Date(comment.timestamp).toLocaleString()}</p> */}
            </div>
          ))
        ) : (
          <p className="no-comments">
            No comments yet. Be the first to comment!
          </p>
        )}
      </div>
    </div>
  );
};

export default CommentList;
