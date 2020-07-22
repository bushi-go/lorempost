import React from 'react';
import { Grid } from '@material-ui/core';
import { PostCard } from './PostCard';
import { Post } from '../model/interface';
export interface PostGridProps {
    posts: readonly Post[];
    loading: "idle" | "pending" | "suceeded" | "failed";
}

export function PostGrid(props: PostGridProps) {
    const { posts, loading } = props;
    return (
        <Grid container spacing={3}>
            {posts.map(post =>
                <Grid xs sm={6} lg={4} item key={post.id} style={{ display: 'flex' }}>
                    <PostCard post={post} loading={loading}></PostCard>
                </Grid>)}
        </Grid>);
};