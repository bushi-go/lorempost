import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchPosts, PostListState, PostSlice } from './model/slice/PostSlice';
import { PostGrid } from './components/PostGrid';
import { Box, Container, Portal } from '@material-ui/core';
import { Paginator } from '../core/components/Paginator';


export function PostPage() {

    const dispatch = useDispatch();

    const { posts, error, loading, pagination } = useSelector((state: { posts: PostListState }) => {
        return { posts: state.posts.entities, error: state.posts.error, loading: state.posts.loading, pagination: state.posts.pagination }
    });
    useEffect(() => {
        dispatch(fetchPosts(pagination.search));
    }, [dispatch, pagination.search]);

    if (error || loading === "failed") {
        return <div>error</div>
    }

    const paginatorContainer = document.getElementById("paginator-container");
    return (<>
        {paginatorContainer && 
            <Portal container={paginatorContainer}>
                <Paginator
                    pagination={pagination}
                    nextPageAction={PostSlice.actions.nextPage}
                    previousPageAction={PostSlice.actions.previousPage}
                    pageSizeAction={PostSlice.actions.pageSize}
                />
            </Portal>}
        <Container>
            <Box marginTop={10}>
                <PostGrid posts={posts} loading={loading} />
            </Box>
        </Container>
    </>
    );
}