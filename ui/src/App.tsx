import React from 'react';
import { PostPage } from './posts/PostPage';
import { configureStore } from '@reduxjs/toolkit';
import { PostSlice } from './posts/model/slice/PostSlice';
import { Provider } from 'react-redux';
import { AppShell } from './core/components/AppShell';
import { Theming } from './core/components/Theming';
import { Box } from '@material-ui/core';
//import {BrowserRouter as Router, Switch, Route, Link} from 'react-router-dom'
const store = configureStore({
    reducer: {
        posts: PostSlice.reducer
    }
})

function PaginatorContainer(){
    return <Box bgcolor="primary" id="paginator-container" width="100%"/>;
}

function App() {
    
  return (
    <Provider store ={store}>
        <Theming>
        <AppShell appBarComponent={<PaginatorContainer/>}>
            <PostPage/>
        </AppShell>
        </Theming>
    </Provider>
  );
}

export default App;
