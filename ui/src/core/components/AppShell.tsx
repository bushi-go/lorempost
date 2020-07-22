import React from 'react';
import {
    CssBaseline,
    AppBar,
    useScrollTrigger,
    Toolbar,
    Typography,
    Container,
    makeStyles,
    Theme,
    createStyles,
    Zoom,
    Fab,
    Grid,
    useMediaQuery} from '@material-ui/core';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';

interface Props {
    children: React.ReactElement;
    appBarComponent?: React.ReactElement;
}

interface ScrollProps extends Props {
    anchor: React.RefObject<HTMLDivElement>;
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            position: 'fixed',
            bottom: theme.spacing(2),
            right: theme.spacing(2),
        },
    }),
);

function ElevationScroll(props: Props) {
    const { children } = props;
    const trigger = useScrollTrigger({
        disableHysteresis: true,
        threshold: 0
    });
    return React.cloneElement(children, {
        elevation: trigger ? 4 : 0
    });
};

function ScrollTop(props: ScrollProps) {
    const { anchor, children } = props;
    const classes = useStyles();
    const trigger = useScrollTrigger({
        disableHysteresis: true,
        threshold: 100,
    });

    const handleClick = () => {

        if (anchor) {
            anchor?.current?.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    };
    return (
        <Zoom in={trigger}>
            <div onClick={handleClick} role="presentation" className={classes.root}>
                {children}
            </div>
        </Zoom>
    );
}

export function AppShell(props: Props) {
    const { children, appBarComponent } = props;
    const anchor = React.useRef<HTMLDivElement>(null);
    const mobile = useMediaQuery('(max-width:600px)');
    return (<>
        <CssBaseline />
        <ElevationScroll>
            <AppBar>
                <Toolbar>
                    <Grid container direction={mobile ? "column": "row"} alignItems="center" justify="space-between" style={{width:"100%"}}>
                        <Grid item xs>
                            <Typography variant="h4">Lorem Posts</Typography>
                        </Grid>
                        <Grid item xs style={{width:"100%", padding:"2%"}}>
                            {appBarComponent && appBarComponent}
                        </Grid>
                    </Grid>
                </Toolbar>
            </AppBar>
        </ElevationScroll>
        <Toolbar ref={anchor} />
        <Container>
            {children}
        </Container>
        <ScrollTop anchor={anchor}>
            <Fab color="secondary" size="small" aria-label="scroll back to top">
                <KeyboardArrowUpIcon />
            </Fab>
        </ScrollTop>
    </>
    );
}