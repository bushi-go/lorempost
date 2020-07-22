import {createSlice, createAsyncThunk, PayloadAction}  from "@reduxjs/toolkit";
import {getPosts} from "../../service/PostApi";
import { SearchCriteria, Post, PaginationState, Sort } from "../interface";


export const fetchPosts = createAsyncThunk("posts/fetchPosts", async (criteria: SearchCriteria) => {
    const response = await getPosts(criteria);
    return response;
})

export interface PostListState{
    entities:Post[];
    pagination: PaginationState;
    loading: "idle" | "pending" | "suceeded" | "failed";
    error?: string ;
}

const initialState : PostListState = {
    entities:[], loading: "idle",
    pagination: {
        search: {page: 0, pageSize: 50, sort: { property: "title", direction: "ASC"}},
        currentCount: 0,
        currentPage: 0,
        totalPages: 0,
        totalCount: 0
    }
}

export const PostSlice = createSlice({
    name: "posts",
    initialState,
    reducers:{
        nextPage: (state, action: PayloadAction<number>) => {
            if(state.pagination.search.page < state.pagination.totalPages-1){
                state.pagination.search.page = state.pagination.search.page + 1
            }
        },
        previousPage: (state, action: PayloadAction<number>) => {
            if(state.pagination.search.page > 0){
                state.pagination.search.page = state.pagination.search.page - 1;
            }else{
                state.pagination.search.page = 0;
            } 
        },
        lastPage:(state, action: PayloadAction<undefined>) => {
            state.pagination.search.page = state.pagination.totalPages -1;
        },
        firstPage:(state, action: PayloadAction<undefined>)=> {
            state.pagination.search.page = 0;
        },
        pageSize: (state, action: PayloadAction<number>) => {
            state.pagination.search.pageSize = action.payload;
        },
        sort: (state, action: PayloadAction<Sort>) => {
            state.pagination.search.sort.property = action.payload.property;
            state.pagination.search.sort.direction = action.payload.direction;
        }

    },
    extraReducers: builder => {
        builder.addCase(fetchPosts.pending, (state, action) => {
            state.loading = "pending";
        });
        builder.addCase(fetchPosts.fulfilled, (state,action)=>{
            
            state.loading = "suceeded";
            state.entities = action.payload.content;
            state.pagination.currentCount = action.payload.numberOfElement;
            state.pagination.currentPage = action.payload.number;
            state.pagination.totalCount = action.payload.totalElements;
            state.pagination.totalPages = action.payload.totalPages;
        })
    }
});

