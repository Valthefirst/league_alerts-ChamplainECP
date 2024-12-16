import React, { useState, ChangeEvent, FormEvent } from "react";
import { ArticleRequestModel } from "../../models/ArticleRequestModel"; 
import { Button } from "react-bootstrap"; 
import { editArticle } from "features/articles/api/editArticle";
import { useNavigate } from "react-router-dom";

interface EditArticlePageProps {
    article: ArticleRequestModel;
}

export default function EditArticle({ article }: EditArticlePageProps): JSX.Element {

    const [formData, setFormData] = useState<ArticleRequestModel>(article);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleChanges = (
        e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ): void => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    const handleTagChanges = (e: React.ChangeEvent<HTMLSelectElement>): void => {
        const { value } = e.target; // Get the selected value
        setFormData({ ...formData, tagsTag: value }); // Update tagsTag in formData
    };

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        console.log("Submit button clicked");
        if (!validate()) {
            console.log("Validation failed");
            return;
        }

        try {
            console.log("Sending request to editArticle");
            await editArticle(article.articleId, formData);
            alert("Article updated successfully");
            navigate(`/articles/${article.articleId}`);
        } catch (err) {
            console.error("Error updating article:", err);
            setError('Failed to update article. Please try again later.');
        }
    }

    const validate = (): boolean => {
        const newErrors: { [key: string]: string } = {};

        if (!formData.title) newErrors.title = 'Title is required';
        if (!formData.tags) newErrors.tags = 'Categories are required';
        if (!formData.tagsTag) newErrors.tagsTag = 'Tags are required';
        if (!formData.body) newErrors.body = 'Body is required';
        if (!formData.photoUrl) newErrors.photoUrl = 'Photo URL is required';

        if (Object.keys(newErrors).length > 0) {
            console.error("Validation errors:", newErrors);
            setError('Please fix the errors above.');
            return false;
        }

        setError(null);
        return true;
    };

    return (
        <div className="con-color">
            <div className="container">
                <h1 className="center">Edit Article</h1>

                {/* Title and Tags Fields */}
                <div className="sameLine">
                    <div className="field" style={{ flex: 1, marginRight: '10px' }}>
                        <label>Title</label>
                        <input
                            type="text"
                            name="title"
                            value={formData.title}
                            onChange={handleChanges}
                        />
                    </div>
                    
                    <div className="field" style={{ flex: 1 }}>
                        <label>Categories</label>
                        <input
                            type="text"
                            name="tags"
                            value={formData.tags}
                            onChange={handleChanges}
                        />
                    </div>
                </div>

                {/* Tags Select Field */}
                <div className="field">
                    <label>Tags</label>
                    <select
                        name="tagsTag"
                        value={formData.tagsTag}
                        onChange={handleTagChanges}
                    >
                        <option value="NBA">NBA</option>
                        <option value="NHL">NHL</option>
                        <option value="UFC">UFC</option>
                        <option value="NFL">NFL</option>
                        <option value="MLB">MLB</option>
                    </select>
                </div>

                {/* Description Field */}
                <div className="field">
                    <label>Description</label>
                    <textarea
                        name="descitpion"
                        value={formData.articleDescpition}
                        onChange={handleChanges}
                    />
                </div>

                {/* Photo URL Field */}
                <div className="field">
                    <label>Photo URL</label>
                    <input
                        type="text"
                        name="photoUrl"
                        value={formData.photoUrl}
                        onChange={handleChanges}
                    />
                </div>

                {/* Body Field */}
                <div className="field">
                    <label>Body</label>
                    <textarea
                        name="body"
                        value={formData.body}
                        onChange={handleChanges}
                    />
                </div>
                    
                    <Button type="submit" onClick={handleSubmit}>
                        Update Article
                    </Button>
                    
            </div>
        </div>
    );
}
