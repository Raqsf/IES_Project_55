import * as React from 'react';
import { ListSubheader, List, ListItemButton, ListItemText, Collapse } from '@mui/material';
import { ExpandLess, ExpandMore, StarBorder } from '@mui/icons-material';

const centros = ['Centro de Vacinação de Aveiro', 'Centro de Vacinação do Porto','Centro de Vacinação de Lisboa', 'Centro de Vacinação de Coimbra','Centro de Vacinação de Setubal','Centro de Vacinação de Faro'];


const NestedList = () => {
  const [open, setOpen] = React.useState(true);

  const handleClick = () => {
    setOpen(!open);
  };

  return (
    <>
    <List
      sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}
      component="nav"
      aria-labelledby="nested-list-subheader"
      subheader={
        <ListSubheader component="div" id="nested-list-subheader">
          Centros de Vacinação
        </ListSubheader>
      }
    >
      {centros.map((centro) => (
          <ListItemButton>
          <ListItemText primary={centro} />
        </ListItemButton>
      ))}
    </List> 
    </>
  );
}

export default NestedList;