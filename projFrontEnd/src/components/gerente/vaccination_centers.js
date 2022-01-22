import { useState } from 'react';
import { ListSubheader, List, ListItemButton, ListItemText, LinearProgress, Box } from '@mui/material';
import NextLink from 'next/link';
import ArrowForwardIos from '@mui/icons-material/ArrowForwardIos';

// const centros = ['Centro de Vacinação de Aveiro', 'Centro de Vacinação do Porto','Centro de Vacinação de Lisboa', 'Centro de Vacinação de Coimbra','Centro de Vacinação de Setubal','Centro de Vacinação de Faro'];


const NestedList = (props) => {
  const [open, setOpen] = useState(true);
  const {centros} = props;
    
  return (
    <>
    <List
      sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper', pt: 2 }}
      component="nav"
      aria-labelledby="nested-list-subheader"
      subheader={
        <ListSubheader component="div" id="nested-list-subheader">
          Centros de Vacinação
        </ListSubheader>
      }
    >
      {centros.length > 0 ? centros.map((centro) => (
        <NextLink 
          href={{
            pathname: "/vaccination_center",
            query: { id: centro.id },
          }} 
          // as="/vaccination_center"
          key={centro.id}
          passHref
        >
          <ListItemButton>
            <ListItemText primary={centro.nome} />
            <ArrowForwardIos />
          </ListItemButton>
        </NextLink>
      )) :  
      <Box sx={{ width: '100%' }}>
        <LinearProgress />
      </Box>}
    </List> 
    </>
  );
}

export default NestedList;