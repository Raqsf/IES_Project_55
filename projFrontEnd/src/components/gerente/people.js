import React from 'react';
import { List, ListSubheader, ListItemButton, ListItemText } from '@mui/material';
//import { customers } from '../__mocks__/customers';
import api from "../../api";

export const People = () => {
    return(
        <>
    <List
        sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper', pt: 2 }}
        component="nav"
        aria-labelledby="nested-list-subheader"
        subheader={
            <ListSubheader component="div" id="nested-list-subheader">
            Pessoas A Serem Vacinadas
            </ListSubheader>
        }
        >
        
            <ListItemButton /* onClick={handleClick} */ >
                <ListItemText primary="nome" />
            </ListItemButton>
    </List> 
    </>
    );
}