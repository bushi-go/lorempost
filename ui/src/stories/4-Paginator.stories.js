import React from 'react';
import {Paginator} from '../core/components/Paginator'; 
import { Provider } from 'react-redux';
import { configureStore, createReducer } from '@reduxjs/toolkit';

export default {
    title: 'Paginator',
    component: Paginator,
  };

  const store = configureStore({
      reducer: createReducer({}, {nextPage: (state)=> state})
  });

  export const basicPaginator = ()=> <Provider store={store}><Paginator></Paginator></Provider>

  basicPaginator.story = {
      name: "Basic Paginator"
  }