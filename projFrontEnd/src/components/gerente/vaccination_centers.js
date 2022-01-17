import * as React from 'react';
import { ListSubheader, List, ListItemButton, ListItemText, LinearProgress, Box } from '@mui/material';
import NextLink from 'next/link';
import ArrowForwardIos from '@mui/icons-material/ArrowForwardIos';

// const centros = ['Centro de Vacinação de Aveiro', 'Centro de Vacinação do Porto','Centro de Vacinação de Lisboa', 'Centro de Vacinação de Coimbra','Centro de Vacinação de Setubal','Centro de Vacinação de Faro'];


const NestedList = (props) => {
  const [open, setOpen] = React.useState(true);
  const {centros} = props;
  // const [centros, setCentros] = React.useState([]);

  // // const handleClick = () => {
  // //   alert("HELLO");
  // //   setOpen(!open);
  // // };
  // // function getVaccinationCenters() {
    
  //   const headers = {
  //     "Access-Control-Allow-Origin": "*",
  //     "Content-Type": "application/json",
  //   };

  //   React.useEffect(() => {
  //     const getData = async () => {
  //       const data = await api.get(
  //         `/centrovacinacao`, headers
  //       ).then((response) => {
  //         setCentros(response.data);
  //       })
  //       .catch((err) => {
  //         console.error("ops! ocorreu um erro" + err);
  //         alert("Erro");
  //       });
  //     };
  //     getData();
  //   }, []);
    
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
          <ListItemButton /* onClick={handleClick} */ >
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