import React from 'react';
import { ThemeProvider, createMuiTheme } from '@material-ui/core';


const palette = {
    primary: { main: "#64c9c3", contrastText: "#fff" },
    secondary: { main: "#f68092", contrastText: "#fff" },
    contrastThreshold: 3
}

const theme = createMuiTheme({
    palette: palette
});

export function Theming(props: any) {

    return <ThemeProvider theme={theme}>
        {props.children}
    </ThemeProvider>
}