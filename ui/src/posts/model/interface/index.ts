export interface Author{
    id: number;
    username: string;
}

export interface Post{
    id: number;
    title: string;
    body: string;
    author?: Author;
}

export interface Sort{
    property: string;
    direction: 'ASC' | 'DESC';
}

export interface SearchCriteria{
    page: number;
    pageSize: number;
    sort: Sort;
}

export interface PaginationState{
    search: SearchCriteria;
    currentCount: number;
    currentPage: number;
    totalCount: number;
    totalPages: number;
}