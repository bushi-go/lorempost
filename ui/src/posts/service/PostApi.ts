import { SearchCriteria } from "../model/interface";

const API_POSTS = "http://localhost:8080/api/posts"

export async function getPosts(criteria: SearchCriteria | null = null){
    return (await fetch(API_POSTS + getQueryString(criteria), {method: "GET"})).json();
}

function getQueryString(criteria: SearchCriteria | null = null){
    let query = "";
    if(!criteria){
        return "";
    }
    if(criteria.page|| criteria.pageSize || criteria.sort){
        query+="?";
    }
    if(criteria.page){
       query+=`page=${criteria.page}${criteria.pageSize||criteria.sort ? "&":""}`;
    }
    if(criteria.pageSize){
        query+=`size=${criteria.pageSize}${criteria.sort ? "&": ""}`;
    }
    if(criteria.sort){
        query+=`sort=${criteria.sort.property}${criteria.sort.direction ? ","+criteria.sort.direction:""}`
    }
    return query;
}