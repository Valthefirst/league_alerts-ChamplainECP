import React, { useState } from 'react';
import axios from 'axios';
import { ArticleRequestModelI } from 'features/articles/models/ArticleRequestModel';

import "./ArticleForm.css"

const ArticleForm = () => {
    const [formData, setFormData] = useState<ArticleRequestModelI>({
        title: '',
        body: '',
        wordCount: 300,
        articleStatus: '',
        tags: '',
        tagsTag: '',
        timePosted: '',
        authorIdentifier: '',
    });

    enum TagsTagEnum{
        Tag1 = 'NBA',
        Tag2 = 'NHL',
        Tag3 = 'UFC',
        Tag4 = 'NFL',
        Tag5 = 'MLB'
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
            tagsTag: e.target.value
        });
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await axios.post('/api/articles', formData);
            console.log('Article created:', response.data);
        } catch (error) {
            console.error('Error creating article:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="title" placeholder="Title" value={formData.title} onChange={handleChange} />
            <textarea name="body" placeholder="Body" value={formData.body} onChange={handleChange} />

            <select name="tagsTag" value={formData.tagsTag} onChange={handleChange}>
                <option value={TagsTagEnum.Tag1}>NBA</option>
                <option value={TagsTagEnum.Tag2}>NHL</option>
                <option value={TagsTagEnum.Tag3}>UFC</option>
                <option value={TagsTagEnum.Tag4}>NFL</option>
                <option value={TagsTagEnum.Tag5}>MLB</option>

            </select>
            



            <button type="submit">Create Article</button>
        </form>
    );
};

export default ArticleForm;