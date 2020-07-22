import { CardHeader } from '@material-ui/core';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Skeleton from '@material-ui/lab/Skeleton';
import React from 'react';
import { Post } from '../model/interface';



export function PostCard(props: { post: Post, loading: string }) {
    const { loading } = props;
    const { title, body, author } = props && props.post;
    return <Card >
        {loading === 'pending' ?
            <CardHeader
                title={<Skeleton width={240} variant="text" animation="wave"></Skeleton>}
                subheader={<Skeleton width={240} variant="text" animation="wave"></Skeleton>}
            /> : <CardHeader
                titleTypographyProps={{ color: "primary" }}
                title={title}
                subheader={author && `by ${author.username}`} />}
        <CardContent>
            {loading === 'pending' ?
                <>
                    <Skeleton width={240} variant="text" animation="wave"></Skeleton>
                    <Skeleton width={240} variant="text" animation="wave"></Skeleton>
                    <Skeleton width={240} variant="text" animation="wave"></Skeleton>
                    <Skeleton width={240} variant="text" animation="wave"></Skeleton>
                </> :
                <Typography >
                    {body}
                </Typography>}
        </CardContent>
    </Card>;
}