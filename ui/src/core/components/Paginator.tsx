import React, { ChangeEvent } from 'react';
import { Pagination, PaginationItem } from '@material-ui/lab';
import { PaginationState } from '../../posts/model/interface';
import { useDispatch } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { Typography, Grid, withStyles, useTheme, Select, MenuItem, makeStyles, useMediaQuery } from '@material-ui/core';

export interface PaginatorProps {
    pagination: PaginationState;
    nextPageAction: (page: number) => PayloadAction<number>;
    previousPageAction: (page: number) => PayloadAction<number>;
    pageSizeAction: (pageSize: number) => PayloadAction<number>
}




export function Paginator(props: PaginatorProps) {
    const theme = useTheme();
    const PaginationItemWithStyles = withStyles({
        textSecondary: {
            color: theme.palette.secondary.contrastText,
        }
    })(PaginationItem);
    const useStyles = makeStyles({
        root: {
            color: theme.palette.secondary.contrastText
        }, nativeInput: {
            color: theme.palette.secondary.contrastText,
            borderBottomColor: theme.palette.secondary.contrastText
        }
    });
    const classes = useStyles();
    const { pagination, nextPageAction, previousPageAction, pageSizeAction } = props;
    const mobile = useMediaQuery('(max-width:600px)');
    const dispatch = useDispatch();
    const onPaginationChange = (event: ChangeEvent<unknown>, page: number) => {
        if (page - 1 > pagination.currentPage) {
            dispatch(nextPageAction(page));
        }
        if (page - 1 < pagination.currentPage) {
            dispatch(previousPageAction(page));
        }
    }
    const onPageSizeChange = (event: React.ChangeEvent<{
        value: unknown;
    }>) => {
        dispatch(pageSizeAction(event.target.value as number));
    }
    return (
        <Grid container direction="row" justify={mobile ? "space-between": "flex-end"} style={{width:"100%"}}>
            <Grid item xs={8} lg>
                <Pagination
                    page={pagination.currentPage + 1}
                    count={pagination.totalPages}
                    onChange={onPaginationChange}
                    color="secondary"
                    renderItem={(props) => <PaginationItemWithStyles {...props} ></PaginationItemWithStyles>}
                    boundaryCount={1}
                    siblingCount={1}
                />
            </Grid>
            <Grid item xs lg>
                <Select value={pagination.search.pageSize} onChange={onPageSizeChange} 
                        classes={{ root: classes.root }} 
                        inputProps={{ classes: { root: classes.root, nativeInput: classes.nativeInput } }}>
                    <MenuItem value={10}>10</MenuItem>
                    <MenuItem value={20}>20</MenuItem>
                    <MenuItem value={30}>30</MenuItem>
                    <MenuItem value={40}>40</MenuItem>
                    <MenuItem value={50}>50</MenuItem>
                </Select>
            </Grid>
        </Grid>)
}
