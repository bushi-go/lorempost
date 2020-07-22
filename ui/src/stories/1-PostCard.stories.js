import React from 'react';
import {PostCard} from '../posts/components/PostCard';

export default {
    title: 'Post Card',
    component: PostCard,
  };

  const post= {
      title: "Lorem Ipsum",
      body: `Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
        Curabitur placerat erat nec sollicitudin hendrerit. 
        Pellentesque convallis malesuada condimentum. 
        Vivamus ornare efficitur lorem ac eleifend. 
        Nulla malesuada turpis id odio scelerisque molestie. 
        Suspendisse rhoncus fermentum est id lobortis. 
        Cras laoreet imperdiet libero ac dignissim. 
        Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. 
        Vivamus venenatis lectus ac iaculis sagittis. Fusce ornare interdum dui. 
        Cras vel odio dignissim, cursus est quis, lacinia erat.`,
       author:{
           id: 1,
           username: "JohnDoe"
       }
  }

export const withAPost = () => <PostCard post={post}></PostCard>;

withAPost.story = {
    name: 'with a Post should render'
  };

export const withoutPost = () => <PostCard></PostCard>;

withoutPost.story = {
    name: 'without a post should not render'
}

const postWithourAuthor = Object.assign({}, {title: post.title, body: post.body});

export const withoutAuthorShouldRender = () => <PostCard post={postWithourAuthor}></PostCard>;

withoutAuthorShouldRender.story = {
    name: 'without author should render a post with title and no subtitle'
}